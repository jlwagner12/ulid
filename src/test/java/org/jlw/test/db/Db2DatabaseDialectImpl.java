package org.jlw.test.db;

import java.time.Duration;

import org.testcontainers.containers.Db2Container;
import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.containers.MSSQLServerContainer;
import org.testcontainers.utility.DockerImageName;

public class Db2DatabaseDialectImpl extends ContainerDatabaseDialectImpl
{
	private final DockerImageName imageName = DockerImageName.parse("ibmcom/db2")
			.withTag("11.5.0.0a");

	private Db2Container container;

	@Override
	public void createInstance()
	{
		container = new Db2Container(imageName)
				.acceptLicense()
				// DB2 takes an AMAZINGLY LONG time to start
				// you might as well go get a cup of coffee
				.withConnectTimeoutSeconds((int) Duration.ofMinutes(10).toSeconds());
		container.start();
	}

	@Override
	public String getDriver()
	{
		return "com.ibm.db2.jcc.DB2Driver";
	}

	@Override
	protected JdbcDatabaseContainer<?> getContainer()
	{
		return container;
	}

	@Override
	public String getDatabaseName()
	{
		return container.getDatabaseName();
	}
}
