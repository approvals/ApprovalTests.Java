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


 * Approvals. [verify ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L278-L295) (ApprovalApprover, $\color{#AAA}{\textsf{Options}}$)  <!-- include: DocumentHelpersTest.listAllVerifyFunctions.approved.md -->
 * Approvals. [verify ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L238-L241) (ApprovalWriter, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verify ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L228-L231) (ApprovalWriter, ApprovalNamer, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verify ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L302-L305) (ExecutableCommand, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verify ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L209-L212) (File, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verify ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L312-L315) (Map, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verify ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L65-L69) (Object, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verify ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L199-L202) (Path, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verify ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L322-L325) (ResultSet, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verify ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L332-L335) (SqlLoader, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verify ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L55-L58) (String, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verify ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L76-L80) (Verifiable, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verifyAll ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L125-L128) (Iterable, Function1, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verifyAll ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L130-L134) (Object[], Function1, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verifyAll ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L168-L171) (String, Iterable, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verifyAll ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L151-L156) (String, Iterable, Function1, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verifyAll ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L99-L102) (String, Object[], $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verifyAll ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L141-L144) (String, Object[], Function1, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verifyAll ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L178-L182) (String, String, Iterable, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verifyAll ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L109-L113) (String, String, Object[], $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verifyEachFileInDirectory ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L347-L350) (File, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verifyEachFileInDirectory ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L357-L360) (File, FileFilter, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verifyEachFileInDirectory ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L367-L370) (File, FilenameFilter, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verifyException ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L485-L491) (Action0, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verifyHtml ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L189-L192) (String, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verifyXml ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L248-L251) (String, $\color{#AAA}{\textsf{Options}}$)  
 * Approvals. [verifyXml ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/Approvals.java#L258-L262) (String, Function1, $\color{#AAA}{\textsf{Options}}$)  
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
 * CombinationApprovals. [verifyAllCombinations ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L50-L53) (Function1, Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyAllCombinations ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L87-L91) (Function2, Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyAllCombinations ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L125-L129) (Function3, Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyAllCombinations ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L164-L168) (Function4, Object[], Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyAllCombinations ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L205-L211) (Function5, Object[], Object[], Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyAllCombinations ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L249-L255) (Function6, Object[], Object[], Object[], Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyAllCombinations ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L294-L301) (Function7, Object[], Object[], Object[], Object[], Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyAllCombinations ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L347-L354) (Function8, Object[], Object[], Object[], Object[], Object[], Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyAllCombinations ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L397-L404) (Function9, Object[], Object[], Object[], Object[], Object[], Object[], Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyAllCombinations ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L58-L63) (Labels, Function1, Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyAllCombinations ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L96-L101) (Labels, Function2, Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyAllCombinations ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L134-L139) (Labels, Function3, Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyAllCombinations ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L173-L179) (Labels, Function4, Object[], Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyAllCombinations ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L216-L222) (Labels, Function5, Object[], Object[], Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyAllCombinations ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L260-L267) (Labels, Function6, Object[], Object[], Object[], Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyAllCombinations ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L306-L314) (Labels, Function7, Object[], Object[], Object[], Object[], Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyAllCombinations ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L360-L368) (Labels, Function8, Object[], Object[], Object[], Object[], Object[], Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyAllCombinations ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L409-L416) (Labels, Function9, Object[], Object[], Object[], Object[], Object[], Object[], Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyBestCoveringPairs ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L429-L433) (Function1, Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyBestCoveringPairs ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L455-L459) (Function2, Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyBestCoveringPairs ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L481-L485) (Function3, Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyBestCoveringPairs ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L508-L512) (Function4, Object[], Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyBestCoveringPairs ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L538-L544) (Function5, Object[], Object[], Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyBestCoveringPairs ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L571-L577) (Function6, Object[], Object[], Object[], Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyBestCoveringPairs ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L605-L612) (Function7, Object[], Object[], Object[], Object[], Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyBestCoveringPairs ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L644-L651) (Function8, Object[], Object[], Object[], Object[], Object[], Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyBestCoveringPairs ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L681-L688) (Function9, Object[], Object[], Object[], Object[], Object[], Object[], Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyBestCoveringPairs ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L435-L440) (Labels, Function1, Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyBestCoveringPairs ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L461-L466) (Labels, Function2, Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyBestCoveringPairs ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L487-L492) (Labels, Function3, Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyBestCoveringPairs ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L514-L520) (Labels, Function4, Object[], Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyBestCoveringPairs ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L546-L552) (Labels, Function5, Object[], Object[], Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyBestCoveringPairs ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L579-L586) (Labels, Function6, Object[], Object[], Object[], Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyBestCoveringPairs ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L614-L622) (Labels, Function7, Object[], Object[], Object[], Object[], Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyBestCoveringPairs ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L653-L661) (Labels, Function8, Object[], Object[], Object[], Object[], Object[], Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
 * CombinationApprovals. [verifyBestCoveringPairs ](https://github.com/approvals/ApprovalTests.Java/blob/master/approvaltests/src/main/java/org/approvaltests/combinations/CombinationApprovals.java#L690-L697) (Labels, Function9, Object[], Object[], Object[], Object[], Object[], Object[], Object[], Object[], Object[], $\color{#AAA}{\textsf{Options}}$)  
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
