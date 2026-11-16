package gitflow.ui;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.ValidationInfo;
import com.intellij.openapi.util.text.StringUtil;
import com.intellij.ui.CollectionComboBoxModel;
import gitflow.GitflowInitOptions;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * @author Andreas Vogler (Andreas.Vogler@geneon.de)
 */

public class GitflowInitOptionsDialog extends DialogWrapper {
    private JPanel contentPane;
    private JCheckBox useNonDefaultConfigurationCheckBox;

    private JComboBox<String> productionBranchComboBox;
    private JComboBox<String> developmentBranchComboBox;
    private JTextField featurePrefixTextField;
    private JTextField releasePrefixTextField;
    private JTextField hotfixPrefixTextField;
    private JTextField supportPrefixTextField;
    private JTextField versionPrefixTextField;
    private JTextField bugfixPrefixTextField;

    private final List<String> localBranches;

    @SuppressWarnings("this-escape") //standard DialogWrapper init() pattern
    public GitflowInitOptionsDialog(Project project, List<String> _localBranches) {
        super(project);
        localBranches = _localBranches;

        setTitle("Options for Gitflow Init");
        setLocalBranchesComboBox(false);

        init();
        useNonDefaultConfigurationCheckBox.addItemListener(e -> enableFields(e.getStateChange()==ItemEvent.SELECTED));
    }

    /**
     * Find the best production branch from existing branches.
     * Priority: main > master > production > trunk > first available branch > "main" as fallback
     */
    private String findBestProductionBranch() {
        if (localBranches.contains("main")) return "main";
        if (localBranches.contains("master")) return "master";
        if (localBranches.contains("production")) return "production";
        if (localBranches.contains("trunk")) return "trunk";
        if (!localBranches.isEmpty()) return localBranches.getFirst();
        return "main";
    }

    /**
     * Find the best development branch from existing branches.
     * Priority: develop > dev > development > staging > "develop" as fallback
     */
    private String findBestDevelopmentBranch() {
        if (localBranches.contains("develop")) return "develop";
        if (localBranches.contains("dev")) return "dev";
        if (localBranches.contains("development")) return "development";
        if (localBranches.contains("staging")) return "staging";

        // Try to find a second branch that's not production
        String production = findBestProductionBranch();
        for (String branch : localBranches) {
            if (!branch.equals(production)) {
                return branch;
            }
        }

        return "develop";
    }

    private void setLocalBranchesComboBox(boolean isNonDefault){
        String defaultProduction = findBestProductionBranch();
        String defaultDevelopment = findBestDevelopmentBranch();

        if (isNonDefault){
            // Combine popular branch names with existing local branches
            List<String> productionBranches = new ArrayList<>(Arrays.asList("main", "master", "production"));
            List<String> developmentBranches = new ArrayList<>(Arrays.asList("develop", "development", "dev"));

            // Add existing local branches to the options
            for (String branch : localBranches) {
                if (!productionBranches.contains(branch)) {
                    productionBranches.add(branch);
                }
                if (!developmentBranches.contains(branch)) {
                    developmentBranches.add(branch);
                }
            }

            productionBranchComboBox.setModel(new CollectionComboBoxModel<>(productionBranches));
            productionBranchComboBox.setEditable(true);
            productionBranchComboBox.setSelectedItem(defaultProduction);

            developmentBranchComboBox.setModel(new CollectionComboBoxModel<>(developmentBranches));
            developmentBranchComboBox.setEditable(true);
            developmentBranchComboBox.setSelectedItem(defaultDevelopment);
        } else {
            // Use smart defaults based on existing branches
            developmentBranchComboBox.setModel(new CollectionComboBoxModel<>(Collections.singletonList(defaultDevelopment)));
            developmentBranchComboBox.setEditable(false);
            productionBranchComboBox.setModel(new CollectionComboBoxModel<>(Collections.singletonList(defaultProduction)));
            productionBranchComboBox.setEditable(false);
        }
    }

    private void enableFields(boolean enable) {
        setLocalBranchesComboBox(enable);

        productionBranchComboBox.setEnabled(enable);
        developmentBranchComboBox.setEnabled(enable);
        featurePrefixTextField.setEnabled(enable);
        releasePrefixTextField.setEnabled(enable);
        hotfixPrefixTextField.setEnabled(enable);
        supportPrefixTextField.setEnabled(enable);
        bugfixPrefixTextField.setEnabled(enable);
        versionPrefixTextField.setEnabled(enable);
    }

    public boolean useNonDefaultConfiguration()
    {
        return useNonDefaultConfigurationCheckBox.isSelected();
    }

    public GitflowInitOptions getOptions()
    {
        GitflowInitOptions options = new GitflowInitOptions();

        options.setUseDefaults(!useNonDefaultConfigurationCheckBox.isSelected());
        options.setProductionBranch((String) productionBranchComboBox.getSelectedItem());
        options.setDevelopmentBranch((String) developmentBranchComboBox.getSelectedItem());
        options.setFeaturePrefix(featurePrefixTextField.getText());
        options.setReleasePrefix(releasePrefixTextField.getText());
        options.setHotfixPrefix(hotfixPrefixTextField.getText());
        options.setBugfixPrefix(bugfixPrefixTextField.getText());
        options.setSupportPrefix(supportPrefixTextField.getText());
        options.setVersionPrefix(versionPrefixTextField.getText());

        return options;
    }

    @Nullable
    @Override
    protected ValidationInfo doValidate() {
        String message = "Please fill all branch names and prefixes";

        if(useNonDefaultConfiguration()) {
            if(Objects.equals(productionBranchComboBox.getSelectedItem(), developmentBranchComboBox.getSelectedItem())) {
                return new ValidationInfo("Production and development branch must be distinct branches", developmentBranchComboBox);
            }
            if (StringUtil.isEmptyOrSpaces(featurePrefixTextField.getText())) {
                return new ValidationInfo(message, featurePrefixTextField);
            }
            if (StringUtil.isEmptyOrSpaces(releasePrefixTextField.getText())) {
                return new ValidationInfo(message, releasePrefixTextField);
            }
            if (StringUtil.isEmptyOrSpaces(hotfixPrefixTextField.getText())) {
                return new ValidationInfo(message, hotfixPrefixTextField);
            }
            if (StringUtil.isEmptyOrSpaces(supportPrefixTextField.getText())) {
                return new ValidationInfo(message, supportPrefixTextField);
            }
            if (StringUtil.isEmptyOrSpaces(bugfixPrefixTextField.getText())) {
                return new ValidationInfo(message, bugfixPrefixTextField);
            }
        }

        return null;
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return contentPane;
    }
}
