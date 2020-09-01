package org.approvaltests.combinations.pairwise;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface Case1 extends Cloneable{

    Object put(String key, Object value);

    Collection<String> keySet();

    Set<Map.Entry<String, Object>> entrySet();

    Object get(int key);

    Object get(Object key);

    void putAll(Map<? extends String, ? extends Object> pair);

    Case1 clone();

}
