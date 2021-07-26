package org.jlw.ulid

import spock.lang.Specification


class UlidGeneratorSpec extends Specification {
    def "correct name"() {
        expect:
        new UlidGenerator().getName() == UlidGenerator.GENERATOR_NAME
    }

    def "hibernate next value"() {
        expect:
        new UlidGenerator().generate(null, null) != null
    }

    def "supports batch inserts"() {
        expect:
        new UlidGenerator().supportsJdbcBatchInserts()
    }

    def "ebean next value"() {
        expect:
        new UlidGenerator().nextValue() != null
    }
}
