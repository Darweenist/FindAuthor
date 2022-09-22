/**
 * The Token class represents a token in a file. There are many different types
 * of tokens and they all have a value and type instance field. They can be used
 * to form phrases and sentences that make up the file.
 * @version 2/28/19
 * @author Dawson Chen
 */
public class Token {
    private String value;
    private Scanner.TOKEN_TYPE type;
    /**
     * Constructor for Token class. Sets type and value to 
     * 2 inputs
     * @param type the type of token
     * @param value the string value of the token
     */
    public Token(Scanner.TOKEN_TYPE type, String value)
    {
        this.value = value;
        this.type = type;
    }

    /**
     * Accessor method to get the value of the token
     * @return value
     */
    public String getValue()
    {
        return value;
    }

    /**
     * Converts the Token to a string in the form of
     * type: value
     * @return the Token as a string
     * @override
     */
    public String toString()
    {
        return type.toString() + ": " + value;
    }

    /**
     * Gets the type of the token
     * @return type
     */
    public Scanner.TOKEN_TYPE getType()
    {
        return type;
    }

    /**
     * Determines whether this Tokens is equal to the Token
     * in the parameter
     * @param otherToken is the token to compare this to
     * @return true if the 2 tokens have the same value
     * otherwise false
     * @override
     */
    public boolean equals(Token otherToken)
    {
        return this.value.equalsIgnoreCase(otherToken.getValue()) &&
            this.type.equals(otherToken.getType());
    }
}