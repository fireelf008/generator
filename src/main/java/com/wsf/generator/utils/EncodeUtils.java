package com.wsf.generator.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.shiro.codec.Hex;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.util.Stack;

/**
 * 编码解码工具类
 * Created by wangshaofu on 2017/12/25.
 */
public class EncodeUtils {

    private static Logger logger = LoggerFactory.getLogger(EncodeUtils.class);

    private static final String DEFAULT_URL_ENCODING = "UTF-8";

    private static char[] charSet = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz".toCharArray();

    private static final char[] HEX_DIGITS = {'0', '1', '2', '3', '4', '5',
            '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};

    private static final String ALGORITHM = "DESede";

    public static final String BDCZX_3DES_KEY = "bcd8d6ab3b68069de4bb35b6b97bff2c";

    private EncodeUtils() {

    }

    /**
     * 将字符串以MD5格式编码
     * @param str
     * @return
     */
    public static String md5Encode(String str)
    {
        String md5Str = "";
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte []bytes = md5.digest(str.getBytes(DEFAULT_URL_ENCODING));
            String temp;
            for (int i = 0; i < bytes.length; i++) {
                temp = Integer.toHexString(bytes[i] & 0xFF);
                if (1 == temp.length()) {
                    md5Str += "0";
                }
                md5Str += temp;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return md5Str;
    }

    /**
     * 将字符串按指定编码转换成字节流，以MD5格式编码
     * @param str
     * @return
     */
    public static String md5Encode(String str, String charset)
    {
        String md5Str = "";
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            byte []bytes = md5.digest(str.getBytes(charset));
            String temp;
            for (int i = 0; i < bytes.length; i++) {
                temp = Integer.toHexString(bytes[i] & 0xFF);
                if (1 == temp.length()) {
                    md5Str += "0";
                }
                md5Str += temp;
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return md5Str;
    }

    /**
     * Hex编码.
     */
    public static String hexEncode(byte[] input) {
        String str = null;
        try {
            str = new String(Hex.encode(input));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return str;
    }

    /**
     * Hex解码.
     */
    public static byte[] hexDecode(String input) {
        byte []bytes = null;
        try {
            bytes = Hex.decode(input.toCharArray());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return bytes;
    }

    /**
     * Base64编码.
     */
    public static String base64Encode(byte[] input) {
        String str = null;
        try {
            str = new String(Base64.encodeBase64(input));
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return str;
    }

    /**
     * Base64解码.
     */
    public static byte[] base64Decode(String input) {
        byte []bytes = null;
        try {
            bytes = Base64.decodeBase64(input.getBytes());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return bytes;
    }

    /**
     * URL 编码, Encode默认为UTF-8.
     */
    public static String urlEncode(String input) {
        String url = null;
        try {
            url = URLEncoder.encode(input, DEFAULT_URL_ENCODING);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return url;
    }

    /**
     * URL 编码.
     */
    public static String urlEncode(String input, String encoding) {
        String url = null;
        try {
            url = URLEncoder.encode(input, encoding);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return url;
    }

    /**
     * URL 解码, Encode默认为UTF-8.
     */
    public static String urlDecode(String input) {
        String url = null;
        try {
            url = URLDecoder.decode(input, DEFAULT_URL_ENCODING);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return url;
    }

    /**
     * URL 解码.
     */
    public static String urlDecode(String input, String encoding) {
        String url = null;
        try {
            url = URLDecoder.decode(input, encoding);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return url;
    }

    /**
     * Html转码.
     */
    public static String htmlEscape(String html) {
        String str = null;
        try {
            str = StringEscapeUtils.unescapeHtml4(html);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return str;
    }

    /**
     * Html 反转码.
     */
    public static String htmlUnescape(String htmlEscaped) {
        String str = null;
        try {
            str = StringEscapeUtils.unescapeHtml4(htmlEscaped);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return str;
    }

    /**
     * Xml转码.
     */
    public static String xmlEscape(String xml) {
        return StringEscapeUtils.escapeXml(xml);
    }

    /**
     * Xml 反转码.
     */
    public static String xtmlUnescape(String xmlEscaped) {
        return StringEscapeUtils.unescapeXml(xmlEscaped);
    }

    public static StringBuilder appendString(String args1,String args2 ){
        StringBuilder sb = new StringBuilder(args1);
        sb.append(args2);
        return sb;
    }

    /**
     * Unicode编码
     * @param string
     * @return
     */
    public static String stringToUnicode(String string) {

        StringBuilder unicode = new StringBuilder();
        for (int i = 0; i < string.length(); i++) {

            // 取出每一个字符
            char c = string.charAt(i);

            // 转换为unicode
            unicode.append("\\u" + Integer.toHexString(c));
        }
        return unicode.toString();
    }

    /**
     * Unicode解码
     * @param unicode
     * @return
     */
    public static String unicodeToString(String unicode) {

        StringBuilder sb = new StringBuilder();
        String[] hex = unicode.split("\\\\u");
        for (int i = 1; i < hex.length; i++) {

            // 转换出每一个代码点
            int data = Integer.parseInt(hex[i], 16);

            // 追加成string
            sb.append((char) data);
        }
        return sb.toString();
    }

    /**
     * 将10进制转化为62进制
     *
     * @param number
     * @param length 转化成的62进制长度，不足length长度的话高位补0，否则不改变什么
     * @return
     */
    public static String convert62(long number, int length) {
        Long rest = number;
        Stack<Character> stack = new Stack<Character>();
        StringBuilder result = new StringBuilder(0);
        while (rest != 0) {
            stack.add(charSet[new Long((rest - (rest / 62) * 62)).intValue()]);
            rest = rest / 62;
        }
        for (; !stack.isEmpty(); ) {
            result.append(stack.pop());
        }
        int result_length = result.length();
        StringBuilder temp0 = new StringBuilder();
        for (int i = 0; i < length - result_length; i++) {
            temp0.append('0');
        }

        return temp0.toString() + result.toString();

    }

    /**
     * 将62进制转换成10进制数
     *
     * @param ident62
     * @return
     */
    public static long convert10(String ident62) {
        Long dst = 0L;
        for (int i = 0; i < ident62.length(); i++) {
            char c = ident62.charAt(i);
            for (int j = 0; j < charSet.length; j++) {
                if (c == charSet[j]) {
                    dst = (dst * 62) + j;
                    break;
                }
            }
        }
        String str = String.format("%08d", dst);
        return Long.parseLong(str);
    }

    private static String getFormattedText(byte[] bytes) {
        int len = bytes.length;
        StringBuilder buf = new StringBuilder(len * 2);
        // 把密文转换成十六进制的字符串形式
        for (int j = 0; j < len; j++) {
            buf.append(HEX_DIGITS[(bytes[j] >> 4) & 0x0f]);
            buf.append(HEX_DIGITS[bytes[j] & 0x0f]);
        }
        return buf.toString();
    }

    /**
     * SHA1加密
     * @param str
     * @return
     */
    public static String getSHA1Encode(String str) {
        if (str == null) {
            return null;
        }
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA1");
            messageDigest.update(str.getBytes());
            return getFormattedText(messageDigest.digest());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    public static byte[] getHex(String key) {
        byte[] secretKeyByte = new byte[24];
        try {
            byte[] hexByte;
            hexByte = new String(DigestUtils.md5Hex(key)).getBytes(DEFAULT_URL_ENCODING);
            //秘钥长度固定为24位
            System.arraycopy(hexByte, 0, secretKeyByte, 0, 24);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return secretKeyByte;
    }

    /**
     * 3DES编码
     * @param input
     * @param key
     * @return
     */
    public static String desEncode(String input, String key) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(getHex(key), ALGORITHM));
            return Base64.encodeBase64String(cipher.doFinal(input.getBytes(DEFAULT_URL_ENCODING)));
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 3des解码
     * @param input
     * @param key
     * @return
     */
    public static String desDecode(String input, String key) {
        try {
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(getHex(key), ALGORITHM));
            return new String(cipher.doFinal(new Base64().decode(input)), DEFAULT_URL_ENCODING);
        } catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        String str = "超级管理员";
        String key = EncodeUtils.BDCZX_3DES_KEY;
        System.out.println("key----->" + key);
        String encodeStr = EncodeUtils.desEncode(str, key);
        System.out.println("encode----->" + encodeStr);
        String decodeStr = EncodeUtils.desDecode(encodeStr, key);
        System.out.println("decode----->" + decodeStr);
    }
}
