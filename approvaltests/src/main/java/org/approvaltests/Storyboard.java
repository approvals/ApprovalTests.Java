package org.approvaltests;

import org.lambda.functions.Function0;

public class Storyboard
{
  private StringBuffer stringBuffer = new StringBuffer();
  int                  index        = 0;
  public void add(Object object)
  {
    String title = index == 0 ? "Initial:" : "\n\nFrame #" + index + ":";
    stringBuffer.append(String.format("%s\n%s", title, object));
    index++;
  }
  public void addFrames(int howMany, Function0<Object> getNextFrame)
  {
    for (int i = 0; i < howMany; i++) {
      add(getNextFrame.call());
    }
  }

  @Override
  public String toString() {
    return stringBuffer.toString();
  }
}
