package gitflow.ui;

import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nullable;

/**
 * @author rubin 2023年07月27日
 */
public class GitflowFinishActionAckDialog extends AbstractGitflowActionAckDialog {
    public GitflowFinishActionAckDialog(@Nullable Project project) {
        super(project);
    }

    @Override
    protected String getAckText() {
        return "Are you sure you want to Finish?";
    }

}
