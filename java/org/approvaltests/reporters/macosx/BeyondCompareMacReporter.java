package org.approvaltests.reporters.macosx;



import java.text.MessageFormat;



import org.approvaltests.reporters.GenericDiffReporter;



public class BeyondCompareMacReporter extends GenericDiffReporter {

private static final String DIFF_PROGRAM = "/Applications/Beyond Compare.app/Contents/MacOS/bcomp";

static final String MESSAGE = MessageFormat.format("Unable to find Beyond Compare at {0}", DIFF_PROGRAM);

public static final BeyondCompareMacReporter INSTANCE = new BeyondCompareMacReporter();



public BeyondCompareMacReporter() {

super(DIFF_PROGRAM, MESSAGE);

}

}

