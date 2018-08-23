package org.approvaltests.reporters.windows;

import org.approvaltests.reporters.GenericDiffReporter;
import org.approvaltests.reporters.macosx.DiffPrograms.Windows;

public class IntelliJUltimateReporter extends GenericDiffReporter {
    public static final IntelliJUltimateReporter INSTANCE = new IntelliJUltimateReporter();

    private IntelliJUltimateReporter() {
        super(Windows.INTELLIJ_U);
    }


}
