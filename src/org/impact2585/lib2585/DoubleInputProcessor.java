package org.impact2585.lib2585;

/**
 * Functional interface that contains a method to process input
 */
@FunctionalInterface
public interface DoubleInputProcessor {
	
	/**
	 * @param input an array of input values from a controller that are doubles
	 * @return the value (usually motor controller speed) that should be output
	 */
	public double process(double... input);
}
