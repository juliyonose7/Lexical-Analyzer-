# Contributing

## Workflow

1. Create an issue or pick one.
2. Branch from `develop`:
   - `feature/<name>`
   - `fix/<name>`
3. Commit using Conventional Commits.
4. Open PR to `develop`.
5. Ensure CI is green.
6. Request review.

## Local validation

- Generate lexer from `Lexer.flex`.
- Compile Java sources.
- Run scanner with `ejemplo.txt`.

## Release preparation

- Update `CHANGELOG.md` under `Unreleased`.
- Move release notes to tagged version section.
- Create and push tag `vX.Y.Z`.
