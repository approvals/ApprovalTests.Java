package org.approvaltests.awt;

import java.util.Arrays;
import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

class GifSequenceWriterTest
{
  @Test
  void applesauce()
  {
    Approvals.verifyAll("", new Boolean[]{true, false},
        b -> Arrays.toString(GifSequenceWriter.getBytesForLoopContinuously(b)));
  }
}
