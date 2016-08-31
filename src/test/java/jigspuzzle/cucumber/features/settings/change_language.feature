Feature: Change language

   As a player I want to choose between several languages that the user
   interface can have. That way, a player can have the language of the UI in
   his nativ language.

   when I change the language, the change should happen 'on the fly', so that
   I do not need to restart the program in order to see the changes.

   English should be the default language when no other language is set before.


   Scenario: Change language to german

   I want to be able to change the language to german, because the creator
   of this program is a german nativ speaker. :D

      Given that I am on the appearance-settings window
      And I have no settings set up to now
      Then I should see the user interface in the language "english"
      When I change the language to "german"
      Then I should see the user interface in the language "german"
