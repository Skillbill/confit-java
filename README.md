# confit-java
confit configurations loader in java

## Build
mvn package

You'll find the jar under target/confit-java-1.0.jar

## Usage
Sample Main class that loads a configuration by alias:
```java
import net.skillbill.confit.ConfitReader;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        ConfitReader confitReader = ConfitReader.getConfByAlias(
                "uuid",
                "alias",
                "ref",
                "secret"
        );
        try (Scanner scanner = new Scanner(confitReader.getResponse())) {
            String responseBody = scanner.useDelimiter("\\A").next();
            System.out.println(responseBody);
        }
        confitReader.getResponse().close();
    }
}
```