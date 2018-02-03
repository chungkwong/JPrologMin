# JPrologMin

JPrologMin is a implementation(interpreter) of the Prolog language 
on the Java platform.

As a JVM based implementation, JPrologMin enables Prolog programs
to use the full power of Java's API. JPrologMin also enables Java
programs to evaluate Prolog's code.

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

## Getting JPrologMin

### Clone and build from source

You can surely clone this repository and build it using ant:

```
ant
```

### Maven central repository

If you are using Maven, you can add the following into your `pom.xml`:

```xml
<dependency>
  <groupId>com.github.chungkwong</groupId>
  <artifactId>jprologmin</artifactId>
  <version>1.0-SNAPSHOT</version>
</dependency>
```

If you are using something like ivy or gradle, you can also try the central
repository.

### Download JAR from Github

See https://github.com/chungkwong/JPrologMin/releases ,although that may
not be up-to-date.

## Usage

### Standlone

You can start the graphical interpreter by running the JAR
1. Usually, just double click the icon of the JAR file will do
2. If not, try something like `java -jar JPrologMin.jar`
3. If that is not working still, ensure that you have JRE 8.0 or above installed 

You can start the command line interpreter by something like 
`java -jar JPrologMin.jar --cli`

Once you have started the interpreter, you can enter prolog text into it.
If you want to make query, prepend '?-' before the query.

### Library

You can use JPrologMin as a library too.