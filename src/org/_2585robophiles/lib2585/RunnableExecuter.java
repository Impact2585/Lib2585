package org._2585robophiles.lib2585;

import java.util.Vector;

/**
 * Executer that executes runnables
 */
public abstract class RunnableExecuter implements Executer {
	
	private final Vector runnables = new Vector();

	/* (non-Javadoc)
	 * @see org._2585robophiles.lib2585.Executer#execute()
	 */
	public void execute() {
		//run all the runnables
		for(int i = 0; i < runnables.size(); i++){
			Runnable runnable = (Runnable) runnables.elementAt(i);
			runnable.run();
		}
	}

}
