package org.impact2585.lib2585.tests;


import org.impact2585.lib2585.Toggler;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * Unit test for the Toggler class
 */
public class TogglerTest {
	private Toggler testToggler;
	
	/**
	 * Set up the unit test
	 */
	@Before
	public void setUp() {
		testToggler = new Toggler(false);
	}

	/**
	 * Performs the unit test for the toggler
	 */
	@Test
	public void test() {
		//tests if the toggleState is false by default
		Assert.assertTrue(testToggler.toggle(false) == false);
		
		//tests if the toggleState can be toggled to true
		Assert.assertTrue(testToggler.toggle(true) == true);
		
		//tests if the toggleState will not toggle if the toggle is held down
		Assert.assertTrue(testToggler.toggle(true) == true);
		
		//tests if the toggleState is still true
		Assert.assertTrue(testToggler.toggle(false) == true);
		
		//tests if the toggleState can be toggled back
		Assert.assertTrue(testToggler.toggle(true) == false);
		
		//tests if the toggleState stays the same again
		Assert.assertTrue(testToggler.toggle(false) == false);
	}

}
