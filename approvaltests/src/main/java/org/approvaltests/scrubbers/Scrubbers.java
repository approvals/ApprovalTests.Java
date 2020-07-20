package org.approvaltests.scrubbers;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.lambda.functions.Function1;

import com.spun.util.StringUtils;

public class Scrubbers
{
  public static String scrubGuid(String input)
  {
    Pattern p = Pattern.compile("[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}");
    Map<String, Integer> guids = new HashMap<>();
    Function1<String, String> replacer = s -> {
      guids.putIfAbsent(s, guids.size() + 1);
      return "guid_" + guids.get(s);
    };
    return StringUtils.replaceAll(input, p, replacer);
  }

}
