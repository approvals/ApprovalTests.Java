# Console Output Utility
**note:** All code examples are in Java

The `ConsoleOutput` class provides a try-with-resources compatible utility for capturing console output during tests.

## Purpose
Allows users to capture and verify console output (both System.out and System.err) in approval tests, making it easy to test code that writes to the console.

## Features
- **Automatic capture**: Redirects System.out and System.err to internal buffers
- **Try-with-resources compatible**: Automatically restores original streams when closed
- **ApprovalTests integration**: Built-in methods for verifying captured output
- **Separate stream handling**: Can capture and verify both stdout and stderr independently

## Behavior
1. **Constructor**: Stores original streams and redirects System.out/System.err to capture buffers
2. **getOutput()**: Returns captured standard output as a string
3. **getError()**: Returns captured standard error as a string
4. **verifyOutput()**: Convenience method that calls Approvals.verify() on captured output
5. **verifyError()**: Convenience method that calls Approvals.verify() on captured error
5. **verifyAll()**: Convenience method that calls Approvals.verify() on both the output and error


6. **close()**: Restores original System.out and System.err streams

## Usage Example
```java
try (ConsoleOutput console = new ConsoleOutput()) {
    System.out.println("Hello, World!");
    console.verifyOutput(); // Verifies "Hello, World!\n"
}
```

## Integration
- Works seamlessly with ApprovalTests
- Automatically handles stream restoration even if exceptions occur

## Tests
1. Test verifyOutput
2. Test verifyError
3. Test verifyAll