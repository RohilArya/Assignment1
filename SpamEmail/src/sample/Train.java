package sample;

import java.io.File;
import java.io.IOException;
//import java.io.PrintWriter;
import java.util.*;

/**
 * Created by 100585195 on 3/4/2017.
 */
public class Train {
    public Map<String,Integer> hamTree;
    public Map<String,Integer> spamTree;
    public Map<String, Double> HamProb;
    public Map<String, Double> SpamProb;
    public TreeMap<String,Double> TotalProb;


    public File file;
    public File file2;
    public int numFiles;
    public int numFiles2;

    public Train(File file, File file2) throws IOException {
        this.file=file;
        this.file2=file2;
        TrainHam();
        TrainSpam();
        System.out.println("Probability Map");
        System.out.print(TotalProb());

    }

    public void TrainHam() throws IOException {
        hamTree=new TreeMap<>();
        processFileHam(file);
        System.out.println("HAM TREE:");
        System.out.print(hamTree);
        System.out.println("\nHam Frequency:");
        System.out.println(ProbabilityHam());

        //System.out.print(HamProb);

    }

    public void TrainSpam() throws IOException {
        spamTree=new TreeMap<>();
        processFileSpam(file2);
        System.out.println("SPAM TREE:");
        System.out.print(spamTree);
        System.out.println("\nSpam Frequency:");
        System.out.println(ProbabilitySpam());

    }




    public void processFileSpam(File file) throws IOException {
        if (file.isDirectory()) {
            // for directories, recursively call
            File[] filesInDir = file.listFiles();
            numFiles2 = filesInDir.length;
            for (int i = 0; i < filesInDir.length; i++) {
                processFileSpam(filesInDir[i]);
            }

        } else {
            // for single files, load the words and count
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String word = scanner.next();
                if (isWord(word)) {
                    if(isUniqueSpam(word)){
                    }
                }
            }
        }
    }

    public void processFileHam(File file) throws IOException {
        //System.out.println("processFile running");
        if (file.isDirectory()) {
            // for directories, recursively call
            File[] filesInDir = file.listFiles();
            numFiles = filesInDir.length;
            for (int i = 0; i < filesInDir.length; i++) {
                processFileHam(filesInDir[i]);
            }
        } else {
            // for single files, load the words and count
            Scanner scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String word = scanner.next();
                if (isWord(word)) {
                    if(isUniqueHam(word)){
                    }
                }
            }
        }

    }



    private boolean isWord(String token) {
        String pattern = "^[a-zA-Z]*$";
        if (token.matches(pattern)) {
            return true;
        } else {
            return false;
        }
    }

    private boolean isUniqueHam(String token)
    {
        String key = token;
        if(hamTree.get(key) == null) {

            hamTree.put(key, 1);
            return true;
        }
        else {

            hamTree.put(key, (hamTree.get(key))+1);
            return false;
        }
    }
    private boolean isUniqueSpam(String token)
    {
        String key = token;
        if(spamTree.get(key) == null) {

            spamTree.put(key, 1);
            return true;
        }
        else {

            spamTree.put(key, (spamTree.get(key))+1);
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

    public TreeMap<String,Double> ProbabilityHam(){
        double ProbHam = 0.0;
        TreeMap<String,Double>HamProb=new TreeMap<>();

        for (Map.Entry<String, Integer>entry: hamTree.entrySet())
        {
            String key = entry.getKey();
            double Value = entry.getValue();
            ProbHam = (Value / numFiles);

            HamProb.put(key,ProbHam);

        }
        return HamProb;
    }
    public TreeMap<String, Double> ProbabilitySpam(){
        double ProbSpam = 0.0;
        TreeMap<String,Double>SpamProb=new TreeMap<>();

        for (Map.Entry<String, Integer>entry: spamTree.entrySet())
        {
            String key = entry.getKey();
            double Value = entry.getValue();
            ProbSpam = (Value / numFiles2);

            SpamProb.put(key,ProbSpam);

        }
        return SpamProb;
    }
    public TreeMap<String,Double> TotalProb(){
        double totalProb = 0.0;
        double ValueSpamP;
        HamProb = ProbabilityHam();
        SpamProb = ProbabilitySpam();
        TreeMap<String,Double>TotalProb = new TreeMap<>();
        for (Map.Entry<String, Double>entry:HamProb.entrySet()) {
            String key = entry.getKey();
            double ValueHamP = entry.getValue();

            if (SpamProb.get(key) == null) {
                totalProb = 0.0;
            }
            else
            {
                ValueSpamP = SpamProb.get(key);
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
                    double ValueHamP = entry.getValue();

                    totalProb = ValueSpamP / (ValueHamP + ValueSpamP);

                }
                TotalProb.put(key, totalProb);
            }
        }
        return TotalProb;
    }
}


