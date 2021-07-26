# Universally Unique, Lexicographically Sortable Identifier

[![Project status](https://img.shields.io/github/release/jlwagner12/ulid.svg)](https://github.com/jlwagner12/ulid/releases/latest)
[![license](https://img.shields.io/github/license/jlwagner12/ulid.svg)](https://github.com/jlwagner12/ulid/releases/latest)

[![Sonarcloud Status](https://sonarcloud.io/api/project_badges/measure?project=jlwagner12_ulid&metric=alert_status)](https://sonarcloud.io/dashboard?id=jlwagner12_ulid)
[![SonarCloud Coverage](https://sonarcloud.io/api/project_badges/measure?project=jlwagner12_ulid&metric=coverage)](https://sonarcloud.io/component_measures/metric/coverage/list?id=jlwagner12_ulid)
[![SonarCloud Bugs](https://sonarcloud.io/api/project_badges/measure?project=jlwagner12_ulid&metric=bugs)](https://sonarcloud.io/component_measures/metric/reliability_rating/list?id=jlwagner12_ulid)
[![SonarCloud Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=jlwagner12_ulid&metric=vulnerabilities)](https://sonarcloud.io/component_measures/metric/security_rating/list?id=jlwagner12_ulid)

# Implementation

This is a complete implementation of the [ULID specification](https://github.com/ulid/spec).
Internally, the ID is represented as two 64-bit, `long` values to create the
128-bit ULID.

## Encoding

Crockford's Base32 is used as shown. This alphabet excludes the letters I, L,
O, and U to avoid confusion and abuse. The updated version of Crockford's Base32
specification allows treating i, I, l, and L as a one digit, o and O as the
zero digit. This implementation does not honor this convention as it seems to
violate the original intent of the ULID specification. The implementation also
does not allow the hyphens or check-digits allowed in Crockford's Base32
specification.

This implementation is intended solely to deliver a high-performance,
distributed identifier creation mechanism specifically for use in RDMSs (and
distributed systems in general) -- not an all-in-one solution for all conceivable
use-cases and handling. Data integrity and transmission are left to clients of
the library.

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
