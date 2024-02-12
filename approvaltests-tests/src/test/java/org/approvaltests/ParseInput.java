package org.approvaltests;

import com.spun.util.Tuple;
import org.approvaltests.core.Options;
import org.lambda.functions.Function1;
import org.lambda.query.Query;
import org.lambda.query.Queryable;

public class ParseInput <T>{

    private String expected;
    private final Function1<String, Tuple<String, T>> transformer;



    public static ParseInput<String> create(String expected) {
        return new ParseInput<String>(expected, s -> new Tuple<>(s,s));

    }
    public static<T> ParseInput<T> create(String expected,Function1<String, T> transformer) {
        return new ParseInput<T>(expected,s -> new Tuple<>(s,transformer.call(s)));

    }

    private ParseInput(String expected, Function1<String, Tuple<String, T>> transformer) {
        this.expected = expected;
        this.transformer = transformer;
    }

    public static <T> ParseInput<T> createFromParts(String expected, Function1<Queryable<String>, T> transformer) {
        return new ParseInput<T>(expected,s -> {
            var temp = Queryable.as(s.split(",")).select(String::trim);
            return new Tuple<>(s, transformer.call(temp));
        });
    }

    public Queryable<Tuple<String, T>> parse(String expected) {
        return Queryable.as(expected.lines())
                .select(l -> l.split(" -> ")[0].trim())
                .select(l -> transformer.call(l));
    }

    public String print(String input, Object output) {
        return input + " -> " + output;
    }

    public void verifyAll(Function1<T, Object> transform) {
        Approvals.verifyAll("", parse(expected),
                s -> print(s.getFirst(), transform.call(s.getSecond())), new Options().inline(expected));
    }
}













