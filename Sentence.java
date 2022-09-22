import java.util.*;
/**
 * Represents a sequence of phrases, making up a sentence. 
 * These phrases are stored in a ArrayList because traversing is efficient,
 * and it is easy to access any individual element.
 *
 * @author Dawson Chen
 * @version 3/27/19
 */
public class Sentence
{
    // instance variables - replace the example below with your own
    private ArrayList<Phrase> phrases;

    /**
     * Constructor for phrases of class Sentence,
     * instantiating it as a new ArrayList.
     */
    public Sentence()
    {
        phrases = new ArrayList<Phrase>();
    }
    
    /**
     * @return an arraylist of phrases
     */
    public ArrayList<Phrase> getSentence()
    {
        return phrases;
    }
    
    /**
     * Adds a phrase to phrases
     * @param phr is the new phrase to be added to the sentence
     * Runs in O(1) time
     */
    public void addPhrase(Phrase phr)
    {
        phrases.add(phr);
    }
    
    /**
     * Returns a copy of phrases
     * @return a deep copy of phrases
     */
    public ArrayList<Phrase> getPhrases()
    {
        ArrayList<Phrase> phrasesCopy = new ArrayList<Phrase>();
        for (int i = 0; i < phrases.size(); i++)
        {
            phrasesCopy.add(phrases.get(i));
        }
        return phrasesCopy;
    }
    
    /**
     * Returns a string form of the phrase
     * @return the phrase represented by a string
     */
    public String toString()
    {
        String ret = "";
        for (int i = 0; i < phrases.size(); i++)
        {
            ret += phrases.get(i).toString();
            ret += " ";
        }
        return ret;
    }
}
