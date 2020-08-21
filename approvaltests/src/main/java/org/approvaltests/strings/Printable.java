package org.approvaltests.strings;

import javafx.scene.chart.AxisBuilder;
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

    public static <T> Printable<T>[] create(T[] parameters, Function1<T,String> toString) {
        return Arrays.stream(parameters)
                .map(p -> new Printable<>(p, toString)).toArray(Printable[]::new);
    }

    public static LableMakerStarter with() {
        return new LableMakerStarter();
    }

    public T get() {
        return value;
    }

    @Override
    public String toString() {
        return toString.call(value);
    }

    public static class LableMakerStarter {
        public <T> LableMaker<T> label(T value, String label) {
            LableMaker<T> maker = new LableMaker<T>();

            return maker.label(value, label);

        }
    }

    public static class LableMaker<T> {
        private ArrayList<Printable<T>> values = new ArrayList<>();
        public LableMaker<T> label(T value, String label) {
            values.add(new Printable(value, __ -> label));
            return this;

        }

        public Printable<T>[] get() {
            return values.toArray( new Printable[0]);
        }
    }
}
