package org.impact2585.lib2585;

/**
 * Functional interface that contains a method to process input
 */
public interface DoubleInputProcessor {
	
	/**
	 * @param input an array of input values from a controller that are doubles
	 * @return the value(usually speed) that should be sent to the motor controllers 
	 */
	public double process(double... input);
}
