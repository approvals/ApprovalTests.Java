package org.approvaltests;

import org.lambda.functions.Function1;

public class ParseInputWith1Parameters<T1, OUT>
{
  private String                 expected;
  private Function1<String, OUT> transformer;
  public ParseInputWith1Parameters(String expected, Function1<String, OUT> transformer)
  {
    this.expected = expected;
    this.transformer = transformer;
  }
  public static <OUT> ParseInputWith1Parameters<String, OUT> create(String expected, Class<OUT> type1)
  {
    return new ParseInputWith1Parameters<>(expected, ParseInput.getTransformerForClass(type1));
  }
  public void verifyAll(Function1<OUT, Object> transform)
  {
    ParseInput.from(expected, transformer).verifyAll(transform);
  }
  public <OUT2> ParseInputWith1Parameters<String, OUT2> transformTo(Function1<OUT, OUT2> transformer1)
  {
    Function1<String, OUT2> transformer2 = (String t) -> transformer1.call(transformer.call(t));
    return new ParseInputWith1Parameters<>(expected, transformer2);
  }
}
