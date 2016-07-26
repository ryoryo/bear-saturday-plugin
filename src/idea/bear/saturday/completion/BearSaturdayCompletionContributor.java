package idea.bear.saturday.completion;

import com.intellij.codeInsight.completion.CompletionContributor;
import com.intellij.codeInsight.completion.CompletionType;
import com.intellij.patterns.ElementPattern;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.patterns.StandardPatterns;
import com.intellij.psi.PsiElement;

import com.jetbrains.php.lang.parser.PhpElementTypes;
import com.jetbrains.php.lang.patterns.PhpPatterns;
import com.jetbrains.php.lang.psi.elements.StringLiteralExpression;
import idea.bear.saturday.completion.provider.AppRoCompletionProvider;
import idea.bear.saturday.completion.provider.PageResourceCompletionProvider;
import idea.bear.saturday.completion.provider.PhpClassCompletionProvider;

/**
 * BEAR::dependencyとリソース用URIにコード補完を提供する
 * <p>
 * Created by ryoryo on 2016/07/26.
 */
public class BearSaturdayCompletionContributor extends CompletionContributor {

    public BearSaturdayCompletionContributor() {
        extend(CompletionType.BASIC, dependencyPattern(), new PhpClassCompletionProvider());
        extend(CompletionType.BASIC, resourcePattern(), new AppRoCompletionProvider());
        extend(CompletionType.BASIC, resourcePattern(), new PageResourceCompletionProvider());
    }

    public static ElementPattern<PsiElement> dependencyPattern() {
        return PlatformPatterns.psiElement(PsiElement.class).withParent(
                PlatformPatterns.psiElement(StringLiteralExpression.class).withParent(
                        PlatformPatterns.psiElement(PhpElementTypes.PARAMETER_LIST).withParent(
                                PlatformPatterns.psiElement(PhpElementTypes.METHOD_REFERENCE).referencing(
                                        PhpPatterns.psiElement().withElementType(
                                                PhpElementTypes.CLASS_METHOD
                                        ).withName(
                                                StandardPatterns.string().oneOf("dependency")
                                        ).withParent(
                                                PhpPatterns.psiElement().withName("BEAR")
                                        )
                                )
                        )
                )
        );
    }

    public static ElementPattern<PsiElement> resourcePattern() {
        ElementPattern<PsiElement> pattern = PlatformPatterns.psiElement(PsiElement.class).withParent(
                PlatformPatterns.psiElement(StringLiteralExpression.class).withParent(
                        PlatformPatterns.psiElement(PhpElementTypes.ARRAY_VALUE).withParent(
                                PlatformPatterns.psiElement(PhpElementTypes.HASH_ARRAY_ELEMENT).withChild(
                                        PlatformPatterns.psiElement(PhpElementTypes.ARRAY_KEY).withText(
                                                PlatformPatterns.string().contains("uri")
                                        )
                                )
                        )
                )
        );

        return pattern;
    }
}
