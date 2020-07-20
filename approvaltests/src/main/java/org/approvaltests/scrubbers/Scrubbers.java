package org.approvaltests.scrubbers;

import org.lambda.functions.Function1;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Scrubbers
{
  public static String scrubGuid(String input)
  {
    Pattern p = Pattern.compile("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}");

    Map<String, Integer> guids = new HashMap<>();
    Function1<String, String> replacer = s -> {
      guids.put(s, guids.size() + 1);
      return "guid_" + guids.get(s);
    };

    Matcher matcher = p.matcher(input);
    boolean result = matcher.find();
    if (result)
    {
      StringBuilder sb = new StringBuilder();
      int start = 0;
      do
      {
        sb.append(input, start, matcher.start());
        sb.append(replacer.call(matcher.group()));
        start = matcher.end();
        result = matcher.find();
      }
      while (result);
      sb.append(input.substring(start));
      return sb.toString();
    }
    return input;
  }
}
