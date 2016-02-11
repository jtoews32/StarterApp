package com.jt.app.starterapp;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Class BoyerMoore **/
public class BoyerMoore
{
    final int ALPHABET_SIZE = 65536;//256;

    public boolean findPattern(String t, String p, String filename)
    {
        char[] text = t.toCharArray();
        char[] pattern = p.toCharArray();
        int pos = indexOf(text, pattern);
        if (pos == -1)
        {	return false; }//        System.out.println(" No Match"); 
        // remove return false for original
        else {
            System.out.println(filename + " -- pattern found at position first at " + pos);
            return true; // remove return true for original
        }
    }

    public int indexOf(char[] text, char[] pattern) throws ArrayIndexOutOfBoundsException
    {
        if (pattern.length == 0)
            return 0;

        int charTable[] = makeCharTable(pattern);
        int offsetTable[] = makeOffsetTable(pattern);

        for (int i = pattern.length - 1, j; i < text.length;)
        {
            for (j = pattern.length - 1; pattern[j] == text[i]; --i, --j)
                if (j == 0)
                    return i;


            if( text[i] >= ALPHABET_SIZE) {
                throw new ArrayIndexOutOfBoundsException(text[i]);
            }

            i += Math.max(offsetTable[pattern.length - 1 - j], charTable[text[i]]);
        }
        return -1;
    }

    private int[] makeCharTable(char[] pattern)
    {

        int[] table = new int[ALPHABET_SIZE];
        //     for (int i = 0; i < table.length; ++i)	// put this back but not sure why necessary
        //         table[i] = pattern.length;
        for (int i = 0; i < pattern.length - 1; ++i)
            table[pattern[i]] = pattern.length - 1 - i;
        return table;
    }

    private static int[] makeOffsetTable(char[] pattern)
    {
        int[] table = new int[pattern.length];
        int lastPrefixPosition = pattern.length;
        for (int i = pattern.length - 1; i >= 0; --i)
        {
            if (isPrefix(pattern, i + 1))
                lastPrefixPosition = i + 1;
            table[pattern.length - 1 - i] = lastPrefixPosition - i + pattern.length - 1;
        }
        for (int i = 0; i < pattern.length - 1; ++i)
        {
            int slen = suffixLength(pattern, i);
            table[slen] = pattern.length - 1 - i + slen;
        }
        return table;
    }

    private static boolean isPrefix(char[] pattern, int p)
    {
        for (int i = p, j = 0; i < pattern.length; ++i, ++j)
            if (pattern[i] != pattern[j])
                return false;
        return true;
    }

    private static int suffixLength(char[] pattern, int p)
    {
        int len = 0;
        for (int i = p, j = pattern.length - 1; i >= 0 && pattern[i] == pattern[j]; --i, --j)
            len += 1;
        return len;
    }


    public static void searchDirectory(File directory, String pattern) {
        if (!directory.exists())
            return;

        File[] files = directory.listFiles();

        for (int i = 0; i < files.length; ++i) {
            File file = files[i];

            if (file.isDirectory()) {
                BoyerMoore.searchDirectory(file, pattern);
            } else {
                BoyerMoore.searchFile(file, pattern);
            }
        }
    }


    public static void searchFile (File file, String pattern) {
        String fullFilePath = file.getAbsoluteFile().toString();
        String line = null;
        BoyerMoore boyerMoore = new BoyerMoore();

        try {

            FileInputStream fileInputStream = new FileInputStream(file);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(fileInputStream));

            StringBuilder builder = new StringBuilder();
            int count = 1;

            while ((line = bufferedReader.readLine()) != null) {

                builder.append(line);
                if( count++%100 == 0) {

                    if( boyerMoore.findPattern(builder.toString(), pattern, fullFilePath) ) {


                        bufferedReader.close(); // remove to revert back, and take out ifstatement
                        // should see multiple results but it take way too long
                        fileInputStream.close();

                        return;	// remove to revert back
                    }

                    builder = new StringBuilder();
                }

                //



            }

            bufferedReader.close();
            fileInputStream.close();
        } catch (Exception e) {
            System.out.println( "Blahaha " + e.toString());
        } finally {

        }

    }

    public static void main(String[] args) {
        File file = new File("/home/jtoews/Desktop/"); //boyermooretest/");
        // File file = new File("/home/jtoews/Desktop/boyermooretest/thefile4.txt");
        String pattern = "Font";
        BoyerMoore.searchDirectory(file, pattern);
    }

}