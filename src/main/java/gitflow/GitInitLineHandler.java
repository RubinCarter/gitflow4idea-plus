package gitflow;

import com.intellij.execution.ExecutionException;
import com.intellij.execution.configurations.GeneralCommandLine;
import com.intellij.execution.process.OSProcessHandler;
import com.intellij.execution.process.ProcessAdapter;
import com.intellij.execution.process.ProcessEvent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Key;
import com.intellij.openapi.util.registry.Registry;
import com.intellij.openapi.vfs.VirtualFile;
import git4idea.commands.GitCommand;
import git4idea.commands.GitLineHandler;
import git4idea.commands.GitTextHandler;
import git4idea.util.GitVcsConsoleWriter;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Serial;
import java.util.Locale;


public class GitInitLineHandler extends GitLineHandler {
    private static final Logger LOG = Logger.getInstance(GitInitLineHandler.class);

    private final GitVcsConsoleWriter consoleWriter;

    private BufferedWriter writer;
    GitflowInitOptions _initOptions;

    public GitInitLineHandler(GitflowInitOptions initOptions,
            @NotNull Project project, @NotNull VirtualFile vcsRoot,
            @NotNull GitCommand command) {
        super(project, vcsRoot, command);
        consoleWriter = GitVcsConsoleWriter.getInstance(project);
        _initOptions = initOptions;
    }

    @Override
    protected OSProcessHandler createProcess(@NotNull GeneralCommandLine commandLine) throws ExecutionException {
        MyOSProcessHandler process = new MyOSProcessHandler(commandLine, this.myWithMediator && Registry.is("git.execute.with.mediator"));
        process.addProcessListener(new ProcessAdapter() {
            @Override
            public void onTextAvailable(@NotNull ProcessEvent event, @NotNull Key outputType) {
                String s = event.getText();
                GitInitLineHandler.this.onTextAvailable(s);
            }
        });
        return process;
    }

    public void onTextAvailable(String s) {
        try {
            if (containsAnyIgnoreCase(s, "name for production releases", "trunk branch")) {
                consoleWriter.showCommandLine(_initOptions.getProductionBranch());

                writer.write(_initOptions.getProductionBranch());
                writer.write("\n");
                writer.flush();
            }

            if (containsAnyIgnoreCase(s, "name for \"next release\"", "name for development")) {
                consoleWriter.showCommandLine(_initOptions.getDevelopmentBranch());

                writer.write(_initOptions.getDevelopmentBranch());
                writer.write("\n");
                writer.flush();
            }

            if (containsAnyIgnoreCase(s, "feature branches", "feature branch prefix", "feature prefix")) {
                consoleWriter.showCommandLine(_initOptions.getFeaturePrefix());

                writer.write(_initOptions.getFeaturePrefix());
                writer.write("\n");
                writer.flush();
            }
            if (containsAnyIgnoreCase(s, "bugfix branches", "bugfix branch prefix", "bugfix prefix")) {
                consoleWriter.showCommandLine(_initOptions.getBugfixPrefix());

                writer.write(_initOptions.getBugfixPrefix());
                writer.write("\n");
                writer.flush();
            }
            if (containsAnyIgnoreCase(s, "release branches", "release branch prefix", "release prefix")) {
                consoleWriter.showCommandLine(_initOptions.getReleasePrefix());

                writer.write(_initOptions.getReleasePrefix());
                writer.write("\n");
                writer.flush();
            }
            if (containsAnyIgnoreCase(s, "hotfix branches", "hotfix branch prefix", "hotfix prefix")) {
                consoleWriter.showCommandLine(_initOptions.getHotfixPrefix());

                writer.write(_initOptions.getHotfixPrefix());
                writer.write("\n");
                writer.flush();
            }
            if (containsAnyIgnoreCase(s, "support branches", "support branch prefix", "support prefix")) {
                consoleWriter.showCommandLine(_initOptions.getSupportPrefix());

                writer.write(_initOptions.getSupportPrefix());
                writer.write("\n");
                writer.flush();
            }
            if (containsAnyIgnoreCase(s, "version tag", "tag prefix")) {
                consoleWriter.showCommandLine(_initOptions.getVersionPrefix());

                writer.write(_initOptions.getVersionPrefix());
                writer.write("\n");
                writer.flush();
            }
            if (s.contains("Hooks and filters")) {
                writer.write("\n");
                writer.flush();
            }


        } catch (IOException e) {
            LOG.error(e);
        }
    }

    private static boolean containsAnyIgnoreCase(String source, String... needles) {
        if (source == null) {
            return false;
        }
        final String lowerSource = source.toLowerCase(Locale.ROOT);
        for (String needle : needles) {
            if (needle != null && lowerSource.contains(needle.toLowerCase(Locale.ROOT))) {
                return true;
            }
        }
        return false;
    }

    @Nullable
    @Override
    protected Process startProcess() throws ExecutionException {
        Process p = super.startProcess();
        if (p != null) {
            writer = new BufferedWriter(new OutputStreamWriter(p.getOutputStream()));
        }
        return p;
    }

    @Override
    protected void processTerminated(int exitCode) {
        super.processTerminated(exitCode);
    }

    static class MyOSProcessHandler extends GitTextHandler.MyOSProcessHandler {
        @Serial
        private static final long serialVersionUID = 1L;

        MyOSProcessHandler(@NotNull GeneralCommandLine commandLine,
                boolean withMediator) throws ExecutionException {
            super(commandLine, withMediator);
        }
    }
}
