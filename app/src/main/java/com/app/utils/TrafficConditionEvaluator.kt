package com.app.utils


import kotlin.math.exp
import kotlin.math.max
import kotlin.math.min

class MembershipFunction {
    companion object {
        fun gaussian(x: Double, mean: Double, sigma: Double): Double {
            return exp(-((x - mean) * (x - mean)) / (2 * sigma * sigma))
        }

        fun triangular(x: Double, a: Double, b: Double, c: Double): Double {
            return max(0.0, min((x - a) / (b - a), (c - x) / (c - b)))
        }

        fun trapezoidal(x: Double, a: Double, b: Double, c: Double, d: Double): Double {
            return max(0.0, min(min((x - a) / (b - a), 1.0), (d - x) / (d - c)))
        }
    }
}

class ConsequentLayer(numRules: Int, numInputs: Int) {
    private val consequents: List<Triple<DoubleArray, DoubleArray, Double>>

    init {
        consequents = List(numRules) {
            val a = DoubleArray(numInputs) { Math.random() }
            val b = DoubleArray(numInputs) { Math.random() }
            val c = Math.random()
            Triple(a, b, c)
        }
    }

    fun evaluate(fuzzifiedInputs: DoubleArray): DoubleArray {
        return consequents.map { (a, b, c) ->
            a.zip(fuzzifiedInputs).sumOf { (ai, fi) -> ai * fi } +
                    b.zip(fuzzifiedInputs.map { it * it }.toDoubleArray()).sumOf { (bi, fi) -> bi * fi } + c
        }.toDoubleArray()
    }
}

class OutputLayer(private val numRules: Int) {
    fun defuzzify(ruleOutputs: DoubleArray, firingStrengths: DoubleArray): Double {
        val weightedSum = ruleOutputs.zip(firingStrengths).sumOf { (ro, fs) -> ro * fs }
        val totalWeight = firingStrengths.sum()
        return weightedSum / totalWeight
    }

    fun evaluate(consequentOutputs: DoubleArray, firingStrengths: DoubleArray): Double {
        return defuzzify(consequentOutputs, firingStrengths)
    }
}

class TrafficConditionEvaluator {
    fun evaluateTrafficCondition(speed: Double, density: Double, motionDetected: Boolean): String {
        val trafficSpeed = when {
            speed in 40.0..60.0 -> "Normal"
            speed in 20.0..40.0 -> "Abnormal"
            speed < 20.0 -> "Heavy"
            else -> "Unknown"
        }

        val trafficDensity = when {
            density in 10.0..20.0 -> "Normal"
            density in 20.0..30.0 -> "Abnormal"
            density > 30.0 -> "Heavy"
            else -> "Unknown"
        }

        val trafficMotion = if (motionDetected) "Detected" else "Not Detected"

        return when {
            trafficSpeed == "Normal" && trafficDensity == "Normal" && trafficMotion == "Detected" -> "Normal Traffic"
            trafficSpeed == "Abnormal" && trafficDensity == "Abnormal" && trafficMotion == "Detected" -> "Abnormal Traffic"
            trafficSpeed == "Heavy" || trafficDensity == "Heavy" || trafficMotion == "Not Detected" -> "Heavy Traffic"
            else -> "Unknown Traffic Condition"
        }
    }
}

// Usage Example
fun main() {
    val speed = 35.0
    val density = 25.0
    val motionDetected = true

    val evaluator = TrafficConditionEvaluator()
    val trafficCondition = evaluator.evaluateTrafficCondition(speed, density, motionDetected)

    println("Traffic Condition: $trafficCondition")

    // Example of using MembershipFunction, ConsequentLayer, and OutputLayer
    val x = 50.0
    val mean = 40.0
    val sigma = 10.0
    val gaussianValue = MembershipFunction.gaussian(x, mean, sigma)
    println("Gaussian Membership Value: $gaussianValue")

    val numRules = 3
    val numInputs = 2
    val consequentLayer = ConsequentLayer(numRules, numInputs)
    val fuzzifiedInputs = doubleArrayOf(0.5, 0.8)
    val consequentOutputs = consequentLayer.evaluate(fuzzifiedInputs)
    println("Consequent Outputs: ${consequentOutputs.joinToString(", ")}")

    val outputLayer = OutputLayer(numRules)
    val firingStrengths = doubleArrayOf(1.0, 1.0, 1.0)
    val finalOutput = outputLayer.evaluate(consequentOutputs, firingStrengths)
    println("Final Output: $finalOutput")
}
