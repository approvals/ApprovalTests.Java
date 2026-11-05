package org.approvaltests.partial;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SelectionDetectorTest
{
  @Test
  void detectSampleProject()
  {
    List<File> classpath = Stream.of(System.getProperty("java.class.path").split(File.pathSeparator)).map(File::new)
        .collect(Collectors.toList());
    SelectionDetector.Result result = SelectionDetector
        .detect(new File("src/test/resources/sample-project").getAbsoluteFile(), classpath);
    Approvals.verify("Java files: " + result.javaFiles + "\n" + "Version: " + result.approvaltestsVersion + "\n"
        + "Entry points:\n  " + String.join("\n  ", result.entryPoints) + "\n" + "Unresolved:\n  "
        + String.join("\n  ", result.unresolved) + "\n");
  }
}
