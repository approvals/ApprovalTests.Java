package org.approvaltests.utils.parseinput;

import com.spun.util.Tuple;
import org.lambda.functions.Function1;
import org.lambda.functions.Function2;
import org.lambda.query.Queryable;

public class ParseInputWith2Parameters<IN1, IN2>
{
  private final String                             expected;
  private final Function1<String, Tuple<IN1, IN2>> transformer;
  private final ParseInput.ParseInputOptions       options;
  public ParseInputWith2Parameters(String expected, Function1<String, Tuple<IN1, IN2>> transformer,
      ParseInput.ParseInputOptions options)
  {
    this.expected = expected;
    this.transformer = transformer;
    this.options = options;
  }
  public static <IN1, IN2> ParseInputWith2Parameters<IN1, IN2> create(String expected, Function1<String, IN1> t1,
      Function1<String, IN2> t2, ParseInput.ParseInputOptions options)
  {
    Function1<String, Tuple<IN1, IN2>> f = s -> {
      Queryable<String> temp = Queryable.as(s.split(",")).select(String::trim);
      IN1 v1 = t1.call(temp.get(0));
      IN2 v2 = t2.call(ParseInput.getLast(temp, 1));
      return new Tuple<>(v1, v2);
    };
    return new ParseInputWith2Parameters<>(expected, f, options);
  }
  private ParseInput<Tuple<IN1, IN2>> getParseInput()
  {
    return new ParseInput<>(expected, s -> new Tuple<>(s, transformer.call(s)), options);
  }
  public <OUT> ParseInputWith1Parameters<OUT> transformTo(Function2<IN1, IN2, OUT> transform)
  {
    Function1<String, OUT> f1 = (t) -> {
      Tuple<IN1, IN2> transformed = transformer.call(t);
      return transform.call(transformed.getFirst(), transformed.getSecond());
    };
    return new ParseInputWith1Parameters<>(expected, f1, options);
  }
  public void verifyAll(Function2<IN1, IN2, Object> transform)
  {
    getParseInput().verifyAll((t) -> transform.call(t.getFirst(), t.getSecond()));
  }
  public void verifyAll(Function1<Tuple<IN1, IN2>, Object> transform)
  {
    getParseInput().verifyAll(transform);
  }
}
