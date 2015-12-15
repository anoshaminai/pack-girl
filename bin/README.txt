=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=
CIS 120 Game Project README
PennKey: anosha
=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=:=

===================
=: Core Concepts :=
===================

- List the four core concepts, the features they implement, and why each feature
  is an appropriate use of the concept. You may copy and paste from your proposal
  document if you did not change the features you are implementing.

  1. Modeling state using 2-D arrays or collections:
  
  The board of the game is modeled using a 2-D array. Every cell in the array 
  represents a potential location for an item. As the avatar moves around, the current state of 
  the spaces into which the avatar moves (whether it contains clothes, trash, etc) determines 
  how the game state changes. 

  The board has a fixed size and # of locations, which arrays can represent. Most
  importantly, arrays allow for quick and easy random access - this makes my logic for determining 
  if I've found an item very easy! I can just relate my avatar position to an array location
  and see what the Item array contains at that location. It helped me avoid doing more complicated
  positioning / collision detection.

  2. Object-oriented design using inheritance and subtyping
  
    All objects that live on the board are GameObj - a class that defines object movement, position,
    calculates the associated array index (according to position on board), etc. The Player class
    extends GameObj and adds functionality to find objects. Any objects that can be collected 
    are of abstract class Item - Item ensures that every collectable object has a point value and
    an actionOnPickUp, but doesn't implement / set those values because that will change according
    to the item. Clothes and Trash are abstract classes that further extend Item - these two classes
    implement actionOnPickUp (because all items of type Clothes and all of type Trash act the same
    way when picked up) but do not set point values. Clothes and Trash also maintain a few static
    variables - one is the "max value" or needed value to "fill" the suitcase or trash can. The
    other is the current value of the suitcase or trash can. These values are shared among all
    instances of the Clothing & Trash types, so it made sense to have them there. Finally, the 
    actual items that are picked up are the Shirt and Can classes - these classes set the actual
    point values & images associated. This structure makes sense as 1) it allows code to be shared, 
    2) it requires that certain functionality be implemented at the right level and 3) it allows
    for easy extension.

  3. Using I/O to parse a novel file format
  
    This game sets the item location on the board based on an input file that is read. If no file
    is provided, there is a default option. Files don't have to be a specific size, but if the file
    is smaller than the board, the rest of the board is filled in as empty, and if the file is 
    longer, the extra parts are ignored. The input file can also specify the player's starting 
    location, but if no player is specified, a default player is created. 
    
    Having this functionality allows the game to change very easily, as the configuration of the
    items on the board changes the game experience entirely! Files are related to items through
    an identifying character, set in the Item's class.

  4. Using JUnit to test some features of your model

    I used JUnit to test the BoardCreator (the file reading class that created boards). I/O is 
    often tricky and can have many bugs that might not show up right away, so it was a good place
    for JUnit testing. In fact, without it, I would not have successfully been able to implement
    the file reading. 

=========================
=: Your Implementation :=
=========================

- Provide an overview of each of the classes in your code, and what their
  function is in the overall game.

    BoardCreator - reads an input file and creates a board with items / players placed accordingly
    BoardCreatorTest - JUnit testing for BoardCreator
    Can - an extension of abstract class Trash, Cans are collected and added to the trash can to
          help win the game by cleaning my room
    Clothes - an extension of abstract class Item. Clothes is one major Item type, it maintains
              a count of how many points are needed to fill a suitcase & how many have been earned
              so far. Clothes also implements the actionOnPickup method.
    Direction - a direction for a moving object (from starter game)
    Game - runs the game, builds the GUI
    GameCourt - contains all the real game logic. Initializes values, sets up the board, and 
                updates game status as action happens 
    GameObj - base class for all objects in game. Provides basic functionality for movement,
              determining location, etc.
    Item - abstract class for any objects that can be picked up on the board. Creates abtract
           method actionOnPickUp, requiring all extensions to actually implement. 
    Player - the user, moves around on board through arrow key movement and picks up items
    ReadInstructions - reads in input file and returns a String, used to create the instructions
    Shirt - an extension of abstract class Clothes, Shirts are collected and added to the suitcase 
            to help win the game by packing my stuff
    Trash - an extension of abstract class Item. Trash is one major Item type, it maintains
            a count of how many points are needed to fill a trash can & how many have been earned
            so far. Trash also implements the actionOnPickup method.

- Revisit your proposal document. What components of your plan did you end up
  keeping? What did you have to change? Why?

    I made several significant changes. I will list some below:
    
    1) Did not implement obstacles, enemies, or more than one Clothes or Trash type. This was
       mainly out of a time constraint - my original proposal was more complicated than I thought
       so I had to let these parts go. However, now that I have the basic game working, with the
       way it was designed, it should not be too complicated to implement these. In fact, even 
       adding the Trash class / Can trash (after having Clothes / Shirt) working was very easy.
       
    2) Got rid of Deposit class - originally I was going to create an entire class Deposit to 
       represent how many Clothes / Trash / etc were collected. It turned out to be unnecessary
       and overly complicated - having the object made sharing information difficult, and also
       made it necessary that I "type check" the Items, which was part of the reason why I had
       implemented the Item abstract class in the first place - to avoid that. Having static
       variables in Clothes and Trash was easy, lightweight, and allowed me to use those classes
       even more.
       
    3) Both Player and Item extend GameObj - all the game objects shared more code than I originally
       expected, making it sensible to have them all extend GameObj. Doing this will also make it
       easier to add different GameObjs of different types in the future - for example, an added
       Enemy class will have different functionality than both Player & Item but can use a lot of 
       the code for positioning / etc, so can just extend GameObj. Item was also going to be an
       interface but is now an abstract class - mainly due to sharing code and making sense for
       code to be actually implemented at higher levels (mainly at the GameObj level).
       
    4) I switched my JUnit testing from testing game logic to testing I/O. I had forgotten how
       complicated I/O is and how necessary it is to test, and also I found that my game logic,
       though sometimes tricky, was not really so complicated that I needed to do lots of testing
       for it. I was able to reason through it through playing / tracing through the code. With
       I/O, I was unable to do that, and JUnit testing made my code more consistently working.
       
    5) I had originally planned to limit hte # of items the Player could carry and require the
       player to physically go to locations to drop them off. I didn't do this because it was
       pretty complicated and it also wasn't that much fun to play - it made the game pacing
       very slow and boring.

- Were there any significant stumbling blocks while you were implementing your
  game (related to your design, or otherwise)?

    Many! Before I first started implementing, I had spent a lot of time before planning out my
    class inheritance, because I knew it was complicated and had potential for being a big problem.
    But, even once I did start implementing I moved too quickly without checking what I was doing
    (because I was doing pretty simple things, I thought) and ended up with code that didn't work
    for reasons I couldn't determine. I had to start all over from the starter game and work very 
    slowly. Even though I thought I had a solid game design, I had been overconfident. 

- Evaluate your design. Is there a good separation of functionality? How well is
  private state encapsulated? What would you refactor, if given the chance?

  I think I generally did a good job separating functionality and encapsulating state - if fields
  were final, I allowed them to be public as it was easier access & they couldn't be changed 
  anyway. My use of abstract classes allowed for good code sharing. My biggest design problem
  was probably in my GUI. I have a lot of duplicate code in how I built my GUI that I probably
  could have pared down into methods I could re-use. I also am passing a lot of information around - 
  my constructors take a lot of inputs, and I wonder if I could have somehow done that differently.

========================
=: External Resources :=
========================

- Cite any external resources (libraries, images, tutorials, etc.) that you may
  have used while implementing your game.



