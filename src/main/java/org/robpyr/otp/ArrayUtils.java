package org.robpyr.otp;

import java.util.Objects;

class ArrayUtils {

    static void revertArray(byte[] array) {
        if (array != null && array.length >= 2) {
            for (int i = 0; i < array.length / 2; i++) {
                byte temp = array[i];
                array[i] = array[array.length - 1 - i];
                array[array.length - 1 - i] = temp;
            }
        }
    }

    static byte[] paddingWitLeading(byte[] array, int length) {
        Objects.requireNonNull(array, "Array is required");
        if (array.length == length) {
            return array;
        } else if (array.length > length) {
            return trimLeading(array, length);
        } else {
            byte[] target = new byte[length];
            int offset = length - array.length;

            for (int i = 0; offset < length; ++offset, ++i) {
                target[offset] = array[i];
            }
            return target;
        }
    }

    private static byte[] trimLeading(byte[] array, int length) {
        int offset = length;
        byte[] target = new byte[length];

        for (int i = 0; offset < array.length; ++offset, ++i) {
            target[i] = array[offset];
        }
        return target;
    }
}
