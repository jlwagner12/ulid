package org.jlw.ulid;

import io.ebean.config.IdGenerator;

/**
 * @author jwagner
 * @implSpec This class is immutable and thread-safe.
 */
public class UlidGenerator implements IdGenerator
{
	public static final String GENERATOR_NAME = "org.jlw.ulid.gen";

	/**
	 * Return the next Id value.
	 */
	public ULID nextValue()
	{
		return ULID.randomULID();
	}

	/**
	 * The name is used to assign the {@code UlidGenerator} to a property using
	 * <code>@GeneratedValue(name=UlidGenerator.GENERATOR_NAME)</code>
	 */
	public String getName()
	{
		return GENERATOR_NAME;
	}
}
