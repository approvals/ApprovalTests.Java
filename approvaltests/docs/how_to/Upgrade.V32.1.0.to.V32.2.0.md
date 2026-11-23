<a id="top"></a>

# Upgrade from 32.1.0 to 32.2.0

32.2.0 adds two features and deprecates three constructors. There are no behavior changes.
Approved files keep their names. You do not need to re-approve anything.

## For AI coding agents

1. Change the dependency version to `32.2.0`.
2. Compile. Nothing should break.
3. If you construct `ApprovalBinaryFileWriter` yourself, you will see deprecation warnings. Fix them as shown in [ApprovalBinaryFileWriter takes Options](#approvalbinaryfilewriter-takes-options).
4. Optionally, replace hand-written binary verification with [`Approvals.verifyBinary`](#new-approvalsverifybinary).
5. Run the tests. They should pass without new `.received.` files.

## New: Approvals.verifyBinary

Approve raw bytes, such as images or PDFs, without writing your own writer:

```java
Approvals.verifyBinary(bytes, ".png");
Approvals.verifyBinary(bytes, ".png", options);
```

If you used to write

```java
Approvals.verify(new ApprovalBinaryFileWriter(new ByteArrayInputStream(bytes), "png"));
```

you can replace it with `Approvals.verifyBinary(bytes, ".png")`. See [Verify binary data](VerifyBinary.md#top).

## New: Scrubbers.removeLinesContaining

Remove every line that contains some text before comparing:

```java
Approvals.verify(text, new Options().withScrubber(Scrubbers.removeLinesContaining("DEBUG")));
```

See [Line Scrubbing](../Scrubbers.md#line-scrubbing).

## ApprovalBinaryFileWriter takes Options

The constructors that take `String fileExtensionWithoutDot` are deprecated. Use the ones that take `Options`, which read the extension from `options.forFile().getFileExtension()`:

```java
// before
new ApprovalBinaryFileWriter(stream, "png")
// after
new ApprovalBinaryFileWriter(stream, new Options().forFile().withExtension(".png"))
```

`withExtension` accepts the extension with or without the dot.

---

[Back to User Guide](/approvaltests/docs/README.md#top)
