# Getting Started


### Prerequisites

 - Java 17 or newer
 - To run the test you need Junit 5 or newer

### Using this library

Here is an example how to use this library.


A POJO Class representing data:

```java
public class Person {

	private int age;
	private String firstname;
	private String lastname;
	private boolean hasCar;
	private LocalDate birthday;
	private double income;
	
	... // Constructor
	... // getters and setters
	
	}
```
 
A service class representing the API call: 
 
```java

	List<Person> content = new ArrayList<>();
	content = new ArrayList<>();
	content.add(new Person(18, "Maik", "Muster", false, LocalDate.of(2001, 1, 12), 120.21));
	content.add(new Person(38, "Lisa", "Schuster", true, LocalDate.of(1981, 1, 15), 3010.45));
	
	// This list containing the cell name a function and a formatter(optional)
	// For each column you would like to print,
	// you should specify a name of the cell(first line), a function which will be called on the content.
	// The formatter formats the output of the function to a String
	List<Column<Person>> functions = new ArrayList<>();
	functions = new ArrayList<>();
	functions.add(new Column<>("Age", Person::getAge));
	functions.add(new Column<>("Firstname", Person::getFirstname));
	functions.add(new Column<>("Lastname", Person::getLastname));
	functions.add(new Column<>("Married ?", Person::isMarried, new BooleanYesNoFormatter()));
	functions.add(new Column<>("Birthday", Person::getBirthday, new LocalDateFormatter("dd.MM.yyyy")));
	functions.add(new Column<>("Income in €", Person::getIncome, new FloatFormatter()));
	
	// Initialize Printer with content
	Printer printer = CsvPrinterFactory.getInstance(new CsvBuilder<>(content, functions));
	
	// Call the function 
	byte[] csvFile = printer.print();
```

Output :

```
	Age;Firstname;Lastname;Married ?;Birthday;Income in €
	18;Maik;Muster;no;12.01.2001;120,21
	38;Lisa;Schuster;yes;15.01.1981;3010,45
```


### Advanced examples and tips

You can build your own Formatter by implementing the interface **Formatter.java**   
(This class is already shipped with this library.)

The interface class:

```java
	public interface Formatter {
	
		String format(Object object);
	
	}
```

You can also modify the **line breaks**, **delimiter** and value **quotes** of the csv file.  
Just use the optional functions of the Builder class.

Examples:

```java

	CsvBuilder builder = new CsvBuilder<>(content, functions).lineBreak("\r\n");
	
	CsvBuilder builder = new CsvBuilder<>(content, functions).delimiter(":");
	
	CsvBuilder builder = new CsvBuilder<>(content, functions).quote("\"");
	
	CsvBuilder builder = new CsvBuilder<>(content, functions).lineBreak("\r\n").delimiter(":").quote("\"")
	
	CsvPrinter printer = CsvPrinterFactory.getInstance(builder);
	
	printer.print();
	
```

### System settings when running tests
In order to see DEBUG messages, you would need to pass in this System Property at your Java startup.

```
-Dorg.slf4j.simpleLogger.defaultLogLevel=DEBUG
```