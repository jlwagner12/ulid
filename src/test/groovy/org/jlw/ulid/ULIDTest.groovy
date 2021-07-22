package org.jlw.ulid

import spock.lang.Specification

import java.time.LocalDateTime

class ULIDTest extends Specification {
    def "bad string #value"() {
        when:
        ULID.of(value)

        then:
        thrown(IllegalArgumentException.class)

        where:
        value << [
                null,
                '',
                '   ',
                '                          ',
        ]
    }

    def "bad cast"() {
        given:
        var ulid = ULID.randomULID()

        and:
        var value = new Long(5L)

        expect:
        !ulid.equals(value)
    }

    def "to local date"(String string, String value) {
        when:
        var id = ULID.of(string)
        var dt = LocalDateTime.parse(value)

        then:
        id.getLocalDateTimeStamp() == dt

        where:
        string                       | value
        '03E27EHMP27BFNGNB53KPY3V6V' | '2089-11-04T18:54:03.458'
        '037YR7R8VQ6T22S8Z565WNSNZH' | '2083-03-12T12:05:48.535'
        '025TGXC985EV6D1RHK023HP922' | '2046-01-11T18:08:37.125'
        '03JRNEZ3HVYEDDEB7HX4F1Q65M' | '2094-12-18T08:45:20.315'
        '03515SS2THSSV87K05YAK53R9K' | '2080-01-04T13:55:25.649'
        '038DEM36EF26ARZBW3C1J59KRY' | '2083-09-11T04:24:32.463'
        '02VNW4SAVS2V4P1B5K0CC86D12' | '2069-10-28T23:41:58.521'
        '03M993896YQTQ5JTE4XMNV2ME6' | '2096-08-13T12:18:54.302'
        '029Q18XH8CX5EZWY2ZFHJNQWYF' | '2050-04-08T03:04:33.804'
        '03GWH82WP61SYP2CXYN6P8T40T' | '2092-12-01T01:46:17.158'
        '01KBW5ZQEG36Q4951KDT6FPBPH' | '2025-12-07T10:33:43.632'
        '01MMGKSS51ZASWRQ048KGW2GNV' | '2027-04-26T11:27:50.433'
        '02X0FE82XMJDKCDR1YKPXX5ZD1' | '2071-04-11T10:32:09.908'
        '02265PX8RR9D9QJYFDD194WVQW' | '2042-01-26T19:23:03.320'
        '02R5HBZRAT5B98QP3X6RQWHR81' | '2066-01-01T21:58:05.914'
        '031S5NEYKY7S5JDRSMY02Z1ZYY' | '2076-06-21T01:27:29.662'
        '02YSDRXR1J0PG370VVKM4K0ACQ' | '2073-03-19T03:55:00.530'
        '03AT5GQ8FJ03HJQY2TY4JFJEZ3' | '2086-04-21T13:41:06.162'
        '02S4HVD779MB4Q0NGC4H1066C2' | '2067-01-22T08:34:12.329'
        '02XYAN42G3APD3D2S8Q3BDB88Z' | '2072-04-16T09:47:00.995'
        '03DZVA4TKCP81NNB0KMCQNQMR9' | '2089-10-06T05:14:52.396'
        '01FGKA8X0908TSPSRHG1TMJ9HC' | '2021-09-27T09:44:44.297'
        '02YC24S2TC6E5V082AYJDE48QX' | '2072-10-04T02:07:13.740'
        '026G40TC56V7MH01BMCZXSHGYA' | '2046-10-07T03:43:57.094'
        '01NE6XX3JDJXAC9WC91K6PVF2F' | '2028-03-10T20:00:08.269'
        '02E7MJC5Q7F2GC9PF88DPTJN5A' | '2055-03-10T04:35:47.559'
        '02D6YNRAMRTWTA9E887CCCV6ZR' | '2054-01-28T00:09:15.672'
        '02D1WMWW9J22841QGPEZGE5FBS' | '2053-11-26T01:57:18.258'
        '01T6EG6KG4RPE4CR3ZNPXQEWXG' | '2033-05-15T18:20:32.132'
        '01KR1YA7ZTVJZEN2YGMYQ03G4G' | '2026-05-07T19:23:28.122'
        '02BK1TDJWMZ5YCZN9Y3A2TEZFQ' | '2052-04-22T23:52:14.996'
        '01MM5ZMRXV0YJXR1K2WPXRY7R9' | '2027-04-22T08:23:10.523'
        '025JVS72DW5KTYJQCG502SW0AG' | '2045-10-08T13:21:56.156'
        '03GQ32BC576QFKK4AV9CS69M0D' | '2092-09-24T10:18:12.519'
        '02CMS6KBAVW8DVHDG45PCE4V7F' | '2053-06-16T04:25:38.907'
        '02NP7S804WXRXW9MHF9A5FJW5Q' | '2063-04-22T09:57:19.132'
        '02AT8XCPN0NA1G7KVHWBXJHNES' | '2051-06-20T01:26:27.232'
        '01HDQJJGTCJFA04VWDAMJ4JEEM' | '2023-10-27T03:24:17.100'
        '01NX8VJB28CTWPGGYG875WWFBW' | '2028-09-13T23:53:14.568'
        '028EA9W2B0PCEY1M20EE1ZRA67' | '2048-11-18T02:30:49.440'
        '03JHSY9A5GAKDT84279AMCNVZQ' | '2094-09-24T02:40:08.368'
        '03CAXZVQ6DSCXY25Q81MKWA58D' | '2087-12-18T14:20:54.861'
        '02PQK7ZRGASWN3SE7JQWBCVHVX' | '2064-06-09T23:24:36.746'
        '02TSNY04N7W507CP7P2GJ6HTSZ' | '2068-11-12T14:28:23.847'
        '02A87TMPAD0GNZSSJBEEFDGM72' | '2050-11-07T22:36:36.045'
        '01TGARM4ZHEPADEGDJWW5J6GVF' | '2033-09-15T14:07:45.137'
        '023SCG9KP4186V96JDTHJ3HVRB' | '2043-10-25T06:02:41.732'
        '02CNJGJHZWQDXADFSMBCTCE25B' | '2053-06-26T00:20:59.516'
        '02V53VDVG74KK2W4MX7F7T98CJ' | '2069-04-03T17:05:29.607'
        '023F7KGRNK4F4HRK4ADC6C5ZKQ' | '2043-06-21T01:45:51.539'
        '02JXSJFFFA243Q072RR4AS5VZC' | '2060-04-18T06:28:21.610'
        '03D3B26B89JGJMZXWHCSMN4CQ7' | '2088-10-16T22:28:31.625'
        '02MGVJQZ1FQ79ZJ0B5FH1GT8K8' | '2062-01-12T20:32:01.583'
        '021BRYYYANWC148CSBNC897G80' | '2041-03-04T17:45:31.733'
    }

    def "string round trip #iteration"() {
        expect:
        var ulid = ULID.randomULID()
        var str = ulid.toString()

        ulid == ULID.of(str)

        where:
        iteration << (1..10_000)
    }

    def "not equal to null"() {
        expect:
        !ULID.randomULID().equals(null)
    }

    def "hashCode #iteration"() {
        expect:
        ULID.randomULID().hashCode() != 0

        where:
        iteration << (1..10_000)
    }

    def "equality #iteration"() {
        expect:
        var ulid = ULID.randomULID()

        ulid.equals(ulid)

        where:
        iteration << (1..10_000)
    }

    def "less than"() {
        given:
        var ulidLeast = new ULID(0, 0)

        and:
        var ulidMost = new ULID(1, 1)

        expect:
        ulidLeast < ulidMost
    }

    def "greater than"() {
        given:
        var ulidLeast = new ULID(0, 0)

        and:
        var ulidMost = new ULID(1, 1)

        expect:
        ulidMost > ulidLeast
    }
}
