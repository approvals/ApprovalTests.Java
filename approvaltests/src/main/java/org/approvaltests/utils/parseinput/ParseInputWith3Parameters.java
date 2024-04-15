package org.approvaltests.utils.parseinput;

import com.spun.util.Tuple;
import com.spun.util.Tuple3;
import org.lambda.functions.Function1;
import org.lambda.functions.Function3;
import org.lambda.query.Queryable;

public class ParseInputWith3Parameters<IN1, IN2, IN3>
{
  private final String                                   expected;
  private final Function1<String, Tuple3<IN1, IN2, IN3>> transformer;
  private final ParseInput.ParseInputOptions             options;
  public ParseInputWith3Parameters(String expected, Function1<String, Tuple3<IN1, IN2, IN3>> transformer,
      ParseInput.ParseInputOptions options)
  {
    this.expected = expected;
    this.transformer = transformer;
    this.options = options;
  }
  public static <IN1, IN2, IN3> ParseInputWith3Parameters<IN1, IN2, IN3> create(String expected,
      Function1<String, IN1> t1, Function1<String, IN2> t2, Function1<String, IN3> t3,
      ParseInput.ParseInputOptions options)
  {
    Function1<String, Tuple3<IN1, IN2, IN3>> f = s -> {
      Queryable<String> temp = Queryable.as(s.split(",")).select(String::trim);
      IN1 v1 = t1.call(temp.get(0));
      IN2 v2 = t2.call(temp.get(1));
      IN3 v3 = t3.call(ParseInput.getLast(temp, 2));
      return new Tuple3<>(v1, v2, v3);
    };
    return new ParseInputWith3Parameters<>(expected, f, options);
  }
  private ParseInput<Tuple3<IN1, IN2, IN3>> getParseInput()
  {
    return new ParseInput<>(expected, s -> new Tuple<>(s, transformer.call(s)), options);
  }
  public <OUT> ParseInputWith1Parameters<OUT> transformTo(Function3<IN1, IN2, IN3, OUT> transform)
  {
    Function1<String, OUT> f1 = (t) -> {
      Tuple3<IN1, IN2, IN3> transformed = transformer.call(t);
      return transform.call(transformed.getFirst(), transformed.getSecond(), transformed.getThird());
    };
    return new ParseInputWith1Parameters<>(expected, f1, options);
  }
  public void verifyAll(Function1<Tuple3<IN1, IN2, IN3>, Object> transform)
  {
    getParseInput().verifyAll(transform);
  }
  public void verifyAll(Function3<IN1, IN2, IN3, Object> transform)
  {
    getParseInput().verifyAll((t) -> transform.call(t.getFirst(), t.getSecond(), t.getThird()));
  }
}
