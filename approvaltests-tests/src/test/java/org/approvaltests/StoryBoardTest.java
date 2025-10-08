package org.approvaltests;

import com.spun.util.markdown.MarkdownCompatible;
import org.junit.jupiter.api.Test;
import org.lambda.functions.Function2;
import org.lambda.query.Queryable;
import org.lambda.utils.Grid;

public class StoryBoardTest
{
  @Test
  void gameOfLife()
  {
    Approvals.settings().allowMultipleVerifyCallsForThisMethod();
    GameOfLife gameOfLife = new GameOfLife((x, y) -> y == 2 && 1 <= x && x <= 3);
    StoryBoard storyboard = new StoryBoard();
    storyboard.add(gameOfLife);
    storyboard.addFrames(3, gameOfLife::advance);
    Approvals.verify(storyboard);
    gameOfLife = new GameOfLife((x, y) -> y == 2 && 1 <= x && x <= 3);
    Approvals.verify(StoryBoard.createSequence(gameOfLife, 3, gameOfLife::advance));
    gameOfLife = new GameOfLife((x, y) -> y == 2 && 1 <= x && x <= 3);
    // begin-snippet: StoryBoardExample
    Approvals.verify(new StoryBoard().add(gameOfLife).addFrames(3, gameOfLife::advance));
    // end-snippet
  }

  @Test
  void gameOfLifeWithDescription()
  {
    try (VerifiableStoryBoard storyboard = StoryBoardApprovals.verifyStoryboard())
    {
      GameOfLife gameOfLife = new GameOfLife((x, y) -> y == 2 && 1 <= x && x <= 3);
      storyboard.addDescription("Game of Life");
      storyboard.add(gameOfLife);
      storyboard.addFrame("Start Game", gameOfLife.advance());
      storyboard.addFrame(gameOfLife.advance());
      storyboard.addDescriptionWithData("setting alive", gameOfLife.setAliveCell("*"));
      storyboard.addDescriptionWithData("setting dead", gameOfLife.setDeadCell("_"));
      storyboard.addFrames(2, gameOfLife::advance);
      storyboard.addDescriptionWithData("setting dead", gameOfLife.setDeadCell(" "));
      storyboard.addFrames(1, gameOfLife::advance);
    }
  }

  @Test
  //  @UseReporter(QuietReporter.class)
  void gameOfLifeInMarkdown()
  {
    GameOfLife gameOfLife = new GameOfLife((x, y) -> y == 2 && 1 <= x && x <= 3);
    MarkdownStoryBoard storyboard = new MarkdownStoryBoard();
    storyboard.addTitle("Game of Life");
    storyboard.add(gameOfLife);
    storyboard.addFrame("Start Game", gameOfLife.advance());
    storyboard.addFrame(gameOfLife.advance());
    storyboard.addDescriptionWithData("setting alive", gameOfLife.setAliveCell("*"));
    storyboard.addDescriptionWithData("setting dead", gameOfLife.setDeadCell("_"));
    storyboard.addFrames(2, gameOfLife::advance);
    storyboard.addDescriptionWithData("setting dead", gameOfLife.setDeadCell(" "));
    storyboard.addFrames(1, gameOfLife::advance);
    Approvals.verify(storyboard);
  }
  private static class GameOfLife implements MarkdownCompatible
  {
    private String                               deadSymbol  = ".";
    private String                               aliveSymbol = "x";
    private Function2<Integer, Integer, Boolean> board;
    public GameOfLife(Function2<Integer, Integer, Boolean> board)
    {
      this.board = board;
    }

    public GameOfLife advance()
    {
      final Function2<Integer, Integer, Boolean> old = this.board;
      this.board = (x, y) -> {
        final int aliveNeighbours = Queryable
            .as(old.call(x - 1, y - 1), old.call(x - 0, y - 1), old.call(x + 1, y - 1), old.call(x - 1, y - 0),
                //            old.call(x - 0, y - 0),
                old.call(x + 1, y - 0), old.call(x - 1, y + 1), old.call(x - 0, y + 1), old.call(x + 1, y + 1))
            .where(a -> a).size();
        return aliveNeighbours == 3 || (aliveNeighbours == 2 && old.call(x, y));
      };
      return this;
    }

    @Override
    public String toString()
    {
      return Grid.print(5, 5, (x, y) -> board.call(x, y) ? aliveSymbol + " " : deadSymbol + " ");
    }

    @Override
    public String toMarkdown()
    {
      return Grid.printMarkdown(5, 5, (x, y) -> board.call(x, y) ? aliveSymbol : deadSymbol);
    }

    public String setAliveCell(String s)
    {
      this.aliveSymbol = s;
      return s;
    }

    public String setDeadCell(String s)
    {
      this.deadSymbol = s;
      return s;
    }
  }
}
