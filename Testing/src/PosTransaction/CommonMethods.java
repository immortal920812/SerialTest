package PosTransaction;

import java.util.ArrayList;
import java.util.List;

public class CommonMethods {
    public static String ASCIItoHEX(String ascii)
    {
        String hex = "";
        for (int i = 0; i < ascii.length(); i++) {
            char ch = ascii.charAt(i);
            int in = (int)ch;
            String part = Integer.toHexString(in);
            hex += part;
        }
        return hex;
    }

    public static String ASCIItoHEX(String ascii, int length)
    {
        String hex = "";
        if(ascii.length() <length){
            int i = 0;
            while(i < length - ascii.length()) {
                hex += Integer.toHexString('0');
                i++;
            }
        }
        for (int i = 0; i < ascii.length(); i++) {
            char ch = ascii.charAt(i);
            int in = (int)ch;
            String part = Integer.toHexString(in);
            hex += part;
        }
        return hex;
    }


    private static String addLeadZero(int number) {
        String formattedNumber = "";
        formattedNumber =String.format("%02d",number);
        return formattedNumber;
    }

    private static int[] changeToIntArray(int number){
        String s = Integer.toString(number);
        int[] intArray = new int[s.length()];
        for(int i = 0 ; i < s.length() ; i++)
            intArray[i] = Character.getNumericValue(s.charAt(i));
        return intArray;
    }

    public static String formatDataLength(int length) {
        String dataLengthStr = "";
        if(length >= 100) {
            dataLengthStr += addLeadZero(length / 100);
            dataLengthStr += addLeadZero(length % 100);
        }else{
            dataLengthStr += "00";
            if(length >= 10)
                dataLengthStr += String.valueOf(length);
            else
                dataLengthStr += addLeadZero(length);
        }
        return dataLengthStr;
    }

    public static String formatIPAddress(String url) {
        String result = "";
        String ipAddress = url.substring(0, url.indexOf(":"));
        String portNumber = url.substring(url.indexOf(":") + 1);
        result = ipAddress + "00" + portNumber;
        return result;
    }

    public static String HEXtoASCII(String hex)
    {
        String ascii = "";
        for (int i = 0; i < hex.length(); i++) {
            char ch = ascii.charAt(i);
            int in = (int)ch;
            String part = Integer.toOctalString(in);
            ascii += part;
        }
        return ascii;
    }

    public static byte[] stringToHexByteArray(String str){
        byte[] data = new byte[str.length()*2];
        int j = 0;
        for (int i = 0; i < str.length(); i++)
        {
            char ch = str.charAt(i);
            int high_nibble = (ch & 0xf0) >>> 4;
            int low_nibble = (ch & 0x0f);
            data[i*2] = (byte)high_nibble;
            data[i*2+1] = (byte)low_nibble;
        }
        return data;
    }
}
