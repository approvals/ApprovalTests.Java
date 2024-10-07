<a id="top"></a>

# Approvals.verify
<!-- toc -->
## Contents

  * [API](#api)
  * [Optional Options](#optional-options)<!-- endToc -->

## API
Approvals provides many convenience functions to verify different scenarios.  
Here is a list:

*(Note: All verify functions have an [optional "Options" parameter](#optional-options) as the last parameter.)*


 * Approvals. [verify ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L227-L243) (ApprovalApprover, $\color{#AAA}{\textsf{Options}}$)  <!-- include: DocumentHelpersTest.listAllVerifyFunctions.approved.md -->
 * Approvals. [verify ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L194-L197) (ApprovalWriter, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verify ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L186-L189) (ApprovalWriter, ApprovalNamer, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verify ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L248-L251) (ExecutableCommand, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verify ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L170-L173) (File, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verify ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L256-L259) (Map, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verify ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L61-L65) (Object, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verify ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L264-L267) (ResultSet, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verify ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L272-L275) (SqlLoader, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verify ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L53-L56) (String, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verify ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L70-L74) (Verifiable, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verifyAll ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L111-L114) (Iterable, Function1, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verifyAll ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L115-L119) (Object[], Function1, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verifyAll ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L145-L148) (String, Iterable, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verifyAll ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L132-L136) (String, Iterable, Function1, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verifyAll ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L90-L93) (String, Object[], $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verifyAll ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L124-L127) (String, Object[], Function1, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verifyAll ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L153-L157) (String, String, Iterable, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verifyAll ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L98-L102) (String, String, Object[], $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verifyEachFileInDirectory ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L284-L287) (File, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verifyEachFileInDirectory ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L292-L295) (File, FileFilter, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verifyEachFileInDirectory ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L300-L303) (File, FilenameFilter, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verifyException ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L405-L411) (Action0, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verifyHtml ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L162-L165) (String, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verifyXml ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L202-L205) (String, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verifyXml ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L210-L214) (String, Function1, $\color{#AAA}{\textsf{Options}}$)  
 * AwtApprovals. [verify ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/awt/AwtApprovals.java#L44-L47) (BufferedImage, $\color{#AAA}{\textsf{Options}}$)  
 * AwtApprovals. [verify ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/awt/AwtApprovals.java#L52-L55) (Component, $\color{#AAA}{\textsf{Options}}$)  
 * AwtApprovals. [verify ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/awt/AwtApprovals.java#L36-L39) (Image, $\color{#AAA}{\textsf{Options}}$)  
 * AwtApprovals. [verify ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/awt/AwtApprovals.java#L60-L63) (Paintable, $\color{#AAA}{\textsf{Options}}$)  
 * AwtApprovals. [verifySequence ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/awt/AwtApprovals.java#L111-L124) (Paintable, int, Duration, Function1, $\color{#AAA}{\textsf{Options}}$)  
 * AwtApprovals. [verifySequence ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/awt/AwtApprovals.java#L101-L105) (Paintable, int, Function1, $\color{#AAA}{\textsf{Options}}$)  
 * AwtApprovals. [verifySequence ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/awt/AwtApprovals.java#L88-L95) (int, Duration, Function1, $\color{#AAA}{\textsf{Options}}$)  
 * AwtApprovals. [verifySequence ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/awt/AwtApprovals.java#L78-L82) (int, Function1, $\color{#AAA}{\textsf{Options}}$)  
 * AwtApprovals. [verifySequenceWithTimings ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/awt/AwtApprovals.java#L73-L77) (int, Function1, $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyAllCombinations ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L23-L27) ($\color{#AAA}{\textsf{Options}}$, Object, String, Object[][])  
 * CombinationApprovals. [verifyAllCombinations ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L39-L43) (Function1, Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyAllCombinations ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L56-L61) (Function2, Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyAllCombinations ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L74-L79) (Function3, Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyAllCombinations ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L92-L97) (Function4, Object[], Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyAllCombinations ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L111-L117) (Function5, Object[], Object[], Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyAllCombinations ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L131-L137) (Function6, Object[], Object[], Object[], Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyAllCombinations ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L151-L159) (Function7, Object[], Object[], Object[], Object[], Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyAllCombinations ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L177-L185) (Function8, Object[], Object[], Object[], Object[], Object[], Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyAllCombinations ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L200-L207) (Function9, Object[], Object[], Object[], Object[], Object[], Object[], Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyBestCoveringPairs ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L213-L218) (Function1, Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyBestCoveringPairs ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L225-L230) (Function2, Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyBestCoveringPairs ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L237-L242) (Function3, Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyBestCoveringPairs ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L249-L254) (Function4, Object[], Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyBestCoveringPairs ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L262-L268) (Function5, Object[], Object[], Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyBestCoveringPairs ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L276-L282) (Function6, Object[], Object[], Object[], Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyBestCoveringPairs ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L291-L299) (Function7, Object[], Object[], Object[], Object[], Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyBestCoveringPairs ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L309-L317) (Function8, Object[], Object[], Object[], Object[], Object[], Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyBestCoveringPairs ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L326-L333) (Function9, Object[], Object[], Object[], Object[], Object[], Object[], Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * JsonApprovals. [verifyAsJson ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/JsonApprovals.java#L54-L57) (Object, $\color{#AAA}{\textsf{Options}}$)  
 * JsonApprovals. [verifyAsJson ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/JsonApprovals.java#L49-L52) (Object, Function1, $\color{#AAA}{\textsf{Options}}$)  
 * JsonApprovals. [verifyJson ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/JsonApprovals.java#L17-L20) (String, $\color{#AAA}{\textsf{Options}}$)  
 * JsonApprovals. [verifyJson ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/JsonApprovals.java#L37-L40) (String, boolean, $\color{#AAA}{\textsf{Options}}$)  
 * JsonApprovals. [verifyJson ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/JsonApprovals.java#L29-L36) (String, boolean, Function1, $\color{#AAA}{\textsf{Options}}$)  
 * JsonJacksonApprovals. [verifyAsJson ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/JsonJacksonApprovals.java#L17-L20) (Object, $\color{#AAA}{\textsf{Options}}$)  
 * JsonJacksonApprovals. [verifyAsJson ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/JsonJacksonApprovals.java#L25-L29) (Object, Function1, $\color{#AAA}{\textsf{Options}}$)  
 * JsonXstreamApprovals. [verifyAsJson ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/JsonXstreamApprovals.java#L18-L21) (Object, $\color{#AAA}{\textsf{Options}}$)  
 * VelocityApprovals. [verify ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/velocity/VelocityApprovals.java#L16-L24) (ContextAware, $\color{#AAA}{\textsf{Options}}$)  
 * XmlXomApprovals. [verifyXml ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/XmlXomApprovals.java#L17-L20) (String, $\color{#AAA}{\textsf{Options}}$)  <!-- endInclude -->

## Optional Options

The Java language does not allow optional parameters (like many other languages do), however the same effect can be achieved with method overloading.  
Every method is actually two methods. One with an Options parameter, and one without.  

The one without simply calls the other method with a `new Options()`.

To find out more about the [Options parameter, click here](Options.md)  

---

[Back to User Guide](../README.md#top)
