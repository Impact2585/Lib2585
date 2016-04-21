package org.impact2585.lib2585;

/**
 * A functional interface for processing boolean inputs
 */
@FunctionalInterface
public interface BooleanInputProcessor {
	
	/**
	 * @param input input an array of boolean input values from a controller
	 * @return the value(usually speed) that should be sent to the motor controllers 
	 */
	public double process(Boolean... input);
}
