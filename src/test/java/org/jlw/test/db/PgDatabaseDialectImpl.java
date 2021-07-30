package org.jlw.test.db;

import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

public class PgDatabaseDialectImpl extends ContainerDatabaseDialectImpl
{
	private final DockerImageName imageName = DockerImageName.parse("postgres")
			.withTag("12");

	private PostgreSQLContainer<?> container;

	@Override
	public void createInstance()
	{
		container = new PostgreSQLContainer<>(imageName);
		container.start();
	}

	@Override
	public String getDriver()
	{
		return "org.postgresql.Driver";
	}

	@Override
	protected JdbcDatabaseContainer<?> getContainer()
	{
		return container;
	}
}
