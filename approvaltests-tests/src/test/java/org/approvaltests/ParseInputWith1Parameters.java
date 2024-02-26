package org.approvaltests;

import com.spun.util.Tuple;
import org.lambda.functions.Function1;
import org.lambda.query.Queryable;

public class ParseInputWith1Parameters<OUT>
{
  private final String                 expected;
  private final Function1<String, OUT> transformer;
  private final boolean                multiline;
  public ParseInputWith1Parameters(String expected, Function1<String, OUT> transformer, boolean multiline)
  {
    this.expected = expected;
    this.transformer = transformer;
    this.multiline = multiline;
  }
  public static <OUT> ParseInputWith1Parameters<OUT> create(String expected, Class<OUT> type1, boolean multiline)
  {
    return new ParseInputWith1Parameters<>(expected, ParseInput.getTransformerForClass(type1), multiline);
  }
  public <OUT2> ParseInputWith1Parameters<OUT2> transformTo(Function1<OUT, OUT2> transformer1)
  {
    Function1<String, OUT2> transformer2 = (String t) -> transformer1.call(transformer.call(t));
    return new ParseInputWith1Parameters<>(expected, transformer2, multiline);
  }
  public void verifyAll(Function1<OUT, Object> transform)
  {
    getParseInput().verifyAll(transform);
  }
  public Queryable<OUT> getInputs()
  {
      return getParseInput().parse().select(Tuple::getSecond);
  }
  private ParseInput<OUT> getParseInput()
  {
    Function1<String, Tuple<String, OUT>> transformer = s -> new Tuple<>(s, this.transformer.call(s));
    return new ParseInput<OUT>(expected, transformer, multiline);
  }
}
