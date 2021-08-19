<a id="top"></a>

# How to separate and test data access and data rendering

<!-- toc -->
<!-- endToc -->


## The problem
Many programming tasks involve getting data from a data source, doing something with it,
and rendering the end result to a user. This pattern will show us how to separate those
pieces so they are all individually easy to test.

## The scenario
In our example scenario we are wanting to render the employee schedule for a restaurant.


