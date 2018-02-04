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

## Features

Supported directives:
- discontiguous/1
- initialization/1
- ensure_loaded/1
- char_conversion/1
- include/1
- dynamic/1
- op/3
- multifile/1

Supported control constructs:
- ,/2
- ->/2
- throw/1
- true/0
- !/0
- fail/0
- catch/3
- call/1
- ;/2

Supported buildin predicates:
- @>/2
- current_prolog_flag/2
- retract/1
- fail_if/1
- real/1
- unify_with_occurs_check/2
- atom/1
- halt/1
- halt/0
- assertz/1
- number_chars/2
- ==/2
- compound/1
- var/1
- is/2
- bagof/3
- copy_term/2
- atomic/1
- abolish/1
- atom_concat/3
- repeat/0
- >/2
- findall/3
- atom_length/2
- char_code/2
- set_prolog_flag/2
- setof/3
- arg/3
- once/1
- =../2
- atom_codes/2
- atom_chars/2
- clause/2
- sub_atom/4
- functor/3
- =:=/2
- asserta/1
- current_predicate/1
- integer/1
- number_codes/2
- @</2
- </2

Supported operator:
- atan/1
- '**'/2
- abs/1
- -/2
- +/2
- truncate/1
- '/\'/2
- '\/'/2
- '\'/1
- '/'/2
- float/1
- sqrt/1
- round/1
- '//'/2
- '*'/2
- mod/2
- sign/1
- float_round/2
- >>/2
- float_integer_part/1
- float_fractional_part/1
- sin/1
- -/1
- <</2
- exp/1
- float_truncate/2
- floor/1
- log/1
- ceiling/1
- rem/2
- cos/1

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

### API

The API of JPrologMin is contained in the package `com.github.chungkwong.jprologmin`.

Since JPrologMin have implemented the javax.script API of the Java platform,
you should be familar to the usage. For example, a naive Prolog interpreter
may look like:

```java
public static void main(String[] args) throws ScriptException{
	Scanner in=new Scanner(System.in);
	PrologEngine processor=(PrologEngine)PrologEngineFactory.INSTANCE.getScriptEngine();
	while(in.hasNextLine()){
		Iterator<Substitution> iterator=(Iterator<Substitution>)processor.eval(in.nextLine());
		if(iterator!=null){
			while(iterator.hasNext()){
				System.out.println(iterator.next().toStringUser());
			}
		}
	}
}
```

Note that `eval` return null if the script ends with a query that failed
or the script do not ends with a query, otherwise it will return a Iterator
and you can access the substitutions made each time the query being reexecuted.

If you want to use Prolog on computer-generated data, you can bypass the step 
parse by providing internal representation of the data.