package org._2585robophiles.lib2585;

import java.io.Serializable;
import java.util.Vector;

/**
 * Executer that executes runnables
 */
public abstract class RunnableExecuter implements Executer, Serializable {
	
	private static final long serialVersionUID = -7700534735844195641L;
	
	private final Vector<Runnable> runnables = new Vector<Runnable>();

	/* (non-Javadoc)
	 * @see org._2585robophiles.lib2585.Executer#execute()
	 */
	public void execute() {
		//run all the runnables
		for(int i = 0; i < runnables.size(); i++){
			Runnable runnable = runnables.elementAt(i);
			runnable.run();
		}
	}

	/**
	 * @return the runnables
	 */
	public synchronized Vector<Runnable> getRunnables() {
		return runnables;
	}

}
