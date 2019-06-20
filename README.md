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
- **S** ingle Responsibility Principle (SRP)
- **O** pen Close Design Principle
- **L** iskov Substitution Principle (LSP)
- **I** nterface Segregation Principle (ISP)
- **D** ependency Injection
- Favor Composition over Inheritance
- Programming for Interface not Implementation
- Delegation Principle

## Don't Repeat Yourself (DRY)

For any programming, it is always true to **NOT REPEAT YOURSELF**. Coding in Java, an OOP language, it is wise to abstract common logic in a reusable block.

Both `SmaService` and `BollingerService` need stock prices as input. Fetching the price list needs a logic of date range inputs. The logic is common in both service classes, also common in basic stock quote. As a result, instead of repeating the same code triple, share the common method in the three classes. 

`StockQuoteService.java` provides a Common Method for fetching historical stock quote list.
```java
    public List<HistoricalQuote> getHistoricalQuoteList(
            String stockCode
            , Optional<String> fromDateOptional
            , Optional<String> toDateOptional
    ) throws IOException, ParseException {
        Calendar fromDateCal = DateUtils.getDateFromOptional(
                fromDateOptional
                , appConfig.getRest().getDatePattern()
                , appConfig.getRest().getTimezone()
        ); // Get From Date
        Calendar toDateCal = DateUtils.getDateFromOptional(
                toDateOptional
                , appConfig.getRest().getDatePattern()
                , appConfig.getRest().getTimezone()
        ); // Get To Date
        return getHistoricalQuoteList(stockCode, fromDateCal, toDateCal);
    }
```

## Encapsulation

Every internal logic should be encapsulated from the external. Any internal change does not impact the external. Thus it makes testing and maintenance easier. 
For example, Factory Design Pattern encapsulates the creation logic. In this case, external parties will not be impacted the variety of creation logic.

`ExceptionResponseFactory.java` encapsulates Response Entity Creation logic of two Exception classes.
```java
    public static IExceptionResponse toExceptionResponse(Exception ex) {
        if (ex instanceof ParseException) {
            ParseException pex = (ParseException) ex;
            return new ParseExceptionResponse(
                    pex.getClass().getName()
                    , pex.getMessage()
                    , Arrays.stream(pex.getStackTrace()).map(StackTraceElement::toString).collect(Collectors.toList())
                    , pex.getErrorOffset()
            );
        } else {
            return new ExceptionResponse(
                    ex.getClass().getName()
                    , ex.getMessage()
                    , Arrays.stream(ex.getStackTrace()).map(StackTraceElement::toString).collect(Collectors.toList())
            );
        }
    }
```

## S: Single Responsibility Principle (SRP)

A class should only have a single responsibility. This is because more functions makes more coupling, thus much complexity and testing effort.

## O: Open Close Design Principle

Open for extension, close for modification.

## L: Liskov Substitution Principle (LSP)

Methods using superclass type should work well with objects of subclass without any issue.

## I: Interface Segregation Principle (ISP)

Do not implement an interface if it is not used.

## D: Dependency Injection

The caller object does not depend on the creation code of the receiver because object creation code is centralized. It is easy to do mock test and maintain.
For example, Spring Framework utilizes XML or Aspect Oriented Programming to implement it.  

## Favor Composition over Inheritance

## Programming for Interface not Implementation

Interact with superclass, not subclass.

## Delegation Principle

## Appendix A - Program Overview
This program is to provide stock price quotes and technical analysis indicators via Restful APIs. 
This uses [Spring Boot](https://spring.io/projects/spring-boot) for rapid development with dependency injection framework support.
Stock price quotes which come from [Yahoo! Finance](https://finance.yahoo.com/) are retrieved by an unofficial Java library called [Quotes API for Yahoo Finance](https://financequotes-api.com/).