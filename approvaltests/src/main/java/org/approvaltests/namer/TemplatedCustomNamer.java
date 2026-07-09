package org.approvaltests.namer;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplatedCustomNamer
{
  // begin-snippet: templated_custom_namer_default_template
  public static final String DEFAULT_TEMPLATE = "{test_source_directory}/{approvals_subdirectory}/{test_file_name}.{test_case_name}.{approved_or_received}.{file_extension}";
  // end-snippet
  public static String resolveTemplate(String template, TemplateFields.TemplateFieldsRetriever fields)
  {
    Map<String, String> substitutions = new HashMap<>();
    substitutions.put(TemplateFields.APPROVED_OR_RECEIVED, fields.getApprovedOrReceived());
    substitutions.put(TemplateFields.APPROVALS_SUBDIRECTORY, fields.getApprovalsSubdirectory());
    substitutions.put(TemplateFields.FILE_EXTENSION, fields.getFileExtension());
    substitutions.put(TemplateFields.RELATIVE_TEST_SOURCE_DIRECTORY, fields.getRelativeTestSourceDirectory());
    substitutions.put(TemplateFields.TEST_CASE_NAME, fields.getTestCaseName());
    substitutions.put(TemplateFields.TEST_FILE_NAME, fields.getTestFileName());
    substitutions.put(TemplateFields.TEST_SOURCE_DIRECTORY, fields.getTestSourceDirectory());
    String result = template;
    for (Map.Entry<String, String> entry : substitutions.entrySet())
    {
      result = result.replace("{" + entry.getKey() + "}", entry.getValue());
    }
    Matcher matcher = Pattern.compile("\\{([^}]+)\\}").matcher(result);
    if (matcher.find())
    { throw new IllegalArgumentException("Unknown template field: {" + matcher.group(1) + "}"); }
    return result;
  }
}
