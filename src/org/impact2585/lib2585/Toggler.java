package org.impact2585.lib2585;

/**
 * Class that toggles a boolean
 */
public class Toggler {
	private boolean toggleState;
	private boolean prevToggle;
	
	/**Toggler constructor that takes in a parameter for the default state of the toggle
	 * @param defaultState the default state of the toggle
	 */
	public Toggler(boolean defaultState) {
		toggleState = defaultState;
		prevToggle = false;
	}
	
	/**
	 * @param toggle a boolean that toggles the toggleState if true and does nothing if otherwise
	 * @return the toggleState of the Toggler
	 */
	public boolean toggle(boolean toggle) {
		
		//The toggleState will not be toggled if the toggle has been held down too long
		if(toggle && !prevToggle) {
			toggleState = !toggleState;
		}
		prevToggle = toggle;
		return toggleState;
	}
}
