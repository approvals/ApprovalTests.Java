package org.teachingkidsprogramming.recipes.completed;

import org.teachingextensions.windows.MessageBox;

public class AdLibs
{
  public static void main(String[] args)
  {
    //Ask the user to enter an adverb, save it as currentAdverb --#2
    String currentAdverb = MessageBox.askForTextInput("What is the adverb?");
    //Ask the user to enter a verb ending in '-ed', save it as currentEdVerb --#4
    String currentEdVerb = MessageBox.askForTextInput("What is the -ed verb?");
    //Ask the user to enter a body part, save it as currentBodyPart --#6
    String currentBodyPart = MessageBox.askForTextInput("What is the body part?");
    //Set the value of the currentStory to the word "Today " --#1.2
    String currentStory = "Today ";
    //Add the words "I woke " + currentAdverb + ". " to the currentStory --#3
    currentStory = currentStory + "I woke " + currentAdverb + ". ";
    //Add the words '"Then I " + currentEdVerb + " " to the currentStory --#5
    currentStory = currentStory + "Then I " + currentEdVerb + " ";
    //Add the words "my " + currentBodyPart + ". " to the current story --#7
    currentStory = currentStory + "my " + currentBodyPart + ". ";
    //Show the currentStory in a message box as a message --#1.1
    MessageBox.showMessage(currentStory);
  }
}