package com.spun.util.persistence;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

import java.util.Stack;

public class SaverTest
{
  @Test
  void testSaving()
  {
    Stack<String> strings = new Stack<>();
    count(10, s -> strings.push(s));
    Approvals.verifyAll("", strings);
  }
  private void count(int num, Saver<String> saver)
  {
    for (int i = 0; i < num; i++)
    {
      saver.save("" + i);
    }
  }
}
