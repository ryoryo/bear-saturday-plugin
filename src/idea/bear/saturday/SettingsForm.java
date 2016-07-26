package idea.bear.saturday;

import com.intellij.openapi.options.Configurable;
import com.intellij.openapi.options.ConfigurationException;
import com.intellij.openapi.project.Project;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

/**
 * SettingsForm
 * <p>
 * Created by yoshida on 2016/07/26.
 */
public class SettingsForm implements Configurable {
    private JCheckBox pluginEnabled;
    private JPanel panel;

    private Project project;

    public SettingsForm(@NotNull final Project project) {
        this.project = project;
    }

    @Nls
    @Override
    public String getDisplayName() {
        return "BEAR.Saturday Plugin";
    }

    @Nullable
    @Override
    public String getHelpTopic() {
        return null;
    }

    public JComponent createComponent() {
        return panel;
    }

    @Override
    public boolean isModified() {
        return !pluginEnabled.isSelected() == getSettings().pluginEnabled;
    }

    @Override
    public void apply() throws ConfigurationException {
        getSettings().pluginEnabled = pluginEnabled.isSelected();
    }

    @Override
    public void reset() {
        pluginEnabled.setSelected(getSettings().pluginEnabled);
    }

    @Override
    public void disposeUIResources() {
    }

    private Settings getSettings() {
        return Settings.getInstance(project);
    }

    private void createUIComponents() {
        // TODO: place custom component creation code here
    }
}
