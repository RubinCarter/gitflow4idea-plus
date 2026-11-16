plugins {
    id("java")
    id("org.jetbrains.intellij.platform") version "2.10.0"
}

repositories {
    mavenCentral()
    intellijPlatform {
        defaultRepositories()
    }
}

group = "gitflow4idea-plus"
version = "0.8.1-beta.9"

java {
    sourceCompatibility = JavaVersion.VERSION_21
    targetCompatibility = JavaVersion.VERSION_21
}

dependencies {
    testImplementation("junit:junit:4.13.2")

    intellijPlatform {
        intellijIdeaCommunity("2024.2")
        bundledPlugin("Git4Idea")
        bundledPlugin("com.intellij.tasks")
    }
}

intellijPlatform {
    pluginConfiguration {
        id = "Gitflow-Fix"
        name = "Git Flow Integration Plus"
        description = """
            <H2>Git Flow Integration for Intellij</H2>
            An intelliJ plugin providing a UI layer for git-flow, which in itself is a collection of Git extensions to provide high-level repository operations for Vincent <a href="https://nvie.com/posts/a-successful-git-branching-model/">Driessen's branching model</a>
        """
        version = "${project.version}"
        ideaVersion {
            sinceBuild = "242"
        }
        changeNotes = """
            <H2>Changelog for 0.8.1-beta.9</H2>
            <ul>
              <li>Fix RepoActions.getChildren() nullability annotation to match the platform's type-use @NotNull on the array return type</li>
              <li>Bump Gradle wrapper to 8.14.5</li>
            </ul>

            <H2>Changelog for 0.8.1-beta.8</H2>
            <ul>
              <li>Fix "Re-Initialize Gitflow Repository..." action ignoring its display name (constructor parameter bug)</li>
              <li>Fix potential NullPointerException in task dialog panel provider when no branch info is available yet</li>
              <li>Fix potential NullPointerException in the base/production branch combo boxes on init and start dialogs</li>
              <li>Fix potential NullPointerException in startProcess() if the underlying git process fails to start</li>
              <li>Remove more dead code (GitflowComponent, unused imports/methods) and modernize (lambdas, diamond operators, text blocks, enhanced for-loops)</li>
              <li>Replace printStackTrace() with proper platform logging</li>
            </ul>

            <H2>Changelog for 0.8.1-beta.7</H2>
            <ul>
              <li>Remove dead code: unused UnsupportedVersionWidgetPresentation class and isCurrentBranchMaster() method</li>
              <li>Fix potential NullPointerException when starting a feature/hotfix/bugfix from the task dialog with no base branch selected</li>
              <li>General code cleanup (unused imports/fields, redundant boolean checks, diamond operators, enhanced for-loops)</li>
            </ul>

            <H2>Changelog for 0.8.1-beta.6</H2>
            <ul>
              <li>Require IntelliJ 2024.2+ (bundled JetBrains Runtime 21) and build against Java 21</li>
              <li>Fix "Gitflow Operations Popup..." action doing nothing (was calling a deprecated API that always returned null)</li>
              <li>Clean up all compiler warnings (deprecated API usage, missing serialVersionUID, raw types)</li>
            </ul>

            <H2>Changelog for 0.8.1-beta.5</H2>
            <ul>
              <li>Fix false-positive "merge conflict" detection (and the resulting IDE freeze/infinite retry) when a squash finish fails for a non-conflict reason</li>
              <li>Force English git output for all Gitflow commands (LC_ALL=C) so error/conflict detection works correctly on non-English system locales</li>
            </ul>

            <H2>Changelog for 0.8.1-beta.4</H2>
            <ul>
              <li>Fix "Requires plugin com.intellij.tasks" preventing install/load on newer IDEA versions (Task Management dependency is now optional)</li>
              <li>Fix IDE freezing when finishing a feature/bugfix with "Squash during merge" enabled (git-flow's internal commit no longer waits on an interactive editor)</li>
            </ul>

            <H2>Changelog for 0.8.1-beta.3</H2>
            <ul>
              <li>Support git-flow-next implementation #45</li>
              <li>Support gitflow-cjs implementation #34</li>
              <li>Fix "Could not determine git flow version" for Windows users (Git 2.51.1+) #44</li>
              <li>Add git-flow-next config compatibility layer (from PR #46)</li>
              <li>Smart branch detection for init dialog (prioritize main over master)</li>
              <li>Improve init prompt matching for different git-flow implementations</li>
              <li>Improve unsupported version error message with installation guidance</li>
              <li>Add "Squash during merge" (-S) option for finishing features and bugfixes</li>
            </ul>

            <H2>Changelog for 0.7.13</H2>
            <ul>
              <li>Fix "(class com.intellij.openapi.project.impl.ProjectImpl) has already been disposed" #29 </li>
            </ul>
            
            <H2>Changelog for 0.7.11</H2>
            <ul>
              <li>Fix "Access is allowed from event dispatch thread only" #17 #21 #19 </li>
            </ul>
            
            <H2>Changelog for 0.7.10</H2>
            <ul>
              <li>Fix "Error during startup" #4</li>
            </ul>
        
            <H2>Changelog for 0.7.9</H2>
            <ul>
              <li>Support for 2022.1 build</li>
            </ul>
        
            <H2>Changelog for 0.7.8</H2>
            <ul>
              <li>Support for 2021.3 build</li>
            </ul>
        
            <H2>Changelog for 0.7.7</H2>
            <ul>
              <li>Fix issue with "Unsupported gitflow version" message presented at startup #328 #329</li>
              <li>Support for 2021.2 build</li>
            </ul>
        
            <H2>Changelog for 0.7.6</H2>
            <ul>
              <li>Fix "Error using shortcuts" #322</li>
              <li>fix Finishing BugFix throws stacktrace #320</li>
              <li>Support for 2021.1 build</li>
            </ul>
        
            <H2>Changelog for 0.7.5</H2>
            <ul>
              <li>PluginException: Icon cannot be found in 'AllIcons.Vcs.CheckOut' #314 (@tumb1er)</li>
              <li>Support for 2020.3 build</li>
            </ul>
        
            <H2>Changelog for 0.7.4</H2>
            <ul>
              <li>Fix deprecations #298 (@fabmars)</li>
              <li>Support for 2020.2 build</li>
            </ul>
        
            <H2>Changelog for 0.7.3</H2>
            <ul>
              <li>Implemented sorting and filtering of track branch dialog #290 (@mmopitz)</li>
              <li>Fix Version 0.7.2 causes that Active Tool Windows only is showed in one project if you have several open #301 (@tumb1er)</li>
              <li>Fix Unsupported Git Flow version Fix #302 (@opherv)</li>
              <li>Fix init settings shown in UI are misleading (do not match default) #283 (@opherv)</li>
            </ul>
        
            <H2>Changelog for 0.7.2</H2>
            <ul>
              <li>Support for Idea build 200 #276 (@fabmars, @tumb1er )</li>
              <li>Fix Icon cannot be found in 'AllIcons.Vcs.' #286 (@fabmars)</li>
              <li>Fix finish release error (Mac OS) #273 (@opherv)</li>
              <li>Breaking 'Search Everywhere' dialog window for projects without git #265 (@opherv)</li>
            </ul>
        
            <H2>Changelog for 0.7.1</H2>
            <ul>
              <li>Support for Idea build 193 #259 (@opherv)</li>
              <li>Check that the user has AVH version of git flow installed, show dialog otherwise #253 (@opherv)</li>
              <li>Add safety which should help fix #249 - Init repo failed #259 (@opherv)</li>
              <li>Fix Memory leak of ProjectImpl and GitRepositoryImpl after projet is closed #255 (@opherv)</li>
              <li>Add icons to actions #232 (@opherv)</li>
            </ul>
        
            <H2>Changelog for 0.7.0</H2>
            <ul>
              <li>Fix NPE when clicking Gitflow menu #245 (@opherv)</li>
              <li>Fix "Init gitflow" doesn't update widget #247 (@opherv)</li>
              <li>Fix Wrong message when finishing a feature #144 (@opherv)</li>
              <li>Feature: Re-init gitflow (access from VCS>Git>Gitflow>Advanced menu) #50 (@bmwsedee/@opherv)</li>
              <li>"Feature": Don't show branch select combo on new Hotfix (@opherv)</li>
            </ul>
        
            <H2>Changelog for 0.6.9</H2>
            <ul>
              <li>Support for Idea build 192 #241 (@opherv)</li>
              <li>Feature: No Fast-forward option is not working #225 (@opherv)</li>
              <li>Hotfix: option -k to keep branch after performing finish #199 (@opherv)</li>
              <li>Exceptions #243 #235 #323 #223 (@bmwsedee)</li>
            </ul>
        
            <H2>Changelog for 0.6.8</H2>
            <ul>
              <li>Support for Idea build 191 #221 (@ottnorml)</li>
              <li>Fix performance issues in plugin #195 (@bmwsedee)</li>
            </ul>
        
            <p>Note - if you see 'no gitflow' in the status bar you will need to re-init using <code>git flow init -f</code></p>
        """
    }
}
