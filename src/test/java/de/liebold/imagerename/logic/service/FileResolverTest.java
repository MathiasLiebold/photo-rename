package de.liebold.imagerename.logic.service;

import static org.junit.Assert.assertTrue;

import java.util.Collections;

import org.junit.Test;

public class FileResolverTest {

	@Test
	public void checkNoDuplicateAssignment() {
		assertTrue(Collections.disjoint(FileResolver.IMAGE_FILE_TYPES, FileResolver.RAW_IMAGE_FILE_TYPES));
	}

}
