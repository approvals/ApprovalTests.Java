package org.approvaltests.reporters.macosx;

import org.approvaltests.Approvals;
import org.approvaltests.core.Options;
import org.junit.jupiter.api.Test;

class KaleidoscopeDiffReporterTest
{
  @Test
  void kaleidoscope()
  {
    Approvals.verify("Hello, world!", new Options(new KaleidoscopeDiffReporter()));
  }
}
