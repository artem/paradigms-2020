# Тесты к курсу «Парадигмы программирования»

[Условия домашних заданий](http://www.kgeorgiy.info/courses/paradigms/homeworks.html)


## Домашнее задание 1. Обработка ошибок

Модификации
 * *Базовая*
    * Класс `ExpressionParser` должен реализовывать интерфейс
        [Parser](java/expression/exceptions/Parser.java)
    * Классы `CheckedAdd`, `CheckedSubtract`, `CheckedMultiply`,
        `CheckedDivide` и `CheckedNegate` должны реализовывать интерфейс
        [TripleExpression](java/expression/TripleExpression.java)
    * Нельзя использовать типы `long` и `double`
    * Нельзя использовать методы классов `Math` и `StrictMath`
    * [Исходный код тестов](java/expression/exceptions/ExceptionsTest.java)
 * *PowLog2*
    * Дополнительно реализуйте унарные операции:
        * `log2` – логарифм по уснованию 2, `log2 10` равно 3;
        * `pow2` – два в степени, `pow2 4` равно 16.
    * [Исходный код тестов](java/expression/exceptions/ExceptionsPowLog2Test.java)
