package org.impact2585.lib2585.tests;

import java.util.LinkedList;

import org.impact2585.lib2585.RunnableExecuter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test the RunnableExecuter Class
 */
public class RunnableExecuterTest {
	
	private RunnableExecuter executer;

	/**
	 * Set up before test
	 */
	@Before
	public void setUp() {
		executer = new RunnableExecuter() {

			private static final long serialVersionUID = 1L;
			
			};
	}

	/**
	 * Actually do the unit test
	 */
	@Test
	public void test() {
		LinkedList<TestRunnable> testRunnables = new LinkedList<>();
		for (int i = 0; i < 3; i++)
			testRunnables.add(new TestRunnable());
		for (Runnable item : testRunnables)
			executer.getRunnables().add(item);
		executer.execute();
		for (TestRunnable runnable : testRunnables)
			Assert.assertTrue(runnable.ran);
	}

	/**
	 * Class that implements Runnable for testing
	 */
	private class TestRunnable implements Runnable{
		
		private boolean ran;

		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		@Override
		public void run() {
			ran = true;
		}
		
	}
}
