package idea.bear.saturday.action;

import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.fileEditor.FileEditorManager;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import org.jetbrains.annotations.NotNull;

/**
 * ページクラス〜テンプレート間を行き来する機能を提供するクラス
 * <p>
 * Created by ryoryo on 2016/07/26.
 */
public class PageGotoTemplateFile extends AnAction {

    @Override
    public void update(@NotNull AnActionEvent e) {
        VirtualFile targetFile = getTargetFile(e);

        if (targetFile == null || targetFile.getExtension() == null) {
            e.getPresentation().setEnabledAndVisible(false);
            return;
        }

        String goTo = "Page Class File";
        if (targetFile.getExtension().equals("tpl")) {
            goTo = "Template File";
        }

        e.getPresentation().setDescription("Go to " + goTo + ":" + targetFile.getPath());
    }

    @Override
    public void actionPerformed(@NotNull AnActionEvent e) {
        Project project = e.getProject();
        if (project == null) {
            return;
        }

        VirtualFile targetFile = getTargetFile(e);
        if (targetFile == null) {
            return;
        }

        FileEditorManager.getInstance(project).openFile(targetFile, true);
    }

    private VirtualFile getTargetFile(@NotNull AnActionEvent e) {
        if (e.getProject() == null) {
            return null;
        }

        Project project = e.getProject();
        VirtualFile virtualFile = e.getData(PlatformDataKeys.VIRTUAL_FILE);

        if (project == null
                || virtualFile == null
                || (!virtualFile.getPath().contains("/htdocs/")
                && !virtualFile.getPath().contains("/App/views/pages/"))
                || e.getPresentation().getText() == null) {
            return null;
        }

        String filePath = virtualFile.getPath();
        if (e.getPresentation().getText().contains("Template")
                && filePath.contains("/htdocs/")) {
            filePath = filePath.replace(project.getBaseDir().getPath() + "/htdocs", "");
            filePath = "App/views/pages" + filePath.replace(".php", ".tpl");
        } else if (e.getPresentation().getText().contains("Class")
                && filePath.contains("/App/views/pages/")) {
            filePath = filePath.replace(project.getBaseDir().getPath() + "/App/views/pages", "");
            filePath = "htdocs" + filePath.replace(".tpl", ".php");
        } else {
            filePath = null;
        }

        if (filePath == null) {
            return null;
        }

        return project.getBaseDir().findFileByRelativePath(filePath);
    }
}
