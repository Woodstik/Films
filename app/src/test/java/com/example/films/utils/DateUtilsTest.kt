package com.example.films.utils

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.*

class DateUtilsTest {

    @Test
    fun testFormatReleaseDate() {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, 8)
        calendar.set(Calendar.MONTH, 0)
        calendar.set(Calendar.YEAR, 2019)
        assertEquals("Release format", "January 8, 2019", formatReleaseDate(calendar.time))
    }

    @Test
    fun testFormatReleaseDateShort() {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, 8)
        calendar.set(Calendar.MONTH, 9)
        calendar.set(Calendar.YEAR, 2019)
        assertEquals("Release format", "10/8/2019", formatReleaseDateShort(calendar.time))
    }

    @Test
    fun testFormatDate() {
        assertEquals("Empty format", "", formatDate(Date(), ""))
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, 8)
        calendar.set(Calendar.MONTH, 9)
        calendar.set(Calendar.YEAR, 2019)
        assertEquals("Just Year", "2019", formatDate(Date(), "yyyy"))
    }

    @Test
    fun formatPostTime() {
        var calendar = Calendar.getInstance().apply { add(Calendar.MILLISECOND, -500) }
        assertEquals("Created 1 minute ago", "1 min", formatPostTime(calendar.time))
        calendar = Calendar.getInstance().apply { add(Calendar.MINUTE, -13) }
        assertEquals("Created 13 minutes ago", "13 mins", formatPostTime(calendar.time))
        calendar = Calendar.getInstance().apply { add(Calendar.HOUR, -4) }
        assertEquals("Created 4 hours ago", "4 hrs", formatPostTime(calendar.time))
        calendar = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -1) }
        assertEquals("Created 1 day ago", "1 day", formatPostTime(calendar.time))
        calendar = Calendar.getInstance().apply { add(Calendar.DAY_OF_YEAR, -6) }
        assertEquals("Created 6 days ago", "6 days", formatPostTime(calendar.time))
        calendar = Calendar.getInstance().apply { add(Calendar.WEEK_OF_YEAR, -1) }
        assertEquals("Created 1 week ago", "1 wk", formatPostTime(calendar.time))
        calendar = Calendar.getInstance().apply { add(Calendar.WEEK_OF_YEAR, -3) }
        assertEquals("Created 3 weeks ago", "3 wk", formatPostTime(calendar.time))
        calendar = Calendar.getInstance().apply { add(Calendar.MONTH, -1) }
        assertEquals("Created 1 month ago", "1 month", formatPostTime(calendar.time))
        calendar = Calendar.getInstance().apply { add(Calendar.MONTH, -3) }
        assertEquals("Created 3 months ago", "3 months", formatPostTime(calendar.time))
        calendar = Calendar.getInstance().apply { add(Calendar.YEAR, -1) }
        assertEquals("Created 1 year ago", "1 yr", formatPostTime(calendar.time))
        calendar = Calendar.getInstance().apply { add(Calendar.YEAR, -2) }
        assertEquals("Created 2 years ago", "2 yr", formatPostTime(calendar.time))
    }
}
