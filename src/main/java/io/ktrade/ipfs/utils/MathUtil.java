package io.ktrade.ipfs.utils;

import org.apache.commons.lang3.StringUtils;

import java.math.BigDecimal;

public class MathUtil {
    public static BigDecimal hexToDec(String hex) {
        BigDecimal result = new BigDecimal("0");
        if (StringUtils.isEmpty(hex)) {
            return result;
        }
        //String line = "000000000000000000000000000000000000000000000000ed23e5ba6b42a000";
        String line = hex.toUpperCase();
        if (line.startsWith("0X")) {
            line = line.substring(2);
        }

        BigDecimal base = new BigDecimal("16");
        for (int i = 0; i < line.length(); i++) {
            char ch = line.charAt(line.length() - 1 - i);
            if (ch >= 'A' && ch <= 'F') {
                BigDecimal tmp = base.pow(i).multiply(new BigDecimal(Integer.toString((ch - 'A' + 10))));
                result = result.add(tmp);
            } else {
                BigDecimal tmp = base.pow(i).multiply(new BigDecimal(Character.toString(ch)));
                result = result.add(tmp);
            }
        }
        //System.out.println("result = " + result);
        return result;
    }

    public static void main(String[] args) {
        String line = "000000000000000000000000000000000000000000000000ed23e5ba6b42a000";

        BigDecimal d = MathUtil.hexToDec(line);
        System.out.println("d=" + d);
    }
}
