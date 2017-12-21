package org.impact2585.lib2585;

import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Victor;

/**
 * A speed controller that uses linear ramping to smooth the output speed
 */
public class RampedSpeedController implements Destroyable {
	private SpeedController controller;
	
	protected double currentSpeed = 0;
	
	public static final double DEADZONE = 0.01;
	public static final double RAMP = 0.6;
	
	/**
	 * Constructor that creates a Victor to use as the speed controller with the channel number
	 * for the Victor to use
	 * @param channel the number of the channel to create the speed controller with
	 */
	public RampedSpeedController(int channel) {
		controller = new Victor(channel);
		controller.set(0);
	}
	
	/**
	 * Constructor that takes a speed controller that will receive the ramped inputs
	 * @param cont the speed controller instance that should be used to set speeds
	 */
	public RampedSpeedController(SpeedController cont) {
		controller = cont;
		controller.set(0);
	}
	
	/**
	 * @param newSpeed the speed to be ramped and set to the speed controller
	 */
	public void updateWithSpeed(double newSpeed) {
		currentSpeed = applyRamp(applyDeadZone(newSpeed));
		setSpeedRaw(currentSpeed);
	}
	
	/**
	 * @param newSpeed the speed to set the motorController to
	 */
	private void setSpeedRaw(double newSpeed) {
		controller.set(newSpeed);
	}
	
	/**
	 * @param speedIn the raw speed to be ramped
	 * @return the speed after ramping has been applied
	 */
	private double applyRamp(double speedIn) {
		// Go straight to 0 if the input is 0
		if (speedIn == 0) {
			return 0;
		}
		
		double speedDiff = speedIn - currentSpeed;
		double speed;
		if (Math.abs(speedDiff) < 0.01) {
			speed = speedIn;
		} else {
			speed = currentSpeed + RAMP * (speedDiff);
		}
		return speed;
	}
	
	/**
	 * @return the value of the current speed 
	 */
	public double getCurrentSpeed() {
		return currentSpeed;
	}
	
	/**
	 * @param val the value to apply the deadzone to
	 * @return either 0 or the value depending on whether it falls within the deadzone
	 */
	protected double applyDeadZone(double val) {
		if (Math.abs(val) < DEADZONE) {
			val = 0;
		}
		return val;
	}

	/* (non-Javadoc)
	 * @see org.impact2585.lib2585.Destroyable#destroy()
	 */
	@Override
	public void destroy() {
		if (controller instanceof PWM) {
			((PWM) controller).free();
		}
	}
}
