/*
 * The MIT License (MIT)
 *
 * Copyright (c) <YEAR> Jay L. Wagner
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 *
 */
package org.jlw.ulid;

import java.util.Optional;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * JPA {@link AttributeConverter} for {@link ULID} values stored in the database as a long array in network order.
 *
 * @author jwagner
 * @implSpec This class is immutable and thread-safe.
 */
@Converter
public class UlidLongAttributeConverter implements AttributeConverter<ULID, long[]>
{
	/**
	 * Converts a {@link ULID} to a long array for storage in a database column. The long array must be at least
	 * two {@code long}s long and stored in network byte order.
	 *
	 * @param attribute
	 * 		to convert from a {@link ULID} to a {@code long[]}.
	 * @return The supplied {@link ULID} converted to a {@code long[]} if {@code attribute} is not {@code null};
	 * 		otherwise, {@code null}.
	 */
	@Override
	public long[] convertToDatabaseColumn(final ULID attribute)
	{
		return Optional.ofNullable(attribute)
				.map(ULID::longArray)
				.orElse(null);
	}

	/**
	 * Converts a {@code long[]} to a {@link ULID}.
	 *
	 * @param dbData
	 * 		a long array with at least two elements.
	 * @return The array converted to a {@link ULID} if {@code dbData} is not {@code null}; otherwise, {@code null}.
	 * @throws IllegalArgumentException
	 * 		if {@code dbData} is not {@code null} and does not represent a correct {@link ULID} formatted long array.
	 */
	@Override
	public ULID convertToEntityAttribute(final long[] dbData)
	{
		return Optional.ofNullable(dbData)
				.map(ULID::of)
				.orElse(null);
	}
}
