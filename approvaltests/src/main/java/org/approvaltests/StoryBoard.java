package org.approvaltests;

import org.lambda.functions.Function0;

public class StoryBoard
{
  private StringBuffer stringBuffer  = new StringBuffer();
  int                  index         = 0;
  private boolean      newLineNeeded = false;
  public static <T> StoryBoard createSequence(T initial, int additionalFrames, Function0<T> getNextFrame)
  {
    return new StoryBoard().add(initial).addFrames(additionalFrames, getNextFrame);
  }
  public <T> StoryBoard add(T object)
  {
    String title = index == 0 ? "Initial" : "Frame #" + index;
    return addFrame(title, object);
  }
  public <T> StoryBoard addFrame(String title, T frame)
  {
    String frameTitle = index == 0 ? title : "\n\n" + title;
    stringBuffer.append(String.format("%s:\n%s", frameTitle, frame));
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
  public void addDescription(String game_of_life)
  {
    stringBuffer.append(game_of_life);
    stringBuffer.append("\n");
    stringBuffer.append("\n");
  }
  public <T> StoryBoard addFrame(T frame)
  {
    return add(frame);
  }
  public <T> StoryBoard addDescriptionWithData(String description, String data)
  {
    if (newLineNeeded)
    {
      stringBuffer.append("\n");
    }
    else
    {
      stringBuffer.append("\n\n");
    }
    newLineNeeded = true;
    stringBuffer.append(description + ": " + data);
    return this;
  }
}
