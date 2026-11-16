package gitflow.actions;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.Key;
import gitflow.ui.NotifyUtil;

public class GitflowErrorsListener extends GitflowLineHandler{

    boolean hasMergeError=false;

    //git-flow's finish scripts print "There were merge conflicts" whenever the underlying
    //"git merge"/"git commit" call fails for ANY reason, not only on a real conflict (e.g. a
    //squash finish whose auto-commit fails). Only treat it as a real conflict, and trigger the
    //merge-tool flow, when git itself actually reported one via a "CONFLICT (...)" line;
    //otherwise handleMerge()'s wait loop spins forever since there is nothing to show.
    private boolean sawRealConflictMarker=false;

    GitflowErrorsListener(Project project){
        myProject=project;
    }

    @Override
    public void onLineAvailable(String line, Key outputType) {
        if (line.contains("'flow' is not a git command")) {
            NotifyUtil.notifyError(myProject, "Error", "Gitflow is not installed");
        }
        if (line.contains("Not a gitflow-enabled repo yet")) {
            NotifyUtil.notifyError(myProject, "Error", "Not a gitflow-enabled repo yet. Please init git flow");
        }
        if (line.contains("CONFLICT (")) {
            sawRealConflictMarker=true;
        }
        if (line.contains("There were merge conflicts")){
            hasMergeError=sawRealConflictMarker;
            if (!sawRealConflictMarker) {
                NotifyUtil.notifyError(myProject, "Error", "Finish failed (not a merge conflict). Please check the Version Control console for the underlying git error.");
            }
        }
    }

}