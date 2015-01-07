#tests the key press triggers

board name=KeyTest gravity = 25.0

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

triangleBumper name=TriA x=19 y=0 orientation=90

# define an absorber
absorber name=Abs x=0 y=19 width=20 height=1 


# define key actions
keydown key=a action=FlipA
keydown key=s action=FlipB
keydown key=d action=FlipC
keydown key=0 action=FlipD
keydown key=j action=FlipE
keydown key=k action= FlipF
keydown key = l action=FlipG
keydown key=semicolon action=FlipH
keyup key=space action=Abs