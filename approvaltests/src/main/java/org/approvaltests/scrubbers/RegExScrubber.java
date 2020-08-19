package org.approvaltests.scrubbers;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import org.approvaltests.core.Scrubber;
import org.lambda.functions.Function1;

import com.spun.util.StringUtils;

public class RegExScrubber implements Scrubber
{
  private final Pattern                    pattern;
  private final Function1<Integer, String> replacement;
  public RegExScrubber(String pattern, Function1<Integer, String> replacement)
  {
    this(compilePattern(pattern), replacement);
  }
  public RegExScrubber(String pattern, String replacement)
  {
    this(compilePattern(pattern), replacement);
  }

  private static Pattern compilePattern(String pattern) {
    return StringUtils.isNonZero(pattern) ? Pattern.compile(pattern) : null;
  }

  public RegExScrubber(Pattern pattern, String replacement)
  {
    this(pattern, n -> replacement);
  }
  public RegExScrubber(Pattern pattern, Function1<Integer, String> replacement)
  {
    this.pattern = pattern;
    this.replacement = replacement;
  }
  @Override
  public String scrub(String input)
  {
    if (pattern == null) { return input;}
    Map<String, Integer> matches = new HashMap<>();
    Function1<String, String> replacer = s -> {
      matches.putIfAbsent(s, matches.size() + 1);
      return replacement.call(matches.get(s));
    };
    return StringUtils.replaceAll(input, pattern, replacer);
  }
}
