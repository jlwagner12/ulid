package org.jlw.ulid

import spock.lang.Specification


class UlidGeneratorSpec extends Specification {
    def "correct name"() {
        expect:
        new EbeanUlidGenerator().getName() == EbeanUlidGenerator.GENERATOR_NAME
    }

    def "ebean next value"() {
        expect:
        new EbeanUlidGenerator().nextValue() != null
    }
}
