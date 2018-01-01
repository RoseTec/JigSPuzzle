Feature: Change number of puzzlepieces

   As a player I want to be able to change the numbr of puzzlepieces that a
   puzzle has.

   Scenario: Change the color of the puzzleare background
      Given that I have no settings set up to now
      And I am on the puzzle-settings window
      When I change the number of puzzlepieces to "42"
      Then I should see the number of puzzlepieces in the settings window to be "42"
      When I change the number of puzzlepieces to "330"
      Then I should see the number of puzzlepieces in the settings window to be "330"

      When I save the settings
      And I create a new puzzle
      Then the new puzzle should have "330" puzzlepieces
