package com.example.helloworld;

import java.util.Random;
import java.util.Scanner;

public class Lab2_Final {
    public static String[] SHORT_NAMES =
            { "A","R", "N", "D", "C", "Q", "E",
                    "G",  "H", "I", "L", "K", "M", "F",
                    "P", "S", "T", "W", "Y", "V"
            };

    public static String[] FULL_NAMES =
            {
                    "alanine","arginine", "asparagine",
                    "aspartic acid", "cysteine",
                    "glutamine",  "glutamic acid",
                    "glycine" ,"histidine","isoleucine",
                    "leucine",  "lysine", "methionine",
                    "phenylalanine", "proline",
                    "serine","threonine","tryptophan",
                    "tyrosine", "valine"
            };
    public static void main(String[] arg)
    {
        int count = 0;
        long start = System.currentTimeMillis();
        long end = start + 30*1000;
        while(System.currentTimeMillis() < end)
        {
            Random random = new Random();
            int aminoAcid = random.nextInt(20);
            Scanner aminoAnswer = new Scanner(System.in);
            System.out.println("Give the one letter code to the following Amino Acid: " + FULL_NAMES[aminoAcid]);
            String aminoLetter = aminoAnswer.nextLine().toUpperCase();
            long endQ = System.currentTimeMillis();
            if (SHORT_NAMES[aminoAcid].equals(aminoLetter)) {
                System.out.println("Correct");
                System.out.println("Time Elapsed: " + ((endQ - start) / 1000) + " out of 30 sec");
                count = count + 1;
            }
            else
            {
                System.out.println("Incorrect, total of " + count + " correct answers.");
                System.out.println("Time Elapsed: " + ((endQ - start) / 1000) + " out of 30 sec");
                break;
            }
        }
        System.out.println("Sorry, ran out of time with a total of " + count + " correct answers.");
    }
}
