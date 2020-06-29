package org.approvaltests.namer;

import com.spun.util.tests.TestUtils;
import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

public class AttributeStackSelectorTest
{
  @Test
  void unrollLambda()
  {
    String[] methodNames = {"doStuff", "lambda$handleCallback$0"};
    Approvals.verifyAll("unroll lambda", methodNames,
        m -> String.format("%s -> %s", m, TestUtils.unrollLambda(m)));
  }
}
