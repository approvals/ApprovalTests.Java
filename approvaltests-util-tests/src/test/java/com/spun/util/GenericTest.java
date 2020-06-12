package com.spun.util;

import java.util.ArrayList;
import org.junit.jupiter.api.Test;

public class GenericTest
{
  @SuppressWarnings({"unchecked", "rawtypes"})
  @Test
  public void test()
  {
    ArrayList<Integer> ints = new ArrayList<Integer>();
    ints.add(1);
    ArrayList<Number> nums = new ArrayList<Number>();
    nums.add(1);
    // Works as Excepted
    sumInteger(ints);
    sumGeneric(ints);
    sumNumber(nums);
    sumGeneric(nums);
    // Works but odd
    sumInteger((ArrayList<Integer>) (ArrayList) nums);
    sumNumber((ArrayList<Number>) (ArrayList) ints);
    // also works but odd that i'm forced to leave the generic world
    sumInteger((ArrayList) nums);
    sumNumber((ArrayList) ints);
    // Works but constructs a new object
    sumNumber(new ArrayList<Number>(ints));
    // Doesn't compile
    // sumNumber(ints);
    // Also note the oddity, the first compiles, the second doesn't
    @SuppressWarnings("unused")
    Object[] objectArray = new String[0];
    // ArrayList<Object> objectList = new ArrayList<String>();
    // big question why would I EVER want the method sumNumber over sumGeneric
    // ????
  }
  private double sumInteger(ArrayList<Integer> nums)
  {
    return sumGeneric(nums);
  }
  private double sumNumber(ArrayList<Number> nums)
  {
    return sumGeneric(nums);
  }
  private double sumGeneric(ArrayList<? extends Number> nums)
  {
    double total = 0;
    for (Number n : nums)
    {
      total += n.doubleValue();
    }
    return total;
  }
}
