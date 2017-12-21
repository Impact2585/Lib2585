package org.impact2585.lib2585.tests;

import org.impact2585.lib2585.RampedSpeedController;
import org.impact2585.lib2585.testing.FakeSpeedController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * Unit tests for the ramped speed controller class
 */
public class RampedSpeedControllerTest {
	RampedSpeedController controller;

	/**
	 * Sets up before each test by creating a new RampedSpeedController
	 */
	@Before
	public void setUp() {
		controller = new RampedSpeedController(new FakeSpeedController());
	}

	/**
	 * Tests that the speed starts as 0
	 */
	@Test
	public void defaultsToZero() {
		Assert.assertTrue(controller.getCurrentSpeed() == 0);
	}
	
	/**
	 * Tests that the ramp is appropriately applied one time when a positive input is given
	 */
	@Test
	public void rampsOncePositive() {
		controller.updateWithSpeed(1);
		Assert.assertTrue(controller.getCurrentSpeed() == RampedSpeedController.RAMP);
	}
	
	/**
	 * Tests that the ramp is appropriately applied one time when a negative input is given
	 */
	@Test
	public void rampsOnceNegative() {
		controller.updateWithSpeed(-1);
		Assert.assertTrue(controller.getCurrentSpeed() == -RampedSpeedController.RAMP);
	}
	
	/**
	 * Tests that the speed continues to increase when positive inputs are given multiple times
	 */
	@Test
	public void rampsMultiplePositive() {
		controller.updateWithSpeed(1);
		controller.updateWithSpeed(1);
		Assert.assertTrue(controller.getCurrentSpeed() > RampedSpeedController.RAMP &&
						  controller.getCurrentSpeed() < 1);
	}
	
	/**
	 * Tests that the speed continues to decrease when negative inputs are given multiple times
	 */
	@Test
	public void rampsMultipleNegative() {
		controller.updateWithSpeed(-1);
		controller.updateWithSpeed(-1);
		Assert.assertTrue(controller.getCurrentSpeed() < -RampedSpeedController.RAMP &&
						  controller.getCurrentSpeed() > -1);
	}
	
	/**
	 * Tests that a positive input speed is eventually reached rather than being approached asymptotically
	 */
	@Test
	public void reachesMaxPositive() {
		for (int i=0; i<15; i++) {
			controller.updateWithSpeed(1);
		}

		Assert.assertTrue(controller.getCurrentSpeed() == 1);
	}
	
	/**
	 * Tests that a negative input speed is eventually reached rather than being approached asymptotically
	 */
	@Test
	public void reachesMaxNegative() {
		for (int i=0; i<15; i++) {
			controller.updateWithSpeed(-1);
		}

		Assert.assertTrue(controller.getCurrentSpeed() == -1);
	}
	
	/**
	 * Tests that the speed eventually reaches a fractional value after multiple updates
	 */
	@Test
	public void reachesFractionalValue() {
		for (int i=0; i<15; i++) {
			controller.updateWithSpeed(0.25);
		}

		Assert.assertTrue(controller.getCurrentSpeed() == 0.25);
	}
	
	/**
	 * Tests that the output speed immediately drops to 0 when 0 is given as an input
	 */
	@Test
	public void returnsToZeroImmediately() {
		controller.updateWithSpeed(1);
		controller.updateWithSpeed(1);
		controller.updateWithSpeed(0);
		
		Assert.assertTrue(controller.getCurrentSpeed() == 0);
		
	}

}
