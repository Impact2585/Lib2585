package org.impact2585.lib2585;

/**
 *A 3d vector
 */
public class Vector3 {

	private double xComponent,yComponent,zComponent;

	/**
	 * Constructor that sets the quantities to 0
	 */
	public Vector3() {
		xComponent = yComponent = zComponent = 0;
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
