Feature: Automatically decrease puzzlepieces

   As a player I want to be able to load big images and puzzle with a small
   variant of this. Means, I can have bis images, but the puzzle fits into my
   small puzzlearea on the screen.

   Scenario: Decrease puzzlepieces when the window size changes
      Given that I am on the puzzle window
      And the size of the puzzle window is: width=500px, height=750px
      And I have created a sqaure puzzle with 100 puzzlepieces out of a big image
      Then the size of one puzzlepiece should be: width=50px, height=50px

      When I resize the puzzle window to: width=1200px, height=900px
      Then the size of one puzzlepiece should be: width=90px, height=90px
