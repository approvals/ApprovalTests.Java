Loaders and Savers

Approval Tests has the concept of a "loader". Whereas Mocks simulate an object and its functionality in a specific situation, a Loader is intended to permit tests to insert a testable seam into an otherwise untestable method. You can pass in a loader for that heavyweight operation that the method depends on.

For example: If you need to access some data from a database, you can mock out the data connection, and then mock out the query operation, and then mock out the result set (along with whatever intermediate dependencies need to be considered). Alternatively, with a loader you would simply pass a loader function as a parameter into the method, replacing the specific call with a loader that would return the expected results for the test, or the loader that calls the original function to get the live results. (Remember: we're not testing the database functionality. We can assume it works as expected. We're only testing the method of interest.)

Let's see the workflow for adding a loader for testing purposes:

We want to test the following method:

<!-- snippet: step1 -->
<a id='snippet-step1'></a>
```java
public void sendOutSeniorDiscounts(DataBase database, MailServer mailServer) {
    List<Customer> seniorCustomers = database.getSeniorCustomers();
    for (Customer customer : seniorCustomers) {
        Discount seniorDiscount = getSeniorDiscount();
        String message = generateDiscountMessage(customer, seniorDiscount);
        mailServer.sendMessage(customer, message);
    }
}
```
<sup><a href='/approvaltests-util-tests/src/test/java/com/spun/util/persistence/LoadersAndSaversExamplesTest.java#L7-L16' title='Snippet source file'>snippet source</a> | <a href='#snippet-step1' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

In this case, we want to replace the functions that use the DataBase object with Loaders :

Step 1: Determine what the function in question returns in the case we want to test. We start with the test:
```
public void senior_customer_list_includes_only_those_over_age_65() {

&nbsp;   DataBase database = // initialize database object

&nbsp;   MailServer mailServer = // initialize server

&nbsp;   sendOutSeniorDiscounts(database, mailServer);

&nbsp;   Approvals.verifyAll(mailServer.getRecipients();

}
```
In this case we will want to replace the function that returns a list of senior customers. (FIX THIS)

Step 2: Dump the data resulting from a successful query so that we can create a fake object with its contents. (You may need to create a toString() method to make a readable data set)
```java

...

List&lt;Customer&gt; seniorCustomers = database.getSeniorCustomers();

senorCustomers.stream().forEach(System.out::println);

...
```

generates

```
Bob, Jones, 123 Elm St., Tempe, AZ, 14-MAR-1958

Mary, Smith, 345 Oak St., Mason, VA, 04-MAY-1944

...
```

Step 3: Create a result object populated with these values (or at least enough of them to ensure the function using the data will be properly exercised).

```
List&lt;Customer&gt; seniorCustomers = List.of(new Customer("Bob", "Jones", '123 Elm St.", ...), /\* ... \*/);
```

### Step 4: In the original method, replace the function call with a Loader
<pre style="color: gray">
 public void sendOutSeniorDiscounts(DataBase database, MailServer mailServer) {
    List&lt;Customer> seniorCustomers = <b style="color: red">database.getSeniorCustomers(); </b>
    for (Customer customer : seniorCustomers) {
        Discount seniorDiscount = getSeniorDiscount();
        String message = generateDiscountMessage(customer, seniorDiscount);
        mailServer.sendMessage(customer, message);
    }
}
</pre>
# ⇓
<pre style="color: gray">
 public void sendOutSeniorDiscounts(DataBase database, MailServer mailServer) {
<b style="color: green">    Loader&lt;List&lt;Customer>> seniorCustomerLoader = () -> database.getSeniorCustomers(); </b>
    List&lt;Customer> seniorCustomers = <s style="color: red">database.getSeniorCustomers(); </s> <b style="color: green">seniorCustomerLoader.load(); </b>
    for (Customer customer : seniorCustomers) {
        Discount seniorDiscount = getSeniorDiscount();
        String message = generateDiscountMessage(customer, seniorDiscount);
        mailServer.sendMessage(customer, message);
    }
}
</pre>

Step 5: Now we introduce the new loader function as a parameter to the original function. (If you use the IDE's refactoring tools to do this it will save a lot of effort).

```
public void sendOutSeniorDiscounts(DataBase database, MailServer mailServer) {

&nbsp;   List&lt;Customer&gt; seniorCustomers = ((Loader&lt;List<Customer&gt;>) () -> database.getSeniorCustomers()).load();

&nbsp;       // ...

}
```

becomes

```
public void sendOutSeniorDiscounts(DataBase database, MailServer mailServer, Loader&lt;List<Customer&gt;> seniorCustomerLoader) {

&nbsp;   List&lt;Customer&gt; seniorCustomers = seniorCustomerLoader.load();

&nbsp;   // ...

}
```

Step 6: Update the calls to this function (including tests) to use the new Loader parameter.

public void senior_customer_list_includes_only_those_over_age_65() {

&nbsp;   MailServer mailServer = // initialize server

&nbsp;   List&lt;Customer&gt; seniorCustomers = List.of(new Customer("Bob", "Jones", /\* ... /), / ... \*/);

&nbsp;   sendOutSeniorDiscounts(null, mailServer, () -> seniorCustomers));

&nbsp;   Approvals.verifyAll(mailServer.getRecipients());

}

Step 7: Now we can remove the DataBase as a parameter altogether.

public void sendOutSeniorDiscounts(DataBase database, MailServer mailServer, Loader&lt;List<Customer&gt;> seniorCustomerLoader) {

&nbsp;   List seniorCustomers = seniorCustomerLoader.load();

&nbsp;   // ...

}

becomes

public void sendOutSeniorDiscounts(MailServer, Loader&lt;List<Customer&gt;> seniorCustomerLoader) { ... }

and we can now remove that parameter from any function that calls this one - including our test.

This removes the dependency on the database for testing purposes.

Why not just use a mock object to do this?

Because mocking the object in question may require much more than what we are doing here. In this case we are simply replacing a call to a method with the result of that method - as if it were called. Defining a mock object would require more overhead and initialization.

But we still have a dependency on the MailServer in the example above.

Thanks for pointing that out! We'll now show how to solve that problem using a Saver.

Savers

Let's continue with the above example:

public void sendOutSeniorDiscounts(MailServer mailServer, Loader&lt;List<Customer&gt;> seniorCustomerLoader) {

&nbsp;   List seniorCustomers = seniorCustomerLoader.load();

&nbsp;   for (Customer customer : seniorCustomers) {

&nbsp;       Discount seniorDiscount = getSeniorDiscount();

&nbsp;       String message = generateDiscountMessage(customer, seniorDiscount);

&nbsp;       mailServer.sendMessage(customer, message);

&nbsp;   }

}

Here we're sending out an email message. But we don't really care if it gets sent, we just want to make sure it contains the information we expect. Replacing the MailServer object with a Saver is very similar to the process of introducing a Loader.

Step 1: Determine the function that we call to save (or in this case, send) the data.

public void sendOutSeniorDiscounts(MailServer mailServer, Loader&lt;List<Customer&gt;> seniorCustomerLoader) {

&nbsp;   List seniorCustomers = seniorCustomerLoader.load();

&nbsp;   for (Customer customer : seniorCustomers) {

&nbsp;       Discount seniorDiscount = getSeniorDiscount();

&nbsp;       String message = generateDiscountMessage(customer, seniorDiscount);

&nbsp;       **mailServer.sendMessage(new Email(customer, message));**

**mailSaver.save(new Email(customer, message));**

&nbsp;   }

Record Email(Customer customer, String message) {}

}

Step 2: Determine the data we want to test for its saved state. We start with a test. Unfortunately, since the MailServer is a “write-only” object, it’s hard to determine what’s being sent to it. The Saver gives us the ability to easily test for this state now.:

public void senior_customer_message_indicates_benefits_for_those_over_age_65() {

Stack&lt;Email&gt; sent = new Stack<>();

&nbsp;   List seniorCustomers = List.of(new Customer("Bob", "Jones", /\* ... /), / ... \*/);

&nbsp;   sendOutSeniorDiscounts(null, m -> sent.push(m), () -> seniorCustomers));

&nbsp;   Approvals.verifyAll(“Email”, sent);

}

Step 3: In the test, replace the object that does the saving with a Saver.

public void senior_customer_message_indicates_benefits_for_those_over_age_65() {

&nbsp;   TestMailServer mailServer = new TestMailServer();

&nbsp;   List seniorCustomers = List.of(new Customer("Bob", "Jones", /\* ... /), / ... \*/);

&nbsp;   sendOutSeniorDiscounts(null, () -> mailServer, () -> seniorCustomers));

&nbsp;   Approvals.verifyAll(mailServer.getMessage());

}

Class TestMailServer() {

&nbsp;     private String message;

&nbsp;     void sendMessage(Customer customer, String message) {

&nbsp;           this.message = message;

&nbsp;     }

&nbsp;     String getMessage() { return message; }

}

Step 4: In the original method, replace the function call that saves the data with a Saver.

mailServer.sendMessage(customer, message);

becomes

Saver&lt;Email&gt; mailSaver = e -> mailServer.sendMessage(e.customer, e.message);

mailSaver.save(new Email(customer, message)));

Step 5: Now we introduce the new saver function as a parameter to the original function. (If you use the IDE's refactoring tools to do this it will save a lot of effort).

public void sendOutSeniorDiscounts(MailServer mailServer, Loader&lt;List&gt; seniorCustomerLoader) {

&nbsp;       // ...

&nbsp;   ((Saver&lt;MailServer&gt;)() -> mailServer.sendMessage(customer, message)).save();

&nbsp;       // ...

}

becomes

public void sendOutSeniorDiscounts(MailServer mailServer, Saver&lt;MailServer&gt; mailServerSaver, Loader&lt;List&gt; seniorCustomerLoader) {

&nbsp;   // ...

&nbsp;   mailServerSaver.save();

&nbsp;   // ...

}

Step 6: Update the calls to this function to use the new Saver parameter.

public void senior_customer_list_includes_only_those_over_age_65() {

&nbsp;   TestMailServer mailServer = new TestMailServer();

&nbsp;   List seniorCustomers = List.of(new Customer("Bob", "Jones", /\* ... /), / ... \*/);

&nbsp;   sendOutSeniorDiscounts(null, () -> mailServer, () -> seniorCustomers));

&nbsp;   Approvals.verifyAll(mailServer.getRecipients());

}

Step 7: Now we can remove the MailServer as a parameter altogether.

public void sendOutSeniorDiscounts(MailServer mailServer, Saver&lt;MailServer&gt; mailServerSaver, Loader&lt;List&gt; seniorCustomerLoader) {

&nbsp;   // ...

&nbsp;   mailServerSaver.save();

&nbsp;   // ...

}

becomes

public void sendOutSeniorDiscounts(Saver&lt;MailServer&gt; mailServerSaver, Loader&lt;list&gt; seniorCustomerLoader) { ... }

and we can now remove that parameter from any function that calls this one - including our test.

This removes the dependency on the MailServer for testing purposes.

&nbsp;

&nbsp;
