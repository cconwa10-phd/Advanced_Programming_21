package com.example.helloworld;
import java.io.*;
import java.util.*;
import java.util.Random;
//import org.apache.commons.math3.stat.inference.OneWayAnova;
import com.opencsv.*;
public class batch_anova_effect
{
    public static ArrayList<String[]> read_file_nist(File myFile, String indexedRows, String subString) throws IOException {
        String line = "";
        String splitBy = ",";
        BufferedReader file = new BufferedReader(new FileReader(myFile));
        ArrayList<String> header = new ArrayList<>();
        ArrayList<String[]> newFile = new ArrayList<>();
        int count = 0;
        //String[] firstLine = file.readLine().split(splitBy);
        while((line = file.readLine()) != null)
        {
            if(count == 0)
            {
                String[] headerS = line.split(splitBy);
                header.addAll(Arrays.asList(headerS));
                header.add("pValue");
                String[] headerStr = new String[header.size()];
                headerStr = header.toArray(headerStr);
                newFile.add(headerStr);
//                if(indexedRows.equalsIgnoreCase("true"))
//                {
//                    header.remove(0);
//                }
                count += 1;
            }
            else
            {
                String[] body = line.split(splitBy);
                ArrayList<String> bodyArray= (ArrayList<String>) loopRow(body, indexedRows);
                ArrayList<Double> indices = (ArrayList<Double>) findIndices(header, bodyArray, subString);
                double anovaValue = anovaTest(indices);
                if(anovaValue >= 0.3)
                {
                    bodyArray.add(String.valueOf(anovaValue));
                    String[] bodyArrayStr = new String[bodyArray.size()];
                    bodyArrayStr = bodyArray.toArray(bodyArrayStr);
                    newFile.add(bodyArrayStr);
                }
                count += 1;
            }
        }

        //System.out.println(header);
    return newFile;
    }
    private static List<String> loopRow(String[] splitRows, String indexedRows)
    {
        List<String> line = new ArrayList<>();
        line.addAll(Arrays.asList(splitRows));
//        if(indexedRows.equalsIgnoreCase("true"))
//        {
//            line.remove(0);
//        }
        return line;
    }
    private static List<Double> findIndices(List<String> header, List<String> body, String subString)
    {
        List<Double> indexBody = new ArrayList<>();
        for (int x = 0; x < header.size(); x++)
        {
            if (header.get(x).contains(subString) && !header.get(x).equals("") && !body.get(x).equals("")) {
                indexBody.add(Double.parseDouble(body.get(x)));
            }
        }
        return indexBody;
    }
    private static double anovaTest(List<Double> indexBody)
    {
        //using random float as place holder until I figure ANOVA out
        Random random = new Random();
        return random.nextFloat();
    }
    public static void newBetweenBatchBlank(String filePath, ArrayList<String[]> cleanedArray) throws IOException
    {
        File newFile = new File(filePath);
        FileWriter outputFile = new FileWriter(newFile);
        CSVWriter writer = new CSVWriter(outputFile);
        writer.writeAll(cleanedArray);

        writer.close();
    }

    public static void main(String[] args) throws Exception
    {
        String path = "/Users/cconwa10/Desktop/";
        ArrayList<String[]> cleanedFile = read_file_nist(new File(path + "final_peaks_new.csv"), "True", "NIST");
        newBetweenBatchBlank(path + "final_peaks_cleaned.csv", cleanedFile);
    }
}
