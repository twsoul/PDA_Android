package net.m3mobile.ugr_demo;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by M3 on 2017-12-07.
 */

public class TAG {
    public String EPC;
    public int Reads;
    public String TIME;

    public TAG(String EPC, int reads) {
        this.EPC = EPC;
        Reads = reads;

        long currentTime = System.currentTimeMillis();
        SimpleDateFormat currentDayTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        TIME = currentDayTime.format(new Date(currentTime));
    }
}
