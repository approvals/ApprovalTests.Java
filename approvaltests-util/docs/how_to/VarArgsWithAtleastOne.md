<a id="top"></a>

# How to create non-empty arrays with var args

<!-- toc -->
## Contents

  * [The problem](#the-problem)
    * [Runtime solution](#runtime-solution)
    * [Compile time solution](#compile-time-solution)
    * [Advantages](#advantages)
    * [Where to use this](#where-to-use-this)<!-- endToc -->

## The problem

Let's say you want to accept a variable amount of arguments, but you have a minimum of one that is required.
Most solutions for this occur at runtime. Of course, it would be better if they occurred at compile time.

### Runtime solution

<!-- snippet: minimalVarargsRuntime -->
<a id='snippet-minimalVarargsRuntime'></a>
```java
public Integer findSmallest(Integer... numbers)
{
  if (numbers == null || numbers.length < 1)
  { throw new IllegalArgumentException("you must have at least one number"); }
  // rest of the code
```
<sup><a href='/approvaltests-util-tests/src/test/java/com/spun/util/MinimumVarargSamples.java#L21-L27' title='Snippet source file'>snippet source</a> | <a href='#snippet-minimalVarargsRuntime' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

### Compile time solution

An easy way to deal with this is force the first parameter by declaring it explicitly.
If you do this, you will want to recombine this array almost immediately `ArrayUtils.combine(T, T...)` is an elegant way to do this.
Please be aware that it will not work with primitives.

<!-- snippet: minimalVarargsCompileTime -->
<a id='snippet-minimalVarargsCompileTime'></a>
```java
public Integer findSmallest(Integer first, Integer... numbers)
{
  Integer[] combined = ArrayUtils.combine(first, numbers);
  // rest of the code
```
<sup><a href='/approvaltests-util-tests/src/test/java/com/spun/util/MinimumVarargSamples.java#L31-L36' title='Snippet source file'>snippet source</a> | <a href='#snippet-minimalVarargsCompileTime' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

### Advantages

If you use the runtime solution, the following will compile but throw an error when you run it.  
If you use the compile time solution, it will not compile.

<!-- snippet: minimalVarargsException -->
<a id='snippet-minimalVarargsException'></a>
```java
int smallest = findSmallest();
```
<sup><a href='/approvaltests-util-tests/src/test/java/com/spun/util/MinimumVarargSamples.java#L12-L14' title='Snippet source file'>snippet source</a> | <a href='#snippet-minimalVarargsException' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

### Where to use this

Even though this only adds a small amount of complexity, we don't tend to use it for methods that are only called internally.
However, it is highly suggested if you have a public facing method.

Following the philosophy of [poka-yoke](https://en.wikipedia.org/wiki/Poka-yoke) or mistake proofing.
