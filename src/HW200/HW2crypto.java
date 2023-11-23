

import java.util.Random;

/**
 *   HW2crypto provides the encrypt method for encrypting a string
 *
 *   Usage:
 *   HW2crypto.encrypt(...)
 */
public class HW2crypto {
    /**
     * This method creates an encrypted string of the word provided
     *
     * @param word The word that needs to be encrypted
     * @return The encrypted word as a String
     */
    public static String encrypt(String word) {

        final long seed = 723637564;
        Random rnd = new Random(seed);

        StringBuilder plainText = new StringBuilder(word);
        int size = 20  - plainText.length();
        int ascii = (int) (plainText.charAt(plainText.length() - 1));
        for(int i=0;i<size;i++) {
            plainText.append((char) (((rnd.nextInt(Integer.MAX_VALUE) + ascii) % 94) + 33));
            ascii += rnd.nextInt(200);
        }

        StringBuilder encryptedWord = new StringBuilder();

        ascii = (int) (plainText.charAt(plainText.length() - 1));
        for (char c : plainText.toString().toCharArray()) {
            ascii += (int) c;
            encryptedWord.append((char) (((rnd.nextInt(Integer.MAX_VALUE) + ascii) % 94) + 33));
            encryptedWord.append((char) (((rnd.nextInt(Integer.MAX_VALUE) + ascii) % 94) + 33));
        }

        return encryptedWord.toString();
    }

    public static void main(String[] args) {

        System.out.println(encrypt("cat"));
        System.out.println(encrypt("at"));
        System.out.println(encrypt("dog"));
        System.out.println(encrypt("and"));
        System.out.println(encrypt("tan"));
        System.out.println(encrypt("on"));
        System.out.println(encrypt("arr"));
        System.out.println(encrypt("ace"));
        System.out.println(encrypt("hex"));

    }
}
