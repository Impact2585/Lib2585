package org.impact2585.lib2585;

/**
 *A set of numbers that represent rotation, the x, y, and z components represent the axis and the w component represents the amount of rotation
 */
public class Quaternion {

	private double wComponent, xComponent, yComponent, zComponent;

	/**
	 * Constructor that sets the magnitude of the magnitude of the quaternion to 1
	 */
	public Quaternion() {
		zComponent = xComponent = yComponent = 0;
		wComponent = 1;
	}

	/**
	 * @return the wComponent
	 */
	public double getWComponent() {
		return wComponent;
	}

	/**
	 * @param wComponent the wComponent to set
	 */
	public void setWComponent(double wComponent) {
		this.wComponent = wComponent;
	}

	/**
	 * @return the xComponent
	 */
	public double getXComponent() {
		return xComponent;
	}

	/**
	 * @param xComponent the xComponent to set
	 */
	public void setXComponent(double xComponent) {
		this.xComponent = xComponent;
	}

	/**
	 * @return the yComponent
	 */
	public double getYComponent() {
		return yComponent;
	}

	/**
	 * @param yComponent the yComponent to set
	 */
	public void setYComponent(double yComponent) {
		this.yComponent = yComponent;
	}

	/**
	 * @return the zComponent
	 */
	public double getZComponent() {
		return zComponent;
	}

	/**
	 * @param zComponent the zComponent to set
	 */
	public void setZComponent(double zComponent) {
		this.zComponent = zComponent;
	}

}
