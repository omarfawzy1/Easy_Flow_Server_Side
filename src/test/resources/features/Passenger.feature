Feature: passenger access api

  Background:
    Given the passengers in the database are the following
      |firstName|lastName|phoneNumber|city |gender|birthDay  |username|password|email          |
      |Mona     |Mahmoud |12311561655|Cairo|F     |2023-02-10|Mona    |omar    |mona@gmail.com |
      |ALy      |Khaled  |01256156165|Cairo|M     |2023-02-10|ALy     |omar    |aly@gmail.com  |
      |Waled    |Yahia   |01254556464|Giza |M     |2023-02-10|Waled   |omar    |waled@gmail.com|
      |Omar     |Fawzy   |01251253311|Cairo|M     |2023-02-10|Omar    |omar    |omar@gmail.com |
    And them wallets in the database are the following
      |   creditCard   |  balance  |
      |4532771649784837|  157621   |
      |4539210009944643|  9787744  |
      |4485467874182352|  4657     |
      |4489781224672352|  23       |
    Scenario: the passenger request is valid
      When the passenger send register request with the following data
        |firstName|lastName|phoneNumber|city |gender|birthDay  |username|password    |email         |
        |tony     |tony    |01270618534|Giza |M     |2023-02-11|tony    |tonyPassword|tony@gmail.com|
      Then the server response with status 200
    Scenario: the passenger request to register with the used username
      When the passenger send register request with the following data
        |firstName|lastName|phoneNumber|city |gender|birthDay  |username|password    |email          |
        |Mona     |tony    |01270618534|Giza |M     |2023-02-11|Mona    |tonyPassword|tony@gmail.com|
      Then the server response with status 404

