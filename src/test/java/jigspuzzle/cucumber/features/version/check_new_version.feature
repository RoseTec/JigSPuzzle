Feature: Serach for new version

   As a player I want to know, if there is newer version available. I check the
   newer version via the menü item 'search for a new version'.

   Is a newer version available, then a window is opening, where I can navigate
   to an internet link and download it from there.
   Is no new version available, then the windows states, that this version is
   the newest version.

   Scenario: Searching for new version, when a new version is avalable
      Given that I am on the puzzle window
      And a newer version is available on the internet
      When I check for a newer version
      Then I should see, that a newer version is available
      And I should be able to navigate to an internet link with the newer version

   Scenario: Searching for new version, when no new version is avalable
      Given that I am on the puzzle window
      And no newer version is available on the internet
      When I check for a newer version
      Then I should see, that this version is up to date
