package org._2585robophiles.lib2585;

import edu.wpi.first.wpilibj.IterativeRobot;

public abstract class ExecuterRobot extends IterativeRobot {

    private RobotEnvironment environment;
    private Executer executer;

    /* (non-Javadoc)
     * @see edu.wpi.first.wpilibj.IterativeRobot#robotInit()
     */
    public abstract void robotInit();

    /* (non-Javadoc)
     * @see edu.wpi.first.wpilibj.IterativeRobot#autonomousPeriodic()
     */
    public void autonomousPeriodic() {
        executer.execute();
    }

    /* (non-Javadoc)
     * @see edu.wpi.first.wpilibj.IterativeRobot#teleopPeriodic()
     */
    public void teleopPeriodic() {
        executer.execute();
    }

    /* (non-Javadoc)
     * @see edu.wpi.first.wpilibj.IterativeRobot#testPeriodic()
     */
    public void testPeriodic() {
        executer.execute();
    }

    /* (non-Javadoc)
     * @see edu.wpi.first.wpilibj.IterativeRobot#disabledInit()
     */
    public void disabledInit() {

    }

    /* (non-Javadoc)
     * @see edu.wpi.first.wpilibj.IterativeRobot#disabledPeriodic()
     */
    public void disabledPeriodic() {

    }

    /* (non-Javadoc)
     * @see edu.wpi.first.wpilibj.RobotBase#free()
     */
    public void free() {
        environment.destroy();
        super.free();
    }

	/**
	 * @return the environment
	 */
	protected synchronized RobotEnvironment getEnvironment() {
		return environment;
	}

	/**
	 * @param environment the environment to set
	 */
	protected synchronized void setEnvironment(RobotEnvironment environment) {
		this.environment = environment;
	}

	/**
	 * @return the executer
	 */
	protected synchronized Executer getExecuter() {
		return executer;
	}

	/**
	 * @param executer the executer to set
	 */
	protected synchronized void setExecuter(Executer executer) {
		this.executer = executer;
	}

}
