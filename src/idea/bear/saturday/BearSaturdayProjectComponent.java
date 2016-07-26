package idea.bear.saturday;

import com.intellij.openapi.components.ProjectComponent;
import com.intellij.openapi.diagnostic.Logger;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class BearSaturdayProjectComponent implements ProjectComponent {

    final private static Logger LOG = Logger.getInstance("BEAR.Saturday Plugin");

    private Project project;

    public BearSaturdayProjectComponent(Project project) {
        this.project = project;
    }

    public void initComponent() {
    }

    public void disposeComponent() {
    }

    @NotNull
    public String getComponentName() {
        return "BearSaturdayProjectComponent";
    }

    public void projectOpened() {
    }

    public void projectClosed() {
    }

    public static Logger getLogger() {
        return LOG;
    }

    public static boolean isEnabled(@Nullable Project project) {
        return project != null && Settings.getInstance(project).pluginEnabled;
    }
}
