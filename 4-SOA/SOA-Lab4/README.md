# Четвертая лабораторная работа, Сервис - Ориентированная архитектура
Отчет в основном репозитории ITMO

## Ссылки
Реализуемая спецификация из первой работы: [Лабораторная работа №1](https://se.ifmo.ru/~s284731/SOA/)  
Вторая/Третья лабораторная работа: [Исходный код лабораторной №3](https://github.com/EgorMIt/SOA-Lab2)
Клиентское приложение: [EgorMIt/SOA-Lab2-frontend](https://github.com/EgorMIt/SOA-Lab2-frontend)
## Вариант задания

Переработать сервисы из лабораторной работы #3 следующим образом:

* Второй ("вызывающий") сервис переписать в соответствии с требованиями протокола SOAP.
* Развернуть переработанный сервис на сервере приложений по собственному выбору.
* Оставшийся сервис не модифицировать, не менять его API, протокол и используемый сервер приложений.
* Установить и сконфигурировать на сервере Helios программное обеспечение Mule ESB.
* Настроить интеграцию двух сервисов с использованием установленного программного обеспечения.
* Реализовать дополнительную REST-"прослойку", обеспечивающую возможность доступа к переработанному сервису клиентского приложения без необходимости его модификации. Никакой дополнительной логики, помимо вызовов SOAP-сервиса, разработанная REST-прослойка содержать не должна.
