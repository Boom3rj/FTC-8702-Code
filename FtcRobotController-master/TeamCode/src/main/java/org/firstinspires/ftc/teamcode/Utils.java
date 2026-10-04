package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
public class Utils {
    public static int dif;
    public static double setVelo(int tps, int velo) {
        // finds velocity of launcher
        dif = velo - tps;
        // finds the difference of current speed and where it needs to be
        double power = 0;

        power = (dif + 80) / 120;
            // (difference + offset) / Agressiveness(lower is more agressive) NEED TO TUNE


        return -power;
    }
}
