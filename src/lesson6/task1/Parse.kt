@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson6.task1

import lesson2.task2.daysInMonth
import java.lang.StringBuilder

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
fun dateStrToDigit(str: String): String {
    val parts = str.split(" ")
    if (parts.size != 3) return ""
    val number: Int
    val part = parts[1]
    number = when {
        (part == "января") -> 1
        (part == "февраля") -> 2
        (part == "марта") -> 3
        (part == "апреля") -> 4
        (part == "мая") -> 5
        (part == "июня") -> 6
        (part == "июля") -> 7
        (part == "августа") -> 8
        (part == "сентября") -> 9
        (part == "октября") -> 10
        (part == "ноября") -> 11
        (part == "декабря") -> 12
        else -> return ""
    }
    return when {
        (parts[0].toInt() == 0) || (parts[2].toInt() == 0) -> ""
        parts[0].toInt() > daysInMonth(number, parts[2].toInt()) -> ""
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
    val part = parts[1]
    when {
        (part == "01") -> number.append("января")
        (part == "02") -> number.append("февраля")
        (part == "03") -> number.append("марта")
        (part == "04") -> number.append("апреля")
        (part == "05") -> number.append("мая")
        (part == "06") -> number.append("июня")
        (part == "07") -> number.append("июля")
        (part == "08") -> number.append("августа")
        (part == "09") -> number.append("сентября")
        (part == "10") -> number.append("октября")
        (part == "11") -> number.append("ноября")
        (part == "12") -> number.append("декабря")
        else -> return ""
    }
    return when {
        (parts[0].toInt() == 0) || (parts[2].toInt() == 0) -> ""
        parts[0].toInt() > daysInMonth(part.toInt(), parts[2].toInt()) -> ""
        else -> String.format("%d $number %d", parts[0].toInt(), parts[2].toInt())
    }
}

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
    val s = "+-() "
    val c = "1234567890"
    var plus = false
    val result = StringBuilder()
    for (i in 0 until phone.length) {
        if (phone[i] == s[0]) {
            plus = true
            if (result.isNotEmpty()) return ""
        }
        if (!((phone[i] in s) || (phone[i] in c))) return ""
        if (((phone[i] == s[0]) || (phone[i] == s[2])) && (phone.length > 1)) if (phone[i + 1] !in c) return ""
        if (phone[i] in c) result.append(phone[i])
    }
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
    val s = " %-"
    val c = "1234567890"
    var maximum = 0
    val res = StringBuilder()
    for (ch in jumps) {
        if (!((ch in s) || (ch in c))) return -1
        if (ch in c) {
            res.append(ch)
            if (res.toString().toInt() > maximum) maximum = res.toString().toInt()
        } else res.clear()
    }
    if (maximum == 0) return -1
    return maximum
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
    val jump = jumps.removeRange(jumps.length - 2, jumps.length) //новая строка нужна для перебора символов в ней
    val s = " %"                                                       //и стравнением jumps[ch + 2] с (+ и -)
    val k = "-+"
    val c = "1234567890"
    var maximum = 0
    val res = StringBuilder()
    for (ch in jump.indices) {
        val symbol = jumps[ch + 2]
        if (!((jump[ch] in s) || (jump[ch] in c) || (jump[ch] in k))) return -1
        if (jump[ch] in c) {
            res.append(jump[ch])
            if ((res.toString().toInt() > maximum) && (symbol in k)) maximum = res.toString().toInt()
        } else res.clear()
    }
    if (maximum == 0) return -1
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
    val s = "1234567890"
    val h = "+-"
    val chiffre = StringBuilder()
    val setChiffres = mutableListOf<Int>()
    val setPM = mutableListOf<String>()
    var chiffreCount = 0
    var expressionCount = 0
    for (i in expression) {
        if (!((i in s) || (i in h) || (i.toString() == " "))) throw IllegalArgumentException()
        if (i in s) {
            chiffre.append(i)
            expressionCount = 0
        }
        if ((i.toString() == " ") && (chiffre.isNotEmpty())) {
            setChiffres.add(chiffre.toString().toInt())
            chiffreCount += 1
            chiffre.clear()
        }
        if ((i in h) && (setChiffres.size != 0)) {
            setPM.add(i.toString())
            chiffreCount = 0
            expressionCount += 1
        } else if ((i in h) && (setChiffres.size == 0)) throw IllegalArgumentException()
        if ((chiffreCount > 1) || (expressionCount > 1)) throw IllegalArgumentException()
    }
    if (chiffre.isNotEmpty()) {
        setChiffres.add(chiffre.toString().toInt())
        chiffreCount += 1
    }
    if ((chiffreCount > 1) || (expressionCount > 1)) throw IllegalArgumentException()
    var result = setChiffres[0]
    setPM.add(" ")
    for (i in 1 until setChiffres.size) {
        if (setPM[i - 1] == "+") result += setChiffres[i]
        if (setPM[i - 1] == "-") result -= setChiffres[i]
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
fun firstDuplicateIndex(str: String): Int = TODO()

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
fun mostExpensive(description: String): String = TODO()

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
fun fromRoman(roman: String): Int = TODO()

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
