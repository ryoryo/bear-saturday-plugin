package idea.bear.saturday.completion.provider;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.completion.impl.CamelHumpMatcher;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.project.Project;
import com.intellij.util.ProcessingContext;
import com.jetbrains.php.PhpIndex;
import com.jetbrains.php.lang.psi.elements.PhpClass;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collection;

/**
 * Php Class Provider
 * <p>
 * Created by ryoryo on 2016/07/26.
 */
public class PageResourceCompletionProvider extends CompletionProvider {

    @Override
    protected void addCompletions(@NotNull CompletionParameters completionParameters, ProcessingContext processingContext, @NotNull CompletionResultSet completionResultSet) {
        Project project = completionParameters.getPosition().getContainingFile().getProject();
        PhpIndex phpIndex = PhpIndex.getInstance(project);
        Collection<String> pageIndex = phpIndex.getAllClassNames(new CamelHumpMatcher("Page_"));
        Object[] pageSortIndex = pageIndex.toArray();
        Arrays.sort(pageSortIndex);
        for (Object className : pageSortIndex) {
            PhpClass phpClass = phpIndex.getClassByName((String) className);
            if (phpClass != null) {
                String er = "page://self/" + phpClass.getName().replace("Page_", "").replace("_", "/");
                completionResultSet.addElement(LookupElementBuilder.create(er));
            }
        }
    }
}
