import java.util.*;
import java.io.*;
/**
 * DocumentStats computes which author a given doc corresponds to
 *
 * @author Dawson Chen
 * @version 5/17/19
 */
public class DocumentStats
{
    private Document document;
    private double[] weights = {0, 11, 33, 50, 0.4, 4};
    private double[] docStats;
    private Map<String, List<Double>> authors;
    /**
     * Constructor for objects of class DocumentStats
     * @param doc is the document to evaluate
     * @param f is the map of <file name, File object>
     */
    public DocumentStats(Document doc, Map<String, File> f) throws IOException
    {
        //loop thru and put in files
        document = doc;
        docStats = new double[6];
        authors = loadAuthorStats(f);
        //document.parseDocument();
    }

    /**
     * Loads the statistics of all the given authors
     * @param files is the map of files and their file names to be loaded
     * @return a map of <author name, their statistics>
     */
    public Map<String, List<Double>> loadAuthorStats(Map<String, File> files) 
        throws IOException
    {
        Map<String, List<Double>> stats = new HashMap<String, List<Double>>();
        Reader read = null;
        for (String str : files.keySet())
        {
            read = new FileReader(files.get(str));
            int index = read.read();
            List<Double> statsList = new ArrayList<Double>();
            String in = "";
            while (index != -1)
            {
                in += (char)index;
                index = read.read();
            }
            in = in.substring(in.indexOf("\n") + 1);
            String[] names = new String[5];
            int counter = 0;
            while (counter < 5)
            {
                names[counter] = in.substring(0, in.indexOf("\n"));
                counter++;
                in = in.substring(in.indexOf("\n") + 1);
            }
            for (int i = 0; i < names.length; i++)
            {
                statsList.add(Double.valueOf(names[i]));
            }
            stats.put(str, statsList);
        }
        return stats;
    }

    /**
     * Computes the statistics of the document
     */
    public void computeStats()
    {
        int numSent = 0;
        int numPhrase = 0;
        int numWords = 0;
        int wordLenSum = 0;
        List<String> oneTimers = new ArrayList<String>();
        Set<String> set = new HashSet<String>();
        for (Sentence sent : document.getDocument())
        {
            numSent++;
            for (Phrase phrase : sent.getPhrases())
            {
                numPhrase++;
                for (Token tok : phrase.getPhrase())
                {
                    if (tok.getType().equals(Scanner.TOKEN_TYPE.WORD))
                    {
                        numWords++;
                        wordLenSum += tok.getValue().length();
                        set.add(tok.getValue());
                        if (!oneTimers.contains(tok.getValue()))
                            oneTimers.add(tok.getValue());
                        else
                            oneTimers.remove(tok.getValue());
                    }
                }
            }
        }
        docStats[1] = (double)wordLenSum / numWords;
        docStats[2] = (double)set.size() / numWords;
        docStats[3] = (double)oneTimers.size() / numWords;
        // System.out.println("Num sentences: " + numSent);
        // System.out.println("Num words: " + numWords);
        // System.out.println("Num phrases: " + numPhrase);
        docStats[4] = (double)numWords / numSent;
        docStats[5] = (double)numPhrase / numSent;
        for (int i = 0; i < docStats.length; i++)
            System.out.println(docStats[i]);
    }

    /**
     * Return the author of the selected document
     * @return the name of the author
     */
    public String findAuthor()
    {
        computeStats();
        Map<String, Double> diffs = new HashMap<String, Double>();
        for (String author : authors.keySet())
        {
            //Weights : 11, 33, 50, 0.4, 4
            double sumDiff = 0;
            for (int i = 1; i <= 5; i++)
            {
                sumDiff += weights[i] * Math.abs(docStats[i] 
                    - authors.get(author).get(i - 1));
            }
            //System.out.println(author + ": " + sumDiff);
            diffs.put(author, sumDiff);
        }
        double min = Double.MAX_VALUE;
        String minAuthor = "";
        for (String author : diffs.keySet())
        {
            if (diffs.get(author) < min)
            {
                min = diffs.get(author);
                minAuthor = author;
            }
        }
        return minAuthor;
    }
}
