package com.spun.util;

import java.util.HashMap;

public class MapBuilder<K, V> extends HashMap<K, V>
{
  public MapBuilder(K key, V value)
  {
    and(key, value);
  }
  public MapBuilder<K, V> and(K key, V value)
  {
    put(key, value);
    return this;
  }
}
