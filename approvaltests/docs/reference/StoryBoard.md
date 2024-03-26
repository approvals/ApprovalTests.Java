<a id="top"></a>

# StoryBoard

<!-- toc -->
## Contents

  * [How to verify sequences](#how-to-verify-sequences)<!-- endToc -->

## How to verify sequences

StoryBoards can be very helpful to tell a story of steps that happen over time.
Here is a simple example 

<!-- snippet: StoryBoardExample -->
<a id='snippet-StoryBoardExample'></a>
```java
Approvals.verify(new StoryBoard().add(gameOfLife).addFrames(3, gameOfLife::advance));
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/StoryBoardTest.java#L23-L25' title='Snippet source file'>snippet source</a> | <a href='#snippet-StoryBoardExample' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->

which produces

<!-- snippet: StoryBoardTest.gameOfLife.approved.txt -->
<a id='snippet-StoryBoardTest.gameOfLife.approved.txt'></a>
```txt
Initial:
.  .  .  .  .  
.  .  .  .  .  
.  x  x  x  .  
.  .  .  .  .  
.  .  .  .  .  


Frame #1:
.  .  .  .  .  
.  .  x  .  .  
.  .  x  .  .  
.  .  x  .  .  
.  .  .  .  .  


Frame #2:
.  .  .  .  .  
.  .  .  .  .  
.  x  x  x  .  
.  .  .  .  .  
.  .  .  .  .  


Frame #3:
.  .  .  .  .  
.  .  x  .  .  
.  .  x  .  .  
.  .  x  .  .  
.  .  .  .  .
```
<sup><a href='/approvaltests-tests/src/test/java/org/approvaltests/StoryBoardTest.gameOfLife.approved.txt#L1-L30' title='Snippet source file'>snippet source</a> | <a href='#snippet-StoryBoardTest.gameOfLife.approved.txt' title='Start of snippet'>anchor</a></sup>
<!-- endSnippet -->
