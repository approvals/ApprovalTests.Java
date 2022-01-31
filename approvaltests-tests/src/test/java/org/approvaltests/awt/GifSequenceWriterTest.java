package org.approvaltests.awt;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

class GifSequenceWriterTest
{
  @Test
  void applesauce()
  {
    Approvals.verifyAll("", new Boolean[]{true, false},
        b -> Arrays.toString(GifSequenceWriter.getBytesForLoopContinuously(b)));
  }
}
