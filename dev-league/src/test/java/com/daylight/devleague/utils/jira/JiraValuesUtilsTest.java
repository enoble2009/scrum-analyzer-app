package com.daylight.devleague.utils.jira;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.jupiter.api.Test;

public class JiraValuesUtilsTest {

	@Test
	public void should_instantiate_when_create_object() {
		JiraValuesUtils utils = new JiraValuesUtils();
		assertNotNull(utils);
	}
	
	@Test
	public void should_return_empty_string_when_text_is_null() {
		String text = null;
		
		String responseText = JiraValuesUtils.cutoff(text, 0);
		
		assertEquals("", responseText);
	}

	@Test
	public void should_return_same_text_when_maxlength_is_longer_than_text_length() {
		String text = "Sample Text";
		int maxLength = text.length() + 1;
		
		String responseText = JiraValuesUtils.cutoff(text, maxLength);
		
		assertEquals(text, responseText);
	}

	@Test
	public void should_return_cut_text_when_maxlength_is_shorter_than_text_length() {
		String text = "Sample Text";
		int maxLength = text.length() - 1;
		
		String responseText = JiraValuesUtils.cutoff(text, maxLength);
		
		assertEquals(text.substring(0, maxLength), responseText);
	}

	@Test
	public void should_return_same_text_when_maxlength_is_equals_than_text_length() {
		String text = "Sample Text";
		int maxLength = text.length();
		
		String responseText = JiraValuesUtils.cutoff(text, maxLength);
		
		assertEquals(text, responseText);
	}
	
}
