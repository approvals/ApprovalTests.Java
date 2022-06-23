package org.approvaltests.utils;

import com.spun.util.MarkdownTableBasic;
import org.approvaltests.core.Options;
import org.approvaltests.core.Verifiable;
import org.approvaltests.core.VerifyParameters;
import org.lambda.functions.Function1;

public class MarkdownTable extends MarkdownTableBasic implements Verifiable
{
  public MarkdownTable(MarkdownTableBasic markdownTableBasic) {
    this.markdown = markdownTableBasic.toMarkdown();
  }

  public static <I, O> MarkdownTable create(I[] inputs, Function1<I, O> o, String column1, String column2)
  {
    return new MarkdownTable(MarkdownTableBasic.create(inputs, o, column1, column2));
  }
  public static MarkdownTable withHeaders(String... columnNames) {
    return new MarkdownTable(MarkdownTableBasic.withHeaders(columnNames));
  }
  @Override
  public VerifyParameters getVerifyParameters(Options options)
  {
    return new VerifyParameters(options.forFile().withExtension(".md"));
  }
}
