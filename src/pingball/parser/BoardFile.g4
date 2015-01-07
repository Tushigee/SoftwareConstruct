grammar BoardFile;


// This puts a Java package statement at the top of the output Java files.
@header {
package pingball.parser;
}

@rulecatch {
    catch(RecognitionException e) {
        e.printStackTrace();
   }
}


// This adds code to the generated lexer and parser.
@members {
    /**
     * Call this method to have the lexer or parser throw a RuntimeException if
     * it encounters an error.
     */
    public void reportErrorsAsExceptions() {
        addErrorListener(new ExceptionThrowingErrorListener());
    }
    
    private static class ExceptionThrowingErrorListener extends BaseErrorListener {
        @Override
        public void syntaxError(Recognizer<?, ?> recognizer,
                Object offendingSymbol, int line, int charPositionInLine,
                String msg, RecognitionException e) {
            throw new RuntimeException(msg);
        }
    }
    
}

/*
 * These are the lexical rules. They define the tokens used by the lexer.
 * *** ANTLR requires tokens to be CAPITALIZED, like START_ITALIC, END_ITALIC, and TEXT.
 */

//Odds and ends
SPACE : ('\t'|' ')+ ;
EQUALS :  ' '? '='  ' '?;
COMMENT_MARKER : '#' ;
NEGATIVE : '-' ;
NUMBER : ('0'..'9')+ ;
LOWERCASE : 'a'..'z' ;
UPPERCASE : 'A'..'Z';
LETTER : ('a'..'z' | 'A'..'Z' | 'x' | 'y') ;

UNDERSCORE : '_' ;
NEWLINE : ('\r\n'|'\n') ;

//Board
BOARD : 'board ' ;

//Board Elements types
SQUARE_BUMPER : 'squareBumper ' ;
TRIANGLE_BUMPER : 'triangleBumper ' ;
CIRCLE_BUMPER : 'circleBumper ' ;
ABSORBER : 'absorber ' ;
BALL : 'ball ' ;
LEFT_FLIPPER : 'leftFlipper ' ;
RIGHT_FLIPPER : 'rightFlipper ' ;
PORTAL : 'portal ' ;

//Board element component labels
NAME_LABEL : 'name' ;
ORIENTATION_LABEL : 'orientation' ;
X_VELOCITY_LABEL : 'xVelocity' ;
Y_VELOCITY_LABEL : 'yVelocity' ;
WIDTH_LABEL : 'width' ;
HEIGHT_LABEL : 'height' ;
OTHER_BOARD_LABEL : 'otherBoard' ;
OTHER_PORTAL_LABEL : 'otherPortal' ;

//Other Specifier Labels
GRAVITY_LABEL : 'gravity' ;
FRICTION1_LABEL : 'friction1' ;
FRICTION2_LABEL : 'friction2' ;
FIRE : 'fire' ;
TRIGGER : 'trigger' ;
ACTION : 'action' ;
KEYUP: 'keyup' ;
KEYDOWN: 'keydown' ;
KEYTOK : 'key' ;


/*
 * These are the parser rules. They define the structures used by the parser.
 * *** ANTLR requires grammar nonterminals to be lowercase
 */
root : commentLine* boardLine otherLine* EOF ;


otherLine : (SPACE? boardElementLine SPACE? ) | (SPACE? fireLine SPACE?) | (commentLine SPACE?) | (SPACE? keyLine SPACE?);

boardLine : SPACE? BOARD nameSpec SPACE* (boardSpecifiers SPACE*)* ;
boardElementLine : (ballLine | sqBumperLine | trBumperLine | circBumperLine | absorberLine | portalLine | leftFlipLine | rightFlipLine) ; 
fireLine : FIRE SPACE+ triggerSpec SPACE+ actionSpec;
commentLine : SPACE? COMMENT_MARKER (. | ',' | '!' | '?' | ':' | '.' | 'y' | 'x')*?;
keyLine : (KEYUP | KEYDOWN) SPACE keySpec SPACE actionSpec ;

//Specifications for the board
boardSpecifiers : gravitySpec | friction1Spec | friction2Spec ;
gravitySpec : GRAVITY_LABEL EQUALS antlrFloat ;
friction1Spec : FRICTION1_LABEL EQUALS antlrFloat ;
friction2Spec : FRICTION2_LABEL EQUALS antlrFloat ;

//Board Elements
sqBumperLine : SQUARE_BUMPER nameSpec SPACE+ xSpec SPACE+ ySpec ;
trBumperLine : TRIANGLE_BUMPER nameSpec SPACE+ xSpec SPACE+ ySpec SPACE+ orientationSpec ;
circBumperLine : CIRCLE_BUMPER nameSpec SPACE+ xSpec SPACE+ ySpec ;
absorberLine : ABSORBER nameSpec SPACE+ xSpec SPACE+ ySpec SPACE+ widthSpec SPACE+ heightSpec ;
portalLine : PORTAL nameSpec SPACE+ xSpec SPACE+ ySpec SPACE+ otherBoardSpec? otherPortalSpec ;
leftFlipLine : LEFT_FLIPPER nameSpec SPACE+ xSpec SPACE+ ySpec SPACE+ orientationSpec ;
rightFlipLine : RIGHT_FLIPPER nameSpec SPACE+ xSpec SPACE+ ySpec SPACE+ orientationSpec ;
ballLine : BALL nameSpec SPACE+ xFloatSpec SPACE+ yFloatSpec SPACE+ xVelSpec SPACE+ yVelSpec ;

//Element Arguments
nameSpec : NAME_LABEL EQUALS name ;
xSpec : 'x' EQUALS antlrInt ;
ySpec : 'y' EQUALS antlrInt ;
orientationSpec : ORIENTATION_LABEL EQUALS antlrInt ;
widthSpec : WIDTH_LABEL EQUALS antlrInt ;
heightSpec : HEIGHT_LABEL EQUALS antlrInt ;
otherBoardSpec : OTHER_BOARD_LABEL EQUALS name SPACE? ;
otherPortalSpec : OTHER_PORTAL_LABEL EQUALS name ;
xFloatSpec : 'x' EQUALS antlrFloat ;
yFloatSpec : 'y' EQUALS antlrFloat ;
xVelSpec : X_VELOCITY_LABEL EQUALS antlrFloat ;
yVelSpec : Y_VELOCITY_LABEL EQUALS antlrFloat ;
keySpec : KEYTOK EQUALS key ;

//Fire Arguments
triggerSpec : TRIGGER EQUALS name ;
actionSpec : ACTION EQUALS name ;

//Other things:
name : ('y'|'x'| 'up' | UPPERCASE| LOWERCASE | UNDERSCORE )+ ('y'| 'x' |'up'|UPPERCASE | LOWERCASE| UNDERSCORE | NUMBER)* ; //SPACE*;
antlrInt : NUMBER+ ;
antlrFloat : NEGATIVE? ( NUMBER '.' NUMBER* | '.'? NUMBER+ ) ;
key : LOWERCASE
		| NUMBER
        | 'shift' | 'ctrl' | 'alt' | 'meta'
        | 'space'
        | 'left' | 'right' | 'up' | 'down'
        | 'minus' | 'equals' | 'backspace'
        | 'openbracket' | 'closebracket' | 'backslash'
        | 'semicolon' | 'quote' | 'enter'
        | 'comma' | 'period' | 'slash' ;