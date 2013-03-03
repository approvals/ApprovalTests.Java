package com.spun.util.database.management;

public class SequenceDiscrepancy
{
  public enum Type {
    SET_WRONG, MISSING
  }
  private String onColumn;
  private Type   type;
  private String expected;
  private String actual;  
  public SequenceDiscrepancy(String onColumn, Type type, String expected, String actual)
  {
    this.onColumn = onColumn;
    this.type = type;
    this.expected = expected;
    this.actual = actual;
  }
  public String getActualSequenceName()
  {
    return actual;
  }
  public boolean isMissing()
  {
    return type == Type.MISSING;
  }
  public String getExpectedSequenceName()
  {
    return expected;
  }
  public String getOnColumn()
  {
    return onColumn;
  }
  public Type getType()
  {
    return type;
  }
  public static SequenceDiscrepancy[] toArray(java.util.Collection<SequenceDiscrepancy> collection)
  {
    if (collection == null) { return new SequenceDiscrepancy[0]; }
    return (SequenceDiscrepancy[]) collection.toArray(new SequenceDiscrepancy[collection.size()]);
  }
}
