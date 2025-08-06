# ApprovalTests.Java Architecture

## Core Components Overview

```mermaid
classDiagram
    class Approvals {
        +verify(String, Options)
        +verify(ApprovalWriter, Options)
        +verify(ApprovalWriter, ApprovalNamer, Options)    
    }
    
    class Options {
        -Map~Fields,Object~ fields
        +forFile() FileOptions
        +withReporter(ApprovalFailureReporter)
        +withScrubber(Scrubber)
        +createWriter(Object) ApprovalWriter
        +inline(String expected)
    }
    
    class FileOptions {
        +withExtension(String)
        +withNamer(ApprovalNamer)
        +getNamer() ApprovalNamer
    }
    
    class ApprovalWriter {
        <<interface>>
        +writeReceivedFile(File) File
        +getFileExtensionWithDot() String
    }
    
    class ApprovalNamer {
        <<interface>>
        +getApprovedFile(String) File
        +getReceivedFile(String) File
        +getApprovalName() String
        +getSourceFilePath() String
        +addAdditionalInformation(String) ApprovalNamer
        +getAdditionalInformation() String
    }
    
    class StackTraceNamer {
        -StackTraceReflectionResult info
        -String additionalInformation
    }
    
    class FileApprover {
        -File received
        -File approved
        -ApprovalWriter writer
        +approve() VerifyResult
        +reportFailure(ApprovalFailureReporter) VerifyResult
        +verify(ApprovalFailureReporter)
    }
    
    class ApprovalFailureReporter {
        <<interface>>
        +report(String received, String approved) boolean
    }
    
    class FirstWorkingReporter {
        -ApprovalFailureReporter[] reporters
        +approveWhenReported() VerifyResult
    }
    
    class Verifiable {
        <<interface>>
        +getVerifyParameters(Options) VerifyParameters
    }
    
    class VerifyResult {
        <<enumeration>>
        SUCCESS
        FAILURE
    }
    
    class Scrubber {
        <<interface>>
        +scrub(String) String
    }
    
    class VerifyParameters {
        +writer ApprovalWriter
        +namer ApprovalNamer
        +reporter ApprovalFailureReporter
    }

    %% Relationships - Inheritance
    StackTraceNamer ..|> ApprovalNamer : implements
    FirstWorkingReporter ..|> ApprovalFailureReporter : implements
    
    %% Relationships - Dependencies and Associations
    Approvals ..> Options : uses
    Approvals ..> ApprovalWriter : creates via Options
    Approvals ..> ApprovalNamer : creates
    Approvals ..> FileApprover : creates
    
    Options --> FileOptions : contains
    Options ..> ApprovalWriter : creates
    Options ..> ApprovalNamer : holds
    Options ..> ApprovalFailureReporter : holds
    Options ..> Scrubber : holds
    
    FileOptions ..> ApprovalNamer : manages
    
    FileApprover --> ApprovalWriter : uses
    FileApprover --> ApprovalNamer : uses
    FileApprover ..> VerifyResult : returns
    FileApprover ..> ApprovalFailureReporter : reports to
    
    Verifiable ..> VerifyParameters : creates
    VerifyParameters --> ApprovalWriter : contains
    VerifyParameters --> ApprovalNamer : contains
    VerifyParameters --> ApprovalFailureReporter : contains
```

## Approval Flow Sequence

```mermaid
sequenceDiagram
    participant User
    participant Approvals
    participant Options
    participant FileApprover
    participant ApprovalWriter
    participant ApprovalNamer
    participant ApprovalFailureReporter
    
    User->>Approvals: verify(content, options)
    Approvals->>Options: createWriter(content)
    Options-->>Approvals: ApprovalWriter
    Approvals->>Options: getNamer()
    Options-->>Approvals: ApprovalNamer
    
    Approvals->>FileApprover: new(writer, namer)
    Approvals->>FileApprover: verify(reporter)
    
    FileApprover->>ApprovalWriter: writeReceivedFile()
    ApprovalWriter-->>FileApprover: received file
    
    FileApprover->>ApprovalNamer: getApprovedFile()
    ApprovalNamer-->>FileApprover: approved file
    
    FileApprover->>FileApprover: compare files
    
    alt Files Match
        FileApprover-->>Approvals: SUCCESS
    else Files Don't Match
        FileApprover->>ApprovalFailureReporter: report(received, approved)
        ApprovalFailureReporter-->>FileApprover: handled
        FileApprover-->>Approvals: FAILURE
    end
```

## Key Architectural Patterns

### 1. **Options Pattern**
The `Options` class acts as a configuration object that bundles all the customizable aspects of the approval process:
- Reporter selection
- File naming strategy
- Content scrubbing
- Writer factory
- File extensions

### 2. **Strategy Pattern**
Multiple interfaces allow for pluggable implementations:
- `ApprovalFailureReporter` - Different ways to handle failures (IDE diff tools, auto-approve, etc.)
- `ApprovalNamer` - Different naming strategies (stack trace, custom, master directory)
- `ApprovalWriter` - Different ways to write output (text, image, JSON, etc.)
- `Scrubber` - Different content normalization strategies

### 3. **Chain of Responsibility**
`FirstWorkingReporter` implements a chain of reporters, trying each until one successfully handles the failure.

### 4. **Factory Pattern**
`Options.createWriter()` acts as a factory for creating appropriate writers based on the input type.

### 5. **Template Method**
The core verification flow in `FileApprover` follows a template:
1. Write received file
2. Get approved file
3. Compare
4. Report if failure

## Component Responsibilities

| Component | Primary Responsibility |
|-----------|----------------------|
| **Approvals** | Entry point, orchestrates the approval process |
| **Options** | Configuration and customization |
| **FileApprover** | Core comparison logic |
| **ApprovalWriter** | Serialization of objects to files |
| **ApprovalNamer** | File naming and location strategy |
| **ApprovalFailureReporter** | Failure handling and user feedback |
| **Scrubber** | Content normalization before comparison |
| **Verifiable** | Self-verifying objects |
| **VerifyResult** | Outcome of verification |
| **FirstWorkingReporter** | Composite reporter pattern |