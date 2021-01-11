package org.approvaltests.scrubbers;

import com.spun.util.ArrayUtils;
import org.lambda.functions.Function1;
import org.lambda.query.Query;

import com.spun.util.FormattedException;

public class DateScrubber extends RegExScrubber
{
  public DateScrubber(String pattern)
  {
    this(pattern, n -> "[Date" + n + "]");
  }
  public DateScrubber(String pattern, Function1<Integer, String> replacement)
  {
    super(pattern, replacement);
  }
  public static SupportedFormat[] getSupportedFormats()
  {
    return new SupportedFormat[]{_("[a-zA-Z]{3} [a-zA-Z]{3} \\d{2} \\d{2}:\\d{2}:\\d{2}", "Tue May 13 16:30:00"),
                                 _("[a-zA-Z]{3} [a-zA-Z]{3} \\d{2} \\d{4} \\d{2}:\\d{2}:\\d{2}.\\d{3}",
                                     "Tue May 13 2014 23:30:00.789"),
                                 _("[a-zA-Z]{3} [a-zA-Z]{3} \\d{2} \\d{2}:\\d{2}:\\d{2} -\\d{4} \\d{4}",
                                     "Tue May 13 16:30:00 -0800 2014"),
                                 _("\\d{2} [a-zA-Z]{3} \\d{4} \\d{2}:\\d{2}:\\d{2},\\d{3}",
                                     "13 May 2014 23:50:49,999"),
                                 _("[a-zA-Z]{3} \\d{2}, \\d{4} \\d{2}:\\d{2}:\\d{2} [a-zA-Z]{2} [a-zA-Z]{3}",
                                     "May 13, 2014 11:30:00 PM PST"),
                                 _("\\d{2}:\\d{2}:\\d{2}", "23:30:00"),
                                 _("\\d{4}/\\d{2}/\\d{2} \\d{2}:\\d{2}:\\d{2}.\\d{2}\\d",
                                     "2014/05/13 16:30:59.786"),
                                 _("\\d{4}-\\d{1,2}-\\d{1,2}T\\d{1,2}:\\d{2}Z", "2020-9-10T08:07Z",
                                     "2020-09-9T08:07Z", "2020-09-10T8:07Z", "2020-09-10T08:07Z"),
                                 _("\\d{4}-\\d{1,2}-\\d{1,2}T\\d{1,2}:\\d{2}:\\d{2}Z", "2020-09-10T08:07:89Z"),
                                 _("\\d{4}-\\d{1,2}-\\d{1,2}T\\d{1,2}:\\d{2}\\:\\d{2}\\.\\d{3}Z",
                                     "2020-09-10T01:23:45.678Z")};
  }
  private static SupportedFormat _(String regex, String... examples)
  {
    return new SupportedFormat(regex, examples);
  }
  public static DateScrubber getScrubberFor(String formattedExample)
  {
    for (SupportedFormat pattern : getSupportedFormats())
    {
      DateScrubber scrubber = new DateScrubber(pattern.getRegex());
      if ("[Date1]".equals(scrubber.scrub(formattedExample)))
      { return scrubber; }
    }
    throw new FormattedException(
        "No match found for %s.\n Feel free to add your date at https://github.com/approvals/ApprovalTests.Java/issues/112 \n Current supported formats are: %s",
        formattedExample, Query.select(getSupportedFormats(), SupportedFormat::getRegex));
  }
  @Override
  public String toString()
  {
    return super.toString();
  }
  public static class SupportedFormat
  {
    private final String[] examples;
    private final String   regex;
    public SupportedFormat(String regex, String... examples)
    {
      if (ArrayUtils.isEmpty(examples)) {
        throw new IllegalArgumentException("must have at least one example");
      }
      this.examples = examples;
      this.regex = regex;
    }
    public String[] getExamples()
    {
      return examples;
    }
    public String getRegex()
    {
      return regex;
    }
  }
}
