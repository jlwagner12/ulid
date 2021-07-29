package org.jlw.test.db;

public class SqliteDatabaseDialectInitializer implements DatabaseDialectInitializer
{
	private static final String DEFAULT_BYTE_TABLE = "CREATE TABLE BINARY_TABLE (TEST_ID BLOB NOT NULL, VALUE_COLUMN INT NOT NULL, CONSTRAINT bin_pk PRIMARY KEY (TEST_ID))";

	private static final String DEFAULT_UUID_TABLE = "CREATE TABLE UUID_TABLE (TEST_ID BLOB NOT NULL, VALUE_COLUMN INT NOT NULL, CONSTRAINT uuid_pk PRIMARY KEY (TEST_ID))";

	private static final String DEFAULT_EMBEDDED_TABLE = "CREATE TABLE EMBEDDED_TABLE (TEST_ID_MSB BIGINT NOT NULL, TEST_ID_LSB BIGINT NOT NULL, VALUE_COLUMN INT NOT NULL, CONSTRAINT embedded_pk PRIMARY KEY (TEST_ID_MSB, TEST_ID_LSB))";

	public String getByteBasedTable()
	{
		return DEFAULT_BYTE_TABLE;
	}

	public String getUuidBasedTable()
	{
		return DEFAULT_UUID_TABLE;
	}

	public String getEmbeddedBasedTable()
	{
		return DEFAULT_EMBEDDED_TABLE;
	}
}
