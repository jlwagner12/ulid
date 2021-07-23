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
		return ULID.nextULID();
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
