package org.jlw.ulid

import spock.lang.Specification

class UlidBinaryAttributeConverterSpec extends Specification {
    def "conversion"(final long msb, final long lsb, final byte[] array) {
        when:
        var ulid = new ULID(msb, lsb)

        then:
        new UlidBinaryAttributeConverter().convertToDatabaseColumn(ulid) == array
        new UlidBinaryAttributeConverter().convertToEntityAttribute(array) == ulid

        where:
        msb                | lsb                  | array
        106488908812075305 | -4319074297954495403 | [1, 122, 83, 25, 46, 67, 65, 41, -60, 15, -112, -49, 55, -25, 20, 85]
        106488908812472562 | 6572592153539793428  | [1, 122, 83, 25, 46, 73, 80, -14, 91, 54, -119, -67, -107, 66, 82, 20]
        106488908812582212 | 8044406689517789089  | [1, 122, 83, 25, 46, 74, -3, 68, 111, -93, 121, 67, 85, 123, -33, -95]
        106488908812532456 | -8530477724920209271 | [1, 122, 83, 25, 46, 74, 58, -24, -119, -99, -89, -76, -95, -65, -96, -119]
        106488908812531945 | 8902815651882132302  | [1, 122, 83, 25, 46, 74, 56, -23, 123, -115, 39, -86, -113, 56, -29, 78]
        106488908812580922 | 7590718262229469868  | [1, 122, 83, 25, 46, 74, -8, 58, 105, 87, -90, 10, -54, 66, 10, -84]
        106488908812578650 | -3855439503401833733 | [1, 122, 83, 25, 46, 74, -17, 90, -54, 126, -70, 50, -38, -71, -86, -5]
        106488908812531689 | -3236540622851187374 | [1, 122, 83, 25, 46, 74, 55, -23, -45, 21, 127, 115, -111, -43, 93, 82]
        106488908812542931 | -7457531745653787183 | [1, 122, 83, 25, 46, 74, 99, -45, -104, -127, -122, 99, -105, 27, 117, -47]
        106488908812567387 | -2131976270164827955 | [1, 122, 83, 25, 46, 74, -61, 91, -30, 105, -78, -32, -61, 67, 96, -51]
        106488908812558036 | -3290583620705089743 | [1, 122, 83, 25, 46, 74, -98, -44, -46, 85, 127, -95, -104, 39, -121, 49]
        106488908812607746 | -2330995220813317111 | [1, 122, 83, 25, 46, 75, 97, 2, -33, -90, -92, 50, 98, 73, -4, 9]
        106488908812589037 | -210475388775447952  | [1, 122, 83, 25, 46, 75, 23, -19, -3, 20, 61, -65, -62, 19, -62, 112]
        106488908812597086 | -204248045653603675  | [1, 122, 83, 25, 46, 75, 55, 94, -3, 42, 93, 124, 16, -65, -54, -91]
        106488908812625946 | -4849753891595863982 | [1, 122, 83, 25, 46, 75, -88, 26, -68, -78, 54, -121, -37, 92, 56, 82]
        106488908812655775 | 2336043870035427907  | [1, 122, 83, 25, 46, 76, 28, -97, 32, 107, 75, -123, -35, 48, -62, 67]
        106488908812672180 | 3846502394207342721  | [1, 122, 83, 25, 46, 76, 92, -76, 53, 97, -123, -116, 66, 107, 16, -127]
        106488908812668771 | 3176672949875242721  | [1, 122, 83, 25, 46, 76, 79, 99, 44, 21, -49, 55, -104, -25, -10, -31]
        106488908812075305 | -4319074297954495403 | [1, 122, 83, 25, 46, 67, 65, 41, -60, 15, -112, -49, 55, -25, 20, 85]
        106488908812472562 | 6572592153539793428  | [1, 122, 83, 25, 46, 73, 80, -14, 91, 54, -119, -67, -107, 66, 82, 20]
        106488908812582212 | 8044406689517789089  | [1, 122, 83, 25, 46, 74, -3, 68, 111, -93, 121, 67, 85, 123, -33, -95]
        106488908812532456 | -8530477724920209271 | [1, 122, 83, 25, 46, 74, 58, -24, -119, -99, -89, -76, -95, -65, -96, -119]
        106488908812531945 | 8902815651882132302  | [1, 122, 83, 25, 46, 74, 56, -23, 123, -115, 39, -86, -113, 56, -29, 78]
        106488908812580922 | 7590718262229469868  | [1, 122, 83, 25, 46, 74, -8, 58, 105, 87, -90, 10, -54, 66, 10, -84]
        106488908812578650 | -3855439503401833733 | [1, 122, 83, 25, 46, 74, -17, 90, -54, 126, -70, 50, -38, -71, -86, -5]
        106488908812531689 | -3236540622851187374 | [1, 122, 83, 25, 46, 74, 55, -23, -45, 21, 127, 115, -111, -43, 93, 82]
        106488908812542931 | -7457531745653787183 | [1, 122, 83, 25, 46, 74, 99, -45, -104, -127, -122, 99, -105, 27, 117, -47]
        106488908812567387 | -2131976270164827955 | [1, 122, 83, 25, 46, 74, -61, 91, -30, 105, -78, -32, -61, 67, 96, -51]
        106488908812558036 | -3290583620705089743 | [1, 122, 83, 25, 46, 74, -98, -44, -46, 85, 127, -95, -104, 39, -121, 49]
        106488908812607746 | -2330995220813317111 | [1, 122, 83, 25, 46, 75, 97, 2, -33, -90, -92, 50, 98, 73, -4, 9]
        106488908812589037 | -210475388775447952  | [1, 122, 83, 25, 46, 75, 23, -19, -3, 20, 61, -65, -62, 19, -62, 112]
        106488908812597086 | -204248045653603675  | [1, 122, 83, 25, 46, 75, 55, 94, -3, 42, 93, 124, 16, -65, -54, -91]
        106488908812625946 | -4849753891595863982 | [1, 122, 83, 25, 46, 75, -88, 26, -68, -78, 54, -121, -37, 92, 56, 82]
        106488908812655775 | 2336043870035427907  | [1, 122, 83, 25, 46, 76, 28, -97, 32, 107, 75, -123, -35, 48, -62, 67]
        106488908812672180 | 3846502394207342721  | [1, 122, 83, 25, 46, 76, 92, -76, 53, 97, -123, -116, 66, 107, 16, -127]
        106488908812668771 | 3176672949875242721  | [1, 122, 83, 25, 46, 76, 79, 99, 44, 21, -49, 55, -104, -25, -10, -31]
    }

    def "bad ulid"(final byte[] value) {
        when:
        new UlidBinaryAttributeConverter().convertToEntityAttribute(value)

        then:
        thrown(IllegalArgumentException.class)

        where:
        value << [
                [1],
                [1, 122],
                [1, 122, 83],
                [1, 122, 83, 25],
                [1, 122, 83, 25, 46],
                [1, 122, 83, 25, 46, 74],
                [1, 122, 83, 25, 46, 74, -17],
                [1, 122, 83, 25, 46, 74, 55, -23],
                [1, 122, 83, 25, 46, 74, 99, -45, -104],
                [1, 122, 83, 25, 46, 74, -61, 91, -30, 105],
                [1, 122, 83, 25, 46, 74, -98, -44, -46, 85, 127],
                [1, 122, 83, 25, 46, 75, 97, 2, -33, -90, -92, 50],
                [1, 122, 83, 25, 46, 75, 23, -19, -3, 20, 61, -65, -62],
                [1, 122, 83, 25, 46, 75, 55, 94, -3, 42, 93, 124, 16, -65],
                [1, 122, 83, 25, 46, 75, -88, 26, -68, -78, 54, -121, -37, 92],
                [1, 122, 83, 25, 46, 76, 28, -97, 32, 107, 75, -123, -35, 48, -62],
        ]
    }
}