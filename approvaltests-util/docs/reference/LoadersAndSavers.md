<a id="top"></a>

# Loaders and Savers

<!-- toc -->
## Contents

    * [ExecutableCommand](#executable-query)
  * [Loaders](#loaders)<!-- endToc -->
## What it is

Loaders and Savers is a very simple abstraction to help separate business logic from a database / services layer.

Loaders tend to work similarly in functionality to a stored procedure.

Savers allow you to save changes 

### ExecutableCommand

`ExecutableCommand`s extend Loaders to allow for easy testing.

## Loaders

The Loader interface looks like:
<!-- snippet: loader_interface -->
<a id='snippet-loader_interface'></a>
```java
public T load();
```
<sup><a href='/approvaltests-util/src/main/java/com/spun/util/persistence/Loader.java#L5-L7' title='Snippet source file'>snippet source</a> | <a href='#snippet-loader_interface' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

The purpose for this is to allow you to split your method into two methods:

1. Small method that gathers the loaders and calls the business logic
1. Larger method that contains your business logic and calls the loaders

For example:




---

[Back to User Guide](README.md#top)
