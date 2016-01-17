package org.impact2585.lib2585;

import java.io.Serializable;

import edu.wpi.first.wpilibj.SensorBase;
import edu.wpi.first.wpilibj.SpeedController;

/**
 * This class can be used to control multiple motors which are working together as one.
 */
public class MultiMotor extends SensorBase implements SpeedController, Serializable {

	private static final long serialVersionUID = 6048538258446457916L;
	
	private transient SpeedController[] motors;

	/**
	 * Constructor that consumes an array
	 * @param motors SpeedController array
	 * @throws IllegalArgumentException if array is empty
	 */
	public MultiMotor(SpeedController[] motors) throws IllegalArgumentException {
		if (motors.length < 1)
			throw new IllegalArgumentException("Empty array");
		this.motors = motors;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.wpi.first.wpilibj.PIDOutput#pidWrite(double)
	 */
	@Override
	public void pidWrite(double output) {
		for (int i = 0; i < motors.length; i++) {
			motors[i].pidWrite(output);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.wpi.first.wpilibj.SpeedController#disable()
	 */
	@Override
	public void disable() {
		for (int i = 0; i < motors.length; i++) {
			motors[i].disable();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.wpi.first.wpilibj.SpeedController#get()
	 */
	@Override
	public double get() {
		return motors[0].get();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.wpi.first.wpilibj.SpeedController#set(double)
	 */
	@Override
	public void set(double speed) {
		for (int i = 0; i < motors.length; i++)
			motors[i].set(speed);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.wpi.first.wpilibj.SpeedController#set(double, byte)
	 */
	@Override
	public void set(double speed, byte syncGroup) {
		for (int i = 0; i < motors.length; i++)
			motors[i].set(speed, syncGroup);
	}
	
	/* (non-Javadoc)
	 * @see edu.wpi.first.wpilibj.SpeedController#getInverted()
	 */
	@Override
	public boolean getInverted(){
		return motors[0].getInverted();
		
	}
	
	/* (non-Javadoc)
	 * @see edu.wpi.first.wpilibj.SpeedController#setInverted(boolean)
	 */
	@Override
	public void setInverted(boolean isInverted) {
		for (int i = 0; i < motors.length; i++)
			motors[i].setInverted(isInverted);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see edu.wpi.first.wpilibj.SensorBase#free()
	 */
	@Override
	public void free() {
		for (int i = 0; i < motors.length; i++) {
			if (motors[i] instanceof SensorBase) {
				SensorBase motor = (SensorBase) motors[i];
				motor.free();
			}
		}
	}

}
