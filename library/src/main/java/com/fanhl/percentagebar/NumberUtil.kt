package com.fanhl.percentagebar

import java.text.DecimalFormat

object NumberUtil {
    /**
     * 百分比
     *
     * 0.12341234 -> 12%
     */
    fun perc(percentage: Float): String {
        return DecimalFormat("##%").format(percentage)
    }
}