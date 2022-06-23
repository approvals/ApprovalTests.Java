package org.approvaltests.utils;

import com.spun.util.ArrayUtils;
import org.approvaltests.core.Options;
import org.approvaltests.core.Verifiable;
import org.approvaltests.core.VerifyParameters;
import org.approvaltests.strings.MarkdownCompatible;
import org.lambda.functions.Function1;
import org.lambda.query.Queryable;

public class MarkdownTableBasic implements MarkdownCompatible
{
  protected String markdown;
  public static <I, O> MarkdownTableBasic create(I[] inputs, Function1<I, O> o, String column1, String column2)
  {
    MarkdownTableBasic table = new MarkdownTableBasic().withColumnHeaders(column1, column2);
    for (I input : inputs)
    {
      table.addRow(input, o.call(input));
    }
    return table;
  }
  public static MarkdownTableBasic withHeaders(String... columnNames)
  {
    MarkdownTableBasic table = new MarkdownTableBasic();
    return table.withColumnHeaders(columnNames);
  }
  public <I> MarkdownTableBasic addRowsForInputs(I[] inputs, Function1<I, Object>... transfers)
  {
    for (I input : inputs)
    {
      Queryable<Object> row = Queryable.as(transfers).select(f -> f.call(input));
      row.add(0, input);
      addRow(row.toArray());
    }
    return this;
  }
  public MarkdownTableBasic addRow(Object... columns)
  {
    markdown += printRow(columns);
    return this;
  }
  public MarkdownTableBasic withColumnHeaders(String... headers)
  {
    markdown = printColumnHeaders(headers);
    return this;
  }
  @Override
  public String toString()
  {
    return toMarkdown();
  }
  @Override
  public String toMarkdown()
  {
    return markdown;
  }
  public static String printColumnHeaders(String... headers)
  {
    return printRow(headers) + printRow(ArrayUtils.of("---", headers.length));
  }
  public static String printRow(Object... columns)
  {
    StringBuffer b = new StringBuffer();
    b.append("|");
    for (int x = 0; x < columns.length; ++x)
    {
      b.append(String.format(" %s |", columns[x]));
    }
    b.append("\n");
    return b.toString();
  }
}
