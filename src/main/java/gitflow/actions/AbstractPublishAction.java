package gitflow.actions;

import com.intellij.openapi.actionSystem.ActionUpdateThread;
import com.intellij.openapi.application.Application;
import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.progress.Task;
import git4idea.repo.GitRepository;
import gitflow.GitflowConfigUtil;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractPublishAction extends AbstractBranchAction {
    AbstractPublishAction(String actionName, BranchType type) {
        super(actionName, type);
    }

    AbstractPublishAction(GitRepository repo, String actionName, BranchType type) {
        super(repo, actionName, type);
    }

    @Override
    protected boolean isActionAllowedForBranch() {
        if (!super.isActionAllowedForBranch()) {
            return false;
        }
        
        return !branchUtil.isCurrentBranchPublished();
    }

    @Override
    public @NotNull ActionUpdateThread getActionUpdateThread() {
        return ActionUpdateThread.EDT;
    }

    protected void startPublish(Runnable runnable) {
        Application application = ApplicationManager.getApplication();
        application.runReadAction(runnable);
    }

}
