package org.approvaltests;

import org.lambda.functions.Function0;

public class StoryBoard
{
  private StringBuffer stringBuffer = new StringBuffer();
  int                  index        = 0;
  public static <T> StoryBoard createSequence(T initial, int additionalFrames, Function0<T> getNextFrame)
  {
    return new StoryBoard().add(initial).addFrames(additionalFrames, getNextFrame);
  }
  public <T> StoryBoard add(T object)
  {
    String title = index == 0 ? "Initial:" : "\n\nFrame #" + index + ":";
    stringBuffer.append(String.format("%s\n%s", title, object));
    index++;
    return this;
  }
  public <T> StoryBoard addFrames(int howMany, Function0<T> getNextFrame)
  {
    for (int i = 0; i < howMany; i++)
    {
      add(getNextFrame.call());
    }
    return this;
  }
  @Override
  public String toString()
  {
    return stringBuffer.toString();
  }

  public void addDescription(String game_of_life) {
    stringBuffer.append(game_of_life);
    stringBuffer.append("\n");
    stringBuffer.append("\n");
  }

  public StoryBoard addFrame(String title, Object frame) {
    stringBuffer.append(String.format("%s\n%s", title, frame));
    index++;
    return this;
  }
}
