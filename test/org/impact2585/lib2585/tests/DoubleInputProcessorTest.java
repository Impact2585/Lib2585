package org.impact2585.lib2585.tests;


import org.impact2585.lib2585.DoubleInputProcessor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


/**
 * Test for the DoubleInputProcessor
 */
public class DoubleInputProcessorTest {
	private double button1, button2, button3;
	private double firstMotorSpeed;
	private double secondMotorSpeed;
	private TestSystem system;
	private DoubleInputProcessor generalProcessor;
	
	/**
	 * Sets up the unit test
	 */
	@Before
	public void setUp() {
		system = new TestSystem();
		generalProcessor = new DoubleInputProcessor() {
			
			/* (non-Javadoc)
			 * @see org.impact2585.lib2585.DoubleInputProcessor#process(double[])
			 */
			@Override
			public double process(double... input) {
				return input[0];
			}
		};
	}

	/**
	 * Perform unit test on the DoubleInputProcessor
	 */
	@Test
	public void test() {
		button1 = 1;
		system.run();
		Assert.assertTrue(firstMotorSpeed == 1 && secondMotorSpeed == 0);
		button2 = 1;
		system.run();
		Assert.assertTrue(firstMotorSpeed == 0 && secondMotorSpeed == 0);
		button3 = -1;
		button1 = 0;
		system.run();
		Assert.assertTrue(firstMotorSpeed == -1 && secondMotorSpeed == -1);
	}
	
	/**
	 * A test system to process input from the input processor
	 */
	private class TestSystem implements Runnable{
		
		/** Sets the speed of the first motor using a DoubleInputProcessor
		 * @param processor a DoubleInputProcessor that takes input from two buttons
		 */
		public void setFirstMotorSpeed(DoubleInputProcessor processor) {
			firstMotorSpeed = processor.process(button1, button2);
		}
		
		/** Sets the speed of the second motor using a DoubleInputProcessor
		 * @param processor a DoubleInputProcessor that takes input from one button
		 */
		public void setSecondMotorSpeed(DoubleInputProcessor processor) {
			secondMotorSpeed = processor.process(button3);
		}
		
		/* (non-Javadoc)
		 * @see java.lang.Runnable#run()
		 */
		public void run() {
			this.setFirstMotorSpeed((a) -> {
				if(a[0] != 0 && a[1] == 0) {
					return 1;
				} else if(a[0] == 0 && a[1] != 0) {
					return -1;
				} else {
					return 0;
				}
			});
			
			this.setSecondMotorSpeed(generalProcessor);
		}
		
	}

}


