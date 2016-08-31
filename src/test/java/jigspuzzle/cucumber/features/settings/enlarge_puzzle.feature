Feature: Automatically enlarge puzzlepieces

   As a player I want to be able to load small images and puzzle with a big
   variant of this. Means, I can have smalle images, but the image is scaled up
   to fit better into my large puzzlearea on the screen.

   Scenario: Enlarge puzzlepieces when the window size changes
      Given that I am on the puzzle window
      And the size of the puzzle window is: width=1700px, height=1000px
      And I have created a sqaure puzzle with 100 puzzlepieces out of a small image
      Then the size of one puzzlepiece should be: width=100px, height=100px

      When I resize the puzzle window to: width=1500px, height=900px
      Then the size of one puzzlepiece should be: width=90px, height=90px
