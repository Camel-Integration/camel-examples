# Number Conversion Module
This module is built as a java library. It can be used by other modules or applications.<br>
It contains two camel routes:
* `number-to-words` - converts numbers words
* `number-to-dollars` - converts numbers to dollars

The whole idea is to have a java library that can be used by other modules or applications.<br>
This module is not runnable by itself, just packaged as a library.<br>


## How use it

### Build
```bash
mvn clean install
```

### Add as a dependency
In `pom.xml`:
```xml
<dependencies>    
    <dependency>
        <groupId>org.example.module.numberconversion</groupId>
        <artifactId>number-conversion-module</artifactId>
        <version>1.0-SNAPSHOT</version>
    </dependency>
</dependencies>
```



