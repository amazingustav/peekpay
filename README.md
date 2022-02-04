<h1 align="center">
    <br>PeekPay<br/>
</h1>

<p align="center">
  <a href="#about">About</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
  <a href="#stack">Stack</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
  <a href="#how-to-run">How to run</a>&nbsp;&nbsp;&nbsp;|&nbsp;&nbsp;&nbsp;
  <a href="#specifications">Specifications</a>
</p>

## About

PeekPay is a server-side application responsible to create orders, append payments to orders and filter them.

This project was built in order to present as a tech test to Peek, for a Software Engineer hole

## Stack

- [Kotlin](https://kotlinlang.org/)
- [Spring Boot](https://spring.io/projects/spring-boot)
- [H2](https://www.h2database.com/html/main.html)
- [JUnit](https://junit.org/junit5/)
- [Mockk](https://mockk.io/)

## How to Run

- ### **Requirements**

    - Make sure you
      have **[Java JDK 17](https://docs.jboss.org/jbossas/docs/Installation_Guide/4/html/Pre_Requisites-Configuring_Your_Java_Environment.html)**
      installed and configured into your machine (you can run `java -version` to check)
    - You 
    - You must have connection internet to download all libraries (but I'm sure you have because you are reading this on
      GitHub)

To run it, just enter the root folder and run a simple command, as below:

```sh
  $ cd peekpay
  
  $ make up
```
If you wish to execute only the test suite, run:

```sh
  $ make test-app
```

## Specifications

This application doesn't provide any web layer, so practically speaking you don't need to run it. The project 
organization (folders and packages) where built in the intention to split each responsibility, and inside each one we 
can see a domain-separation package:

```dtd
├── persistence       ->     Storage layer, responsible to communicate to the database and execute all DB transactions
│   ├── customer      ->     Customer domain's persistence logics 
│   ├── order         ->     Order domain's persistence logics
│   └── payment       ->     Payment domain's persistence logics
├── usecase           ->     Business Logic layer, responsible to orchestrate the logic between domain services
│   ├── customer      ->     Customer domain's business rules
│   ├── order         ->     Order domain's business rules
│   └── payment       ->     Payment domain's business rules
├── core              ->     Layer responsible to run the application
│   └── AppBoot       ->     This is where the main() function should be
```

Inside the `usecase` layer, we can find our 4 principal methods in two different places:
* `OrderService` - Here is where we can find the `create_order` and `get_order` methods;
* `PeekPayService` - Here we can find the `apply_payment_to_order`, `create_order_and_pay` and `get_orders_for_customer`

They were built in 2 different places mainly for 2 reasons:
1. As this app doesn't have a web layer and I wasn't familiar with GraphQL, I preferred to not take a risk trying to 
implement it and delay this feature;
2. The `OrderService` component grabs those two methods because they don't depend on any other context. In the meanwhile
`PeekpayService` was created to handle more than one context, and orchestrate the responsibilities between them 
(e.g. create an order and then create a payment for this order.)
