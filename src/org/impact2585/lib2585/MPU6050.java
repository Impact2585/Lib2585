package org.impact2585.lib2585;

import java.nio.BufferOverflowException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ReadOnlyBufferException;


import edu.wpi.first.wpilibj.I2C;

/**
 *Systems on the robot should use this class if they want to use an MPU6050 to measure acceleration and orientation
 */
public class MPU6050 {
	public static final int DEVICE_ADDRESS = 0x34;
	public static final short DMP_PACKET_SIZE = 42;
	public static final int RESET_BIT = 7;
	public static final int SLEEP_BIT = 6;
	public static final int SLAVE_0_ADDRESS =  0x25;
	public static final int POWER_MANAGEMENT_REGISTER = 0x6B;
	public static final int USER_CONTROL_REGISTER = 0x6A;
	public static final int USER_CONTROL_BIT = 5;
	public static final int USER_CONTROL_RESET_BIT = 1;
	public static final int USER_CONTROL_DMP_ENABLED_BIT = 7;
	public static final int USER_CONTROL_FIFO_ENABLED_BIT = 6;
	public static final int USER_CONTROL_DMP_RESET_BIT = 3;
	public static final int MASTER_RESET_BIT = 1;
	public static final int RATE_REGISTER = 0x19;
	public static final int DMP_CONFIG_REGISTER_1 = 0x70;
	public static final int DMP_CONFIG_REGISTER_2 = 0x71;
	public static final int XGYRO_OFFSET_REGISTER = 0x00;
	public static final int YGYRO_OFFSET_REGISTER = 0x01;
	public static final int ZGYRO_OFFSET_REGISTER = 0x02;
	public static final int OTP_BIT = 0;
	public static final int OFFSET_BIT = 6;
	public static final int MOTION_THRESHOLD_REGISTER = 0x1F;
	public static final int ZERO_MOTION_THRESHOLD_REGISER = 0x21;
	public static final int MOTION_DETECTION_DURATION_REGISTER = 0x20;
	public static final double GRAVITY_UNIT = 8192.0;
	public static final double ACCELERATION_FROM_GRAVITY = 9.81;
	

	private I2C i2cBus;
	private boolean connectionSuccessfull;

	private byte fifoBuffer[];
	private short rawValues[];
	private byte fifoCount[];
	private Quaternion quaternion;
	private Vector3 accel;
	private Vector3 gravity;
	private double gyro[];
	private short currentFIFOCount;

	/**
	 * initializes the MPU6050 on the I2Cbus and sets up the instance variables
	 */
	public MPU6050() {
		i2cBus = new I2C(I2C.Port.kOnboard, DEVICE_ADDRESS);
		connectionSuccessfull = !i2cBus.addressOnly();
		if(connectionSuccessfull) {
			try{
				initialize();
			} catch(BufferUnderflowException underflow) {
				underflow.printStackTrace();
			} catch(IndexOutOfBoundsException bounds) {
				bounds.printStackTrace();
			} catch(BufferOverflowException overflow) {
				overflow.printStackTrace();
			} catch(ReadOnlyBufferException buffer) {
				buffer.printStackTrace();
			}
		}
		accel = new Vector3();
		gravity = new Vector3();
		gyro = new double[3];
		currentFIFOCount = 0;
		fifoCount = new byte[2];
		quaternion = new Quaternion();
		rawValues = new short[6];
		fifoBuffer = new byte[64];

	}

	/**Initializes the MPU6050
	 * @throws BufferUnderflowException
	 * @throws BufferOverflowException
	 * @throws IndexOutOfBoundsException
	 * @throws ReadOnlyBufferException
	 */
	public void initialize() throws BufferUnderflowException, BufferOverflowException, IndexOutOfBoundsException, ReadOnlyBufferException{
		//resets the dmp
		writeBit(POWER_MANAGEMENT_REGISTER, RESET_BIT, true);
		writeBit(POWER_MANAGEMENT_REGISTER, SLEEP_BIT, false);
		i2cBus.write(SLAVE_0_ADDRESS, 0x7F);
		writeBit(USER_CONTROL_REGISTER, USER_CONTROL_BIT, false);
		i2cBus.write(SLAVE_0_ADDRESS, 0x68);
		writeBit(USER_CONTROL_REGISTER, MASTER_RESET_BIT, true);

		//sets rate to 200 hz formula is 1000 hz / (1 + byte written)
		i2cBus.write(RATE_REGISTER, 4);
		//sets the frame sync rate
		writeBits(0x1A, 5, 3, 0x1); 

		//DMP config registers
		i2cBus.write(DMP_CONFIG_REGISTER_1, 3);
		i2cBus.write(DMP_CONFIG_REGISTER_2, 0);

		//clears the OTP
		writeBit(XGYRO_OFFSET_REGISTER, OTP_BIT, false);

		//offsets the gyro
		writeBits(XGYRO_OFFSET_REGISTER, OFFSET_BIT, 6, 220);
		writeBits(YGYRO_OFFSET_REGISTER, OFFSET_BIT, 6, 76);
		writeBits(ZGYRO_OFFSET_REGISTER, OFFSET_BIT, 6, -85);

		//sets the motion detection threshold
		i2cBus.write(MOTION_THRESHOLD_REGISTER, 2);
		i2cBus.write(ZERO_MOTION_THRESHOLD_REGISER, 156);
		i2cBus.write(MOTION_DETECTION_DURATION_REGISTER, 80);

		//resets the FIFO buffer
		writeBit(USER_CONTROL_REGISTER, USER_CONTROL_RESET_BIT, true);
		//enables the FIFO buffer
		writeBit(USER_CONTROL_REGISTER, USER_CONTROL_FIFO_ENABLED_BIT, true);
		//enables the DMP
		writeBit(USER_CONTROL_REGISTER, USER_CONTROL_DMP_ENABLED_BIT, true);
		//resets the DMP
		writeBit(USER_CONTROL_REGISTER, USER_CONTROL_DMP_RESET_BIT, true);

		//sets the gyro and accelerometer ranges
		i2cBus.write(0x1B, 0);
		i2cBus.write(0x1C, 0);
	}
	
	/**
	 * @return true if the MPU6050 successfully updated, false if otherwise
	 */
	public boolean update() {
		updateFIFOcount();
		if (currentFIFOCount >= DMP_PACKET_SIZE && connectionSuccessfull) {
			readRawValues();
			return true;
		} 
		return false;
	}

	/**
	 * @return the current size of the FIFO buffer
	 */
	public short getCurrentFIFOCount() {
		return currentFIFOCount;
	}

	/**Sets the current FIFO count to size
	 * @param size the new size of the FIFO buffer
	 */
	public void setCurrentFIFOCount(short size) {
		currentFIFOCount = size;
	}

	/**writes more than one bit through I2C
	 * @param register the register on the slave to write to
	 * @param position the starting bit on the register for the data to start writing to
	 * @param length the amount of bits the data writes to
	 * @param data the data to write to
	 */
	private void writeBits(int register, int position, int length, int data) {
		if(connectionSuccessfull) {
			byte registerdata[] = new byte[1];
			try {
				i2cBus.read(register, 1, registerdata);
				int mask = (1 << length - 1) << (position - length + 1);

				//shifts the data to the correct position
				data <<= (position - length + 1);
				//zeros all the bits that do not need to be written
				data &= mask;
				int bytetowrite = byteArrayToInt(registerdata);
				//zeros the bits in the byte that are going to be written to
				bytetowrite &= ~mask;
				//writes the data to the byte
				bytetowrite |= data;
				i2cBus.write(register, bytetowrite);
			} catch(BufferUnderflowException underflow) {
				underflow.printStackTrace();
			} catch(BufferOverflowException overflow) {
				overflow.printStackTrace();
			} catch(ReadOnlyBufferException buff) {
				buff.printStackTrace();
			} catch(IndexOutOfBoundsException bounds) {
				bounds.printStackTrace();
			}
		}
	}

	/**
	 *updates the currentFIFOCount
	 *this should be called in the execute method of a system
	 */
	public void updateFIFOcount() {
		if(connectionSuccessfull) {
			try {
				i2cBus.read(0x72, 2, fifoCount);
				currentFIFOCount = (short) (fifoCount[1] << 8 | fifoCount[0]);
			} catch(BufferUnderflowException underflow) {
				underflow.printStackTrace();
			} catch(IndexOutOfBoundsException bounds) {
				bounds.printStackTrace();
			}
		}
	}

	/**
	 * @param initialbyte the byte array to transform into an int
	 * @return the int version of the byte array
	 */
	private int byteArrayToInt(byte initialbyte[]) {
		ByteBuffer buf = ByteBuffer.wrap(initialbyte);
		return buf.getInt();
	}

	/**writes a bit to the slave register through I2C
	 * @param register the register to write the bit to
	 * @param position the bit to write to (0-7)
	 * @param bit turn the bit on or off
	 */
	public void writeBit(int register, int position, boolean bit) {
		if(connectionSuccessfull) {
			byte changedByte[] = new byte[1];
			try {
				i2cBus.read(register, 1, changedByte);
				changedByte[0] = (byte) ((bit) ? (changedByte[0] | (byte)(1 << position)) : (changedByte[0] & (byte)(~(1<<position))));
				int bytetowrite = byteArrayToInt(changedByte);
				i2cBus.write(register, bytetowrite);
			} catch(BufferUnderflowException underflow) {
				underflow.printStackTrace();
			} catch(BufferOverflowException overflow) {
				overflow.printStackTrace();
			} catch(IndexOutOfBoundsException out) {
				out.printStackTrace();
			} catch(ReadOnlyBufferException buffer) {
				buffer.printStackTrace();
			}
		}
	}

	/**
	 *Gets the raw accelerometer and gyro values from the MPU6050 
	 */
	public void readRawValues() {
		if(connectionSuccessfull) {
			try {
				i2cBus.read(0x74, DMP_PACKET_SIZE, fifoBuffer);
			} catch (BufferUnderflowException underflow){
				underflow.printStackTrace();
			} catch (IndexOutOfBoundsException bounds) {
				bounds.printStackTrace();
			}
			quaternionValues();
			gravity();
			accelerationValues();
			gyroValues();
		}
	}

	/**
	 * Gets the values for the quaternion from the FIFO buffer
	 */
	public void quaternionValues() {
		for(int i = 0; i < 4; i++) {
			rawValues[i] = (short)((fifoBuffer[i * 4] << 8) | fifoBuffer[i+1]);
		}
		quaternion.setWComponent((double)rawValues[0] / 2 * GRAVITY_UNIT);
		quaternion.setXComponent((double)rawValues[0] / 2 * GRAVITY_UNIT);
		quaternion.setYComponent((double)rawValues[0] / 2 * GRAVITY_UNIT);
		quaternion.setZComponent((double)rawValues[0] / 2 * GRAVITY_UNIT);
	}

	/**
	 * Gets the acceleration values
	 */
	public void accelerationValues() {
		accel.setXComponent(fifoBuffer[28] << 8 | fifoBuffer[29]);
		accel.setYComponent(fifoBuffer[32] << 8 | fifoBuffer[33]);
		accel.setZComponent(fifoBuffer[36] << 8 | fifoBuffer[37]);

		//removes gravity from the acceleration vector
		accel.setXComponent(accel.getXComponent() - gravity.getXComponent() * GRAVITY_UNIT);
		accel.setYComponent(accel.getYComponent() - gravity.getYComponent() * GRAVITY_UNIT);
		accel.setZComponent(accel.getZComponent() - gravity.getZComponent() * GRAVITY_UNIT);
	}

	/**
	 * gets the gravity vector
	 */
	public void gravity() {
		gravity.setXComponent(2 * quaternion.getXComponent() * quaternion.getZComponent() - quaternion.getWComponent() * quaternion.getYComponent());
		gravity.setYComponent(2 * quaternion.getWComponent() * quaternion.getXComponent() + quaternion.getYComponent() *quaternion.getZComponent());
		gravity.setZComponent(quaternion.getWComponent() * quaternion.getWComponent() - quaternion.getXComponent() * quaternion.getXComponent() - quaternion.getYComponent() * quaternion.getYComponent() + quaternion.getZComponent() + quaternion.getZComponent());
	}

	/**
	 * gets the gyro values in yaw, pitch, and roll
	 */
	public void gyroValues() {
		gyro[0] = Math.atan2(2*quaternion.getXComponent()*quaternion.getYComponent() - 2*quaternion.getWComponent()*quaternion.getZComponent(), 2*quaternion.getWComponent()*quaternion.getWComponent() + 2*quaternion.getXComponent()*quaternion.getXComponent() - 1);
		gyro[1] = Math.atan(gravity.getXComponent() / Math.sqrt(gravity.getYComponent() * gravity.getYComponent() + gravity.getZComponent() * gravity.getZComponent()));
		gyro[2] = Math.atan(gravity.getYComponent() / Math.sqrt(gravity.getXComponent() * gravity.getXComponent() + gravity.getZComponent() * gravity.getZComponent()));
	}

	/**
	 * @return the x-axis gyro value or the yaw
	 */
	public double getYaw() {
		return gyro[0]*180/Math.PI;
	}

	/**
	 * @return the y-axis gyro value or the pitch
	 */
	public double getPitch() {
		return gyro[1]*180/Math.PI;
	}

	/**
	 * @return the z-axis gyro value or the roll
	 */
	public double getRoll() {
		return gyro[2]*180/Math.PI;
	}

	/**
	 * @returns the acceleration in the x-axis in m/s^2
	 */
	public double getXAccel() {
		return accel.getXComponent() / GRAVITY_UNIT * ACCELERATION_FROM_GRAVITY;
	}

	/**
	 * @returns the acceleration in the y-axis in m/s^2
	 */
	public double getYAccel() {
		return accel.getYComponent() / GRAVITY_UNIT * ACCELERATION_FROM_GRAVITY;
	}

	/**
	 * @returns the acceleration in the z-axis in m/s^2
	 */
	public double getZAccel() {
		return accel.getZComponent() / GRAVITY_UNIT * ACCELERATION_FROM_GRAVITY;
	}

	/**
	 * a 3d vector, a mathematical quantity that has both direction and magnitude
	 */
	private class Vector3 {
		private double xComponent,yComponent,zComponent;

		/**
		 * Constructor that sets the quantities to 0
		 */
		public Vector3() {
			xComponent = yComponent = zComponent = 0;
		}

		/**
		 * @return the xComponent
		 */
		public double getXComponent() {
			return xComponent;
		}

		/**
		 * @param xComponent the xComponent to set
		 */
		public void setXComponent(double xComponent) {
			this.xComponent = xComponent;
		}

		/**
		 * @return the yComponent
		 */
		public double getYComponent() {
			return yComponent;
		}

		/**
		 * @param yComponent the yComponent to set
		 */
		public void setYComponent(double yComponent) {
			this.yComponent = yComponent;
		}

		/**
		 * @return the zComponent
		 */
		public double getZComponent() {
			return zComponent;
		}

		/**
		 * @param zComponent the zComponent to set
		 */
		public void setZComponent(double zComponent) {
			this.zComponent = zComponent;
		}
		
	}
	
	/**
	 * A set of numbers that represent rotation, the x, y, and z components represent the axis and the w component represents the amount of rotation
	 */
	private class Quaternion {
		private double wComponent, xComponent, yComponent, zComponent;

		/**
		 * Constructor that sets the magnitude of the magnitude of the quaternion to 1
		 */
		public Quaternion() {
			zComponent = xComponent = yComponent = 0;
			wComponent = 1;
		}

		/**
		 * @return the wComponent
		 */
		public double getWComponent() {
			return wComponent;
		}

		/**
		 * @param wComponent the wComponent to set
		 */
		public void setWComponent(double wComponent) {
			this.wComponent = wComponent;
		}

		/**
		 * @return the xComponent
		 */
		public double getXComponent() {
			return xComponent;
		}

		/**
		 * @param xComponent the xComponent to set
		 */
		public void setXComponent(double xComponent) {
			this.xComponent = xComponent;
		}

		/**
		 * @return the yComponent
		 */
		public double getYComponent() {
			return yComponent;
		}

		/**
		 * @param yComponent the yComponent to set
		 */
		public void setYComponent(double yComponent) {
			this.yComponent = yComponent;
		}

		/**
		 * @return the zComponent
		 */
		public double getZComponent() {
			return zComponent;
		}

		/**
		 * @param zComponent the zComponent to set
		 */
		public void setZComponent(double zComponent) {
			this.zComponent = zComponent;
		}

	}
}
