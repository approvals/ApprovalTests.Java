package org.approvaltests;

import org.approvaltests.core.Options;
import org.approvaltests.inline.InlineComparator;
import org.junit.jupiter.api.Test;

import java.util.List;

public class InlineApprovalsTest
{
  @Test
  public void test()
  {
    var expected = """
        Hello World***
        """;
    Approvals.verify("Hello Llewellyn***", Options.inline(expected));
  }
  //Test received file
  //test method can have arguments and they are not ignored
  //Does this work with annotations on tests
  //Concatenating strings
  //There's some code before verify that is not replacing the result

  @Test
  public void testCreateReceivedFileText()
  {
    var inputs = List.of( """
                  @Test
                  public void testyMctest() {
                    var expected = ""\"
                        Hello World***
                        ""\";
                    Approvals.verify("", Options.inline(expected));
                  }
            """,
            """
                 @Test
                 public void testyMctest()
                 {
                   var expected = ""\"
                       Hello World***
                       ""\";
                   Approvals.verify("", Options.inline(expected));
                 }
            """,
            """
                  @Test
                  public void testyMctest(int foo) {
                    var expected = ""\"
                        Hello World***
                        ""\";
                    Approvals.verify("", Options.inline(expected));
                  }
            """
    );
    Approvals.verifyAll("Substitution",inputs, i -> InlineComparator.createNewReceivedFileText(i, "1\n2", "testyMctest"));
  }
}
