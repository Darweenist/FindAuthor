import java.io.IOException;
import java.io.Reader;

/**
 * A Scanner is responsible for reading an input stream, one character at a
 * time, and separating the input into tokens.  A token is defined as:
 *  1. A 'word' which is defined as a non-empty sequence of characters that 
 *     begins with an alpha character and then consists of alpha characters, 
 *     numbers, the single quote character "'", or the hyphen character "-". 
 *  2. An 'end-of-sentence' delimiter defined as any one of the characters 
 *     ".", "?", "!".
 *  3. An end-of-file token which is returned when the scanner is asked for a
 *     token and the input is at the end-of-file.
 *  4. A phrase separator which consists of one of the characters ",",":" or
 *     ";".
 *  5. A digit.
 *  6. Any other character not defined above.
 * @author Mr. Page
 * @author Dawson Chen
 * @version 2/28/19
 */
public class Scanner
{
    private Reader in;
    private String currentChar;
    private boolean endOfFile;
    // define symbolic constants for each type of token
    /**
     * Enum defining all possible tokens 
     */
    public static enum TOKEN_TYPE
    {
        /**
         * Words in the english dictionary, period/commas, digits, etc.
         */
        WORD, END_OF_SENTENCE, END_OF_FILE, END_OF_PHRASE, DIGIT, UNKNOWN
    };
    /**
     * Constructor for Scanner objects.  The Reader object should be one of
     *  1. A StringReader
     *  2. A BufferedReader wrapped around an InputStream
     *  3. A BufferedReader wrapped around a FileReader
     *  The instance field for the Reader is initialized to the input parameter,
     *  and the endOfFile indicator is set to false.  The currentChar field is
     *  initialized by the getNextChar method.
     * @param in is the reader object supplied by the program constructing
     *        this Scanner object.
     */
    public Scanner(Reader in)
    {
        this.in = in;
        endOfFile = false;
        getNextChar();
    }

    /**
     * The getNextChar method attempts to get the next character from the input
     * stream.  It sets the endOfFile flag true if the end of file is reached on
     * the input stream.  Otherwise, it reads the next character from the stream
     * and converts it to a Java String object.
     * postcondition: The input stream is advanced one character if it is not at
     * end of file and the currentChar instance field is set to the String 
     * representation of the character read from the input stream.  The flag
     * endOfFile is set true if the input stream is exhausted.
     */
    private void getNextChar()
    {
        try
        {
            int inp = in.read();
            if (inp == -1) 
                endOfFile = true;
            else 
                currentChar = "" + (char) inp;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            System.exit(-1);
        }
    }

    /**
     * Goes to the next character in the file by "eating" the current character
     * @param obj is what is tested to be current character. If it is, then
     * it is eaten.
     */
    private void eat(String obj)
    {
        if (obj.equals(currentChar))
        {
            getNextChar();
        }
        else
        {
            System.out.println();
            throw new IllegalArgumentException("Characters don't match!"
                + " CurrentChar: " + currentChar + ", Obj: " + obj);
        }
    }

    /**
     * Gets whether s is a letter
     * @param s is the character to be tested
     * @return true if s is a letter;
     * otherwise, false
     */
    private boolean isLetter(String s)
    {
        return s.compareTo("A") >= 0 && s.compareTo("Z") <= 0 ||
        s.compareTo("a") >= 0 && s.compareTo("z") <= 0;
    }

    /**
     * Gets whether s is a digit
     * @param s is the character to be tested
     * @return true if s is a digit;
     * otherwise, false
     */
    private boolean isDigit(String s)
    {
        return s.compareTo("0") >= 0 && s.compareTo("9") <= 0;
    }

    /**
     * Gets whether s is none of the other listed characters
     * @param s is the character to be tested
     * @return true if s is none of the other listed characters
     */
    private boolean isSpecial(String s)
    {
        return s.equals("'") || s.equals("-");
    }

    /**
     * Gets whether s is a colon, comma, or semicolon, which terminates phrases
     * @param s is the character to be tested
     * @return true if s is a phrase terminator listed above
     */
    private boolean isPhraseTerminator(String s)
    {
        return s.equals(":") || s.equals(",") || s.equals(";");
    }

    /**
     * Gets whether s is a period, question mark, or exclamation point,
     * which terminates sentences
     * @param s is the character to be tested
     * @return true if s is a sentence terminator listed above
     */
    private boolean isSentenceTerminator(String s)
    {
        return s.equals(".") || s.equals("?") || s.equals("!");
    }

    /**
     * Gets whether s is a white space like a space, next line, or tab
     * @param s is the character to be tested
     * @return true if s is a white space listed above
     */
    private boolean isWhiteSpace(String s)
    {
        return s.equals(" ") || s.equals("\t") || s.equals("\n")
            || s.equals("\r");
    }

    /**
     * Gets whether there are more tokens in the input stream.
     * @return true if there are more tokens in the input stream. 
     * There are more tokens if the input stream is not at end-of-file.
     */
    public boolean hasNextToken()
    {
        return !endOfFile;
    }

    /**
     * Gets the next Token in the file
     * @return the next Token
     */
    public Token nextToken()
    {
        while (isWhiteSpace(currentChar))
        {
            eat(currentChar);
            if (endOfFile)
            {
                return new Token(TOKEN_TYPE.END_OF_FILE, " ");
            }
        }
        String currentLowerCase = currentChar.toLowerCase();
        if (endOfFile)
        {
            return new Token(TOKEN_TYPE.END_OF_FILE, " ");
        }
        else if (isSentenceTerminator(currentLowerCase))
        {
            String temp = currentLowerCase;
            eat(currentChar);
            return new Token(TOKEN_TYPE.END_OF_SENTENCE, temp);
        }
        else if (isPhraseTerminator(currentLowerCase))
        {
            String temp = currentLowerCase;
            eat(currentChar);
            return new Token(TOKEN_TYPE.END_OF_PHRASE, temp);
        }
        else if (isDigit(currentLowerCase))
        {
            String temp = currentLowerCase;
            eat(currentChar);
            return new Token(TOKEN_TYPE.DIGIT, temp);
        }
        else if (isSpecial(currentLowerCase))
        {
            eat(currentChar);
            return new Token(TOKEN_TYPE.UNKNOWN, currentLowerCase);
        }
        else if (isLetter(currentLowerCase))
        {
            String temp = currentLowerCase;
            while (hasNextToken())
            {
                eat(currentChar);
                currentLowerCase = currentChar.toLowerCase();
                if (isDigit(currentLowerCase) || isLetter(currentLowerCase)
                    || isSpecial(currentLowerCase))
                {
                    temp += currentLowerCase;
                }
                else
                {
                    return new Token(TOKEN_TYPE.WORD, temp);
                }
            }
            return new Token(TOKEN_TYPE.WORD, temp);
        }
        eat(currentChar);
        return nextToken();
    }
}
