package org.jlw.test.db;

import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.OracleContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

public class OracleDatabaseDialectImpl extends ContainerDatabaseDialectImpl
{
	private final DockerImageName imageName = DockerImageName.parse("wnameless/oracle-xe-11g-r2:18.04-apex");

	private OracleContainer container;

	@Override
	public void createInstance()
	{
		container = new OracleContainer(imageName);
		container.start();
	}

	@Override
	public String getDriver()
	{
		return "oracle.jdbc.driver.OracleDriver";
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
