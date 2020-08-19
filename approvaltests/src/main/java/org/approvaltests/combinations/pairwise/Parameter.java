package org.approvaltests.combinations.pairwise;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class Parameter<T> extends ArrayList<T> {


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name;

    public Parameter() {
        super();
    }

    public Parameter(String name) {
        this.name = name;
    }

    public Parameter(String name, Collection<T> c) {
        this(name);
        this.addAll(c);
    }

    public Parameter(String name, T... values) {
        this(name, Arrays.asList(values));
    }

    @Override
    public String toString() {
        return name + ": " + super.toString();
    }

}