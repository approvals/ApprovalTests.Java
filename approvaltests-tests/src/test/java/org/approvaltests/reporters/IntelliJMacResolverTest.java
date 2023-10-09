package org.approvaltests.reporters;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;
import org.lambda.query.Queryable;

class IntelliJMacResolverTest
{
  @Test
  void testFindIt()
  {
    Queryable<String> q = new Queryable<>(String.class);
    IntelliJMacResolver.getDiffInfo("user/lars", f -> {
      q.add(f);
      return false;
    });
    Approvals.verifyAll("Files", q);
  }
}
