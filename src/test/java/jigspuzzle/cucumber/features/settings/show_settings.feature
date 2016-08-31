Feature: Show Settings Window

   As a player I want to see a settings-window, in order to change some options.

   Scenario: Show appearance settings window
      Given that I am on the puzzle window
      When I open the appearance-settings window
      Then I should see the appearance-settings window

   Scenario: Show puzzle settings window
      Given that I am on the puzzle window
      When I open the puzzle-settings window
      Then I should see the puzzle-settings window
