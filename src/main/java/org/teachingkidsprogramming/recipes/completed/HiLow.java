package org.teachingkidsprogramming.recipes.completed;

import org.teachingextensions.windows.MessageBox;

public class HiLow
{
  public static void main(String[] args)
  {
    //    Choose a random number between 1 and 100 --#4.1 (fake!) & --#13 ***Math does not permit the generation of a random number between an interval
    int answer = 34;
    //    Do the following 8 times --#9
    for (int i = 1; i <= 8; i++)
    {
      //     Ask the user for a guess --#1
      int guess = MessageBox.askForNumericalInput("Can you guess the random number between 1 and 100?");
      //     If the guess is correct --#4
      if (guess == answer)
      {
        //     Play a bell --#2 ***
        //     Tell the user that they won the game  --#3
        MessageBox.showMessage("You won!");
        //     and exit --#10
        break;
      }
      //     Otherwise, if the guess is too high --#6
      else if (guess > answer)
      {
        //     Tell the end user that it is too high --#5
        MessageBox.showMessage("Try a lower number.");
      }
      //     Otherwise, if the guess is too low --#8
      else if (guess < answer)
      {
        //     Tell the end user that it is too low --#7
        MessageBox.showMessage("Try a higher number.");
      }
      //    If after 8 times they haven't guessed correctly then --#12
      if (i == 8)
      {
        //     Tell them they've lost the game --#11
        MessageBox.showMessage("You lost!");
      }
    }
  }
}
