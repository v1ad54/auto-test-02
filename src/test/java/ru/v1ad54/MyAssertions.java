package ru.v1ad54;

import org.jspecify.annotations.Nullable;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.function.Executable;
import org.opentest4j.AssertionFailedError;

import java.util.Arrays;


/**
 * Класс обертка над Assertions
 * В зависимости от результатов теста выводят в стандартный вывод
 * фразу "TEST PASSED" или "TEST PASSED" и вызывают соответствующий метод Assertions
 */
public class MyAssertions {

    private static final String PASSED = "TEST PASSED";
    private static final String FAILED = "TEST FAILED";


    // --- printEquals --------------------------------------------------------

    private static void printCondition(boolean condition) {
        if (condition) {
            System.out.println(PASSED);
        } else {
            System.out.println(FAILED);
        }
    }

    // --- assertEquals --------------------------------------------------------

    public static void assertEquals(String expected, String actual, String message) {
        printCondition(expected.equals(actual));
        Assertions.assertEquals(expected, actual, message);
    }

    public static void assertEquals(String expected, String actual) {
        assertEquals(expected, actual, null);
    }

    public static void assertEquals(int expected, int actual, String message) {
        printCondition(expected == actual);
        Assertions.assertEquals(expected, actual);
    }

    public static void assertEquals(int expected, int actual) {
        assertEquals(expected, actual, null);
    }


    public static void assertEquals(Integer expected, Integer actual) {
        printCondition(expected.equals(actual));
        Assertions.assertEquals(expected, actual);
    }

    public static void assertEquals(Integer expected, int actual) {
        printCondition(expected.equals(actual));
        Assertions.assertEquals(expected, actual);
    }

    public static void assertEquals(Object expected, Object actual) {
        printCondition(expected.equals(actual));
        Assertions.assertEquals(expected, actual);
    }

    // --- assertTrue --------------------------------------------------------

    public static void assertTrue(boolean condition, String message) {
        printCondition(condition);
        Assertions.assertTrue(condition, message);
    }

    public static void assertTrue(boolean condition) {
        assertTrue(condition, null);
    }

    // --- assertFalse --------------------------------------------------------

    public static void assertFalse(boolean condition, String message) {
        printCondition(!condition);
        Assertions.assertFalse(condition, message);
    }

    public static void assertFalse(boolean condition) {
        assertFalse(condition, null);
    }

    // --- assertArrayEquals --------------------------------------------------------

    public static void assertArrayEquals(Object[] expected, Object[] actual) {
        printCondition(Arrays.equals(expected, actual));
        Assertions.assertArrayEquals(expected, actual);
    }

    // --- assertThrows --------------------------------------------------------

    public static <T extends Throwable> T assertThrows(Class<T> expectedType, Executable executable, String message) {
        try {
            T exception = Assertions.assertThrows(expectedType, executable, message);
            System.out.println(PASSED);
            return exception;
        } catch (AssertionFailedError e) {
            System.out.println(FAILED);
            throw e;
        }
    }

    public static <T extends Throwable> T assertThrows(Class<T> expectedType, Executable executable) {
        try {
            T exception = assertThrows(expectedType, executable, null);
            return exception;
        } catch (AssertionFailedError e){
            throw e;
        }
    }

        // --- fail --------------------------------------------------------

    public static <V> V fail(@Nullable String message) {
        System.out.println(FAILED);
        Assertions.fail(message);
        return null;
    }

}
