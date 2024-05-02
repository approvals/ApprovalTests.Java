package com.spun.util.persistence;

import org.approvaltests.Approvals;
import org.junit.jupiter.api.Test;

import java.util.List;

public class LoadersAndSaversExamplesTest {
    public static class Step0 {
        // begin-snippet: step0
        @Test
        public void senior_customer_list_includes_only_those_over_age_65() {
            DataBase database = initializeDatabase();
            MailServer mailServer = initializeMailServer();
            sendOutSeniorDiscounts(database, mailServer);
            Approvals.verifyAll("", mailServer.getRecipients());
        }
        // end-snippet

        private MailServer initializeMailServer() {
            return new MailServer();
        }

        private DataBase initializeDatabase() {
            return null;
        }

        private void sendOutSeniorDiscounts(DataBase database, MailServer mailServer) {

        }
    }

    class Step1 {
        // begin-snippet: step1
        public void sendOutSeniorDiscounts(DataBase database, MailServer mailServer) {
            List<Customer> seniorCustomers = database.getSeniorCustomers();
            for (Customer customer : seniorCustomers) {
                Discount seniorDiscount = getSeniorDiscount();
                String message = generateDiscountMessage(customer, seniorDiscount);
                mailServer.sendMessage(customer, message);
            }
        }
        // end-snippet
    }

    class Step2 {
        // begin-snippet: step2
        public void sendOutSeniorDiscounts(DataBase database, MailServer mailServer) {
            Loader<List<Customer>> seniorCustomerLoader = () -> database.getSeniorCustomers();
            List<Customer> seniorCustomers = seniorCustomerLoader.load();
            for (Customer customer : seniorCustomers) {
                Discount seniorDiscount = getSeniorDiscount();
                String message = generateDiscountMessage(customer, seniorDiscount);
                mailServer.sendMessage(customer, message);
            }
        }
        // end-snippet
    }

    class Step3 {
        // begin-snippet: step3
        public void sendOutSeniorDiscounts(DataBase database, MailServer mailServer) {
            sendOutSeniorDiscounts(mailServer, database::getSeniorCustomers); // +
        } // +

        // +
        public void sendOutSeniorDiscounts(MailServer mailServer, Loader<List<Customer>> seniorCustomerLoader) { // +
            List<Customer> seniorCustomers = seniorCustomerLoader.load();
            for (Customer customer : seniorCustomers) {
                Discount seniorDiscount = getSeniorDiscount();
                String message = generateDiscountMessage(customer, seniorDiscount);
                mailServer.sendMessage(customer, message);
            }
        }
        // end-snippet
    }

    private String generateDiscountMessage(Customer customer, Discount seniorDiscount) {
        return null;
    }

    private Discount getSeniorDiscount() {
        return null;
    }

    private class DataBase {
        public List<Customer> getSeniorCustomers() {
            return List.of();
        }
    }

    private static class MailServer {
        public void sendMessage(Customer customer, String message) {
        }

        public String[] getRecipients() {
            return new String[] {};
        }
    }

    private class Customer {
    }

    private class Discount {
    }
}
