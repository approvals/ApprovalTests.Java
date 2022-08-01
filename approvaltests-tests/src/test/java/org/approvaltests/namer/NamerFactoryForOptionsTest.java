package org.approvaltests.namer;

import org.approvaltests.Approvals;
import org.approvaltests.OptionsTest;
import org.approvaltests.core.Options;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

import static org.junit.jupiter.api.Assertions.assertEquals;

class NamerFactoryForOptionsTest
{
  @EnabledOnOs(OS.MAC)
  @Test
  void testOsSpecificTest()
  {
    Options options = Approvals.NAMES.asOsSpecificTest();
    ApprovalNamer namer = options.forFile().getNamer();
    String name = namer.getApprovedFile(".txt").getName();
    assertEquals("NamerFactoryForOptionsTest.testOsSpecificTest.Mac_OS_X.approved.txt", name);
  }
  @Disabled("TODO: only on Lars' laptop")
  //  @EnabledOnOs(OS.MAC)
  @Test
  void testMachineSpecificTestAndOsSPecific()
  {
    Options options = Approvals.NAMES.asOsSpecificTest().and(Approvals.NAMES::asMachineNameSpecificTest);
    ApprovalNamer namer = options.forFile().getNamer();
    String name = namer.getApprovedFile(".txt").getName();
    assertEquals("NamerFactoryForOptionsTest.testMachineSpecificTest.Mac_OS_X.lars-mbp-14.approved.txt", name);
  }
  @Test
  void allHaveOptions()
  {
    OptionsTest.verifyEachVerifyMethodHasOneWithOptions(NamerFactoryForOptions.class, "as");
  }
}
