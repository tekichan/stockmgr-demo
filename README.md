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

`StockQuoteService` provides a Common Method for fetching historical stock quote list.
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

`ExceptionResponseFactory` encapsulates Response Entity Creation logic of two Exception classes.
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

Taking `TaIndicatorCtrl` as an example, if you put the logic into `StockQuoteCtrl.java`, you may reduce some lines of codes 
(Saved a line of `SmaService` declaration?). However you may the class becoming complicated and increases complexity of maintanence.
For the same reason, you should not put `SmaService.java` or `BollingerService.java` into the Restful Controller class either. If so,
any change of Restful API, SMA or Bollinger logic will impact the remaining, thus it increases development and testing efforts.

## O: Open Close Design Principle

Open for extension, close for modification.

```java
SMAIndicator smaIndicator = new SMAIndicator(closePrice, timeFrame);
```

Simple Moving Average (SMA) is a frequently used indicator. It is not only applicable to price but also other figures like volume.
If we want to have SMA on Open Price, do we need to create another indicator? No. The class `SMAIndicator` will calculate
the list of values according the first parameter. If the parameter is Close Price, for example, the SMA is on Close Price.

## L: Liskov Substitution Principle (LSP)

Methods using superclass type should work well with objects of subclass without any issue.

```java
List<SmaItem> smaList = new ArrayList<>();
```
`List` and `ArrayList` are typical examples of LSP. Developers totally feel safe to call methods, e.g. `size()` and `iterator()`
 of `List` without care of implementation or subclass, which could be `ArrayList` or `Vector` or `AttributeList`. 
Those methods are still applicable and aligned with the expected results. The Restful Controller classes can convert
the resulted List to JSON array in a generic way safely.

## I: Interface Segregation Principle (ISP)

Do not implement an interface if it is not used.

OOP prefers calling the interface rather the implementation for abstraction. Callers have expectation on the methods they call.
If you call a method of a class and it does not behave as you expect if the implementation is changed to another, then it 
could lead subsequent logic broken.

```java
public int getErrorOffset()
```
This is a method of `ParseExceptionResponse` implementation `IExceptionResponse`. If it were made to be a method of the interface,
then `ExceptionResponse` has to make it as well but the method is not meaningful there. If we have a logic on displaying Error 
Offset, it would get the same meaningless result from calling it from `ExceptionResponse`.

## D: Dependency Injection

The caller object does not depend on the creation code of the receiver because object creation code is centralized. It is easy to do mock test and maintain.
For example, Spring Framework utilizes XML or Aspect Oriented Programming to implement it.  

Spring Framework is famous at DI or Inversion Of Control (IoC). The main application does not need to know what classes
and how they are constructed. They can be configured by either annotation or configuration.

```java
@SpringBootApplication
public class MainApp {
    /**
     * Main static method of this application
     * @param args  arguments
     */
    public static void main(String[] args) {
        SpringApplication.run(MainApp.class, args);
    }
}
```
The main application class is so simple and clear. Either I created `VersionCtrl`, `StockQuoteCtrl` or more, it remains the same.

In `StockQuoteService`,
```java
@Service
public class StockQuoteService {
```

In `StockQuoteCtrl`,
```java
    @Autowired
    private StockQuoteService stockQuoteService;
```

`@Autowired` is commonly used annotation for DI. Spring creates `StockQuoteService` based on `@Service`. `@Autowired` in
other classes can reference an instance of `StockQuoteService` which has been constructed in advance.

## Favor Composition over Inheritance

Composition is HAS-A relationship while Inheritance is IS-A relationship. Composition gives more flexibility to use a base class
while Inheritance has more restriction on a subsequent class. "Favor" does not mean always. You should always think about
"Should I inherit from the base class?".

For example in `SmaService`,
```java
@Service
public class SmaService {
    ......
    
    /** Stock Quote Service */
    @Autowired
    private StockQuoteService stockQuoteService;

    /**
     * Get SMA value list
     */
    public List<SmaItem> getSmaList(
            String stockCode
            , Optional<String> fromDateOptional
            , Optional<String> toDateOptional
            , Optional<Integer> timeFrameOptional
    ) throws IOException, ParseException {
        List<HistoricalQuote> quoteList = stockQuoteService.getHistoricalQuoteList(stockCode, fromDateOptional, toDateOptional);
        ......
    }
}
```
`SmaService` needs to append a SMA value with each stock quote. The above logic can work the same if `SmaService` inherits `StockQuoteService`
and `SmaItem` inherits `HistoricalQuote`. If it is the case, `SmaItem` must have all fields of `HistoricalQuote` in which some fields are not
relevant to SMA. More or less future functions in `StockQuoteService` will need `SmaService` to provide but they may not used in SMA's case.
This will create much burden without value added. 

## Programming for Interface not Implementation

Interact with superclass, not subclass. Same reason with LSP.

## Delegation Principle

Remember SRP. Don't create a massive can-do-all class. It is not only mass but also a mess. Delegate non-core functions to other classes
for avoiding code duplication and making better maintenance.

```java
    public static TimeSeries geTimeSeries(List<HistoricalQuote> quoteList, String symbol) {
        TimeSeries series = new BaseTimeSeries.SeriesBuilder().withName(symbol).build();
        quoteList.forEach(quote -> {
                series.addBar(
                        DateUtils.getZonedDateTime(quote.getDate())
                        , quote.getOpen()
                        , quote.getHigh()
                        , quote.getLow()
                        , quote.getClose()
                        , quote.getVolume()
                );
        });
        return series;
    }
```

```java
    TimeSeries timeSeries = TaUtils.geTimeSeries(quoteList, stockCode);
```
Getting TimeSeries is always used in many scenarios. Rather than repeating the logic in each class, simply create a utility class
to delegate the logic to it. Then caller classes will have much cleaner codes.

## Appendix A - Program Overview
This program is to provide stock price quotes and technical analysis indicators via Restful APIs. 
This uses [Spring Boot](https://spring.io/projects/spring-boot) for rapid development with dependency injection framework support.
Stock price quotes which come from [Yahoo! Finance](https://finance.yahoo.com/) are retrieved by an unofficial Java library called [Quotes API for Yahoo Finance](https://financequotes-api.com/).