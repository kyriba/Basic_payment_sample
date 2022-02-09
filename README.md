# Basic_payment_sample

## Requirements

Building the API client library requires:
1. JDK 1.8 or higher - [Installation](https://www.oracle.com/java/technologies/javase-downloads.html)
2. Gradle - [Installation](https://gradle.org/install/)
3. JRE 1.8 or higher - [Installation](https://java.com/download)

> :warning: *As you also have JRE installed, you should create a new environment variable for Java and set a new path for the PATH environment to the JDK folder in "Program Files".
> Guides for creating and setting a new path: 
> [Windows](https://stackoverflow.com/a/35623142) 
> / [Unix](https://linuxize.com/post/how-to-set-and-list-environment-variables-in-linux/?__cf_chl_managed_tk__=2d828b14fa177654a647a55ddfd6c806684ba724-1623071966-0-AQljDh9QvmnbGDiK9JEyOIzDsAnC1zFP3J8sHt1Rq5PuJ6vmiibCZCiYTQF1w-VaFoGl2Xp0uOYGFGH7uaNxWbX-S7qW84E3xKzPpdm2g1alzFj3X-mlJGemcio1_AcmIw1T1P_SjgjqhTWCRjTV6-aw39KSFnFtNmcori6DHk9fiRCPHFJWiqJ8bE5Ps4Z0BW0SLQ0M08ZI_-zne14-sqX6I0VyKLPh_43Y8U_KQPgVpSHvPyh2hhPmEWmrymHEzTb9fC2qNwtHXI81nbqj2s8BiilvJ-NAhFB7dQ4_nwY7hCOda0XP6fSicXuBHKrJChBE4ynd_7Kk1BJizfvB0zAx5OCxKPGJTJCiLHKj4Ompnrxb229jpGc6p4JfBd9Oz-J-7HAN81SAQxyONgSGp5fYGSGzedoL5jOgioCIoQvTq0ce3hFDGpBaz1ShHym71eixeNjJAk2m7cNHVwSfhqM-jAUPRFANj_QLIzuwkxy_pdb3kZ5mH1GzKT0gXH_rfMSctm8-PkHn0Yzgjr3ne8I9de0df7-8EOA53Qw5Zq0Ed6Yw-evxD7TJuFKspdjUe6ZdbdsmrjHgPZl7WBaNKGhNDpHZxWRA_R5TDqH57oqtngzMW8IsEwQSXmIZToWCoU4SM15_D2SL_SNU2OAwslmmg0-8z8fMQ9nC4MvIDB_RAubUFonkPL60VTu10xg4XmahsxBbF8SNKe_INR0bLBOLZmVA0ijhD_h1-UusutbdarDHKuxaursdW6Jb8gcn3A) 
> / [MacOS](https://apple.stackexchange.com/a/229941)*

> :warning: *After creating a new environment variable for JDK, create a new environment variable for JRE in the same way.
> Name this variable JAVA_JRE (if name is other than Runner.exe file will not execute)*
## Installation

To clone the repository locally, copy the link of the repository, open command prompt, go to the location where the project will be installed and execute git clone command:

```git
git clone https://github.com/kyriba/Basic_payment_sample.git
```

> :warning: *Before building the project, go to .../root/application.yml and update **client.id** and **client.secret** with your credentials used for Get Token method in Postman.*

Then, build the project by running **gradle_clean_build.bat** file for Windows or  **gradle_clean_build.bat** file for
Unix-like operating systems.

After that, run **Runner.exe** file to start the application. After few seconds, the following prompt will appear, signilizing that the application is ready to be used:

```git
Enter command you want to execute. To stop the application enter "exit". To learn about available functions enter "-help".
```

Alternatively, the application can be launched by running jar file:

```git
java -jar root/build/libs/tesco-api-poc.jar
```
