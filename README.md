# Práctica: especificaciones léxicas con JFlex

Este proyecto incluye una solución base para la actividad de analizadores léxicos:

- Especificación JFlex: `Lexer.flex`
- Tipos de token: `src/TokenType.java`
- Estructura de token: `src/Token.java`
- Programa de prueba: `src/Main.java`
- Entrada de ejemplo: `ejemplo.txt`

## 1) Requisitos

- JDK instalado y agregado al `PATH`
- JFlex (por ejemplo `jflex-full-1.9.1.jar`)

## 2) Generar el analizador léxico

En PowerShell, ubicado en la carpeta del proyecto:

```powershell
java -jar .\jflex-full-1.9.1.jar -d .\src .\Lexer.flex
```

Esto genera `src/Lexer.java`.

## 3) Compilar

```powershell
javac -d .\out .\src\*.java
```

## 4) Ejecutar con archivo de prueba

```powershell
java -cp .\out Main .\ejemplo.txt
```

## 5) Qué reconoce este lexer

- Palabras reservadas: `if, else, while, for, return, int, float, double, boolean, String, char, void, true, false`
- Identificadores
- Literales: enteros, reales y cadenas
- Operadores aritméticos y lógicos
- Delimitadores: `(){}[];, .`
- Comentarios `//...` y `/* ... */`
- Manejo de error léxico (`TokenType.ERROR`)

## 6) Personalización rápida

Si tu profesor pide un conjunto específico de tokens, edita las reglas dentro de `Lexer.flex` y agrega/elimina elementos en `TokenType.java`.

## 7) Versionado detallado en GitHub

Este repositorio ya incluye una base de versionado profesional:

- Estrategia de ramas y releases: `VERSIONING.md`
- Historial de cambios: `CHANGELOG.md`
- Guía de colaboración: `CONTRIBUTING.md`
- Plantillas para PR e issues: `.github/pull_request_template.md` y `.github/ISSUE_TEMPLATE/*`

### Convención recomendada

- Commits con Conventional Commits (`feat`, `fix`, `docs`, `ci`, etc.).
- Versionado semántico (`MAJOR.MINOR.PATCH`).
- Releases usando tags tipo `vX.Y.Z`.

## 8) CI/CD con GitHub Actions

Se configuraron dos pipelines:

- CI: `.github/workflows/ci.yml`
	- Ejecuta en `push` y `pull_request`.
	- Descarga JFlex, genera `Lexer.java`, compila y hace prueba de ejecución.

- CD: `.github/workflows/cd-release.yml`
	- Ejecuta al hacer push de un tag `v*.*.*`.
	- Compila, empaqueta artefacto `.tar.gz` y crea un GitHub Release.

## 9) Publicar en GitHub

1. Configura tu identidad Git (si aún no está):

```powershell
git config --global user.name "Tu Nombre"
git config --global user.email "tu_correo@ejemplo.com"
```

2. Crea commit inicial:

```powershell
git add .
git commit -m "feat(lexer): initial project with versioning and ci-cd"
```

3. Conecta tu repositorio remoto y sube `main`:

```powershell
git remote add origin https://github.com/<usuario>/<repositorio>.git
git push -u origin main
```

4. Crea una release (dispara CD):

```powershell
git tag v0.1.0
git push origin v0.1.0
```
