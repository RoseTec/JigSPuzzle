Feature: Create new puzzle

   As a player I want to be able to puzzle. For this I want to select an
   image-file from my HDD. This image is then transformed in a puzzle, means
   I can see puzzlepieces.

   After creating the puzzle, the puzzle is first shown complete in the
   puzzleare, but then one puzzlepiece after the other 'breaks apart' and so
   the puzzle is scattered over the puzzlearea afterwards.

   Scenario: Create new puzzle from the puzzle window
      Given that I am on the puzzle window
      When I create a new puzzle with an image from my HDD
      Then I should see the complete puzzle
      And the puzzle should scatter all over the puzzlearea

   Scenario: Create new puzzle with drag&drop
   As a player I can rag and drop a file from my system into the puzzle window.
   In that case, a new puzzle is created out of the dragged file.

      Given that I am on the puzzle window
      When I drag an image into the puzzlearea
      Then I should see a puzzle of that image

