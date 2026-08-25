The plugin supports the [AVH edition](https://github.com/petervanderdoes/gitflow), [git-flow-next](https://github.com/gittower/git-flow-next), and gitflow-cjs. The unmaintained [Vanilla Git Flow](https://github.com/nvie/gitflow) is not supported.

**How to check Git Flow version**

run `git flow version` 

If it identifies the AVH edition, git-flow-next, or gitflow-cjs, it is supported. A `git-flow-next` installation reports `2.0.0 (git-flow-next)` and can be installed with `brew install git-flow-next`.


**How to uninstall wrong version on OSX**

If the unsupported Vanilla implementation was installed via Homebrew, run `brew uninstall git-flow` and then install a supported implementation such as `brew install git-flow-next`.


**Mac/Linux users:**

If you're running into issues like getting
`Gitflow is not installed`
or
`git: 'flow' is not a git command. See 'git --help'.`

Please be sure to check out [this thread](https://github.com/OpherV/gitflow4idea/issues/7)
