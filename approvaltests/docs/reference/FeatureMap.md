<a id="top"></a>

# Feature Map

What you can do with ApprovalTests.Java, one row per feature.

- **Generic**: features that also exist in other ApprovalTests languages.
- **Java**: features that only make sense on the JVM, or the Java way of doing a generic idea.

## Generic

| Area | Feature | Java |
|---|---|---|
| Verify | Verify a string or object | [`Approvals.verify`](Verify.md#top) |
| Verify | Verify each item in a list | [`Approvals.verifyAll`](../how_to/TestAVarietyOfValues.md#top) |
| Verify | Verify a map | `Approvals.verify(Map)` |
| Verify | Verify an exception's message | `Approvals.verifyException` |
| Verify | Verify a file | `Approvals.verify(File)` |
| Verify | Verify every file in a directory | [`Approvals.verifyEachFileInDirectory`](../how_to/VerifyMultipleFiles.md#top) |
| Verify | Verify JSON | `JsonApprovals.verifyJson` |
| Verify | Verify an object as JSON | [`JsonApprovals.verifyAsJson`](../how_to/ShowNullsInJson.md#top) |
| Verify | Verify XML | `Approvals.verifyXml` |
| Verify | Verify HTML | `Approvals.verifyHtml` |
| Verify | Verify images | [`AwtApprovals.verify`](AwtApprovals.md#top) |
| Verify | Verify a markdown table of inputs and outputs | [`VerifiableMarkdownTable`](../how_to/TestAVarietyOfValues.md#tables) |
| Verify | Verify console output | `ConsoleOutput` |
| Verify | Verify a log | [`SimpleLoggerApprovals.verify`](../../../approvaltests-util/docs/reference/SimpleLogger.md#top) |
| Verify | Verify a sequence of states (storyboard) | [`StoryBoard`, `MarkdownStoryBoard`](StoryBoard.md#top) |
| Verify | Verify a query and its result | [`Approvals.verify(ExecutableCommand)`](../how_to/PatternsForTestingDataAccessAndRendering.md#top) |
| Verify | Make your own objects verifiable | [`Verifiable`](../how_to/CreateCustomizedVerifyMethods.md#top) |
| Verify | Write your own verify methods | [✅](../how_to/CreateCustomizedVerifyMethods.md#top) |
| Combinations | Verify all combinations of inputs | [`CombinationApprovals.verifyAllCombinations`](CombinationApprovals.md#top) |
| Combinations | Verify pairwise combinations | [`CombinationApprovals.verifyBestCoveringPairs`](../Features.md#940) |
| Combinations | Add headers and labels to combinations | `Labels` |
| Inputs | Parse test inputs from the approved text | `ParseInput` |
| Inline | Keep the approved text inside the test source | [`Options.inline`](../how_to/InlineApprovals.md#top) |
| Inline | Choose how inline results are approved | [`InlineOptions`](../explanations/InlineApprovalsWorkflow.md#top) |
| Options | Configure a single verify call | [`Options`](Options.md#top) |
| Options | Change the file extension | [`Options.forFile().withExtension`](Options.md#file-extensions) |
| Options | Use a custom comparator | [`Options.withComparator`](Options.md#custom-comparators) |
| Options | Use a custom writer | `Options.withWriter` |
| Scrubbers | Remove changing data before comparing | [`Options.withScrubber`](../Scrubbers.md#top) |
| Scrubbers | Scrub GUIDs | [`Scrubbers.scrubGuid`, `GuidScrubber`](../Scrubbers.md#guid-scrubbing) |
| Scrubbers | Scrub dates | [`DateScrubber`](../Scrubbers.md#date-scrubbing) |
| Scrubbers | Scrub with a regular expression | [`RegExScrubber`](../Scrubbers.md#regex-scrubbing) |
| Scrubbers | Combine scrubbers | [`Scrubbers.scrubAll`](../Scrubbers.md#scrubbing-multiple-parts-of-a-string) |
| Scrubbers | Normalize whitespace | `NormalizeSpacesScrubber` |
| Naming | Name files after the test | [✅](Naming.md#top) |
| Naming | Add extra information to the file name | [`Options.forFile().withAdditionalInformation`](Naming.md#additional-naming-parameters) |
| Naming | Name files for parameterized tests | [`Approvals.NAMES.withParameters`](../how_to/ParameterizedTest.md#top) |
| Naming | Separate approved files per OS | [`Approvals.NAMES.asOsSpecificTest`](../how_to/UpdateFromNamerFactoryToOptions.md#os-specific-tests) |
| Naming | Separate approved files per machine | [`Approvals.NAMES.asMachineNameSpecificTest`](../how_to/MachineNameSpecificTest.md#top) |
| Naming | Several approved files in one test | [`Approvals.NAMES.useMultipleFiles`](../how_to/UpdateFromNamerFactoryToOptions.md#multiple-files-in-one-test) |
| Naming | Name files with a template | `TemplatedCustomNamer` |
| Naming | Use a custom namer | [`Options.forFile().withNamer`](Options.md#custom-namer) |
| Naming | Put approved files in a subdirectory | [`ApprovalTestPackageSettings.USE_APPROVAL_SUBDIRECTORY`](../Features.md#using-subdirectories-for-approval-output-files) |
| Naming | Put approved files in another base directory | [`ApprovalTestPackageSettings.APPROVAL_BASE_DIRECTORY`](../Configuration.md#alternative-base-directory-for-output-files) |
| Reporters | Open a diff tool on failure | [`DiffReporter`](Reporters.md#top) |
| Reporters | Use a specific diff tool | [`ReportWithBeyondCompare`, `ReportWithKdiff3`, ...](Reporters.md#supported-diff-tools) |
| Reporters | Combine reporters | [`MultiReporter`, `FirstWorkingReporter`](Reporters.md#combining-reporters) |
| Reporters | Copy the approve command to the clipboard | `ClipboardReporter` |
| Reporters | Report nothing | `QuietReporter` |
| Reporters | Auto approve | `AutoApproveReporter` |
| Reporters | Auto approve when there is no approved file | `AutoApproveWhenEmptyReporter` |
| Reporters | Capture received files from CI | [`FileCaptureReporter`](../how_to/CaptureFilesFromCI.md#top) |
| Reporters | Choose the reporter with an environment variable | [`APPROVAL_TESTS_USE_REPORTER`](../how_to/ConfigureReporters.md#method-4-environment-variables) |
| Reporters | Report in a way AI coding agents understand | [`AiCodingAgentReporter`](AiCodingAgentReporter.md#top) |
| Reporters | Override reporters on CI and build servers | [`DefaultFrontLoadedReporter`](FrontLoadedReporter.md#top) |
| Reporters | Write your own reporter | [`ApprovalFailureReporter`](../how_to/CreateCustomReporter.md#top) |
| Reporters | Report in the IDE | [`IntelliJReporter`](IntelliJReporter.md#top) |
| Configuration | Set defaults for a whole package | [`PackageSettings`](PackageSettings.md#top) |
| Configuration | Configure the project directory | [`APPROVALTESTS_PROJECT_DIRECTORY`](../how_to/ConfigureProjectDirectory.md#top) |
| Configuration | Disable approval script downloads | [`APPROVALTESTS_DISABLE_SCRIPT_DOWNLOADS`](../how_to/DisableScriptDownloads.md#top) |
| Safety | Fail when a test calls verify twice | `Approvals.settings().allowMultipleVerifyCallsForThisMethod` |
| Utilities | Run tests in a fixed time zone | [`WithTimeZone`](../how_to/ConsistentTimeZones.md#top) |
| Utilities | Customize how objects print | [`Printable`](../how_to/PrintableWrappers.md#top) |
| Utilities | Test, commit, or revert (TCR) | [`TestCommitRevertTest`](../Features.md#test-commit-revert-tcr) |

## Java

| Area | Feature | Java |
|---|---|---|
| Verify | Verify Swing and AWT components | [`AwtApprovals.verify(Component)`](AwtApprovals.md#top) |
| Verify | Verify an animated GIF of frames | [`AwtApprovals.verifySequence`](AwtApprovals.md#create-an-animated-gif) |
| Verify | Verify JSON with Gson | `JsonApprovals` |
| Verify | Verify JSON with Jackson 2 | `JsonJacksonApprovals` |
| Verify | Verify JSON with Jackson 3 | `JsonJackson3Approvals` |
| Verify | Verify JSON with XStream | `JsonXstreamApprovals` |
| Verify | Verify XML with XOM | `XmlXomApprovals` |
| Verify | Verify a JDBC `ResultSet` | `SqlApprovals.verify` |
| Verify | Verify a Velocity template | `VelocityApprovals` |
| Inline | Inline approvals in Kotlin | `InlineKotlinReporter` |
| Test frameworks | Works with JUnit 3, 4, 5, and TestNG | ✅ |
| Test frameworks | Dynamic tests with `@TestFactory` | [`JupiterApprovals.dynamicTest`](../how_to/UseTestFactory.md#top) |
| Configuration | Set reporters with annotations | [`@UseReporter`](../how_to/ConfigureReporters.md#method-2-class-and-method-level-annotations) |
| Configuration | Help find the source directory | [`TestUtils.registerSourceDirectoryFinder`](../Configuration.md#alternative-source-path-discovery) |
| Design | No checked exceptions | [✅](../explanations/NoCheckedExceptions.md#top) |

---

[Back to User Guide](../README.md#top)
