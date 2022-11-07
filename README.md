# Organisation Address

 * Support address information when creating an organisation.
 * Support adding an address for an existing organisation.

## Design Considerations

### 1. Optional Address

 * Organisation addresses are added as an optional construct. The reason for doing so is to ensure theoretical backwards compatability. As an alternative, this could also be supported by adding an "v2" resource for `Organisation`s, which would include a mandatory address.

 * Only a single address is supported, which may be restrictive in a real world setting. Adding support for multiple addresses per organisation should be trivial.

 * A further improvement could be to remove the top level `country` field from the `Organisation`, but the requirements are unclear on what exactly the `country` alludes to. It could be the country of origin, country of operation or the country of registration - each of which may or may not be the same as the country found in the organisation address. 

### 2. JPA versus JDBC

I've chosen to swap out direct JDBC interaction for JPA via Hibernate for several reasons:
 * Higher level of abstraction - domain entities instead of primitives
 * Generated repositories for simple queries.
 * Type safety. Consider `ps.setDate(3, Date.valueOf(org.dateFounded))` - the index is incorrect, which will only be discovered at runtime.

JDBC does provide more fine-grained control over exactly what DML is issued to a DB, but this can also be achieved by using `@Query` and `@NativeQuery` annotations for a given repository.

If JPA is undesirable for whatever reason, the a typesafe SQL generator like JOOQ could be employed.

### 3. Test Containers

The previous implementation required that the host environment has a DB running at a given port in order to run tests. I'm a strong proponent for enabling a developer to checkout a project and simply run the tests. To facilitate that, the tests were changed to run against test containers. This ensures a clean and up to date schema based on flyway migrations.

### 4. Modelling & Mapping 

In a simple application, I prefer to split models between `Entity`s and `DTO`s to separate concerns. The previous implementation modelled the DB table on an object named `*Response`. This can be confusing as one might expect that we're storing responses in the database, rather than explicit domain models.

Simple mappers are generated during compilation via `MapStruct`. Slightly more complex mappings are hand written where necessary.

-----------

Pair Programming Exercise for Billie
=============
### The Requirements

The way a business like Billie works is this:

```
A business buyer goes to a merchant's ecommerce platform catering for B2B and buys goods. 
At the checkout, the buyer chooses Billie as a payment method, and checks out via our 
widget on the merchants site.  Upon shipment, the merchant tells us this, and is paid
immediately. Billie also issues an invoice to the buyer.  The buyer is then invoiced 
from Billie and they pay the invoice
```

At this point, we have built an API to map simple organisations, but not much else.  
There are a lot of features still to build!!! 
* Adding an address to an organisation
* Issuing an invoice for the buyer once the merchant notifies us the goods have been shipped
* Once the buyer has paid the invoice, there needs to be the ability to mark the invoice as paid 
* The ability for the merchant to notify us of shipment of an order, so they can get paid

We will tackle _some_ of these cases as part of the pairing exercise


The Exercise
====
```
In order for you to prepare for the pair programming we need you to be familiar 
with the code we've already got in this repository.
Please make a fork of this repo and add *one* feature from the list above and 
send us a link to your repo.  This tells us you've worked in the code and are 
familiar with it.  We like high success rates for our pairing sessions and
history tells us that is much more likely if a candidate has familiarity with 
the code already.

Strong hint: we are fans of TDD, pairing and continuous deployment.
```


### The Tech Stuff
#### Prerequisites
We assume that you have docker, docker compose, and Java 15 installed, and can run gradle

Running the tests:
```shell
cd <project_root>
docker compose up database -d
gradle flywayMigrate
gradle clean build
docs at -> http://localhost:8080/swagger-ui/index.html
```
Work has been started but not done yet to containerise the kotlin service.

The service runs in the host right now.  Feel free to fix that if it makes your life easier
