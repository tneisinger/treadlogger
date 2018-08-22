package logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ElapsedTimeTest {

    @Test
    public void testConstructorHourMinute() {
        int hours = 1;
        int minutes = 45;
        ElapsedTime elapsedTime = new ElapsedTime(hours, minutes);
        assertEquals(hours * 60 + minutes, elapsedTime.getMinutes());
    }

    @Test
    public void testConstructorMinute() {
        int minutes = 112;
        ElapsedTime elapsedTime = new ElapsedTime(minutes);
        assertEquals(minutes, elapsedTime.getMinutes());
    }

    @Test
    public void testPlus() {
        int hours1 = 2;
        int minutes1 = 45;
        int minutes2 = 33;

        ElapsedTime elapsedTime1 = new ElapsedTime(hours1, minutes1);
        ElapsedTime elapsedTime2 = new ElapsedTime(minutes2);
        ElapsedTime summedTime = elapsedTime1.plus(elapsedTime2);
        assertEquals(hours1*60 + minutes1 + minutes2, summedTime.getMinutes());
    }

    @Test
    public void testToString() {
        int hours = 1;
        int minutes = 100;
        ElapsedTime elapsedTime = new ElapsedTime(hours, minutes);
        assertEquals("2:40", elapsedTime.toString());


        hours = 33;
        minutes = 11;
        elapsedTime = new ElapsedTime(hours, minutes);
        assertEquals("1:09:11", elapsedTime.toString());


        hours = 0;
        minutes = 3;
        elapsedTime = new ElapsedTime(hours, minutes);
        assertEquals("0:03", elapsedTime.toString());
    }
}
