Feature: Save/load a puzzle

   As a player I want to be able to save a puzzle and load it later. For this
   I want to select a file from my HDD. The puzzzle is saved to that file or
   loaded from that file.

   Scenario: Save a puzzle and load it
      Given that I am on the puzzle window
      And I have created a puzzle
      When I save the puzzle
      And restart the program
      And I load a puzzle
      Then I should see the complete puzzle
