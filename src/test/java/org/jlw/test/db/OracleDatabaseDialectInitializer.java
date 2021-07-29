package org.jlw.test.db;

public class OracleDatabaseDialectInitializer implements DatabaseDialectInitializer
{
	private static final String DEFAULT_BYTE_TABLE = "CREATE TABLE BINARY_TABLE (TEST_ID RAW(16) NOT NULL, VALUE_COLUMN INT NOT NULL)";

	private static final String DEFAULT_EMBEDDED_TABLE = "CREATE TABLE EMBEDDED_TABLE (TEST_ID_MSB NUMBER(20) NOT NULL, TEST_ID_LSB NUMBER(20) NOT NULL, VALUE_COLUMN INT NOT NULL, CONSTRAINT embedded_pk PRIMARY KEY (TEST_ID_MSB, TEST_ID_LSB))";

	public String getByteBasedTable()
	{
		return DEFAULT_BYTE_TABLE;
	}

	public String getEmbeddedBasedTable()
	{
		return DEFAULT_EMBEDDED_TABLE;
	}

	public boolean hasUuid()
	{
		return false;
	}
}
