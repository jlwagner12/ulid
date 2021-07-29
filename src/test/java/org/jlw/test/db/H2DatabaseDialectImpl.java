package org.jlw.test.db;

public class H2DatabaseDialectImpl implements DatabaseDialectImpl
{
	@Override
	public void createInstance()
	{
	}

	@Override
	public String getDriver()
	{
		return "org.h2.Driver";
	}

	@Override
	public String getJdbcUrl()
	{
		return "jdbc:h2:mem:test";
	}

	@Override
	public String getUserName()
	{
		return "sa";
	}

	@Override
	public String getPassword()
	{
		return "";
	}

	@Override
	public String getDatabaseName()
	{
		return "mem";
	}

	@Override
	public String getSchemaName()
	{
		return "test";
	}

	@Override
	public void close()
	{
	}
}
