# Verify Binary

## Purpose
Lets users approve raw bytes (images, PDFs, serialized data, anything not text) without writing their own `ApprovalWriter`.
Ported from ApprovalTests.Python `verify_binary()`.

## Current State
`ApprovalBinaryFileWriter` already exists in `org.approvaltests.writers`, but nothing uses it, it has no tests, and it is not documented.
Today a user would have to write `Approvals.verify(new ApprovalBinaryFileWriter(new ByteArrayInputStream(bytes), "png"))`.

## API
```java
Approvals.verifyBinary(byte[] data, String fileExtension)
Approvals.verifyBinary(byte[] data, String fileExtension, Options options)
```

`fileExtension` works with or without the leading dot (`".png"` or `"png"`), the same as `Options.forFile().withExtension`.

## Behavior
1. Writes the bytes unchanged to the received file. No text conversion, no line-ending changes, no scrubbing.
2. The file extension comes from the `fileExtension` argument.
3. Compares received and approved byte for byte (the default file comparison) and reports with the normal reporter.

## Implementation
Delegate to the existing `ApprovalBinaryFileWriter` (wrapping the bytes in a `ByteArrayInputStream`) instead of adding a new writer.

## Unit Tests
Inline approvals don't fit binary output, so use an approved file.
- One test that verifies a small fixed byte array (e.g. the bytes of a tiny PNG or a few non-UTF-8 bytes like `0x00 0xFF 0xFE`) with `.bin` or `.png`, producing a committed `.approved.*` file.

## Docs
- Add a short how-to (`approvaltests/docs/how_to/VerifyBinary.md`), linked from the User Guide.
- Change the FeatureMap row "Verify binary data" from ❌ missing to `Approvals.verifyBinary`.
