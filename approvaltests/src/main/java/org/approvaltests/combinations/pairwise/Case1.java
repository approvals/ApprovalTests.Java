package org.approvaltests.combinations.pairwise;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface Case1<K, V> extends Cloneable{

    V put(K key, V value);

    Collection<K> keySet();

    Set<Map.Entry<K, V>> entrySet();

    Object get(int key);

    V get(Object key);

    void putAll(Case1 pair);

    public Case1 clone();

    Case1 union(Case1 c);

    boolean matches(Case1 pair1);
}
