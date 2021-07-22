package org.jlw.ulid;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import javax.sql.DataSource;

import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.MSSQLServerContainer;
import org.testcontainers.containers.OracleContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import io.ebean.Database;
import io.ebean.DatabaseFactory;
import io.ebean.annotation.PersistBatch;
import io.ebean.config.DatabaseConfig;
import io.ebean.datasource.DataSourceConfig;
import lombok.Getter;

public abstract class AbstractDatabaseTestContainer
{
	private JdbcDatabaseContainer<?> container;

	@Getter
	private Database database;

	protected void initContainer(final DockerImageName image, final DatabasePlatform platform) throws SQLException
	{
		switch (platform)
		{
			case POSTGRES:
				container = new PostgreSQLContainer<>(image);
				break;

			case ORACLE:
				container = new OracleContainer(image);
				break;

			case MSSQL_16:
			case MSSQL_17:
				container = new MSSQLServerContainer<>(image)
						.acceptLicense();
				break;

			default:
				throw new IllegalStateException("unknown database platform");
		} // switch

		container.start();

		// data source configuration
		final DataSourceConfig dataSourceConfig = new DataSourceConfig();

		dataSourceConfig.setUsername(container.getUsername());
		dataSourceConfig.setPassword(container.getPassword());
		dataSourceConfig.setDriver(container.getDriverClassName());
		dataSourceConfig.setUrl(container.getJdbcUrl());

		final DatabaseConfig databaseConfig = new DatabaseConfig();

		// database configuration
		databaseConfig.setDataSourceConfig(dataSourceConfig);
		databaseConfig.setDataTimeZone("UTC");
		databaseConfig.setName("TEST");

		databaseConfig.setPersistBatch(PersistBatch.ALL);
		databaseConfig.setPersistBatchSize(100);
		databaseConfig.setDefaultServer(false);
		databaseConfig.addPackage(ULID.class.getPackageName());

		switch (platform)
		{
			case MSSQL_16:
				databaseConfig.setDatabasePlatformName("sqlserver16");
				break;

			case MSSQL_17:
				databaseConfig.setDatabasePlatformName("sqlserver17");
				break;

			default:
				break;
		} // switch

		// create database and put them in a HashMap
		database = DatabaseFactory.create(databaseConfig);
		createSchema(database);
	}

	private void createSchema(final Database db) throws SQLException
	{
		final String sql = "CREATE TABLE TEST_TABLE (TEST_ID CHAR(26) PRIMARY KEY, TEST_VALUE INTEGER NOT NULL, CONSTRAINT utv UNIQUE(TEST_VALUE))";
		final DataSource ds = db.getDataSource();

		try (final Connection con = ds.getConnection())
		{
			try (final Statement stmt = con.createStatement())
			{
				stmt.executeUpdate(sql);
			} // try
		} // try
	}

	protected void destroy()
	{
		container.close();
	}
}
