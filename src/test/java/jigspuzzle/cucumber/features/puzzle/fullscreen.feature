Feature: Show fullscreen

   As a player I want to be able to puzzle in a fullscreen window.

   The menu is then not shown by default. It is only shown, when I move the move
   to the top of the screen.

   Scenario: Show fullscreen
      Given that I am on the puzzle window
      When I trigger fullscreen mode
      Then I should see the puzzlearea in fullsceen
      When I leave fullscreen mode
      Then I should not see the puzzlearea in fullsceen

   Scenario: Show menu on fullscreen
      Given that I am on the puzzle window
      Then I should see the menu
      When I trigger fullscreen mode
      Then I should not see the menu
      When I move the mouse to the top of the screen
      And I should see the menu
