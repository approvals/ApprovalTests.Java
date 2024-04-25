package com.spun.util.persistence;

import java.util.List;

public class LoadersAndSaversExamplesTest
{
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
  private class MailServer
  {
    public void sendMessage(Customer customer, String message)
    {
    }
  }
  private class Customer
  {
  }
  private class Discount
  {
  }
}
