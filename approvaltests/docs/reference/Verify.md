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


 * Approvals. [verify ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L266-L283) (ApprovalApprover, $\color{#AAA}{\textsf{Options}}$)  <!-- include: DocumentHelpersTest.listAllVerifyFunctions.approved.md -->
 * Approvals. [verify ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L226-L229) (ApprovalWriter, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verify ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L216-L219) (ApprovalWriter, ApprovalNamer, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verify ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L290-L293) (ExecutableCommand, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verify ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L197-L200) (File, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verify ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L300-L303) (Map, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verify ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L64-L68) (Object, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verify ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L310-L313) (ResultSet, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verify ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L320-L323) (SqlLoader, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verify ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L54-L57) (String, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verify ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L75-L79) (Verifiable, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verifyAll ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L124-L127) (Iterable, Function1, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verifyAll ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L129-L133) (Object[], Function1, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verifyAll ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L166-L169) (String, Iterable, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verifyAll ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L150-L154) (String, Iterable, Function1, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verifyAll ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L98-L101) (String, Object[], $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verifyAll ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L140-L143) (String, Object[], Function1, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verifyAll ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L176-L180) (String, String, Iterable, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verifyAll ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L108-L112) (String, String, Object[], $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verifyEachFileInDirectory ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L335-L338) (File, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verifyEachFileInDirectory ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L345-L348) (File, FileFilter, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verifyEachFileInDirectory ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L355-L358) (File, FilenameFilter, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verifyException ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L473-L479) (Action0, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verifyHtml ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L187-L190) (String, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verifyXml ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L236-L239) (String, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verifyXml ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L246-L250) (String, Function1, $\color{#AAA}{\textsf{Options}}$)  
 * AwtApprovals. [verify ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/awt/AwtApprovals.java#L47-L50) (BufferedImage, $\color{#AAA}{\textsf{Options}}$)  
 * AwtApprovals. [verify ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/awt/AwtApprovals.java#L57-L60) (Component, $\color{#AAA}{\textsf{Options}}$)  
 * AwtApprovals. [verify ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/awt/AwtApprovals.java#L37-L40) (Image, $\color{#AAA}{\textsf{Options}}$)  
 * AwtApprovals. [verify ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/awt/AwtApprovals.java#L67-L70) (Paintable, $\color{#AAA}{\textsf{Options}}$)  
 * AwtApprovals. [verifySequence ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/awt/AwtApprovals.java#L128-L141) (Paintable, int, Duration, Function1, $\color{#AAA}{\textsf{Options}}$)  
 * AwtApprovals. [verifySequence ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/awt/AwtApprovals.java#L116-L120) (Paintable, int, Function1, $\color{#AAA}{\textsf{Options}}$)  
 * AwtApprovals. [verifySequence ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/awt/AwtApprovals.java#L101-L108) (int, Duration, Function1, $\color{#AAA}{\textsf{Options}}$)  
 * AwtApprovals. [verifySequence ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/awt/AwtApprovals.java#L89-L93) (int, Function1, $\color{#AAA}{\textsf{Options}}$)  
 * AwtApprovals. [verifySequenceWithTimings ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/awt/AwtApprovals.java#L83-L87) (int, Function1, $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyAllCombinations ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L24-L28) ($\color{#AAA}{\textsf{Options}}$, Object, String, Object[][])  
 * CombinationApprovals. [verifyAllCombinations ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L42-L46) (Function1, Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyAllCombinations ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L61-L66) (Function2, Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyAllCombinations ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L81-L86) (Function3, Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyAllCombinations ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L101-L106) (Function4, Object[], Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyAllCombinations ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L122-L128) (Function5, Object[], Object[], Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyAllCombinations ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L144-L150) (Function6, Object[], Object[], Object[], Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyAllCombinations ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L166-L174) (Function7, Object[], Object[], Object[], Object[], Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyAllCombinations ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L194-L202) (Function8, Object[], Object[], Object[], Object[], Object[], Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyAllCombinations ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L219-L226) (Function9, Object[], Object[], Object[], Object[], Object[], Object[], Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyBestCoveringPairs ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L234-L239) (Function1, Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyBestCoveringPairs ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L248-L253) (Function2, Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyBestCoveringPairs ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L262-L267) (Function3, Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyBestCoveringPairs ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L276-L281) (Function4, Object[], Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyBestCoveringPairs ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L291-L297) (Function5, Object[], Object[], Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyBestCoveringPairs ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L307-L313) (Function6, Object[], Object[], Object[], Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyBestCoveringPairs ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L324-L332) (Function7, Object[], Object[], Object[], Object[], Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyBestCoveringPairs ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L344-L352) (Function8, Object[], Object[], Object[], Object[], Object[], Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyBestCoveringPairs ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L363-L370) (Function9, Object[], Object[], Object[], Object[], Object[], Object[], Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * JsonApprovals. [verifyAsJson ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/JsonApprovals.java#L63-L66) (Object, $\color{#AAA}{\textsf{Options}}$)  
 * JsonApprovals. [verifyAsJson ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/JsonApprovals.java#L57-L60) (Object, Function1, $\color{#AAA}{\textsf{Options}}$)  
 * JsonApprovals. [verifyJson ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/JsonApprovals.java#L18-L21) (String, $\color{#AAA}{\textsf{Options}}$)  
 * JsonApprovals. [verifyJson ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/JsonApprovals.java#L42-L45) (String, boolean, $\color{#AAA}{\textsf{Options}}$)  
 * JsonApprovals. [verifyJson ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/JsonApprovals.java#L33-L40) (String, boolean, Function1, $\color{#AAA}{\textsf{Options}}$)  
 * JsonJackson3Approvals. [verifyAsJson ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/JsonJackson3Approvals.java#L24-L27) (Object, $\color{#AAA}{\textsf{Options}}$)  
 * JsonJackson3Approvals. [verifyAsJson ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/JsonJackson3Approvals.java#L34-L38) (Object, Function1, $\color{#AAA}{\textsf{Options}}$)  
 * JsonJacksonApprovals. [verifyAsJson ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/JsonJacksonApprovals.java#L18-L21) (Object, $\color{#AAA}{\textsf{Options}}$)  
 * JsonJacksonApprovals. [verifyAsJson ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/JsonJacksonApprovals.java#L28-L32) (Object, Function1, $\color{#AAA}{\textsf{Options}}$)  
 * JsonXstreamApprovals. [verifyAsJson ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/JsonXstreamApprovals.java#L19-L22) (Object, $\color{#AAA}{\textsf{Options}}$)  
 * VelocityApprovals. [verify ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/velocity/VelocityApprovals.java#L17-L25) (ContextAware, $\color{#AAA}{\textsf{Options}}$)  
 * XmlXomApprovals. [verifyXml ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/XmlXomApprovals.java#L18-L21) (String, $\color{#AAA}{\textsf{Options}}$)  <!-- endInclude -->

## Optional Options

The Java language does not allow optional parameters (like many other languages do), however the same effect can be achieved with method overloading.  
Every method is actually two methods. One with an Options parameter, and one without.  

The one without simply calls the other method with a `new Options()`.

To find out more about the [Options parameter, click here](Options.md)  

---

[Back to User Guide](../README.md#top)
