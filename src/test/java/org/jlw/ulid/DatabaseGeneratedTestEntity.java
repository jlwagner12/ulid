package org.jlw.ulid;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "TEST_TABLE")
public class DatabaseGeneratedTestEntity
{
	@Id
	@Column(name = "TEST_ID")
	@GeneratedValue(generator = UlidGenerator.GENERATOR_NAME)
	private ULID id;

	@Column(name = "TEST_VALUE")
	private int value;
}
