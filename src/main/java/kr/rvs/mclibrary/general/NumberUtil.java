package kr.rvs.mclibrary.general;

/**
 * Created by Junhyeong Lim on 2017-10-08.
 */
public class NumberUtil {
    public static String toRomanNumeral(int input) {
        if (input < 1 || input > 3999)
            return "Invalid Roman Number Value";
        StringBuilder builder = new StringBuilder();
        while (input >= 1000) {
            builder.append("M");
            input -= 1000;        }
        while (input >= 900) {
            builder.append("CM");
            input -= 900;
        }
        while (input >= 500) {
            builder.append("D");
            input -= 500;
        }
        while (input >= 400) {
            builder.append("CD");
            input -= 400;
        }
        while (input >= 100) {
            builder.append("C");
            input -= 100;
        }
        while (input >= 90) {
            builder.append("XC");
            input -= 90;
        }
        while (input >= 50) {
            builder.append("L");
            input -= 50;
        }
        while (input >= 40) {
            builder.append("XL");
            input -= 40;
        }
        while (input >= 10) {
            builder.append("X");
            input -= 10;
        }
        while (input >= 9) {
            builder.append("IX");
            input -= 9;
        }
        while (input >= 5) {
            builder.append("V");
            input -= 5;
        }
        while (input >= 4) {
            builder.append("IV");
            input -= 4;
        }
        while (input >= 1) {
            builder.append("I");
            input -= 1;
        }
        return builder.toString();
    }
}
