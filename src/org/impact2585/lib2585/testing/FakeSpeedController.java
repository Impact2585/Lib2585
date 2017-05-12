package org.impact2585.lib2585.testing;

import edu.wpi.first.wpilibj.SpeedController;

/**
 * SpeedController used for testing
 */
public class FakeSpeedController implements SpeedController {
	
	private double speed, pidOutput;
	private byte sync;
	private boolean inverted;

	/* (non-Javadoc)
	 * @see edu.wpi.first.wpilibj.PIDOutput#pidWrite(double)
	 */
	@Override
	public void pidWrite(double output) {
		pidOutput = output;
	}

	/* (non-Javadoc)
	 * @see edu.wpi.first.wpilibj.SpeedController#disable()
	 */
	@Override
	public void disable() {
		speed = 0;
	}

	/* (non-Javadoc)
	 * @see edu.wpi.first.wpilibj.SpeedController#get()
	 */
	@Override
	public double get() {
		return speed;
	}

	/* (non-Javadoc)
	 * @see edu.wpi.first.wpilibj.SpeedController#set(double)
	 */
	@Override
	public void set(double speed) {
		this.speed = speed;
	}

	/**
	 * @return the pidOutput
	 */
	public double getPidOutput() {
		return pidOutput;
	}

	/**
	 * @param pidOutput the pidOutput to set
	 */
	protected void setPidOutput(double pidOutput) {
		this.pidOutput = pidOutput;
	}

	/**
	 * @return the sync
	 */
	public byte getSync() {
		return sync;
	}

	/**
	 * @param sync the sync to set
	 */
	protected void setSync(byte sync) {
		this.sync = sync;
	}

	/* (non-Javadoc)
	 * @see edu.wpi.first.wpilibj.SpeedController#setInverted(boolean)
	 */
	@Override
	public void setInverted(boolean isInverted) {
		inverted = isInverted;
	}

	/* (non-Javadoc)
	 * @see edu.wpi.first.wpilibj.SpeedController#getInverted()
	 */
	@Override
	public boolean getInverted() {
		return inverted;
	}

	/* (non-Javadoc)
	 * @see edu.wpi.first.wpilibj.SpeedController#stopMotor()
	 */
	@Override
	public void stopMotor() {
		speed = 0;
	}

}