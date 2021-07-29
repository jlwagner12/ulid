package org.jlw.test.db.hibernate;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.GenericGenerator;
import org.jlw.test.db.TestEntity;
import org.jlw.ulid.ULID;
import org.jlw.ulid.EbeanUlidGenerator;

@MappedSuperclass
public class HibernateEntity implements TestEntity
{
	@Id
	@GenericGenerator(name = EbeanUlidGenerator.GENERATOR_NAME,
			strategy = "org.jlw.ulid.UlidGenerator")
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
