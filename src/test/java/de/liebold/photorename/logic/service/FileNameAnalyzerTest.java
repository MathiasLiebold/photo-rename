package de.liebold.photorename.logic.service;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/applicationContext.xml" })
public class FileNameAnalyzerTest {

	@Autowired
	private FileNameAnalyzer fileNameAnalyzer;

	@Test(expected = NullPointerException.class)
	public void getDescriptionInBrackets_NullInput_ThrowsException() {
		fileNameAnalyzer.getDescriptionInBrackets(null);
	}

	@Test
	public void getDescriptionInBrackets_EmptyInput_ReturnsBlank() {
		assertEquals("", fileNameAnalyzer.getDescriptionInBrackets(" "));
	}

	@Test
	public void getDescriptionInBrackets_NoBrackets_ReturnsBlank() {
		assertEquals("", fileNameAnalyzer.getDescriptionInBrackets("AnyPicture.jpg"));
	}

	@Test
	public void getDescriptionInBrackets_NormalBrackets_ReturnsBracketContent() {
		assertEquals("content to return",
				fileNameAnalyzer.getDescriptionInBrackets("AnyPicture(content to return).jpg"));
	}

	@Test
	public void getDescriptionInBrackets_DoubleBrackets_ReturnsLast() {
		assertEquals("1)(2", fileNameAnalyzer.getDescriptionInBrackets("AnyPicture(1)(2).jpg"));
	}

	@Test
	public void getDescriptionInBrackets_NestedBrackets_ReturnsOuter() {
		assertEquals("1(2)", fileNameAnalyzer.getDescriptionInBrackets("AnyPicture(1(2)).jpg"));
	}

	@Test
	public void getDescriptionInBrackets_MissingBracketsBefore_ReturnsInner() {
		assertEquals("1 2)", fileNameAnalyzer.getDescriptionInBrackets("AnyPicture(1 2)).jpg"));
	}

	@Test
	public void getDescriptionInBrackets_MissingBracketsAfter_ReturnsInner() {
		assertEquals("(1 2", fileNameAnalyzer.getDescriptionInBrackets("AnyPicture((1 2).jpg"));
	}

	@Test
	public void getDescriptionInBrackets_ContainsDotBefore_ReturnsBracketContent() {
		assertEquals("1 2", fileNameAnalyzer.getDescriptionInBrackets("AnyPic.ture(1 2).jpg"));
	}

	@Test
	public void getDescriptionInBrackets_ContainsDotInBrackets_ReturnsBracketContent() {
		assertEquals("1. 2", fileNameAnalyzer.getDescriptionInBrackets("AnyPicture(1. 2).jpg"));
	}

	@Test
	public void getDescriptionInBrackets_ContainsNoContentInBrackets_ReturnsBlank() {
		assertEquals("", fileNameAnalyzer.getDescriptionInBrackets("AnyPicture().jpg"));
	}

	@Test
	public void getLowerCaseFileSuffix_StandardName_Works() {
		assertEquals("jpg", fileNameAnalyzer.getLowerCaseFileSuffix("AnyPicture.JPg"));
	}

	@Test
	public void getLowerCaseFileSuffix_NameContainsDots_Works() {
		assertEquals("jpg", fileNameAnalyzer.getLowerCaseFileSuffix("Any.Picture.JPg"));
	}

}
