package org.jlw.test.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Optional;

import javax.sql.DataSource;

import org.jlw.ulid.EbeanMappingType;
import org.jlw.ulid.UlidEbeanConfiguration;
import org.testcontainers.shaded.org.apache.commons.lang.StringUtils;

import io.avaje.config.Config;
import io.ebean.Database;
import io.ebean.DatabaseFactory;
import io.ebean.annotation.Platform;
import io.ebean.config.DatabaseConfig;
import io.ebean.config.dbplatform.IdType;
import io.ebean.datasource.DataSourceConfig;

public class DatabaseInitializer
{
	private final DatabaseDialect dialect;

	private final EbeanMappingType type;

	public DatabaseInitializer(final DatabaseDialect dialect)
	{
		this(dialect, EbeanMappingType.STRING);
	}

	public DatabaseInitializer(final DatabaseDialect dialect, final EbeanMappingType type)
	{
		this.dialect = Objects.requireNonNull(dialect);
		this.type = Objects.requireNonNull(type);
	}

	public Database getEbeanDatabase() throws SQLException
	{
		initialize();

		final DatabaseDialectImpl init = dialect.getImplementation();
		final DataSourceConfig dataSourceConfig = createDataSourceConfig(init);
		final DatabaseConfig databaseConfig = createDatabaseConfig(init, dataSourceConfig);
		final Database db = DatabaseFactory.create(databaseConfig);

		createSchema(db.getDataSource());
		return db;
	}

	private DatabaseConfig createDatabaseConfig(final DatabaseDialectImpl init, final DataSourceConfig dataSourceConfig)
	{
		final DatabaseConfig databaseConfig = new DatabaseConfig();

		Optional.ofNullable(init.getDriver())
				.filter(v -> StringUtils.containsIgnoreCase(v, "sqlserver"))
				.ifPresent(v -> databaseConfig.setDatabasePlatformName(Platform.SQLSERVER17.name().toLowerCase()));

		Optional.ofNullable(init.getDatabaseName())
				.filter(StringUtils::isNotBlank)
				.ifPresent(databaseConfig::setName);

		Optional.ofNullable(init.getSchemaName())
				.filter(StringUtils::isNotBlank)
				.ifPresent(databaseConfig::setDbSchema);

		Config.setProperty(UlidEbeanConfiguration.PROPERTY_NAME, type.name());
		databaseConfig.setDataTimeZone("UTC");
		databaseConfig.setDataSourceConfig(dataSourceConfig);
		databaseConfig.setDefaultServer(false);
		databaseConfig.loadFromProperties();

		return databaseConfig;
	}

	private DataSourceConfig createDataSourceConfig(final DatabaseDialectImpl init)
	{
		final DataSourceConfig dataSourceConfig = new DataSourceConfig();

		dataSourceConfig.setUsername(init.getUserName());
		dataSourceConfig.setPassword(init.getPassword());
		dataSourceConfig.setDriver(init.getDriver());
		dataSourceConfig.setUrl(init.getJdbcUrl());

		return dataSourceConfig;
	}

	private void initialize()
	{
		final DatabaseDialectImpl impl = dialect.getImplementation();

		impl.createInstance();
	}

	private void createSchema(final DataSource ds) throws SQLException
	{
		final DatabaseDialectInitializer init = dialect.getInitializer();

		try (final Connection connection = ds.getConnection())
		{
			try
			{
				init.createStringIdTable(connection);
				init.createByteIdTable(connection);
				init.createEmbeddedIdTable(connection);

				if (init.hasUuid())
				{
					init.createUuidIdTable(connection);
				} // if

				connection.commit();
			} // try

			catch (final SQLException e)
			{
				connection.rollback();
				throw e;
			} // catch
		} // try
	}
}
