package com.rdc.takebus.model.utils;

/**
 * 编码转换
 */
public class EncoderUtil {

    /**
     * 将unicode编码转化为utf-8编码格式
     */
    public static String decodeUnicode(String str) {
        char c;
        int length = str.length();
        StringBuffer stringBuffer = new StringBuffer(length);
        for (int x = 0; x < length; ) {
            c = str.charAt(x++);
            if (c == '\\') {
                c = str.charAt(x++);
                if (c == 'u') {
                    int value = 0;
                    for (int i = 0; i < 4; i++) {
                        c = str.charAt(x++);
                        switch (c) {
                            case '0':
                            case '1':
                            case '2':
                            case '3':
                            case '4':
                            case '5':
                            case '6':
                            case '7':
                            case '8':
                            case '9':
                                value = (value << 4) + c - '0';
                                break;
                            case 'a':
                            case 'b':
                            case 'c':
                            case 'd':
                            case 'e':
                            case 'f':
                                value = (value << 4) + 10 + c - 'a';
                                break;
                            case 'A':
                            case 'B':
                            case 'C':
                            case 'D':
                            case 'E':
                            case 'F':
                                value = (value << 4) + 10 + c - 'A';
                                break;
                            default:
                                throw new IllegalArgumentException(
                                        "Malformed   \\uxxxx   encoding.");
                        }

                    }
                    stringBuffer.append((char) value);
                } else {
                    if (c == 't')
                        c = '\t';
                    else if (c == 'r')
                        c = '\r';
                    else if (c == 'n')
                        c = '\n';
                    else if (c == 'f')
                        c = '\f';
                    stringBuffer.append(c);
                }
            } else
                stringBuffer.append(c);
        }
        return stringBuffer.toString();
    }
}
