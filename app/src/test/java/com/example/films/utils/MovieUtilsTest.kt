package com.example.films.utils

import org.junit.Assert.assertEquals
import org.junit.Test

class MovieUtilsTest {

    @Test
    fun testFormatPlaytime() {
        assertEquals("Playtime Empty", "", formatPlaytime(0))
        assertEquals("Playtime 1 Day", "1d", formatPlaytime(1440))
        assertEquals("Playtime 1 hour", "1h", formatPlaytime(60))
        assertEquals("Playtime Minutes", "59m", formatPlaytime(59))
        assertEquals("Playtime Day and Minute", "1d 1m", formatPlaytime(1441))
        assertEquals("Playtime Day and Hour", "1d 1h", formatPlaytime(1500))
        assertEquals("Playtime Day, Hour and Minute", "1d 1h 1m", formatPlaytime(1501))
        assertEquals("Playtime Days, Hours and Minutes", "2d 2h 3m", formatPlaytime(3003))
    }
}