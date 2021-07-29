package org.jlw.test.db;

import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.MSSQLServerContainer;
import org.testcontainers.utility.DockerImageName;

public class MsSqlDatabaseDialectImpl extends ContainerDatabaseDialectImpl
{
	private final DockerImageName imageName = DockerImageName.parse("mcr.microsoft.com/mssql/server:2019-latest");

	private MSSQLServerContainer<?> container;

	@Override
	public void createInstance()
	{
		container = new MSSQLServerContainer<>(imageName)
				.acceptLicense();
		container.start();
	}

	@Override
	public String getDriver()
	{
		return "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	}

	@Override
	protected JdbcDatabaseContainer<?> getContainer()
	{
		return container;
	}

	@Override
	public String getDatabaseName()
	{
		return "";
	}
}
