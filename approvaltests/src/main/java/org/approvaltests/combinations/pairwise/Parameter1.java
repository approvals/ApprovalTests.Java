package org.approvaltests.combinations.pairwise;

public interface Parameter1<T> {

    String getName();

    T[] toArray();

    int size();

    T get(int index);
}
