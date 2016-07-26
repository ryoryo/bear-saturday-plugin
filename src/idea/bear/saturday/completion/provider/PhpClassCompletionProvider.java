package idea.bear.saturday.completion.provider;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.openapi.project.Project;
import com.intellij.util.ProcessingContext;
import com.jetbrains.php.PhpIndex;
import com.jetbrains.php.completion.PhpLookupElement;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import org.jetbrains.annotations.NotNull;

/**
 * Php Class Provider
 * <p>
 * Created by ryoryo on 2016/07/26.
 */
public class PhpClassCompletionProvider extends CompletionProvider {

    @Override
    protected void addCompletions(@NotNull CompletionParameters completionParameters, ProcessingContext processingContext, @NotNull CompletionResultSet completionResultSet) {
        Project project = completionParameters.getPosition().getContainingFile().getProject();
        PhpIndex phpIndex = PhpIndex.getInstance(project);
        for (String className : phpIndex.getAllClassNames(completionResultSet.getPrefixMatcher())) {
            PhpClass phpClass = phpIndex.getClassByName(className);
            if (phpClass != null) {
                completionResultSet.addElement(new PhpLookupElement(phpClass));
            }
        }
    }
}
