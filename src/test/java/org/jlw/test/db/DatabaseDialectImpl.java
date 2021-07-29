package org.jlw.test.db;

public interface DatabaseDialectImpl extends AutoCloseable
{
	void createInstance();

	String getDriver();

	String getJdbcUrl();

	String getUserName();

	String getPassword();

	String getDatabaseName();

	String getSchemaName();
}
