package ua.kpi.comsys.ip8404.ui.model

import android.graphics.PointF
import kotlin.math.min

data class Graph(
        var width: Float,
        var height: Float,
        val arrows: Boolean = true
) {
    val left = PointF(0f, height / 2f)
    val right = PointF(width, height / 2f)
    val top = PointF(width / 2f, 0f)
    val bottom = PointF(width / 2f, height)
    val center = PointF(width / 2f, height / 2f)
    val unit = min(width, height) / 8f

    fun xToGraph(x: Float) = center.x + x * unit
    fun yToGraph(y: Float) = center.y - y * unit
}