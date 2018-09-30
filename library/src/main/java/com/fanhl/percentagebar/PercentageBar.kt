package com.fanhl.percentagebar

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.support.v4.content.ContextCompat
import android.support.v4.math.MathUtils
import android.text.TextPaint
import android.util.AttributeSet
import android.view.View
import kotlin.math.roundToInt

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
        set(value) {
            if (field == value) {
                return
            }
            field = value
            requestLayout()
        }

    /** 提示文字与百分比条的间距 */
    private var textPadding: Float = 0f
        set(value) {
            if (field == value) {
                return
            }
            field = value
            requestLayout()
        }

    /**
     * 这是用来自定义提示文字
     */
    var textHintProvider: TextHintProvider? = null
        set(value) {
            if (field == value) {
                return
            }
            field = value
            requestLayout()
        }
    private var defaultTextHintProvider = DefaultTextHintProvider()

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
        val textColor = a.getColor(R.styleable.PercentageBar_textColor, ContextCompat.getColor(context, R.color.percentage_text_color_default))

        textPadding = a.getDimension(R.styleable.PercentageBar_textPadding, context.resources.getDimension(R.dimen.percentage_text_padidng_default))

        percentage = a.getFloat(R.styleable.PercentageBar_percentage, .5f).let { MathUtils.clamp(it, 0f, 1f) }

        a.recycle()

        textPaint.apply {
            this.textSize = textSize
            this.color = textColor
        }
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        val textHint = createTextHint()
        textPaint.getTextBounds(textHint, 0, textHint.length, textBounds)
        textCenterYOffset = -((textPaint.descent() + textPaint.ascent()) / 2)

        val desiredWidth = textBounds.width()
        val desiredHeight = maxOf(textBounds.height(), barHeight.roundToInt())

        val widthMode = View.MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = View.MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = View.MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = View.MeasureSpec.getSize(heightMeasureSpec)

        //Measure Width
        val width = if (widthMode == View.MeasureSpec.EXACTLY) {
            //Must be this size
            widthSize
        } else if (widthMode == View.MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            Math.min(desiredWidth, widthSize)
        } else {
            //Be whatever you want
            textBounds.width()
        }

        //Measure Height
        val height = if (heightMode == View.MeasureSpec.EXACTLY) {
            //Must be this size
            heightSize
        } else if (heightMode == View.MeasureSpec.AT_MOST) {
            //Can't be bigger than...
            Math.min(desiredHeight, heightSize)
        } else {
            //Be whatever you want
            textBounds.height()
        }

        //MUST CALL THIS
        setMeasuredDimension(width, height)
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
                (validHeight / 2f - barHeight / 2f).roundToInt(),
                barWidthPercent.roundToInt(),
                (validHeight / 2f + barHeight / 2f).roundToInt()
            )
            draw(canvas)
        }
        canvas.drawText(createTextHint(), barWidthPercent + textPadding, validHeight / 2f + textCenterYOffset, textPaint)
    }

    private fun createTextHint() = (textHintProvider ?: defaultTextHintProvider).createTextHint(percentage)

    interface TextHintProvider {
        fun createTextHint(percentage: Float): String
    }

    class DefaultTextHintProvider : TextHintProvider {
        override fun createTextHint(percentage: Float): String {
            return NumberUtil.perc(percentage)
        }
    }
}