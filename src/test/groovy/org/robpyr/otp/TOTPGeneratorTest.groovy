package org.robpyr.otp

import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

import static org.robpyr.otp.DefaultSettings.SHA_1_ALG

class TOTPGeneratorTest extends Specification {

    @Subject
    private TOTPGenerator subject = new TOTPGenerator()

    @Unroll
    def "should generate TOTP - #seconds, #alg"() {
        given:
            def key = "12345678901234567890"
        when:
            def result = subject.generateOTP(key, new BigInteger(seconds))
        then:
            result == expected
        where:
            seconds       | expected   | alg
            "59"          | "94287082" | SHA_1_ALG
            "1111111109"  | "07081804" | SHA_1_ALG
            "1111111111"  | "14050471" | SHA_1_ALG
            "1234567890"  | "89005924" | SHA_1_ALG
            "2000000000"  | "69279037" | SHA_1_ALG
            "20000000000" | "65353130" | SHA_1_ALG
    }
}
