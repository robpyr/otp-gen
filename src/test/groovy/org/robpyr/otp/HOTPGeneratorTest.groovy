package org.robpyr.otp

import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

class HOTPGeneratorTest extends Specification {

    @Subject
    private HOTPGenerator subject = new HOTPGenerator()

    @Unroll
    def "should generate HOTP - #counter"() {
        given:
            def key = "12345678901234567890"
        when:
            def result = subject.generateOTP(key, counter)
        then:
            result == expected
        where:
            counter             | expected
            new BigInteger("0") | "755224"
            new BigInteger("1") | "287082"
            new BigInteger("2") | "359152"
            new BigInteger("3") | "969429"
            new BigInteger("4") | "338314"
            new BigInteger("5") | "254676"
            new BigInteger("6") | "287922"
            new BigInteger("7") | "162583"
            new BigInteger("8") | "399871"
            new BigInteger("9") | "520489"
    }
}
