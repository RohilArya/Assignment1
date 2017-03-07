package sample;

import java.io.File;
import java.io.IOException;
//import java.io.PrintWriter;
import java.util.*;

/**
 * Created by 100585195 on 3/4/2017.
 */
public class Train {
    public TreeMap<String,Integer> hamTree;
    public TreeMap<String,Integer> spamTree;
    public TreeMap<String, Double> HamProb;
    public TreeMap<String, Double> SpamProb;
    public TreeMap<String,Double> TotalProb;

    public int Occurence;
    public int countWord;
    public File file;
    public File file2;
    public int numFiles;

    public double TrainHam() throws IOException {
        TreeMap<String,Integer> hamTree= new TreeMap<>();
        this.hamTree=hamTree;
        processFileHam(file,hamTree);
        return 0.0;}

    public double TrainSpam() throws IOException {
        TreeMap<String,Integer>spamTree= new TreeMap<>();
        this.spamTree=spamTree;
        processFileSpam(file2,spamTree);

        return 0.0;}


    public Train(File file, File file2) throws IOException {
        this.file=file;
        this.file2=file2;
        TrainHam();
        TrainSpam();
    }

    public void processFileSpam(File file, TreeMap wordCounter) throws IOException {
        if (file.isDirectory()) {
            // for directories, recursively call
            File[] filesInDir = file.listFiles();
            numFiles = filesInDir.length;
            for (int i = 0; i < filesInDir.length; i++) {
                processFileSpam(filesInDir[i],wordCounter);
            }
            System.out.println(file);
        } else {
            // for single files, load the words and count
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String word = scanner.next();
                if (isWord(word)) {
                    if(isUniqueSpam(word,wordCounter)){
                    }
                }
            }
        }
    }

    public void processFileHam(File file, TreeMap wordCounter) throws IOException {
        if (file.isDirectory()) {
            // for directories, recursively call
            File[] filesInDir = file.listFiles();
            numFiles = filesInDir.length;
            for (int i = 0; i < filesInDir.length; i++) {
                processFileHam(filesInDir[i],wordCounter);
            }
            System.out.println(file);
        } else {
            // for single files, load the words and count
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String word = scanner.next();
                if (isWord(word)) {
                    if(isUniqueHam(word,wordCounter)){
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

    private boolean isUniqueHam(String token,TreeMap wordCounter)
    {
        String key = token;
        if(wordCounter.get(key) == null) {

            wordCounter.put(key, 1);
            return true;
        }
        else {
            int i = hamTree.get(key);
            wordCounter.put(key, (hamTree.get(key))+1);
            return false;
        }
    }
    private boolean isUniqueSpam(String token,TreeMap wordCounter)
    {
        String key = token;
        if(wordCounter.get(key) == null) {

            wordCounter.put(key, 1);
            return true;
        }
        else {
            int i = spamTree.get(key);
            wordCounter.put(key, (spamTree.get(key))+1);
            return false;
        }
    }
    private boolean isUniqueTotal(String token,TreeMap wordCounter)
    {
        String key = token;
        if(wordCounter.get(key) == null) {
            return true;
        }
        else {
            return false;
        }
    }

    public void ProbabilityHam(){
        double ProbHam = 0.0;
        TreeMap<String,Double>HamProb = new TreeMap<>();
        this.HamProb = HamProb;
        for (Map.Entry<String, Integer>entry: hamTree.entrySet())
        {
            String key = entry.getKey();
            Integer Value = entry.getValue();

            ProbHam = Value / numFiles;

            HamProb.put(key,ProbHam);

        }
    }
    public void ProbabilitySpam(){
        double ProbSpam = 0.0;
        TreeMap<String,Double>SpamProb = new TreeMap<>();
        this.SpamProb = SpamProb;
        for (Map.Entry<String, Integer>entry: spamTree.entrySet())
        {
            String key = entry.getKey();
            Integer Value = entry.getValue();

            ProbSpam = Value / numFiles;

            SpamProb.put(key,ProbSpam);

        }
    }
    public void TotalProb(){
        Double totalProb = 0.0;
        Double ValueSpamP;
        Double ValueHamP;
        TreeMap<String, Double>TotalProb = new TreeMap<>();
        this.TotalProb = TotalProb;
        for (Map.Entry<String, Double>entry:HamProb.entrySet()) {
            String key = entry.getKey();
            ValueHamP = entry.getValue();

            if (SpamProb.get(key) == null) {
                totalProb = 0.0;
            } else {
                Map.Entry<String, Double> entry1 = (Map.Entry<String, Double>) SpamProb.entrySet();
                ValueSpamP = entry1.getValue();

                totalProb = ValueSpamP / (ValueHamP + ValueSpamP);

            }
            TotalProb.put(key, totalProb);
        }
        for (Map.Entry<String, Double>entry1:SpamProb.entrySet())
        {
            String key = entry1.getKey();
            ValueSpamP = entry1.getValue();
            if (isUniqueTotal(key,TotalProb)){
                if(HamProb.get(key) == null){
                    totalProb = 1.0;
                }
                else
                {
                    Map.Entry<String, Double>entry = (Map.Entry<String, Double>) HamProb.entrySet();
                    ValueHamP = entry.getValue();

                    totalProb = ValueSpamP / (ValueHamP + ValueSpamP);

                }
                TotalProb.put(key, totalProb);
            }
        }
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
