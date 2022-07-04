<a id="top"></a>

# AwtApprovals

<!-- toc -->
## Contents

  * [Paintables](#paintables)
    * [Why](#why)
  * [How To](#how-to)
    * [create an animated gif](#create-an-animated-gif)<!-- endToc -->

## Paintables

### Why

Java AWT is a nice utility for rendering simple graphics, but it does not play well with CI systems,
especially when it is headless. Paintables provides an abstraction that allows you to use Awt
independently of OS and CI.

## How To

### create an animated gif

Sometimes you want to verify a series of steps. A great way to visualize this is with an animated
gif.

![Hexagonal Game of Life](https://github.com/isidore/HexGameOfLife/blob/master/src/test/java/org/gameoflife/hex/graphics/GameOfLifePanelTest.testCompellingSequence.approved.gif)

To create such a gif you pass the number of frames you want plus a function that returns a Paintable
for every frame. Here is an example for a simple expanding box:

<!-- snippet: SequencePaintables -->
<a id='snippet-sequencepaintables'></a>
```java
SquareDrawer squareDrawer = new SquareDrawer();
AwtApprovals.verifySequence(5, f -> squareDrawer.setSquareSize(f * 10));
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/awt/ApprovalsTest.java#L51-L54' title='Snippet source file'>snippet source</a> | <a href='#snippet-sequencepaintables' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

**Note**: Method overloads allow specifying the time between frames or the time for each frame.



