package org.teachingextensions.logo.utils;

import java.awt.Toolkit;

public class Sounds
{
  /**
   * Plays a beep through your speakers. BEEP!<br />
   * <b>Example:</b> {@code  Sounds.playBeep()}
   */
  public static void playBeep()
  {
    Toolkit.getDefaultToolkit().beep();
  }
}
