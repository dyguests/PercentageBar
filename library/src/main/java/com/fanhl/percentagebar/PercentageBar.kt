package com.fanhl.percentagebar

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View

/**
 * 一个百分比条
 *
 * @author fanhl
 */
class PercentageBar @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var textPaint = TextPaint().apply {
        color = Color.WHITE
    }

    private var foregroundDrawable: Drawable? = null

    var percentage: Float = 0f

    init {

        val a = context.obtainStyledAttributes(attrs, R.styleable.PercentageBar, defStyleAttr, R.style.Widget_Percentage_Bar)

        foregroundDrawable = a.getDrawable(R.styleable.PercentageBar_foregroundDrawable) ?: ContextCompat.getDrawable(context, R.drawable.img_percentage_bar_foreground_default)

        percentage = a.getFloat(R.styleable.PercentageBar_percentage, .5f)
        val textSize = a.getDimension(R.styleable.PercentageBar_textSize, context.resources.getDimension(R.dimen.percentage_text_size_default))

        a.recycle()


        textPaint.textSize = textSize
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val validWidth = width - paddingLeft - paddingRight
        val validHeight = height - paddingTop - paddingBottom

        val saveCount = canvas?.save() ?: return
        canvas.translate(paddingLeft.toFloat(), paddingRight.toFloat())

        drawValid(canvas, validWidth, validHeight)

        canvas.restoreToCount(saveCount)

    }

    private fun drawValid(canvas: Canvas, validWidth: Int, validHeight: Int) {
        canvas.drawColor(Color.BLACK)
        foregroundDrawable?.apply {
            setBounds(
                0,
                0,
                (validWidth * percentage).toInt(),
                validHeight
            )
            draw(canvas)
        }
        canvas.drawText(createTextHint(), (validWidth * percentage), validHeight / 2f, textPaint)
    }

    private fun createTextHint() = NumberUtil.perc(percentage)
}