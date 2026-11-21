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


 * Approvals. [verify ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L255-L272) (ApprovalApprover, $\color{#AAA}{\textsf{Options}}$)  <!-- include: DocumentHelpersTest.listAllVerifyFunctions.approved.md -->
 * Approvals. [verify ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L224-L227) (ApprovalWriter, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verify ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L214-L217) (ApprovalWriter, ApprovalNamer, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verify ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L279-L282) (ExecutableCommand, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verify ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L204-L207) (File, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verify ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L289-L292) (Map, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verify ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L60-L64) (Object, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verify ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L194-L197) (Path, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verify ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L50-L53) (String, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verify ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L71-L75) (Verifiable, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verifyAll ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L120-L123) (Iterable, Function1, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verifyAll ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L125-L129) (Object[], Function1, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verifyAll ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L163-L166) (String, Iterable, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verifyAll ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L146-L151) (String, Iterable, Function1, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verifyAll ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L94-L97) (String, Object[], $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verifyAll ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L136-L139) (String, Object[], Function1, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verifyAll ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L173-L177) (String, String, Iterable, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verifyAll ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L104-L108) (String, String, Object[], $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verifyEachFileInDirectory ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L309-L312) (File, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verifyEachFileInDirectory ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L319-L322) (File, FileFilter, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verifyEachFileInDirectory ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L329-L332) (File, FilenameFilter, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verifyException ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L373-L379) (Action0, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verifyHtml ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L184-L187) (String, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verifyXml ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L234-L237) (String, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verifyXml ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L244-L248) (String, Function1, $\color{#AAA}{\textsf{Options}}$)  
 * AwtApprovals. [verify ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/awt/AwtApprovals.java#L47-L50) (BufferedImage, $\color{#AAA}{\textsf{Options}}$)  
 * AwtApprovals. [verify ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/awt/AwtApprovals.java#L57-L60) (Component, $\color{#AAA}{\textsf{Options}}$)  
 * AwtApprovals. [verify ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/awt/AwtApprovals.java#L37-L40) (Image, $\color{#AAA}{\textsf{Options}}$)  
 * AwtApprovals. [verify ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/awt/AwtApprovals.java#L67-L70) (Paintable, $\color{#AAA}{\textsf{Options}}$)  
 * AwtApprovals. [verifySequence ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/awt/AwtApprovals.java#L128-L141) (Paintable, int, Duration, Function1, $\color{#AAA}{\textsf{Options}}$)  
 * AwtApprovals. [verifySequence ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/awt/AwtApprovals.java#L116-L120) (Paintable, int, Function1, $\color{#AAA}{\textsf{Options}}$)  
 * AwtApprovals. [verifySequence ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/awt/AwtApprovals.java#L101-L108) (int, Duration, Function1, $\color{#AAA}{\textsf{Options}}$)  
 * AwtApprovals. [verifySequence ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/awt/AwtApprovals.java#L89-L93) (int, Function1, $\color{#AAA}{\textsf{Options}}$)  
 * AwtApprovals. [verifySequenceWithTimings ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/awt/AwtApprovals.java#L83-L87) (int, Function1, $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyAllCombinations ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L48-L51) (Function1, Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyAllCombinations ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L106-L110) (Function2, Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyAllCombinations ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L165-L169) (Function3, Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyAllCombinations ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L225-L229) (Function4, Object[], Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyAllCombinations ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L287-L300) (Function5, Object[], Object[], Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyAllCombinations ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L366-L380) (Function6, Object[], Object[], Object[], Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyAllCombinations ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L447-L463) (Function7, Object[], Object[], Object[], Object[], Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyAllCombinations ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L537-L554) (Function8, Object[], Object[], Object[], Object[], Object[], Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyAllCombinations ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L628-L646) (Function9, Object[], Object[], Object[], Object[], Object[], Object[], Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyAllCombinations ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L56-L72) (Labels, Function1, Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyAllCombinations ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L115-L131) (Labels, Function2, Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyAllCombinations ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L174-L190) (Labels, Function3, Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyAllCombinations ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L234-L251) (Labels, Function4, Object[], Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyAllCombinations ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L305-L322) (Labels, Function5, Object[], Object[], Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyAllCombinations ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L385-L402) (Labels, Function6, Object[], Object[], Object[], Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyAllCombinations ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L468-L486) (Labels, Function7, Object[], Object[], Object[], Object[], Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyAllCombinations ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L560-L578) (Labels, Function8, Object[], Object[], Object[], Object[], Object[], Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyAllCombinations ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L651-L669) (Labels, Function9, Object[], Object[], Object[], Object[], Object[], Object[], Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyBestCoveringPairs ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L693-L697) (Function1, Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyBestCoveringPairs ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L741-L745) (Function2, Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyBestCoveringPairs ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L789-L793) (Function3, Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyBestCoveringPairs ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L838-L842) (Function4, Object[], Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyBestCoveringPairs ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L889-L902) (Function5, Object[], Object[], Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyBestCoveringPairs ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L957-L971) (Function6, Object[], Object[], Object[], Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyBestCoveringPairs ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L1027-L1043) (Function7, Object[], Object[], Object[], Object[], Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyBestCoveringPairs ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L1103-L1120) (Function8, Object[], Object[], Object[], Object[], Object[], Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyBestCoveringPairs ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L1181-L1199) (Function9, Object[], Object[], Object[], Object[], Object[], Object[], Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyBestCoveringPairs ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L699-L715) (Labels, Function1, Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyBestCoveringPairs ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L747-L763) (Labels, Function2, Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyBestCoveringPairs ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L795-L811) (Labels, Function3, Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyBestCoveringPairs ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L844-L861) (Labels, Function4, Object[], Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyBestCoveringPairs ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L904-L921) (Labels, Function5, Object[], Object[], Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyBestCoveringPairs ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L973-L990) (Labels, Function6, Object[], Object[], Object[], Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyBestCoveringPairs ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L1045-L1063) (Labels, Function7, Object[], Object[], Object[], Object[], Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyBestCoveringPairs ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L1122-L1140) (Labels, Function8, Object[], Object[], Object[], Object[], Object[], Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyBestCoveringPairs ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L1201-L1219) (Labels, Function9, Object[], Object[], Object[], Object[], Object[], Object[], Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
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
 * SqlApprovals. [verify ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/SqlApprovals.java#L32-L35) (ResultSet, $\color{#AAA}{\textsf{Options}}$)  
 * SqlApprovals. [verify ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/SqlApprovals.java#L22-L25) (SqlLoader, $\color{#AAA}{\textsf{Options}}$)  
 * VelocityApprovals. [verify ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/velocity/VelocityApprovals.java#L18-L27) (ContextAware, $\color{#AAA}{\textsf{Options}}$)  
 * XmlXomApprovals. [verifyXml ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/XmlXomApprovals.java#L18-L21) (String, $\color{#AAA}{\textsf{Options}}$)  <!-- endInclude -->

## Optional Options

The Java language does not allow optional parameters (like many other languages do), however the same effect can be achieved with method overloading.  
Every method is actually two methods. One with an Options parameter, and one without.  

The one without simply calls the other method with a `new Options()`.

To find out more about the [Options parameter, click here](Options.md)  

---

[Back to User Guide](../README.md#top)
