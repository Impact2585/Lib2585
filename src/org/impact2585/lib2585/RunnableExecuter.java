package org.impact2585.lib2585;

import java.io.Serializable;
import java.util.Vector;

/**
 * Executer that executes runnables
 */
public abstract class RunnableExecuter implements Executer, Serializable {
	
	private static final long serialVersionUID = -7700534735844195641L;
	
	private final Vector<Runnable> runnables = new Vector<Runnable>();

	/* (non-Javadoc)
	 * @see org.impact2585.lib2585.Executer#execute()
	 */
	public void execute() {
		//run all the runnables
		runnables.parallelStream().forEach(runnable -> runnable.run());
	}

	/**
	 * Accessor for the Runnable List
	 * @return the runnables
	 */
	public synchronized Vector<Runnable> getRunnables() {
		return runnables;
	}

}
