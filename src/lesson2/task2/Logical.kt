@file:Suppress("UNUSED_PARAMETER")

package lesson2.task2

import lesson1.task1.sqr
import java.lang.Math.*
import kotlin.math.pow
import kotlin.math.sqrt

/**
 * Пример
 *
 * Лежит ли точка (x, y) внутри окружности с центром в (x0, y0) и радиусом r?
 */
fun pointInsideCircle(x: Double, y: Double, x0: Double, y0: Double, r: Double) = sqr(x - x0) + sqr(y - y0) <= sqr(r)

/**
 * Простая (2 балла)
 *
 * Четырехзначное число назовем счастливым, если сумма первых двух ее цифр равна сумме двух последних.
 * Определить, счастливое ли заданное число, вернуть true, если это так.
 */
fun isNumberHappy(number: Int): Boolean {
    val firstPairSum = (number / 1000) + ((number / 100) % 10)
    val secondPairSum = (number % 10) + ((number / 10) % 10)
    return firstPairSum == secondPairSum
}

/**
 * Простая (2 балла)
 *
 * На шахматной доске стоят два ферзя (ферзь бьет по вертикали, горизонтали и диагоналям).
 * Определить, угрожают ли они друг другу. Вернуть true, если угрожают.
 * Считать, что ферзи не могут загораживать друг друга.
 */
fun queenThreatens(x1: Int, y1: Int, x2: Int, y2: Int): Boolean {
    var space1 = x1 - y1
    var space2 = x1 + y1
    return (((space1 == x2 - y2) || (space2 == x2 + y2)) || (x1 == x2) || (y1 == y2))
}

/**
 * Простая (2 балла)
 *
 * Дан номер месяца (от 1 до 12 включительно) и год (положительный).
 * Вернуть число дней в этом месяце этого года по григорианскому календарю.
 */
fun daysInMonth(month: Int, year: Int): Int {
    val so31 = setOf(1, 3, 5, 7, 8, 10, 12)
    val so30 = setOf(4, 6, 9, 11)
    return when {
        (month in so31) -> 31
        (month in so30) -> 30
        ((month == 2) && ((((year % 4) == 0) && ((year % 100) != 0)) || ((year % 400) == 0))) -> 29
        else -> 28
    }
}

/**
 * Простая (2 балла)
 *
 * Проверить, лежит ли окружность с центром в (x1, y1) и радиусом r1 целиком внутри
 * окружности с центром в (x2, y2) и радиусом r2.
 * Вернуть true, если утверждение верно
 */
fun circleInside(
    x1: Double, y1: Double, r1: Double, x2: Double, y2: Double, r2: Double
): Boolean {
    val absx = x2 - x1
    val absy = y2 - y1
    return sqrt(absx.pow(2) + absy.pow(2)) <= r2 - r1
}

/**
 * Средняя (3 балла)
 *
 * Определить, пройдет ли кирпич со сторонами а, b, c сквозь прямоугольное отверстие в стене со сторонами r и s.
 * Стороны отверстия должны быть параллельны граням кирпича.
 * Считать, что совпадения длин сторон достаточно для прохождения кирпича, т.е., например,
 * кирпич 4 х 4 х 4 пройдёт через отверстие 4 х 4.
 * Вернуть true, если кирпич пройдёт
 */
fun brickPasses(a: Int, b: Int, c: Int, r: Int, s: Int): Boolean {
    val numbers = listOf(a, b, c)
    val min = numbers.min()
    val medium = a + b + c - (numbers.maxOrNull() ?: 0) - min
    return ((min <= r) && (min <= s)) && ((medium <= r) || (medium <= s))
}
