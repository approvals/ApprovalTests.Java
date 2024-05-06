package com.spun.util.persistence;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

import java.util.List;

public class LoadersAndSaversExamplesTest
{
  public static class Step0
  {
    // begin-snippet: step0
    @Test
    public void senior_customer_list_includes_only_those_over_age_65()
    {
      DataBase database = initializeDatabase();
      MailServer mailServer = initializeMailServer();
      sendOutSeniorDiscounts(database, mailServer);
      Approvals.verifyAll("", mailServer.getRecipients());
    }
    // end-snippet
    private MailServer initializeMailServer()
    {
      return new MailServer();
    }
    private DataBase initializeDatabase()
    {
      return null;
    }
    private void sendOutSeniorDiscounts(DataBase database, MailServer mailServer)
    {
    }
  }
  public static class Step0_5
  {
    @Test
    void test_dump_data()
    {
      MyDatabase database = new MyDatabase();
      // begin-snippet: step_capture_data
      List<Customer> seniorCustomers = database.getSeniorCustomers();
      seniorCustomers.stream().forEach(System.out::println);
      // end-snippet
      Approvals.verifyAll("", seniorCustomers, c -> c.toString());
    }
    public static class MyDatabase
    {
      public List<Customer> getSeniorCustomers()
      {
        return List.of(new Customer("Bob, Jones, 123 Elm St., Tempe, AZ, 14-MAR-1958"),
            new Customer("Mary, Smith, 345 Oak St., Mason, VA, 04-MAY-1944"));
      }
    }
  }
  class Step1
  {
    // begin-snippet: step1
    public void sendOutSeniorDiscounts(DataBase database, MailServer mailServer)
    {
      List<Customer> seniorCustomers = database.getSeniorCustomers();
      for (Customer customer : seniorCustomers)
      {
        Discount seniorDiscount = getSeniorDiscount();
        String message = generateDiscountMessage(customer, seniorDiscount);
        mailServer.sendMessage(customer, message);
      }
    }
    // end-snippet
  }
  class Step2
  {
    // begin-snippet: step2
    public void sendOutSeniorDiscounts(DataBase database, MailServer mailServer)
    {
      Loader<List<Customer>> seniorCustomerLoader = () -> database.getSeniorCustomers();
      List<Customer> seniorCustomers = seniorCustomerLoader.load();
      for (Customer customer : seniorCustomers)
      {
        Discount seniorDiscount = getSeniorDiscount();
        String message = generateDiscountMessage(customer, seniorDiscount);
        mailServer.sendMessage(customer, message);
      }
    }
    // end-snippet
  }
  class Step3
  {
    // begin-snippet: step3
    public void sendOutSeniorDiscounts(DataBase database, MailServer mailServer)
    {
      sendOutSeniorDiscounts(mailServer, database::getSeniorCustomers); // +
    } // +
    // +
    public void sendOutSeniorDiscounts(MailServer mailServer, Loader<List<Customer>> seniorCustomerLoader)
    { // +
      List<Customer> seniorCustomers = seniorCustomerLoader.load();
      for (Customer customer : seniorCustomers)
      {
        Discount seniorDiscount = getSeniorDiscount();
        String message = generateDiscountMessage(customer, seniorDiscount);
        mailServer.sendMessage(customer, message);
      }
    }
    // end-snippet
  }
  private String generateDiscountMessage(Customer customer, Discount seniorDiscount)
  {
    return null;
  }
  private Discount getSeniorDiscount()
  {
    return null;
  }
  private class DataBase
  {
    public List<Customer> getSeniorCustomers()
    {
      return List.of();
    }
  }
  private static class MailServer
  {
    public void sendMessage(Customer customer, String message)
    {
    }
    public String[] getRecipients()
    {
      return new String[]{};
    }
  }
  private static class Customer
  {
    private final String s;
    public Customer(String s)
    {
      this.s = s;
    }
    @Override
    public String toString()
    {
      return s;
    }
  }
  private class Discount
  {
  }
}
