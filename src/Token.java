/**
 * Representa una unidad léxica producida por el escáner.
 *
 * <p>El token encapsula:
 * <ul>
 *   <li>tipo sintáctico ({@link TokenType}),</li>
 *   <li>lexema exacto reconocido,</li>
 *   <li>posición de origen en términos de línea y columna (1-based).</li>
 * </ul>
 */
public class Token {
    private final TokenType type;
    private final String lexeme;
    private final int line;
    private final int column;

    /**
     * Construye un token inmutable.
     *
     * @param type tipo de token reconocido.
     * @param lexeme representación textual capturada del input.
     * @param line línea de origen (base 1).
     * @param column columna de origen (base 1).
     */
    public Token(TokenType type, String lexeme, int line, int column) {
        this.type = type;
        this.lexeme = lexeme;
        this.line = line;
        this.column = column;
    }

    /** @return tipo léxico del token. */
    public TokenType getType() {
        return type;
    }

    /** @return lexema original reconocido. */
    public String getLexeme() {
        return lexeme;
    }

    /** @return línea de origen del token (base 1). */
    public int getLine() {
        return line;
    }

    /** @return columna de origen del token (base 1). */
    public int getColumn() {
        return column;
    }

    @Override
    public String toString() {
        return String.format("Token{type=%s, lexeme='%s', line=%d, column=%d}", type, lexeme, line, column);
    }
}
