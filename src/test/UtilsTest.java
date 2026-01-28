package test;

import app.Utils;
import org.junit.Test;

import static org.junit.Assert.*;

public class UtilsTest {

    @Test
    public void timeToSeconds() {
        int result = Utils.hhmmssToSeconds("01:30:45");
        assertEquals(5445, result);
    }

    @Test
    public void timeToSeconds_noHours() {
        int result = Utils.hhmmssToSeconds("00:30:00");
        assertEquals(1800, result);
    }

    @Test
    public void timeToSeconds_onlySeconds() {
        int result = Utils.hhmmssToSeconds("00:00:45");
        assertEquals(45, result);
    }

    @Test
    public void secondsToTime() {
        String result = Utils.secondsToHHMMSS(5445);
        assertEquals("01:30:45", result);
    }

    @Test
    public void secondsToTime_zero() {
        String result = Utils.secondsToHHMMSS(0);
        assertEquals("00:00:00", result);
    }

    @Test
    public void secondsToTime_onlySeconds() {
        String result = Utils.secondsToHHMMSS(45);
        assertEquals("00:00:45", result);
    }

    @Test
    public void secondsToTime_oneHour() {
        String result = Utils.secondsToHHMMSS(3600);
        assertEquals("01:00:00", result);
    }

    @Test
    public void convertBackAndForth() {
        String time = "02:15:30";
        int seconds = Utils.hhmmssToSeconds(time);
        String result = Utils.secondsToHHMMSS(seconds);
        assertEquals(time, result);
    }
}
