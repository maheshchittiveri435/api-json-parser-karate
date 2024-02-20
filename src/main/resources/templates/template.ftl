Feature: ${featureName}

<#list scenarios as scenario>
  Scenario: ${scenario.name}
    * def urlValue = "${scenario.envHM["base_url"]}"
    * def updatedUrl = "${scenario.url}".replace('{{base_url}}', urlValue)
    * url updatedUrl
    When method ${scenario.method}
    Then status 200
    <#if scenario.text?exists>
      And match response contains "${scenario.text}"
    </#if>
</#list>
