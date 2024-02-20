Feature: REST API basics

  Scenario: Get data
    * def urlValue = "https://postman-rest-api-learner.glitch.me"
    * def updatedUrl = "{{base_url}}/info?id=1".replace('{{base_url}}', urlValue)
    * url updatedUrl
    When method GET
    Then status 200
  Scenario: Post data
    * def urlValue = "https://postman-rest-api-learner.glitch.me"
    * def updatedUrl = "{{base_url}}/info".replace('{{base_url}}', urlValue)
    * url updatedUrl
    When method POST
    Then status 200
  Scenario: Update data
    * def urlValue = "https://postman-rest-api-learner.glitch.me"
    * def updatedUrl = "{{base_url}}/info?id=1".replace('{{base_url}}', urlValue)
    * url updatedUrl
    When method PUT
    Then status 200
  Scenario: Delete data
    * def urlValue = "https://postman-rest-api-learner.glitch.me"
    * def updatedUrl = "{{base_url}}/info?id=1".replace('{{base_url}}', urlValue)
    * url updatedUrl
    When method DELETE
    Then status 200
  Scenario: YT
    * def urlValue = "https://postman-rest-api-learner.glitch.me"
    * def updatedUrl = "https://www.youtube.com/watch?v=mKHIGIdFgBw".replace('{{base_url}}', urlValue)
    * url updatedUrl
    When method GET
    Then status 200
      And match response contains "Chittiveri"
