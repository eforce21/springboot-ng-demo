####Introduction 

This project provides an example use of the technology stack that eForce21 uses to implement web projects.
The following sections describe the needed tooling and the steps that are needed to install and run the different parts 
of the application.

####Setup of the development environment

1. Java/JDK: The minimum required Java version needed to run the backend application is Java 8. Please refer to the
[Oracle download site](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) to download the
correct JDK for your platform and execute the according installer.
2. Maven: The application uses Maven as its dependency management and build tool. Maven can be downloaded [here](http://maven.apache.org/download.cgi). 
There is a package for every major operating system. If you need any help installing Maven please refer to this [site](http://maven.apache.org/install.html).
After installing Maven you can verify the installation by entering ```mvn -v``` into you CMD/Bash window. You should get something like this:
```
Apache Maven 3.3.9 (bb52d8502b132ec0a5a3f4c09453c07478323dc5; 2015-11-10T17:41:47+01:00)
Maven home: /Users/bbuchner/apache-maven-3.3.9
Java version: 1.8.0_121, vendor: Oracle Corporation
Java home: /Library/Java/JavaVirtualMachines/jdk1.8.0_121.jdk/Contents/Home/jre
Default locale: en_US, platform encoding: UTF-8
OS name: "mac os x", version: "10.13.2", arch: "x86_64", family: "mac"
```
If you cannot see this output or you see some sort of error message please make sure that you have added Maven to your system path.


3. git: You will need the source code management tool git to work with the source code of the application. [Download](https://git-scm.com/download)
git for your operating system and install accordingly.
4. The frontend application uses npm as its package manager. To get and use npm you need to [download](https://nodejs.org/en/download/package-manager/)
and install Node using the according Node installer for your operating system.
5. The frontend being an Angular application it is required to install the Angular CLI. The Angular CLI is a command line tool
that helps creating the different parts of an Angular app, executes builds and runs a small development server with live reload.
The following steps describe the installation process.

a) Install the Angular CLI by typing the following command into your CMD/Bash:

```
npm install -g @angular/cli
```
**Note**: Do not forget to use the flag ```-g```. This flag installs the CLI globally and hereby enables you to use the
CLI commands from anywhere in your system.

b) Verify your installation by typing the following command into your CMD/Bash:
```
ng -v
```
Your output should look something like this:
```
    _                      _                 ____ _     ___
   / \   _ __   __ _ _   _| | __ _ _ __     / ___| |   |_ _|
  / △ \ | '_ \ / _` | | | | |/ _` | '__|   | |   | |    | |
 / ___ \| | | | (_| | |_| | | (_| | |      | |___| |___ | |
/_/   \_\_| |_|\__, |\__,_|_|\__,_|_|       \____|_____|___|
               |___/

Angular CLI: 1.6.0
Node: 7.6.0
OS: darwin x64
Angular:
```

If you see an error please check if you have installed the Angular CLI using the ```-g``` flag.

####IDE recommendations

We would recommend that you use Microsoft's free editor Visual Studio Code for development. Visual Studio supports both 
Java and Angular. If you are already comfortable with Eclipse or run into any issues with Java in Visual Studio Code 
we would recommend that you use Eclipse for Java development and install Visual Studio Code for frontend development 
as the support for Angular is not optimal in Eclipse.

**Note:** Refer to [this](https://code.visualstudio.com/docs/languages/java) page if you need any assistance with enabling
Java in Visual Studio Code.

####Application build and execution

After cloning the project you need to download all needed dependencies and thus preparing the application for execution.

**1. Frontend**
1. Make sure that you are located in the root folder of the application with your CMD/Bash.
2. Type ```cd webscale-ui``` and press enter
3. Your are now in the root folder of the frontend part of the application. To download the needed dependencies enter the
following command in you CMD/Bash ```npm install```. This process might take a few minutes.

**2. Backend**
1. Go back into the root directory of the application by entering ```cd ..``` in your CMD/Bash.
2. Enter ```cd webscale-classic``` to get to the root folder of the backend application.
3. Enter command ```mvn clean package``` into your CMD/Bash. This will trigger the Maven build. It should pass without
any errors.

You are now ready to start the application. Start the backend application in your chosen IDE. Refer to the documentation
of your IDE if you need any help with starting a Java Application from the IDE. To start the frontend application follow
these steps:

1. Navigate into the directory ```webscale-ui``` with your CMD/Bash
2. Execute the command ```ng serve --proxy-conf=proxy.conf.json``` in the directory you have navigated into.
**Note:** The command must be executed in the root directory of the frontend application otherwise the Angular CLI will
cannot start the development server.
3. Navigate to the URL ```http://localhost:4200``` using a browser of your choice.
4. You should see the applications landing page
5. Open the login window by klicking on the login link in the top left corner
6. Username is ```admin``` and the password is ```password```.
7. Enjoy :) 
