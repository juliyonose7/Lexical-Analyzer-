/**
 * Represents a lexical unit produced by the scanner.
 *
 * <p>The token encapsulates:
 * <ul>
 *   <li>syntactic type ({@link TokenType}),</li>
 *   <li>exact recognized lexeme,</li>
 *   <li>source position in terms of line and column (1-based).</li>
 * </ul>
 */
public class Token {
    private final TokenType type;
    private final String lexeme;
    private final int line;
    private final int column;

    /**
        * Builds an immutable token.
     *
        * @param type recognized token type.
        * @param lexeme textual representation captured from input.
        * @param line source line (1-based).
        * @param column source column (1-based).
     */
    public Token(TokenType type, String lexeme, int line, int column) {
        this.type = type;
        this.lexeme = lexeme;
        this.line = line;
        this.column = column;
    }

    /** @return lexical token type. */
    public TokenType getType() {
        return type;
    }

    /** @return original recognized lexeme. */
    public String getLexeme() {
        return lexeme;
    }

    /** @return token source line (1-based). */
    public int getLine() {
        return line;
    }

    /** @return token source column (1-based). */
    public int getColumn() {
        return column;
    }

    @Override
    public String toString() {
        return String.format("Token{type=%s, lexeme='%s', line=%d, column=%d}", type, lexeme, line, column);
    }
}
