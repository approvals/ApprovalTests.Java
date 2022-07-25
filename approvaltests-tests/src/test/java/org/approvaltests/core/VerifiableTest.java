package org.approvaltests.core;

import org.junit.jupiter.api.Test;

import static org.approvaltests.Approvals.verify;

public class VerifiableTest
{
  // begin-snippet: verifiable_object_example
  @Test
  void testVerifiable()
  {
    verify(new MarkdownParagraph("Paragraph Title", "This is where the paragraph text is."));
  }
  public static class MarkdownParagraph implements Verifiable
  {
    private String title;
    private String paragraph;
    public MarkdownParagraph(String title, String paragraph)
    {
      this.title = title;
      this.paragraph = paragraph;
    }
    @Override
    public VerifyParameters getVerifyParameters(Options options)
    {
      return new VerifyParameters(options.forFile().withExtension(".md"));
    }
    @Override
    public String toString()
    {
      return String.format("# %s\n%s", title, paragraph);
    }
  }
  // end-snippet
}
