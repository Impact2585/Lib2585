package org.impact2585.lib2585.tests;


import org.impact2585.lib2585.testing.FakeSpeedController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 *Class for testing the FakeSpeedController
 */
public class FakeSpeedControllerTest {

	private FakeSpeedController controller;
	private double speed;
	private boolean invert;
	/**
	 * Set up the FakeSpeedControllerTest
	 */
	@Before
	public void setUp() {
		controller = new FakeSpeedController();
		invert = false;
		speed = 0;
	}

	
	/**
	 *Test the FakeSpeedController 
	 */
	@Test
	public void test() {
		//test setting the speed
		speed = 0.3;
		controller.set(speed);
		Assert.assertTrue(controller.get() == speed);
		
		//test inversion
		invert = true;
		controller.setInverted(invert);
		//for verbosity
		Assert.assertTrue(controller.getInverted() == true);
		
		//test inversion
		invert = false;
		controller.setInverted(invert);
		Assert.assertTrue(controller.getInverted() == false);
		
		//test negative speed
		speed = -0.2;
		controller.set(speed);
		Assert.assertTrue(controller.get() == speed);
		
		//test PIDController
		speed = 1.0;
		controller.pidWrite(speed);
		Assert.assertTrue(controller.getPidOutput() == speed);
		
		//test disabling the motor
		controller.disable();
		Assert.assertTrue(controller.get() == 0);
		
		//test stopping the motor
		controller.set(speed);
		controller.stopMotor();
		Assert.assertTrue(controller.get() == 0);
	}

}
