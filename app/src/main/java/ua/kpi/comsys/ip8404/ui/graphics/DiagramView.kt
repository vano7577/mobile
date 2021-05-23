package ua.kpi.comsys.ip8404.ui.graphics

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.View
import ua.kpi.comsys.ip8404.ui.model.Chunk
import kotlin.math.max
import kotlin.math.min

class DiagramView : View {
    private val strokeWidth by lazy {
        min(height, width) / 4f
    }

    private val paint by lazy {
        Paint().also {
            it.style = Paint.Style.STROKE
            it.strokeWidth = strokeWidth
        }
    }

    private val rect by lazy {
        val yCenter = height / 2f
        val xCenter = width / 2f
        val side = min(width, height) / 2f
        val bias = strokeWidth / 2f

        RectF(
            xCenter - side + bias,
            yCenter - side + bias,
            xCenter + side - bias,
            yCenter + side - bias
        )
    }
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(
        context: Context,
        attrs: AttributeSet,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr)

    var chunks: List<Chunk>? = null

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        var angle = 0f
        chunks?.forEach {
            paint.color = it.color
            val curAngle = percentToAngle(it.percent)
            canvas?.drawArc(rect, angle, curAngle, false, paint)
            angle += curAngle
        }
    }

    private fun percentToAngle(percent: Float) = percent * 360f / 100f
}