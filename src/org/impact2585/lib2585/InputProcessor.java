package org.impact2585.lib2585;

/**
 * Functional interface that contains a method to process input
 */
@FunctionalInterface
public interface InputProcessor<T>{
	
	/**
	 * @param input an array of input values from a controller
	 * @return the value(usually speed) that should be sent to the motor controllers 
	 */
	public double process(T... input);
}
