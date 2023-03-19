package org.robpyr.otp;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.security.Key;

import static org.robpyr.otp.DefaultSettings.DEFAULT_HOTP_LENGTH;
import static org.robpyr.otp.DefaultSettings.SHA_1_ALG;

public class HOTPGenerator {

    public String generateOTP(String key, BigInteger counter) {
        byte[] keyBytes = key.getBytes(StandardCharsets.UTF_8);
        byte[] counterBytes = counter.toByteArray();
        ArrayUtils.revertArray(counterBytes);

        byte[] text = ArrayUtils.paddingWitLeading(counterBytes, 8);

        try {
            Mac mac = Mac.getInstance(SHA_1_ALG);
            mac.init(mapToKey(keyBytes));
            byte[] hashBytes = mac.doFinal(text);

            int offset = hashBytes[hashBytes.length - 1] & 0x0f;
            byte[] offsetHash = {hashBytes[offset],
                    hashBytes[offset + 1],
                    hashBytes[offset + 2],
                    hashBytes[offset + 3]};

            ByteBuffer byteBuffer = ByteBuffer.wrap(offsetHash);
            long otp = byteBuffer.getInt();

            otp &= 0x7FFFFFFF;
            otp %= Math.pow(10, DEFAULT_HOTP_LENGTH);

            return String.format("%0" + DEFAULT_HOTP_LENGTH + "d", otp);
        } catch (Exception e) {
            return null;
        }
    }

    private Key mapToKey(byte[] keyBytes) {
        return new SecretKeySpec(keyBytes, "RAW");
    }
}
