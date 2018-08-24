package logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ElapsedTimeTest {

    @Test
    void testConstructorHourMinute() {
        int hours = 1;
        int minutes = 45;
        ElapsedTime elapsedTime = new ElapsedTime(hours, minutes);
        assertEquals(hours * 60 + minutes, elapsedTime.getMinutes());
    }

    @Test
    void testConstructorMinute() {
        int minutes = 112;
        ElapsedTime elapsedTime = new ElapsedTime(minutes);
        assertEquals(minutes, elapsedTime.getMinutes());
    }

    @Test
    void testPlus() {
        int hours1 = 2;
        int minutes1 = 45;
        int minutes2 = 33;

        ElapsedTime elapsedTime1 = new ElapsedTime(hours1, minutes1);
        ElapsedTime elapsedTime2 = new ElapsedTime(minutes2);
        ElapsedTime summedTime = elapsedTime1.plus(elapsedTime2);
        assertEquals(hours1*60 + minutes1 + minutes2, summedTime.getMinutes());
    }

    @Test
    void toString_1Hour100Minutes() {
        int hours = 1;
        int minutes = 100;
        ElapsedTime elapsedTime = new ElapsedTime(hours, minutes);
        assertEquals("2:40", elapsedTime.toString());
    }

    @Test
    void toString_33Hours11Minutes() {
        int hours = 33;
        int minutes = 11;
        ElapsedTime elapsedTime = new ElapsedTime(hours, minutes);
        assertEquals("1:09:11", elapsedTime.toString());
    }

    @Test
    void toString_zeroHours3Minutes() {
        int hours = 0;
        int minutes = 3;
        ElapsedTime elapsedTime = new ElapsedTime(hours, minutes);
        assertEquals("0:03", elapsedTime.toString());
    }

    @Test
    void testParse() {
        String inputString1 = "2:31";
        try {
            ElapsedTime elapsedTime = ElapsedTime.parse(inputString1);
            assertEquals(151, elapsedTime.getMinutes());
        } catch (ElapsedTimeFormatException e) {
            fail("The string " + inputString1 + " should be parsable into an ElapsedTime");
        }

        String inputString2 = "12";
        try {
            ElapsedTime elapsedTime = ElapsedTime.parse(inputString2);
            assertEquals(12, elapsedTime.getMinutes());
        } catch (ElapsedTimeFormatException e) {
            fail("The string " + inputString2 + " should be parsable into an ElapsedTime");
        }

        final String INPUT_STRING_3 = "cannot parse this";
        assertThrows(ElapsedTimeFormatException.class, () -> {
            ElapsedTime elapsedTime = ElapsedTime.parse(INPUT_STRING_3);
        });

        final String INPUT_STRING_4 = "321:232";
        assertThrows(ElapsedTimeFormatException.class, () -> {
            ElapsedTime elapsedTime = ElapsedTime.parse(INPUT_STRING_4);
        });

        final String INPUT_STRING_5 = "1:92";
        assertThrows(ElapsedTimeFormatException.class, () -> {
            ElapsedTime elapsedTime = ElapsedTime.parse(INPUT_STRING_5);
        });
    }
}
