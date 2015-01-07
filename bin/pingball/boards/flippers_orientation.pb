board name=FlippersOrientation gravity = 25.0

# define a ball
ball name=BallA x=0.25 y=3.25 xVelocity=0.1 yVelocity=0.1 

# define some left flippers
leftFlipper name=FlipA x=0 y=8 orientation=0 
leftFlipper name=FlipB x=4 y=8 orientation=90 
leftFlipper name=FlipC x=9 y=8 orientation=180
leftFlipper name=FlipD x=15 y=8 orientation=270

# define some right flippers 
leftFlipper name=FlipE x=0 y=11 orientation=0 
leftFlipper name=FlipF x=4 y=11 orientation=90 
leftFlipper name=FlipG x=9 y=11 orientation=180
leftFlipper name=FlipH x=15 y=11 orientation=270

squareBumper name=SquareA x=19 y=0

# define an absorber
absorber name=Abs x=0 y=19 width=20 height=1 


# define events between gizmos
fire trigger=Abs action=Abs
fire trigger=SquareA action=FlipA
fire trigger=SquareA action=FlipB
fire trigger=SquareA action=FlipC
fire trigger=SquareA action=FlipD
fire trigger=SquareA action=FlipE
fire trigger=SquareA action=FlipF
fire trigger=SquareA action=FlipG
fire trigger=SquareA action=FlipH
