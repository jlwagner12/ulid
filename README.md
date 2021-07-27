# Universally Unique, Lexicographically Sortable Identifier

A complete binary implementation of the [ULID specification](https://github.com/ulid/spec)

[![Project status](https://img.shields.io/github/release/jlwagner12/ulid.svg)](https://github.com/jlwagner12/ulid/releases/latest)
[![license](https://img.shields.io/github/license/jlwagner12/ulid.svg)](https://github.com/jlwagner12/ulid/releases/latest)

[![Conventional Commits](https://camo.githubusercontent.com/74420c84a8cdfb2ed88abc162cde2dd9fc6dcf14f2ee0ec0779eb2cca98836e9/68747470733a2f2f696d672e736869656c64732e696f2f62616467652f436f6e76656e74696f6e616c253230436f6d6d6974732d312e302e302d79656c6c6f772e737667)](https://conventionalcommits.org/)
[![Semantic Versioning](https://img.shields.io/badge/semver-2.0.0-green)](https://semver.org/)
[![Semantic Release](https://img.shields.io/badge/%20%20%F0%9F%93%A6%F0%9F%9A%80-semantic--release-e10079.svg)](https://github.com/semantic-release/semantic-release)

[![Sonarcloud Status](https://sonarcloud.io/api/project_badges/measure?project=jlwagner12_ulid&metric=alert_status)](https://sonarcloud.io/dashboard?id=jlwagner12_ulid)
[![SonarCloud Coverage](https://sonarcloud.io/api/project_badges/measure?project=jlwagner12_ulid&metric=coverage)](https://sonarcloud.io/component_measures/metric/coverage/list?id=jlwagner12_ulid)
[![SonarCloud Bugs](https://sonarcloud.io/api/project_badges/measure?project=jlwagner12_ulid&metric=bugs)](https://sonarcloud.io/component_measures/metric/reliability_rating/list?id=jlwagner12_ulid)
[![SonarCloud Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=jlwagner12_ulid&metric=vulnerabilities)](https://sonarcloud.io/component_measures/metric/security_rating/list?id=jlwagner12_ulid)

# Usage

To create a new identifier, you use `ULID.nextULID()`. To convert a `String`,
`byte[]`, or `long[]`, you use `ULID.of(value)`.

## ORM Integration

The ULID implementation can be integrated for use with either Hibernate or 
Ebeans with minimal configuration. Just use the appropriate annotations for your
platform, and the rest should be taken care of for you.

The library handles storing the ULID values in a database as either a
26-character String (e.g. `CHAR(26)`, `VARCHAR(26)`, etc.), an array of 16
8-bit bytes, an array of two 64-bit longs, or a `UUID`.

For either of the ORM solutions supported, you must make certain that the
`org.jlw.ulid` package and classes are mapped appropriately.

### Hibernate/Spring

```java
@Entity
public class Client
{
    @Id
    @GenericGenerator(name = UlidGenerator.GENERATOR_NAME, strategy = "org.jlw.ulid.UlidGenerator")
    @GeneratedValue(generator = UlidGenerator.GENERATOR_NAME)  
    @Column(name="id")
    private ULID id;
}
```

### Ebean ORM

```java
@Entity
public class Client
{
    @Id
    @GeneratedValue(generator = UlidGenerator.GENERATOR_NAME)  
    @Column(name="id")
    private ULID id;
}
```

# Implementation

Internally, the ID is represented as two 64-bit `long` values to create the
128-bit ULID.

## Encoding

Crockford's Base32 is used as shown. This alphabet excludes the letters I, L,
O, and U to avoid confusion and abuse. The updated version of Crockford's Base32
specification allows treating i, I, l, and L as a one digit, o and O as the
zero digit. This implementation does not honor this convention as it seems to
violate the original intent of the ULID specification. The implementation also
does not allow the hyphens or check-digits allowed in Crockford's Base32
specification.

However, it does allow lower-case alphabet letters, but lower-case
letters will not be preserved. The following is an example of a "round-trip" of
a string value with lower-case letters. The `toString` method will always produce
only upper-case characters.

```java
assert ULID.of("01f99hjbj9a3s5pdm9qpam4mgm").toString().equals("01F99HJBJ9A3S5PDM9QPAM4MGM") == true; 
```

This implementation is intended solely to deliver a high-performance,
distributed identifier creation mechanism specifically for use in SQL and NoSQL
(and distributed systems in general) -- not an all-in-one solution for all
conceivable use-cases and handling. Data integrity and transmission are left to
clients of the library.

## Monotonicity

This implementation honors the ULID specification's constraint upon creating
multiple ULIDs within the same millisecond. After creating an initial ULID, all
subsequent ULIDs created within the same millisecond within the same JVM will be
monotonic. Quite simply, this means each ULID created will be the 128-bit value
of the previous ULID incremented by one.

The implementation is thread-safe and is handled using non-blocking techniques
for best performance.

## Binary Layout and Byte Order

The components are encoded as 16 octets. Each component is encoded with the Most
Significant Byte first (network byte order). The ULID implementation has methods
for handling ULID strings as well as arrays of `byte`s and `long`s.

```
0                   1                   2                   3
0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1 2 3 4 5 6 7 8 9 0 1
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
|                      32_bit_uint_time_high                    |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
|     16_bit_uint_time_low      |       16_bit_uint_random      |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
|                       32_bit_uint_random                      |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
|                       32_bit_uint_random                      |
+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+
```
