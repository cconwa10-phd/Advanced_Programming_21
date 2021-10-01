package com.example.helloworld;

import java.util.Random;

public class Lab3_2
{
    private static Random random = new Random();

    public static char getCharFromAlphabet(char[] chars, float[] d)
    {
        double totalWeight = random.nextFloat();
        for(int i = 0; i < d.length; i++)
        {
            totalWeight = totalWeight - d[i];
            if(totalWeight <= 0)
            {
                return chars[i];
            }
        }
        return chars[chars.length - 1];
    }
    public static String generateRandomSequence(char[] alphabet, float[] weights, int length) throws Exception
    {
        double tolerance = 0.02;
        double sum = 0;
        for (double weight : weights) {
            sum = sum + weight;
        }
        if(alphabet.length != weights.length)
        {
            throw new Exception("ERROR: the array lengths of the alphabet and weigths are not equal, try again.");
        }
        else if(length <= 0)
        {
            throw new Exception("ERROR: The length is insufficient, please provide a different length");
        }

        else if(Math.abs(1 - sum) > tolerance)
        {
            throw new Exception("ERROR: Please check the weights of each value to ensure they add up close to 1, with a tolerance of 0.02");
        }
        else
        {
            StringBuilder sequence = new StringBuilder();
            for(int j = 0; j < length; j++)
            {
                sequence.append(getCharFromAlphabet(alphabet, weights));
            }
            return sequence.toString();
        }

    }
    public static void main(String[] args) throws Exception
    {
        float[] dnaWeights = { .3f, .3f, .2f, .2f };
        char[] dnaChars = { 'A', 'C', 'G', 'T' };

        // a random DNA 30 mer
        System.out.println(generateRandomSequence(dnaChars, dnaWeights,30));

        // background rate of residues from https://www.science.org/doi/abs/10.1126/science.286.5438.295
        float proteinBackground[] =
                {0.072658f, 0.024692f, 0.050007f, 0.061087f,
                        0.041774f, 0.071589f, 0.023392f, 0.052691f, 0.063923f,
                        0.089093f, 0.023150f, 0.042931f, 0.052228f, 0.039871f,
                        0.052012f, 0.073087f, 0.055606f, 0.063321f, 0.012720f,
                        0.032955f};


        char[] proteinResidues =
                new char[] { 'A', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T',
                        'V', 'W', 'Y' };

        // a random protein with 30 residues
        System.out.println(generateRandomSequence(proteinResidues, proteinBackground, 30));

    }
}
