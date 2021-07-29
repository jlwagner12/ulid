package org.jlw.test.db;

public class MySqlDatabaseDialectInitializer implements DatabaseDialectInitializer
{
	public boolean hasUuid()
	{
		return false;
	}
}
