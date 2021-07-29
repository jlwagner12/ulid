package org.jlw.test.db.ebean;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.jlw.test.db.TestEntity;
import org.jlw.ulid.ULID;
import org.jlw.ulid.EbeanUlidGenerator;

@MappedSuperclass
public abstract class EBeanEntity implements TestEntity
{
	@Id
	@GeneratedValue(generator = EbeanUlidGenerator.GENERATOR_NAME)
	@Column(name = "TEST_ID")
	private ULID id;

	@Column(name = "VALUE_COLUMN")
	private int value;

	public ULID getId()
	{
		return id;
	}

	public void setId(final ULID id)
	{
		this.id = id;
	}

	public int getValue()
	{
		return value;
	}

	public void setValue(final int value)
	{
		this.value = value;
	}
}
