# Analizador léxico con JFlex

Proyecto académico para la asignatura de Procesadores de Lenguajes.

## Objetivo

Construir un analizador léxico capaz de reconocer tokens comunes de un lenguaje imperativo, devolviendo para cada token su tipo, lexema y posición (línea y columna).

## Estructura del proyecto

- `Lexer.flex`: especificación léxica en JFlex.
- `src/TokenType.java`: catálogo de tipos de token.
- `src/Token.java`: modelo de token.
- `src/Main.java`: programa de prueba del analizador.
- `ejemplo.txt`: archivo de entrada de ejemplo.

## Requisitos

- JDK 17 o superior.
- JFlex 1.9.x.

## Tokens reconocidos

- Palabras reservadas: `if`, `else`, `while`, `for`, `return`, `int`, `float`, `double`, `boolean`, `String`, `char`, `void`, `true`, `false`.
- Identificadores.
- Literales: enteros, reales y cadenas.
- Operadores: aritméticos, relacionales, lógicos e incrementales.
- Delimitadores: `()`, `{}`, `[]`, `,`, `;`, `.`.
- Comentarios de línea (`//`) y de bloque (`/* ... */`).
- Errores léxicos (`ERROR`) y fin de archivo (`EOF`).

## Flujo técnico

1. JFlex procesa `Lexer.flex` y genera la clase `Lexer`.
2. `Main` invoca el escáner y obtiene tokens con `nextToken()`.
3. Cada token se imprime con su tipo, lexema, línea y columna.

## Alcance actual

La versión actual implementa un primer baseline funcional del analizador léxico, listo para extender reglas, añadir nuevos tokens y ajustar acciones semánticas según la guía del curso.
