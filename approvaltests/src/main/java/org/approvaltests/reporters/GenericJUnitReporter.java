package org.approvaltests.reporters;

import com.spun.util.ObjectUtils;
import com.spun.util.io.FileUtils;

import java.io.File;

public abstract class GenericJUnitReporter implements EnvironmentAwareReporter
{
    @Override
    public void report(String received, String approved)
    {
        String aText = new File(approved).exists() ? FileUtils.readFile(approved) : "";
        String rText = FileUtils.readFile(received);
        String approveCommand = "To approve run : " + ClipboardReporter.getAcceptApprovalText(received, approved);
        System.out.println(approveCommand);
        assertEquals(aText, rText);
    }

    @Override
    public boolean isWorkingInThisEnvironment(String forFile)
    {
        try
        {
            ObjectUtils.loadClass(getClassNameToTestJunitVersion());
        }
        catch (Throwable t)
        {
            return false;
        }
        return GenericDiffReporter.isFileExtensionValid(forFile, GenericDiffReporter.TEXT_FILE_EXTENSIONS);
    }
    abstract void assertEquals(String aText, String rText);
    abstract String getClassNameToTestJunitVersion();
}
