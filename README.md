6.005 Project - Pingball
========================

**NOTE:  The contents of this file can be rendered using [dillinger.io](http://dillinger.io).**

Phase 2
-------

*  Kenneth Leidal (kkleidal@mit.edu)
*  Nicholas Matthews (nikm@mit.edu)
*  Battushig Myanganbayar (btushig@mit.edu)

Phase 3
-------

*  Kenny Gea (kennygea@mit.edu)
*  Debra Van Egeren (dve@mit.edu)
*  Battushig Myanganbayar (btushig@mit.edu)

# Project Structure

## Event handling:  the TriggerEventHandler and TriggerEventEmitter

```java
interface TriggerEventHandler {
	void trigger(Ball dispatcher);
}
```

There shouldn't be classes which implement `TriggerEventHandler`.  The interfaced will only be used to initiate instances in context (similar to lambda functions).  For example:

```java
public static void main(String[] args) {
	TriggerHandler onTrigger = new TriggerHandler() {
		void trigger(Ball dispatcher) {
			System.out.println("Event dispatched.");
		}
	}
}
```

To put the Emitter/Handler interaction in context:  an event emitter might be a wall, and when a ball is about to collide with the wall, the wall's `emitTrigger(ball)` method will be called, which will run the `trigger(ball)` function of all its `TriggerEventHandler`'s, one of which will reverse the ball's direction.

## GameObject

All the various physical objects in the game will implement the `GameObject` interface.

The renderText method takes a map which maps Points of integer precision (corresponding to one character in the text output) to a String (or possibly a character).  For example, if the key `(0,0)` maps to `"*"`, then the textual output will have a `"*"` at location `(0,0)`.  The `renderText` method changes the map based on the current state of the GameObject.  The board calls the `renderText()` method for all of its GameObject's to generate the textual output.

The game objects are mutable, but contain immutable data types.  The mutability of the class allows for less memory overhead and less time spent reallocating memory, which is important since quick render times is a priority in order to avoid lag.

We also have two sub-interfaces of the `GameObject` interface:  `MoveableGameObject` and `StationaryGameObject`.  A `MoveableGameObject` will be things like bumpers, which change their position and need to be updated by the physics engine.  A `StationaryGameObject` does not have to be updated by the physics engine:  it is something like a wall, a portal, etc.

## Board

The Board class tracks all the GameObject's on the board and handles the interactions between them.

The advance method advances the physics simulation `seconds` seconds.

The renderText method prints the textual representation of the Board to the specified PrintStream (System.out is a PrintStream, so if render(System.out) is called, the rendered board will be printed to stdout).

The balls, moveable objects, and stationary objects must be seperated into different containers in order to save time because the physics engine does not have to update the position of stationary objects and should not attempt to (it would waste time).

Client/Server Wire Protocol
---------------------------

Messages are transferred between client and server as newline-delimited JSON messages.  They are wrapped in a JSON object with a property specifying the type of message.

There are 4 major type of messages that used for communication between server and client.

1. BallRoutingMessage - used to transfer ball from current client’s board to other client’s board

2. BoardObjectInContext - used to represent standard object on the board such as various types wall, and portals.

3. ClientInformationMessage - used to inform server when client is connected (includes information about clients board name).

4. WallConnectionInformationMessage - used to inform a client that it needs to display name of connected board in their designated wall.
 
 
# Following is the wire protocol used for 4 major type of messages

## WIRE PROTOCOL OF BallRoutingMessage:
    ```json
    {"ball":<BALL>,"source":<SOURCE>,"destination":<DESTINATION>}
    ```
Where:
    *  \<BALL\> is a Ball serialized into a JSON object, (refer to “major objects JSON serialisation” for more information)
    *  \<SOURCE\> is a BoardObjectInContext representing the source of the message, serialized into JSON, (refer to  BoardObjectInContext for more information)
    *  and \<DESTINATION\> is a BoardObjectInContext representing the destination of the message serialized into JSON, (refer to BoardObjectInContext for more information)

## WIRE PROTOCOL OF BoardObjectInContext:
    ```json
    {"boardname":<BOARD NAME>,"boardObjectType":<NAMED or WALL>,"name":<NAME>,"id":<WALL POSITION>}
    ```
Where:
    *   \<BOARD NAME\> is a string representing the containing board's name,
    *   \<NAMED or WALL\> is a string representing the value of the 	BoardObjectType enum, representing whether the object referred to is an ordinary GameObject or outer wall
    *   \<NAME\> is a string referring to the name of the object (if NAMED)
    *   \<WALL POSITION\> is the position of the wall (LEFT, RIGHT, TOP, or BOTTOM) if WALL or NONE if NAMED
 

    
## WIRE PROTOCOL OF ClientInformationMessage:
    ```json
    {"name":<CLIENT NAME>}
    ```
  where \<CLIENT NAME\> is a string representing the name of the client's board
     


## WIRE PROTOCOL OF WallConnectionInformationMessage:
    ```json
    {“connectedTo":<ConnectedObjectInContext>,"source":<SourceObjectInContext>,"isConnected": <BOOLEAN>}
    ```
Where:
    *  \<SourceObjectInContext\> is BoardObjectInContext representing wall on the board that message is sent, serialized into JSON. 
    *  \<ConnectedObjectInContext\> is a BoardObjectInContext representing a wall that is connected to 
    *  wall represented by \<SourceObjectInContext\>,  serialized into JSON.
    *  \<BOOLEAN\> is boolean variable, if <BOOLEAN> is true then, client will display \<ConnectedObjectInContext\>'s board name on \<SourceObjectInContext\>
    *  if it is false then, client will remove a name from \<SourceObjectInContext\>
    
## WIRE PROTOCOL OF DisconnectMessage:
    ```json
    {"name":<CLIENT NAME>}
    ```
where \<CLIENT NAME\> is a string representing the name of the client's board
    

# Major objects JSON serialization:

There is 1 major type of objects that we used its JSON serialization to transfer information between server and client.

## Ball: represents ball object with its position and velocity etc.
WIRE PROTOCOL OF Ball JSON serialisation:
    ```json
    {"x":<X POSITION>,"y":<Y POSITION>,"xVel":<X VELOCITY>,"yVel":<Y VELOCITY>,"mass":<MASS>,"radius":<RADIUS>}
    ```
    Where \<X POSITION\>, \<Y POSITION\>, \<X VELOCITY\>, \<Y VELOCITY\>, \<MASS\>, \<RADIUS\> are doubles

# Thread safety argument

##RoutingManager:  
thread-safe because its mutable resources are only accessed when a lock is obtained on the RoutingManager, and no mutable objects are ever returned.  RoutingManager and ConnectionManager are only accessed by ClientProcessor threads (and the thread which accepts new connections/user input), and all of these only maintain a lock temporarily and never request other locks while holding the lock, which prevents deadlock.

##ConnectionManager:  
thread-safe for the same reasons as RoutingManager

##ClientProcessor:  thread-safe because it only communicates with other threads through two blocking queues (inbox and outbox)

##MessageDispatcher/MessageReceiver:  thread-safe because they read/write from their respective blocking queues, and communicate with the client.  There is only one MessageDispatcher and one MessageReceiver per connection, ensuring messages will only be written once and that messages will be read and added to the queue in a deterministic order.

##On the client side:

Board/Pingball are thread-safe, because it is only ever modified by a single thread (the thread running the simulation and rendering the board to standard output). There is at most only one GUISimulator thread running at all times (every time a new one is spawned the old one is killed). Message to/from the server are put on the inbox/outbox blocking queues by MessageDispatcher/MessageReceiver threads, ensuring that mutable resources are never modified by other threads.

There are only 4 threads at most on the client-side, the thread handling sending messages to the server, the thread handling receiving messages from the server, and the thread handling the simulation, and the thread handling the GUI.  Communication between the first three threads only occurs through blocking queues. The GUI thread and the simulation thread share the board, but GUI thread doesn't call methods directly on the board except when initializing a new board (no simulation thread is running at this time) or doing a threadsafe reset of the board's message queue (board.disconnect()).

# GUI features:

## Speed controller:
One of main features we added in this phase was ability to control the speed of game. Speed can range from 0.1 to 2.5 representing slower motion if value is less than one, slightly faster motion otherwise.

Player can see finest details of our beautiful physics engine, and graphics with slower motion. Sometimes we need to observe carefully, and takes our time to appreciate beauty of things.   

In high speed motion, it will increase gravity, and speed of balls since time is passing extremely fast. Therefore, it is possible for balls to be go inside a circle, and square bumper if they surpass certain speed. It makes our board more interesting to see. 

## Different graphical representation for same board
We also added one minor feature which is board will be displayed slightly different each time when you hit restart button. Sometimes circle bumpers are bold, or sometimes it is thin. Everything is too boring if the board looks exactly the same whenever you plays on same board . Different things adds value to life. 

## Colorful balls:
We also made it such that if balls will get random color whenever they initialized. Balls will get random colors if they go through portals, or transitioned into new board. Life is better with more different things!!!
 
# Important things to know working with GUI:
## Basics functionality of button:
As expected, start, pause, restart buttons will do what you wanted. However, there are couple of things you need to use them. 

###Restart:
If you click restart button, then all the current connection to server will be disconnected, and board is same as it is started. 
 
## Making server connection:
User cannot connect to server before uploading a board file. To make connection with server with current board, you have to upload a board first, then provide appropriate host, and port number that server is listening.
Whenever user uploaded a board, all the previous connection with server lost. 
To disconnect from server, you have to click disconnect button. 

#Instructions for initializing program:

To run GUI program, go to Pingball.java in PingballGUI package, and run it (user can run this class with command line argument as grammar specified in project phase 2. If command line arguments are present, and correct, it will initialize board file already loaded).
To initialize server, go to PingballServer.java inside the pingball.server package (to change the port, pls run with "--port [PORT]" command line argument). 
 
