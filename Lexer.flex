%%
/*
 * Global JFlex configuration:
 * - %line / %column enables position tracking for diagnostics.
 * - %type Token declares that each rule returns a domain Token object.
 * - %state COMMENT defines a lexical state for block comments.
 */
%public
%class Lexer
%unicode
%line
%column
%type Token
%function nextToken
%state COMMENT
%{
    /*
        * Token factories to centralize creation and normalize position handling.
        * Line/column are exposed as 1-based indexes for human-friendly output.
     */
    private Token token(TokenType type) {
        return new Token(type, yytext(), yyline + 1, yycolumn + 1);
    }

    private Token token(TokenType type, String lexeme) {
        return new Token(type, lexeme, yyline + 1, yycolumn + 1);
    }
%}

/* Reusable macros: improve readability and avoid regex duplication. */
LineTerminator = \r|\n|\r\n
WhiteSpace = {LineTerminator}|[ \t\f]
Digit = [0-9]
Letter = [A-Za-z_ÁÉÍÓÚáéíóúÑñ]
Identifier = {Letter}({Letter}|{Digit})*
Integer = 0|[1-9]{Digit}*
Real = {Digit}+"."{Digit}+([eE][+-]?{Digit}+)?
StringChar = [^\"\\\n\r]
EscapeSeq = \\[btnrf\"\\]
StringLiteral = \"({StringChar}|{EscapeSeq})*\"

%%

/* Initial state: recognizes source-language tokens. */
<YYINITIAL> {
    {WhiteSpace}+         { /* Ignore whitespace */ }

    /* Comment start: explicit transition between lexical states. */
    "/*"                 { yybegin(COMMENT); }
    "//"[^\r\n]*        { /* Ignore line comment */ }

    /* Reserved words (priority before Identifier). */
    "if"                 { return token(TokenType.KW_IF); }
    "else"               { return token(TokenType.KW_ELSE); }
    "while"              { return token(TokenType.KW_WHILE); }
    "for"                { return token(TokenType.KW_FOR); }
    "return"             { return token(TokenType.KW_RETURN); }
    "int"                { return token(TokenType.KW_INT); }
    "float"              { return token(TokenType.KW_FLOAT); }
    "double"             { return token(TokenType.KW_DOUBLE); }
    "boolean"            { return token(TokenType.KW_BOOLEAN); }
    "String"             { return token(TokenType.KW_STRING); }
    "char"               { return token(TokenType.KW_CHAR); }
    "void"               { return token(TokenType.KW_VOID); }
    "true"               { return token(TokenType.KW_TRUE); }
    "false"              { return token(TokenType.KW_FALSE); }

    /* Literals and identifiers. */
    {Real}                { return token(TokenType.REAL_LITERAL); }
    {Integer}             { return token(TokenType.INT_LITERAL); }
    {StringLiteral}       { return token(TokenType.STRING_LITERAL); }
    {Identifier}          { return token(TokenType.ID); }

    /* Composite operators (must be evaluated before simple ones). */
    "++"                 { return token(TokenType.INC); }
    "--"                 { return token(TokenType.DEC); }
    "=="                 { return token(TokenType.EQ); }
    "!="                 { return token(TokenType.NEQ); }
    "<="                 { return token(TokenType.LE); }
    ">="                 { return token(TokenType.GE); }
    "&&"                 { return token(TokenType.AND); }
    "||"                 { return token(TokenType.OR); }

    /* Simple operators. */
    "="                  { return token(TokenType.ASSIGN); }
    "+"                  { return token(TokenType.PLUS); }
    "-"                  { return token(TokenType.MINUS); }
    "*"                  { return token(TokenType.STAR); }
    "/"                  { return token(TokenType.SLASH); }
    "%"                  { return token(TokenType.MOD); }
    "!"                  { return token(TokenType.NOT); }
    "<"                  { return token(TokenType.LT); }
    ">"                  { return token(TokenType.GT); }

    /* Delimiters and separators. */
    "("                  { return token(TokenType.LPAREN); }
    ")"                  { return token(TokenType.RPAREN); }
    "{"                  { return token(TokenType.LBRACE); }
    "}"                  { return token(TokenType.RBRACE); }
    "["                  { return token(TokenType.LBRACKET); }
    "]"                  { return token(TokenType.RBRACKET); }
    ";"                  { return token(TokenType.SEMICOLON); }
    ","                  { return token(TokenType.COMMA); }
    "."                  { return token(TokenType.DOT); }

    /* End-of-file token to close the analysis flow. */
    <<EOF>>               { return token(TokenType.EOF, "<EOF>"); }

    /* Fallback: any unrecognized symbol is reported as a lexical error. */
    [^]                   { return token(TokenType.ERROR); }
}

/*
 * COMMENT state: consumes input until block closing delimiter.
 * If EOF is reached without closure, an explicit ERROR token is returned.
 */
<COMMENT> {
    "*/"                 { yybegin(YYINITIAL); }
    <<EOF>>               { return token(TokenType.ERROR, "Comentario no cerrado"); }
    [^]                   { /* Consume all content inside comment */ }
}
