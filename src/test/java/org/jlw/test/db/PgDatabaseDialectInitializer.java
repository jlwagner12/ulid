package org.jlw.test.db;

public class PgDatabaseDialectInitializer implements DatabaseDialectInitializer
{
	private static final String DEFAULT_BYTE_TABLE = "CREATE TABLE BINARY_TABLE (TEST_ID bytea NOT NULL, VALUE_COLUMN INT NOT NULL)";

	public String getByteBasedTable()
	{
		return DEFAULT_BYTE_TABLE;
	}
}
