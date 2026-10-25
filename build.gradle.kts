plugins {
    id("java")
}

group = "ru.v1ad54.homework02"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:6.0.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

tasks.test {
    useJUnitPlatform()

    // Стандартные выводы внутри тестов направляем в консоль
    testLogging {
        showStandardStreams = true
    }
}

tasks.register<Test>("task1") {
    group = "my_test_tasks"
    description = "Задача вызывает стандартный запуск всех тестов в проекте."

    // Запуск тестов через стандартные задачи
//    // Предварительно запустим clean, иначе не запускаются уже прошедшие тесты
//    dependsOn("clean")
//    // Задача task1 запускает стандартную задачу test
//    dependsOn(tasks.test)


    // Запуск тестов самостоятельно (не через задачи)

    // Стандартные выводы внутри тестов направляем в консоль
    testLogging {
        showStandardStreams = true
    }

    useJUnitPlatform()

    // Чтобы тесты всегда прогонялись заново, иначе не запускаются уже пройденные тесты
    outputs.upToDateWhen { false }

    // Зададим пути к тестовым и связанным классам
    val testSourceSet = sourceSets.test.get()
    testClassesDirs = testSourceSet.output.classesDirs
    classpath = testSourceSet.runtimeClasspath
}

/**
 * Задача которая после выполнения тестов выводит фразу: Test run is over
 */
tasks.register<Test>("task2"){
    group = "my_test_tasks"
    description = "Задача после выполнения всех тестов (task1) выводит в консоль \"Test run is over\". Запускается после завершения первой задачи."

    // Запуск задачи task1 перед task2
    dependsOn("task1")

    // После выполнения задачи вывести: Test run is over
    doLast {
        println("Test run is over")
    }
}
