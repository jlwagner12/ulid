package org.jlw.ulid

import spock.lang.Specification

import java.time.LocalDateTime

class ULIDSpec extends Specification {
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
                '8ZZZZZZZZZZZZZZZZZZZZZZZZZ',
                '9ZZZZZZZZZZZZZZZZZZZZZZZZZ',
                'AZZZZZZZZZZZZZZZZZZZZZZZZZ',
                'BZZZZZZZZZZZZZZZZZZZZZZZZZ',
                'CZZZZZZZZZZZZZZZZZZZZZZZZZ',
                'DZZZZZZZZZZZZZZZZZZZZZZZZZ',
                'EZZZZZZZZZZZZZZZZZZZZZZZZZ',
                'FZZZZZZZZZZZZZZZZZZZZZZZZZ',
                'GZZZZZZZZZZZZZZZZZZZZZZZZZ',
                'HZZZZZZZZZZZZZZZZZZZZZZZZZ',
                'JZZZZZZZZZZZZZZZZZZZZZZZZZ',
                'KZZZZZZZZZZZZZZZZZZZZZZZZZ',
                'MZZZZZZZZZZZZZZZZZZZZZZZZZ',
                'NZZZZZZZZZZZZZZZZZZZZZZZZZ',
                'PZZZZZZZZZZZZZZZZZZZZZZZZZ',
                'QZZZZZZZZZZZZZZZZZZZZZZZZZ',
                'RZZZZZZZZZZZZZZZZZZZZZZZZZ',
                'SZZZZZZZZZZZZZZZZZZZZZZZZZ',
                'TZZZZZZZZZZZZZZZZZZZZZZZZZ',
                'VZZZZZZZZZZZZZZZZZZZZZZZZZ',
                'WZZZZZZZZZZZZZZZZZZZZZZZZZ',
                'XZZZZZZZZZZZZZZZZZZZZZZZZZ',
                'YZZZZZZZZZZZZZZZZZZZZZZZZZ',
                'ZZZZZZZZZZZZZZZZZZZZZZZZZZ',
                '01F99GM3AAFN3KR86D6JGPKXPI',
                '01F99GM3AJ5Q1G13PFWMR8WN3i',
                '01F99GM3AKF17ECK3WTXB6VMSL',
                '01F99GM3AKGWVZ3Y7GR5NWHVEl',
                '01F99GM3AKMQ5TPF65BD85CB3O',
                '01F99GM3AM41J2GN3DTVN5YAKo',
                '01F99GM3AMPYXJNMGPWP14J0KU',
                '01F99GM3AM1JN13NEAQ3ARG77u',
                '01f99gm3aafn3kr86d6jgpkxpi',
                '01f99gm3aj5q1g13pfwmr8wn3i',
                '01f99gm3akf17eck3wtxb6vmsl',
                '01f99gm3akgwvz3y7gr5nwhvel',
                '01f99gm3akmq5tpf65bd85cb3o',
                '01f99gm3am41j2gn3dtvn5yako',
                '01f99gm3ampyxjnmgpwp14j0ku',
                '01f99gm3am1jn13neaq3arg77u',
                '01f99gm3am1jn13n' + '\u0100' + 'aq3arg77u',
        ]
    }

    def "bad cast"() {
        given:
        var ulid = ULID.nextULID()

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
        var ulid = ULID.nextULID()
        var str = ulid.toString()

        ulid == ULID.of(str)

        where:
        iteration << (1..10_000)
    }

    def "not equal to null"() {
        expect:
        ULID.nextULID() != null
    }

    def "hashCode #iteration"() {
        expect:
        ULID.nextULID().hashCode() != 0

        where:
        iteration << (1..10_000)
    }

    def "equality #iteration"() {
        expect:
        var ulid = ULID.nextULID()

        ulid.compareTo(ulid) == 0
        ulid == ulid

        where:
        iteration << (1..1000)
    }

    def "equals branches"(final long msb1, final long lsb1, final long msb2, final long lsb2, final boolean result) {
        given:
        var ulid1 = new ULID(msb1, lsb1)

        and:
        var ulid2 = new ULID(msb2, lsb2)

        expect:
        if (result) {
            ulid1.equals(ulid2)
        }
        else {
            !ulid1.equals(ulid2)
        }

        where:
        msb1 | lsb1 | msb2 | lsb2 | result
        0    | 0    | 0    | 0    | true
        0    | 0    | 1    | 0    | false
        1    | 0    | 0    | 0    | false
        0    | 0    | 0    | 1    | false
        0    | 1    | 0    | 0    | false
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

    def "bits #value"() {
        when:
        var ulid = ULID.of(value)
        var target = new ULID(msb, lsb)

        then:
        ulid.getMostSignificantBits() == msb
        ulid.getLeastSignificantBits() == lsb
        ulid == target
        ulid.equals(target)
        ulid.compareTo(target) == 0

        where:
        value                        | msb                | lsb
        "01F99HJBJ384MW83WGSWVYE52N" | 106488908812075305 | -4319074297954495403
        "01F99HJBJ9A3S5PDM9QPAM4MGM" | 106488908812472562 | 6572592153539793428
        "01F99HJBJAZN26Z8VS8DAQQQX1" | 106488908812582212 | 8044406689517789089
        "01F99HJBJA7BM8K7D7PJGVZ849" | 106488908812532456 | -8530477724920209271
        "01F99HJBJA73MQQ397NA7KHRTE" | 106488908812531945 | 8902815651882132302
        "01F99HJBJAZ0X6JNX61B5442NC" | 106488908812580922 | 7590718262229469868
        "01F99HJBJAXXDCMZNT6BDBKAQV" | 106488908812578650 | -3855439503401833733
        "01F99HJBJA6ZMX65BZEE8XAQAJ" | 106488908812531689 | -3236540622851187374
        "01F99HJBJACF9SH0C6CEBHPXEH" | 106488908812542931 | -7457531745653787183
        "01F99HJBJARDDY4TDJW31M6R6D" | 106488908812567387 | -2131976270164827955
        "01F99HJBJAKVAD4NBZM6C2F1SH" | 106488908812558036 | -3290583620705089743
        "01F99HJBJBC41DZ9N469H4KZ09" | 106488908812607746 | -2330995220813317111
        "01F99HJBJB2ZPZT51XQZ117GKG" | 106488908812589037 | -210475388775447952
        "01F99HJBJB6XFFTAJXFG8BZJN5" | 106488908812597086 | -204248045653603675
        "01F99HJBJBN0DBSCHPGZDNRE2J" | 106488908812625946 | -4849753891595863982
        "01F99HJBJC3JFJ0TTBGQEK1GJ3" | 106488908812655775 | 2336043870035427907
        "01F99HJBJCBJT3ARC5HH16P441" | 106488908812672180 | 3846502394207342721
        "01F99HJBJC9XHJR5EF6YCEFXQ1" | 106488908812668771 | 3176672949875242721
    }

    def "monotonicity"() {
        given:
        var values = (1..10_000).collect { ULID.nextULID() }

        and:
        def counts = [:]
        var map = values.groupBy { it.timestamp() }
                .each { timestamp, list ->
                    counts[list.size()] = timestamp
                }

        and:
        var ulids = map[counts.max { entry -> entry.getKey() }.getValue()]

        expect:
        ulids == ulids.sort(false)
        map.forEach((timestamp, list) -> {
            list.sort(ULID::compareTo)

            BigInteger value = null

            for (final ULID id : list) {
                final BigInteger next = new BigInteger(id.entropy())

                if (value != null) {
                    value.add(BigInteger.ONE) == next
                } // if

                value = next
            } // for
        })
    }

    def "null comparison"() {
        when:
        ULID.nextULID().compareTo(null)

        then:
        thrown(NullPointerException.class)
    }

    def "bad long array"() {
        when:
        ULID.of([0] as long[])

        then:
        thrown(IllegalArgumentException.class)
    }

    def "bad long array offset"() {
        when:
        ULID.of([0, 0, 0] as long[], 2)

        then:
        thrown(IllegalArgumentException.class)
    }

    def "long arrays"(final long msb, final long lsb) {
        when:
        var ulid = new ULID(msb, lsb)

        then:
        ulid == ULID.of([msb, lsb] as long[])
        Arrays.compare(ulid.longArray(), [msb, lsb] as long[]) == 0

        where:
        msb                | lsb
        106488908812075305 | -4319074297954495403
        106488908812472562 | 6572592153539793428
        106488908812582212 | 8044406689517789089
        106488908812532456 | -8530477724920209271
        106488908812531945 | 8902815651882132302
        106488908812580922 | 7590718262229469868
        106488908812578650 | -3855439503401833733
        106488908812531689 | -3236540622851187374
        106488908812542931 | -7457531745653787183
        106488908812567387 | -2131976270164827955
        106488908812558036 | -3290583620705089743
        106488908812607746 | -2330995220813317111
        106488908812589037 | -210475388775447952
        106488908812597086 | -204248045653603675
        106488908812625946 | -4849753891595863982
        106488908812655775 | 2336043870035427907
        106488908812672180 | 3846502394207342721
        106488908812668771 | 3176672949875242721
    }

    def "bad byte array"() {
        when:
        ULID.of([0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0] as byte[])

        then:
        thrown(IllegalArgumentException.class)
    }

    def "bad long byte offset"() {
        when:
        ULID.of([0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0] as byte[], 8)

        then:
        thrown(IllegalArgumentException.class)
    }

    def "byte arrays"(final long msb, final long lsb) {
        when:
        var ulid = new ULID(msb, lsb)

        and:
        var array = ulid.array()

        then:
        ulid == ULID.of(array)
        Arrays.compare(ulid.array(), array) == 0

        where:
        msb                | lsb
        106488908812075305 | -4319074297954495403
        106488908812472562 | 6572592153539793428
        106488908812582212 | 8044406689517789089
        106488908812532456 | -8530477724920209271
        106488908812531945 | 8902815651882132302
        106488908812580922 | 7590718262229469868
        106488908812578650 | -3855439503401833733
        106488908812531689 | -3236540622851187374
        106488908812542931 | -7457531745653787183
        106488908812567387 | -2131976270164827955
        106488908812558036 | -3290583620705089743
        106488908812607746 | -2330995220813317111
        106488908812589037 | -210475388775447952
        106488908812597086 | -204248045653603675
        106488908812625946 | -4849753891595863982
        106488908812655775 | 2336043870035427907
        106488908812672180 | 3846502394207342721
        106488908812668771 | 3176672949875242721
    }
}
