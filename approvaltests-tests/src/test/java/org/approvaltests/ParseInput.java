package org.approvaltests;

import com.spun.util.Tuple;
import org.approvaltests.core.Options;
import org.lambda.functions.Function1;
import org.lambda.functions.Function2;
import org.lambda.query.Queryable;

import java.util.HashMap;

public class ParseInput<OUT>
{
  private final String                                expected;
  private final Function1<String, Tuple<String, OUT>> transformer;
  private boolean                                     multiline;
  public static ParseInput<String> from(String expected)
  {
    return new ParseInput<String>(expected, s -> new Tuple<>(s, s));
  }
  public static <OUT> ParseInput<OUT> from(String expected, Function1<String, OUT> transformer)
  {
    return new ParseInput<OUT>(expected, s -> new Tuple<>(s, transformer.call(s)));
  }
  private ParseInput(String expected, Function1<String, Tuple<String, OUT>> transformer)
  {
    this.expected = expected;
    this.transformer = transformer;
  }
  public static <OUT> ParseInput<OUT> create(String expected, Function1<Queryable<String>, OUT> transformer)
  {
    return new ParseInput<OUT>(expected, s -> {
      var temp = Queryable.as(s.split(",")).select(String::trim);
      return new Tuple<>(s, transformer.call(temp));
    });
  }
  public static <IN1, IN2, OUT> ParseInput<OUT> create(String expected, Function2<IN1, IN2, OUT> transformer,
      Class<IN1> in1, Class<IN2> in2)
  {
    Function1<String, IN1> t1 = getTransformerForClass(in1);
    Function1<String, IN2> t2 = getTransformerForClass(in2);
    return new ParseInput<OUT>(expected, s -> {
      var temp = Queryable.as(s.split(",")).select(String::trim);
      IN1 v1 = t1.call(temp.get(0));
      IN2 v2 = t2.call(temp.get(1));
      OUT out = transformer.call(v1, v2);
      return new Tuple<>(temp.join(", "), out);
    });
  }
  public static <OUT> ParseInput<OUT> from(String expected, Class<OUT> tranformTo)
  {
    Function1<String, OUT> transformer1 = getTransformerForClass(tranformTo);
    return ParseInput.from(expected, transformer1);
  }
  public <OUT> ParseInputWith1Parameters<String, OUT> withTypes(Class<OUT> type1)
  {
    return ParseInputWith1Parameters.create(expected, type1);
  }
//  public <OUT> ParseInput<OUT> transformTo(Function1<String, OUT> transformer)
//  {
//    return new ParseInput<OUT>(expected, s -> new Tuple<>(s, transformer.call(s)));
//  }
  public static <OUT> Function1<String, OUT> getTransformerForClass(Class<OUT> tranformTo)
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
    Function1<String, OUT> transformer1 = (Function1<String, OUT>) transformers.get(tranformTo);
    return transformer1;
  }
  public Queryable<Tuple<String, OUT>> parse()
  {
    Function1<String, Boolean> f = multiline ? s -> s.contains("->") : s -> true;
    return Queryable.as(expected.lines()) //
        .where(f) //
        .select(l -> l.split("->")[0].trim()) //
        .where(l -> !l.isEmpty()) //
        .select(l -> transformer.call(l));
  }
  public String print(String input, Object output)
  {
    return input + " -> " + output;
  }
  public void verifyAll(Function1<OUT, Object> transform)
  {
    Approvals.verifyAll("", parse(), s -> print(s.getFirst(), transform.call(s.getSecond())),
        new Options().inline(expected));
  }
  public <T1, T2> ParseInputWith2Parameters<T1, T2> withTypes(Class<T1> type1, Class<T2> type2)
  {
    return new ParseInputWith2Parameters<T1, T2>(expected, type1, type2);
  }

  public ParseInput<OUT> multiline()
  {
    this.multiline = true;
    return this;
  }

  public <OUT> ParseInput<OUT> transformTo(Function1<String, OUT> function1)
  {
    return from(expected, function1);
  }

  public class ParseInputWith2Parameters<T1, T2>
  {
    private final String    expected;
    private final Class<T1> type1;
    private final Class<T2> type2;
    public ParseInputWith2Parameters(String expected, Class<T1> type1, Class<T2> type2)
    {
      this.expected = expected;
      this.type1 = type1;
      this.type2 = type2;
    }
    public <OUT> ParseInput<OUT> transformTo(Function2<T1, T2, OUT> transformer)
    {
      return ParseInput.create(expected, transformer, type1, type2);
    }
  }
}
