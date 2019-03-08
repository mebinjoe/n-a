# spring-boot-couchbase-angularjs

Prototyped as a full stack application that makes use of Couchbase Server's N1QL query language.

This Paardigm separates Java and Couchbase Server into the back-end and leaves AngularJS, HTML, and CSS as the front-end that requests data from the back-end and presents it to the user.

## Prerequisites

You will need the following dependencies. Versions might depend. Below is the versions that is used:

* Maven 3.3 or above
* Java 1.8 or above
* Couchbase Server 6.0
* Spring Boot 2.0.4.RELEASE
* AngularJS v1.4.7
* Bootstrap v3.3.5

## Couchbase Database

This project requires Couchbase 4.1 or higher in order to function because it makes use of the N1QL query language. With Couchbase Server installed, create a new bucket called **sample-bucket** or whatever you've named it in your **src/main/resources/application.properties** file.

We're not done yet. In order to use N1QL queries in your application you must create a primary index on your bucket. This can be done by using the Couchbase Query Client (CBQ).

Setting up Couchbase DB:

1. Head over to the [downloads](https://www.couchbase.com/get-started#Download_Couchbase_Server) section of the Couchbase website and get the latest installation for your computer, whether it be Mac, Windows, or Linux.
2. Start the server
3. Server will be opened in a GUI by default in URL and Port http://127.0.0.1:8091 

Using Couchbase Query Client (CBQ) or GUI, create an index like so:

```
CREATE PRIMARY INDEX ON `sample-bucket`;
```

## Running the Application

```
$ mvn spring-boot:run
```

Open **http://localhost:8080** from your web browser you will be able to use the application.

### Steps to note

* Always use latest version of couchbase `java-client` The version I used is the latest available 2.7.2

```
        <dependency>
            <groupId>com.couchbase.client</groupId>
            <artifactId>java-client</artifactId>
            <version>2.7.2</version>
        </dependency>
```

* While configuring couchbase ensure that bucket name and user name are same (Example; `bucket=sample-bucket, name=sample-bucket` with user having access to the bucket `sample-bucket`)

Note: In Couchbase Server 5.0, role-based access controls were introduced. You must now create a user with a name that matches the bucket name and use that user's password when opening the bucket. The user must have a role with access rights to the bucket.

```
hostname=127.0.0.1
bucket=sample-bucket
password=
```

* Known Issues: `java.util.concurrent.TimeoutException`

If TimeoutException occur the possible reason is your couchbase environment connectionTimeout is 5000ms or 5sec, which is the default value of connection timeout.

You need to increase this value to `10000ms` or greater. Your problem will be solved.

```
//this tunes the SDK (to customize connection timeout)
CouchbaseEnvironment env = DefaultCouchbaseEnvironment.builder()
        .connectTimeout(10000) //10000ms = 10s, default is 5s
        .build();
```