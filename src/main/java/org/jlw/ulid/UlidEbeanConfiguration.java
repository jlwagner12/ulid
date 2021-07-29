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
 */
package org.jlw.ulid;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.ebean.config.AutoConfigure;
import io.ebean.config.DatabaseConfig;
import io.ebeaninternal.server.core.bootup.BootupClassPathSearch;
import io.ebeaninternal.server.core.bootup.BootupClasses;

public class UlidEbeanConfiguration implements AutoConfigure
{
	public static final String PROPERTY_NAME = "ebean.ulid.type";

	private static final Logger logger = LoggerFactory.getLogger(UlidEbeanConfiguration.class);

	private static String databaseType;

	public static void setDatabaseType(final EbeanUlidType value)
	{
		setDatabaseType(value.name());
	}

	public static void setDatabaseType(final String value)
	{
		databaseType = value;
	}

	@Override
	public void preConfigure(final DatabaseConfig config)
	{
		// intentionally left blank
		// there's nothing to do here until after we've gotten all of the
		// 		settings from the properties
	}

	@Override
	public void postConfigure(final DatabaseConfig config)
	{
		cleanClasses(config, getBootupClasses(config));
		config.add(new UlidGenerator());
		config.addClass(ULID.class);

		final EbeanUlidType type = getDatabaseType(config);

		switch (type)
		{
			case BINARY:
				config.addClass(UlidBinaryAttributeConverter.class);
				break;

			case UUID:
				config.addClass(UlidUuidAttributeConverter.class);
				break;

			case EMBEDDED:
				break;

			default:
			case STRING:
				config.addClass(UlidStringAttributeConverter.class);
				break;
		} // switch

		logger.debug("default ULID database type set to {}", type);
	}

	EbeanUlidType getDatabaseType(final DatabaseConfig config)
	{
		return Optional.ofNullable(config.getProperties())
				.map(p -> p.getProperty(PROPERTY_NAME))
				.filter(v -> v.length() > 0)
				.filter(Predicate.not(v -> v.matches("^\\s+$")))
				.map(EbeanUlidType::valueOf)
				.orElseGet(() -> Optional.ofNullable(databaseType)
						.filter(v -> v.length() > 0)
						.filter(Predicate.not(v -> v.matches("^\\s+$")))
						.map(EbeanUlidType::valueOf)
						.orElse(EbeanUlidType.STRING));
	}

	private void cleanClasses(final DatabaseConfig config, final BootupClasses classes)
	{
		final Set<Class<?>> library = Stream.of(ULID.class,
				UlidBinaryAttributeConverter.class,
				UlidEbeanConfiguration.class,
				UlidGenerator.class,
				UlidStringAttributeConverter.class,
				UlidUuidAttributeConverter.class)
				.collect(Collectors.toSet());

		config.setPackages(Collections.emptyList());
		Stream.of(classes.getAttributeConverters(),
				classes.getEmbeddables(),
				classes.getEntities(),
				classes.getScalarConverters(),
				classes.getScalarTypes())
				.flatMap(List::stream)
				.filter(Predicate.not(library::contains))
				.forEach(config::addClass);
	}

	private BootupClasses getBootupClasses(final DatabaseConfig config)
	{
		final List<Class<?>> entityClasses = config.getClasses();

		if (config.isDisableClasspathSearch()
				|| Optional.ofNullable(entityClasses)
				.filter(Predicate.not(List::isEmpty))
				.isPresent())
		{
			return staticClasses(entityClasses);
		} // if

		return searchClasses(config);
	}

	BootupClasses staticClasses(final List<Class<?>> entityClasses)
	{
		return new BootupClasses(entityClasses);
	}

	BootupClasses searchClasses(final DatabaseConfig config)
	{
		return BootupClassPathSearch.search(config);
	}
}
