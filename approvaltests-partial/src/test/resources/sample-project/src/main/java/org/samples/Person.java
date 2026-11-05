package org.samples;

public class Person
{
  private final String  firstName;
  private final String  lastName;
  private final boolean isMale;
  private final int     age;
  public Person(String firstName, String lastName, boolean isMale, int age)
  {
    this.firstName = firstName;
    this.lastName = lastName;
    this.isMale = isMale;
    this.age = age;
  }
  public String getFirstName()
  {
    return firstName;
  }
  public String getLastName()
  {
    return lastName;
  }
  public boolean isMale()
  {
    return isMale;
  }
  public int getAge()
  {
    return age;
  }
}
