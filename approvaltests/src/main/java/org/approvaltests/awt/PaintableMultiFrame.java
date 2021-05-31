package org.approvaltests.awt;

import java.time.Duration;

import org.lambda.functions.Function1;

import com.spun.swing.Paintable;
import com.spun.util.Tuple;

public class PaintableMultiFrame
{
  private final int                                            numberOfFrames;
  private final Function1<Integer, Tuple<Paintable, Duration>> frameGetter;
  public PaintableMultiFrame(int numberOfFrames, Function1<Integer, Tuple<Paintable, Duration>> frameGetter)
  {
    this.numberOfFrames = numberOfFrames;
    this.frameGetter = frameGetter;
  }
  public int getNumberOfFrames()
  {
    return numberOfFrames;
  }
  public Function1<Integer, Tuple<Paintable, Duration>> getFrameGetter()
  {
    return frameGetter;
  }
}
