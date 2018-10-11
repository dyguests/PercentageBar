package com.fanhl.percentagebar

import org.junit.Assert.assertEquals
import org.junit.Test

class NumberUtilTest {
    @Test
    fun perc_test() {
        mapOf(
            1f to "100%",
            2f to "200%",
            .5f to "50%",
            .55f to "55%",
            .555f to "56%",
            .554f to "55%",
            -1f to "-100%",
            -.5f to "-50%",
            -.55f to "-55%",
            -.555f to "-56%",
            0f to "0%"
        ).forEach { t, u ->
            assertEquals(NumberUtil.perc(t), u)
        }
    }
}
