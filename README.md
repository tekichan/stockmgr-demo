# stockmgr-demo
Stock Manager for Demo. This project aims at demonstrating best practices of Object Oriented Design in Java.
[![License](https://img.shields.io/badge/license-MIT-green.svg)](/LICENSE)

## Introduction
Inspired by the article ["10 OOP Design Principles Every Programmer Should Know"](https://hackernoon.com/10-oop-design-principles-every-programmer-should-know-f187436caf65)
which was written by [@javinpaul](https://hackernoon.com/@javinpaul), I built this project to implement the top Object Oriented Design principles in Java.
The implementation is about Stock Price Quote which sounds like interesting and practical.

The mentioned 10 Object Oriented Design principles are:
- Don't Repeat Yourself (DRY)
- Encapsulation
- Open Close Design Principle
- Single Responsibility Principle (SRP)
- Dependency Injection
- Favor Composition over Inheritance
- Liskov Substitution Principle (LSP)
- Interface Segregation Principle (ISP)
- Programming for Interface not Implementation
- Delegation Principle

## Appendix A - Program Overview
This program is to provide stock price quotes and technical analysis indicators via Restful APIs. 
This uses [Spring Boot](https://spring.io/projects/spring-boot) for rapid development with dependency injection framework support.
Stock price quotes which come from [Yahoo! Finance](https://finance.yahoo.com/) are retrieved by an unofficial Java library called [Quotes API for Yahoo Finance](https://financequotes-api.com/).