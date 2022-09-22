import java.util.*;
/**
 * Creates a new document composed of sentences
 * 
 * @author Dawson Chen
 * @version 3.25.19
 */
public class Document
{
    private Token token;
    private List<Sentence> document;
    private Phrase currPhrase;
    private Scanner scanner;
    private boolean start;
    /**
     * Take scanner and generate document from given text
     * @param sc the scanner to use to generate the document.
     */
    public Document(Scanner sc)
    {
        token = sc.nextToken();
        document = new ArrayList<Sentence>();
        scanner = sc;
        parseDocument();
        start = true;
    }

    /**
     * Returns a list of sentences 
     * @return a list of sentences that compose the document
     */
    public List<Sentence> getDocument()
    {
        return document;
    }

    /**
     * Gets the nextToken
     * @return the next Token
     */
    public Token getNextToken()
    {
        return scanner.nextToken();
    }

    /**
     * Gets the next token if the given token is the same as the current one
     * @param t the Token that is currently on
     */
    public void eat(Token t)
    {
        if (t == null)
            t = new Token(token.getType(), token.getValue());
        if (t.equals(token))
            token = getNextToken();
    }

    /**
     * Parses and gets a list of tokens into the current phrase
     */
    public void parsePhrase()
    {
        if (currPhrase == null)
            currPhrase = new Phrase();
        while (!token.getType().equals(Scanner.TOKEN_TYPE.END_OF_SENTENCE) &&
            !token.getType().equals(Scanner.TOKEN_TYPE.END_OF_FILE) &&
            !token.getType().equals(Scanner.TOKEN_TYPE.END_OF_PHRASE))
        {
            //System.out.println("parsing phrase");
            if (token.getType().equals(Scanner.TOKEN_TYPE.WORD))
            {
                currPhrase.addToken(token);
            }
            eat(token);
        }
        // else if (t.getType() == Scanner.TOKEN_TYPE.END_OF_SENTENCE ||
        // t.getType() == Scanner.TOKEN_TYPE.END_OF_FILE ||
        // t.getType() == Scanner.TOKEN_TYPE.END_OF_PHRASE)
        // {
            // return;
        // }
    }

    /**
     * Parses and gets a list of phrases into a sentence
     * @return Sentence parses the sentence
     */
    public Sentence parseSentence()
    {
        Sentence ret = new Sentence();
        while (!token.getType().equals(Scanner.TOKEN_TYPE.END_OF_FILE) &&
            !token.getType().equals(Scanner.TOKEN_TYPE.END_OF_SENTENCE))
        {
            //System.out.println("parsing sentence");
            parsePhrase();
            ret.addPhrase(currPhrase);
            currPhrase = new Phrase();
            if (token.getType().equals(Scanner.TOKEN_TYPE.END_OF_PHRASE))
                eat(token);
        }
        return ret;
    }

    /**
     * Parses the entire document into a list of sentences
     */
    public void parseDocument()
    {
        while (!(token.getType().equals(Scanner.TOKEN_TYPE.END_OF_FILE)))
        {
            Sentence sent = parseSentence();
            document.add(sent);
            if (token.getType().equals(Scanner.TOKEN_TYPE.END_OF_SENTENCE))
                eat(token);
        }
    }
}