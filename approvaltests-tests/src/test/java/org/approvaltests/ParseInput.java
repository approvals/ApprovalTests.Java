package org.approvaltests;

import com.spun.util.Tuple;
import org.approvaltests.core.Options;
import org.lambda.functions.Function1;
import org.lambda.query.Queryable;

import java.util.HashMap;

public class ParseInput<OUT>
{
  private final String                                expected;
  private final boolean                               multiline;
  private final Function1<String, Tuple<String, OUT>> transformer;
  ParseInput(String expected, Function1<String, Tuple<String, OUT>> transformer, boolean multiline)
  {
    this.expected = expected;
    this.transformer = transformer;
    this.multiline = multiline;
  }
  public static ParseInput<String> from(String expected)
  {
    return new ParseInput<String>(expected, s -> new Tuple<>(s, s), false);
  }
  public ParseInput<OUT> multiline()
  {
    return new ParseInput<>(expected, transformer, true);
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
  public Queryable<OUT> getInputs()
  {
    return parse().select(Tuple::getSecond);
  }
  public void verifyAll(Function1<OUT, Object> transform)
  {
    Approvals.verifyAll("", parse(), s -> String.format("%s -> %s", s.getFirst(), transform.call(s.getSecond())),
        new Options().inline(expected));
  }
  public static <OUT> Function1<String, OUT> getTransformerForClass(Class<OUT> targetType)
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
    Function1<String, OUT> transformer1 = (Function1<String, OUT>) transformers.get(targetType);
    return transformer1;
  }
  // ************* 1 parameter
  public <T1> ParseInputWith1Parameters<T1> withTypes(Class<T1> type1)
  {
    return ParseInputWith1Parameters.create(expected, type1, multiline);
  }
  public <T1> ParseInputWith1Parameters<T1> transformTo(Function1<String, T1> transformer)
  {
    return new ParseInputWith1Parameters<>(expected, transformer, multiline);
  }
  // ************* 2 parameters
  public <T1, T2> ParseInputWith2Parameters<T1, T2> withTypes(Class<T1> type1, Class<T2> type2)
  {
    return ParseInputWith2Parameters.create(expected, getTransformerForClass(type1), getTransformerForClass(type2),
        multiline);
  }
  public <T1, T2> ParseInputWith2Parameters<T1, T2> transformTo(Function1<String, T1> transformer1,
      Function1<String, T2> transformer2)
  {
    return ParseInputWith2Parameters.create(expected, transformer1, transformer2, multiline);
  }
}
