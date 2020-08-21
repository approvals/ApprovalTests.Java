package org.approvaltests.strings;

import org.lambda.functions.Function1;

import java.util.ArrayList;
import java.util.Arrays;

public class Printable<T>  {

    private T value;
    private final Function1<T, String> toString;

    public Printable(T value, Function1<T, String> toString) {
        this.value = value;
        this.toString = toString;
    }

    public static <T> Printable<T>[] create( Function1<T,String> toString, T... parameters) {
        return Arrays.stream(parameters)
                .map(p -> new Printable<>(p, toString)).toArray(Printable[]::new);
    }

    public static LabelMakerStarter with() {
        return new LabelMakerStarter();
    }

    public T get() {
        return value;
    }

    @Override
    public String toString() {
        return toString.call(value);
    }

    public static class LabelMakerStarter {
        public <T> LabelMaker<T> label(T value, String label) {
            LabelMaker<T> maker = new LabelMaker<T>();

            return maker.label(value, label);

        }
    }

    public static class LabelMaker<T> {
        private ArrayList<Printable<T>> values = new ArrayList<>();
        public LabelMaker<T> label(T value, String label) {
            values.add(new Printable(value, __ -> label));
            return this;

        }

        public Printable<T>[] toArray() {
            return values.toArray( new Printable[0]);
        }
    }
}
