package org.approvaltests.utils;

import org.approvaltests.Approvals;
import org.approvaltests.core.Options;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

/**
 * A try-with-resources compatible utility for capturing console output.
 * Captures both System.out and System.err streams and provides methods
 * to retrieve the captured content as strings.
 */
public class ConsoleOutput implements AutoCloseable
{
  private final ByteArrayOutputStream outContent;
  private final ByteArrayOutputStream errContent;
  private final PrintStream           originalOut;
  private final PrintStream           originalErr;
  /**
   * Creates a new ConsoleOutput instance and begins capturing console output.
   * Both System.out and System.err are redirected to internal buffers.
   */
  public ConsoleOutput()
  {
    // Store original streams
    originalOut = System.out;
    originalErr = System.err;
    // Create capture streams
    outContent = new ByteArrayOutputStream();
    errContent = new ByteArrayOutputStream();
    // Redirect system streams
    System.setOut(new PrintStream(outContent));
    System.setErr(new PrintStream(errContent));
  }
  /**
   * Returns the captured standard output as a string.
   * @return The content written to System.out since this ConsoleOutput was created
   */
  public String getOutput()
  {
    return outContent.toString();
  }
  /**
   * Returns the captured standard error as a string.
   * @return The content written to System.err since this ConsoleOutput was created
   */
  public String getError()
  {
    return errContent.toString();
  }
  /**
   * Verifies the captured standard output using ApprovalTests.
   * This is a convenience method that calls Approvals.verify() on the captured output.
   */
  public void verifyOutput()
  {
    verifyOutput(new Options());
  }
  /**
   * Verifies the captured standard output using ApprovalTests with options.
   * This is a convenience method that calls Approvals.verify() on the captured output.
   * @param options The options to use for verification
   */
  public void verifyOutput(Options options)
  {
    Approvals.verify(getOutput(), options);
  }
  /**
   * Verifies the captured standard error using ApprovalTests.
   * This is a convenience method that calls Approvals.verify() on the captured error output.
   */
  public void verifyError()
  {
    Approvals.verify(getError());
  }
  /**
   * Verifies the captured standard error using ApprovalTests with options.
   * This is a convenience method that calls Approvals.verify() on the captured error output.
   * @param options The options to use for verification
   */
  public void verifyError(Options options)
  {
    Approvals.verify(getError(), options);
  }
  /**
   * Verifies both captured standard output and error using ApprovalTests.
   * This is a convenience method that calls Approvals.verify() on both the output and error combined.
   */
  public void verifyAll()
  {
    verifyAll(new Options());
  }
  /**
   * Verifies both captured standard output and error using ApprovalTests with options.
   * This is a convenience method that calls Approvals.verify() on both the output and error combined.
   * @param options The options to use for verification
   */
  public void verifyAll(Options options)
  {
    String combined = "Output:\n" + getOutput() + "\nError:\n" + getError();
    Approvals.verify(combined, options);
  }
  /**
   * Restores the original System.out and System.err streams.
   * This method is automatically called when used in a try-with-resources block.
   */
  @Override
  public void close()
  {
    // Restore original streams
    System.setOut(originalOut);
    System.setErr(originalErr);
  }
}