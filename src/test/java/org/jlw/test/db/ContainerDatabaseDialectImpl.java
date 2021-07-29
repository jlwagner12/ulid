package org.jlw.test.db;

import java.util.Optional;

import org.testcontainers.containers.JdbcDatabaseContainer;
import org.testcontainers.lifecycle.Startable;

public abstract class ContainerDatabaseDialectImpl implements DatabaseDialectImpl
{
	protected abstract JdbcDatabaseContainer<?> getContainer();

	@Override
	public String getJdbcUrl()
	{
		return getContainer().getJdbcUrl();
	}

	@Override
	public String getUserName()
	{
		return getContainer().getUsername();
	}

	@Override
	public String getPassword()
	{
		return getContainer().getPassword();
	}

	@Override
	public String getDatabaseName()
	{
		return getContainer().getDatabaseName();
	}

	@Override
	public String getSchemaName()
	{
		return "";
	}

	@Override
	public void close()
	{
		Optional.ofNullable(getContainer())
				.ifPresent(Startable::close);
	}
}
