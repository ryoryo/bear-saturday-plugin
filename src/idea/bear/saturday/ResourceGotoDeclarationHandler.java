package idea.bear.saturday;

import com.intellij.codeInsight.navigation.actions.GotoDeclarationHandler;
import com.intellij.openapi.actionSystem.DataContext;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.PsiManager;
import com.intellij.psi.impl.source.tree.LeafPsiElement;
import com.jetbrains.php.PhpIndex;
import com.jetbrains.php.lang.psi.elements.PhpClass;

import java.util.ArrayList;
import java.util.List;

/**
 * RoのURLから実ファイルへジャンプする機能を提供するクラス
 * <p>
 * Created by ryoryo on 2016/07/26.
 */
public class ResourceGotoDeclarationHandler implements GotoDeclarationHandler {

    @Override
    public PsiElement[] getGotoDeclarationTargets(PsiElement psiElement, int i, Editor editor) {

        Project project = psiElement.getProject();

        if (!BearSaturdayProjectComponent.isEnabled(project)) {
            return new PsiElement[0];
        }

        if (!((LeafPsiElement) psiElement).getElementType().toString().toLowerCase().contains("string")) {
            return new PsiElement[0];
        }

        String resourceName = psiElement.getText();

        if (resourceName.contains(" ")) {
            return new PsiElement[0];
        }

        String roDir = "";
        String roClass = resourceName;

        VirtualFile targetFile;
        if (resourceName.startsWith("/")
                && !resourceName.endsWith(".tpl")) {
            targetFile = project.getBaseDir().findFileByRelativePath(resourceName);
            if (targetFile == null) {
                targetFile = project.getBaseDir().findFileByRelativePath("vendor" + resourceName);
            }
            if (targetFile == null) {
                targetFile = project.getBaseDir().findFileByRelativePath("lib" + resourceName);
            }
        } else if (resourceName.startsWith("/")
                && resourceName.endsWith(".tpl")) {
            targetFile = project.getBaseDir().findFileByRelativePath("App/views/pages" + resourceName);
        } else if (resourceName.endsWith(".tpl")) {
            targetFile = project.getBaseDir().findFileByRelativePath("App/views/" + resourceName);
        } else {
            PhpClass phpClass = PhpIndex.getInstance(project).getClassByName(roDir + roClass);
            if (phpClass == null) {
                roDir = "App_Ro_";
                roClass = roClass.replace("/", "_");
                phpClass = PhpIndex.getInstance(project).getClassByName(roDir + roClass);
            }

            if (phpClass == null) {
                return new PsiElement[0];
            }

            targetFile = phpClass.getContainingFile().getVirtualFile();
        }

        if (targetFile == null) {
            return new PsiElement[0];
        }

        PsiFile psiFile = PsiManager.getInstance(project).findFile(targetFile);
        List<PsiElement> psiElements = new ArrayList<>();
        psiElements.add(psiFile);

        return psiElements.toArray(new PsiElement[psiElements.size()]);
    }

    public String getActionText(DataContext var1) {
        return null;
    }
}
