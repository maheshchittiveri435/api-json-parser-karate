Feature: sample karate test script
  for help, see: https://github.com/karatelabs/karate/wiki/IDE-Support

  Background:
    * url 'https://jsonplaceholder.typicode.com'
  Scenario: Get data
    * url "https://postman-rest-api-learner.glitch.me"
    Given path '/info'
    And param Fid = 1
    When method GET
    Then status 200
  Scenario: Post data
    * def base_url = "https://postman-rest-api-learner.glitch.me"
    * url base_url
    Given path '/info'
    When method post
    Then status 200
  Scenario: Update data
    * url "https://postman-rest-api-learner.glitch.me"
    Given path '/info'
    And param Fid = 1
    When method PUT
    Then status 200
  Scenario: Delete data
    * url "https://postman-rest-api-learner.glitch.me"
    Given path '/info'
    And param Fid = 1
    When method DELETE
    Then status 200
  Scenario: YT
    * url 'https://www.youtube.com/watch?v=mKHIGIdFgBw'
    When method GET
    Then status 200
    And match response contains "Chittiveri"

  Scenario: get all users and then get the first user by id
    Given path 'users'
    When method get
    Then status 200

    * def first = response[0]

    Given path 'users', first.id
    When method get
    Then status 200

  Scenario: create a user and then get it by id
    * def user =
      """
      {
        "name": "Test User",
        "username": "testuser",
        "email": "test@user.com",
        "address": {
          "street": "Has No Name",
          "suite": "Apt. 123",
          "city": "Electri",
          "zipcode": "54321-6789"
        }
      }
      """

    Given url 'https://jsonplaceholder.typicode.com/users'
    And request user
    When method post
    Then status 201

    * def id = response.id
    * print 'created id is: ', id

    Given path id
    # When method get
    # Then status 200
    # And match response contains user
  