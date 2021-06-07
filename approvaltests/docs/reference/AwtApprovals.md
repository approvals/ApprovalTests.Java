<a id="top"></a>

# AwtApprovals

toc

## Paintables

### Why

Java AWT is a nice utility for rendering simple graphics, but it does not play well with CI systems,
especially when it is headless. Paintables provides an abstraction that allows you to use Awt
independently of OS and CI.

## How To

### create an animated gif

Sometimes you want to verify a series of steps. A great way to visualize this is with an animated
gif.

![Hexagonal Game of Life](https://github.com/isidore/HexGameOfLife/blob/master/src/test/java/org/gameoflife/hex/GameOfLifePanelTest.testCompellingSequence.approved.gif)

To create such a gif you pass the number of frames you want plus a function that returns a Paintable
for every frame. Here is an example for a simple expanding box:

snippet: SequencePaintables

**Note**: Method overloads allow specifying the time between frames or the time for each frame.



