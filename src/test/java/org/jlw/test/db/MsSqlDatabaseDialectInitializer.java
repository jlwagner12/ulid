package org.jlw.test.db;

public class MsSqlDatabaseDialectInitializer implements DatabaseDialectInitializer
{
	private static final String DEFAULT_UUID_TABLE = "CREATE TABLE UUID_TABLE (TEST_ID UNIQUEIDENTIFIER NOT NULL, VALUE_COLUMN INT NOT NULL)";

	@Override
	public String getUuidBasedTable()
	{
		return DEFAULT_UUID_TABLE;
	}
}
