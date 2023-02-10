Feature: Admin is logged in and access following api

  Scenario: The admin send a request to login
    Given the admin in the database are the following
#     | name |  username  | password  |
      |haridy|  haridy    | haridy    |
      |ehab  |  ehab      | 0000      |
    When the admin try to log in with username haridy and password haridy
    Then the server allow this request
