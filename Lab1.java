package com.example.helloworld;
import java.util.Random;

public class Lab1
{
    public static void main(String[] args) {
        Random random = new Random(); //casts "random" variable from Random package
        int stringLength = 1000; //defines string length of 1000 codons
        String nucleotide = ""; //String New for collecting the 3-mers
        String nucleotideNew = "";
        //Keeping track of percentages
        int aCount = 0;
        int tCount = 0;
        int cCount = 0;
        int gCount = 0;

        int aCountNew = 0;
        int tCountNew = 0;
        int cCountNew = 0;
        int gCountNew = 0;

        //Keeping track of AAA 3-mer
        int aaaCount = 0;
        //(1/64 chance of seeing "AAA")
        int aaaCountNew = 0;
        //(0.001728 chance of seeing "AAA")
        //Keeping track of nucleotide loop
        int nucleotideCount = 0;
        int nucleotideCountNew = 0;

        for (int x = 0; x < stringLength; x++) //for loop that initializes at x=0,checks x is within 100 length, and after each increment change
        {
            String codon = ""; //String New for collecting a single 3-mer
            String codonNewPercentages = "";
            //for (int x1 = 0; x1 < codonLength; x1++)
            while(nucleotideCount < 3) //loops through until three codons have been selected
            {
                int dnaSEQ = random.nextInt(4); //chooses integer 0-3
                if (dnaSEQ == 0 && aCount < 750)
                {
                    codon = codon + "A";//if 0 then append A to the string
                    aCount = aCount + 1;
                    nucleotideCount = nucleotideCount + 1;
                }
                else if (dnaSEQ == 1 && tCount < 750)
                {
                    codon = codon + "T";//if 1 then append T to the string
                    tCount = tCount + 1;
                    nucleotideCount = nucleotideCount + 1;
                }
                else if (dnaSEQ == 2 && cCount < 750)
                {
                    codon = codon + "C";//if 2 then append C to the string
                    cCount = cCount + 1;
                    nucleotideCount = nucleotideCount + 1;
                }
                else if (dnaSEQ == 3 && gCount < 750)
                {
                    codon = codon + "G";//if 3 then append G to the string
                    gCount = gCount + 1;
                    nucleotideCount = nucleotideCount + 1;
                }
            }
            if( codon.equals("AAA") )
            {
                aaaCount = aaaCount + 1; //counts the number of "aaa" seen
            }
            nucleotide = nucleotide + codon; //adds the codons to the overall nucleotide list
            nucleotideCount = nucleotideCount - 3; //empties out the nucleotide count list

            while(nucleotideCountNew < 3) //loops through until three codons have been selected
            {
                int dnaSEQ = random.nextInt(4); //chooses integer 0-3
                if (dnaSEQ == 0 && aCountNew < 360)
                {
                    codonNewPercentages = codonNewPercentages + "A";//if 0 then append A to the string
                    aCountNew = aCountNew + 1;
                    nucleotideCountNew = nucleotideCountNew + 1;
                }
                else if (dnaSEQ == 1 && tCountNew < 330)
                {
                    codonNewPercentages = codonNewPercentages + "T";//if 1 then append T to the string
                    tCountNew = tCountNew + 1;
                    nucleotideCountNew = nucleotideCountNew + 1;
                }
                else if (dnaSEQ == 2 && cCountNew < 1140)
                {
                    codonNewPercentages = codonNewPercentages + "C";//if 2 then append C to the string
                    cCountNew = cCountNew + 1;
                    nucleotideCountNew = nucleotideCountNew + 1;
                }
                else if (dnaSEQ == 3 && gCountNew < 1170)
                {
                    codonNewPercentages = codonNewPercentages + "G";//if 3 then append G to the string
                    gCountNew = gCountNew + 1;
                    nucleotideCountNew = nucleotideCountNew + 1;
                }
            }
            if( codonNewPercentages.equals("AAA") )
            {
                aaaCountNew = aaaCountNew + 1; //counts the number of "aaa" seen
            }
            nucleotideNew = nucleotideNew + codonNewPercentages; //adds the codons to the overall nucleotide list
            nucleotideCountNew = nucleotideCountNew - 3; //empties out the nucleotide count list
        }
        System.out.println("25% frequency rates");
        System.out.println(nucleotide);//prints out 3000 char sequence
        System.out.println("Count of A(25%):" + aCount);
        System.out.println("Count of T(25%):" + tCount);
        System.out.println("Count of C(25%):" + cCount);
        System.out.println("Count of G(25%):" + gCount);
        System.out.println("Count of AAA (~0.0156 or 15.6):" + aaaCount);

        System.out.println("Changes of frequency rates");
        System.out.println(nucleotide);//prints out 3000 char sequence
        System.out.println("Count of A(12%):" + aCountNew);
        System.out.println("Count of T(38%):" + tCountNew);
        System.out.println("Count of C(39%):" + cCountNew);
        System.out.println("Count of G(11%):" + gCountNew);
        System.out.println("Count of AAA (~0.001728 or 1.7):" + aaaCountNew);

    }

}
