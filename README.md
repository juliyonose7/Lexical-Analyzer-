# Analizador léxico con JFlex

![Java](https://img.shields.io/badge/Java-17%2B-007396?logo=openjdk&logoColor=white)
![JFlex](https://img.shields.io/badge/JFlex-1.9.1-ff6f00)
![Swing](https://img.shields.io/badge/GUI-Swing-1976d2)
![CI](https://img.shields.io/github/actions/workflow/status/juliyonose7/New-folder--2-/ci.yml?branch=main&label=CI&logo=githubactions)
![CD](https://img.shields.io/github/actions/workflow/status/juliyonose7/New-folder--2-/cd-release.yml?label=CD%20Release&logo=githubactions)
![License](https://img.shields.io/badge/License-Academic-8e24aa)

Proyecto académico de Procesadores de Lenguajes para construir un escáner léxico en Java con JFlex, salida de tokens estructurada y modo interactivo (GUI).

## Objetivo

Reconocer tokens de un lenguaje imperativo y devolver, por cada unidad léxica, su:

- tipo (`TokenType`)
- lexema
- línea
- columna

## Características

- Especificación léxica en `Lexer.flex`.
- Generación automática de `Lexer.java` con JFlex.
- Modelo de token inmutable (`Token`) con posición 1-based.
- Modo consola para análisis por archivo.
- GUI interactiva (`LexerGUI`) con:
	- editor de entrada,
	- tabla de tokens,
	- botón de análisis,
	- carga de archivos `.txt`.

## Estructura del proyecto

- `Lexer.flex`: reglas léxicas y estados.
- `src/Lexer.java`: lexer generado.
- `src/TokenType.java`: catálogo de tipos de token.
- `src/Token.java`: DTO de salida léxica.
- `src/Main.java`: entrypoint (GUI o consola).
- `src/LexerGUI.java`: interfaz gráfica Swing.
- `ejemplo.txt`: entrada de prueba.

## Requisitos

- JDK 17+ (CI usa JDK 21).
- JFlex 1.9.1.
- `java-cup-runtime` (requerido por JFlex en ejecución CLI/CI).

## Tokens reconocidos

- Palabras reservadas: `if`, `else`, `while`, `for`, `return`, `int`, `float`, `double`, `boolean`, `String`, `char`, `void`, `true`, `false`.
- Identificadores.
- Literales: enteros, reales y cadenas.
- Operadores: aritméticos, relacionales, lógicos e incrementales.
- Delimitadores: `()`, `{}`, `[]`, `,`, `;`, `.`.
- Comentarios de línea (`//`) y bloque (`/* ... */`).
- Tokens de control: `ERROR`, `EOF`.

## Ejecución local

### 1) Generar lexer (opcional si `src/Lexer.java` ya existe)

Descarga dependencias:

- `https://repo1.maven.org/maven2/de/jflex/jflex/1.9.1/jflex-1.9.1.jar`
- `https://repo1.maven.org/maven2/com/github/vbmacher/java-cup-runtime/11b-20160615/java-cup-runtime-11b-20160615.jar`

Generación:

- Linux/macOS:
	- `java -cp "jflex.jar:java-cup-runtime.jar" jflex.Main -d ./src ./Lexer.flex`
- Windows (PowerShell/cmd):
	- `java -cp "jflex.jar;java-cup-runtime.jar" jflex.Main -d ./src ./Lexer.flex`

### 2) Compilar

- `javac -d ./out ./src/*.java`

### 3) Ejecutar

- Modo GUI:
	- `java -cp ./out Main`
- Modo consola (archivo):
	- `java -cp ./out Main ./ejemplo.txt`

## Flujo técnico

1. JFlex transforma `Lexer.flex` en `src/Lexer.java`.
2. `Main` crea una instancia de `Lexer` sobre un `Reader`.
3. Se consume `nextToken()` hasta `EOF` o `ERROR`.
4. La salida se presenta en consola o en la tabla de la GUI.

## CI/CD

El repositorio incluye workflows en GitHub Actions:

- `CI` (`.github/workflows/ci.yml`):
	- descarga JFlex + `java-cup-runtime`,
	- genera lexer,
	- compila,
	- ejecuta smoke test con `ejemplo.txt`.
- `CD Release` (`.github/workflows/cd-release.yml`):
	- se activa en tags `v*.*.*`,
	- empaqueta artefactos,
	- publica release en GitHub.

## Alcance actual

Baseline funcional del analizador léxico, preparado para extender gramática, agregar tokens y conectar con fases posteriores (parser/semántica).
