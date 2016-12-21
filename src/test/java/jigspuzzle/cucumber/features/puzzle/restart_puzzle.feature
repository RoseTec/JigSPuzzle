Feature: Restart a puzzle

   As a player I want to be able to restart my puzzle. When doing so, a new
   puzzle with the image of the current puzzle is created.

   Scenario: Save a puzzle and load it
      Given that I am on the puzzle window
      And I have created a puzzle
      When I restart the puzzle
      Then I should see the restarted puzzle
