package ru.v1ad54;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

//import static org.junit.jupiter.api.Assertions.*;
import static ru.v1ad54.MyAssertions.*;


/**
 * Структура тестовых данных для параметризованного теста {@link LogicTasksTest#isEvenParametrizedTest(IsEvenTestData)}
 *
 * @param n              - проверяемое число для параметра n
 * @param expectedResult - ожидаемый результат метода isEven
 */
record IsEvenTestData(
        int n,
        boolean expectedResult
) {
}

/**
 * Структура тестовых данных для параметризованного теста {@link LogicTasksTest#checkAccessParametrizedTest(CheckAccessTestData)}
 *
 * @param age            - возраст для параметра age
 * @param expectedResult - ожидаемый результат метода checkAccess
 */
record CheckAccessTestData(
        int age,
        String expectedResult
) {
}

/**
 * Структура тестовых данных для параметризованного теста {@link LogicTasksTest#isPositiveParametrizedTest(IsPositiveTestData)}
 *
 * @param n              - проверяемое число для параметра n
 * @param expectedResult - ожидаемый результат метода isPositive
 */
record IsPositiveTestData(
        int n,
        boolean expectedResult
) {
}

/**
 * Структура тестовых данных для параметризованного теста {@link LogicTasksTest#getGradeParametrizedTest(GetGradeTestData)}
 *
 * @param score          - баллы от 0 до 100 для параметра score
 * @param expectedResult - ожидаемый результат метода getGrade
 */
record GetGradeTestData(
        int score,
        String expectedResult
) {
}


class LogicTasksTest {

    /*====================[Простые тесты]====================*/

    @Test
    @DisplayName("Простые тесты метода isEven")
    void isEvenTest() {
        Random random = new Random();
        int value;

        // Положительные числа
        value = random.nextInt(50) * 2; // случайное четное число
        assertTrue(LogicTasks.isEven(value), String.valueOf(value) + " должно быть четным");
        value = random.nextInt(50) * 2 + 1; // случайное не четное число
        assertFalse(LogicTasks.isEven(value), value + " не должно быть четным");

        // Ноль
        assertTrue(LogicTasks.isEven(0), "0 должен быть четным");

        // Отрицательные числа
        value = -random.nextInt(50) * 2; // случайное четное число
        assertTrue(LogicTasks.isEven(value), value + " должно быть четным");
        value = -random.nextInt(50) * 2 + 1; // случайное не четное число
        assertFalse(LogicTasks.isEven(value), value + " не должно быть четным");
    }

    @Test
    @DisplayName("Простые тесты метода checkAccess")
    void checkAccessTest() {
        Random random = new Random();
        int value;

        // Меньше 18
        value = random.nextInt(0, 18);
        assertEquals("Denied", LogicTasks.checkAccess(value), "Для " + value + " доступ должен быть Denied");

        // Граничное значение 18 (в условии строго больше 'age > 18')
        assertEquals("Denied", LogicTasks.checkAccess(18), "Для 18 доступ должен быть Denied");

        // Больше 18
        value = random.nextInt(19, 100);
        assertEquals("Allowed", LogicTasks.checkAccess(value), "Для " + value + " доступ должен быть Allowed");
    }

    @Test
    @DisplayName("Простые тесты метода isPositive")
    void isPositiveTest() {
        Random random = new Random();
        int value;

        // Положительные числа
        value = random.nextInt(1, 100);
        assertTrue(LogicTasks.isPositive(value), value + " должно быть positive");

        // Ноль (в условии 'больше или равно 0')
        assertTrue(LogicTasks.isPositive(0), "0 должен быть positive");

        // Отрицательные числа
        value = -random.nextInt(1, 100);
        assertFalse(LogicTasks.isPositive(value), value + " не должно быть positive");
    }

    @Test
    @DisplayName("Простые тесты метода getGrade")
    void getGradeTest() {
        Random random = new Random();
        int value;

        // Границы диапазона E (0-20)
        assertEquals("E", LogicTasks.getGrade(0));
        value = random.nextInt(1, 20);
        assertEquals("E", LogicTasks.getGrade(value));
        assertEquals("E", LogicTasks.getGrade(20));

        // Границы диапазона D (21-40)
        assertEquals("D", LogicTasks.getGrade(21));
        value = random.nextInt(22, 40);
        assertEquals("D", LogicTasks.getGrade(value));
        assertEquals("D", LogicTasks.getGrade(40));

        // Границы диапазона C (41-60)
        assertEquals("C", LogicTasks.getGrade(41));
        value = random.nextInt(42, 60);
        assertEquals("C", LogicTasks.getGrade(value));
        assertEquals("C", LogicTasks.getGrade(60));

        // Границы диапазона B (61-80)
        assertEquals("B", LogicTasks.getGrade(61));
        value = random.nextInt(62, 80);
        assertEquals("B", LogicTasks.getGrade(value));
        assertEquals("B", LogicTasks.getGrade(80));

        // Границы диапазона A (81-100)
        assertEquals("A", LogicTasks.getGrade(81));
        value = random.nextInt(82, 100);
        assertEquals("A", LogicTasks.getGrade(value));
        assertEquals("A", LogicTasks.getGrade(100));

//        // Исключение IllegalArgumentException при отрицательных баллах
//        assertThrows(IllegalArgumentException.class, () -> LogicTasks.getGrade(-1),
//                "Должно быть исключение IllegalArgumentException при отрицательных баллах");
//        // Исключение IllegalArgumentException при баллах больше 100
//        assertThrows(IllegalArgumentException.class, () -> LogicTasks.getGrade(101),
//                "Должно быть исключение IllegalArgumentException при баллах больше 100");
        // Если переданное число не входит в границы — вернуть строку Error.
        value = -random.nextInt(1, 100);
        assertEquals("Error", LogicTasks.getGrade(value));
        value = random.nextInt(101, 1000);
        assertEquals("Error", LogicTasks.getGrade(value));
    }


    /*====================[Повторяющиеся тесты]====================*/

    @RepeatedTest(value = 5, name = "тест {currentRepetition} из {totalRepetitions}")
    @DisplayName("Повторяющиеся тесты метода isEven")
    void isEvenRepeatedTest() {
        Random random = new Random();
        int value;

        // Положительные числа
        value = random.nextInt(50) * 2; // случайное четное число
        assertTrue(LogicTasks.isEven(value), String.valueOf(value) + " должно быть четным");
        value = random.nextInt(50) * 2 + 1; // случайное не четное число
        assertFalse(LogicTasks.isEven(value), value + " не должно быть четным");

        // Ноль
        assertTrue(LogicTasks.isEven(0), "0 должен быть четным");

        // Отрицательные числа
        value = -random.nextInt(50) * 2; // случайное четное число
        assertTrue(LogicTasks.isEven(value), value + " должно быть четным");
        value = random.nextInt(50) * 2 + 1; // случайное не четное число
        assertFalse(LogicTasks.isEven(value), value + " не должно быть четным");
    }

    @RepeatedTest(value = 5, name = "тест {currentRepetition} из {totalRepetitions}")
    @DisplayName("Повторяющиеся тесты метода checkAccess")
    void checkAccessRepeatedTest() {
        Random random = new Random();
        int value;

        // Меньше 18
        value = random.nextInt(0, 18);
        assertEquals("Denied", LogicTasks.checkAccess(value), "Для " + value + " доступ должен быть Denied");

        // Граничное значение 18 (в условии строго больше 'age > 18')
        assertEquals("Denied", LogicTasks.checkAccess(18), "Для 18 доступ должен быть Denied");

        // Больше 18
        value = random.nextInt(19, 100);
        assertEquals("Allowed", LogicTasks.checkAccess(value), "Для " + value + " доступ должен быть Allowed");
    }

    @RepeatedTest(value = 5, name = "тест {currentRepetition} из {totalRepetitions}")
    @DisplayName("Повторяющиеся тесты метода isPositive")
    void isPositiveRepeatedTest() {
        Random random = new Random();
        int value;

        // Положительные числа
        value = random.nextInt(1, 100);
        assertTrue(LogicTasks.isPositive(value), value + " должно быть positive");

        // Ноль (в условии 'больше или равно 0')
        assertTrue(LogicTasks.isPositive(0), "0 должен быть positive");

        // Отрицательные числа
        value = -random.nextInt(1, 100);
        assertFalse(LogicTasks.isPositive(value), value + " не должно быть positive");
    }

    @RepeatedTest(value = 5, name = "тест {currentRepetition} из {totalRepetitions}")
    @DisplayName("Повторяющиеся тесты метода getGrade")
    void getGradeRepeatedTest() {
        Random random = new Random();
        int value;

        // Границы диапазона E (0-20)
        assertEquals("E", LogicTasks.getGrade(0));
        value = random.nextInt(1, 20);
        assertEquals("E", LogicTasks.getGrade(value));
        assertEquals("E", LogicTasks.getGrade(20));

        // Границы диапазона D (21-40)
        assertEquals("D", LogicTasks.getGrade(21));
        value = random.nextInt(22, 40);
        assertEquals("D", LogicTasks.getGrade(value));
        assertEquals("D", LogicTasks.getGrade(40));

        // Границы диапазона C (41-60)
        assertEquals("C", LogicTasks.getGrade(41));
        value = random.nextInt(42, 60);
        assertEquals("C", LogicTasks.getGrade(value));
        assertEquals("C", LogicTasks.getGrade(60));

        // Границы диапазона B (61-80)
        assertEquals("B", LogicTasks.getGrade(61));
        value = random.nextInt(62, 80);
        assertEquals("B", LogicTasks.getGrade(value));
        assertEquals("B", LogicTasks.getGrade(80));

        // Границы диапазона A (81-100)
        assertEquals("A", LogicTasks.getGrade(81));
        value = random.nextInt(82, 100);
        assertEquals("A", LogicTasks.getGrade(value));
        assertEquals("A", LogicTasks.getGrade(100));

//        // Исключение IllegalArgumentException при отрицательных баллах
//        assertThrows(IllegalArgumentException.class, () -> LogicTasks.getGrade(-1),
//                "Должно быть исключение IllegalArgumentException при отрицательных баллах");
//        // Исключение IllegalArgumentException при баллах больше 100
//        assertThrows(IllegalArgumentException.class, () -> LogicTasks.getGrade(101),
//                "Должно быть исключение IllegalArgumentException при баллах больше 100");
        // Если переданное число не входит в границы — вернуть строку Error.
        value = -random.nextInt(1, 100);
        assertEquals("Error", LogicTasks.getGrade(value));
        value = random.nextInt(101, 1000);
        assertEquals("Error", LogicTasks.getGrade(value));
    }


    /*====================[Параметризованные тесты]====================*/

    /**
     * Метод предоставляет данные для параметризованного теста isEven
     *
     * @return - возвращает список структур
     */
    static private List<IsEvenTestData> isEvenParametrizedTestData() {
        List<IsEvenTestData> dataList = new ArrayList<>();

        Random random = new Random();

        // Положительные числа
        dataList.add(new IsEvenTestData(random.nextInt(50) * 2, true));  // случайное четное число
        dataList.add(new IsEvenTestData(random.nextInt(50) * 2 + 1, false));  // случайное не четное число

        dataList.add(new IsEvenTestData(0, true));  // Ноль

        // Отрицательные числа
        dataList.add(new IsEvenTestData(-random.nextInt(50) * 2, true));  // случайное четное число
        dataList.add(new IsEvenTestData(-random.nextInt(50) * 2 + 1, false));  // случайное не четное число

        return dataList;
    }

    /**
     * Параметризованный тест метода isEven
     *
     * @param data - структура тестовых данных для параметризованного теста
     */
    @ParameterizedTest
    @MethodSource("isEvenParametrizedTestData")
    @DisplayName("Параметризованный тест метода isEven")
    void isEvenParametrizedTest(IsEvenTestData data) {
        assertEquals(data.expectedResult(), LogicTasks.isEven(data.n()));
    }

    /**
     * Метод предоставляет данные для параметризованного теста checkAccess
     *
     * @return - возвращает список структур
     */
    static private List<CheckAccessTestData> checkAccessParametrizedTestData() {
        List<CheckAccessTestData> dataList = new ArrayList<>();

        Random random = new Random();

        // Меньше 18
        dataList.add(new CheckAccessTestData(random.nextInt(0, 18), "Denied"));

        // Граничное значение 18 (в условии строго больше 'age > 18')
        dataList.add(new CheckAccessTestData(18, "Denied"));

        // Больше 18
        dataList.add(new CheckAccessTestData(random.nextInt(19, 100), "Allowed"));

        return dataList;
    }

    /**
     * Параметризованный тест метода checkAccess
     *
     * @param data - структура тестовых данных для параметризованного теста
     */
    @ParameterizedTest
    @MethodSource("checkAccessParametrizedTestData")
    @DisplayName("Параметризованный тест метода checkAccess")
    void checkAccessParametrizedTest(CheckAccessTestData data) {
        assertEquals(data.expectedResult(), LogicTasks.checkAccess(data.age()));
    }

    /**
     * Метод предоставляет данные для параметризованного теста isPositive
     *
     * @return - возвращает список структур
     */
    static private List<IsPositiveTestData> isPositiveParametrizedTestData() {
        List<IsPositiveTestData> dataList = new ArrayList<>();

        Random random = new Random();

        // Положительные числа
        for(int i = 0; i < 5; i++) {
            dataList.add(new IsPositiveTestData(random.nextInt(1, 100), true));
        }

        // Ноль (в условии 'больше или равно 0')
        dataList.add(new IsPositiveTestData(0, true));

        // Отрицательные числа
        for(int i = 0; i < 5; i++) {
            dataList.add(new IsPositiveTestData(-random.nextInt(1, 100), false));
        }

        return dataList;
    }

    /**
     * Параметризованный тест метода isPositive
     *
     * @param data - структура тестовых данных для параметризованного теста
     */
    @ParameterizedTest
    @MethodSource("isPositiveParametrizedTestData")
    @DisplayName("Параметризованный тест метода isPositive")
    void isPositiveParametrizedTest(IsPositiveTestData data) {
        assertEquals(data.expectedResult(), LogicTasks.isPositive(data.n()));
    }

    /**
     * Метод предоставляет данные для параметризованного теста getGrade
     *
     * @return - возвращает список структур
     */
    static private List<GetGradeTestData> getGradeParametrizedTestData() {
        List<GetGradeTestData> dataList = new ArrayList<>();

        Random random = new Random();

        // Границы диапазона E (0-20)
        dataList.add(new GetGradeTestData(0, "E"));
        dataList.add(new GetGradeTestData(random.nextInt(1, 20), "E"));
        dataList.add(new GetGradeTestData(20, "E"));

        // Границы диапазона D (21-40)
        dataList.add(new GetGradeTestData(21, "D"));
        dataList.add(new GetGradeTestData(random.nextInt(22, 40), "D"));
        dataList.add(new GetGradeTestData(40, "D"));

        // Границы диапазона C (41-60)
        dataList.add(new GetGradeTestData(41, "C"));
        dataList.add(new GetGradeTestData(random.nextInt(42, 60), "C"));
        dataList.add(new GetGradeTestData(60, "C"));

        // Границы диапазона B (61-80)
        dataList.add(new GetGradeTestData(61, "B"));
        dataList.add(new GetGradeTestData(random.nextInt(62, 80), "B"));
        dataList.add(new GetGradeTestData(80, "B"));

        // Границы диапазона A (81-100)
        dataList.add(new GetGradeTestData(81, "A"));
        dataList.add(new GetGradeTestData(random.nextInt(82, 100), "A"));
        dataList.add(new GetGradeTestData(100, "A"));

        // Если переданное число не входит в границы — вернуть строку Error.
        dataList.add(new GetGradeTestData(-random.nextInt(1, 100), "Error"));
        dataList.add(new GetGradeTestData(random.nextInt(101, 1000), "Error"));

        return dataList;
    }

    /**
     * Параметризованный тест метода getGrade
     *
     * @param data - структура тестовых данных для параметризованного теста
     */
    @ParameterizedTest
    @MethodSource("getGradeParametrizedTestData")
    @DisplayName("Параметризованный тест метода getGrade")
    void getGradeParametrizedTest(GetGradeTestData data) {
        assertEquals(data.expectedResult(), LogicTasks.getGrade(data.score()));
    }

}
