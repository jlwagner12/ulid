package org.jlw.ulid;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "TEST_TABLE")
public class DefaultIdTestEntity
{
	@Id
	@Column(name = "TEST_ID")
	private ULID id = ULID.randomULID();

	@Column(name = "TEST_VALUE")
	private int value;
}
