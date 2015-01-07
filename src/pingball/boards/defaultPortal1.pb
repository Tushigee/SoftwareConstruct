board name=defaultPortal1 gravity = 25.0

# define a ball
ball name=BallA x=1.25 y=1.25 xVelocity=0 yVelocity=0

# define a series of square bumpers
squareBumper name=SquareA x=0 y=17
squareBumper name=SquareB x=1 y=17
squareBumper name=SquareC x=2 y=17

# define a series of circle bumpers
circleBumper name=CircleA x=1 y=10
circleBumper name=CircleB x=7 y=18
circleBumper name=CircleC x=8 y=18
circleBumper name=CircleD x=9 y=18

circleBumper name=CircleE x=10 y=19
circleBumper name=CircleF x=11 y=19
circleBumper name=CircleG x=12 y=19
circleBumper name=CircleH x=13 y=19
circleBumper name=CircleB1 x=14 y=18
circleBumper name=CircleC1 x=15 y=18
circleBumper name=CircleD1 x=16 y=18

# define a triangle bumper
portal name=Heaven x=12 y=15 otherBoard=defaultPortal2 otherPortal=Hell