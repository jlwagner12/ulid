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

# Key Features

- Full binary implementation
- ORM support
- Fully tested against
  - H2
  - Microsoft SQL Server
  - MySql
  - Oracle
  - PostgreSQL
  - Sqlite
- Support for database column types of
  - any string/text type
  - binary
  - native `UUID` types
  - embedded type using two 64-bit capable SQL columns
- Monotonic generation within the same JVM
- Thread-safe

# Contents

- [Key Features](#key-features)
- [Usage](#usage)
  - [ORM Integration](#orm-integration)
    - [Hibernate/Spring](#hibernatespring)
    - [Ebean ORM](#ebean-orm)
  - [ULID to SQL DDL Mapping](#ulid-to-sql-ddl-mapping)
- [Implementation](#implementation)
  - [Encoding](#encoding)
  - [Monotonicity](#monotonicity)
  - [Binary Layout and Byte Order](#binary-layout-and-byte-order)

# Usage

To create a new identifier, you use `ULID.nextULID()`. To convert a `String`,
`byte[]`, or `long[]`, you use `ULID.of(value)`.

## ORM Integration

The ULID implementation can be integrated for use with either Hibernate or 
Ebeans with minimal configuration. Just use the appropriate annotations for your
platform and a few minor settings, and the rest should be taken care of for you.

The library handles storing the ULID values in a database as either a
26-character String (e.g. `CHAR(26)`, `VARCHAR(26)`, etc.), an array of 16
8-bit bytes, a `UUID`, or as an embedded identifier with two `long` values.

For comprehensive examples, look to the test packages -- specifically,
/src/test/java/org/jlw/test/db/(ebean|hibernate).

### Hibernate/Spring

Coming soon...

### Ebean ORM

Ebean is not a comprehensive JPA implementation. Instead, it attempts to provide
a compromise between full JPA and ease of use. Regardless whether you feel it's
successful in it's attempts, it's a great, straight-forward platform to use.

It does have its idiosyncrasies, though. One of the major is the limit of regarding
column type conversions. When mapping custom Java types, such as ULID, to
databasse types, Ebeans limits clients to a
[single, database-wide mapping](https://github.com/ebean-orm/ebean/issues/1777).

This implementation allows you to set the mapping for your database when
[configuring the Database](https://ebean.io/docs/intro/configuration/). If you
choose to configure your database via properties files, you can set the
property `ebean.ulid.type` to one of `"BINARY"`, `"UUID"`, `"STRING"`, or
`"EMBEDDED"` with `"STRING"` being the default if none are specified.
This is accomplished via Ebean's
[AutoConfigure](https://ebean.io/apidoc/12/io/ebean/config/AutoConfigure.html)
SPI.

```java
DatabaseConfig databaseConfig = new DatabaseConfig();

// ...

databaseConfig.loadFromProperties();

Database db = DatabaseFactory.create(databaseConfig);
```

Configuring the mapping programmatically, you just construct a
`UlidEbeanConfiguration` instance and call `configure` passing in the `DatabaseConfig`
with either the `EbeanMappingType` enumeration value or the `String` equivalent. The
default constructor will create a `STRING` mapping.

```java
UlidEbeanConfiguration ulidConfig = new UlidEbeanConfiguration(); // default STRING mapping
// or
UlidEbeanConfiguration ulidConfig = new UlidEbeanConfiguration(EbeanMappingType.BINARY);
// or
UlidEbeanConfiguration ulidConfig = new UlidEbeanConfiguration("BINARY");

DatabaseConfig config = new DatabaseConfig();
// ...
ulidConfig.configure(config);
```

Using a ULID within an entity is straight-forward. You only need specify the
Ebean `IdGenerator` for all except the `EbeanMappingType.EMBEDDED` mapping.

```java
@Entity
public class Client
{
    @Id
    @GeneratedValue(generator = EbeanUlidGenerator.GENERATOR_NAME)  
    @Column(name = "id")
    private ULID id;

    // ...
}
```

For the `EbeanMappingType.EMBEDDED` mapping, a bit more work is involved.

```java
@Entity
public class Client
{
    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "msb", column = @Column(name = "MSB_COLUMN_NAME")),
            @AttributeOverride(name = "lsb", column = @Column(name = "LSB_COLUMN_NAME"))
    })
    private ULID id;

    // ...
}
```

## ULID to SQL DDL Mapping

| Database                 | ULID Mapping Type    | SQL Column Type                |
| ------------------------ | -------------------- | ------------------------------ |
|                          |                      |                                |
| **H2**                   | BINARY               | BINARY(16)                     |
|                          | EMBEDDED<sup>†</sup> | MSB BIGINT, LSB BIGINT         |
|                          | STRING               | CHAR(26)                       |
|                          | UUID                 | UUID                           |
|                          |                      |                                |
| **Microsoft SQL Server** | BINARY               | BINARY(16)                     |
|                          | EMBEDDED<sup>†</sup> | MSB BIGINT, LSB BIGINT         |
|                          | STRING               | CHAR(26)                       |
|                          | UUID                 | UNIQUEIDENTIFIER               |
|                          |                      |                                |
| **MySql**                | BINARY               | BINARY(16)                     |
|                          | EMBEDDED<sup>†</sup> | MSB BIGINT, LSB BIGINT         |
|                          | STRING               | CHAR(26)                       |
|                          | UUID<sup>‡</sup>     |                                |
|                          |                      |                                |
| **Oracle**               | BINARY               | RAW(16)                        |
|                          | EMBEDDED<sup>†</sup> | MSB NUMBER(20), LSB NUMBER(20) |
|                          | STRING               | CHAR(26)                       |
|                          | UUID<sup>‡</sup>     |                                |
|                          |                      |                                |
| **PostgreSQL**           | BINARY               | bytea                          |
|                          | EMBEDDED<sup>†</sup> | MSB bigint, LSB bigint         |
|                          | STRING               | char(26)                       |
|                          | UUID                 | uuid                           |
|                          |                      |                                |
| **Sqlite**               | BINARY               | BLOB                           |
|                          | EMBEDDED<sup>†</sup> | MSB BIGINT, LSB BIGINT         |
|                          | STRING               | CHAR(26)                       |
|                          | UUID                 | BLOB                           |

<sup>†</sup> This mapping type requires two columns. You'll need to make certain
to use the `@AttributeOverrides` and `@AttributeOverride` JPA annotations where
the name argument to the `@AttributeOverride` annotation is `msb` for the most
significant bits and `lsb` for the least significant bits. To maintain proper
sort order, the `msb` column should be defined before the `lsb` column in your
DDL.

<sup>‡</sup> The specified RDBMS does not have native support for the UUID type.

### DDL Examples

Using Microsoft SQL Server as your database, you might define a table such as the
following when mapping a ULID to `EBeanMappingType.BINARY`.

```roomsql
CREATE TABLE accounts
(
    account_id BINARY(16) PRIMARY KEY,
    -- ...
);
```

Likewise for Postgres, you might define a table such as the following when
mapping a ULID to `EBeanMappingType.EMBEDDED`.

```roomsql
CREATE TABLE accounts
(
    account_msb_id bigint NOT NULL,
    account_lsb_id bigint NOT NULL,
    -- ...
    CONSTRAINT accounts_pk PRIMARY KEY (account_msb_id, account_lsb_id)
);
```

With the corresponding Java class defined as the following.

```java
@Entity
public class Accounts
{
    @EmbeddedId
    @AttributeOverrides({
            @AttributeOverride(name = "msb", column = @Column(name = "account_msb_id")),
            @AttributeOverride(name = "lsb", column = @Column(name = "account_lsb_id"))
    })
    private ULID id;

    // ...
}
```

Using the default mapping or specifying `STRING`, a table chould be defined as the following.

```roomsql
CREATE TABLE accounts
(
    account_id CHAR(26) PRIMARY KEY,
    -- ...
);
```

The `STRING` mapping type can handle any text-based column type (e.g. `CHAR`,
`VARCHAR`, `TEXT`, etc.). However, for performance and storage considerations,
it's best to use the `CHAR` type with a length of 26 characters.

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
