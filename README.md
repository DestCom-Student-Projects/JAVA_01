# Exercices JAVA MT4 - 1

## Usage with maven (folder `hetic-calculator`)

configure the `application.properties` file to set the needed properties:

```bash
mvn clean install
java -jar target/hetic-calculator-1.0-SNAPSHOT.jar <number1> <operator> <number2>
```

## Usage without maven (folder `without_maven`)

```bash
java Calculator <number1> <operator> <number2>
```

or

```bash
java Calculator <folderPath>
```

With the `.op` file containing the operations in the following format:

```text
<number1> <operator> <number2>
<number1> <operator> <number2>
...
```
