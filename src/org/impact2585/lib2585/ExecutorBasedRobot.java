package org.impact2585.lib2585;

import java.io.Serializable;

import edu.wpi.first.wpilibj.IterativeRobot;

/**
 * Robot that uses executers This is the equivelant of a main class in WPILib.
 */
public abstract class ExecutorBasedRobot extends IterativeRobot implements Serializable {

	private static final long serialVersionUID = 2220871954112107703L;

	private transient Executer executor;

	private RobotEnvironment environ;

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
		if (executor != null)
			executor.execute();
	}

	/*
	 * (non-Javadoc)
	 * @see edu.wpi.first.wpilibj.IterativeRobot#teleopPeriodic()
	 */
	@Override
	public void teleopPeriodic() {
		if (executor != null)
			executor.execute();
	}

	/*
	 * (non-Javadoc)
	 * @see edu.wpi.first.wpilibj.IterativeRobot#testPeriodic()
	 */
	@Override
	public void testPeriodic() {
		if (executor != null)
			executor.execute();
	}

	/*
	 * (non-Javadoc)
	 * @see edu.wpi.first.wpilibj.IterativeRobot#disabledInit()
	 */
	@Override
	public void disabledInit() {
		setExecutor(null);
		if (environ != null)
			environ.stopRobot();
	}

	/*
	 * (non-Javadoc)
	 * @see edu.wpi.first.wpilibj.IterativeRobot#disabledPeriodic()
	 */	
	@Override
	public void disabledPeriodic() {
		if (environ != null)
			environ.stopRobot();
	}

	/**
	 * Accessor for executer
	 * @return the executer
	 */
	protected synchronized Executer getExecutor() {
		return executor;
	}

	/**
	 * Mutator for executer
	 * @param executer the executer to set
	 */
	protected synchronized void setExecutor(Executer executer) {
		this.executor = executer;
	}

}
