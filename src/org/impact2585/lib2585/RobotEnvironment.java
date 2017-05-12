package org.impact2585.lib2585;

import java.io.Serializable;

import edu.wpi.first.wpilibj.RobotBase;

/**
 * This class represents the environment of the robot. Subclasses should contain the systems.
 */
public abstract class RobotEnvironment implements Destroyable, Serializable {

	private static final long serialVersionUID = 5958899478403442774L;
	
	private transient RobotBase robot;

	/**
	 * Just a default constructor that does nothing
	 */
	public RobotEnvironment() {

	}

	/**
	 * Set the RobotBase
	 * @param robot the RobotBase to set
	 */
	public RobotEnvironment(RobotBase robot) {
		this.setRobot(robot);
	}
	
	/**
	 * Stops all motors running on the robot.
	 * Should be called while robot is disabled.
	 */
	public abstract void stopRobot();

	/**
	 * Find out which mode the bot is in
	 * @return if the robot is in auton mode
	 */
	public boolean isAutonomous() {
		return getRobot().isAutonomous();
	}

	/**
	 * See if the robot is in teleop
	 * @return if robot is in teleop mode
	 */
	public boolean isOperatorControl() {
		return getRobot().isOperatorControl();
	}

	/**
	 * Accessor for robot
	 * @return the robot
	 */
	protected synchronized RobotBase getRobot() {
		return robot;
	}

	/**
	 * Mutator for robot
	 * @param robot the robot to set
	 */
	protected synchronized void setRobot(RobotBase robot) {
		this.robot = robot;
	}
	
}
