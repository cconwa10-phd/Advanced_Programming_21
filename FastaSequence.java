package com.example.helloworld;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FastaSequence {
    private String fseq;

    public FastaSequence(String fseq) {
        this.fseq = fseq;
    }
    public String getHeader()
    {
        return fseq.split("\n")[0];
    }
    public String getSequence()
    {
        return fseq.split("\n")[1];
    }
    public float getGCRatio()
    {
        float GC = 0;
        String seq = fseq.split("\n")[1];
        for(int x = 0; x < seq.length(); x++)
        {
            if (seq.charAt(x) == 'G' || seq.charAt(x) == 'C')
            {
                GC += 1;
            }
        }
        return (GC/seq.length())*100;
    }
    public String getCounts(char letter)
    {
        String seq = fseq.split("\n")[1];
        int count = 0;
        for(int x = 0; x < seq.length(); x++)
        {
            if(seq.charAt(x) == letter)
            {
                count += 1;
            }
        }
        return String.valueOf(count);
    }

    public static List<FastaSequence> readFastaFile(String filepath) throws Exception
    {
        List<FastaSequence> fasta = new ArrayList<>();
        BufferedReader input = new BufferedReader(new FileReader(filepath));
        StringBuilder fastaEntry = new StringBuilder();
        String line = input.readLine();

        if( line == null)
        {
            throw new Exception(filepath + " is not a valid file");
        }
        if( line.charAt(0) != '>')
        {
            throw new Exception(filepath + " is not a valid FASTA File");
        }
        else
        {
            fastaEntry.append(line.split(">")[1]);
            fastaEntry.append("\n");
            for(line = input.readLine().trim(); line != null; line = input.readLine())
            {
                if(line.length()>0 && line.charAt(0) == '>')
                {
                    if(fastaEntry != null)
                    {
                        FastaSequence FastaEntry = new FastaSequence(fastaEntry.toString());
                        fasta.add(FastaEntry);
                        fastaEntry = new StringBuilder();
                    }
                    fastaEntry.append(line.split(">")[1]);
                    fastaEntry.append("\n");

                }
                else
                {
                    fastaEntry.append(line);
                }
            }
            FastaSequence FastaEntry = new FastaSequence(fastaEntry.toString());
            fasta.add(FastaEntry);
        }
        return fasta;

    }

    public static void writeTableSummary(List<FastaSequence> fastaList, File myFile) throws Exception
    {
        StringBuilder newFile = new StringBuilder();
        newFile.append("sequenceID\tnumA\tnumC\tnumG\tnumT\tsequence\n");
        for (FastaSequence fs : fastaList)
        {
            newFile.append(fs.getHeader() + "\t");
            newFile.append(fs.getCounts('A') + "\t");
            newFile.append(fs.getCounts('C') + "\t");
            newFile.append(fs.getCounts('G') + "\t");
            newFile.append(fs.getCounts('T') + "\t");
            newFile.append(fs.getSequence() + "\n");
        }
        BufferedWriter textSeq = new BufferedWriter(new FileWriter(myFile));
        textSeq.write(newFile.toString());
        textSeq.close();
    }

    public static void main(String[] args) throws Exception {
        List<FastaSequence> fastaList =
                FastaSequence.readFastaFile(
                        "/Users/cconwa10/Documents/fasta_test.txt");

        for (FastaSequence fs : fastaList) {
            System.out.println(fs.getHeader());
            System.out.println(fs.getSequence());
            System.out.println(fs.getGCRatio());
        }

        File myFile = new File("/Users/cconwa10/Documents/fasta_test_table.txt");

        writeTableSummary(fastaList, myFile);
    }



}
