# EmailValidation
This project provides the solution to verify email body and subject


## Added Page factory Implementation

```sh
If you use the PageFactory, you can assume that the fields are initialised. If you don't use the PageFactory, then NullPointerExceptions will be thrown if you make the assumption that the fields are already initialised
```

```sh
Introduced seperate page constants and page classes . This will bring more readability and usablity for the users.
```

## Reusability of the methods

```sh
Created a separate util SeleniumUtil for basic UI operations. This will bring more re usability for the user who is writing the tests. 
```

## Data Parameterization

```sh
Instead of hard coding ,using TestNG data provider concept for parameterizing the test data . Test data is present as a part of JSON file . Each test should have a separate JSON test data file.
```
```sh
Using properties file for test configuration. Using spring for loading the properties file.
```
 
## WebDriver handling for specific browser

```sh
BrowserFactory class returns WebDriver reference for specific browser passed from the properties file.
```

## Driver handling

```sh
TestBase class is created to get the driver reference returned from the BrowserFactory class. Each test should extend TestBase.
In this way we can avoid handling the driver in the test class.
```


## WebDriverManager
```sh
Managing the executables will become very difficult as you need to update the drivers manually as soon as new version  of browser gets released. WebDriverManager handles the automatic configuration of the executables for you . Depending upon the machine you are executing your test ,WDM downloads the compatible driver version for your browser. There is no need to specifically define the platform, it is smart enough to identify the platform you  are executing your tests.Hence reducing your code to big extent. You can also define the architecture and restrict the WDM to use the specific version for the driver also.
```

## NADA API
Using NADA API you  could request for any email address!Making a GET request with an email address creates an instant Inbox for the email address.
```
https://getnada.com/api/v1/inboxes/{email-id}
```

Inbox API gives all the list of email messages we have received for the email. If we need to get the email content, we need to get the ‘uid’  of the specific email message, then call this API.


```
https://getnada.com/api/v1/messages/{message-id}
```


## Execution

First of all open cmd or terminal from the root directoty of the project now run below command
```
mvn clean install -DskipTests=true
```
It will download all the dependencies from the maven central repository . Now execute the test by running below command from cmd or terminal
```
mvn clean test
```



