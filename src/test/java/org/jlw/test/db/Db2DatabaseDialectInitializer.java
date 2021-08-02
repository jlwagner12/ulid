package org.jlw.test.db;

public class Db2DatabaseDialectInitializer implements DatabaseDialectInitializer
{
	private static final String DEFAULT_BYTE_TABLE = "CREATE TABLE BINARY_TABLE (TEST_ID CHAR(16) FOR BIT DATA NOT NULL, VALUE_COLUMN INT NOT NULL, CONSTRAINT bin_pk PRIMARY KEY (TEST_ID))";

	public String getByteBasedTable()
	{
		return DEFAULT_BYTE_TABLE;
	}

	public boolean hasUuid()
	{
		return false;
	}
}
