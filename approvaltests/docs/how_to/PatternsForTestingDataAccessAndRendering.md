<a id="top"></a>

# How to separate and test data access and data rendering

<!-- toc -->
## Contents

  * [The problem](#the-problem)
  * [The scenario](#the-scenario)
  * [Schedule for Kitchen](#schedule-for-kitchen)
    * [01/02/2020](#01022020)
  * [Pattern #1: Separating the database](#pattern-1-separating-the-database)
    * [Summary](#summary)
  * [Pattern #2: Testing the loaders](#pattern-2-testing-the-loaders)<!-- endToc -->


## The problem
Many programming tasks involve getting data from a data source, doing something with it,
and rendering the end result to a user. This pattern will show us how to separate those
pieces so they are all individually easy to test.

## The scenario
In our example scenario we are wanting to render the employee schedule for a restaurant.

Here is the basic architectural pattern:
![Architectural pattern](../images/employee_schedule_diagram.svg)

When you request the schedule for a given day, you might get something that looks like:

 <!-- include: LoaderTest.testWithMockedData.approved.md -->
## Schedule for Kitchen
### 01/02/2020

| Person | Position | Start Time | 
| ---- | ------- | ------ |
| Scott | Chef | 5:00 PM |
| Llewellyn | Dishwasher | 6:30 PM |
<!-- endInclude -->

## Pattern #1: Separating the database

The method everyone uses is

<!-- snippet: entry_point_production -->
<a id='snippet-entry_point_production'></a>
```java
// Called by production code
public static String print(Calendar day)
```
<sup><a href='/approvaltests-util-tests/src/test/java/com/spun/util/persistence/KitchenScheduler.java#L11-L14' title='Snippet source file'>snippet source</a> | <a href='#snippet-entry_point_production' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

The problem with this is that it HAS to go to the database. If we test by going 
through the database we are doing an integration test where we want to do a unit test.
So we are going to split this method and wrap the part that gets the data into
a [loader](../../../approvaltests-util/docs/reference/LoadersAndSavers.md#top).
Once split, we will have a second method that looks like this:

<!-- snippet: entry_point_test -->
<a id='snippet-entry_point_test'></a>
```java
// Called by tests and the above function
public static String print(Loader<List<Shift>> shifts, Calendar day)
```
<sup><a href='/approvaltests-util-tests/src/test/java/com/spun/util/persistence/KitchenScheduler.java#L18-L21' title='Snippet source file'>snippet source</a> | <a href='#snippet-entry_point_test' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

The important part about this method is that it has no knowledge of a database.
Now we can call the remaining method and pass in a fake loader to mock the data. 
Here is a test that does that:

<!-- snippet: testing_rendered_data -->
<a id='snippet-testing_rendered_data'></a>
```java
void testWithMockedData()
{
  try (WithTimeZone tz = new WithTimeZone("PST"))
  {
    Calendar day = DateUtils.asCalendar(DateUtils.parse("2020/01/02"));
    // Mocked data
    MockLoader<List<Shift>> shifts = new MockLoader<>(
        Arrays.asList(new Shift("Scott", "Chef", DateUtils.setTime(day, 8, 0)),
            new Shift("Llewellyn", "Dishwasher", DateUtils.setTime(day, 9, 30))));
    verifyMarkdown(KitchenScheduler.print(shifts, day));
  }
}
```
<sup><a href='/approvaltests-util-tests/src/test/java/com/spun/util/persistence/LoaderTest.java#L20-L33' title='Snippet source file'>snippet source</a> | <a href='#snippet-testing_rendered_data' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

### Summary

1. Separate your method into a very small method that creates loaders and savers and calls ...
1. A method that takes the loaders and savers and does all the business logic
1. Ignore the first method and test the second method passing in fake (mock) data for your loaders and savers

## Pattern #2: Testing the loaders

