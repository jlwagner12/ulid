package org.jlw.test.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public interface DatabaseDialectInitializer
{
	String DEFAULT_STRING_TABLE = "CREATE TABLE STRING_TABLE (TEST_ID CHAR(26) NOT NULL, VALUE_COLUMN INT NOT NULL, CONSTRAINT str_pk PRIMARY KEY (TEST_ID))";

	String DEFAULT_BYTE_TABLE = "CREATE TABLE BINARY_TABLE (TEST_ID BINARY(16) NOT NULL, VALUE_COLUMN INT NOT NULL, CONSTRAINT bin_pk PRIMARY KEY (TEST_ID))";

	String DEFAULT_UUID_TABLE = "CREATE TABLE UUID_TABLE (TEST_ID UUID NOT NULL, VALUE_COLUMN INT NOT NULL, CONSTRAINT uuid_pk PRIMARY KEY (TEST_ID))";

	String DEFAULT_EMBEDDED_TABLE = "CREATE TABLE EMBEDDED_TABLE (TEST_ID_MSB BIGINT NOT NULL, TEST_ID_LSB BIGINT NOT NULL, VALUE_COLUMN INT NOT NULL, CONSTRAINT embedded_pk PRIMARY KEY (TEST_ID_MSB, TEST_ID_LSB))";

	default String getStringBasedTable()
	{
		return DEFAULT_STRING_TABLE;
	}

	default String getByteBasedTable()
	{
		return DEFAULT_BYTE_TABLE;
	}

	default String getUuidBasedTable()
	{
		return DEFAULT_UUID_TABLE;
	}

	default String getEmbeddedBasedTable()
	{
		return DEFAULT_EMBEDDED_TABLE;
	}

	default void createStringIdTable(final Connection connection) throws SQLException
	{
		try (final Statement statement = connection.createStatement())
		{
			statement.executeUpdate(getStringBasedTable());
		} // try
	}

	default void createByteIdTable(final Connection connection) throws SQLException
	{
		try (final Statement statement = connection.createStatement())
		{
			statement.executeUpdate(getByteBasedTable());
		} // try
	}

	default boolean hasUuid()
	{
		return true;
	}

	default void createUuidIdTable(final Connection connection) throws SQLException
	{
		try (final Statement statement = connection.createStatement())
		{
			statement.executeUpdate(getUuidBasedTable());
		} // try
	}

	default void createEmbeddedIdTable(final Connection connection) throws SQLException
	{
		try (final Statement statement = connection.createStatement())
		{
			statement.executeUpdate(getEmbeddedBasedTable());
		} // try
	}
}
