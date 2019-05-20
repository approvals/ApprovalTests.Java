package org.approvaltests.reporters.intellij;

import org.approvaltests.reporters.GenericDiffReporter;
import org.approvaltests.reporters.macosx.DiffPrograms.Windows;

public class IntelliJUltimateReporter extends GenericDiffReporter {
    public static final IntelliJUltimateReporter INSTANCE = new IntelliJUltimateReporter();

    public IntelliJUltimateReporter() {
        super(Windows.INTELLIJ_U);
    }


}
