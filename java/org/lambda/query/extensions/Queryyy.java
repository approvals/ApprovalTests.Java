package org.lambda.query.extensions;

import java.util.List;

import org.lambda.functions.Function1;
import org.lambda.query.Query;

public class Queryyy<In> extends ExtendableBase<List<In>>
{
  public <Out> List<Out> select(Function1<In, Out> function)
  {
    return Query.select(caller, function);
  }
  public List<In> where(Function1<In, Boolean> function)
  {
    return Query.where(caller, function);
  }
  public In max(Function1<In, Comparable> function)
  {
    return Query.max(caller, function);
  }
  public In min(Function1<In, Comparable> function)
  {
    return Query.min(caller, function);
  }
}
