package org.impact2585.lib2585;

import java.io.Serializable;
import java.util.Vector;

/**
 * Executer that executes runnables
 */
public abstract class RunnableExecuter implements Executer, Serializable {
	
	private static final long serialVersionUID = -7700534735844195641L;
	
	private final Vector<Runnable> runnables = new Vector<Runnable>();
	private boolean concurrent;
	
	/**
	 * Just a default constructor. Concurrency will not be used.
	 */
	public RunnableExecuter(){
		
	}
	
	/**
	 * Set whether or not the Runnables execute concurrently
	 * @param concurrent concurrent to set
	 */
	public RunnableExecuter(boolean concurrent){
		this.concurrent = concurrent;
	}

	/* (non-Javadoc)
	 * @see org.impact2585.lib2585.Executer#execute()
	 */
	@Override
	public void execute() {
		//run all the runnables
		if(concurrent)
			runnables.parallelStream().forEach(runnable -> runnable.run());
		else {
			for (Runnable runnable : runnables) {
				runnable.run();
			}
		}
	}

	/**
	 * Accessor for the Runnable List
	 * @return the runnables
	 */
	public synchronized Vector<Runnable> getRunnables() {
		return runnables;
	}

}
