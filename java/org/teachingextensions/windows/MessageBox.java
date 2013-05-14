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
  private static MessageBoxInstance messageBox = new MessageBoxInstance();
  /**
   * Prints a request for a numerical input to the window. <br/>
   * <b>Example:</b> {@code  int cookies = MessageBox.askForNumericalInput("How many cookies would you like?");}
   * 
   * @param message
   *          the text to be displayed
   * @return the user input
   */
  public static int askForNumericalInput(String message)
  {
    return messageBox.askForNumericalInput(message);
  }
  /**
   * Prints a request for a text input to the window. <br/>
   * <b>Example:</b> {@code  String name = MessageBox.askForTextInput("What is your nickname?");}
   * 
   * @param message
   *          the text to be displayed
   * @return the user input
   */
  public static String askForTextInput(String message)
  {
    return messageBox.askForTextInput(message);
  }
  /**
   * Prints the message to the window. <br/>
   * <b>Example:</b> {@code  MessageBox.showMessage("Girl programmers rule!");}
   * 
   * @param message
   *          the text to be displayed
   */
  public static void showMessage(String message)
  {
    messageBox.showMessage(message);
  }
  public static void mock(MessageBoxInstance messageBoxMock)
  {
    messageBox = messageBoxMock;
  }
  public static class MessageBoxInstance
  {
    public int askForNumericalInput(String message)
    {
      String input = askForTextInput(message);
      return NumberUtils.load(input, 0);
    }
    public String askForTextInput(String message)
    {
      return JOptionPane.showInputDialog(message);
    }
    public void showMessage(String message)
    {
      JOptionPane.showMessageDialog(null, message);
    }
  }
}
