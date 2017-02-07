Feature: Restart a puzzle

   As a player I want to be able to restart my puzzle. When doing so, a new
   puzzle with the image of the current puzzle is created.

   Scenario: Restart a created puzzle
      Given that I am on the puzzle window
      And I have created a puzzle
      When I restart the puzzle
      Then I should see the restarted puzzle

   Scenario: Button for restart a puzzle is not allways enabled
     The button should only be enabled, when there is is puzzle.
      Given that I am on the puzzle window
      Then the button for restarting the puzzle should not be enabled
      When I have created a puzzle
      Then the button for restarting the puzzle should be enabled

