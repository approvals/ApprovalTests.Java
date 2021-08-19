<a id="top"></a>

# How to separate and test data access and data rendering

<!-- toc -->
## Contents

  * [The problem](#the-problem)
  * [The scenario](#the-scenario)<!-- endToc -->


## The problem
Many programming tasks involve getting data from a data source, doing something with it,
and rendering the end result to a user. This pattern will show us how to separate those
pieces so they are all individually easy to test.

## The scenario
In our example scenario we are wanting to render the employee schedule for a restaurant.

Here is the basic architectural pattern:
![Architectural pattern](../images/employee_schedule_diagram.svg)

When you request the schedule for a given day, you might get something that looks like:

include: LoaderTest.testWithMockedData.approved.md

## Pattern #1: Separating the database

The method everyone uses is

snippet: entry_point_production

The problem with this is that it HAS to go to the database. If we test by going 
through the database we are doing an integration test where we want to do a unit test.
So we are going to split this method and wrap the part that gets the data into
a [loader](../../../approvaltests-util/docs/reference/LoadersAndSavers.md#top).
Once split, we will have a second method that looks like this:

snippet: entry_point_test

The important part about this method is that it has no knowledge of a database.
Now we can call the remaining method and pass in a fake loader to mock the data. 
Here is a test that does that:

snippet: testing_rendered_data

### Summary

1. Separate your method into a very small method that creates loaders and savers and calls ...
1. A method that takes the loaders and savers and does all the business logic
1. Ignore the first method and test the second method passing in fake (mock) data for your loaders and savers

## Pattern #2: Testing the loaders

