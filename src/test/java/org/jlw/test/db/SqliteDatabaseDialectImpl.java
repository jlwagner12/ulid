package org.jlw.test.db;

public class SqliteDatabaseDialectImpl implements DatabaseDialectImpl
{
	@Override
	public void createInstance()
	{
	}

	@Override
	public String getDriver()
	{
		return "org.sqlite.JDBC";
	}

	@Override
	public String getJdbcUrl()
	{
		return "jdbc:sqlite::memory:";
	}

	@Override
	public String getUserName()
	{
		return "";
	}

	@Override
	public String getPassword()
	{
		return "";
	}

	@Override
	public String getDatabaseName()
	{
		return "memory";
	}

	@Override
	public String getSchemaName()
	{
		return "";
	}

	@Override
	public void close()
	{
	}
}
