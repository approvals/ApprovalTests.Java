package org.approvaltests.reporters;

import com.spun.util.io.FileUtils;
import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class AiCodingAgentReporterTest
{
  @TempDir
  File temp;
  private static AiCodingAgentReporter withEnvironment(String... keyValues)
  {
    Map<String, String> env = new HashMap<>();
    for (int i = 0; i < keyValues.length; i += 2)
    {
      env.put(keyValues[i], keyValues[i + 1]);
    }
    return new AiCodingAgentReporter(env);
  }

  @Test
  void detectsClaudeCode()
  {
    assertTrue(withEnvironment("CLAUDECODE", "1").isAiCodingAgentDetected());
  }

  @Test
  void detectsNothingWhenNoVariableIsSet()
  {
    assertFalse(withEnvironment().isAiCodingAgentDetected());
  }

  @Test
  void blankAndFalseValuesAreNotSet()
  {
    for (String value : new String[]{"", "  ", "0", "false", "f", "F", "FALSE", "False"})
    {
      assertFalse(withEnvironment("CLAUDECODE", value).isAiCodingAgentDetected(), "value: '" + value + "'");
    }
  }

  @Test
  void doesNotReportWhenNoAgentIsDetected()
  {
    assertFalse(withEnvironment().report("a.txt", "b.txt"));
  }

  @Test
  void reportWithBothFiles()
  {
    File approved = new File(temp, "Foo.test.approved.txt");
    File received = new File(temp, "Foo.test.received.txt");
    FileUtils.writeFile(approved, "hello world\nline two\n");
    FileUtils.writeFile(received, "hello world\nline 2\n");
    verifyReport(received, approved);
  }

  @Test
  void reportWithMissingApprovedFile()
  {
    File approved = new File(temp, "Foo.test.approved.txt");
    File received = new File(temp, "Foo.test.received.txt");
    FileUtils.writeFile(received, "hello world\n");
    verifyReport(received, approved);
  }

  private void verifyReport(File received, File approved)
  {
    String report = AiCodingAgentReporter.getReport(received.getPath(), approved.getPath(), false);
    Approvals.verify(report.replace(temp.getAbsolutePath() + File.separator, "/abs/path/"));
  }
}
