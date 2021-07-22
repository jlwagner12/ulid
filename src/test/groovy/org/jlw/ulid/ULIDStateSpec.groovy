package org.jlw.ulid

import spock.lang.Specification


class ULIDStateSpec extends Specification {
    def "same millisecond"() {
        when:
        var entropy = ULID.ULIDState.createEntropy()
        var state1 = new ULID.ULIDState(0, entropy)
        var state2 = ULID.ULIDState.nextValue(state1, 0)

        then:
        new BigInteger(state2.createULID().entropy()) == new BigInteger(state1.createULID().entropy()).add(BigInteger.ONE)
    }

    def "same millisecond w/ lsb overflow"() {
        when:
        var state1 = new ULID.ULIDState(0, 0, -1)
        var state2 = ULID.ULIDState.nextValue(state1, 0)

        then:
        state2.lsb == state1.lsb + 1
        state2.msb == state1.msb
        state2.timestamp == state1.timestamp
    }

    def "msb overflow"() {
        when:
        var state1 = new ULID.ULIDState(0, 0xffff, -1)

        ULID.ULIDState.nextValue(state1, 0)

        then:
        thrown(IllegalStateException.class)
    }
}
