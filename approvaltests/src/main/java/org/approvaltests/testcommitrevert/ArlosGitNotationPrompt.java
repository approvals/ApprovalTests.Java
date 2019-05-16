package org.approvaltests.testcommitrevert;

import java.awt.Container;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.spun.util.ThreadUtils;
import com.spun.util.WindowUtils;
import com.spun.util.logger.SimpleLogger;

/**
 * Todo:
 * figure out shortcut keys
 */
public class ArlosGitNotationPrompt
{
  private String  message = "";
  private boolean done    = false;
  private JPanel  panel   = new JPanel();
  public ArlosGitNotationPrompt()
  {
    setLayout();
  }
  private void setLayout()
  {
    panel.setLayout(new GridBagLayout());
    addCustomCommitPart();
    addQuickKeys();
    addArlosGitNotation();
  }
  private void addArlosGitNotation()
  {
    int position = 4;
    addHelpText("Arlo's Git Notation:", position++, true);
    addHelpText("------ High Risk ------", position++);
    addHelpText("F   Feature", position++);
    addHelpText("B   Bug", position++);
    addHelpText("!!! Refactoring without proof", position++);
    addHelpText("*** Non-compiling commit", position++);
    addHelpText("------ Low  Risk ------", position++);
    addHelpText("r   Provable Refactoring", position++);
    addHelpText("t   Test only change", position++);
    addHelpText("c   Comments (add/delete)", position++);
    addHelpText("a   Automated formatting", position++);
    addHelpText("e   Environment (non-code) changes", position++);
    addHelpText("d   changes only to user documentation files", position++);
  }
  private void addQuickKeys()
  {
    int position = 1;
    addHelpText("Quick Actions:", 2, true);
    createQuickbutton("Rename", "r   Rename", KeyEvent.VK_R, position++);
    createQuickbutton("Inline", "r   Inline", KeyEvent.VK_I, position++);
    createQuickbutton("Extract Method", "r   Extract Method", KeyEvent.VK_M, position++);
    createQuickbutton("Extract Variable", "r   Extract Variable", KeyEvent.VK_V, position++);
    createQuickbutton("Delete Clutter", "r   Delete Clutter", KeyEvent.VK_D, position++);
  }
  private void addCustomCommitPart()
  {
    {
      // Commit Message:
      GridBagConstraints c = new GridBagConstraints();
      JLabel commitLabel = new JLabel("Commit Message:");
      c.anchor = GridBagConstraints.FIRST_LINE_START;
      c.insets = new Insets(10, 10, 0, 0);
      c.gridx = 1;
      c.gridwidth = 1;
      c.gridy = 1;
      panel.add(commitLabel, c);
    }
    JTextField prompt;
    {
      // [Commit Message Text]
      GridBagConstraints c = new GridBagConstraints();
      prompt = new JTextField("");
      prompt.setAction(new AbstractAction()
      {
        @Override
        public void actionPerformed(ActionEvent e)
        {
          doCommit(prompt.getText());
        }
      });
      c.fill = GridBagConstraints.HORIZONTAL;
      c.anchor = GridBagConstraints.NORTHWEST;
      c.insets = new Insets(10, 10, 0, 0);
      c.gridx = 2;
      c.gridwidth = 3;
      c.gridy = 1;
      panel.add(prompt, c);
    }
    {
      // [Commit]
      GridBagConstraints c = new GridBagConstraints();
      JButton commit = new JButton("Commit");
      commit.setMnemonic(KeyEvent.VK_C);
      commit.setDefaultCapable(true);
      c.fill = GridBagConstraints.HORIZONTAL;
      c.anchor = GridBagConstraints.NORTHWEST;
      c.insets = new Insets(10, 10, 0, 0);
      c.gridx = 5;
      c.gridwidth = 1;
      c.gridy = 1;
      commit.addActionListener(e -> doCommit(prompt.getText()));
      panel.add(commit, c);
    }
  }
  private void addHelpText(String text, int position)
  {
    addHelpText(text, position, false);
  }
  private void addHelpText(String text, int position, boolean first)
  {
    GridBagConstraints c = new GridBagConstraints();
    JLabel helpText = new JLabel(text);
    Font font = new Font("Courier", first ? Font.BOLD : Font.PLAIN, 16);
    helpText.setFont(font);
    c.anchor = GridBagConstraints.WEST;
    c.insets = new Insets(first ? 50 : 0, 10, 10, 0);
    c.gridx = 1;
    c.gridwidth = 5;
    c.gridy = position;
    panel.add(helpText, c);
  }
  private void createQuickbutton(String label, String commitMessage, int shortcut, int position)
  {
    GridBagConstraints c = new GridBagConstraints();
    JButton quick = new JButton(label);
    quick.setMnemonic(shortcut);
    quick.setDefaultCapable(true);
    c.insets = new Insets(10, 10, 10, 10);
    c.gridx = position;
    c.gridwidth = 1;
    c.gridy = 3;
    quick.addActionListener(e -> doCommit(commitMessage));
    panel.add(quick, c);
  }
  public void doCommit(String text)
  {
    this.message = text;
    this.done = true;
    Container parent = panel.getParent();
    while (!(parent instanceof JFrame))
    {
      parent = parent.getParent();
    }
    ((JFrame) parent).dispose();
  }
  public static void main(String[] args)
  {
    String message = ArlosGitNotationPrompt.display();
    SimpleLogger.event(message);
  }
  public static String display()
  {
    ArlosGitNotationPrompt panel = new ArlosGitNotationPrompt();
    openInFrame(panel);
    while (!panel.done)
    {
      ThreadUtils.sleep(500);
    }
    return panel.message;
  }
  private static void openInFrame(ArlosGitNotationPrompt panel)
  {
    JFrame test = new JFrame("Commit...");
    test.getContentPane().add(panel.panel);
    test.pack();
    WindowUtils.centerWindow(test);
    test.setVisible(true);
  }
  public JPanel getPanel()
  {
    return panel;
  }
}