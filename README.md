# Getting Started


### Prerequisites

 - Java 8 or newer
 - To run the test you need Junit 5 or newer

### Using this library

Here is an example how to use this library.


A POJO Class representing data:

```javascript
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
 
```javascript
	//This will be the first line in the csv file
	// The order in the list represent the order of the columns in the file
	List<String> header = new ArrayList<>();
	header.add("Age");
	header.add("Firstname");
	header.add("Lastname");
	header.add("Married ?");
	header.add("Birthday");
	header.add("Income in €");
	
	List<Person> content = new ArrayList<>();
	content = new ArrayList<>();
	content.add(new Person(18, "Maik", "Muster", false, LocalDate.of(2001, 1, 12), 120.21));
	content.add(new Person(38, "Lisa", "Schuster", true, LocalDate.of(1981, 1, 15), 3010.45));
	
	// This list containing a Function and a Formatter(optional)
	// For each Column you would like to print,
	// you should specify a function which will be called on the content
	// The Formatter formats the output of the Function to a String
	List<Column<Person>> functions = new ArrayList<>();
	functions.add(new Column<>(Person::getAge));
	functions.add(new Column<>(Person::getFirstname));
	functions.add(new Column<>(Person::getLastname));
	functions.add(new Column<>(Person::isMarried, new BooleanYesNoFormatter()));
	functions.add(new Column<>(Person::getBirthday, new LocalDateFormatter("dd.MM.yyyy")));
	functions.add(new Column<>(Person::getIncome, new FloatFormatter()));
	
	// Initialise CSVPrinter with content
	CsvPrinter printer = CsvPrinterFactory.getInstance(new CsvBuilder<>(header, content, functions));
	
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

```javascript
	public interface Formatter {
	
		String format(Object object);
	
	}
```

You can also modify the **line breaks**, **delimiter** and value **quotes** of the csv file.  
Just use the optional functions of the Builder class.

Examples:

```javascript
	CsvBuilder builder = new CsvBuilder<>(header, content, functions).lineBreak("\r\n");
	
	CsvBuilder builder = new CsvBuilder<>(header, content, functions).delimiter(":");
	
	CsvBuilder builder = new CsvBuilder<>(header, content, functions).quote("\"");
	
	CsvBuilder builder = new CsvBuilder<>(header, content, functions).lineBreak("\r\n").delimiter(":").quote("\"")
	
	CsvPrinter printer = CsvPrinterFactory.getInstance(builder);
	
	printer.print();
	
```

### System settings when running tests
In order to see DEBUG messages, you would need to pass in this System Property at your Java startup.

```
-Dorg.slf4j.simpleLogger.defaultLogLevel=DEBUG
```