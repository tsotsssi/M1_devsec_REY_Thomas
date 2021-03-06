package com.example.m1_devsec_rey_thomas;

public class XORCrypt {
    /*
    static String value = "https://60102f166c21e10017050128.mockapi.io/accounts";
    */
    public static String keyval = "aiuyb86pnzg0bzt462uyav";
    /*
    public static void XOR() { // test
        int[] encrypted = encrypt(value,keyval);
        for(int i = 0; i < encrypted.length; i++)
            System.out.printf("%d,", encrypted[i]);
        System.out.println();
        System.out.println(decrypt(encrypted,keyval));
    }

    private static int[] encrypt(String str, String key) {
        int[] output = new int[str.length()];
        for(int i = 0; i < str.length(); i++) {
            int o = (Integer.valueOf(str.charAt(i)) ^ Integer.valueOf(key.charAt(i % (key.length() - 1)))) + '0';
            output[i] = o;
        }
        return output;
    }

    private static int[] string2Arr(String str) {
        String[] sarr = str.split(",");
        int[] out = new int[sarr.length];
        for (int i = 0; i < out.length; i++) {
            out[i] = Integer.valueOf(sarr[i]);
        }
        return out;
    }
    */
    protected static String decrypt(int[] input, String key) {
        String output = "";
        for(int i = 0; i < input.length; i++) {
            output += (char) ((input[i] - 48) ^ (int) key.charAt(i % (key.length() - 1)));
        }
        return output;
    }
}