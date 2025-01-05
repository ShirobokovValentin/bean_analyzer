# BeanAnalyzer
A lightweight library bean analyzer designed to simplify server-side backoffice development 
by providing tools for object analysis, descriptor generation, reconstruction, 
and dynamic manipulation of fields and collections.

## Description
BeanAnalyzer was initially created as the backend for a backoffice application 
to provide a universal interface for configuring and managing objects. 
It enables developers to extract metadata, generate structured descriptors, 
and reconstruct objects from descriptors, making it easier to build dynamic user interfaces.

## Features
- **Object Analysis**: Extract metadata and generate structured descriptors for objects.
- **Reconstruction**: Recreate objects from their descriptors with precise mapping of fields and types.
- **Dynamic Manipulation**: 
  - Modify field values.
  - Add or remove elements in collections.
- **Type-Specific and Generic Support**: Includes specialized analyzers for specific types as well as a universal analyzer for general objects.
- **Extensibility**: Easily add custom analyzers and handlers for new types.

## Use Case
The primary use case for BeanAnalyzer is as the server-side engine for backoffice applications.
It facilitates the creation of dynamic, universal user interfaces for configuring and 
managing objects without requiring predefined knowledge about their structure.


## Installation

### From Source
To install the libraryConfig by compiling the source code:
1. Clone the repository:
    ```bash
    git clone https://github.com/ShirobokovValentin/bean_analyzer.git
    ```
2. Navigate to the project directory:
    ```bash
    cd bean_analyzer
    ```
3. Compile and install the libraryConfig locally:
    ```bash
    mvn install
    ```

### Maven
Add the following dependency to your `pom.xml`:
```xml
<dependency>
    <groupId>io.github.shirobokovvalentin</groupId>
    <artifactId>bean_analyzer</artifactId>
    <version>2.0</version>
</dependency>
```

### Gradle
Add the following to your `build.gradle`:
```gradle
implementation 'io.github.shirobokovvalentin:bean_analyzer:2.0'
```


## Usage

### Creating an ObjectDescriptor

```java {title="Main.java"}
import io.github.shirobokovvalentin.bean_analyzer.BeanAnalyzer;
import io.github.shirobokovvalentin.bean_analyzer.descriptors.ObjectDescriptor;

public class Main
{
    public static void main(String[] args) 
    {
        AppConfig appConfig = new AppConfig();
        ObjectDescriptor appConfigDescriptor =  BeanAnalyzer.getInstance().inspect(appConfig);
    }
}
```

### Creating an ObjectDescriptor from CreationMethod
```java {title="Main.java"}

import io.github.shirobokovvalentin.bean_analyzer.BeanAnalyzer;
import io.github.shirobokovvalentin.bean_analyzer.descriptors.Argument;
import io.github.shirobokovvalentin.bean_analyzer.descriptors.CreationMethod;
import io.github.shirobokovvalentin.bean_analyzer.descriptors.ObjectDescriptor;

public class Main
{
    public static void main(String[] args) 
    {
        CreationMethod createStringByConstructor = new CreationMethod()
                .type(String.class);

        CreationMethod createStringByConstructorWithArg = new CreationMethod()
                .type(String.class)
                .addArgument(new Argument<String>(String.class, "string from constructor"));

        CreationMethod creteStringByFactoryMethodWithArg = new CreationMethod()
                .type(String.class)
                .factoryMethod("valueOf")
                .addArgument(new Argument<Integer>(int.class, 9999));

        ObjectDescriptor fromConstructor = BeanAnalyzer.getInstance().create(createStringByConstructor);
        ObjectDescriptor fromConstructorWithArgs  = BeanAnalyzer.getInstance().create(createStringByConstructorWithArg);
        ObjectDescriptor fromFactoryMethodWithArgs  = BeanAnalyzer.getInstance().create(creteStringByFactoryMethodWithArg);
    }
}
```

### Restore an object from an ObjectDescriptor

The class being restored must have a default constructor

```java {title="Main.java"}
import io.github.shirobokovvalentin.bean_analyzer.BeanAnalyzer;
import io.github.shirobokovvalentin.bean_analyzer.descriptors.ObjectDescriptor;

public class Main
{
    public static void main(String[] args)
    {
        AppConfig appConfig = new AppConfig();
        ObjectDescriptor appConfigDescriptor = BeanAnalyzer.getInstance().inspect(appConfig);
        Object obj = BeanAnalyzer.getInstance().restore(appConfigDescriptor);
    }
}
```

### ObjectDescriptor manipulation

- `delete` - deletes field values.
- `put` - Creates a new field value. Not applicable for collections.
- `post` - Adds a new value to the collection. Not applicable for other types..


```java {title="Main.java"}
import io.github.shirobokovvalentin.bean_analyzer.BeanAnalyzer;
import io.github.shirobokovvalentin.bean_analyzer.descriptors.Argument;
import io.github.shirobokovvalentin.bean_analyzer.descriptors.CreationMethod;
import io.github.shirobokovvalentin.bean_analyzer.descriptors.ObjectDescriptor;

import java.util.List;

public class Main
{
    public static void main(String[] args)
    {
        Main.AppConfig appConfig = new Main.AppConfig();

        ObjectDescriptor appConfigDescriptor = BeanAnalyzer.getInstance().inspect(appConfig);

        CreationMethod createStringByConstructor = new CreationMethod()
                .type(Main.AppConfig.AdConfig.class)
                .addArgument(new Argument<Integer>(int.class, 300));

        appConfigDescriptor.delete("str0");
        appConfigDescriptor.put("str1", "new string");

        appConfigDescriptor.put("adConfig", createStringByConstructor);
        appConfigDescriptor.put("adConfig/number", 42);

        appConfigDescriptor.post("adConfigList", createStringByConstructor);
        appConfigDescriptor.post("adConfigList", new Main.AppConfig.AdConfig());

        Object obj = BeanAnalyzer.getInstance().restore(appConfigDescriptor);
    }

    static class AppConfig
    {
        String str0 = "default-string";
        String str1 = "default-string";
        AdConfig adConfig;
        List<AdConfig> adConfigList;
        static public class AdConfig
        {
            int number = 0;
            AdConfig() {}
            AdConfig(int number) {this.number = number;}
        }
    }

}
```
### Annotation Overview

#### `@ViewDescription`
Fills in the description field in the descriptor.
This annotation is used to provide meaningful textual information about the field or object that it decorates. 
It helps clients understand the purpose of the field.

#### `@Required`
Sets the required field of the descriptor to `true`.
Indicates that the annotated field must be present and cannot have a null or default value in the generated descriptor.
#### `@ViewIgnore`
Excludes the marked field from analysis when creating the descriptor.
Fields marked with this annotation will not appear in the descriptor, 
useful for fields that should not be exposed in the API schema.
#### `@ViewId`
Ensures the uniqueness of the field within its context.
This annotation is often used for fields that serve as unique identifiers, 
ensuring that their values are distinct within the scope of the object or collection.


### Example
```json lines
{
  "type": {
    "category": "OBJECT",
    "class": "org.example.AppConfig"
  },
  "required": false,
  "fields": {
    "str0": {
      "type": {
        "category": "STRING",
        "class": "java.lang.String"
      },
      "value": "default-string",
      "required": false
    },
    "listInnerConfig": {
      "type": {
        "category": "LIST",
        "class": "java.util.ArrayList",
        "variants": [
          { "type": "org.example.AdConfig" },
          { "type": "org.example.ScreenConfig" },
          { "type": "java.lang.Object" }
        ]
      },
      "required": false
    }
  }
}


```

### Fields
#### ObjectDescriptor

| Field            | Type                            | Required | Description                                    |
|------------------|---------------------------------|----------|------------------------------------------------|
| `type`           | `TypeDescriptor`                | Yes      | The type of the object.                        |
| `value`          | `Object`                        | No       | The value of field                             |
| `required`       | `boolean`                       | No       | Marks the element as required.                 |
| `description`    | `String`                        | No       | A human-readable description of the field.     |
| `fields`         | `Map<String, ObjectDescriptor>` | No       | A map of object fields or collection elements. |


#### TypeDescriptor
Type descriptor

| Field         | Type                  | Required | Description                                             |
|---------------|-----------------------|----------|---------------------------------------------------------|
| `category`    | `Category`            | Yes      | Defines the type category (e.g., STRING, LIST, OBJECT). |
| `type`        | `Class`               | Yes      | Specifies the Java type of the field.                   |
| `options`     | `Set<String>`         | No       | Enum options applicable to the field.                   |
| `variants`    | `Set<CreationMethod>` | No       | Methods for creating objects of this type.              |

#### CreationMethod
Descriptor for creating a class of a given type

| Field           | Type                      | Required | Description                           |
|-----------------|---------------------------|----------|---------------------------------------|
| `type`          | `Class`                   | Yes      | The Java class of the created object. |
| `factoryMethod` | `String`                  | No       | The name of the factory method.       |
| `arguments`     | `Set<List<Argument<?>> >` | No       | A list of arguments for the method.   |

#### Argument
Argument for a constructor or factory method

| Field            | Type                      | Required      | Description                  |
|------------------|---------------------------|---------------|------------------------------|
| `type`           | `Class`                   | Yes           | The type of the argument.    |
| `value`          | `Object`                  | Yes           | The value of the argument.   |



## Requirements
- Java 6 or higher

## License
ActionDispatcher is licensed under the Apache License Version 2.0. See [LICENSE](LICENSE) for details.

## Contact
For questions or suggestions, please contact or open an issue on GitHub.