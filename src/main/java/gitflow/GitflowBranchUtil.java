package gitflow;

import com.intellij.openapi.project.Project;
import git4idea.GitLocalBranch;
import git4idea.GitRemoteBranch;
import git4idea.branch.GitBranchUtil;
import git4idea.repo.GitRemote;
import git4idea.repo.GitRepository;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 *
 *
 * @author Opher Vishnia / opherv.com / opherv@gmail.com
 */
public class GitflowBranchUtil {

    Project myProject;
    GitRepository myRepo;

    private String currentBranchName;
    private String branchnameMaster;
    private String branchnameDevelop;
    private String prefixFeature;
    private String prefixRelease;
    private String prefixHotfix;
    private String prefixBugfix;
    private ArrayList<GitRemoteBranch> remoteBranches;
    private ArrayList<String> remoteBranchNames;
    private ArrayList<String> localBranchNames;

    @SuppressWarnings("this-escape")
    public GitflowBranchUtil(Project project, GitRepository repo){
        myProject=project;
        myRepo = repo;

        if (repo != null) {
           update();
        }
    }

    public void update(){
        currentBranchName = GitBranchUtil.getBranchNameOrRev(myRepo);

        GitflowConfigUtil gitflowConfigUtil = GitflowConfigUtil.getInstance(myProject, myRepo);
        gitflowConfigUtil.update();
        branchnameMaster = gitflowConfigUtil.masterBranch;
        branchnameDevelop = gitflowConfigUtil.developBranch;
        prefixFeature = gitflowConfigUtil.featurePrefix;
        prefixRelease = gitflowConfigUtil.releasePrefix;
        prefixHotfix = gitflowConfigUtil.hotfixPrefix;
        prefixBugfix = gitflowConfigUtil.bugfixPrefix;

        initRemoteBranches();
        initLocalBranchNames();
    }

    public String getCurrentBranchName() {
        return currentBranchName;
    }

    public boolean hasGitflow(){
        return myRepo != null
                       && getBranchnameMaster() != null
                       && getBranchnameDevelop() != null
                       && getPrefixFeature() != null
                       && getPrefixRelease() != null
                       && getPrefixHotfix() != null
                       && getPrefixBugfix() != null;
    }

    public String getBranchnameMaster() {
        return branchnameMaster;
    }

    public String getBranchnameDevelop() {
        return branchnameDevelop;
    }

    public String getPrefixFeature() {
        return prefixFeature;
    }

    public String getPrefixRelease() {
        return prefixRelease;
    }

    public String getPrefixHotfix() {
        return prefixHotfix;
    }

    public String getPrefixBugfix() {
        return prefixBugfix;
    }

    public boolean isCurrentBranchFeature(){
        return isBranchFeature(currentBranchName);
    }


    public boolean isCurrentBranchRelease(){
        return currentBranchName.startsWith(prefixRelease);
    }

    public boolean isCurrentBranchHotfix(){
        return isBranchHotfix(currentBranchName);
    }

    public boolean isCurrentBranchBugfix(){
        return isBranchBugfix(currentBranchName);
    }

    //checks whether the current branch also exists on the remote
    public boolean isCurrentBranchPublished(){
        return !getRemoteBranchesWithPrefix(currentBranchName).isEmpty();
    }

    public boolean isBranchFeature(String branchName){
        return branchName.startsWith(prefixFeature);
    }

    public boolean isBranchHotfix(String branchName){
        return branchName.startsWith(prefixHotfix);
    }

    public boolean isBranchBugfix(String branchName){
        return branchName.startsWith(prefixBugfix);
    }

    private void initRemoteBranches() {
        remoteBranches =
                new ArrayList<>(myRepo.getBranches().getRemoteBranches());
        remoteBranchNames = new ArrayList<>();

        for (GitRemoteBranch branch : remoteBranches) {
            remoteBranchNames.add(branch.getName());
        }
    }

    private void initLocalBranchNames(){
        ArrayList<GitLocalBranch> localBranches =
                new ArrayList<>(myRepo.getBranches().getLocalBranches());
        localBranchNames = new ArrayList<>();

        for (GitLocalBranch branch : localBranches) {
            localBranchNames.add(branch.getName());
        }
    }

    //if no prefix specified, returns all remote branches
    public ArrayList<String> getRemoteBranchesWithPrefix(String prefix){
        ArrayList<String> selectedBranches = new ArrayList<>();

        for (String branch : remoteBranchNames) {
            if (branch.contains(prefix)){
                selectedBranches.add(branch);
            }
        }

        return selectedBranches;
    }


    public ArrayList<String> filterBranchListByPrefix(Collection<String> inputBranches,String prefix){
        ArrayList<String> outputBranches= new ArrayList<>();

        for (String branch : inputBranches) {
            if (branch.contains(prefix)){
                outputBranches.add(branch);
            }
        }

        return outputBranches;
    }

    public ArrayList<String> getRemoteBranchNames(){
        return remoteBranchNames;
    }

    public ArrayList<String> getLocalBranchNames() {
        return localBranchNames;
    }

    public GitRemote getRemoteByBranch(String branchName){
        GitRemote remote=null;

        for (GitRemoteBranch branch : remoteBranches) {
            if (branch.getName().equals(branchName)){
                remote=branch.getRemote();
                break;
            }
        }

        return remote;
    }

    public boolean areAllBranchesTracked(String prefix){


        ArrayList<String> localBranches = filterBranchListByPrefix(getLocalBranchNames() , prefix) ;

        //to avoid a vacuous truth value. That is, when no branches at all exist, they shouldn't be
        //considered as "all pulled"
        if (localBranches.isEmpty()){
            return false;
        }

        ArrayList<String> remoteBranches = getRemoteBranchNames();

        //check that every local branch has a matching remote branch
        for (String localBranch : localBranches) {
            boolean hasMatchingRemoteBranch = false;

            for (String remoteBranch : remoteBranches) {
                if (remoteBranch.contains(localBranch)){
                    hasMatchingRemoteBranch=true;
                    break;
                }
            }

            //at least one matching branch wasn't found
            if (!hasMatchingRemoteBranch){
                return false;
            }
        }

        return true;
    }

    public ComboBoxModel<ComboEntry> createBranchComboModel(String defaultBranch) {
        final List<String> branchList = this.getLocalBranchNames();
        branchList.remove(defaultBranch);

        ComboEntry[] entries = new ComboEntry[branchList.size() + 1];
        entries[0] = new ComboEntry(defaultBranch, defaultBranch + " (default)");
        for (int i = 1; i <= branchList.size(); i++) {
            String branchName = branchList.get(i - 1);
            entries[i] = new ComboEntry(branchName, branchName);
        }

        return new DefaultComboBoxModel<>(entries);
    }

    /**
     * Strip a full branch name from its gitflow prefix
     * @param fullBranchName full name of the branch (e.g. 'feature/hello');
     * @return the branch name, prefix free (e.g. 'hello')
     */
    public String stripFullBranchName(String fullBranchName) {
        if (fullBranchName.startsWith(prefixFeature)){
            return fullBranchName.substring(prefixFeature.length());
        }
        else if (fullBranchName.startsWith(prefixHotfix)){
            return fullBranchName.substring(prefixHotfix.length());
        } else if (fullBranchName.startsWith(prefixBugfix)){
            return fullBranchName.substring(prefixBugfix.length());
        } else{
            return null;
        }
    }

    /**
     * An entry for the branch selection dropdown/combo.
     */
    public static class ComboEntry {
        private final String branchName, label;

        public ComboEntry(String branchName, String label) {
            this.branchName = branchName;
            this.label = label;
        }

        public String getBranchName() {
            return branchName;
        }

        @Override
        public String toString() {
            return label;
        }
    }
}
