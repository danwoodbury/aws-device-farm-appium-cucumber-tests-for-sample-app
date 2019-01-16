@Login
Feature: Testing the Login screen Functionalities
    In order to test the Login functionalities as a client/pro user I want to able to use the login screen

    Background: A Login Page
        Given I navigate to the login page

    @TC_Login_001 @Smoke @Regression
    Scenario: To verify Client user is able to login the application with a valid Username and Password
        Given the client user enters a valid username and password
        When the user clicks on the login button
        Then the client user is successfully logged in

    @TC_Login_002 @Smoke @Regression
    Scenario: To verify Pro user is able to login the application with a valid Username and Password
        Given the pro user enters a valid username and password
        When the user clicks on the login button
        Then the pro user is successfully logged in

    @TC_Login_003 @Smoke @Regression
    Scenario: To verify when user clicks on login button without entering the Email and password
        Given the user clicks on the login button
        Then the error messages are displayed

    @TC_Login_004 @Regression
    Scenario: To verify user email address enetered is not valid
        Given the user enters an invalid email
        When the user clicks on the login button
        Then the invalid email error message is displayed

    @TC_Login_005 @Regression
    Scenario: To verify error pop-up is displayed when user enters valid Email and Invalid Password
        Given the user enters an invalid password
        When the user clicks on the login button
        Then the invalid password error meesage is displayed

    @TC_Login_006 @Smoke @Regression
    Scenario: To verify "Try Again" button functionality on the Error popup displayed in the login screen when the user enters the Invalid Username and Password
        When the client user enters a invalid username and password
        When the user clicks on the login button
        And the user clicks on the try again button
        Then the login screen is displayed
