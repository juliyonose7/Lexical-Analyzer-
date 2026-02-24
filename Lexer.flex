%%
/*
 * Configuración global de JFlex:
 * - %line / %column habilita rastreo posicional para diagnósticos.
 * - %type Token indica que cada regla retorna un objeto de dominio Token.
 * - %state COMMENT define estado léxico para comentarios de bloque.
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
     * Factories de tokens para centralizar creación y normalizar posición.
     * Se expone línea/columna en base 1 para consumo humano.
     */
    private Token token(TokenType type) {
        return new Token(type, yytext(), yyline + 1, yycolumn + 1);
    }

    private Token token(TokenType type, String lexeme) {
        return new Token(type, lexeme, yyline + 1, yycolumn + 1);
    }
%}

/* Macros reutilizables: mejoran legibilidad y evitan duplicación de regex. */
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

/* Estado inicial: reconoce tokens del lenguaje fuente. */
<YYINITIAL> {
    {WhiteSpace}+         { /* Ignorar espacios en blanco */ }

    /* Inicio de comentarios: transición explícita entre estados léxicos. */
    "/*"                 { yybegin(COMMENT); }
    "//"[^\r\n]*        { /* Ignorar comentario de línea */ }

    /* Palabras reservadas (prioridad antes de Identifier). */
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

    /* Literales e identificadores. */
    {Real}                { return token(TokenType.REAL_LITERAL); }
    {Integer}             { return token(TokenType.INT_LITERAL); }
    {StringLiteral}       { return token(TokenType.STRING_LITERAL); }
    {Identifier}          { return token(TokenType.ID); }

    /* Operadores compuestos (deben evaluarse antes que los simples). */
    "++"                 { return token(TokenType.INC); }
    "--"                 { return token(TokenType.DEC); }
    "=="                 { return token(TokenType.EQ); }
    "!="                 { return token(TokenType.NEQ); }
    "<="                 { return token(TokenType.LE); }
    ">="                 { return token(TokenType.GE); }
    "&&"                 { return token(TokenType.AND); }
    "||"                 { return token(TokenType.OR); }

    /* Operadores simples. */
    "="                  { return token(TokenType.ASSIGN); }
    "+"                  { return token(TokenType.PLUS); }
    "-"                  { return token(TokenType.MINUS); }
    "*"                  { return token(TokenType.STAR); }
    "/"                  { return token(TokenType.SLASH); }
    "%"                  { return token(TokenType.MOD); }
    "!"                  { return token(TokenType.NOT); }
    "<"                  { return token(TokenType.LT); }
    ">"                  { return token(TokenType.GT); }

    /* Delimitadores y separadores. */
    "("                  { return token(TokenType.LPAREN); }
    ")"                  { return token(TokenType.RPAREN); }
    "{"                  { return token(TokenType.LBRACE); }
    "}"                  { return token(TokenType.RBRACE); }
    "["                  { return token(TokenType.LBRACKET); }
    "]"                  { return token(TokenType.RBRACKET); }
    ";"                  { return token(TokenType.SEMICOLON); }
    ","                  { return token(TokenType.COMMA); }
    "."                  { return token(TokenType.DOT); }

    /* Token de fin de archivo para cerrar el flujo de análisis. */
    <<EOF>>               { return token(TokenType.EOF, "<EOF>"); }

    /* Fallback: cualquier símbolo no reconocido se reporta como error léxico. */
    [^]                   { return token(TokenType.ERROR); }
}

/*
 * Estado COMMENT: consume todo hasta cierre de bloque.
 * Si llega EOF sin cierre, se retorna ERROR explícito.
 */
<COMMENT> {
    "*/"                 { yybegin(YYINITIAL); }
    <<EOF>>               { return token(TokenType.ERROR, "Comentario no cerrado"); }
    [^]                   { /* Consumir todo dentro del comentario */ }
}
