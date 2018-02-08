# Introduction

JCli is a Java annotation driven command line manipulation library. Today, there are more than enough command line argument tools for Java in open source world out there. JCli distincts from others for following reasons:

* Parsing rules defined entirely by annotation.
* Customized conversion rules defined by annotation using [Caff](https://github.com/jiaqi/caff/wiki).
* Multi-value option supported.

Initially JCli was part of [Jmxterm](https://github.com/jiaqi/jmxterm) project. The command line parsing logic has been gradually decoupled and now becomes an separated library that can easily fit in other command line parsing scenarios.

# Get started

## Define a POJO

The POJO doesn't have to extend any class or implements any interface. Make sure the fields you want to set or get are correctly defined in POJO class.

```java
public class APojo {
  private int level;
  ...
  public int getLevel() { return level; }
  public void setLevel( int level ) { this.level = level; }
  ...
```

## Annotate options

Annotation defines which command line option is mapped to where. Add annotation around the getter or setter of field, if there are any options.

```java
...
import org.cyclopsgroup.jcli.annotation.*;
...
@Cli(name="test")
public class APojo {
...
@Option(name = "l", longName = "level", description = "A integer value")
public int getLevel() { return level; }
...
```

## Annotate argument

Argument is what's left after options are parsed from original command line input. It's often a multi-value list.

```java
...
private List<String> arguments = new ArrayList<String>();
@MultiValue
@Argument( description = "Left over arguments" )
public List<String> getArguments() { return arguments; }
...
```

## Parse arguments with ArgumentProcessor

With POJO and parsing rules in annotation, ArgumentProcessor class parses string array and set values against a POJO instance.

```java
import org.cyclopsgroup.jcli.ArgumentProcessor;
...
APojo pojo = new APojo();
ArgumentProcessor.newInstance( APojo.class ).process( args, pojo );
doSomethingWith( pojo.getLevel() );
...
```

# FAQ

## How to define option or argument that can take multiple values?

Add annotation @MultiValue to the setter method of option or arguments. Default collection passed to setter method is ArrayList. For example:

```java
@MultiValue
@Option(name="o", longName="option", description="Example option")
public void setOption(List<String> values) ...
```

You may customize the implementation of collection, or the type of element in collection, for example:

```java
@MultiValue(listType=LinkedList.class, valueType=Integer.class)
@Arguments
void setIntegerArguments(List<Integer> values)...
```

At this moment, multi-value can't be passed as array yet.

## How to define boolean option?

When option type is boolean, JCli automatically consider it as an option that doesn't take value. In following case option -f doesn't need value after the option in command line

```java
@Option(name="f", longName="flag", description="A flag option")
void setFlag(boolean flag)...
```

## How to customize conversion rule?

JCli internally use CAFF project for value conversion. Customized conversion rules can be defined with annotations, which can apply to the setter of option of argument. Take @DateField annotation as an example, following code takes syntax such as 20100401 and call setDate with converted Date object.

```java
@DateField(format="yyyyMMdd")
@Option(name="d", longName="date", description="Option with customized conversion")
void setDate(Date date) ...
```

The conversion annotation can combine with @MultiValue annotation, which defines the conversion rules for element in Collection. For example, following code defines a bean that takes list of dates as arguments

```java
@DateField(format="yyyyMMdd")
@MultiValue(valueType=Date.class)
@Arguments
void setDates(List<Date> dates) ...
```

# Development guide

Please follow [Google style guide](https://github.com/google/styleguide) when making a code change.
