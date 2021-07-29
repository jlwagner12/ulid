package org.jlw.ulid

import spock.lang.Specification


class JpaUlidGeneratorSpec extends Specification {
    def "hibernate next value"() {
        expect:
        new JpaUlidGenerator().generate(null, null) != null
    }

    def "supports batch inserts"() {
        expect:
        new JpaUlidGenerator().supportsJdbcBatchInserts()
    }
}
