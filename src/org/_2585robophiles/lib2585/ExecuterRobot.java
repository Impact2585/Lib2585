package org._2585robophiles.lib2585;

import edu.wpi.first.wpilibj.IterativeRobot;

/**
 * Robot that uses executers
 * This is the equivelant of a main class in WPILib.
 */
public abstract class ExecuterRobot extends IterativeRobot {

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
