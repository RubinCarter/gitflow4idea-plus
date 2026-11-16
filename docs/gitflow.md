# Cheat sheet: git-flow (AVH edition) on the command line

This document is the command-line equivalent of the operations performed by this plugin.
Flags were verified directly against the [petervanderdoes/gitflow](https://github.com/petervanderdoes/gitflow) (AVH edition) sources.

## Prerequisites

```bash
git flow version
```

Shows the installed version and implementation. It should mention "AVH" (e.g. `AVH Edition - v1.12.3`) — version `0.4.1` (nvie, the original) does **not** support bugfix branches or squash.

---

## 1. Initializing a repository

```bash
git flow init
```

Interactively configures branch names (`main`/`develop`) and prefixes (`feature/`, `release/`, `bugfix/`, `hotfix/`).

```bash
git flow init -d
```

Same as above, but without prompts — uses the defaults right away (`develop`, `feature/`, `release/`, `hotfix/`, `bugfix/`, no version tag prefix).

```bash
git flow init -f
```

Forces reconfiguration even if the repository is already initialized for git-flow.

---

## 2. Feature (`feature/*` → merged into `develop`)

**Start** — creates a feature branch from `develop`:

```bash
git flow feature start my-feature
git flow feature start my-feature -F                 # -F = fetch from origin first
git flow feature start my-feature release/1.2.0      # start from a base other than develop
```

**Finish** — merges the feature into `develop` and deletes the branch:

```bash
git flow feature finish my-feature
```

| Flag | Meaning |
|---|---|
| `-F, --fetch` | fetch from origin before finishing |
| `-r, --rebase` | rebase before merging instead of a plain merge |
| `--push` | push to origin after finishing |
| `-k, --keep` | keep the branch after finishing |
| `--keeplocal` | keep only the local branch |
| `--keepremote` | keep only the remote branch |
| `-D, --force_delete` | force-delete the branch even if it is not fully merged |
| `--no-ff` | always create a merge commit, never fast-forward |
| `-S, --squash` | **squash** — the whole feature history lands on develop as a single commit |

Examples:

```bash
git flow feature finish my-feature -S              # squash merge
git flow feature finish my-feature -S --push       # squash + push develop to origin
git flow feature finish my-feature --no-ff -k      # regular merge commit, keep the branch
```

**Publish / Track / Pull** (sharing a branch with your team):

```bash
git flow feature publish my-feature           # push the feature branch to origin
git flow feature track their-feature          # fetch and start tracking a teammate's feature
git flow feature pull origin their-feature    # pull changes into a tracked feature
```

---

## 3. Bugfix (`bugfix/*` → merged into `develop`)

Same logic as feature, just `bugfix` instead of `feature`:

```bash
git flow bugfix start fix-login
git flow bugfix finish fix-login -S           # squash merge
git flow bugfix finish fix-login --no-ff --push
git flow bugfix publish fix-login
git flow bugfix track fix-login
```

Finish flags: `-F`, `-r/--rebase`, `--push`, `-k/--keep`, `--keeplocal`, `--keepremote`, `-D/--force_delete`, `--no-ff`, `-S/--squash` (exactly the same as feature).

---

## 4. Release (`release/*` → merged into `main` **and** `develop` + tag)

**Start:**

```bash
git flow release start 1.2.0
git flow release start 1.2.0 -F    # fetch from origin first
```

**Finish** — merges into `main` and `develop`, then tags:

```bash
git flow release finish 1.2.0
```

| Flag | Meaning |
|---|---|
| `-F, --fetch` | fetch before finishing |
| `-m, --message <msg>` | tag message |
| `-f, --messagefile <file>` | read the tag message from a file |
| `-s, --sign` | GPG-sign the tag |
| `-u, --signingkey <key>` | signing key to use (implies `-s`) |
| `-p, --push` | push `main`, `develop` and the tag to origin |
| `-k, --keep` / `--keeplocal` / `--keepremote` | keep the branch |
| `-n, --notag` | do **not** create a tag |
| `-b, --nobackmerge` | do not merge `main`/the tag back into `develop` |
| `-S, --squash` | squash merge |
| `-T, --tagname <name>` | custom tag name (instead of the version) |

Examples:

```bash
git flow release finish 1.2.0 -m "Release 1.2.0" -p     # tag with message + push
git flow release finish 1.2.0 -n                        # no tag
```

**Publish / Track:**

```bash
git flow release publish 1.2.0
git flow release track 1.2.0
```

---

## 5. Hotfix (`hotfix/*` → merged into `main` **and** `develop` + tag)

**Start** (from `main` by default):

```bash
git flow hotfix start 1.2.1
git flow hotfix start 1.2.1 main    # explicit base branch
```

**Finish:**

```bash
git flow hotfix finish 1.2.1 -m "Hotfix 1.2.1"
```

Same flags as release: `-F`, `-m/-f` (tag message), `-s/-u` (signing), `-p/--push`, `-k/--keep(remote|local)`, `-n` (no tag), `-b/--nobackmerge`, `-S/--squash`, `-T/--tagname`.

```bash
git flow hotfix finish 1.2.1 -n -p     # no tag, with push
git flow hotfix publish 1.2.1
```

---

## Practical notes

- **`develop` and `main`** must already exist locally before you run `finish` (git-flow will not create `main` if it never existed).
- `-S/--squash` does **not** remove the need to handle conflicts — a squash can still require resolving merge conflicts; the only difference is that develop gets a single commit instead of the whole branch history.
- If you squash and want to keep information about the source branch in the commit message, add `--squash-info` (feature/release/hotfix).
- All of these commands are exactly what the plugin runs under the hood via `git flow ...` — so switching to the CLI gives identical behavior (plus access to flags the plugin does not expose at all, e.g. `-r/--rebase`, `--nobackmerge`, `-T/--tagname`, `--force_delete`).
