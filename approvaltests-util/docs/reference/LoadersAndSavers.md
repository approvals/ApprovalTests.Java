<a id="top"></a>

# Loaders and Savers

<!-- toc -->
## Contents

    * [Comparison vs. Streams](#comparison-vs-streams)
      * [Other benefits](#other-benefits)
  * [HowTos](#howtos)<!-- endToc -->
## What it is

Loaders and Savers is a very simple abstraction to help separate business logic from a database / services layer.

Loaders tend to work similarly in functionality to a stored procedure.

Savers allow you to save changes 

### Executable Query

Executable Queries extend Loaders to allow for easy testing.

## Loaders

The Loader interface looks like:
snippet: loader_interface

The purpose for this is to allow you to split your method into two methods:

1. Small method that gathers the loaders and calls the business logic
1. Larger method that contains your business logic and calls the loaders

For example:




---

[Back to User Guide](README.md#top)
