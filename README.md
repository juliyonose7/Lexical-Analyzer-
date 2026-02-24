# Lexical Analyzer with JFlex

![Java](https://img.shields.io/badge/Java-17%2B-007396?logo=openjdk&logoColor=white)
![JFlex](https://img.shields.io/badge/JFlex-1.9.1-ff6f00)
![Swing](https://img.shields.io/badge/GUI-Swing-1976d2)
![CI](https://img.shields.io/github/actions/workflow/status/juliyonose7/New-folder--2-/ci.yml?branch=main&label=CI&logo=githubactions)
![CD](https://img.shields.io/github/actions/workflow/status/juliyonose7/New-folder--2-/cd-release.yml?label=CD%20Release&logo=githubactions)
![License](https://img.shields.io/badge/License-Academic-8e24aa)

Academic project for the Compiler Construction course to build a Java lexical scanner with JFlex, structured token output, and an interactive GUI mode.

## Goal

Recognize tokens from an imperative language and return, for each lexical unit, its:

- type (`TokenType`)
- lexeme
- line
- column

## Features

- Lexical specification in `Lexer.flex`.
- Automatic generation of `Lexer.java` with JFlex.
- Immutable token model (`Token`) with 1-based position tracking.
- Console mode for file-based analysis.
- Interactive GUI (`LexerGUI`) with:
	- source input editor,
	- token table,
	- analyze button,
	- `.txt` file loader.

## Project Structure

- `Lexer.flex`: lexical rules and states.
- `src/Lexer.java`: generated lexer.
- `src/TokenType.java`: token type catalog.
- `src/Token.java`: lexical output DTO.
- `src/Main.java`: entry point (GUI or console).
- `src/LexerGUI.java`: Swing graphical interface.
- `ejemplo.txt`: sample input file.

## Requirements

- JDK 17+ (CI uses JDK 21).
- JFlex 1.9.1.
- `java-cup-runtime` (required by JFlex for CLI/CI execution).

## Recognized Tokens

- Reserved keywords: `if`, `else`, `while`, `for`, `return`, `int`, `float`, `double`, `boolean`, `String`, `char`, `void`, `true`, `false`.
- Identifiers.
- Literals: integers, reals, and strings.
- Operators: arithmetic, relational, logical, and incremental.
- Delimiters: `()`, `{}`, `[]`, `,`, `;`, `.`.
- Line (`//`) and block (`/* ... */`) comments.
- Control tokens: `ERROR`, `EOF`.

## Local Execution

### 1) Generate Lexer (optional if `src/Lexer.java` already exists)

Download dependencies:

- `https://repo1.maven.org/maven2/de/jflex/jflex/1.9.1/jflex-1.9.1.jar`
- `https://repo1.maven.org/maven2/com/github/vbmacher/java-cup-runtime/11b-20160615/java-cup-runtime-11b-20160615.jar`

Generation:

- Linux/macOS:
	- `java -cp "jflex.jar:java-cup-runtime.jar" jflex.Main -d ./src ./Lexer.flex`
- Windows (PowerShell/cmd):
	- `java -cp "jflex.jar;java-cup-runtime.jar" jflex.Main -d ./src ./Lexer.flex`

### 2) Compile

- `javac -d ./out ./src/*.java`

### 3) Run

- GUI mode:
	- `java -cp ./out Main`
- Console mode (file input):
	- `java -cp ./out Main ./ejemplo.txt`

## Technical Flow

1. JFlex transforms `Lexer.flex` into `src/Lexer.java`.
2. `Main` creates a `Lexer` instance over a `Reader`.
3. `nextToken()` is consumed until `EOF` or `ERROR`.
4. Output is rendered in the console or in the GUI token table.

## CI/CD

This repository includes GitHub Actions workflows:

- `CI` (`.github/workflows/ci.yml`):
	- downloads JFlex + `java-cup-runtime`,
	- generates lexer,
	- compiles sources,
	- runs smoke test with `ejemplo.txt`.
- `CD Release` (`.github/workflows/cd-release.yml`):
	- triggers on tags `v*.*.*`,
	- packages artifacts,
	- publishes a GitHub Release.

## Current Scope

Functional lexical analyzer baseline, ready to extend grammar rules, add new tokens, and connect with later phases (parsing/semantics).
