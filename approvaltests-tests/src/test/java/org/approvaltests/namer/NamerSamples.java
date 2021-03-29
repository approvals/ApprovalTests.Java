package org.approvaltests.namer;

import java.io.File;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;
import org.lambda.functions.Function2;

import com.spun.util.tests.TestUtils;
import com.spun.util.tests.TestUtils.SourceDirectoryRestorer;

public class NamerSamples
{
  @Test
  void useAlternativeSourceFileFinder()
  {
    // begin-snippet: define_alternative_source_directory_finder
    Function2<Class, String, File> myFinder = new Function2<Class, String, File>()
    {
      @Override
      public File call(Class clazz, String fileName)
      {
        return new File("src/test/java/" + clazz.getPackage().getName().replaceAll("\\.", File.separator));
      }
    };
    // end-snippet
    // begin-snippet: configure_alternative_source_directory
    try (SourceDirectoryRestorer sdr = TestUtils.registerSourceDirectoryFinder(myFinder))
    {
      Approvals.verify("Ragunath");
    }
    // end-snippet
  }
}
