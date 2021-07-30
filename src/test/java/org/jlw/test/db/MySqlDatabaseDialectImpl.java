package org.jlw.test.db;

import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.containers.OracleContainer;
import org.testcontainers.utility.DockerImageName;

public class MySqlDatabaseDialectImpl extends ContainerDatabaseDialectImpl
{
	private final DockerImageName imageName = DockerImageName.parse("mysql")
			.withTag("8.0.26");

	private MySQLContainer<?> container;

	@Override
	public void createInstance()
	{
		container = new MySQLContainer<>(imageName);
		container.start();
	}

	@Override
	public String getDriver()
	{
		return "com.mysql.cj.jdbc.Driver";
	}

	@Override
	protected JdbcDatabaseContainer<?> getContainer()
	{
		return container;
	}
}
