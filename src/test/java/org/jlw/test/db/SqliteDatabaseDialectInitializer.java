package org.jlw.test.db;

public class SqliteDatabaseDialectInitializer implements DatabaseDialectInitializer
{
	private static final String DEFAULT_EMBEDDED_TABLE = "CREATE TABLE EMBEDDED_TABLE (TEST_ID_MSB INTEGER NOT NULL, TEST_ID_LSB INTEGER NOT NULL, VALUE_COLUMN INT NOT NULL, CONSTRAINT embedded_pk PRIMARY KEY (TEST_ID_MSB, TEST_ID_LSB))";

	public String getEmbeddedBasedTable()
	{
		return DEFAULT_EMBEDDED_TABLE;
	}
}
