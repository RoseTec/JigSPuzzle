Feature: Save and load settings

   As a player I want to save the settings. Also, when I stop and start the
   program again, the settings I set earlier should stay.

   Scenario: Save background color of the puzzlearea
      Given that I have no settings set up to now
      And that I am on the appearance-settings window
      Then the background color of the puzzlearea should not be "red"
      When I change the background color of the puzzlearea to "red"
      Then the background color of the puzzlearea should be "red"
      When I save the settings
      And restart the program
      When I open the appearance-settings window
      Then the background color of the puzzlearea should be "red"

   Scenario: Cancel editing the settings
      Given that I am on the appearance-settings window
      Then the background color of the puzzlearea should not be "yellow"
      When I change the background color of the puzzlearea to "yellow"
      Then the background color of the puzzlearea should be "yellow"
      When I cancel the settings window without saving
      Then the background color of the puzzlearea should not be "yellow"