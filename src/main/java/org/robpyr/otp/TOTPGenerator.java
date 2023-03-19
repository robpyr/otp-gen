package org.robpyr.otp;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.Key;

import static org.robpyr.otp.DefaultSettings.*;

public class TOTPGenerator {

    public String generateOTP(String key, BigInteger unixSeconds) {
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        BigInteger timestamp = unixSeconds.divide(BigInteger.valueOf(TOTP_TIME_STEP));

        byte[] timeArray = ByteBuffer.allocate(8).putLong(timestamp.longValue()).array();

        try {
            Mac mac = Mac.getInstance(SHA_1_ALG);
            mac.init(mapToKey(keyBytes));
            byte[] hash = mac.doFinal(timeArray);

            int offset = hash[hash.length - 1] & 0x0f;
            byte[] offsetHash = {hash[offset],
                    hash[offset + 1],
                    hash[offset + 2],
                    hash[offset + 3]};

            ByteBuffer byteBuffer = ByteBuffer.wrap(offsetHash);
            long otp = byteBuffer.getInt();

            otp &= 0x7FFFFFFF;
            otp %= (int) Math.pow(10, DEFAULT_TOTP_LENGTH);

            return String.format("%0" + DEFAULT_TOTP_LENGTH + "d", otp);
        } catch (Exception e) {
            return null;
        }
    }

    private Key mapToKey(byte[] keyBytes) {
        return new SecretKeySpec(keyBytes, "RAW");
    }
}
