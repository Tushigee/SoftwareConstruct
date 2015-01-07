board name=KeyFlippers gravity=20.0

# define a ball
ball name=BallC x=1.8 y=4.5 xVelocity=10.4 yVelocity=10.3 

# define some bumpers
squareBumper name=Square x=0 y=10
squareBumper name=SquareB x=1 y=10
squareBumper name=SquareC x=2 y=10


circleBumper name=Circle x=4 y=3
triangleBumper name=Tri x=19 y=3 orientation=90


# define some flippers
leftFlipper name=FlipL x=3 y=10 orientation=0 
rightFlipper name=FlipR x=8 y=10 orientation=0


# define an absorber to catch the ball
 absorber name=Abs x=10 y=17 width=10 height=2 


# make keys trigger flippers


 keydown key=f action=FlipL
 #comment
 keyup key=j action=FlipR
 keydown key=space action=Abs
