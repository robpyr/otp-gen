package org.robpyr.otp

import spock.lang.Specification
import spock.lang.Unroll

class ArrayUtilsTest extends Specification {

    @Unroll
    def "should return padded byte array for #value"() {
        when:
            def result = ArrayUtils.paddingWitLeading(value, length)
        then:
            result == expected
        where:
            value                                      | length | expected
            [(byte) 125, (byte) 0, (byte) 0] as byte[] | 3      | [(byte) 125, (byte) 0, (byte) 0] as byte[]
            [(byte) 125, (byte) 0, (byte) 0] as byte[] | 2      | [(byte) 0, (byte) 0] as byte[]
            [(byte) 125] as byte[]                     | 3      | [(byte) 0, (byte) 0, (byte) 125] as byte[]
    }

    @Unroll
    def "should revert array - #array"() {
        when:
            ArrayUtils.revertArray(array)
        then:
            array == expected
        where:
            array                                      | expected
            [] as byte[]                               | [] as byte[]
            [(byte) 125] as byte[]                     | [(byte) 125] as byte[]
            [(byte) 125, (byte) 0, (byte) 0] as byte[] | [(byte) 0, (byte) 0, (byte) 125] as byte[]
    }
}
