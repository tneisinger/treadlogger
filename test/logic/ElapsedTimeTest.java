package logic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ElapsedTimeTest {

    @Test
    void ElapsedTime_hoursAndMinutes() {
        int hours = 1;
        int minutes = 45;
        ElapsedTime elapsedTime = new ElapsedTime(hours, minutes);
        assertEquals(hours * 60 + minutes, elapsedTime.getMinutes());
    }

    @Test
    void ElapsedTime_MinutesOnly() {
        int minutes = 112;
        ElapsedTime elapsedTime = new ElapsedTime(minutes);
        assertEquals(minutes, elapsedTime.getMinutes());
    }

    @Test
    void plus() {
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
    void parse_2colon31() throws Exception {
        String inputString1 = "2:31";
        ElapsedTime elapsedTime = ElapsedTime.parse(inputString1);
        assertEquals(151, elapsedTime.getMinutes());
    }

    @Test
    void parse_12() throws Exception {
        String inputString2 = "12";
        ElapsedTime elapsedTime = ElapsedTime.parse(inputString2);
        assertEquals(12, elapsedTime.getMinutes());
    }

    @Test
    void parse_cannotParseThis() {
        String string = "cannotParseThis";
        assertThrows(Exception.class, () -> {
            ElapsedTime elapsedTime = ElapsedTime.parse(string);
        });
    }

    @Test
    void parse_321colon232() {
        String string = "321:232";
        assertThrows(Exception.class, () -> {
            ElapsedTime elapsedTime = ElapsedTime.parse(string);
        });
    }

    @Test
    void parse_1colon92() {
        String string = "1:92";
        assertThrows(Exception.class, () -> {
            ElapsedTime elapsedTime = ElapsedTime.parse(string);
        });
    }
}
