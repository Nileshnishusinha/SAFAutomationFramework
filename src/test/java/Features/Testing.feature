Feature: Testing Goibibo Website

  Background:
    Given Start browser as "chrome" and open the application

#  @TC_01
#  Scenario Outline: Validate the title of the website
#    Given Browser "Chrome" is launched
#    And Navigate to "<Website>" page
##    And Navigate to "<Website1>" page
##    Then Validate the expected title as "<Expectedtitle>"
#    And Close Browser
#    Examples:
#      | Website                  | Website1                  | Expectedtitle                                                                               |
#      | https://www.goibibo.com/ | https://www.facebook.com/ | Goibibo - Best Travel Website. Book Hotels, Flights, Trains, Bus and Cabs with upto 50% off |
#
#
#  @TC_02
#  Scenario: Validate the Screen Recording testing of the website
#    Given Testing of the Screen Recording features
#    And Close Browser

#  @Tc_03


  @Tc_04
  Scenario Outline: Title of your scenario outline
    #Please Do not change Given Temple
    When Test the text in H2 tag and the "<ShipmentId>" for ShipmentID
    Then Validate the Customer name "<customer>" is displayed
    And Close Browser
    Examples:
      | ShipmentId | customer |
      | 6543217    | Maya     |


#    testing of new app will be soon lauched