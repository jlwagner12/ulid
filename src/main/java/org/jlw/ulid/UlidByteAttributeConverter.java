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
 * JPA {@link AttributeConverter} for {@link ULID} values stored in the database as a 16-byte array in network byte
 * order.
 *
 * @author jwagner
 * @implSpec This class is immutable and thread-safe.
 */
@Converter
public class UlidByteAttributeConverter implements AttributeConverter<ULID, byte[]>
{
	/**
	 * Converts a {@link ULID} to a 16-byte array for storage in a database column. The byte array must be at least 16
	 * bytes long and stored in network byte order.
	 *
	 * @param attribute
	 * 		to convert from a {@link ULID} to a {@code byte[]}.
	 * @return The supplied {@link ULID} converted to a {@code byte[]} if {@code attribute} is not {@code null};
	 * 		otherwise, {@code null}.
	 */
	@Override
	public byte[] convertToDatabaseColumn(final ULID attribute)
	{
		return Optional.ofNullable(attribute)
				.map(ULID::array)
				.orElse(null);
	}

	/**
	 * Converts a {@code byte[]} to a {@link ULID}.
	 *
	 * @param dbData
	 * 		a 16-byte array.
	 * @return The array converted to a {@link ULID} if {@code dbData} is not {@code null}; otherwise, {@code null}.
	 * @throws IllegalArgumentException
	 * 		if {@code dbData} is not {@code null} and does not represent a correct {@link ULID} formatted byte array.
	 */
	@Override
	public ULID convertToEntityAttribute(final byte[] dbData)
	{
		return Optional.ofNullable(dbData)
				.map(ULID::of)
				.orElse(null);
	}
}
