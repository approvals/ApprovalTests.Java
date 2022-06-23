package com.spun.util;

import org.lambda.functions.Function1;
import org.lambda.query.Queryable;

public class MarkdownTable implements MarkdownCompatible
{
  public Queryable<MarkdownTableElement> markdown = new Queryable<MarkdownTableElement>(MarkdownTableElement.class);
  public static <I, O> MarkdownTable create(I[] inputs, Function1<I, O> o, String column1, String column2)
  {
    MarkdownTable table = new MarkdownTable().withColumnHeaders(column1, column2);
    for (I input : inputs)
    {
      table.addRow(input, o.call(input));
    }
    return table;
  }
  public static MarkdownTable withHeaders(String... columnNames)
  {
    MarkdownTable table = new MarkdownTable();
    return table.withColumnHeaders(columnNames);
  }
  public <I> MarkdownTable addRowsForInputs(I[] inputs, Function1<I, Object>... transfers)
  {
    for (I input : inputs)
    {
      Queryable<Object> row = Queryable.as(transfers).select(f -> f.call(input));
      row.add(0, input);
      addRow(row.toArray());
    }
    return this;
  }
  public MarkdownTable addRow(Object... columns)
  {
    markdown.addAll(constructRow(columns));
    return this;
  }
  public MarkdownTable withColumnHeaders(String... headers)
  {
    markdown.addAll(constructColumnHeaders(headers));
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
    return render(markdown);
  }
  public static String printColumnHeaders(String... headers)
  {
    return render(constructColumnHeaders(headers));
  }
  public static Queryable<MarkdownTableElement> constructColumnHeaders(String... headers)
  {
    Queryable<MarkdownTableElement> row = constructRow(headers);
    row.addAll(constructRow(ArrayUtils.of("---", headers.length)));
    return row;
  }
  public static String printRow(Object... columns)
  {
    return render(constructRow(columns));
  }

  private static String render(Queryable<MarkdownTableElement> table) {
    return table.join("");
  }

  public static Queryable<MarkdownTableElement> constructRow(Object... columns)
  {
    Queryable row = new Queryable(MarkdownTableElement.class);
    row.add(MarkdownTableElement.DELIMITER);
    for (int x = 0; x < columns.length; ++x)
    {
      row.add(new MarkdownTableContents("" + columns[x]));
      row.add(MarkdownTableElement.DELIMITER);
    }
    row.add(MarkdownTableElement.NEWLINE);
    return row;
  }
}
