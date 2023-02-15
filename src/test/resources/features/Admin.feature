Feature: Admin access api

  Scenario: The admin send a request to login
    Given the admin table in the database contain are the following
#     | name |  username  | password  |
      | haridy | haridy | haridy |
      | ehab   | ehab   | 0000   |
      | admin  | admin  | admin  |
    When the admin try to log in with username admin and password admin
    Then the server allow this request


  Scenario: The admin send a request get a passenger using the username
    Given the passengers in the database are the following
      | firstName | lastName | phoneNumber | type    | city  | gender | birthDay   | username | password | email           |
      | Mona      | Mahmoud  | 12311561655 | Regular | Cairo | F      | 2023-02-10 | Mona     | omar     | mona@gmail.com  |
      | ALy       | Khaled   | 01256156165 | Regular | Cairo | M      | 2023-02-10 | ALy      | omar     | aly@gmail.com   |
      | Waled     | Yahia    | 01254556464 | Regular | Giza  | M      | 2023-02-10 | Waled    | omar     | waled@gmail.com |
      | Omar      | Fawzy    | 01251253311 | Regular | Cairo | M      | 2023-02-10 | Omar     | omar     | omar@gmail.com  |
    And them wallets in the database are the following
      | creditCard       | balance |
      | 4532771649784837 | 157621  |
      | 4539210009944643 | 9787744 |
      | 4485467874182352 | 4657    |
      | 4489781224672352 | 23      |
    And the admin logged in with username admin and password admin
    When the admin request a passenger with username Waled
    Then the server response with the following passenger
      | firstName | lastName | phoneNumber | type    | city | gender | birthDay   | username | password | email           |
      | Waled     | Yahia    | 01254556464 | Regular | Giza | M      | 2023-02-10 | Waled    | omar     | waled@gmail.com |
    And this passenger wallet is
      | creditCard       | balance |
      | 4485467874182352 | 4657    |


  Scenario: The admin send a request get all the passengers
    Given the passengers in the database are the following
      | firstName | lastName | phoneNumber | type    | city  | gender | birthDay   | username | password | email           |
      | Mona      | Mahmoud  | 12311561655 | Regular | Cairo | F      | 2023-02-10 | Mona     | omar     | mona@gmail.com  |
      | ALy       | Khaled   | 01256156165 | Regular | Cairo | M      | 2023-02-10 | ALy      | omar     | aly@gmail.com   |
      | Waled     | Yahia    | 01254556464 | Regular | Giza  | M      | 2023-02-10 | Waled    | omar     | waled@gmail.com |
      | Omar      | Fawzy    | 01251253311 | Regular | Cairo | M      | 2023-02-10 | Omar     | omar     | omar@gmail.com  |
    And them wallets in the database are the following
      | creditCard       | balance |
      | 4532771649784837 | 157621  |
      | 4539210009944643 | 9787744 |
      | 4485467874182352 | 4657    |
      | 4489781224672352 | 23      |
    And the admin logged in with username admin and password admin
    When the admin request to get all the passengers
    Then the server response with the following passengers
      | firstName | lastName | phoneNumber | type    | city  | gender | birthDay   | username | password | email           |
      | Mona      | Mahmoud  | 12311561655 | Regular | Cairo | F      | 2023-02-10 | Mona     | omar     | mona@gmail.com  |
      | ALy       | Khaled   | 01256156165 | Regular | Cairo | M      | 2023-02-10 | ALy      | omar     | aly@gmail.com   |
      | Waled     | Yahia    | 01254556464 | Regular | Giza  | M      | 2023-02-10 | Waled    | omar     | waled@gmail.com |
      | Omar      | Fawzy    | 01251253311 | Regular | Cairo | M      | 2023-02-10 | Omar     | omar     | omar@gmail.com  |
    And this passengers wallets are
      | creditCard       | balance |
      | 4532771649784837 | 157621  |
      | 4539210009944643 | 9787744 |
      | 4485467874182352 | 4657    |
      | 4489781224672352 | 23      |