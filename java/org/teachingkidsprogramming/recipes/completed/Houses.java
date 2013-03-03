package org.teachingkidsprogramming.recipes.completed;

import java.awt.Color;

import org.teachingextensions.logo.Tortoise;

public class Houses
{
  public static void main(String[] args)
  {
    //   Make the tortoise move as fast as possible --#11
    Tortoise.setSpeed(10);
    //   Have the tortoise start at 200 pixels in on the X axis --#14
    Tortoise.setX(200);
    //   The current height is 40 --#1.2
    int height = 40;
    //   DrawHouse (recipe below) --#9
    drawHouse(height);
    //   DrawHouse with height 120 (recipe below) --#10
    drawHouse(120);
    //   DrawHouse with height 90 (recipe below) --#12
    drawHouse(90);
    //   DrawHouse with height 20 (recipe below) --#13
    drawHouse(20);
  }
  public static void drawHouse(int height)
  {
    //   ------------- Recipe for DrawHouse --#9
    //   Change the color of the line the tortoise draws to lightGray --#15
    Tortoise.setPenColor(Color.lightGray);
    //   Move the tortoise the height of a house --#1.1
    Tortoise.move(height);
    //   Turn the tortoise 90 degrees to the right --#2
    Tortoise.turn(90);
    //   Move the tortoise 30 pixels --#3
    Tortoise.move(30);
    //   Turn the tortoise 90 degrees to the right --#4
    Tortoise.turn(90);
    //   Move the tortoise the height of a house --#5
    Tortoise.move(height);
    //   Turn the tortoise 90 degrees to the left --#6
    Tortoise.turn(-90);
    //   Move the tortoise 20 pixels --#7
    Tortoise.move(20);
    //   Turn the tortoise 90 degrees to the left --#8
    Tortoise.turn(-90);
    //   ------------- End of DrawHouse recipe
  }
}
