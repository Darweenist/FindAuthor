import java.util.*;
/**
 * Represents a group of Token objects, each containing a single word. 
 * Tokens are stored in an Arraylist because accessing any given index
 * is efficient and it is easy to traverse the data structure.
 *
 * @author Dawson Chen
 * @version 3/27/19
 */
public class Phrase
{
    private ArrayList<Token> tokens;
    /**
     * Constructor for tokens of class Phrase
     */
    public Phrase()
    {
        tokens = new ArrayList();
    }

    /**
     * Adds a token to tokens
     * @param tok is the new token to be added to the phrase
     * Runs in O(1) time
     */
    public void addToken(Token tok)
    {
        tokens.add(tok);
    }
    
    /**
     * Returns a copy of tokens
     * @return a deep copy of tokens
     */
    public ArrayList<Token> getPhrase()
    {
        ArrayList<Token> tokensCopy = new ArrayList<Token>();
        for (int i = 0; i < tokens.size(); i++)
        {
            tokensCopy.add(tokens.get(i));
        }
        return tokensCopy;
    }
    
    /**
     * Returns a string form of the phrase
     * @return the phrase represented by a string
     */
    public String toString()
    {
        String ret = "";
        for (int i = 0; i < tokens.size(); i++)
        {
            ret += tokens.get(i).toString();
        }
        return ret;
    }
}
