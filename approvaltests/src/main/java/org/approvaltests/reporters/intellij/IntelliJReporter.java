package org.approvaltests.reporters.intellij;

import org.approvaltests.reporters.DiffInfo;
import org.approvaltests.reporters.DiffPrograms;
import org.approvaltests.reporters.GenericDiffReporter;

import java.lang.ProcessHandle;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.approvaltests.reporters.intellij.Edition.Silicon;


public class IntelliJReporter extends GenericDiffReporter
{
    public static final IntelliJReporter INSTANCE = new IntelliJReporter();
    public IntelliJReporter()
    {
        super(new DiffInfo(getPath(),
                "diff %s %s", GenericDiffReporter.TEXT_FILE_EXTENSIONS));
    }



    static class JetBrainsIDE {
        String name;
        String path;

        JetBrainsIDE(String name, String path) {
            this.name = name;
            this.path = path;
        }
    }
    private static String getPath() {
        try {
            List<JetBrainsIDE> ides = findJetBrainsIDEs();
            if (ides.isEmpty()) {
                return "";
            }
            return ides.get(0).path;
        } catch (Exception e) {
            return "";
        }
    }
    public static List<JetBrainsIDE> findJetBrainsIDEs() {
        List<JetBrainsIDE> ides = new ArrayList<>();
        Set<String> seenPaths = new HashSet<>();
        String[] keywords = {"idea", "pycharm", "webstorm", "phpstorm", "goland", "rider", "clion", "rubymine", "appcode", "datagrip"};

        ProcessHandle.allProcesses()
                .forEach(process -> {
                    Optional<String> commandOpt = process.info().command();
                    if (commandOpt.isPresent()) {
                        String command = commandOpt.get();
                        String lowerCommand = command.toLowerCase();

                        for (String keyword : keywords) {
                            if (lowerCommand.contains(keyword) && isMainExecutable(command, keyword)) {
                                if (!seenPaths.contains(command)) {
                                    seenPaths.add(command);
                                    ides.add(new JetBrainsIDE(keyword, command));
                                }
                                break;
                            }
                        }
                    }
                });

        return ides;
    }

    private static boolean isMainExecutable(String path, String keyword) {
        String lowerPath = path.toLowerCase();
        return lowerPath.endsWith("macos/" + keyword) ||
               lowerPath.endsWith(keyword + ".exe") ||
               lowerPath.endsWith("bin/" + keyword);
    }
}
