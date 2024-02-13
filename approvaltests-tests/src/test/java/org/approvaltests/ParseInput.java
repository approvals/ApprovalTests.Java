package org.approvaltests;

import com.spun.util.Tuple;
import org.approvaltests.core.Options;
import org.lambda.functions.Function1;
import org.lambda.functions.Function2;
import org.lambda.query.Query;
import org.lambda.query.Queryable;

import java.util.HashMap;

public class ParseInput<T>
{
  private String                                    expected;
  private final Function1<String, Tuple<String, T>> transformer;
  public static ParseInput<String> create(String expected)
  {
    return new ParseInput<String>(expected, s -> new Tuple<>(s, s));
  }
  public static <T> ParseInput<T> create(String expected, Function1<String, T> transformer)
  {
    return new ParseInput<T>(expected, s -> new Tuple<>(s, transformer.call(s)));
  }
  private ParseInput(String expected, Function1<String, Tuple<String, T>> transformer)
  {
    this.expected = expected;
    this.transformer = transformer;
  }
  public static <T> ParseInput<T> createFromParts(String expected, Function1<Queryable<String>, T> transformer)
  {
    return new ParseInput<T>(expected, s -> {
      var temp = Queryable.as(s.split(",")).select(String::trim);
      return new Tuple<>(s, transformer.call(temp));
    });
  }
  public static <IN1, IN2, T> ParseInput<T> createFromParts(String expected, Function2<IN1, IN2, T> transformer,
      Class<IN1> in1, Class<IN2> in2)
  {
    Function1<String, IN1> t1 = getTransformerForClass(in1);
    Function1<String, IN2> t2 = getTransformerForClass(in2);
    return new ParseInput<T>(expected, s -> {
      var temp = Queryable.as(s.split(",")).select(String::trim);
      IN1 v1 = t1.call(temp.get(0));
      IN2 v2 = t2.call(temp.get(1));
      T out = transformer.call(v1, v2);
      return new Tuple<>(s, out);
    });
  }
  public static <T> ParseInput<T> create(String expected, Class<T> tranformTo)
  {
    Function1<String, T> transformer1 = getTransformerForClass(tranformTo);
    return ParseInput.create(expected, transformer1);
  }
  private static <T> Function1<String, T> getTransformerForClass(Class<T> tranformTo)
  {
    var transformers = new HashMap<Class<?>, Function1<String, Object>>()
    {
      {
        put(Integer.class, s -> Integer.parseInt(s));
        put(String.class, s -> s);
        put(Double.class, s -> Double.parseDouble(s));
        put(Boolean.class, s -> Boolean.parseBoolean(s));
        put(Long.class, s -> Long.parseLong(s));
        put(Float.class, s -> Float.parseFloat(s));
        put(Short.class, s -> Short.parseShort(s));
      }
    };
    Function1<String, T> transformer1 = (Function1<String, T>) transformers.get(tranformTo);
    return transformer1;
  }
  public Queryable<Tuple<String, T>> parse(String expected)
  {
    return Queryable.as(expected.lines())
            .select(l -> l.split("->")[0].trim())
            .where(l -> !l.isEmpty())
            .select(l -> transformer.call(l));
  }
  public String print(String input, Object output)
  {
    return input + " -> " + output;
  }
  public void verifyAll(Function1<T, Object> transform)
  {
    Approvals.verifyAll("", parse(expected), s -> print(s.getFirst(), transform.call(s.getSecond())),
        new Options().inline(expected));
  }
}
