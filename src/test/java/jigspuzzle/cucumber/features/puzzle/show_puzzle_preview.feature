Feature: Show preview of the puzzle

   As a player I want to have a preview of the image of the puzzle, I'm
   currently playing.

   That means, when I created a puzzle (and the option is enabled) then the
   complete image of thepuzzle is displayed in the left top corner of the
   puzzle window. However, it is desplaxed smaller than the real image.
   (size: about 300px)

   Scenario: Show preview of the puzzle after creating a puzzle
      Given that the function for showing a preview of the puzzle is activated
      And I am on the puzzle window
      And I have created a puzzle
      Then I should see a preview of the puzzle

   Scenario: Live changing preview of the puzzle after creating a puzzle
      Given that the function for showing a preview of the puzzle is deactivated
      And I am on the puzzle window
      And I have created a puzzle
      When I decide to change the settings for showing a preview of the puzzle
      Then I should not see a preview of the puzzle
