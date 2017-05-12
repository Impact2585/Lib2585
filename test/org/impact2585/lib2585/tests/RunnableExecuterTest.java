package org.impact2585.lib2585.tests;

import java.util.LinkedList;

import org.impact2585.lib2585.RunnableExecutor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test the RunnableExecuter Class
 */
public class RunnableExecuterTest {
	
	private RunnableExecutor concurrentExecutor;
	private RunnableExecutor sequentialExecutor;

	/**
	 * Set up before test
	 */
	@Before
	public void setUp() {
		concurrentExecutor = new RunnableExecutor(true) {

			private static final long serialVersionUID = 1L;
			
			};
		sequentialExecutor = new RunnableExecutor(false) {

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
			concurrentExecutor.getRunnables().add(item);
		concurrentExecutor.execute();
		for (TestRunnable runnable : testRunnables){
			Assert.assertTrue(runnable.ran);
			runnable.ran = false;// reset runnable
		}
		// test sequential too
		sequentialExecutor.getRunnables().addAll(testRunnables);
		sequentialExecutor.execute();
		for (TestRunnable runnable : testRunnables) {
			Assert.assertTrue(runnable.ran);
		}
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
