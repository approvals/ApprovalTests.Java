package org.approvaltests.scrubbers;

import com.spun.util.ArrayUtils;
import com.spun.util.FormattedException;
import org.lambda.functions.Function1;
import org.lambda.query.Query;

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
    return new SupportedFormat[]{__("[a-zA-Z]{3} [a-zA-Z]{3} \\d{2} \\d{2}:\\d{2}:\\d{2}", "Tue May 13 16:30:00"),
                                 __("[a-zA-Z]{3} [a-zA-Z]{3} \\d{2} \\d{2}:\\d{2}:\\d{2} [a-zA-Z]{3,4} \\d{4}",
                                     "Wed Nov 17 22:28:33 EET 2021"),
                                 __("(Mon|Tue|Wed|Thu|Fri|Sat|Sun), \\d{2} (Jan|Feb|Mar|Apr|May|Jun|Jul|Aug|Sep|Oct|Nov|Dec) \\d{4} \\d{2}:\\d{2}:\\d{2} GMT",
                                     "Wed, 21 Oct 2015 07:28:00 GMT"),
                                 __("[a-zA-Z]{3} [a-zA-Z]{3} \\d{2} \\d{4} \\d{2}:\\d{2}:\\d{2}.\\d{3}",
                                     "Tue May 13 2014 23:30:00.789"),
                                 __("[a-zA-Z]{3} [a-zA-Z]{3} \\d{2} \\d{2}:\\d{2}:\\d{2} -\\d{4} \\d{4}",
                                     "Tue May 13 16:30:00 -0800 2014"),
                                 __("\\d{2} [a-zA-Z]{3} \\d{4} \\d{2}:\\d{2}:\\d{2},\\d{3}",
                                     "13 May 2014 23:50:49,999"),
                                 __("[A-Za-z]{3} \\d{2} \\d{2}:\\d{2}", "Oct 13 15:29"),
                                 __("[a-zA-Z]{3} \\d{2}, \\d{4} \\d{2}:\\d{2}:\\d{2} [a-zA-Z]{2} [a-zA-Z]{3}",
                                     "May 13, 2014 11:30:00 PM PST"),
                                 __("\\d{2}:\\d{2}:\\d{2}", "23:30:00"),
                                 __("\\d{4}/\\d{2}/\\d{2} \\d{2}:\\d{2}:\\d{2}.\\d{2}\\d",
                                     "2014/05/13 16:30:59.786"),
                                 __("\\d{4}-\\d{1,2}-\\d{1,2}T\\d{1,2}:\\d{2}Z", "2020-9-10T08:07Z",
                                     "2020-09-9T08:07Z", "2020-09-10T8:07Z", "2020-09-10T08:07Z"),
                                 __("\\d{4}-\\d{1,2}-\\d{1,2}T\\d{1,2}:\\d{2}:\\d{2}Z", "2020-09-10T08:07:89Z"),
                                 __("\\d{4}-\\d{1,2}-\\d{1,2}T\\d{1,2}:\\d{2}\\:\\d{2}\\.\\d{3}Z",
                                     "2020-09-10T01:23:45.678Z"),
                                 __("\\d{8}T\\d{6}Z", "20210505T091112Z"),
                                 __("\\d{4}-\\d{2}-\\d{2}", "2024-12-17")};
  }
  private static SupportedFormat __(String regex, String... examples)
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
  public static class SupportedFormat
  {
    private final String[] examples;
    private final String   regex;
    public SupportedFormat(String regex, String... examples)
    {
      if (ArrayUtils.isEmpty(examples))
      { throw new IllegalArgumentException("must have at least one example"); }
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
