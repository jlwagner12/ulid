package org.jlw.test.db;

import org.jlw.ulid.ULID;

public interface TestEntity
{
	ULID getId();

	void setId(final ULID id);

	int getValue();

	void setValue(final int value);
}
