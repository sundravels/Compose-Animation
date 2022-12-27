package com.sundravels.composeanimation.ui

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import com.sundravels.composeanimation.ui.theme.*
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun WaveView() {

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val infiniteTransition = rememberInfiniteTransition()
        val degrees = infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 360f,
            animationSpec = infiniteRepeatable(tween(5000, easing = LinearEasing))
        )
        OuterArc()
        InnerCircle(degrees.value)
        Surface(modifier = Modifier.size(278.dp), shape = CircleShape, color = Color.Transparent) {
            Waves()
            Waves(timeSpec = 10000, waveWidth = 800)
            Waves(timeSpec = 15000, waveWidth = 1000)
        }
        Bubbles()
        Bubbles(radius = 40f, addOrPlus = false, tweenSpec = 7000, initialValue = 360f, targetValue = 0f)
        Bubbles(radius = 30f, xOffsetPercentage = 0.5f, yOffsetPercentage =  0.2f, addOrPlus = false, tweenSpec = 9000)
        Bubbles(radius = 20f, xOffsetPercentage = 0.8f, yOffsetPercentage =  0.2f, addOrPlus = false, tweenSpec = 13000)
        Bubbles(radius = 30f, xOffsetPercentage = 0.1f, yOffsetPercentage =  0.2f, tweenSpec = 15000, initialValue = 360f, targetValue = 0f)
        Bubbles(radius = 15f, xOffsetPercentage = 0.1f, yOffsetPercentage =  0.5f, tweenSpec = 18000)
        Bubbles(radius = 20f, xOffsetPercentage = 0.8f, yOffsetPercentage =  0.2f, tweenSpec = 21000, initialValue = 360f, targetValue = 0f)
    }


}

@Composable
private fun OuterArc(){
    Canvas(modifier = Modifier.size(300.dp)){
        drawArc(color= Purple700,
            startAngle = 0f,
            sweepAngle = 360f,
            style = Stroke(width = 15f),
            useCenter = false)
    }
}
@Composable
private fun InnerCircle(degrees:Float=0f) {
    Canvas(modifier = Modifier.size(278.dp)) {
        val circleRadius = size.minDimension / 2
        val centerOffset = Offset(circleRadius, circleRadius)

        drawCircle(
            color = Purple500,
            center = centerOffset,
            radius = circleRadius
        )


        val angleInRadians = degrees * PI / 180F
        val x =
            ((circleRadius / 10) * cos(angleInRadians) + centerOffset.x + (circleRadius / 2)).toFloat()
        val y = ((circleRadius / 10) * sin(angleInRadians) + centerOffset.y + 100f).toFloat()
        drawCircle(
            color = Purple500,
            center = Offset(x, y),
            radius = 10f
        )

    }
}

@Composable
private fun Waves(
    timeSpec: Int = 5000,
    waveWidth: Int = 600,
) {

    val infiniteTransition = rememberInfiniteTransition()

    val yCurve = 125f


    val dx = infiniteTransition.animateFloat(
        initialValue = 1f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(tween(timeSpec, easing = LinearEasing))
    )

    val path = Path()
    Canvas(modifier = Modifier.size(waveWidth.dp)) {
        val x = size.width / 2
        val y = size.height * 0.4f

        drawPath(path = path, color = WhiteTransparent)
        path.reset()
        path.moveTo(-size.width + (size.width * dx.value), y)
        for (i in 0 until 3) {
            path.relativeQuadraticBezierTo(dx1 = x / 2, dy1 = -yCurve, dx2 = x, dy2 = 0.0f)
            path.relativeQuadraticBezierTo(dx1 = x / 2, dy1 = yCurve, dx2 = x, dy2 = 0.0f)
        }

        path.lineTo(size.width, size.height)
        path.lineTo(0f, size.height)
    }
}

@Composable
private fun Bubbles(
    xOffsetPercentage:Float=0.38f,
    yOffsetPercentage:Float=0.60f,
    radius:Float =  50f,
    initialValue:Float = 0f,
    targetValue:Float = 360f,
    tweenSpec:Int = 5000,
    addOrPlus:Boolean=true
){
    val infiniteTransition = rememberInfiniteTransition()
    val degrees = infiniteTransition.animateFloat(
        initialValue = initialValue,
        targetValue = targetValue,
        animationSpec = infiniteRepeatable(tween(tweenSpec, easing = LinearEasing))
    )
    Canvas(modifier = Modifier.size(278.dp)) {
        val circleRadius = size.minDimension / 2
        val centerOffset = Offset(circleRadius, circleRadius)

        val angleInRadians = degrees.value * PI /180F

        when(addOrPlus){
            true ->{
                drawCircle(color= Purple500,
                    center = Offset(((circleRadius * 0.1f) * cos(angleInRadians) + (centerOffset.x + (centerOffset.x * xOffsetPercentage))).toFloat(),
                        ((circleRadius * 0.1f) * sin(angleInRadians) + (centerOffset.y + (centerOffset.y * yOffsetPercentage))).toFloat()),
                    radius = radius)
            }
            else ->{
                drawCircle(color= Purple500,
                    center = Offset(((circleRadius * 0.1f) * cos(angleInRadians) + (centerOffset.x - (centerOffset.x * xOffsetPercentage))).toFloat(),
                        ((circleRadius * 0.1f) * sin(angleInRadians) + (centerOffset.y + (centerOffset.y * yOffsetPercentage))).toFloat()),
                    radius = radius)
            }
        }

    }
}