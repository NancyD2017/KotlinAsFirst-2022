@file:Suppress("UNUSED_PARAMETER", "ConvertCallChainIntoSequence")

package lesson7.task1

import java.io.File
import java.io.FileWriter
import java.lang.StringBuilder
import lesson3.task1.digitNumber
import kotlin.math.pow

// Урок 7: работа с файлами
// Урок интегральный, поэтому его задачи имеют сильно увеличенную стоимость
// Максимальное количество баллов = 55
// Рекомендуемое количество баллов = 20
// Вместе с предыдущими уроками (пять лучших, 3-7) = 55/103

/**
 * Пример
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * Вывести его в выходной файл с именем outputName, выровняв по левому краю,
 * чтобы длина каждой строки не превосходила lineLength.
 * Слова в слишком длинных строках следует переносить на следующую строку.
 * Слишком короткие строки следует дополнять словами из следующей строки.
 * Пустые строки во входном файле обозначают конец абзаца,
 * их следует сохранить и в выходном файле
 */
fun alignFile(inputName: String, lineLength: Int, outputName: String) {
    val writer = FileWriter(outputName)
    var currentLineLength = 0
    writer.use {
        fun append(word: String) {
            if (currentLineLength > 0) {
                if (word.length + currentLineLength >= lineLength) {
                    writer.write(System.lineSeparator())
                    currentLineLength = 0
                } else {
                    writer.write(" ")
                    currentLineLength++
                }
            }
            writer.write(word)
            currentLineLength += word.length
        }
        for (line in File(inputName).readLines()) {
            if (line.isEmpty()) {
                writer.write(System.lineSeparator())
                if (currentLineLength > 0) {
                    writer.write(System.lineSeparator())
                    currentLineLength = 0
                }
                continue
            }
            for (word in line.split(Regex("\\s+"))) {
                append(word)
            }
        }
    }
}

/**
 * Простая (8 баллов)
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * Некоторые его строки помечены на удаление первым символом _ (подчёркивание).
 * Перенести в выходной файл с именем outputName все строки входного файла, убрав при этом помеченные на удаление.
 * Все остальные строки должны быть перенесены без изменений, включая пустые строки.
 * Подчёркивание в середине и/или в конце строк значения не имеет.
 */
fun deleteMarked(inputName: String, outputName: String) {
    val writer = FileWriter(outputName)
    writer.use {
        for (line in File(inputName).readLines()) {
            if (line.isNotEmpty()) {
                if (line[0] != '_') {
                    writer.write(line)
                    writer.write(System.lineSeparator())
                }
            } else writer.write(System.lineSeparator())
        }
    }
}

/**
 * Средняя (14 баллов)
 *
 * Во входном файле с именем inputName содержится некоторый текст.
 * На вход подаётся список строк substrings.
 * Вернуть ассоциативный массив с числом вхождений каждой из строк в текст.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 */
fun countSubstrings(inputName: String, substrings: List<String>): Map<String, Int> {
    val result = mutableMapOf<String, Int>()
    val tInInputName = File(inputName).readText().lowercase()
    for (word in substrings) {
        val w = word.lowercase()
        result[word] = 0
        for (each in tInInputName.windowed(w.length)) {
            if (each == w) result[word] = result[word]!! + 1
        }
    }
    return result
}


/**
 * Средняя (12 баллов)
 *
 * В русском языке, как правило, после букв Ж, Ч, Ш, Щ пишется И, А, У, а не Ы, Я, Ю.
 * Во входном файле с именем inputName содержится некоторый текст на русском языке.
 * Проверить текст во входном файле на соблюдение данного правила и вывести в выходной
 * файл outputName текст с исправленными ошибками.
 *
 * Регистр заменённых букв следует сохранять.
 *
 * Исключения (жюри, брошюра, парашют) в рамках данного задания обрабатывать не нужно
 *
 */
fun sibilants(inputName: String, outputName: String) {
    val writer = FileWriter(outputName)
    val right = mapOf('ы' to 'и', 'Ы' to 'И', 'ю' to 'у', 'Ю' to 'У', 'я' to 'а', 'Я' to 'А')
    val toughs = "жчшщЖЧШЩ"
    writer.use {
        for (line in File(inputName).readLines()) {
            for (i in 0 until line.length) {
                val mistake = line[i]
                if (i > 0) {
                    when {
                        ((line[i - 1] in toughs) && (line[i] in "ыЫюЮяЯ")) -> writer.write(right[mistake].toString())
                        else -> writer.write(line[i].toString())
                    }
                } else writer.write(mistake.toString())
            }
            writer.write(System.lineSeparator())
        }
    }
}

/**
 * Средняя (15 баллов)
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по центру
 * относительно самой длинной строки.
 *
 * Выравнивание следует производить путём добавления пробелов в начало строки.
 *
 *
 * Следующие правила должны быть выполнены:
 * 1) Пробелы в начале и в конце всех строк не следует сохранять.
 * 2) В случае невозможности выравнивания строго по центру, строка должна быть сдвинута в ЛЕВУЮ сторону
 * 3) Пустые строки не являются особым случаем, их тоже следует выравнивать
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых)
 *
 */
fun centerFile(inputName: String, outputName: String) {
    val writer = FileWriter(outputName)
    var maxLen = 0
    val lines = File(inputName).readLines()
    for (line in lines) if (line.trim().length > maxLen) maxLen = line.trim().length
    writer.use {
        for (line in lines) {
            val t = line.trim()
            if (t.length < maxLen) {
                writer.write(" ".repeat((maxLen - t.length) / 2))
                writer.write(t)
            } else writer.write(t)
            writer.write(System.lineSeparator())
        }
    }
}

/**
 * Сложная (20 баллов)
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 * Вывести его в выходной файл с именем outputName, выровняв по левому и правому краю относительно
 * самой длинной строки.
 * Выравнивание производить, вставляя дополнительные пробелы между словами: равномерно по всей строке
 *
 * Слова внутри строки отделяются друг от друга одним или более пробелом.
 *
 * Следующие правила должны быть выполнены:
 * 1) Каждая строка входного и выходного файла не должна начинаться или заканчиваться пробелом.
 * 2) Пустые строки или строки из пробелов трансформируются в пустые строки без пробелов.
 * 3) Строки из одного слова выводятся без пробелов.
 * 4) Число строк в выходном файле должно быть равно числу строк во входном (в т. ч. пустых).
 *
 * Равномерность определяется следующими формальными правилами:
 * 5) Число пробелов между каждыми двумя парами соседних слов не должно отличаться более, чем на 1.
 * 6) Число пробелов между более левой парой соседних слов должно быть больше или равно числу пробелов
 *    между более правой парой соседних слов.
 *
 * Следует учесть, что входной файл может содержать последовательности из нескольких пробелов  между слвоами. Такие
 * последовательности следует учитывать при выравнивании и при необходимости избавляться от лишних пробелов.
 * Из этого следуют следующие правила:
 * 7) В самой длинной строке каждая пара соседних слов должна быть отделена В ТОЧНОСТИ одним пробелом
 * 8) Если входной файл удовлетворяет требованиям 1-7, то он должен быть в точности идентичен выходному файлу
 */
fun alignFileByWidth(inputName: String, outputName: String) {
    val writer = FileWriter(outputName)
    var maxLen = 0                //нужен для определения максимальной длины строки с "нормальным" количеством пробелов
    var newLine = String() //для каждой строки из текста строит строку с "нормальным" количеством пробелов
    val text = mutableListOf<String>()    //строит текст из newLine-ов
    val spaceCountByLineList = mutableListOf<Int>() //ищет "нормальное" количество пробелов для строк
    var spacesCount = 0                 //ищет "нормальное" количество пробелов в одной строке
    for (line in File(inputName).readLines()) {
        for (i in 0 until line.length) {
            if (i + 1 < line.length) {
                if (!((line[i] == line[i + 1]) && (line[i] == ' '))) newLine += line[i].toString()
            } else newLine += line[line.length - 1].toString()
        }
        newLine = newLine.trim()
        if (newLine.length > maxLen) maxLen = newLine.length
        text.add("$newLine\n")
        for (element in newLine) if (element == ' ') spacesCount++
        newLine = String()
        spaceCountByLineList.add(spacesCount)
        spacesCount = 0
    }
    writer.use {
        for (line in 0 until text.size) {
            if (text[line].trim().length < maxLen) {
                var spacesLeft =
                    maxLen - text[line].length + 1  //считает недостаток пробелов в строке, "1" появляется за счёт \n
                spacesCount = spaceCountByLineList[line]   //считает недостающее количество пробелов для каждой из строк
                val spacesNumber =
                    if (spacesCount > 0) spacesLeft / spacesCount else 0 //считает, на сколько пробелов нужно заменить каждый из пробелов
                for (i in text[line].indices) {
                    if ((text[line][i] == ' ') && (spacesLeft > 0)) {
                        if (spacesNumber * spacesCount < spacesLeft) {
                            writer.write(" ".repeat(spacesNumber + 2))              //добавляет к spacesNumber один
                            spacesLeft -= (spacesNumber + 1)                           //пробел который был и один,
                            spacesCount -= 1                                           // образовавшийся из-за остатка
                        } else {
                            writer.write(" ".repeat(spacesNumber + 1))              //добавляет к spacesNumber один
                            spacesLeft -= spacesNumber                                 //пробел который был
                            spacesCount -= 1
                        }
                    } else if ((text[line][i] == ' ') && (spacesLeft == 0)) {
                        writer.write(" ")                                       //оставляет по 1 пробелу, когда это нужно
                    } else writer.write(text[line][i].toString())                  //или записывает непробельный символ line[i]
                }
            } else writer.write(text[line])                       //если у строки maxLen то она не изменяется
        }
    }
}

/**
 * Средняя (14 баллов)
 *
 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * Вернуть ассоциативный массив, содержащий 20 наиболее часто встречающихся слов с их количеством.
 * Если в тексте менее 20 различных слов, вернуть все слова.
 * Вернуть ассоциативный массив с числом слов больше 20, если 20-е, 21-е, ..., последнее слова
 * имеют одинаковое количество вхождений (см. также тест файла input/onegin.txt).
 *
 * Словом считается непрерывная последовательность из букв (кириллических,
 * либо латинских, без знаков препинания и цифр).
 * Цифры, пробелы, знаки препинания считаются разделителями слов:
 * Привет, привет42, привет!!! -привет?!
 * ^ В этой строчке слово привет встречается 4 раза.
 *
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 * Ключи в ассоциативном массиве должны быть в нижнем регистре.
 *
 */
fun top20Words(inputName: String): Map<String, Int> = TODO()

/**
 * Средняя (14 баллов)
 *
 * ��еализовать транслитерацию текста из входного файла в выходной файл посредством ди��амически задаваемых правил.

 * Во входном файле с именем inputName содержится некоторый текст (в том числе, и на русском языке).
 *
 * В ассоциативном массиве dictionary содержится словарь, в котором некоторым символам
 * ставится в соответствие строчка из символов, например
 * mapOf('з' to "zz", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "yy", '!' to "!!!")
 *
 * Необходимо вывести в итоговый файл с именем outputName
 * содержимое текста с заменой всех символов из словаря на соответствующие им строки.
 *
 * При этом регистр символов в словаре должен игнорироваться,
 * но при выводе символ в верхнем регистре отображается в строку, начинающуюся с символа в верхнем регистре.
 *
 * Пример.
 * Входной текст: Здравствуй, мир!
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Пример 2.
 *
 * Входной текст: Здравствуй, мир!
 * Словарь: mapOf('з' to "zZ", 'р' to "r", 'д' to "d", 'й' to "y", 'М' to "m", 'и' to "YY", '!' to "!!!")
 *
 * заменяется на
 *
 * Выходной текст: Zzdrавствуy, mир!!!
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun transliterate(inputName: String, dictionary: Map<Char, String>, outputName: String) {
    TODO()
}

/**
 * Средняя (12 баллов)
 *
 * Во входном файле с именем inputName имеется словарь с одним словом в каждой строчке.
 * Выбрать из данного словаря наиболее длинное слово,
 * в котором все буквы разные, например: Неряшливость, Четырёхдюймовка.
 * Вывести его в выходной файл с именем outputName.
 * Если во входном файле имеется несколько слов с одинаковой длиной, в которых все буквы разные,
 * в выходной файл следует вывести их все через запятую.
 * Регистр букв игнорировать, то есть буквы е и Е считать одинаковыми.
 *
 * Пример входного файла:
 * Карминовый
 * Боязливый
 * Некрасивый
 * Остроумный
 * БелогЛазый
 * ФиолетОвый

 * Соответствующий выходной файл:
 * Карминовый, Некрасивый
 *
 * Обратите внимание: данная функция не имеет возвращаемого значения
 */
fun chooseLongestChaoticWord(inputName: String, outputName: String) {
    val setOfLongestChaoticWord = mutableSetOf<Char>()
    val listOfLongestChaoticWord = mutableListOf<Char>()
    val writer = FileWriter(outputName)
    val s = StringBuilder()
    var maxLen = 0
    var isLetterSame = false
    for (line in File(inputName).readLines()) {
        val l = line.lowercase()
        listOfLongestChaoticWord.clear()
        listOfLongestChaoticWord.addAll(l.toList())
        setOfLongestChaoticWord.addAll(l.toSet())
        if (setOfLongestChaoticWord.size != listOfLongestChaoticWord.size) isLetterSame = true
        if ((setOfLongestChaoticWord.size > maxLen) && (!(isLetterSame))) {
            maxLen = setOfLongestChaoticWord.size
            s.clear().append(line)
        } else if ((setOfLongestChaoticWord.size == maxLen) && (!(isLetterSame))) {
            s.append(", $line")
        }
        setOfLongestChaoticWord.clear()
        isLetterSame = false
    }
    writer.use { writer.write(s.toString()) }
}

/**
 * Сложная (22 балла)
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе элементы текстовой разметки следующих типов:
 * - *текст в курсивном начертании* -- курсив
 * - **текст в полужирном начертании** -- полужирный
 * - ~~зачёркнутый текст~~ -- зачёркивание
 *
 * Следует вывести в выходной файл этот же текст в формате HTML:
 * - <i>текст в курсивном начертании</i>
 * - <b>текст в полужирном начертании</b>
 * - <s>зачёркнутый текст</s>
 *
 * Кроме того, все абзацы исходного текста, отделённые друг от друга пустыми строками, следует обернуть в теги <p>...</p>,
 * а весь текст целиком в теги <html><body>...</body></html>.
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 * Отдельно следует заметить, что открывающая последовательность из трёх звёздочек (***) должна трактоваться как "<b><i>"
 * и никак иначе.
 *
 * При решении этой и двух следующих задач полезно прочитать статью Википедии "Стек".
 *
 * Пример входного файла:
Lorem ipsum *dolor sit amet*, consectetur **adipiscing** elit.
Vestibulum lobortis, ~~Est vehicula rutrum *suscipit*~~, ipsum ~~lib~~ero *placerat **tortor***,

Suspendisse ~~et elit in enim tempus iaculis~~.
 *
 * Соответствующий выходной файл:
<html>
<body>
<p>
Lorem ipsum <i>dolor sit amet</i>, consectetur <b>adipiscing</b> elit.
Vestibulum lobortis. <s>Est vehicula rutrum <i>suscipit</i></s>, ipsum <s>lib</s>ero <i>placerat <b>tortor</b></i>.
</p>
<p>
Suspendisse <s>et elit in enim tempus iaculis</s>.
</p>
</body>
</html>
 *
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlSimple(inputName: String, outputName: String) {
    TODO()
}

/**
 * Сложная (23 балла)
 *
 * Реализовать транслитерацию текста в заданном формате разметки в формат разметки HTML.
 *
 * Во входном файле с именем inputName содержится текст, содержащий в себе набор вложенных друг в друга списков.
 * Списки бывают двух типов: нумерованные и ненумерованные.
 *
 * Каждый элемент ненумерованного списка начинается с новой строки и символа '*', каждый элемент нумерованного списка --
 * с новой строки, числа и точки. Каждый элемент вложенного списка начинается с отступа из пробелов, на 4 пробела большего,
 * чем список-родитель. Максимально глубина вложенности списков может достигать 6. "Верхние" списки файла начинются
 * прямо с начала строки.
 *
 * Следует вывести этот же текст в выходной файл в формате HTML:
 * Нумерованный список:
 * <ol>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ol>
 *
 * Ненумерованный список:
 * <ul>
 *     <li>Раз</li>
 *     <li>Два</li>
 *     <li>Три</li>
 * </ul>
 *
 * Кроме того, весь текст целиком следует обернуть в теги <html><body><p>...</p></body></html>
 *
 * Все остальные части исходного текста должны остаться неизменными с точностью до наборов пробелов и переносов строк.
 *
 * Пример входного файла:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
 * Утка по-пекински
 * Утка
 * Соус
 * Салат Оливье
1. Мясо
 * Или колбаса
2. Майонез
3. Картофель
4. Что-то там ещё
 * Помидоры
 * Фрукты
1. Бананы
23. Яблоки
1. Красные
2. Зелёные
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 *
 *
 * Соответствующий выходной файл:
///////////////////////////////начало файла/////////////////////////////////////////////////////////////////////////////
<html>
<body>
<p>
<ul>
<li>
Утка по-пекински
<ul>
<li>Утка</li>
<li>Соус</li>
</ul>
</li>
<li>
Салат Оливье
<ol>
<li>Мясо
<ul>
<li>Или колбаса</li>
</ul>
</li>
<li>Майонез</li>
<li>Картофель</li>
<li>Что-то там ещё</li>
</ol>
</li>
<li>Помидоры</li>
<li>Фрукты
<ol>
<li>Бананы</li>
<li>Яблоки
<ol>
<li>Красные</li>
<li>Зелёные</li>
</ol>
</li>
</ol>
</li>
</ul>
</p>
</body>
</html>
///////////////////////////////конец файла//////////////////////////////////////////////////////////////////////////////
 * (Отступы и переносы строк в примере добавлены для наглядности, при решении задачи их реализовывать не обязательно)
 */
fun markdownToHtmlLists(inputName: String, outputName: String) {
    TODO()
}

/**
 * Очень сложная (30 баллов)
 *
 * Реализовать преобразования из двух предыдущих задач одновременно над одним и тем же файлом.
 * Следует помнить, что:
 * - Списки, отделённые друг от друга пустой строкой, являются разными и должны оказаться в разных параграфах выходного файла.
 *
 */
fun markdownToHtml(inputName: String, outputName: String) {
    TODO()
}

/**
 * Средняя (12 баллов)
 *
 * Вывести в выходной файл процесс умножения столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 111):
19935
 *    111
--------
19935
+ 19935
+19935
--------
2212785
 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 * Нули в множителе обрабатывать так же, как и остальные цифры:
235
 *  10
-----
0
+235
-----
2350
 *
 */
fun printMultiplicationProcess(lhv: Int, rhv: Int, outputName: String) {
    val writer = FileWriter(outputName)
    val digitNumberLR = digitNumber(rhv * lhv)
    writer.use {
        writer.write("${" ".repeat(digitNumberLR - digitNumber(lhv) + 1)}$lhv\n*")
        writer.write("${" ".repeat(digitNumberLR - digitNumber(rhv))}$rhv\n")
        writer.write("${"-".repeat(digitNumberLR + 1)}\n")
        var i = 0
        var r = rhv
        while (i < digitNumber(rhv)) {
            if (i == 0) {
                writer.write(" ".repeat((digitNumberLR + 1) - digitNumber((r % 10) * lhv)))
                writer.write("${(r % 10) * lhv}\n")
            } else {
                writer.write("+${" ".repeat(digitNumberLR - digitNumber((r % 10) * lhv) - i)}")
                writer.write("${(r % 10) * lhv}\n")
            }
            r /= 10
            i++
        }
        writer.write("${"-".repeat(digitNumberLR + 1)}\n")
        writer.write(" ${lhv * rhv}")
    }
}


/**
 * Сложная (25 баллов)
 *
 * Вывести в выходной файл процесс деления столбиком числа lhv (> 0) на число rhv (> 0).
 *
 * Пример (для lhv == 19935, rhv == 22):
19935 | 22
-198     906
----
13
-0
--
135
-132
----
3

 * Используемые пробелы, отступы и дефисы должны в точности соответствовать примеру.
 *
 */
fun printDivisionProcess(lhv: Int, rhv: Int, outputName: String) {
    val writer = FileWriter(outputName)
    val divisionResult = lhv / rhv
    val newDivisionResult = mutableListOf<String>()
    newDivisionResult += divisionResult.toString()
    val newLhv = mutableListOf<Int>()
    newLhv.add(lhv)
    val newDivided = mutableListOf<String>()
    val minusChiffre = mutableListOf<Int>()
    val spaceNewDivided = mutableListOf<Int>()
    val allSpaces = mutableListOf<Int>()
    var firstLen = 0
    for (j in 0 until digitNumber(divisionResult)) {
        if ((digitNumber(lhv) == digitNumber(divisionResult)) || (j != 0)) {
            minusChiffre += newDivisionResult[j].toInt() / 10.0.pow(newDivisionResult[j].length - 1).toInt() * rhv
            if (j == 0) newDivided += lhv.toString()
            else if (newDivided[j - 1].toInt().toString()
                    .slice(0..digitNumber(minusChiffre[j - 1]) - 1) != minusChiffre[j - 1].toString()
            ) newDivided += (newLhv[j] / 10.0.pow(newDivisionResult[j].length - 1)
                .toInt()).toString() else newDivided += "0${
                (newLhv[j] / 10.0.pow(newDivisionResult[j].length - 1).toInt())
            }"
            newLhv += (newLhv[j] - minusChiffre[j] * 10.0.pow(digitNumber(newLhv[j]) - digitNumber(minusChiffre[j]))
                .toInt())
        } else {
            minusChiffre += divisionResult / 10.0.pow(digitNumber(divisionResult) - 1).toInt() * rhv
            newDivided += lhv.toString()
            newLhv += (newLhv[j] - minusChiffre[j] * 10.0.pow(digitNumber(newLhv[j]) - digitNumber(minusChiffre[j]))
                .toInt())
        }
        newDivisionResult.add(newDivisionResult[j].slice(1..newDivisionResult[j].length - 1))
    }
    val times = digitNumber(divisionResult)
    var i = 0
    writer.use {
        val spaces = if (digitNumber(divisionResult) - 1 + digitNumber(minusChiffre[0]) == digitNumber(lhv))
            1 else 0
        writer.write("${" ".repeat(spaces)}$lhv | $rhv\n")
        firstLen = ("${" ".repeat(spaces)}$lhv | ").length
        while (i < times) {
            if (i == 0) {
                if (minusChiffre[0] == 0 && digitNumber(lhv) > 2) {
                    writer.write("${" ".repeat(digitNumber(lhv) - 2)}-${minusChiffre[i]}   ")
                    writer.write("$divisionResult\n${"-".repeat(digitNumber(lhv))}\n")
                } else {
                    writer.write("-${minusChiffre[i]}")
                    writer.write(" ".repeat(firstLen - 1 - digitNumber(minusChiffre[i])))
                    writer.write("$divisionResult\n${"-".repeat(digitNumber(minusChiffre[i]) + 1)}\n")
                }
            } else {
                spaceNewDivided += if (newDivided[i] != "00" && !newDivided[i].matches(Regex("""0\d+""")))
                    spaces + digitNumber(lhv) - digitNumber(newLhv[i])
                else if (newDivided[i].matches(Regex("""0\d+""")) && spaceNewDivided.size > 0)
                    spaceNewDivided[i - 2] + 1 else if (newDivided[i].matches(Regex("""0\d+"""))
                    && spaceNewDivided.size == 0
                ) spaces + digitNumber(minusChiffre[i - 1]) - 1 else spaceNewDivided[i - 2] + 1
                allSpaces += if (digitNumber(minusChiffre[i]) < newDivided[i].length) spaceNewDivided[i - 1]
                else spaceNewDivided[i - 1] - 1
                writer.write("${" ".repeat(spaceNewDivided[i - 1])}${newDivided[i]}\n")
                if (minusChiffre[i] == 0 && newDivided[i].toInt() > 0) writer.write(
                    "${
                        " ".repeat
                            (allSpaces[i - 1] + newDivided[i].length - 2)
                    }-${minusChiffre[i]}\n"
                )
                else writer.write("${" ".repeat(allSpaces[i - 1])}-${minusChiffre[i]}\n")
                writer.write(
                    "${" ".repeat(allSpaces[i - 1])}${
                        "-".repeat(
                            maxOf(
                                digitNumber(minusChiffre[i]) + 1,
                                newDivided[i].length
                            )
                        )
                    }\n"
                )
            }
            i++
        }
        val odds = lhv - rhv * divisionResult
        if (lhv != 1) writer.write("${" ".repeat(firstLen - 3 - digitNumber(odds))}$odds")
        else writer.write("${" ".repeat(digitNumber(lhv) - 1)} $odds")
    }
}



