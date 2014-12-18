package org._2585robophiles.lib2585;

import edu.wpi.first.wpilibj.RobotBase;

public abstract class RobotEnvironment implements Destroyable {

	private RobotBase robot;

	/**
	 * initializes nothing
	 */
	public RobotEnvironment() {

	}

	/**
	 * Initializes systems
	 * 
	 * @param robot the RobotBase to set
	 */
	public RobotEnvironment(RobotBase robot) {
		this.setRobot(robot);
	}

	/**
	 * @return if the robot is in auton mode
	 */
	public boolean isAutonomous() {
		return getRobot().isAutonomous();
	}

	/**
	 * @return if robot is in teleop mode
	 */
	public boolean isOperatorControl() {
		return getRobot().isOperatorControl();
	}

	/**
	 * @return the robot
	 */
	protected synchronized RobotBase getRobot() {
		return robot;
	}

	/**
	 * @param robot the robot to set
	 */
	protected synchronized void setRobot(RobotBase robot) {
		this.robot = robot;
	}

}
