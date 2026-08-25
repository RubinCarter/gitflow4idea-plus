# Git Flow Integration Plus for Intellij

### Available @ [JetBrains Plugins Repository][1]


An intelliJ plugin providing a UI layer for git-flow, which in itself is a collection of Git extensions to provide high-level repository operations for Vincent [Driessen's branching model](http://nvie.com/git-model).

![screenshot](https://github.com/RubinCarter/gitflow4idea-fix/blob/develop/docs/img/gitflow.png)

## Companion agent skill (experimental)

Using an AI coding agent? Check out **[gitflow-cli-plus](https://github.com/RubinCarter/gitflow-cli-plus)** — an agent skill companion to this plugin, derived from a functional analysis of its semantics. It ships a `SKILL.md` that teaches agents the same git-flow operations (init / feature / release / hotfix / bugfix), backed by a small Go CLI binary, follows the AVH edition semantics and config layout, and recognizes repos initialized by git-flow-next through a read-only compatibility layer.

**Note: this skill project is currently experimental** — expect rough edges, and please report anything you find in its [issue tracker](https://github.com/RubinCarter/gitflow-cli-plus/issues).

## Getting started

For the best introduction to get started with `git flow`, please read Jeff Kreeftmeijer's blog post:

[http://jeffkreeftmeijer.com/2010/why-arent-you-using-git-flow/](http://jeffkreeftmeijer.com/2010/why-arent-you-using-git-flow/)

Or have a look at this [cheat sheet](http://danielkummer.github.io/git-flow-cheatsheet/) by Daniel Kummer:

Huge shoutout [to Kirill Likhodedov](https://github.com/klikh), who wrote much of the original git4idea plugin, without which this plugin could not exist

## Online Installation

The plugin is available via the IntelliJ plugin manager. Just search for "Git Flow Integration Plus" to get the latest version!

**The plugin supports the [AVH edition](https://github.com/petervanderdoes/gitflow), [git-flow-next](https://github.com/gittower/git-flow-next), and gitflow-cjs. The unmaintained [Vanilla Git Flow](https://github.com/nvie/gitflow) is not supported.** Install git-flow-next with `brew install git-flow-next`; see [version details](GITFLOW_VERSION.md).

## Offline Installation
download path: https://github.com/RubinCarter/gitflow4idea-fix/releases

Installation document:https://www.jetbrains.com/help/idea/managing-plugins.html#install_plugin_from_disk

**The plugin supports the [AVH edition](https://github.com/petervanderdoes/gitflow), [git-flow-next](https://github.com/gittower/git-flow-next), and gitflow-cjs. The unmaintained [Vanilla Git Flow](https://github.com/nvie/gitflow) is not supported.** Install git-flow-next with `brew install git-flow-next`; see [version details](GITFLOW_VERSION.md).

## Caveats

While the plugin is operational and contains all basic functions (init/feature/release/hotfix), it may contains bugs. With your help I'll be able to find and zap them all.

## Helping out

This project is under active development.
If you encounter any bug or an issue, I encourage you to add the them to the [Issues list](https://github.com/RubinCarter/gitflow4idea-fix/issues) on Github.
Feedback and suggestions are also very welcome.

## License

This plugin is under the [Apache 2.0 license](http://www.apache.org/licenses/LICENSE-2.0.html).
Copyright 2013-2020, Opher Vishnia.

## Who and why

This plugin was created by [Opher Vishnia](http://www.opherv.com), after I couldn't find any similar implementation.
I saw this [suggestion page](http://youtrack.jetbrains.com/issue/IDEA-65491) on the JetBrains site has more than 220 likes and 80 comments, and decided to take up the gauntlet :)

This plugin is forked from original https://github.com/OpherV/gitflow4idea .



[1]: https://plugins.jetbrains.com/plugin/18320-git-flow-integration-plus
