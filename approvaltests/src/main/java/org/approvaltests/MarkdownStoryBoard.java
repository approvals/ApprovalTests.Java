package org.approvaltests;

import org.approvaltests.core.Options;
import org.approvaltests.core.Verifiable;
import org.approvaltests.core.VerifyParameters;
import com.spun.util.markdown.MarkdownCompatible;
import org.lambda.functions.Function0;

public class MarkdownStoryBoard implements Verifiable
{
  @Override
  public VerifyParameters getVerifyParameters(Options options)
  {
    return new VerifyParameters(options.forFile().withExtension(".md"));
  }
  enum Types {
              None, Description, Frame, Custom
  }
  private StringBuffer stringBuffer = new StringBuffer();
  int                  index        = 0;
  private Types        last         = Types.None;
  public static <T> MarkdownStoryBoard createSequence(T initial, int additionalFrames, Function0<T> getNextFrame)
  {
    return new MarkdownStoryBoard().add(initial).addFrames(additionalFrames, getNextFrame);
  }

  public <T> MarkdownStoryBoard add(T object)
  {
    String title = index == 0 ? "Initial" : "Frame #" + index;
    return addFrame(title, object);
  }

  public <T> MarkdownStoryBoard addFrame(String title, T frame)
  {
    addNewLines(Types.Frame);
    String frameTitle = title;
    String frameData = frame instanceof MarkdownCompatible
        ? (((MarkdownCompatible) frame).toMarkdown())
        : frame.toString();
    stringBuffer.append(String.format("### %s:\n%s", frameTitle, frameData));
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
        if (type != Types.Description)
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

  public <T> MarkdownStoryBoard addFrames(int howMany, Function0<T> getNextFrame)
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

  public MarkdownStoryBoard addTitle(String title)
  {
    addNewLines(Types.Description);
    stringBuffer.append("# " + title + "\n");
    return this;
  }

  public MarkdownStoryBoard addDescription(String description)
  {
    addNewLines(Types.Description);
    stringBuffer.append("`" + description + "`  \n");
    return this;
  }

  public <T> MarkdownStoryBoard addFrame(T frame)
  {
    return add(frame);
  }

  public <T> MarkdownStoryBoard addDescriptionWithData(String description, String data)
  {
    addNewLines(Types.Description);
    stringBuffer.append("`" + description + ": " + data + "`  \n");
    return this;
  }

  public MarkdownStoryBoard addCustomMarkdown(String markdown)
  {
    addNewLines(Types.Description);
    stringBuffer.append(markdown);
    return this;
  }
}
