package org.jlw.test.db;

public enum DatabaseDialect
{
	H2(new H2DatabaseDialectImpl(), new DefaultDatabaseDialectInitializer()),
	DB2(new Db2DatabaseDialectImpl(), new Db2DatabaseDialectInitializer()),
	MSSQL(new MsSqlDatabaseDialectImpl(), new MsSqlDatabaseDialectInitializer()),
	MYSQL(new MySqlDatabaseDialectImpl(), new MySqlDatabaseDialectInitializer()),
	ORACLE(new OracleDatabaseDialectImpl(), new OracleDatabaseDialectInitializer()),
	POSTGRESQL(new PgDatabaseDialectImpl(), new PgDatabaseDialectInitializer()),
	SQLITE(new SqliteDatabaseDialectImpl(), new SqliteDatabaseDialectInitializer());

	private final DatabaseDialectImpl impl;

	private final DatabaseDialectInitializer initializer;

	DatabaseDialect(final DatabaseDialectImpl impl, final DatabaseDialectInitializer initializer)
	{
		this.impl = impl;
		this.initializer = initializer;
	}

	public DatabaseDialectImpl getImplementation()
	{
		return impl;
	}

	public DatabaseDialectInitializer getInitializer()
	{
		return initializer;
	}
}
