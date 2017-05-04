package com.reuth.hack.textdisrupt;

/**
 * Created by mikab on 04/05/2017.
 */

public class VowelsToggle {
    public static final char HYPHEN = '.';
    public static final char MAQAF_HYPHEN = '-';

    private static final char CLOSED_QUOTE = '\u2019';
    private static final char OPEN_QUOTE = '\u2018';
    private static final char K_WITH_LINE = '\u1e35';
    private static final char T_WITH_DOT = '\u1e6d';
    private static final char H_WITH_DOT = '\u1e25';
    private static final char B_WITH_LINE = '\u1E07';

    private static final char QAMATS_QATAN = 0x5C7;

    private static final char SHEVA = 0x05B0;
    private static final char HATAF_SEGOL = 0x5B1;
    private static final char HATAF_PATAH = 0x5B2;
    private static final char HATAF_QAMATS = 0x5B3;
    private static final char HIRIQ = 0x5B4;
    private static final char TSERE = 0x5B5;
    private static final char SEGOL = 0x5B6;
    private static final char PATAH = 0x5B7;
    private static final char QAMATS = 0x5B8;
    private static final char HOLAM = 0x5B9;
    private static final char QAMATS_2 = 0x5BA;
    private static final char QUBUTS = 0x5BB;
    private static final char DAGESH = 0x5BC;
    private static final char METEG = 0x05BD;


    private static final char SHIN_DOT = 0x05C1;
    private static final int ETNAHTA = 0x0591;
    private static final char GERESH = 0x059C;
    private static final char GERESH_MUQDAM = 0x059D;
    private static final int ZINOR = 0x05AE;

    private static final int DAGESH_GAP = 0xFB44 - 0x05e3;
    private static final int ALEPH = 0x05D0;
    private static final char ALEPH_LAMED = 0xFB4F;
    private static final char BET = 0x5D1;
    private static final char GIMEL = 0x5D2;
    private static final char DALET = 0x5D3;
    private static final int HE = 0x5D4;
    private static final char VAV = 0x5D5;
    private static final int ZAYIN = 0x5D6;
    private static final char HET = 0x5D7;
    private static final int TET = 0x5D8;
    private static final char YOD = 0x5D9;
    private static final char FINAL_KAF = 0x5DA;
    private static final char KAF = 0x5DB;
    private static final char LAMED = 0x5DC;
    private static final int FINAL_MEM = 0x5DD;
    private static final int MEM = 0x5DE;
    private static final int FINAL_NUN = 0x5DF;
    private static final int NUN = 0x5E0;
    private static final int SAMEKH = 0x5E1;
    private static final int AYIN = 0x5E2;
    private static final int FINAL_PE = 0x5E3;
    private static final char PE = 0x5E4;
    private static final int FINAL_TSADI = 0x5E5;
    private static final int TSADI = 0x5E6;
    private static final char QOF = 0x5E7;
    private static final char RESH = 0x5E8;
    private static final int SIN = 0x5E9;
    private static final char TAV = 0x5EA;
    private static final char MAQAF = 0x05BE;
    private static final char HEBREW_COMBINED_RANGE_START = 0xFB1D;

    private String str;

    public VowelsToggle(String str) {
        this.str = str;
    }

    /**
     * @return true if the received str contains Hebrew vowel
     */
    private boolean isContainsHebrewVowel() {
        for (char c: str.toCharArray()) {
            if (isHebrewVowel(c)) {
                return true;
            }
        }

        return false;
    }

    /**
     * @param c the character
     * @return true to indicate a vowel
     */
    private static boolean isHebrewVowel(final char c) {
        return c >= SHEVA && c <= QAMATS_QATAN && c != DAGESH && c != SHIN_DOT;
    }



    /**
     * @return text without pointing and vowels
     */
    public String removeVowels() {

        final StringBuilder sb = new StringBuilder(str);
        int i = 0;
        while (i < sb.length()) {
            final char currentChar = sb.charAt(i);
            //ignore characters outside of the Hebrew character set
            if(currentChar < ETNAHTA || currentChar > ALEPH_LAMED) {
                i++;
            } else if (currentChar < ALEPH) {
                sb.deleteCharAt(i);
            } else if (currentChar >= HEBREW_COMBINED_RANGE_START && currentChar < ALEPH_LAMED) {
                sb.setCharAt(i, (char) (currentChar - DAGESH_GAP));
                i++;
            } else {
                i++;
            }
        }
        return sb.toString();
    }

    public String getOriginalString() {
        return str;
    }
}
