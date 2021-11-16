package org.lambda.utils;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;
import org.lambda.functions.Function0;

public class MutableTest
{
  @Test
  public void exampleOfSingleElementArray()
  {
    // snippet: single_element_array
    final int[] i = {1};
    Function0<Integer> counter = () -> i[0]++;
    // end-snippet
  }
  @Test
  public void exampleOfMutable()
  {
    // snippet: mutable_example
    Mutable<String> i = new Mutable<>("Brian");
    Scheduler scheduler = new Scheduler(() -> i.get());
    scheduler.addEvent();
    i.update(n -> "Mr. " + n);
    scheduler.rsvp();
    i.set("Steve");
    scheduler.bookHotel();
    // end-snippet
    Approvals.verify(scheduler);
  }
  private class Scheduler
  {
    private Function0<String> namer;
    private String            log = "";
    public Scheduler(Function0<String> namer)
    {
      this.namer = namer;
    }
    public void rsvp()
    {
      log += "rsvping as " + namer.call() + "\n";
    }
    public void addEvent()
    {
      log += "adding event as " + namer.call() + "\n";
    }
    public void bookHotel()
    {
      log += "booking hotel as " + namer.call() + "\n";
    }
    @Override
    public String toString()
    {
      return log;
    }
  }
}
