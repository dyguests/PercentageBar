package com.fanhl.percentagebar

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
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
        set(value) {
            if (field == value) {
                return
            }
            field = value
            requestLayout()
        }

    private var barHeight: Float = 0f

    /** 提示文字与百分比条的间距 */
    private var textPadding: Float = 0f

    /** 文字垂直居中的偏移值 */
    private var textCenterYOffset: Float = 0f

    /** 存放尺寸 */
    private var textBounds = Rect()

    var percentage: Float = 0f
        set(value) {
            if (field == value) {
                return
            }
            field = value
            requestLayout()
        }

    init {

        val a = context.obtainStyledAttributes(attrs, R.styleable.PercentageBar, defStyleAttr, R.style.Widget_Percentage_Bar)

        foregroundDrawable = a.getDrawable(R.styleable.PercentageBar_foregroundDrawable) ?: ContextCompat.getDrawable(context, R.drawable.img_percentage_bar_foreground_default)

        barHeight = a.getDimension(R.styleable.PercentageBar_barHeight, context.resources.getDimension(R.dimen.percentage_bar_height_default))

        val textSize = a.getDimension(R.styleable.PercentageBar_textSize, context.resources.getDimension(R.dimen.percentage_text_size_default))

        textPadding = a.getDimension(R.styleable.PercentageBar_textPadding, context.resources.getDimension(R.dimen.percentage_text_padidng_default))

        percentage = a.getFloat(R.styleable.PercentageBar_percentage, .5f)

        a.recycle()


        textPaint.textSize = textSize
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val textHint = createTextHint()
        textPaint.getTextBounds(textHint, 0, textHint.length, textBounds)
        textCenterYOffset = -((textPaint.descent() + textPaint.ascent()) / 2)
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
        val barWidthTotal = validWidth - textBounds.width() - textPadding
        val barWidthPercent = barWidthTotal * percentage

        foregroundDrawable?.apply {
            setBounds(
                0,
                (validHeight / 2f - barHeight / 2f).toInt(),
                barWidthPercent.toInt(),
                (validHeight / 2f + barHeight / 2f).toInt()
            )
            draw(canvas)
        }
        canvas.drawText(createTextHint(), barWidthPercent + textPadding, validHeight / 2f + textCenterYOffset, textPaint)
    }

    private fun createTextHint() = NumberUtil.perc(percentage)
}