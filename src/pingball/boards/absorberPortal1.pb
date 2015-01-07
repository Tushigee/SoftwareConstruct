# This board will thorough check the operation of portals on one board. 
# Expected behaviour It will create infinite loop between Earth and Circle portals. 

board name=AbsorberPortal1 gravity = 25.0

# define a ball
ball name=BallA x=10.25 y=15.25 xVelocity=0.1 yVelocity=0.1
ball name=BallB x=19.25 y=3.25 xVelocity=0.1 yVelocity=0.1
ball name=BallC x=1.25 y=5.25 xVelocity=0.1 yVelocity=0.1

# defining a triangle bumper
triangleBumper name=Tri x=19 y=0 orientation=180

# defining some circle bumpers
circleBumper name=CircleB x=2 y=10
circleBumper name=CircleC x=3 y=10
circleBumper name=CircleD x=4 y=10
circleBumper name=CircleE x=5 y=10

# define an absorber to catch the ball
 absorber name=Abs x=0 y=18 width=20 height=2

# define some portals
portal name=Circle x=1 y=10 otherBoard=AbsorberPortal1 otherPortal=Earth 
portal name=Hell x=19 y=15 otherBoard=AbsorberPortal1 otherPortal=Heaven
portal name=Heaven x=19 y=10 otherBoard=AbsorberPortal1 otherPortal=Earth
portal name=Earth x=1 y=1 otherBoard=AbsorberPortal1 otherPortal=he
 
# define events between gizmos
fire trigger=CircleB action=Abs
fire trigger=CircleC action=Abs
fire trigger=CircleD action=Abs
fire trigger=CircleE action=Abs
fire trigger=Abs action=Abs

