package org.approvaltests.utils;

import com.spun.util.markdown.table.MarkdownTable;
import org.approvaltests.core.Options;
import org.approvaltests.core.Verifiable;
import org.approvaltests.core.VerifyParameters;
import org.lambda.functions.Function1;

public class VerifiableMarkdownTable extends MarkdownTable implements Verifiable
{
  public VerifiableMarkdownTable(MarkdownTable markdownTableBasic)
  {
    this.markdown = markdownTableBasic.markdown;
  }

  public static <I, O> VerifiableMarkdownTable create(I[] inputs, Function1<I, O> o, String column1,
      String column2)
  {
    return new VerifiableMarkdownTable(MarkdownTable.create(inputs, o, column1, column2));
  }

  public static VerifiableMarkdownTable withHeaders(String... columnNames)
  {
    return new VerifiableMarkdownTable(MarkdownTable.withHeaders(columnNames));
  }

  @Override
  public VerifyParameters getVerifyParameters(Options options)
  {
    return new VerifyParameters(options.forFile().withExtension(".md"));
  }
}
