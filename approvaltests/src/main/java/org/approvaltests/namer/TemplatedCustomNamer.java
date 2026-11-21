package org.approvaltests.namer;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TemplatedCustomNamer
{
  // begin-snippet: templated_custom_namer_default_template
  public static final String DEFAULT_TEMPLATE = "{test_source_directory}/{approvals_subdirectory}/{test_file_name}.{test_case_name}{additional_information}.{approved_or_received}.{file_extension}";
  // end-snippet
  public static ApprovalNamer create(String template)
  {
    return new StackTraceNamer(new StackTraceNamer().getTemplate(), template);
  }

  public static String resolveTemplate(String template, TemplateFields.TemplateFieldsRetriever fields)
  {
    return resolveTemplate(template, TemplateFieldValues.from(fields));
  }

  public static String resolveTemplate(String template, TemplateFieldValues fields)
  {
    Map<String, String> substitutions = new HashMap<>();
    substitutions.put(TemplateFields.APPROVED_OR_RECEIVED, fields.approvedOrReceived);
    substitutions.put(TemplateFields.APPROVALS_SUBDIRECTORY, fields.approvalsSubdirectory);
    substitutions.put(TemplateFields.FILE_EXTENSION, fields.fileExtension);
    substitutions.put(TemplateFields.RELATIVE_TEST_SOURCE_DIRECTORY, fields.relativeTestSourceDirectory);
    substitutions.put(TemplateFields.TEST_CASE_NAME, fields.testCaseName);
    substitutions.put(TemplateFields.TEST_FILE_NAME, fields.testFileName);
    substitutions.put(TemplateFields.TEST_SOURCE_DIRECTORY, fields.testSourceDirectory);
    substitutions.put(TemplateFields.ADDITIONAL_INFORMATION, fields.additionalInformation);
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
