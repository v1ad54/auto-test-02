package ru.v1ad54;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Структура для хранения соответствия оценки и ее символьного представления
 *
 * @param symbol - символьное представление оценки (A, B, C, D, E)
 * @param grade  - оценка от 0 до 100
 */
record GradeTestData(
        String symbol,
        int grade
) {
}

public class LectureTest {

    /**
     * Тест один раз запускает метод isEven со случайным числом от 1 до 100
     */
    @Test
    @DisplayName("Тест метода isEven")
    void isEvenTest() {
        Random random = new Random();
        int value = random.nextInt(1, 101); // от 0 до 100 включительно
        System.out.println("Тест метода isEven с value = " + value);

        assertEquals(value % 2 == 0, LogicTasks.isEven(value));
    }

    /**
     * Тест 20 раз запускает метод checkAccess со случайными числами от 0 до 99
     */
    @RepeatedTest(value = 20, name = "тест {currentRepetition} из {totalRepetitions}")
    @DisplayName("Тесты метода checkAccess")
    void checkAccessTest() {
        Random random = new Random();
        int age = random.nextInt(0, 100); // от 0 до 99 включительно
        System.out.println("Тест метода checkAccess с value = " + age);

        assertEquals(
                (age > 18) ? "Allowed" : "Denied",
                LogicTasks.checkAccess(age),
                "Не верный доступ для age = " + age
        );
    }

    /**
     * Метод предоставляет данные для параметризованного теста testGetGradeTest
     *
     * @return - возвращает список структур для хранения соответствия оценки и ее символьного представления
     */
    static private List<GradeTestData> getGradeTestData() {
        Random random = new Random();
        List<GradeTestData> list = new ArrayList<>();

        // Сделаем по 5 тестов для каждой группы оценок
        // E - от 0 до 20 (включительно)
        for(int i = 0; i < 5; i++) list.add(new GradeTestData("E", random.nextInt(0, 20)));
        // D - от 21 до 40 (включительно)
        for(int i = 0; i < 5; i++) list.add(new GradeTestData("D", random.nextInt(21, 40)));
        // C - от 21 до 40 (включительно)
        for(int i = 0; i < 5; i++) list.add(new GradeTestData("C", random.nextInt(41, 60)));
        // B - от 21 до 40 (включительно)
        for(int i = 0; i < 5; i++) list.add(new GradeTestData("B", random.nextInt(61, 80)));
        // A - от 21 до 40 (включительно)
        for(int i = 0; i < 5; i++) list.add(new GradeTestData("A", random.nextInt(81, 100)));

        // Оценки вне диапазона от 0 до 100
        // Больше 100
        for(int i = 0; i < 5; i++) list.add(new GradeTestData("Error", random.nextInt(101, 1000)));
        // Меньше 0
        for(int i = 0; i < 5; i++) list.add(new GradeTestData("Error", random.nextInt(-1000, -1)));

        return list;
    }

    /**
     * Параметризованный тест запускает метод getGrade массивом случайных чисел от 0 до 100
     * @param gradeTestData
     */
    @ParameterizedTest
    @MethodSource("getGradeTestData")
    @DisplayName("Тесты метода getGrade")
    void getGradeParameterizedTest(GradeTestData gradeTestData) {
        System.out.println("Тест метода getGrade с score = " + gradeTestData.grade());
        assertEquals(gradeTestData.symbol(), LogicTasks.getGrade(gradeTestData.grade()), "Оценка " + gradeTestData.grade() + " должна быть: " + gradeTestData.symbol());
    }


    @BeforeEach
    void setup() {
        System.out.println("========================Test method start");
    }

    @AfterEach
    void cleanup() {
        System.out.println("Test method end========================");
    }

}


