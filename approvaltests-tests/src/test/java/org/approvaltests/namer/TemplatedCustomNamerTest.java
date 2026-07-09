package org.approvaltests.namer;

import org.approvaltests.Approvals;
import org.approvaltests.core.Options;
import org.junit.jupiter.api.Test;

public class TemplatedCustomNamerTest
{
  @Test
  public void testGetApprovedFilename()
  {
    String expected = """
        /my/source/directory/TestFileName.testCaseName.txt
        """;
    String template = "/my/source/directory/{test_file_name}.{test_case_name}.{file_extension}";
    String path = TemplatedCustomNamer.resolveTemplate(template, new TemplateTestFields());
    Approvals.verify(path, new Options().inline(expected));
  }
  //
  //  @Test
  //  public void testGetReceivedFilename()
  //  {
  //    TemplatedCustomNamer namer = new TemplatedCustomNamer(
  //        "/my/source/directory/{approved_or_received}/{test_file_name}.{test_case_name}.{file_extension}");
  //    Approvals.verify(namer.getReceivedFile(".txt").getPath(), new Options()
  //        .inline("/my/source/directory/received/TemplatedCustomNamerTest.testGetReceivedFilename.txt\n"));
  //  }
  //
  //  @Test
  //  public void testRepeatedTemplateField()
  //  {
  //    TemplatedCustomNamer namer = new TemplatedCustomNamer("/root/{test_case_name}/{test_case_name}.txt");
  //    Approvals.verify(namer.getApprovedFile(".txt").getPath(),
  //        new Options().inline("/root/testRepeatedTemplateField/testRepeatedTemplateField.txt\n"));
  //  }
  //
  //  @Test
  //  public void testTemplateFields() throws IllegalAccessException
  //  {
  //    List<String> lines = new ArrayList<>();
  //    for (Field field : TemplateFields.class.getDeclaredFields())
  //    {
  //      String name = (String) field.get(null);
  //      lines.add(name + " = {" + name + "}");
  //    }
  //    Collections.sort(lines);
  //    Approvals.verify(String.join("\n", lines),
  //        new Options().inline("approvals_subdirectory = {approvals_subdirectory}\n"
  //            + "approved_or_received = {approved_or_received}\n" + "file_extension = {file_extension}\n"
  //            + "relative_test_source_directory = {relative_test_source_directory}\n"
  //            + "test_case_name = {test_case_name}\n" + "test_file_name = {test_file_name}\n"
  //            + "test_source_directory = {test_source_directory}\n"));
  //  }
  //
  //  @Test
  //  public void testRelativeTestSourceDirectoryField()
  //  {
  //    TemplatedCustomNamer namer = new TemplatedCustomNamer(
  //        "/fixed/{relative_test_source_directory}/{approved_or_received}/{test_file_name}.{test_case_name}.{file_extension}");
  //    String path = namer.getApprovedFile(".txt").getPath();
  //    Assert.assertFalse("relative_test_source_directory must be substituted",
  //        path.contains("{relative_test_source_directory}"));
  //    Assert
  //        .assertTrue(path.endsWith("/approved/TemplatedCustomNamerTest.testRelativeTestSourceDirectoryField.txt"));
  //  }
  //
  //  @Test(expected = IllegalArgumentException.class)
  //  public void testUnknownTemplateFieldThrowsException()
  //  {
  //    TemplatedCustomNamer namer = new TemplatedCustomNamer("/my/source/directory/{not_a_real_field}.txt");
  //    namer.getApprovedFile(".txt");
  //  }
  //
  //  @Test
  //  public void testGetApprovalNameIsSimplified()
  //  {
  //    TemplatedCustomNamer namer = new TemplatedCustomNamer(TemplatedCustomNamer.DEFAULT_TEMPLATE);
  //    Assert.assertEquals("testGetApprovalNameIsSimplified", namer.getApprovalName());
  //  }
  //
  //  // begin-snippet: templated_custom_namer_default
  //  @Test
  //  public void testDefaultTemplateMatchesStackTraceNamer()
  //  {
  //    StackTraceNamer stackTraceNamer = new StackTraceNamer();
  //    TemplatedCustomNamer templatedNamer = new TemplatedCustomNamer(TemplatedCustomNamer.DEFAULT_TEMPLATE);
  //    Assert.assertEquals(stackTraceNamer.getApprovedFile(".txt").getAbsolutePath(),
  //        templatedNamer.getApprovedFile(".txt").getAbsolutePath());
  //    Assert.assertEquals(stackTraceNamer.getReceivedFile(".txt").getAbsolutePath(),
  //        templatedNamer.getReceivedFile(".txt").getAbsolutePath());
  //  }
  //  // end-snippet
  //
  //  // begin-snippet: templated_custom_namer_custom_template
  //  @Test
  //  public void testCustomTemplate()
  //  {
  //    String template = "/fixed/custom/{test_file_name}_{test_case_name}.{approved_or_received}.{file_extension}";
  //    TemplatedCustomNamer namer = new TemplatedCustomNamer(template);
  //    // end-snippet
  //    Approvals.verify(namer.getApprovedFile(".txt").getPath(),
  //        new Options().inline("/fixed/custom/TemplatedCustomNamerTest_testCustomTemplate.approved.txt\n"));
  //  }
  //
  //  // begin-snippet: templated_custom_namer_with_options
  //  @Test
  //  public void testUsageWithOptions()
  //  {
  //    String template = "{test_source_directory}/{test_file_name}.{test_case_name}.{approved_or_received}.{file_extension}";
  //    Options options = new Options().forFile().withNamer(new TemplatedCustomNamer(template));
  //    // end-snippet
  //    Assert.assertTrue(options.forFile().getNamer().getApprovedFile(".txt").getAbsolutePath()
  //        .endsWith("TemplatedCustomNamerTest.testUsageWithOptions.approved.txt"));
  //  }
}
