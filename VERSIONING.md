# Versioning strategy (GitHub)

## 1. Branching model

- `main`: stable, production-ready branch.
- `develop`: integration branch for upcoming release.
- `feature/<short-name>`: new functionality.
- `fix/<short-name>`: bug fixes.
- `release/vX.Y.Z`: release stabilization.
- `hotfix/vX.Y.Z`: urgent fixes from `main`.

## 2. Commit convention (Conventional Commits)

Use this format:

`<type>(<scope>): <description>`

Common types:
- `feat`: new feature
- `fix`: bug fix
- `docs`: documentation only
- `refactor`: internal code changes without behavior changes
- `test`: tests added/updated
- `build`: build/dependencies/tooling
- `ci`: CI/CD workflow updates
- `chore`: maintenance tasks

Examples:
- `feat(lexer): add string literal support`
- `fix(lexer): handle unclosed multiline comments`
- `ci(actions): add release workflow`

## 3. Semantic Versioning

Use `MAJOR.MINOR.PATCH`:

- `MAJOR`: incompatible API/token changes.
- `MINOR`: backward-compatible new tokens/rules.
- `PATCH`: backward-compatible fixes.

## 4. Release flow

1. Merge completed work to `develop`.
2. Create `release/vX.Y.Z`.
3. Update `CHANGELOG.md` and version references.
4. Merge release into `main` and tag `vX.Y.Z`.
5. Push tag to trigger CD workflow.
6. Merge `main` back into `develop`.

## 5. Pull request policy

- PRs target `develop` (or `main` for hotfix).
- CI must pass before merge.
- At least 1 reviewer.
- Include checklist and linked issue.

## 6. Suggested protected branch rules

For `main`:
- Require pull request reviews.
- Require status checks to pass.
- Require branches up to date before merge.
- Restrict direct pushes.
