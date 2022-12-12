@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson6.task1

import lesson2.task2.daysInMonth
import kotlin.math.pow
import kotlin.text.StringBuilder

// Урок 6: разбор строк, исключения
// Максимальное количество баллов = 13
// Рекомендуемое количество баллов = 11
// Вместе с предыдущими уроками (пять лучших, 2-6) = 40/54

/**
 * Пример
 *
 * Время представлено строкой вида "11:34:45", содержащей часы, минуты и секунды, разделённые двоеточием.
 * Разобрать эту строку и рассчитать количество секунд, прошедшее с начала дня.
 */
fun timeStrToSeconds(str: String): Int {
    val parts = str.split(":")
    var result = 0
    for (part in parts) {
        val number = part.toInt()
        result = result * 60 + number
    }
    return result
}

/**
 * Пример
 *
 * Дано число n от 0 до 99.
 * Вернуть его же в виде двухсимвольной строки, от "00" до "99"
 */
fun twoDigitStr(n: Int) = if (n in 0..9) "0$n" else "$n"

/**
 * Пример
 *
 * Дано seconds -- время в секундах, прошедшее с начала дня.
 * Вернуть текущее время в виде строки в формате "ЧЧ:ММ:СС".
 */
fun timeSecondsToStr(seconds: Int): String {
    val hour = seconds / 3600
    val minute = (seconds % 3600) / 60
    val second = seconds % 60
    return String.format("%02d:%02d:%02d", hour, minute, second)
}

/**
 * Пример: консольный ввод
 */
fun main() {
    println("Введите время в формате ЧЧ:ММ:СС")
    val line = readLine()
    if (line != null) {
        val seconds = timeStrToSeconds(line)
        if (seconds == -1) {
            println("Введённая строка $line не соответствует формату ЧЧ:ММ:СС")
        } else {
            println("Прошло секунд с начала суток: $seconds")
        }
    } else {
        println("Достигнут <конец файла> в процессе чтения строки. Программа прервана")
    }
}


/**
 * Средняя (4 балла)
 *
 * Дата представлена строкой вида "15 июля 2016".
 * Перевести её в цифровой формат "15.07.2016".
 * День и месяц всегда представлять двумя цифрами, например: 03.04.2011.
 * При неверном формате входной строки вернуть пустую строку.
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30.02.2009) считается неверными
 * входными данными.
 */
val monthList = listOf(
    "января", "февраля", "марта", "апреля", "мая", "июня", "июля", "августа", "сентября", "октября", "ноября",
    "декабря"
)

fun dateStrToDigit(str: String): String {
    val parts = str.split(" ")
    if (parts.size != 3) return ""
    val number: Int
    val month = parts[1]
    val day = parts[0].toIntOrNull()
    val year = parts[2].toIntOrNull()
    if (month in monthList) number = monthList.indexOf(month) + 1 else return ""
    return when {
        !isDigitalValid(parts, day, year, month) -> ""
        day!! > daysInMonth(number, year!!) -> ""
        else -> (String.format("%02d.%02d.%d", parts[0].toInt(), number, parts[2].toInt()))
    }
}


/**
 * Средняя (4 балла)
 *
 * Дата представлена строкой вида "15.07.2016".
 * Перевести её в строковый формат вида "15 июля 2016".
 * При неверном формате входной строки вернуть пустую строку
 *
 * Обратите внимание: некорректная с точки зрения календаря дата (например, 30 февраля 2009) считается неверными
 * входными данными.
 */

fun dateDigitToStr(digital: String): String {
    val parts = digital.split(".")
    if (parts.size != 3) return ""
    val number = StringBuilder()
    val part = parts[1].toIntOrNull()
    val day = parts[0].toIntOrNull()
    val year = parts[2].toIntOrNull()
    if (part in 1..12) number.append(monthList[part!! - 1]) else return ""
    return when {
        !isDigitalValid(parts, day, year, part.toString()) -> ""
        day!! > daysInMonth(part, year!!) -> ""
        else -> String.format("%d $number %d", day, year)
    }
}

fun isDigitalValid(parts: List<String>, day: Int?, year: Int?, part: String): Boolean =
    !((day == null) || (year == null) || ((day <= 0) || (year <= 0)))

/**
 * Средняя (4 балла)
 *
 * Номер телефона задан строкой вида "+7 (921) 123-45-67".
 * Префикс (+7) может отсутствовать, код города (в скобках) также может отсутствовать.
 * Может присутствовать неограниченное количество пробелов и чёрточек,
 * например, номер 12 --  34- 5 -- 67 -89 тоже следует считать легальным.
 * Перевести номер в формат без скобок, пробелов и чёрточек (но с +), например,
 * "+79211234567" или "123456789" для приведённых примеров.
 * Все символы в номере, кроме цифр, пробелов и +-(), считать недопустимыми.
 * При неверном формате вернуть пустую строку.
 *
 * PS: Дополнительные примеры работы функции можно посмотреть в соответствующих тестах.
 */
fun flattenPhoneNumber(phone: String): String {
    val mySymbols = "+-() "
    var plus = false
    var isBraceNumberCorrect2 = 0
    var isBraceNumberCorrect3 = 0
    val result = StringBuilder()
    for (i in 0 until phone.length) {
        if (phone[i] == mySymbols[2]) isBraceNumberCorrect2 += 1
        if (phone[i] == mySymbols[3]) isBraceNumberCorrect3 += 1
        if (phone[i] == mySymbols[0]) {
            if (plus) return ""
            plus = true
            if (result.isNotEmpty()) return ""
        }
        if (!((phone[i] in mySymbols) || (phone[i] in '0'..'9'))) return ""
        if (((phone[i] == mySymbols[0]) || (phone[i] == mySymbols[2])) && (phone.length > 1))
            if (phone[i + 1] !in '0'..'9') return ""
        if (phone[i] in '0'..'9') result.append(phone[i])
    }
    if (isBraceNumberCorrect2 != isBraceNumberCorrect3) return ""
    if ((plus) && (result.isNotEmpty())) return ("+${result}")
    else if (plus) return ("")
    return if (!(plus)) result.toString()
    else ""
}

/**
 * Средняя (5 баллов)
 *
 * Результаты спортсмена на соревнованиях в прыжках в длину представлены строкой вида
 * "706 - % 717 % 703".
 * В строке могут присутствовать числа, черточки - и знаки процента %, разделённые пробелами;
 * число соответствует удачному прыжку, - пропущенной попытке, % заступу.
 * Прочитать строку и вернуть максимальное присутствующее в ней число (717 в примере).
 * При нарушении формата входной строки или при отсутствии в ней чисел, вернуть -1.
 */
fun bestLongJump(jumps: String): Int {
    if (!Regex("""(\d+ |% |- )+(\d+|%|-)""").matches(jumps)) return -1
    val jumpings = jumps.split(" ")
    val mySymbols = " %-"
    var maximum = 0
    for (jump in jumpings) {
        if (jump.toIntOrNull() is Int) if (jump.toInt() > maximum) maximum = jump.toInt()
        for (i in jump) if ((i !in mySymbols) && (i !in '0'..'9')) return -1
    }
    return if (maximum != 0) maximum else -1
}

/**
 * Сложная (6 баллов)
 *
 * Результаты спортсмена на соревнованиях в прыжках в высоту представлены строкой вида
 * "220 + 224 %+ 228 %- 230 + 232 %%- 234 %".
 * Здесь + соответствует удачной попытке, % неудачной, - пропущенной.
 * Высота и соответствующие ей попытки разделяются пробелом.
 * Прочитать строку и вернуть максимальную взятую высоту (230 в примере).
 * При нарушении формата входной строки, а также в случае отсутствия удачных попыток,
 * вернуть -1.
 */
fun bestHighJump(jumps: String): Int {
    for (j in jumps) if (!((j in '0'..'9') || (j in " +-%"))) return -1
    if (!Regex("""(\d+ (\+|%\+|%{1,2}-|%) )*(\d+ (\+|%\+|%{1,2}-|%))""").matches(jumps)) return -1
    val goodJumps = jumps.split(Regex(""" """))
    var maximum = -1
    for (i in goodJumps.indices step 2) {
        if (goodJumps[i + 1] == "+") if (goodJumps[i].toInt() > maximum) maximum = goodJumps[i].toInt()
    }
    return maximum
}

/**
 * Сложная (6 баллов)
 *
 * В строке представлено выражение вида "2 + 31 - 40 + 13",
 * использующее целые положительные числа, плюсы и минусы, разделённые пробелами.
 * Наличие двух знаков подряд "13 + + 10" или двух чисел подряд "1 2" не допускается.
 * Вернуть значение выражения (6 для примера).
 * Про нарушении формата входной строки бросить исключение IllegalArgumentException
 */
fun plusMinus(expression: String): Int {
    if (!Regex("""(([0-9]+ (\+|-) )*[0-9]+)""").matches(expression)) throw IllegalArgumentException()
    val listOfNumbers = expression.split(" ").filter { it.matches(Regex("[0-9]+")) }
    val listOfPlusMinus = expression.split(" ").filter { it.matches(Regex("(\\+|-)")) }
    var result = listOfNumbers[0].toInt()
    for (i in 0 until listOfPlusMinus.size) {
        if (listOfPlusMinus[i] == "+") result += listOfNumbers[i + 1].toInt()
        if (listOfPlusMinus[i] == "-") result -= listOfNumbers[i + 1].toInt()
    }
    return result
}

/**
 * Сложная (6 баллов)
 *
 * Строка состоит из набора слов, отделённых друг от друга одним пробелом.
 * Определить, имеются ли в строке повторяющиеся слова, идущие друг за другом.
 * Слова, отличающиеся только регистром, считать совпадающими.
 * Вернуть индекс начала первого повторяющегося слова, или -1, если повторов нет.
 * Пример: "Он пошёл в в школу" => результат 9 (индекс первого 'в')
 */
fun firstDuplicateIndex(str: String): Int {
    val sets = str.lowercase().split(" ")
    var setOne = sets[0]
    var index = setOne.length
    var isThereDouble = false
    if (sets.size == 1) return -1
    for (i in 1 until sets.size) {
        if (setOne == sets[i]) {
            index -= sets[i].length
            isThereDouble = true
            break
        }
        index += sets[i].length + 1
        setOne = sets[i]
    }
    return if (isThereDouble) index else -1
}

/**
 * Сложная (6 баллов)
 *
 * Строка содержит названия товаров и цены на них в формате вида
 * "Хлеб 39.9; Молоко 62; Курица 184.0; Конфеты 89.9".
 * То есть, название товара отделено от цены пробелом,
 * а цена отделена от названия следующего товара точкой с запятой и пробелом.
 * Вернуть название самого дорогого товара в списке (в примере это Курица),
 * или пустую строку при нарушении формата строки.
 * Все цены должны быть больше нуля либо равны нулю.
 */
fun mostExpensive(description: String): String {
    if (!Regex("""(\S+\s[0-9]+(.[0-9]*)?; )*(\S+\s[0-9]+(.[0-9]*)?)""").matches(description)) return ""
    val productAndPrice = description.split(Regex("(; )|( )"))
    var result = ""
    var maximum = Double.NEGATIVE_INFINITY
    for (i in 0 until productAndPrice.size step 2) {
        if (productAndPrice[i + 1].toDouble() > maximum) {
            maximum = productAndPrice[i + 1].toDouble()
            result = productAndPrice[i]
        }
    }
    return if (maximum != Double.NEGATIVE_INFINITY) result
    else return ""
}

/**
 * Сложная (6 баллов)
 *
 * Перевести число roman, заданное в римской системе счисления,
 * в десятичную систему и вернуть как результат.
 * Римские цифры: 1 = I, 4 = IV, 5 = V, 9 = IX, 10 = X, 40 = XL, 50 = L,
 * 90 = XC, 100 = C, 400 = CD, 500 = D, 900 = CM, 1000 = M.
 * Например: XXIII = 23, XLIV = 44, C = 100
 *
 * Вернуть -1, если roman не является корректным римским числом
 */
fun fromRoman(roman: String): Int {
    if (!Regex("""^M{0,3}(CM|CD|D?C{0,3})?(XC|XL|L?X{0,3})?(IX|IV|V?I{0,3})?$""").matches(roman)) return -1
    if (roman.isBlank()) return -1
    val roman1 = setOf("I", "X", "C", "M")
    val roman5 = setOf("V", "L", "D")
    val roman4 = setOf("IV", "XL", "CD")
    val roman9 = setOf("IX", "XC", "CM")
    var possibleNumber = ""
    var result = 0
    var isSecondDigitUsed = false
    for (i in 0 until roman.length) {
        if (!isSecondDigitUsed) {
            val power1 = roman1.indexOf(roman[i].toString()).toDouble()
            val power5 = roman5.indexOf(roman[i].toString()).toDouble()
            if (i + 1 != roman.length) possibleNumber = (roman[i].toString() + roman[i + 1]) else possibleNumber = ""
            when {
                ((possibleNumber) in roman4) -> {
                    result += 4 * (10.0.pow(roman4.indexOf(possibleNumber).toDouble())).toInt()
                    isSecondDigitUsed = true
                }

                ((possibleNumber) in roman9) -> {
                    result += 9 * (10.0.pow(roman9.indexOf(possibleNumber).toDouble())).toInt()
                    isSecondDigitUsed = true
                }

                ((roman[i].toString()) in roman1) -> result += 10.0.pow(power1).toInt()
                ((roman[i].toString()) in roman5) -> result += 5 * (10.0.pow(power5)).toInt()
            }
        } else isSecondDigitUsed = false
    }
    return result
}

/**
 * Очень сложная (7 баллов)
 *
 * Имеется специальное устройство, представляющее собой
 * конвейер из cells ячеек (нумеруются от 0 до cells - 1 слева направо) и датчик, двигающийся над этим конвейером.
 * Строка commands содержит последовательность команд, выполняемых данным устройством, например +>+>+>+>+
 * Каждая команда кодируется одним специальным символом:
 *	> - сдвиг датчика вправо на 1 ячейку;
 *  < - сдвиг датчика влево на 1 ячейку;
 *	+ - увеличение значения в ячейке под датчиком на 1 ед.;
 *	- - уменьшение значения в ячейке под датчиком на 1 ед.;
 *	[ - если значение под датчиком равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей следующей командой ']' (с учётом вложенности);
 *	] - если значение под датчиком не равно 0, в качестве следующей команды следует воспринимать
 *  	не следующую по порядку, а идущую за соответствующей предыдущей командой '[' (с учётом вложенности);
 *      (комбинация [] имитирует цикл)
 *  пробел - пустая команда
 *
 * Изначально все ячейки заполнены значением 0 и датчик стоит на ячейке с номером N/2 (округлять вниз)
 *
 * После выполнения limit команд или всех команд из commands следует прекратить выполнение последовательности команд.
 * Учитываются все команды, в том числе несостоявшиеся переходы ("[" при значении под датчиком не равном 0 и "]" при
 * значении под датчиком равном 0) и пробелы.
 *
 * Вернуть список размера cells, содержащий элементы ячеек устройства после завершения выполнения последовательности.
 * Например, для 10 ячеек и командной строки +>+>+>+>+ результат должен быть 0,0,0,0,0,1,1,1,1,1
 *
 * Все прочие символы следует считать ошибочными и формировать исключение IllegalArgumentException.
 * То же исключение формируется, если у символов [ ] не оказывается пары.
 * Выход за границу конвейера также следует считать ошибкой и формировать исключение IllegalStateException.
 * Считать, что ошибочные символы и непарные скобки являются более приоритетной ошибкой чем выход за границу ленты,
 * то есть если в программе присутствует некорректный символ или непарная скобка, то должно быть выброшено
 * IllegalArgumentException.
 * IllegalArgumentException должен бросаться даже если ошибочная команда не была достигнута в ходе выполнения.
 *
 */
fun computeDeviceCells(cells: Int, commands: String, limit: Int): List<Int> = TODO()
