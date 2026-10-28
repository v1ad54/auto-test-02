package ru.v1ad54;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.*;

//import static org.junit.jupiter.api.Assertions.*;
import static ru.v1ad54.MyAssertions.*;


/**
 * Структура тестовых данных для параметризованного теста {@link ArrayTasksTest#findMaxParametrizedTest(FindMaxTestData)}
 *
 * @param array          - массив чисел для параметра arr
 * @param expectedResult - ожидаемый результат метода findMax
 */
record FindMaxTestData(
        int[] array,
        String expectedResult
) {
}

/**
 * Структура тестовых данных для параметризованного теста {@link ArrayTasksTest#reverseParametrizedTest(ReverseTestData)}
 *
 * @param array             - массив чисел для параметра arr
 * @param expectedResult    - ожидаемый результат метода reverse
 * @param expectedException - ожидаемое исключение при выполнении метода reverse
 */
record ReverseTestData(
        String[] array,
        String[] expectedResult,
        Class expectedException
) {
}

/**
 * Структура тестовых данных для параметризованного теста {@link ArrayTasksTest#calcAverageParametrizedTest(CalcAverageTestData)}
 *
 * @param list              - список чисел для параметра list
 * @param expectedResult    - ожидаемый результат метода calcAverage
 * @param expectedException - ожидаемое исключение при выполнении метода reverse
 */
record CalcAverageTestData(
        List<Integer> list,
        int expectedResult,
        Class expectedException
) {
}

/**
 * Структура тестовых данных для параметризованного теста
 *
 * @param list              - список имен
 * @param nameToRemove      - имя которое нужно исключить из списка
 * @param expectedResult    - ожидаемый результат метода calcAverage
 * @param expectedException - ожидаемое исключение при выполнении метода reverse
 */
record RemoveSpecificNameTestData(
        List<String> list,
        String nameToRemove,
        List<String> expectedResult,
        Class expectedException
) {
}

public class ArrayTasksTest {

    /*====================[Простые тесты]====================*/

    /**
     * Простой тест метода findMax
     */
    @Test
    @DisplayName("Простые тесты метода findMax")
    void findMaxTest() {
        Random random = new Random();
        List<Integer> list = new ArrayList<>();
        int value;

        // массив с положительными и отрицательными числами
        for (int i = 0; i < 10; i++) {
            list.add(random.nextInt(-10, 10));
        }
        assertEquals(
                Collections.max(list), // Сравниваем с максимальным значением полученным стандартной библиотекой
                ArrayTasks.findMax(
                        list.stream()
                                .mapToInt(Integer::intValue)
                                .toArray()
                )
        );

        // массив в котором все числа одинаковые
        value = random.nextInt(1, 10);
        list.clear();
        for (int i = 0; i < random.nextInt(1, 10); i++) {
            list.add(value);
        }
        assertEquals(
                value,
                ArrayTasks.findMax(list.stream()
                        .mapToInt(Integer::intValue)
                        .toArray()
                )
        );

        // массив из одного элемента
        value = random.nextInt(1, 100);
        assertEquals(value, ArrayTasks.findMax(new int[]{value}));

        // исключение IllegalArgumentException при пустом массиве
        assertThrows(IllegalArgumentException.class, () -> ArrayTasks.findMax(new int[]{}));

        // исключение NullPointerException при передаче null
        assertThrows(NullPointerException.class, () -> ArrayTasks.findMax(null));
    }

    /**
     * Простой тест метода reverse
     */
    @Test
    @DisplayName("Простые тесты метода reverse")
    void reverseTest() {
        Random random = new Random();

        // Создаем список содержащий случайные строки
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("string-" + random.nextInt(1, 100));
        }

        // Создаем реверсированный список с помощью стандартной библиотеки
        List<String> reverseList = new ArrayList<>(list);
        Collections.reverse(reverseList);

        // Сравниваем реверсированный список с результатом который вернул метод ArrayTasks.reverse
        assertArrayEquals(reverseList.toArray(String[]::new), ArrayTasks.reverse(list.toArray(String[]::new)));

        // пустой массив
        assertArrayEquals(new String[]{}, ArrayTasks.reverse(new String[]{}));

        // исключение NullPointerException при передаче null
        assertThrows(NullPointerException.class, () -> ArrayTasks.reverse(null));
    }


    /**
     * Простой тест метода calcAverage
     */
    @Test
    @DisplayName("Простые тесты метода calcAverage")
    void calcAverageTest() {
        Random random = new Random();
        List<Integer> list = new ArrayList<>();

        // просто список чисел, заодно проверка на деление с потерей дробной части (целочисленный int)
        for (int i = 0; i < 10; i++) {
            list.add(random.nextInt(1, 100));
        }
        assertEquals(
                (int) list.stream()
                        .mapToInt(Integer::intValue)
                        .average()
                        .orElse(0.0),
                ArrayTasks.calcAverage(list)
        );

        // исключение IllegalArgumentException на пустой список
        assertThrows(IllegalArgumentException.class, () -> ArrayTasks.calcAverage(new ArrayList<>()));

        // исключение NullPointerException при передаче null
        assertThrows(NullPointerException.class, () -> ArrayTasks.calcAverage(null));

        // исключение IllegalArgumentException, если внутри списка есть null
        assertThrows(IllegalArgumentException.class, () -> ArrayTasks.calcAverage(Arrays.asList(1, 2, null, 4)));
    }

    /**
     * Простой тест метода removeSpecificName
     */
    @Test
    @DisplayName("Простые тесты метода removeSpecificName")
    void removeSpecificNameTest() {
        Random random = new Random();
        List<String> list = new ArrayList<>();

        // Создаем список случайных имен
        for (int i = 0; i < 10; i++) {
            list.add("Name-" + random.nextInt(1, 100));
        }
        String nameFromList = list.get(random.nextInt(list.size())); // Выбираем в списке имен имя, которое нужно исключить из списка
        assertEquals(
                // Сравниваем с новым списком без nameFromList
                list.stream()
                        .filter(name -> !name.equals(nameFromList))
                        .toList()
                , ArrayTasks.removeSpecificName(list, nameFromList)
        );

        // имя для удаления отсутствует в списке
        assertEquals(
                list,
                ArrayTasks.removeSpecificName(list, "Такого_имени_точно_нет_в_списке")
        );

        // исключение NullPointerException при передаче null в параметр array
        assertThrows(
                NullPointerException.class,
                () -> ArrayTasks.removeSpecificName(null, "Name-" + random.nextInt(1, 100))
        );

        // исключение IllegalArgumentException при передаче null в параметр nameFromList
        assertThrows(
                IllegalArgumentException.class,
                () -> ArrayTasks.removeSpecificName(list, null)
        );

        // исключение IllegalArgumentException, если элемент списка равен null
        list.add(null); // Добавим в список null-элемент
        assertThrows(
                IllegalArgumentException.class,
                () -> ArrayTasks.removeSpecificName(list, nameFromList)
        );

    }

    /*====================[Повторяющиеся тесты]====================*/

    /**
     * Повторяющийся тест метода findMax
     */
    @RepeatedTest(value = 5, name = "тест {currentRepetition} из {totalRepetitions}")
    @DisplayName("Повторяющиеся тесты метода findMax")
    void findMaxRepeatedTest() {
        Random random = new Random();
        List<Integer> list = new ArrayList<>();
        int value;

        // массив с положительными и отрицательными числами
        for (int i = 0; i < 10; i++) {
            list.add(random.nextInt(-10, 10));
        }
        value = Collections.max(list); // Сравниваем с максимальным значением полученным стандартной библиотекой
        assertEquals(
                value,
                ArrayTasks.findMax(
                        list.stream()
                                .mapToInt(Integer::intValue)
                                .toArray()
                )
        );

        // массив в котором все числа одинаковые
        value = random.nextInt(1, 10);
        list.clear();
        for (int i = 0; i < random.nextInt(1, 10); i++) {
            list.add(value);
        }
        assertEquals(
                value,
                ArrayTasks.findMax(list.stream()
                        .mapToInt(Integer::intValue)
                        .toArray()
                )
        );

        // массив из одного элемента
        value = random.nextInt(1, 100);
        assertEquals(value, ArrayTasks.findMax(new int[]{value}));

        // исключение IllegalArgumentException при пустом массиве
        assertThrows(IllegalArgumentException.class, () -> ArrayTasks.findMax(new int[]{}));

        // исключение NullPointerException при передаче null
        assertThrows(NullPointerException.class, () -> ArrayTasks.findMax(null));
    }

    /**
     * Повторяющийся тест метода reverse
     */
    @RepeatedTest(value = 5, name = "тест {currentRepetition} из {totalRepetitions}")
    @DisplayName("Повторяющиеся тесты метода reverse")
    void reverseRepeatedTest() {
        Random random = new Random();

        // Создаем список содержащий случайные строки
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            list.add("string-" + random.nextInt(1, 100));
        }

        // Создаем реверсированный список с помощью стандартной библиотеки
        List<String> reverseList = new ArrayList<>(list);
        Collections.reverse(reverseList);

        // Сравниваем реверсированный список с результатом который вернул метод ArrayTasks.reverse
        assertArrayEquals(reverseList.toArray(String[]::new), ArrayTasks.reverse(list.toArray(String[]::new)));

        // пустой массив
        assertArrayEquals(new String[]{}, ArrayTasks.reverse(new String[]{}));

        // исключение NullPointerException при передаче null
        assertThrows(NullPointerException.class, () -> ArrayTasks.reverse(null));
    }

    /**
     * Повторяющийся тест метода calcAverage
     */
    @RepeatedTest(value = 5, name = "тест {currentRepetition} из {totalRepetitions}")
    @DisplayName("Повторяющиеся тесты метода calcAverage")
    void calcAverageRepeatedTest() {
        Random random = new Random();
        List<Integer> list = new ArrayList<>();

        // просто список чисел, заодно проверка на деление с потерей дробной части (целочисленный int)
        for (int i = 0; i < 10; i++) {
            list.add(random.nextInt(1, 100));
        }
        assertEquals(
                (int) list.stream()
                        .mapToInt(Integer::intValue)
                        .average()
                        .orElse(0.0),
                ArrayTasks.calcAverage(list)
        );

        // исключение IllegalArgumentException на пустой список
        assertThrows(IllegalArgumentException.class, () -> ArrayTasks.calcAverage(new ArrayList<>()));

        // исключение NullPointerException при передаче null
        assertThrows(NullPointerException.class, () -> ArrayTasks.calcAverage(null));

        // исключение IllegalArgumentException, если внутри списка есть null
        assertThrows(IllegalArgumentException.class, () -> ArrayTasks.calcAverage(Arrays.asList(1, 2, null, 4)));
    }

    /**
     * Повторяющийся тест метода removeSpecificName
     */
    @RepeatedTest(value = 5, name = "тест {currentRepetition} из {totalRepetitions}")
    @DisplayName("Повторяющиеся тесты метода removeSpecificName")
    void removeSpecificNameRepeatedTest() {
        Random random = new Random();
        List<String> list = new ArrayList<>();

        // Создаем список случайных имен
        for (int i = 0; i < 10; i++) {
            list.add("Name-" + random.nextInt(1, 100));
        }
        String nameFromList = list.get(random.nextInt(list.size())); // Выбираем в списке имен имя, которое нужно исключить из списка
        assertEquals(
                // Сравниваем с новым списком без nameFromList
                list.stream()
                        .filter(name -> !name.equals(nameFromList))
                        .toList()
                , ArrayTasks.removeSpecificName(list, nameFromList)
        );

        // имя для удаления отсутствует в списке
        assertEquals(
                list,
                ArrayTasks.removeSpecificName(list, "Такого_имени_точно_нет_в_списке")
        );

        // исключение NullPointerException при передаче null в параметр array
        assertThrows(
                NullPointerException.class,
                () -> ArrayTasks.removeSpecificName(null, "Name-" + random.nextInt(1, 100))
        );

        // исключение IllegalArgumentException при передаче null в параметр nameFromList
        assertThrows(
                IllegalArgumentException.class,
                () -> ArrayTasks.removeSpecificName(list, null)
        );

        // исключение IllegalArgumentException, если элемент списка равен null
        list.add(null); // Добавим в список null-элемент
        assertThrows(
                IllegalArgumentException.class,
                () -> ArrayTasks.removeSpecificName(list, nameFromList)
        );

    }

    /*====================[Параметризованные тесты]====================*/

    /**
     * Метод предоставляет данные для параметризованного теста findMaxParametrizedTest
     *
     * @return - возвращает список структур
     */
    static private List<FindMaxTestData> findMaxParametrizedTestData() {
        List<FindMaxTestData> dataList = new ArrayList<>();

        Random random = new Random();
        List<Integer> list = new ArrayList<>();
        int value;

        // массив с положительными и отрицательными числами
        for (int i = 0; i < 10; i++) {
            list.add(random.nextInt(-10, 10));
        }
        dataList.add(new FindMaxTestData(
                // Преобразуем список в массив
                list.stream().mapToInt(Integer::intValue).toArray(),
                // Сравнивать будем с максимальным значением полученным стандартной библиотекой
                Collections.max(list).toString()
        ));

        // массив в котором все числа одинаковые
        value = random.nextInt(1, 10);
        list.clear();
        for (int i = 0; i < random.nextInt(1, 10); i++) {
            list.add(value);
        }
        dataList.add(new FindMaxTestData(
                // Преобразуем список в массив
                list.stream().mapToInt(Integer::intValue).toArray(),
                String.valueOf(value)
        ));

        // массив из одного элемента
        value = random.nextInt(1, 100);
        dataList.add(new FindMaxTestData(
                new int[]{value},
                String.valueOf(value)
        ));

        // исключение IllegalArgumentException при пустом массиве
        dataList.add(new FindMaxTestData(
                new int[]{},
                "IllegalArgumentException"
        ));

        // исключение NullPointerException при передаче null
        dataList.add(new FindMaxTestData(
                null,
                "NullPointerException"
        ));

        return dataList;
    }

    /**
     * Параметризованный тест метода findMax
     *
     * @param data - структура тестовых данных для параметризованного теста
     */
    @ParameterizedTest
    @MethodSource("findMaxParametrizedTestData")
    @DisplayName("Параметризованный тест метода findMax")
    void findMaxParametrizedTest(FindMaxTestData data) {
        switch (data.expectedResult()) {
            case "IllegalArgumentException" -> {
                assertThrows(IllegalArgumentException.class, () -> ArrayTasks.findMax(data.array()));
            }
            case "NullPointerException" -> {
                assertThrows(NullPointerException.class, () -> ArrayTasks.findMax(data.array()));
            }
            default -> {
                try {
                    assertEquals(Integer.parseInt(data.expectedResult()), ArrayTasks.findMax(data.array()));
                } catch (NumberFormatException e) {
                    fail("Ошибка данных. Ожидаемый результат не соответствует типу int: " + data.expectedResult());
                }
            }
        }
    }

    /**
     * Метод предоставляет данные для параметризованного теста reverseParametrizedTest
     *
     * @return - возвращает список структур
     */
    static private List<ReverseTestData> reverseParametrizedTestData() {
        List<ReverseTestData> dataList = new ArrayList<>();

        Random random = new Random();
        List<String> list = new ArrayList<>();
        String value;

        // Создаем список содержащий случайные строки
        for (int i = 0; i < 10; i++) {
            list.add("string-" + random.nextInt(1, 100));
        }

        // Создаем реверсированный список с помощью стандартной библиотеки
        List<String> reverseList = new ArrayList<>(list);
        Collections.reverse(reverseList);


        // Сравнение реверсированного списка с результатом который вернул метод ArrayTasks.reverse
        dataList.add(new ReverseTestData(list.toArray(String[]::new), reverseList.toArray(String[]::new), null));

        // пустой массив
        dataList.add(new ReverseTestData(new String[]{}, new String[]{}, null));

        // исключение NullPointerException при передаче null
        dataList.add(new ReverseTestData(null, null, NullPointerException.class));

        return dataList;
    }

    /**
     * Параметризованный тест метода reverse
     *
     * @param data - структура тестовых данных для параметризованного теста
     */
    @ParameterizedTest
    @MethodSource("reverseParametrizedTestData")
    @DisplayName("Параметризованный тест метода reverse")
    void reverseParametrizedTest(ReverseTestData data) {
        if (data.expectedException() != null) {
            assertThrows(data.expectedException(), () -> ArrayTasks.reverse(data.array()));
        } else {
            assertArrayEquals(data.expectedResult(), ArrayTasks.reverse(data.array()));
        }
    }

    /**
     * Метод предоставляет данные для параметризованного теста calcAverageParametrizedTest
     *
     * @return - возвращает список структур
     */
    static private List<CalcAverageTestData> calcAverageParametrizedTestData() {
        List<CalcAverageTestData> dataList = new ArrayList<>();

        Random random = new Random();
        List<Integer> list = new ArrayList<>();

        // просто список чисел, заодно проверка на деление с потерей дробной части (целочисленный int)
        for (int i = 0; i < 10; i++) {
            list.add(random.nextInt(1, 100));
        }
        dataList.add(
                new CalcAverageTestData(
                        list,
                        (int) list.stream().mapToInt(Integer::intValue).average().orElse(0.0),
                        null
                )
        );

        // исключение IllegalArgumentException на пустой список
        dataList.add(new CalcAverageTestData(new ArrayList<>(), 0, IllegalArgumentException.class));

        // исключение NullPointerException при передаче null
        dataList.add(new CalcAverageTestData(null, 0, NullPointerException.class));

        // исключение IllegalArgumentException, если внутри списка есть null
        dataList.add(new CalcAverageTestData(Arrays.asList(1, 2, null, 4), 0, IllegalArgumentException.class));

        return dataList;
    }

    /**
     * Параметризованный тест метода calcAverage
     *
     * @param data - структура тестовых данных для параметризованного теста
     */
    @ParameterizedTest
    @MethodSource("calcAverageParametrizedTestData")
    @DisplayName("Параметризованный тест метода calcAverage")
    void calcAverageParametrizedTest(CalcAverageTestData data) {
        if (data.expectedException() != null) {
            assertThrows(data.expectedException(), () -> ArrayTasks.calcAverage(data.list()));
        } else {
            assertEquals(data.expectedResult(), ArrayTasks.calcAverage(data.list()));
        }
    }

    /**
     * Метод предоставляет данные для параметризованного теста removeSpecificNameParametrizedTest
     *
     * @return - возвращает список структур
     */
    static private List<RemoveSpecificNameTestData> removeSpecificNameParametrizedTestData() {
        List<RemoveSpecificNameTestData> dataList = new ArrayList<>();

        Random random = new Random();
        List<String> list = new ArrayList<>();

        // Создаем список случайных имен
        for (int i = 0; i < 10; i++) {
            list.add("Name-" + random.nextInt(1, 100));
        }
        String nameFromList = list.get(random.nextInt(list.size())); // Выбираем в списке имен имя, которое нужно исключить из списка

        // имя для удаления присутствует в списке
        dataList.add(
                new RemoveSpecificNameTestData(
                        list,
                        nameFromList,
                        list.stream().filter(name -> !name.equals(nameFromList)).toList(), // Сравниваем с новым списком без nameFromList
                        null
                )
        );

        // имя для удаления отсутствует в списке
        dataList.add(
                new RemoveSpecificNameTestData(
                        list,
                        "Такого_имени_точно_нет_в_списке",
                        list,
                        null
                )
        );

        // исключение NullPointerException при передаче null в параметр array
        dataList.add(
                new RemoveSpecificNameTestData(null,
                        "Name-" + random.nextInt(1, 100),
                        null,
                        NullPointerException.class
                )
        );

        // исключение IllegalArgumentException при передаче null в параметр nameFromList
        dataList.add(
                new RemoveSpecificNameTestData(
                        list,
                        null,
                        null,
                        IllegalArgumentException.class
                )
        );

        // исключение IllegalArgumentException, если элемент списка равен null
        list = new ArrayList<>(list); // Создадим новый список на основе старого
        list.add(null); // Добавим в список null-элемент
        dataList.add(
                new RemoveSpecificNameTestData(
                        list,
                        nameFromList,
                        null,
                        IllegalArgumentException.class
                )
        );

        return dataList;
    }

    /**
     * Параметризованный тест метода removeSpecificName
     *
     * @param data - структура тестовых данных для параметризованного теста
     */
    @ParameterizedTest
    @MethodSource("removeSpecificNameParametrizedTestData")
    @DisplayName("Параметризованный тест метода removeSpecificName")
    void removeSpecificNameParametrizedTest(RemoveSpecificNameTestData data) {
        if (data.expectedException() != null) {
            assertThrows(data.expectedException(), () -> ArrayTasks.removeSpecificName(data.list(), data.nameToRemove()));
        } else {
            assertEquals(data.expectedResult(), ArrayTasks.removeSpecificName(data.list(), data.nameToRemove()));
        }
    }

}
