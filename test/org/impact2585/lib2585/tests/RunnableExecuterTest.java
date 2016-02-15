package org.impact2585.lib2585.tests;

import java.util.LinkedList;

import org.hamcrest.CoreMatchers;
import org.impact2585.lib2585.RunnableExecuter;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Test the RunnableExecuter Class
 */
public class RunnableExecuterTest {
	
	private RunnableExecuter concurrentExecuter;
	private RunnableExecuter sequentialExecuter;

	/**
	 * Set up before test
	 */
	@Before
	public void setUp() {
		concurrentExecuter = new RunnableExecuter(true) {

			private static final long serialVersionUID = 1L;
			
			};
		sequentialExecuter = new RunnableExecuter(false) {

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
			concurrentExecuter.getRunnables().add(item);
		concurrentExecuter.execute();
		for (TestRunnable runnable : testRunnables){
			Assert.assertThat(runnable.ran, CoreMatchers.equalTo(true));
			runnable.ran = false;// reset runnable
		}
		// test sequential too
		sequentialExecuter.getRunnables().addAll(testRunnables);
		sequentialExecuter.execute();
		for (TestRunnable runnable : testRunnables) {
			Assert.assertThat(runnable.ran, CoreMatchers.equalTo(true));
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
