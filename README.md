# due-date-application

Due Date Application

Please perform the following steps to be able to run the application:

1. Install Git Client. For this, please search Internet to find the appropriate application for your operational system.

2. Install JDK (version 8 or higher). For this, please follow instructions at the page
   https://docs.oracle.com/en/java/javase/17/install/overview-jdk-installation.html

3. Install the Apache Maven build tool. For this, please follow instructions at the page
   https://maven.apache.org/install.html

4. Clone the given Git repository and change to the cloned directory in the appropriate shell or command line prompt:

```
git clone https://github.com/juliaom/due-date-application.git
cd due-date-application
```

5. Run the following command in the appropriate shell or command line prompt:

```
mvn clean package assembly:single
```

6. Run the application:

```
cd target
java -jar due-date-application-jar-with-dependencies.jar
```

7. Enjoy! :)
