/**
 * Taxonomía de tokens reconocidos por el analizador léxico.
 *
 * <p>La agrupación de constantes mantiene trazabilidad entre reglas de {@code Lexer.flex}
 * y semántica del token en fases posteriores (parser, validación, diagnóstico).
 */
public enum TokenType {
    // Palabras reservadas del lenguaje objetivo.
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

    // Identificadores y literales.
    ID,
    INT_LITERAL,
    REAL_LITERAL,
    STRING_LITERAL,

    // Operadores aritméticos, relacionales y lógicos.
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

    // Delimitadores y separadores.
    LPAREN,
    RPAREN,
    LBRACE,
    RBRACE,
    LBRACKET,
    RBRACKET,
    COMMA,
    SEMICOLON,
    DOT,

    // Tokens de control de flujo léxico.
    ERROR,
    EOF
}
