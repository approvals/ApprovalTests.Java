package org.approvaltests.writers;

import org.approvaltests.core.Options;

import com.spun.util.io.XMLUtils;

public class ApprovalXmlWriter extends ApprovalTextWriter
{
  /**
   *
   * @deprecated Just use the {@link org.approvaltests.writers.ApprovalTextWriter}.
   */
  @Deprecated
  public ApprovalXmlWriter(String text, Options options)
  {
    super(XMLUtils.prettyPrint(text, 2), options.forFile().withExtension(".xml"));
  }

  /**
   * @deprecated Moved to {@link com.spun.util.io.XMLUtils#prettyPrint(String, int)}
   *
   */
  @Deprecated
  public static String prettyPrint(String input, int indent)
  {
    return XMLUtils.prettyPrint(input, indent);
  }
}
