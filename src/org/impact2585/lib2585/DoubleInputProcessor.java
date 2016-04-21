package org.impact2585.lib2585;


/**
 * Functional interface that contains a method to process input
 */
@FunctionalInterface
public interface DoubleInputProcessor{
	
	/**
	 * @param input an array of double input values from a controller
	 * @return the value(usually speed) that should be sent to the motor controllers 
	 */
	public double process(Double... input);
	
}
