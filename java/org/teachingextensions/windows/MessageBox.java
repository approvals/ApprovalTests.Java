package org.teachingextensions.windows;

import javax.swing.JOptionPane;

import com.spun.util.NumberUtils;

/**
 * 
 * MessageBox is a window that can collect a numerical input from the user
 * or prints a message for the user to read. <br/>
 * 
 * 
 *
 */
public class MessageBox
{
  /**
   * Prints a request for a numerical input to the window. <br/>
   * <b>Example:</b> {@code  int cookies = MessageBox.askForNumericalInput("How many cookies would you like?");}
   * 
   * @param message
   *            the text to be displayed
   * @return the user input
   */
  public static int askForNumericalInput(String message)
  {
    String input = JOptionPane.showInputDialog(message);
    return NumberUtils.load(input, 0);
  }
  /**
   * Prints the message to the window. <br/>
   * <b>Example:</b> {@code  MessageBox.showMessage("Girl programmers rule!");}
   * 
   *  @param message
   *            the text to be displayed
   */
  public static void showMessage(String message)
  {
    JOptionPane.showMessageDialog(null, message);
  }
}
