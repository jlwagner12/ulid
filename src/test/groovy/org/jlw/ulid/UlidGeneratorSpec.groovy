package org.jlw.ulid

import spock.lang.Specification


class UlidGeneratorSpec extends Specification {
    def "correct name"() {
        expect:
        new UlidGenerator().getName() == UlidGenerator.GENERATOR_NAME
    }

    def "next value"() {
        expect:
        new UlidGenerator().nextValue() != null
    }
}
