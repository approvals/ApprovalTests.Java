package org.lambda.query.extensions.tests;

public class Person
{
  public static class Address
  {
  }
  public static Person Null = new Person("", 0, 0);
  private int     year;
  private double  grade;
  private  String name;
  public Person(String name, int year, double grade)
  {
    this.name = name;
    this.year = year;
    this.grade = grade;
  }
  public int getYear()
  {
    return year;
  }
  public double getGradePoint()
  {
    return grade;
  }
  public String getName()
  {
    return name;
  }
  public String getLastName()
  {
    return name;
  }
  @Override
  public String toString()
  {
    return name + " [year=" + year + ", grade=" + grade + "]";
  }
  public int getAge()
  {
    return 0;
  }
  public Address getAddress()
  {
   return null; 
  }
}
