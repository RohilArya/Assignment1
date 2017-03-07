package sample;

import java.io.File;
import java.io.IOException;
//import java.io.PrintWriter;
import java.util.*;

/**
 * Created by 100585195 on 3/4/2017.
 */
public class Train {
    public Map<String, Integer> wordCounter;
    public int Occurence;
    public int countWord;
    public File file;
    public File file2;
    public int numFiles;

    public double TrainHam() throws IOException {
        processFile(file);
        return 0.0;}

    public double TrainSpam() throws IOException {
        processFile(file2);

        return 0.0;}


    public Train(File file, File file2) {
        file = this.file;
        file2 = this.file2;

        wordCounter = new TreeMap<>();
    }

    public void processFile(File file) throws IOException {
        if (file.isDirectory()) {
            // for directories, recursively call
            File[] filesInDir = file.listFiles();
            numFiles = filesInDir.length;
            for (int i = 0; i < filesInDir.length; i++) {
                processFile(filesInDir[i]);
            }
            System.out.println(file);
        } else {
            // for single files, load the words and count
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String word = scanner.next();
                if (isWord(word)) {
                    if(isUnique(word)){
                    }
                }
            }
        }
    }

   /* private int countWord(String word) {
        if (wordCounter.containsKey(word)) {
            // increment the count
            int oldCount = wordCounter.get(word);
            wordCounter.put(word, oldCount + 1);
        } else {
            // add the word with count of 1
            wordCounter.put(word, 1);
        }
        return
    } */

    private boolean isWord(String token) {
        String pattern = "^[a-zA-Z]*$";
        if (token.matches(pattern)) {
            return true;
        } else {
            return false;
        }
    }
    private boolean isUnique(String token)
    {
        String key = token;
        if(wordCounter.get(key) == null) {

            wordCounter.put(key, 1);
            return true;
        }
        else {

            wordCounter.put(key, (wordCounter.get(key))+1);
            return false;
        }
    }
    public double Probability(){
        double Prob = 0.0;
        for (Map.Entry<String, Integer>entry: wordCounter.entrySet())
        {
            String key = entry.getKey();
            Integer Value = entry.getValue();

            Prob = Value / numFiles;


        }
        return Prob;
    }

}

    /*public void printWordCounts(int minCount, File outFile) throws IOException {
        if (!outFile.exists() || outFile.canWrite()) {
            PrintWriter fout = new PrintWriter(outFile);

            Set<String> keys = wordCounter.keySet();
            Iterator<String> keyIterator = keys.iterator();
            while (keyIterator.hasNext()) {
                String key = keyIterator.next();
                int count = wordCounter.get(key);

                if (count >= minCount) {
                    fout.println("'" + key + "' -> '" + count + "'");
                }
            }

            fout.close();
        }
    }

    /*public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Usage: java WordCounter <input_dir> <output_file>");
            System.exit(0);
        }

        Train wordCounter = new Train();
        File inputDir = new File(args[0]);

        try {
            if (inputDir.exists()) {
                // read the file
                wordCounter.processFile(inputDir);

                // handle the output
                wordCounter.printWordCounts(2, new File(args[1]));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
} */
