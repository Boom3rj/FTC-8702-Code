package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import org.firstinspires.ftc.teamcode.Utils;
@TeleOp(name="8702 TeleOp", group="8702")
public class MecanumOpMode extends LinearOpMode {
    private final ElapsedTime runtime = new ElapsedTime();
    private DcMotor rearLeftDrive = null;
    private DcMotor frontLeftDrive = null;
    private DcMotor rearRightDrive = null;
    private DcMotor frontRightDrive = null;

    private DcMotor Intake = null;
    private DcMotorEx Launcher = null;

    private Utils utils = null;

    @Override
    public void runOpMode() {
        // Example comment
        telemetry.addData("Status", "Initialized");
        telemetry.update();

        // Initialize the hardware variables. Note that the strings used here as parameters
        // to 'get' must correspond to the names assigned during the robot configuration
        // step (using the FTC Robot Controller app on the phone).
        frontLeftDrive = hardwareMap.get(DcMotor.class, "frontLeftDrive");
        frontRightDrive = hardwareMap.get(DcMotor.class, "frontRightDrive");
        rearLeftDrive = hardwareMap.get(DcMotor.class, "rearLeftDrive");
        rearRightDrive = hardwareMap.get(DcMotor.class, "rearRightDrive");
        Intake = hardwareMap.get(DcMotor.class, "intake");
        Launcher = hardwareMap.get(DcMotorEx.class, "launcher");
        // To drive forward, most robots need the motor on one side to be reversed, because the axles point in opposite directions.
        // Pushing the left stick forward MUST make robot go forward. So adjust these two lines based on your first test drive.
        // Note: The settings here assume direct drive on left and right wheels.  Gear Reduction or 90 Deg drives may require direction flips
        frontLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        rearLeftDrive.setDirection(DcMotor.Direction.REVERSE);
        frontRightDrive.setDirection(DcMotor.Direction.FORWARD);
        rearRightDrive.setDirection(DcMotor.Direction.FORWARD);
        Launcher.setDirection(DcMotor.Direction.FORWARD);
        int flywheelSpeed = 1200;
        waitForStart();
        runtime.reset();

        while (opModeIsActive()) {
            double forward = gamepad1.left_stick_y;
            double strafe = gamepad1.left_stick_x;
            double rotation = gamepad1.right_stick_x;
            boolean intake = gamepad2.dpad_left;
            boolean unintake = gamepad2.dpad_right;
            //Turn on half speed for robot movements
            int halfSpeed = 1;
            if (gamepad1.right_bumper) {
                halfSpeed = 2;
            }
            if (intake) {
                Intake.setPower(1);
            } else if (unintake) {
                Intake.setPower(-1);
            } else {
                Intake.setPower(0);
            }
            if (gamepad2.dpad_up){
                flywheelSpeed += 1;
            } else if (gamepad2.dpad_down){
                flywheelSpeed += -1;
            }
            telemetry.addData("Set flywheel speed: ", flywheelSpeed);
            // Setup a variable for each drive wheel to save power level for telemetry
            double frontLeftPower;
            double rearLeftPower;
            double frontRightPower;
            double rearRightPower;
            //Calculate wheel power
            frontLeftPower = Range.clip((-forward + rotation + strafe) / halfSpeed, -1.0, 1.0);
            frontRightPower = Range.clip((forward + rotation + strafe) / halfSpeed, -1.0, 1.0);
            rearLeftPower = Range.clip((-forward + rotation - strafe) / halfSpeed, -1.0, 1.0);
            rearRightPower = Range.clip((forward + rotation - strafe) / halfSpeed, -1.0, 1.0);
            // Send calculated power to wheels
            frontLeftDrive.setPower(frontLeftPower);
            frontRightDrive.setPower(frontRightPower);
            rearLeftDrive.setPower(rearLeftPower);
            rearRightDrive.setPower(rearRightPower);
            if (gamepad2.a) {
                Launcher.setPower(utils.setVelo(-flywheelSpeed, (int) Launcher.getVelocity()));
                telemetry.addData("tps: ", Launcher.getVelocity());
                telemetry.addData("Power: ", utils.setVelo(-flywheelSpeed, (int) Launcher.getVelocity()));
            } else {
                Launcher.setPower(0);
            }
            telemetry.addData("tps: ", Launcher.getVelocity());
            telemetry.addData("Power: ", utils.setVelo(-flywheelSpeed, (int) Launcher.getVelocity()));
            telemetry.update();
        }
    }
}
