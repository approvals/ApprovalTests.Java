package org.teachingkidsprogramming.recipes.completed;

import org.teachingextensions.logo.Tortoise;

public class WaterMark
{
  public static void main(String[] args)
  {
    //     Get a picture of sand from Flickr
    //
    //     Use that picture as your background
    //
    //    A side is 1 pixel long
    int length = 1;
    //    Do the following 200 times
    for (int i = 0; i < 200; i++)
    {
      //     Get the color of the pixel the tortoise is currently on
      //
      //     Use that color for the line the tortoise draws
      //
      //     Increase the length of a side by 1 pixel
      length += 1;
      //     Move the tortoise the length of a side
      Tortoise.move(length);
      //     Turn the tortoise 1/3rd of 360 degrees to the right
      Tortoise.turn(360.0 / 3);
      //     Turn the tortoise 1 more degree
      Tortoise.turn(1);
    }
  }
}
