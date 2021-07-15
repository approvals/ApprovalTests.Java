<a id="top"></a>

# Query and Queryable

<!-- toc -->
## Contents

  * [Extending Queryable](#extending-queryable)<!-- endToc -->
## What it is
Query (and the Queryable wrapper) is an alternative to Streams. In other words it is an implementation of Map/Reduce 
based on the naming style of MS Linq.

### Comparison vs. Streams
Here is an example of filtering a list of numbers for only even numbers, sorted in reverse order:

snippet: query_example

Here is the exact same function but using Java Streams:

snippet: stream_example

#### Other benefits
* Simpler interface
* Queryable<T> extends List<T>
* Queryable is customizable
* Uses SQL syntax

## HowTos
* [How to extend Queryable](../how_to/ExtendQueryable.md#top)


---

[Back to User Guide](README.md#top)
