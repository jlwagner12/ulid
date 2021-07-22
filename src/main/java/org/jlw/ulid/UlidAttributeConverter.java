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
