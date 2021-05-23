package ua.kpi.comsys.ip8404.ui.graphics

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.minus
import androidx.core.graphics.plus

import ua.kpi.comsys.ip8404.ui.model.Graph

class PloatView : View  {
    private val graph by lazy {
        Graph(width.toFloat(), height.toFloat())
    }
    private val paint_parabola = Paint().also {
        it.color = Color.BLUE
        it.strokeWidth = 5f
        it.style = Paint.Style.FILL
    }
    private val paint_line = Paint().also {
        it.color = Color.BLACK
        it.strokeWidth = 5f
        it.style = Paint.Style.FILL
    }
    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

    constructor(
        context: Context,
        attrs: AttributeSet,
        defStyleAttr: Int
    ) : super(context, attrs, defStyleAttr)

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        drawGraph(canvas)

        val centerY =  height /2f
        val centerX =  width / 2f
        val one = width / 10f
        val points = mutableListOf<Pair<Float, Float>>()
        for (i in -100..100) {
            val x = centerX + i / 20f * one
            val y = centerY - (i * i) / 400f * one
            points.add(x to y)
        }

        canvas?.let {
            for (i in 0 until points.size - 1) {
                it.drawLine(
                        points[i].first,
                        points[i].second,
                        points[i + 1].first,
                        points[i + 1].second,
                        paint_parabola
                )
            }


        }
    }
    private fun drawGraph(canvas: Canvas?) {
        canvas?.let {
            with(graph) {
                it.drawLine(top, bottom, paint_line) // x-axis
                it.drawLine(left, right, paint_line) // y-axis

                if (arrows) {
                    it.drawLine(top, top + PointF(-15f, 30f), paint_line) // arrow y
                    it.drawLine(top, top + PointF(15f, 30f), paint_line)

                    it.drawLine(right, right - PointF(30f, 15f), paint_line) // arrow x
                    it.drawLine(right, right - PointF(30f, -15f), paint_line)
                }

                val unitX = center + PointF(unit, 0f)
                val unitY = center - PointF(0f, unit)

                it.drawLine(unitX + PointF(0f, 15f), unitX + PointF(0f, -15f), paint_line)
                it.drawLine(unitY + PointF(15f, 0f), unitY + PointF(-15f, 0f), paint_line)
            }
        }
    }
    private fun Canvas.drawLine(pointFrom: PointF, pointTo: PointF, paint: Paint) {
        drawLine(pointFrom.x, pointFrom.y, pointTo.x, pointTo.y, paint)
    }
}
