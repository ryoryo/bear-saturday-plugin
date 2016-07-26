package idea.bear.saturday.completion.provider;

import com.intellij.codeInsight.completion.CompletionParameters;
import com.intellij.codeInsight.completion.CompletionProvider;
import com.intellij.codeInsight.completion.CompletionResultSet;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.openapi.project.Project;
import com.intellij.util.ProcessingContext;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;

/**
 * App/Ro配下のファイル一覧のコード補完を提供する
 * <p>
 * Created by ryoryo on 2016/07/26.
 */
public class AppRoCompletionProvider extends CompletionProvider<CompletionParameters> {

    ArrayList<String> list = new ArrayList<>();

    @Override
    protected void addCompletions(@NotNull CompletionParameters completionParameters,
                                  ProcessingContext processingContext,
                                  @NotNull CompletionResultSet completionResultSet) {
        Project project = completionParameters.getPosition().getContainingFile().getProject();

        Path dir = Paths.get(project.getBaseDir().getPath() + "/App/Ro/");

        try {
            Files.walkFileTree(dir, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    list.add(file.toString());
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        Collections.sort(list);

        for (String file : list) {
            String er = file.replace(project.getBaseDir().getPath() + "/App/Ro/", "").replace(".php", "");
            if (er.contains("_untitiled")) {
                continue;
            }
            completionResultSet.addElement(LookupElementBuilder.create(er));
        }
    }
}
