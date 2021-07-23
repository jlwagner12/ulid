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
import java.util.function.Predicate;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 * JPA {@link AttributeConverter} for {@link ULID} values.
 *
 * @author jwagner
 * @implSpec This class is immutable and thread-safe.
 */
@Converter
public class UlidAttributeConverter implements AttributeConverter<ULID, String>
{
	/**
	 * Converts a {@link ULID} to a string as per the <a href="https://github.com/ulid/spec">ULID specification</a> for
	 * storage in a database column. Database columns can be defined as any string type (e.g. CHAR, VARCHAR, etc.). The
	 * produced string will always be 26 characters long.
	 *
	 * @param attribute
	 * 		to convert from a {@link ULID} to a {@code String}.
	 * @return The supplied {@link ULID} converted to a {@code String} if {@code attribute} is not {@code null};
	 * 		otherwise, {@code null}.
	 */
	@Override
	public String convertToDatabaseColumn(final ULID attribute)
	{
		return Optional.ofNullable(attribute)
				.map(ULID::toString)
				.orElse(null);
	}

	/**
	 * Converts a string as per the <a href="https://github.com/ulid/spec">ULID specification</a> to a {@link ULID}.
	 *
	 * @param dbData
	 * 		a 26-character, Base32 string to convert to a {@link ULID}.
	 * @return The string converted to a {@link ULID} if {@code dbData} is not {@code null}; otherwise, {@code null}.
	 * @throws IllegalArgumentException
	 * 		if {@code dbData} is not {@code null} and does not represent a correct {@link ULID} formatted string.
	 */
	@Override
	public ULID convertToEntityAttribute(final String dbData)
	{
		final Optional<String> data = Optional.ofNullable(dbData);

		data.filter(Predicate.not(ULID::isValid))
				.ifPresent(v -> {
					throw new IllegalArgumentException("invalid ULID string");
				});

		return data.map(ULID::of).orElse(null);
	}
}
