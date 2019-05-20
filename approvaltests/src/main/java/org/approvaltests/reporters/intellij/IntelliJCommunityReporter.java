package org.approvaltests.reporters.intellij;

import org.approvaltests.reporters.GenericDiffReporter;
import org.approvaltests.reporters.macosx.DiffPrograms.Windows;

import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;

import static java.lang.Integer.parseInt;

public class IntelliJCommunityReporter extends GenericDiffReporter {
    public static final IntelliJCommunityReporter INSTANCE = new IntelliJCommunityReporter();

    public IntelliJCommunityReporter() {
        super(Windows.INTELLIJ_C);
    }

}
