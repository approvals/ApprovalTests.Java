package org.approvaltests;

import org.lambda.functions.Function0;

public class StoryBoard
{
  enum Types {
              None, Description, Frame
  }
  private StringBuffer stringBuffer = new StringBuffer();
  int                  index        = 0;
  private Types        last         = Types.None;
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
    addNewLines(Types.Frame);
    String frameTitle = title;
    stringBuffer.append(String.format("%s:\n%s", frameTitle, frame));
    index++;
    return this;
  }
  private void addNewLines(Types type)
  {
    switch (last)
    {
      case None :
        break;
      case Description :
        if (type == Types.Frame)
        {
          stringBuffer.append("\n\n");
        }
        break;
      case Frame :
        stringBuffer.append("\n\n");
        break;
    }
    last = type;
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
  public StoryBoard addDescription(String game_of_life)
  {
    addNewLines(Types.Description);
    stringBuffer.append(game_of_life + "\n");
    return this;
  }
  public <T> StoryBoard addFrame(T frame)
  {
    return add(frame);
  }
  public <T> StoryBoard addDescriptionWithData(String description, String data)
  {
    addNewLines(Types.Description);
    stringBuffer.append(description + ": " + data + "\n");
    return this;
  }
}
