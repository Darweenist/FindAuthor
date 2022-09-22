import java.io.*;
import java.util.*;
/**
 * Tests the efficacy of scanner and document statistics.
 *
 * @author Anu Datar
 * @author Dawson Chen
 * @version 05/17/2018
 */
public class ScannerTester
{
    /**
     * Main tester method 
     * @param  str array of String objects 
     */
    public static void main(String[] str) throws IOException
    {
        FileReader reader = new FileReader(new 
            File("./MysteryText/mystery1.txt"));
        Scanner scanner = new Scanner(reader);
        // Scanner test
        // while(scanner.hasNextToken())
        // {
            // System.out.println(scanner.nextToken());
        // }
        
        //test for document statistics
        Document doc = new Document(scanner);
        Map<String, File> map = new HashMap<String, File>();
        File[] files = (new File("./SignatureFiles/")).listFiles();
        for (int i = 0; i < files.length; i++)
        {
            map.put(files[i].getName().substring(0, 
                files[i].getName().indexOf(".stats")), files[i]);
        }
        DocumentStats docStats = new DocumentStats(doc, map);
        System.out.println(docStats.findAuthor());
    }
}
