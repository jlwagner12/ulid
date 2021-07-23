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

import java.security.SecureRandom;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

/**
 * An implementation of the <a href="https://github.com/ulid/spec">ULID</a> specification.
 *
 * @author jwagner
 * @implSpec This class is immutable and thread-safe.
 */
public final class ULID implements Comparable<ULID>
{
	private static final int ULID_LENGTH = 26;

	private static final long MAX_TIME = 0xffff_ffff_ffffL;

	private static final char[] BASE32_CHAR_LUT = new char[] {
			0x30, 0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37,
			0x38, 0x39, 0x41, 0x42, 0x43, 0x44, 0x45, 0x46,
			0x47, 0x48, 0x4a, 0x4b, 0x4d, 0x4e, 0x50, 0x51,
			0x52, 0x53, 0x54, 0x56, 0x57, 0x58, 0x59, 0x5a };

	private static final byte[] BASE32_BYTE_LUT = new byte[] {
			(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
			(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
			(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
			(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
			(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
			(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
			(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
			(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
			(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
			(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
			(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
			(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
			(byte) 0x00, (byte) 0x01, (byte) 0x02, (byte) 0x03,
			(byte) 0x04, (byte) 0x05, (byte) 0x06, (byte) 0x07,
			(byte) 0x08, (byte) 0x09, (byte) 0xff, (byte) 0xff,
			(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
			(byte) 0xff, (byte) 0x0a, (byte) 0x0b, (byte) 0x0c,
			(byte) 0x0d, (byte) 0x0e, (byte) 0x0f, (byte) 0x10,
			(byte) 0x11, (byte) 0xff, (byte) 0x12, (byte) 0x13,
			(byte) 0xff, (byte) 0x14, (byte) 0x15, (byte) 0xff,
			(byte) 0x16, (byte) 0x17, (byte) 0x18, (byte) 0x19,
			(byte) 0x1a, (byte) 0xff, (byte) 0x1b, (byte) 0x1c,
			(byte) 0x1d, (byte) 0x1e, (byte) 0x1f, (byte) 0xff,
			(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
			(byte) 0xff, (byte) 0x0a, (byte) 0x0b, (byte) 0x0c,
			(byte) 0x0d, (byte) 0x0e, (byte) 0x0f, (byte) 0x10,
			(byte) 0x11, (byte) 0xff, (byte) 0x12, (byte) 0x13,
			(byte) 0xff, (byte) 0x14, (byte) 0x15, (byte) 0xff,
			(byte) 0x16, (byte) 0x17, (byte) 0x18, (byte) 0x19,
			(byte) 0x1a, (byte) 0xff, (byte) 0x1b, (byte) 0x1c,
			(byte) 0x1d, (byte) 0x1e, (byte) 0x1f, (byte) 0xff,
			(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
			(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
			(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
			(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
			(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
			(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
			(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
			(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
			(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
			(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
			(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
			(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
			(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
			(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
			(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
			(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
			(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
			(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
			(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
			(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
			(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
			(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
			(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
			(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
			(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
			(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
			(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
			(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
			(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
			(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
			(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
			(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff,
			(byte) 0xff, (byte) 0xff, (byte) 0xff, (byte) 0xff
	};

	private static final AtomicReference<ULIDState> state = new AtomicReference<>(new ULIDState());

	private final long msb;

	private final long lsb;

	/**
	 * Constructs a new {@code ULID} using the specified data.  {@code mostSigBits} is used for the most significant 64
	 * bits of the {@code ULID} and {@code leastSignificantBits} becomes the least significant 64 bits of the {@code
	 * ULID}.
	 *
	 * @param mostSignificantBits
	 * 		The most significant bits of the {@code ULID}
	 * @param leastSignificantBits
	 * 		The least significant bits of the {@code ULID}
	 */
	public ULID(final long mostSignificantBits, final long leastSignificantBits)
	{
		this.msb = mostSignificantBits;
		this.lsb = leastSignificantBits;
	}

	/**
	 * Static factory to retrieve a (pseudo randomly generated) {@code ULID}.
	 * <p>
	 * The {@code ULID} is generated using a cryptographically strong pseudo random number generator.
	 *
	 * @return A randomly generated {@code ULID}.
	 * @throws IllegalStateException
	 * 		if the internal entropy overflows, after ~2^40 calls within the same millisecond.
	 */
	public static ULID nextULID()
	{
		return ULID.state.updateAndGet(ULIDState::nextValue).createULID();
	}

	/**
	 * Creates a {@code ULID} from the string standard representation as described in the {@link #toString} method.
	 *
	 * @param value
	 * 		specifying a {@code ULID}
	 * @return A {@code ULID} with the specified value.
	 * @throws IllegalArgumentException
	 * 		If {@code value} does not conform to the string representation as described in {@link #toString}.
	 */
	public static ULID of(final CharSequence value)
	{
		if (!isValid(value))
		{
			throw new IllegalArgumentException("invalid ULID: " + value);
		} // if

		return new ULID(getMostSignificantBits(value), getLeastSignificantBits(value));
	}

	public static ULID of(final byte[] bytes)
	{
		return of(bytes, 0);
	}

	public static ULID of(final byte[] bytes, final int offset)
	{
		if ((Objects.requireNonNull(bytes).length - offset) < 16)
		{
			throw new IllegalArgumentException("byte array must have at least 16 elements");
		} // if

		final long msb = ((bytes[0] & 0xffL) << 56)
				| ((bytes[1] & 0xffL) << 48)
				| ((bytes[2] & 0xffL) << 40)
				| ((bytes[3] & 0xffL) << 32)
				| ((bytes[4] & 0xffL) << 24)
				| ((bytes[5] & 0xffL) << 16)
				| ((bytes[6] & 0xffL) << 8)
				| (bytes[7] & 0xffL);
		final long lsb = ((bytes[8] & 0xffL) << 56)
				| ((bytes[9] & 0xffL) << 48)
				| ((bytes[10] & 0xffL) << 40)
				| ((bytes[11] & 0xffL) << 32)
				| ((bytes[12] & 0xffL) << 24)
				| ((bytes[13] & 0xffL) << 16)
				| ((bytes[14] & 0xffL) << 8)
				| (bytes[15] & 0xffL);

		return new ULID(msb, lsb);
	}

	public static ULID of(final long[] longs)
	{
		return of(longs, 0);
	}

	public static ULID of(final long[] longs, final int offset)
	{
		if ((Objects.requireNonNull(longs).length - offset) < 2)
		{
			throw new IllegalArgumentException("long array must have at least two elements");
		} // if

		return new ULID(longs[0], longs[1]);
	}

	/**
	 * Tests if the given {@code CharSequence} is a valid representation as described in the {@link #toString} method.
	 *
	 * @param ulid
	 * 		sequence to test
	 * @return {@code true} if the parameter is a valid representation of a {@code ULID}; otherwise, {@code false}.
	 */
	public static boolean isValid(final CharSequence ulid)
	{
		return Optional.ofNullable(ulid)
				.filter(v -> v.length() == ULID_LENGTH)
				.map(v -> v.chars().noneMatch(n -> n >= BASE32_BYTE_LUT.length || BASE32_BYTE_LUT[n] == (byte) 0xff))
				.map(t -> t && BASE32_BYTE_LUT[ulid.charAt(0)] < 8)
				.orElse(false);
	}

	private static long getMostSignificantBits(final CharSequence ulid)
	{
		return (long) BASE32_BYTE_LUT[ulid.charAt(0)] << 61
				| (long) BASE32_BYTE_LUT[ulid.charAt(1)] << 56
				| (long) BASE32_BYTE_LUT[ulid.charAt(2)] << 51
				| (long) BASE32_BYTE_LUT[ulid.charAt(3)] << 46
				| (long) BASE32_BYTE_LUT[ulid.charAt(4)] << 41
				| (long) BASE32_BYTE_LUT[ulid.charAt(5)] << 36
				| (long) BASE32_BYTE_LUT[ulid.charAt(6)] << 31
				| (long) BASE32_BYTE_LUT[ulid.charAt(7)] << 26
				| (long) BASE32_BYTE_LUT[ulid.charAt(8)] << 21
				| (long) BASE32_BYTE_LUT[ulid.charAt(9)] << 16
				| (long) BASE32_BYTE_LUT[ulid.charAt(10)] << 11
				| (long) BASE32_BYTE_LUT[ulid.charAt(11)] << 6
				| (long) BASE32_BYTE_LUT[ulid.charAt(12)] << 1
				| (long) (BASE32_BYTE_LUT[ulid.charAt(13)] >> 4) & 1;
	}

	private static long getLeastSignificantBits(final CharSequence ulid)
	{
		return (long) BASE32_BYTE_LUT[ulid.charAt(13)] << 60
				| (long) BASE32_BYTE_LUT[ulid.charAt(14)] << 55
				| (long) BASE32_BYTE_LUT[ulid.charAt(15)] << 50
				| (long) BASE32_BYTE_LUT[ulid.charAt(16)] << 45
				| (long) BASE32_BYTE_LUT[ulid.charAt(17)] << 40
				| (long) BASE32_BYTE_LUT[ulid.charAt(18)] << 35
				| (long) BASE32_BYTE_LUT[ulid.charAt(19)] << 30
				| (long) BASE32_BYTE_LUT[ulid.charAt(20)] << 25
				| (long) BASE32_BYTE_LUT[ulid.charAt(21)] << 20
				| (long) BASE32_BYTE_LUT[ulid.charAt(22)] << 15
				| (long) BASE32_BYTE_LUT[ulid.charAt(23)] << 10
				| (long) BASE32_BYTE_LUT[ulid.charAt(24)] << 5
				| BASE32_BYTE_LUT[ulid.charAt(25)];
	}

	/**
	 * Returns the least significant 64 bits of this ULID's 128 bit value.
	 *
	 * @return The least significant 64 bits of this ULID's 128 bit value.
	 */
	public long getLeastSignificantBits()
	{
		return lsb;
	}

	/**
	 * Returns the most significant 64 bits of this ULID's 128 bit value.
	 *
	 * @return The most significant 64 bits of this ULID's 128 bit value.
	 */
	public long getMostSignificantBits()
	{
		return msb;
	}

	/**
	 * Retrieve the timestamp associated with this {@code ULID}.
	 *
	 * <p> The 48-bit timestamp value is constructed from the 32_bit_uint_time_high and 16_bit_uint_time_low fields of
	 * this {@code ULID}.  The resulting timestamp is measured in milliseconds since the UNIX epoch.
	 *
	 * @return The timestamp of this {@code ULID}.
	 */
	public long timestamp()
	{
		return (msb >> 16) & MAX_TIME;
	}

	/**
	 * Retrieve the {@link #timestamp()} associated with this {@code ULID} as a {@link LocalDateTime} with a time-zone
	 * of {@link ZoneOffset#UTC}.
	 *
	 * @return The timestamp of this {@code ULID} as a {@link LocalDateTime}.
	 * @see #timestamp()
	 */
	public LocalDateTime getLocalDateTimeStamp()
	{
		final Duration duration = Duration.ofMillis(timestamp());
		final long seconds = duration.getSeconds();
		final int nanos = duration.toNanosPart();

		return LocalDateTime.ofEpochSecond(seconds, nanos, ZoneOffset.UTC);
	}

	public byte[] array()
	{
		final byte[] bytes = new byte[16];

		bytes[0] = (byte) ((msb >>> 56) & 0xff);
		bytes[1] = (byte) ((msb >>> 48) & 0xff);
		bytes[2] = (byte) ((msb >>> 40) & 0xff);
		bytes[3] = (byte) ((msb >>> 32) & 0xff);
		bytes[4] = (byte) ((msb >>> 24) & 0xff);
		bytes[5] = (byte) ((msb >>> 16) & 0xff);
		bytes[6] = (byte) ((msb >>> 8) & 0xff);
		bytes[7] = (byte) (msb & 0xff);

		bytes[8] = (byte) ((lsb >>> 56) & 0xff);
		bytes[9] = (byte) ((lsb >>> 48) & 0xff);
		bytes[10] = (byte) ((lsb >>> 40) & 0xff);
		bytes[11] = (byte) ((lsb >>> 32) & 0xff);
		bytes[12] = (byte) ((lsb >>> 24) & 0xff);
		bytes[13] = (byte) ((lsb >>> 16) & 0xff);
		bytes[14] = (byte) ((lsb >>> 8) & 0xff);
		bytes[15] = (byte) (lsb & 0xff);

		return bytes;
	}

	public long[] longArray()
	{
		return new long[] { msb, lsb };
	}

	/**
	 * The entropy value associated with this {@code ULID}.
	 *
	 * <p> The 80-bit entropy value is constructed from the 16_bit_uint_random, 32_bit_uint_random_high, and
	 * 32_bit_uint_random_low fields of this {@code ULID}.
	 *
	 * @return The entropy, or random value, of this {@code ULID}.
	 */
	public byte[] entropy()
	{
		final byte[] entropy = new byte[10];

		entropy[0] = (byte) ((msb >>> 8) & 0xff);
		entropy[1] = (byte) (msb & 0xff);
		entropy[2] = (byte) ((lsb >>> 56) & 0xff);
		entropy[3] = (byte) ((lsb >>> 48) & 0xff);
		entropy[4] = (byte) ((lsb >>> 40) & 0xff);
		entropy[5] = (byte) ((lsb >>> 32) & 0xff);
		entropy[6] = (byte) ((lsb >>> 24) & 0xff);
		entropy[7] = (byte) ((lsb >>> 16) & 0xff);
		entropy[8] = (byte) ((lsb >>> 8) & 0xff);
		entropy[9] = (byte) (lsb & 0xff);

		return entropy;
	}

	/**
	 * Returns a {@link String} object representing this {@code ULID}. The string representation is a conversion from
	 * the internal, binary representation to <a href="https://www.crockford.com/base32.html">Crockford's Base32
	 * encoding</a>. This alphabet excludes the letters I, L, O, and U to avoid confusion and abuse.
	 *
	 * <p>The maximum string
	 * representation is {@code 7ZZZZZZZZZZZZZZZZZZZZZZZZZ} since a 26-character string can represent 130-bits, while
	 * the {@code ULID} specification only allows 128-bits.</p>
	 *
	 * <blockquote><pre>
	 * {@code
	 *  01AN4Z07BY      79KA1307SR9X4MV3
	 * |----------|    |----------------|
	 *  Timestamp           Entropy
	 *   48-bits            80-bits
	 *   10-chars           16-chars
	 *
	 * }</pre></blockquote>
	 *
	 * @return A string representation of this {@code ULID}
	 */
	public String toString()
	{
		final char[] chars = new char[ULID_LENGTH];

		// time
		chars[0] = BASE32_CHAR_LUT[((byte) (msb >>> 61)) & 0x1f];
		chars[1] = BASE32_CHAR_LUT[((byte) (msb >>> 56)) & 0x1f];
		chars[2] = BASE32_CHAR_LUT[((byte) (msb >>> 51)) & 0x1f];
		chars[3] = BASE32_CHAR_LUT[((byte) (msb >>> 46)) & 0x1f];
		chars[4] = BASE32_CHAR_LUT[((byte) (msb >>> 41)) & 0x1f];
		chars[5] = BASE32_CHAR_LUT[((byte) (msb >>> 36)) & 0x1f];
		chars[6] = BASE32_CHAR_LUT[((byte) (msb >>> 31)) & 0x1f];
		chars[7] = BASE32_CHAR_LUT[((byte) (msb >>> 26)) & 0x1f];
		chars[8] = BASE32_CHAR_LUT[((byte) (msb >>> 21)) & 0x1f];
		chars[9] = BASE32_CHAR_LUT[((byte) (msb >>> 16)) & 0x1f];

		// entropy
		chars[10] = BASE32_CHAR_LUT[((byte) (msb >>> 11)) & 0x1f];
		chars[11] = BASE32_CHAR_LUT[((byte) (msb >>> 6)) & 0x1f];
		chars[12] = BASE32_CHAR_LUT[((byte) (msb >>> 1)) & 0x1f];
		chars[13] = BASE32_CHAR_LUT[(((byte) msb << 4) & 0x1f) | (((byte) (lsb >>> 60)) & 0x0f)];
		chars[14] = BASE32_CHAR_LUT[((byte) (lsb >>> 55)) & 0x1f];
		chars[15] = BASE32_CHAR_LUT[((byte) (lsb >>> 50)) & 0x1f];
		chars[16] = BASE32_CHAR_LUT[((byte) (lsb >>> 45)) & 0x1f];
		chars[17] = BASE32_CHAR_LUT[((byte) (lsb >>> 40)) & 0x1f];
		chars[18] = BASE32_CHAR_LUT[((byte) (lsb >>> 35)) & 0x1f];
		chars[19] = BASE32_CHAR_LUT[((byte) (lsb >>> 30)) & 0x1f];
		chars[20] = BASE32_CHAR_LUT[((byte) (lsb >>> 25)) & 0x1f];
		chars[21] = BASE32_CHAR_LUT[((byte) (lsb >>> 20)) & 0x1f];
		chars[22] = BASE32_CHAR_LUT[((byte) (lsb >>> 15)) & 0x1f];
		chars[23] = BASE32_CHAR_LUT[((byte) (lsb >>> 10)) & 0x1f];
		chars[24] = BASE32_CHAR_LUT[((byte) (lsb >>> 5)) & 0x1f];
		chars[25] = BASE32_CHAR_LUT[(byte) lsb & 0x1f];

		return new String(chars);
	}

	/**
	 * Returns a hash code for this {@code ULID}.
	 *
	 * @return A hash code value for this {@code ULID}
	 */
	public int hashCode()
	{
		final long hilo = msb ^ lsb;
		return ((int) (hilo >> 32)) ^ (int) hilo;
	}

	/**
	 * Compares this object to the specified object.  The result is {@code true} if and only if the argument is not
	 * {@code null}, is a {@code ULID} object, and contains the same value, bit for bit, as this {@code ULID}.
	 *
	 * @param rhs
	 * 		The object to be compared
	 * @return {@code true} if the objects are the same; {@code false} otherwise.
	 */
	public boolean equals(final Object rhs)
	{
		return Optional.ofNullable(rhs)
				.filter(ULID.class::isInstance)
				.map(ULID.class::cast)
				.map(id -> msb == id.msb && lsb == id.lsb)
				.orElse(false);
	}

	/**
	 * Compares this {@code ULID} with the specified {@code ULID}.
	 *
	 * <p> The first of two {@code ULID}s is greater than the second if the most
	 * significant field in which the {@code ULID}s differ is greater for the first {@code ULID}.
	 *
	 * @param rhs
	 *        {@code ULID} to which this {@code ULID} is to be compared
	 * @return {@code -1}, {@code 0} or {@code 1} as this {@code ULID} is less than, equal to, or greater than {@code
	 * 		rhs}, respectively.
	 * @throws NullPointerException
	 * 		if the specified object is null
	 */
	public int compareTo(final ULID rhs)
	{
		int r = Long.compareUnsigned(msb, Objects.requireNonNull(rhs).msb);

		if (r == 0)
		{
			return Long.compareUnsigned(lsb, rhs.lsb);
		} // if

		return r;
	}

	private static class ULIDState
	{
		private static final SecureRandom numberGenerator = new SecureRandom();

		private static final long ENTROPY_MSB_MASK = 0xffff;

		final long timestamp;

		final long msb;

		final long lsb;

		private ULIDState()
		{
			this(System.currentTimeMillis() & MAX_TIME, createEntropy());
		}

		private ULIDState(final long timestamp, final byte[] entropy)
		{
			this(timestamp, getMostSignificantBitsFromEntropy(entropy), getLeastSignificantBitsFromEntropy(entropy));
		}

		private ULIDState(final long timestamp, final long msb, final long lsb)
		{
			this.timestamp = timestamp;
			this.msb = msb;
			this.lsb = lsb;
		}

		private static ULIDState nextValue(final ULIDState previous)
		{
			return nextValue(previous, System.currentTimeMillis() & MAX_TIME);
		}

		private static ULIDState nextValue(final ULIDState previous, final long timestamp)
		{
			if (previous.timestamp != timestamp)
			{
				return new ULIDState(timestamp, createEntropy());
			}
			else
			{
				final long lsb = previous.lsb + 1;

				if (lsb == 0)
				{
					// if lsb is zero, then we have added one and wrapped the
					// limits of a long, so we need to increment msb
					final long msb = previous.msb + 1;

					if (msb > ENTROPY_MSB_MASK)
					{
						// if more than 16 bits are present, then we have
						// exceeded our limit and need to wrap.
						// however, the ULID spec says this is an error, so we
						// report it as such

						// this shouldn't ever happen... only if more than
						// ~2^40, on average, random ULIDs are generated within
						// the same millisecond
						throw new IllegalStateException(
								"overflow; too many random ULIDs generated within the same millisecond");
					} // if
				} // if

				return new ULIDState(timestamp, previous.msb, lsb);
			} // if
		}

		static byte[] createEntropy()
		{
			final byte[] entropy = new byte[10];

			numberGenerator.nextBytes(entropy);
			return entropy;
		}

		private static long getMostSignificantBitsFromEntropy(final byte[] entropy)
		{
			return ((entropy[0] & 0xffL) << 8)
					| (entropy[1] & 0xffL);
		}

		private static long getLeastSignificantBitsFromEntropy(final byte[] entropy)
		{
			return ((entropy[2] & 0xffL) << 56)
					| ((entropy[3] & 0xffL) << 48)
					| ((entropy[4] & 0xffL) << 40)
					| ((entropy[5] & 0xffL) << 32)
					| ((entropy[6] & 0xffL) << 24)
					| ((entropy[7] & 0xffL) << 16)
					| ((entropy[8] & 0xffL) << 8)
					| (entropy[9] & 0xffL);
		}

		private ULID createULID()
		{
			return new ULID((timestamp << 16) | msb, lsb);
		}
	}
}
