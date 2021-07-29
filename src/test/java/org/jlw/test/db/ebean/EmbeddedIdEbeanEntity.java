package org.jlw.test.db.ebean;

import javax.persistence.AttributeOverride;
import javax.persistence.AttributeOverrides;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import org.jlw.test.db.TestEntity;
import org.jlw.ulid.ULID;

@Entity
@Table(name = "EMBEDDED_TABLE")
public class EmbeddedIdEbeanEntity implements TestEntity
{
	@EmbeddedId
	@AttributeOverrides({
			@AttributeOverride(name = "msb", column = @Column(name = "TEST_ID_MSB")),
			@AttributeOverride(name = "lsb", column = @Column(name = "TEST_ID_LSB"))
	})
	private ULID id = ULID.nextULID();

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
