package com.fanhl.percentagebar.sample

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.fanhl.percentagebar.PercentageBar
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        root.apply {
            (getChildAt(childCount - 1) as? PercentageBar)?.textHintProvider = object : PercentageBar.TextHintProvider {
                override fun createTextHint(percentage: Float): String {
                    return "Custom textHint ---$percentage---"
                }
            }
        }
        initData()
    }

    private fun initData() {
        val random = Random()
        root.apply {
            (0 until childCount).map { getChildAt(it) as? PercentageBar }.forEach {
                it?.percentage = random.nextFloat()
            }
        }
    }
}
