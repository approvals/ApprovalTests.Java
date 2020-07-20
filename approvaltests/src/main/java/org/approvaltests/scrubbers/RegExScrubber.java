package org.approvaltests.scrubbers;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.approvaltests.core.Scrubber;
import org.lambda.functions.Function1;

import com.spun.util.StringUtils;

public class RegExScrubber implements Scrubber
{
  private final Pattern pattern;
  private final String  replacementPrefix;
  public RegExScrubber(String pattern, String replacementPrefix)
  {
    this(Pattern.compile(pattern), replacementPrefix);
  }
  public RegExScrubber(Pattern pattern, String replacementPrefix)
  {
    this.pattern = pattern;
    this.replacementPrefix = replacementPrefix;
  }
  @Override
  public String scrub(String input)
  {
    Map<String, Integer> matches = new HashMap<>();
    Function1<String, String> replacer = s -> {
      matches.putIfAbsent(s, matches.size() + 1);
      return replacementPrefix + matches.get(s);
    };
    return StringUtils.replaceAll(input, pattern, replacer);
  }
}
