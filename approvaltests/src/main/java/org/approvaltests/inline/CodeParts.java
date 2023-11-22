package org.approvaltests.inline;

import com.spun.util.ArrayUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CodeParts
{
  public String before;
  public String method;
  public String after;
  public String tab;
  public static CodeParts splitCode(String text, String methodName)
  {
    CodeParts codeParts = new CodeParts();
    String[] lines = text.split("\n");
    int start = 0;
    int end = 0;
    for (int i = 0; i < lines.length; i++)
    {
      String line = lines[i];
      if (start == 0)
      {
        if (line.contains("void " + methodName + "("))
        {
          start = i;
          codeParts.tab = extractLeadingWhitespace(line);
        }
      }
      else if (end == 0)
      {
        if (line.startsWith(codeParts.tab + "}"))
        {
          end = i + 1;
          break;
        }
      }
    }
    codeParts.before = String.join("\n", ArrayUtils.getSubsection(lines, 0, start));
    codeParts.method = String.join("\n", ArrayUtils.getSubsection(lines, start, end));
    codeParts.after = String.join("\n", ArrayUtils.getSubsection(lines, end, lines.length));
    return codeParts;
  }
  public String getFullCode()
  {
    return before + "\n" + method + "\n" + after;
  }
  private static String extractLeadingWhitespace(String text)
  {
    Pattern pattern = Pattern.compile("^\\s+");
    Matcher matcher = pattern.matcher(text);
    if (matcher.find())
    { return matcher.group(); }
    return "\t";
  }
  @Override
  public String toString()
  {
    return "CodeParts{\n" + "before:\n" + before + "\n" + "method:\n" + method + "\n" + "after:\n" + after + "\n"
        + "tab:\n" + tab + "\n" + '}';
  }
}
