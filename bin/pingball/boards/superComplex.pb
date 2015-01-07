board name=superComp123 gravity = 10.0

# define a ball
ball name=BallA x=1 y=4.0 xVelocity=10.4 yVelocity=10.3 
ball name=BallB x=2 y=4.0 xVelocity=-3.4 yVelocity=-2.3 
ball name=BallC x=3 y=4.0 xVelocity=10.4 yVelocity=10.3 
ball name=BallD x=4 y=4.0 xVelocity=-3.4 yVelocity=-2.3 
ball name=BallE x=5 y=4.0 xVelocity=10.4 yVelocity=10.3 
ball name=BallF x=6 y=4.0 xVelocity=-3.4 yVelocity=-2.3 
ball name=BallG x=7 y=4.0 xVelocity=10.4 yVelocity=10.3 
ball name=BallH x=8 y=4.0 xVelocity=-3.4 yVelocity=-2.3 

# define some bumpers
squareBumper name=Square x=0 y=10
squareBumper name=SquareB x=1 y=10
squareBumper name=SquareC x=2 y=10
squareBumper name=SquareD x=3 y=10
squareBumper name=SquareE x=4 y=10
squareBumper name=SquareF x=5 y=10
squareBumper name=SquareG x=6 y=10
squareBumper name=SquareH x=7 y=10

#Define some circle bumpers
circleBumper name=Square1 x=5 y=14
circleBumper name=SquareB1 x=6 y=14
circleBumper name=SquareC1 x=7 y=14
circleBumper name=SquareD1 x=8 y=14
circleBumper name=SquareE1 x=9 y=14
circleBumper name=SquareF1 x=10 y=14
circleBumper name=SquareG1 x=11 y=14
circleBumper name=SquareH1 x=12 y=14

circleBumper name=Circle x=4 y=3
triangleBumper name=Tri x=19 y=3 orientation=90

#define some portals
portal name=Circle1 x=1 y=10 otherBoard=superComplex otherPortal=Earth 
portal name=Hell x=19 y=15 otherBoard=superComplex otherPortal=Heaven
portal name=Heaven x=19 y=10 otherBoard=superComplex otherPortal=Earth
portal name=Earth x=1 y=1 otherBoard=superComplex otherPortal=hey


# define some flippers
  leftFlipper name=FlipL x=10 y=7 orientation=0 
rightFlipper name=FlipR x=12 y=7 orientation=0


# define an absorber to catch the ball
 absorber name=Abs x=10 y=17 width=10 height=2 

# define events between gizmos
fire trigger=Square action=FlipL
fire trigger=SquareB action=FlipL
fire trigger=SquareC action=FlipL
fire trigger=SquareD action=FlipL
fire trigger=SquareE action=FlipL
fire trigger=SquareF action=FlipL
fire trigger=SquareG action=FlipL
fire trigger=SquareH action=FlipL

fire trigger=Square1 action=FlipL
fire trigger=SquareB1 action=FlipL
fire trigger=SquareC1 action=FlipL
fire trigger=SquareD1 action=FlipL
fire trigger=SquareE1 action=FlipL
fire trigger=SquareF1 action=FlipL
fire trigger=SquareG1 action=FlipL
fire trigger=SquareH1 action=FlipL


# make the absorber self-triggering
 fire trigger=Abs action=Abs 
 
 keydown key=shift action=FlipR