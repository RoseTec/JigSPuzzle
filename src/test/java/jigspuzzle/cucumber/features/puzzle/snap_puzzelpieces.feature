Feature: Snap Puzzlepieces

   As a player I want to be able to puzzle. In order to achieve this, I have
   to put neighbored puzzlepieces together. The puzzlepieces snap now
   together, so that I can move both, when dragging one of them.

   Scenario: Let two puzzlepieces snap and drag the complete group
      Given that I am on the puzzle window
      And I have created a puzzle
      When I drag one puzzlepiec to the correct edge of a neighbored puzzlepiece
      Then the two puzzlepieces should snap together and create a group
