# Дипломный проект по тестированию API для [reqres.in](https://reqres.in/)

<p align="center">
<img width="50%" title="reqres.in" name="reqres.in" src="media/logo/logo_reqres.png">
</p>

## :open_book: Содержание

- [Технологии и инструменты](#gear-технологии-и-инструменты)
- [Реализованныe проверки](#heavy_check_mark-тест-кейсы)
- [Запуск тестов из терминала](#computer-запуск-тестов-из-терминала)
- [Запуск тестов в Jenkins](#-запуск-тестов-в-jenkins)
- [Отчет о результатах тестирования в Allure Report](#-отчет-о-результатах-тестирования-в-Allure-report)
- [Интеграция с Allure TestOps](#-интеграция-с-allure-testops)
- [Уведомления в Telegram](#-уведомления-в-telegram)

## :gear: Технологии и инструменты

<p align="left">
<a href="https://www.jetbrains.com/idea/"><img src="media/logo/Intelij_IDEA.svg" width="50" height="50"  alt="IDEA" title="IntelliJ IDEA"/></a>
<a href="https://www.java.com/"><img src="media/logo/Java.svg" width="50" height="50" alt="Java" title="Java"/></a>
<a href="https://github.com/"><img src="media/logo/GitHub.svg" width="50" height="50" alt="Github" title="GitHub"/></a>
<a href="https://junit.org/junit5/"><img src="media/logo/JUnit5.svg" width="50" height="50" alt="JUnit 5" title="JUnit 5"/></a>
<a href="https://gradle.org/"><img src="media/logo/Gradle.svg" width="50" height="50" alt="Gradle" title="Gradle"/></a>
<a href="https://github.com/allure-framework/allure2"><img src="media/logo/Allure_Report.svg" width="50" height="50" alt="Allure" title="Allure"/></a>
<a href="https://www.jenkins.io/"><img src="media/logo/Jenkins.svg" width="50" height="50" alt="Jenkins" title="Jenkins"/></a>
<a href="https://web.telegram.org/"><img src="media\logo\Telegram.svg" width="50" height="50" alt="Telegram" title="Telegram"></a>
<a href="https://qameta.io/"><img src="media\logo\Allure_TO.svg" width="50" height="50" alt="Allure_TO" title="Allure_TO"></a>
</p>

## :heavy_check_mark: Тест кейсы

- Успешная регистрация
- Регистрация без пароля
- Проверка ответа на наличие сортировки по годам
- Обновление пользователя
- Проверка статус кода после удаления
- Проверка email пользователей на содержание reqres.in и проверка наличия avatar
- Проверка email при помощи groovy
- Сравнение даты в ответе и текущей даты

## :computer: Запуск тестов 


```bash
gradle clean test
```
> Для запуска тестов в несколько потоков необходимо добавить параметр <code>-Dthreads={Количество потоков}</code>
>
> Например: <code>gradle clean test -Dthreads=2</code>


## <img width="4%" title="Jenkins" src="media/logo/Jenkins.svg"> Запуск тестов из [Jenkins](https://jenkins.autotests.cloud/job/diploma_project_api/)
Сборка проекта.

<p align="center">
  <img src="media/screen/start_jenkins.png" alt="Jenkins" width="800">
</p>


## <img width="4%" title="Allure Report" src="media/logo/Allure_Report.svg"> Отчет о результатах тестирования в [Allure Report](https://jenkins.autotests.cloud/job/diploma_project_api/8/allure/)

<p align="center">
  <img src="media/screen/allure-report1.png" alt="allure-report" width="900">
</p>

<p align="center">
  <img src="media/screen/allure-report2.png" alt="allure-report_1" width="900">
</p>

## <img width="4%" title="Allure TestOPS" src="media/logo/Allure_TO.svg"> Интеграция с [Allure TestOps](https://allure.autotests.cloud/launch/19372)

### Основной дашборд

<p align="center">
  <img src="media/screen/Allure_TO1.png" alt="Allure_TO1" width="900">
</p>

### Список тестов с результатами

<p align="center">
  <img src="media/screen/Allure_TO2.png" alt="Allure_TO2" width="900">
</p>

### Test plans
<p align="center">
  <img src="media/screen/Allure_TO3.png" alt="Allure_TO3" width="900">
</p>


## <img width="4%" title="Telegram" src="media/logo/Telegram.svg"> Уведомления в Telegram
После завершения сборки специальный бот, созданный в <code>Telegram</code>, автоматически обрабатывает и отправляет сообщение с отчетом о прохождении тестов.

<p align="center">
<img title="Telegram Notifications" src="media/screen/telegram_notify.png">
</p>

[Вернуться к началу ⬆](#reqres.in)