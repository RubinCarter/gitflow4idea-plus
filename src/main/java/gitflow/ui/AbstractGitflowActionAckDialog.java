package gitflow.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * @author rubin 2023年07月27日
 */
public abstract class AbstractGitflowActionAckDialog extends DialogWrapper {
    private JPanel contentPanel;
    private JLabel ackTextLabel;

    public AbstractGitflowActionAckDialog(@Nullable Project project) {
        super(project, true);

        setModal(true);

        setTitle("Ack");
        init();
        ackTextLabel.setText(getAckText());
    }

    @Override
    protected @Nullable JComponent createCenterPanel() {
        return contentPanel;
    }

    protected String getAckText() {
        return "";
    }

}
