/**
 * Taxonomy of tokens recognized by the lexical analyzer.
 *
 * <p>Grouping constants this way preserves traceability between {@code Lexer.flex}
 * rules and token semantics in later phases (parsing, validation, diagnostics).
 */
public enum TokenType {
    // Reserved keywords of the target language.
    KW_IF,
    KW_ELSE,
    KW_WHILE,
    KW_FOR,
    KW_RETURN,
    KW_INT,
    KW_FLOAT,
    KW_DOUBLE,
    KW_BOOLEAN,
    KW_STRING,
    KW_CHAR,
    KW_VOID,
    KW_TRUE,
    KW_FALSE,

    // Identifiers and literals.
    ID,
    INT_LITERAL,
    REAL_LITERAL,
    STRING_LITERAL,

    // Arithmetic, relational, and logical operators.
    PLUS,
    MINUS,
    STAR,
    SLASH,
    MOD,
    ASSIGN,
    EQ,
    NEQ,
    LT,
    GT,
    LE,
    GE,
    AND,
    OR,
    NOT,
    INC,
    DEC,

    // Delimiters and separators.
    LPAREN,
    RPAREN,
    LBRACE,
    RBRACE,
    LBRACKET,
    RBRACKET,
    COMMA,
    SEMICOLON,
    DOT,

    // Lexical control-flow tokens.
    ERROR,
    EOF
}
