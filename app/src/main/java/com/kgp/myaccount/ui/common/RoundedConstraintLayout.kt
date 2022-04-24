package com.kgp.myaccount.ui.common

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.res.use
import com.kgp.myaccount.R

class RoundConstraintLayout : ConstraintLayout {

    private var cornerRadius = -1f
    private var borderWidth = 0f

    var clippedBackgroundColor = 0
        set(value) {
            field = value
            invalidate()
        }
    var borderColor = 0
        set(value) {
            field = value
            invalidate()
        }

    private val borderPath = Path()
    private val borderPaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
    }
    private val cornerOval = RectF()

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        loadAttributes(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        loadAttributes(context, attrs)
    }

    private fun loadAttributes(context: Context, attrs: AttributeSet) {
        context.obtainStyledAttributes(attrs, R.styleable.RoundConstraintLayout).use {
            cornerRadius = it.getDimension(R.styleable.RoundConstraintLayout_cornerRadius, -1f)
            borderWidth = it.getDimension(R.styleable.RoundConstraintLayout_borderWidth, 0f)
            borderColor = it.getColor(R.styleable.RoundConstraintLayout_borderColor, Color.TRANSPARENT)
            clippedBackgroundColor = it.getColor(R.styleable.RoundConstraintLayout_clippedBackgroundColor, Color.TRANSPARENT)
        }
    }

    override fun dispatchDraw(canvas: Canvas) {
        configurePath(width.toFloat(), height.toFloat())

        if (cornerRadius >= 0) {
            canvas.clipPath(borderPath)
        }

        if (clippedBackgroundColor != Color.TRANSPARENT) {
            canvas.drawColor(clippedBackgroundColor)
        }

        super.dispatchDraw(canvas)

        borderPaint.color = borderColor
        borderPaint.strokeWidth = borderWidth
        canvas.drawPath(borderPath, borderPaint)
    }

    private fun configurePath(width: Float, height: Float) {
        if (cornerOval.right == width && cornerOval.bottom == height) {
            return
        }

        cornerOval.right = width
        cornerOval.bottom = height

        val cornerRadiusArray = FloatArray(8) { cornerRadius }

        borderPath.rewind()
        borderPath.addRoundRect(cornerOval, cornerRadiusArray, Path.Direction.CW)
    }
}