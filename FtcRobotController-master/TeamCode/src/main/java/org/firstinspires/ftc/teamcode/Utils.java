package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
public class Utils {
    public static int dif;
    public  double setVelo(int tps, int velo) {
        // finds velocity of launcher
        dif = tps - velo;
        // finds the difference of current speed and where it needs to be
        double power = 0;
        if (dif > 0) {
            power = (dif + 20) / 500;
            // (difference + offset) / Agressiveness(lower is more agressive) NEED TO TUNE
        } else if (dif < -250) {
            power = -0.5;
            // slow down motor
        } else {
            power = 0;
            // motor off
        }
        return power;
    }
}
