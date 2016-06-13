package org.impact2585.lib2585.tests;

import org.impact2585.lib2585.Drivetrain;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.wpi.first.wpilibj.RobotDrive;

/**
 * test for the wheel system
 */
public class DrivetrainTest {
	private TestDrivetrain drivetrain;
	private double driveForward;
	private double currentRampForward;
	private double rotate;
	private boolean invert;
	private boolean toggleRotationExponent;
	public static final double INPUT_DEADZONE = 0.15;
	public static final double RAMP = 0.6;
	public static final double PRIMARY_ROTATION_EXPONENT = 1;
	public static final double SECONDARY_ROTATION_EXPONENT = 0.5;

	/**
	 * method to calculate the ramping
	 * @return correct ramping
	 */
	public double rampForward() {
		double realRampForward = (currentRampForward - driveForward) * drivetrain.getRamp()+ currentRampForward;
		if (driveForward < drivetrain.getDeadzone() && driveForward > -drivetrain.getDeadzone())
			realRampForward = 0;
		else if(Math.abs(currentRampForward - driveForward) < 0.01)
			realRampForward = driveForward;
		return -realRampForward;
	}

	/**
	 * Initializes the test wheel system and input
	 */
	@Before
	public void setUp() {
		drivetrain = new TestDrivetrain(INPUT_DEADZONE, RAMP, PRIMARY_ROTATION_EXPONENT, SECONDARY_ROTATION_EXPONENT, null);
		currentRampForward = 0;
		driveForward = 0;
		rotate = 0;
		invert = false;
		toggleRotationExponent = false;
	}

	/**
	 * Tests initial state, deadzone, rotating, and driving forward
	 */
	@Test
	public void test() {

		// tests if the robot isn't moving at the start
		double ramp = rampForward();
		drivetrain.arcadeControl(driveForward, rotate, invert, toggleRotationExponent);
		Assert.assertTrue(currentRampForward == 0 && rotate == 0);

		// tests deadzone
		driveForward = 0.14;
		rotate = 0.14;
		ramp = rampForward();
		drivetrain.arcadeControl(driveForward, rotate, invert, toggleRotationExponent);
		Assert.assertTrue(currentRampForward == 0 && rotate == 0);
		
		// tests sensitivity
		rotate = .19;
		ramp = rampForward();
		drivetrain.arcadeControl(driveForward, rotate, invert, toggleRotationExponent);
		Assert.assertTrue(rotate == Math.pow(0.19, drivetrain.getPrimaryRotationExponent()) && currentRampForward == 0);

		// tests forward driving
		rotate = .14;
		driveForward = -0.5;
		ramp = rampForward();
		drivetrain.arcadeControl(driveForward, rotate, invert, toggleRotationExponent);

		Assert.assertTrue(ramp == currentRampForward && rotate == 0);

		// tests turning and if currentRampForward immediately goes to 0 if the input is 0

		rotate = 1;
		driveForward = 0;
		ramp = rampForward();
		drivetrain.arcadeControl(driveForward, rotate, invert, toggleRotationExponent);
		Assert.assertTrue(currentRampForward == 0 && rotate == 1);


		// tests turning and driving simultaneously
		rotate = 1;
		driveForward = 0.5;
		drivetrain.setCurrentRampForward(0);
		ramp = rampForward();
		drivetrain.arcadeControl(driveForward, rotate, invert, toggleRotationExponent);
		Assert.assertTrue(currentRampForward == ramp && rotate == 1);

		// tests invert
		invert = true;
		rotate = -0.5;
		driveForward = 0.5;
		ramp = rampForward();
		drivetrain.arcadeControl(driveForward, rotate, invert, toggleRotationExponent);
		Assert.assertTrue(currentRampForward == ramp && rotate == -1*Math.abs(Math.pow(-0.5, drivetrain.getPrimaryRotationExponent())));
			

		// tests if it does not invert if the button is still pressed
		invert = true;
		rotate = -0.5;
		driveForward = 0.5;
		drivetrain.setCurrentRampForward(0.5);
		ramp = rampForward();
		drivetrain.setCurrentRampForward(-0.5);
		drivetrain.arcadeControl(driveForward, rotate, invert, toggleRotationExponent);
		Assert.assertTrue(currentRampForward == ramp && rotate == -1*Math.abs(Math.pow(-0.5, drivetrain.getPrimaryRotationExponent())));
		
		// tests if drivetrain continues to be inverted
		invert = false;
		rotate = -0.5;
		driveForward = 0.5;
		drivetrain.setCurrentRampForward(0.5);
		ramp = rampForward();
		drivetrain.setCurrentRampForward(-0.5);
		drivetrain.arcadeControl(driveForward, rotate, invert, toggleRotationExponent);
		Assert.assertTrue(currentRampForward == ramp && rotate == -1*Math.abs(Math.pow(-0.5, drivetrain.getPrimaryRotationExponent())));
		
		// tests if it inverts to the original position
		invert = true;
		rotate = 0.7;
		driveForward = 0.5;
		drivetrain.setCurrentRampForward(0.5);
		ramp = rampForward();
		drivetrain.setCurrentRampForward(0.5);
		drivetrain.arcadeControl(driveForward, rotate, invert, toggleRotationExponent);
		Assert.assertTrue(currentRampForward == -ramp && rotate == Math.pow(0.7, drivetrain.getPrimaryRotationExponent()));

		// see if movement and rotation go back to 0 again
		rotate = driveForward = 0;
		drivetrain.setCurrentRampForward(0.5);
		ramp = rampForward();
		drivetrain.setCurrentRampForward(0.5);
		drivetrain.arcadeControl(driveForward, rotate, invert, toggleRotationExponent);
		Assert.assertTrue(currentRampForward == ramp && rotate == 0);
		
		//tests rotation exponent
		toggleRotationExponent = true;
		rotate = 0.4;
		drivetrain.arcadeControl(driveForward, rotate, invert, toggleRotationExponent);
		Assert.assertTrue(currentRampForward == ramp && rotate == Math.pow(0.4, drivetrain.getSecondaryRotationExponent()));
		
		//test if the rotation exponent doesn't toggle if the toggle is pressed
		toggleRotationExponent = true;
		rotate = 0.4;
		drivetrain.arcadeControl(driveForward, rotate, invert, toggleRotationExponent);
		Assert.assertTrue(currentRampForward == ramp && rotate == Math.pow(0.4, drivetrain.getSecondaryRotationExponent()));
		
		//tests if the rotation exponent can be toggled off
		toggleRotationExponent = false;
		rotate = 0.5;
		drivetrain.arcadeControl(driveForward, rotate, invert, toggleRotationExponent);
		Assert.assertTrue(currentRampForward == ramp && rotate == Math.pow(0.5, drivetrain.getSecondaryRotationExponent()));
		rotate = 0.3;
		toggleRotationExponent = true;
		drivetrain.arcadeControl(driveForward, rotate, invert, toggleRotationExponent);
		Assert.assertTrue(currentRampForward == ramp && rotate == Math.pow(0.3, drivetrain.getPrimaryRotationExponent()));
	}

	/**
	 * testable version of drivetrain
	 */
	private class TestDrivetrain extends Drivetrain{

		/** 
		 * @param inputDeadzone joystick deadzone 
		 * @param ramping ramping acceleration constant 
		 * @param primaryEx primary rotation exponent
		 * @param secondEx secondary rotation exponent
		 * @param drivebase robot drive object
		 */
		public TestDrivetrain(double inputDeadzone, double ramping, double primaryEx, double secondEx,
				RobotDrive drivebase) {
			super(inputDeadzone, ramping, primaryEx, secondEx, drivebase);
		}

		/* (non-Javadoc)
		 * @see org.impact2585.lib2585.Drivetrain#arcadeDrive(double, double)
		 */
		@Override
		public void arcadeDrive(double movement, double currentRotate) {
			currentRampForward = movement;
			rotate = currentRotate;
		}

		/*
		 * (non-Javadoc)
		 * 
		 * @see
		 * org.impact2585.frc2016.systems.Drivetrain#setCurrentRampForward(
		 * double)
		 */
		@Override
		public void setCurrentRampForward(double rampForward) {
			super.setCurrentRampForward(rampForward);
			currentRampForward = rampForward;
		}

	}

}
