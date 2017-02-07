Feature: Show "about"-windows

   As a user of JigSPuzzle I want to see details of the project.
   Therefore I can access an 'about'-window, that shows me all informations.

   Scenario: show "about"-windows
      Given that I am on the puzzle window
      When I open the about-window
      Then I should see the about-window
