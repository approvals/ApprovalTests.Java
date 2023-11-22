package org.approvaltests.inline;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

class CodePartsTest
{
  @Test
  public void testSplit()
  {
    var code = """
        public class Test {
            @Test
            void testyMctest() {
                Customer customer = Customer.create()
                    .existingCustomer()
                    .speakingEnglish()
                    .build();
                stateService.save(TestUtils.userId().build(), customer);
                var expected = ""\"
                    [Customer]: account lookup
                    [     Bot]: Hi there!
                    [     Bot]: Let me try to help.
                    [     Bot]: routes to '12345'
                    ""\";
                verifyConversation(expected, "account lookup");
            }
        }
        """;
    Approvals.verify(CodeParts.splitCode(code, "testyMctest"));
  }
}
