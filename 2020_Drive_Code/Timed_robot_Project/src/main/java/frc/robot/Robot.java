/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.PWMVictorSPX;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.PWMTalonSRX;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Talon;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  //setting up ports for drive motors and sensors
  private static final int kLeftMotorPort = 0;
  private static final int kRightMotorPort = 1;
  private static final int climbMotorPort = 2;
  private static final int kUltrasonicPort = 0;
 


  //set up of motor controller for victor sp
  private static final PWMTalonSRX climbMotor = new PWMTalonSRX(climbMotorPort);
  private static final PWMVictorSPX kRightMoter = new PWMVictorSPX(kRightMotorPort);
  private static final PWMVictorSPX kLeftMotor = new PWMVictorSPX(kLeftMotorPort);
  //set up a generic joystick
  private static final Joystick m_joystick = new Joystick(0);
  //set up a drive train
  private static final DifferentialDrive driveTrain = new DifferentialDrive(kLeftMotor, kRightMoter);
   // distance in inches the robot wants to stay from an object
  private static final double kHoldDistance = 12.0;

   // factor to convert sensor values to a distance in inches
  private static final double kValueToInches = 0.125;
 
   // proportional speed constant
  private static final double kP = 0.05;

  

  private final AnalogInput m_ultrasonic = new AnalogInput(kUltrasonicPort);
 // private final DifferentialDrive m_robotDrive
   //   = new DifferentialDrive(new PWMVictorSPX(kLeftMotorPort),
     // new PWMVictorSPX(kRightMotorPort));


  

  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    //Drive Code
    driveTrain.arcadeDrive(m_joystick.getY(), m_joystick.getX());
    // sensor returns a value from 0-4095 that is scaled to inches
    double currentDistance = m_ultrasonic.getValue() * kValueToInches;

    // convert distance error to a motor speed
    double currentSpeed = (kHoldDistance - currentDistance) * kP;
    
    climbMotor.set(m_joystick.getRawAxis(3)); //example code set a motor from controller
  

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
