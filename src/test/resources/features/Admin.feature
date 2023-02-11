Feature: Admin access api

  Scenario: The admin send a request to login
    Given the admin table in the database contain are the following
#     | name |  username  | password  |
      |haridy|  haridy    | haridy    |
      |ehab  |  ehab      | 0000      |
    When the admin try to log in with username haridy and password haridy
    Then the server allow this request

  Scenario: The admin send a request get a passenger using the username
    Given the passengers in the database are the following
      |firstName|lastName|phoneNumber|type   |city |gender|birthDay  |username|password|
      |Mona     |Mahmoud |12311561655|Regular|Cairo|F     |2023-02-10|Mona    |omar    |
      |ALy      |Khaled  |01256156165|Regular|Cairo|M     |2023-02-10|ALy     |omar    |
      |Waled    |Yahia   |01254556464|Regular|Giza |M     |2023-02-10|Waled   |omar    |
      |Omar     |Fawzy   |01251253311|Regular|Cairo|M     |2023-02-10|Omar    |omar    |
    And them wallets in the database are the following
      |   creditCard   |  balance  |
      |4532771649784837|  157621   |
      |4539210009944643|  9787744  |
      |4485467874182352|  4657     |
      |4489781224672352|  23       |
    And the admin logged in with username haridy and password haridy

    When the admin request a passenger with username Waled
    Then the server response with the following passenger
      |firstName|lastName|phoneNumber|type   |city |gender|birthDay  |username|password|
      |Waled    |Yahia   |01254556464|Regular|Giza |M     |2023-02-10|Waled   |omar    |
    And this passenger wallet is
      |   creditCard   |  balance  |
      |4485467874182352|  4657     |
