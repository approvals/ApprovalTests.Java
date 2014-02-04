package org.approvaltests.namer.tests;
import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.approvaltests.namer.JUnitStackTraceNamer;
import org.approvaltests.namer.MavenResourcesApprovalNamer;
import org.junit.Test;

public class MavenResourcesApprovalNamerTest {
	@Test
	public void testGetApprovalsBasePath() {
		JUnitStackTraceNamer namer = mock(JUnitStackTraceNamer.class);
		when(namer.getApprovalFileBasePath()).thenReturn("C:\\maven_project\\src\\test\\java\\some\\packet\\");
		assertEquals("C:\\maven_project\\src\\test\\resources\\some\\packet\\", new MavenResourcesApprovalNamer(namer).getApprovalFileBasePath());
	}
}
