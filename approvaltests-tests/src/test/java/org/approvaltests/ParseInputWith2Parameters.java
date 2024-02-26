package org.approvaltests;

import com.spun.util.Tuple;
import org.lambda.functions.Function1;
import org.lambda.functions.Function2;
import org.lambda.query.Queryable;

public class ParseInputWith2Parameters<IN1, IN2>
{
  private final String                 expected;
  private final Function1<String, Tuple<IN1, IN2>> transformer;
  public ParseInputWith2Parameters(String expected, Function1<String, Tuple<IN1, IN2>> transformer)
  {
    this.expected = expected;
    this.transformer = transformer;
  }
  public static <IN1, IN2> ParseInputWith2Parameters<IN1, IN2> create(String expected,
      Function1<String, IN1> t1, Function1<String, IN2> t2)
  {
    Function1<String, Tuple<IN1, IN2>> f = s -> {
      var temp = Queryable.as(s.split(",")).select(String::trim);
      IN1 v1 = t1.call(temp.get(0));
      IN2 v2 = t2.call(temp.get(1));
      return new Tuple<>(v1, v2);
    };
    return new ParseInputWith2Parameters<>(expected, f);
  }
  public void verifyAll(Function1<Tuple<IN1, IN2>, Object> transform)
  {
    getParseInput().verifyAll(transform);
  }
  public void verifyAll(Function2<IN1, IN2, Object> transform)
  {
    getParseInput().verifyAll((t) -> transform.call(t.getFirst(), t.getSecond()));
  }
  private ParseInput<Tuple<IN1, IN2>> getParseInput()
  {
    return new ParseInput<>(expected, s -> new Tuple<>(s, transformer.call(s)));
  }
}
