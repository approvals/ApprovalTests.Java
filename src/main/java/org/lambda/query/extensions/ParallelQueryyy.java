package org.lambda.query.extensions;

import java.util.Collection;
import java.util.List;

import org.lambda.functions.Function1;
import org.lambda.query.ParallelQuery;

public class ParallelQueryyy<T> extends ExtendableBase<Collection<T>>
{
  public List<T> where(Function1<T, Boolean> compare)
  {
    return ParallelQuery.where(caller, compare);
  }
}
