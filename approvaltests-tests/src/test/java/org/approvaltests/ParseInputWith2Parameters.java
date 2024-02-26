package org.approvaltests;

import com.spun.util.Tuple;
import org.lambda.functions.Function1;
import org.lambda.query.Queryable;

public class ParseInputWith2Parameters<IN1, IN2, OUT>
{
  private final String                 expected;
  private final Function1<String, OUT> transformer;
  public ParseInputWith2Parameters(String expected, Function1<String, OUT> transformer)
  {
    this.expected = expected;
    this.transformer = transformer;
  }

  public static <IN1, IN2> ParseInputWith2Parameters<IN1, IN2, Tuple<IN1, IN2>> create(String expected,
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
  //    public <OUT2> ParseInputWith2Parameters<OUT2> transformTo(Function1<IN1, OUT2> transformer1)
  //    {
  //        Function1<String, OUT2> transformer2 = (String t) -> transformer1.call(transformer.call(t));
  //        return new ParseInputWith2Parameters<>(expected, transformer2);
  //    }
  public void verifyAll(Function1<OUT, Object> transform)
  {
    new ParseInput<OUT>(expected, s -> new Tuple<>(s, transformer.call(s))).verifyAll(transform);
  }
}
