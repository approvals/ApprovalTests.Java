package org.approvaltests.namer.tests;

import static org.junit.Assert.assertEquals;

import org.approvaltests.namer.ApprovalNamer;
import org.approvaltests.namer.ApprovalNamerFactory;
import org.approvaltests.namer.ApprovalsNamer;
import org.approvaltests.namer.JUnitStackTraceNamer;
import org.approvaltests.namer.MavenResourcesApprovalNamer;
import org.junit.Test;

public class ApprovalNamerFactoryTest {

	@Test
	public void returns_JUnitStackTraceNamer_if_ApprovalsNamer_annotation_is_not_specified() {
		assertEquals(JUnitStackTraceNamer.class, new WithoutApprovalsNamerAnnotation().test().getClass());
	}

	@Test
	public void returns_ApprovalsNamer_instance_for_class_level_ApprovalsNamer_annotation() {
		assertEquals(MavenResourcesApprovalNamer.class, new WithApprovalsNamerClassLevelAnnotation().test().getClass());
	}
	
	@Test
	public void returns_ApprovalsNamer_instance_for_method_level_ApprovalsNamer_annotation() {
		assertEquals(JUnitStackTraceNamer.class, new WithApprovalsNamerMethodLevelAnnotation().test1().getClass());
		assertEquals(MavenResourcesApprovalNamer.class, new WithApprovalsNamerMethodLevelAnnotation().test2().getClass());
	}

	private static class WithoutApprovalsNamerAnnotation {
		public ApprovalNamer test() {
			return ApprovalNamerFactory.getApprovalNamer();
		}
	}

	@ApprovalsNamer(MavenResourcesApprovalNamer.class)
	private static class WithApprovalsNamerClassLevelAnnotation {
		public ApprovalNamer test() {
			return ApprovalNamerFactory.getApprovalNamer();
		}
	}

	@ApprovalsNamer(MavenResourcesApprovalNamer.class)
	private static class WithApprovalsNamerMethodLevelAnnotation {
		@ApprovalsNamer(JUnitStackTraceNamer.class)
		public ApprovalNamer test1() {
			return ApprovalNamerFactory.getApprovalNamer();
		}

		public ApprovalNamer test2() {
			return ApprovalNamerFactory.getApprovalNamer();
		}
	}
}
