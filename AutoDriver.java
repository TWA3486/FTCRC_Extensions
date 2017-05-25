package org.firstinspires.ftc.teamcode.RobotCoreExtensions;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by Jacob on 2/24/16.
 */
public abstract class AutoDriver {
    protected LinearOpMode opMode;
    protected Drivetrain drivetrain;
    private StallMonitor stallMonitor = new StallMonitor(this);
    protected double power = 1.0D;
    private int wait_time_ms = 500;
    protected boolean eStop = false;


    public AutoDriver(LinearOpMode opMode, Drivetrain drivetrain) {
        this.opMode = opMode;
        this.drivetrain = drivetrain;
    }

    public void set_power(double power) {
        if (power < 0) throw new IllegalArgumentException("the power should always be positive");
        this.power = power;
    }

    public void set_wait_time_between_movements(int milliseconds) {
        if (milliseconds < 0) throw new IllegalArgumentException("the wait time should always be positive");
        this.wait_time_ms = milliseconds;
    }

    private void setup_motion(String motion_description) {
        eStop = false;
        opMode.telemetry.addData("AutoDriver", motion_description);
        drivetrain.resetMotorEncoders();
        stallMonitor.start_monitoring();
    }

    private void end_motion() throws InterruptedException {
        drivetrain.haltDrive();
        stallMonitor.stop_monitoring();
        opMode.telemetry.addData("AutoDriver", "Halting");
        opMode.sleep(wait_time_ms);
    }

    protected void e_stop() {
        eStop = true;
    }

    public AutoDriver drive_forward(int encoderCounts) throws InterruptedException {
        setup_motion("Forward");
        drive_forward_implementation(encoderCounts);
        end_motion();
        return this;
    }

    public AutoDriver drive_backward(int encoderCounts) throws InterruptedException {
        setup_motion("Backward");
        drive_backward_implementation(encoderCounts);
        end_motion();
        return this;
    }

    public AutoDriver turn_clockwise(int degrees) throws InterruptedException {
        setup_motion("Clockwise");
        turn_clockwise_implementation(degrees);
        end_motion();
        return this;
    }

    public AutoDriver turn_counterclockwise(int degrees) throws InterruptedException {
        setup_motion("Counterclockwise");
        turn_counterclockwise_implementation(degrees);
        end_motion();
        return this;
    }

    public abstract AutoDriver drive_forward_implementation(int encoderCounts);
    public abstract AutoDriver drive_backward_implementation(int encoderCounts);
    public abstract AutoDriver turn_clockwise_implementation(int degrees);
    public abstract AutoDriver turn_counterclockwise_implementation(int degrees);
}
