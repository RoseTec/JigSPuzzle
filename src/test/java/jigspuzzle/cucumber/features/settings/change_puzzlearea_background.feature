Feature: Change puzzleare background

   As a player I want to be able to change the background color of the area
   where I solve puzzles.

   If I change the background color, the effect if live viewable on the
   puzzleare.

   Scenario: Change the color of the puzzleare background
      Given that I have no settings set up to now
      And I am on the puzzle window
      When I open the appearance-settings window
      And I change the background color of the puzzlearea to "red"
      Then the background color of the puzzlearea should be "red"
      When I change the background color of the puzzlearea to "blue"
      Then the background color of the puzzlearea should be "blue"
      When I save the settings
      Then the background color of the puzzlearea should be "blue"
