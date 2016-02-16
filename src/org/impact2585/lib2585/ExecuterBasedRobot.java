package org.impact2585.lib2585;

import java.io.Serializable;

import edu.wpi.first.wpilibj.IterativeRobot;

/**
 * Robot that uses executers This is the equivelant of a main class in WPILib.
 */
public abstract class ExecuterBasedRobot extends IterativeRobot implements Serializable {

	private static final long serialVersionUID = 2220871954112107703L;
	
	private transient Executer executer;

	/*
	 * (non-Javadoc)
	 * @see edu.wpi.first.wpilibj.IterativeRobot#robotInit()
	 */
	@Override
	public abstract void robotInit();

	/*
	 * (non-Javadoc)
	 * @see edu.wpi.first.wpilibj.IterativeRobot#autonomousPeriodic()
	 */
	@Override
	public void autonomousPeriodic() {
		if (executer != null)
			executer.execute();
	}

	/*
	 * (non-Javadoc)
	 * @see edu.wpi.first.wpilibj.IterativeRobot#teleopPeriodic()
	 */
	@Override
	public void teleopPeriodic() {
		if (executer != null)
			executer.execute();
	}

	/*
	 * (non-Javadoc)
	 * @see edu.wpi.first.wpilibj.IterativeRobot#testPeriodic()
	 */
	@Override
	public void testPeriodic() {
		if (executer != null)
			executer.execute();
	}


	/*
	 * (non-Javadoc)
	 * @see edu.wpi.first.wpilibj.IterativeRobot#disabledPeriodic()
	 */	
	@Override
	public void disabledPeriodic() {
		if (executer != null)
			executer.execute();
	}

	/**
	 * Accessor for executer
	 * @return the executer
	 */
	protected synchronized Executer getExecuter() {
		return executer;
	}

	/**
	 * Mutator for executer
	 * @param executer the executer to set
	 */
	protected synchronized void setExecuter(Executer executer) {
		this.executer = executer;
	}

}
