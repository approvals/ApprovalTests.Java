package org.lambda.utils;

import org.lambda.functions.Function1;

public class Mutable<T> {
    private T object;

    public Mutable(T object) {
        this.object = object;
    }
    public T get() {
        return object;
    }

    public T set(T object) {
        this.object = object;
        return object;
    }

    public T update(Function1<T, T> updater) {
        this.object = updater.call(this.object);
        return this.object;
    }
}
