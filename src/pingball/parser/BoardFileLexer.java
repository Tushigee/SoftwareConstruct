// Generated from /Users/dve/Documents/6.005/pingball-phase3/src/pingball/parser/BoardFile.g4 by ANTLR 4.0

package pingball.parser;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class BoardFileLexer extends Lexer {
	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__27=1, T__26=2, T__25=3, T__24=4, T__23=5, T__22=6, T__21=7, T__20=8, 
		T__19=9, T__18=10, T__17=11, T__16=12, T__15=13, T__14=14, T__13=15, T__12=16, 
		T__11=17, T__10=18, T__9=19, T__8=20, T__7=21, T__6=22, T__5=23, T__4=24, 
		T__3=25, T__2=26, T__1=27, T__0=28, SPACE=29, EQUALS=30, COMMENT_MARKER=31, 
		NEGATIVE=32, NUMBER=33, LOWERCASE=34, UPPERCASE=35, LETTER=36, UNDERSCORE=37, 
		NEWLINE=38, BOARD=39, SQUARE_BUMPER=40, TRIANGLE_BUMPER=41, CIRCLE_BUMPER=42, 
		ABSORBER=43, BALL=44, LEFT_FLIPPER=45, RIGHT_FLIPPER=46, PORTAL=47, NAME_LABEL=48, 
		ORIENTATION_LABEL=49, X_VELOCITY_LABEL=50, Y_VELOCITY_LABEL=51, WIDTH_LABEL=52, 
		HEIGHT_LABEL=53, OTHER_BOARD_LABEL=54, OTHER_PORTAL_LABEL=55, GRAVITY_LABEL=56, 
		FRICTION1_LABEL=57, FRICTION2_LABEL=58, FIRE=59, TRIGGER=60, ACTION=61, 
		KEYUP=62, KEYDOWN=63, KEYTOK=64;
	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] tokenNames = {
		"<INVALID>",
		"'down'", "'shift'", "'up'", "'y'", "'?'", "'backspace'", "'period'", 
		"'ctrl'", "','", "'openbracket'", "'right'", "'space'", "'.'", "'semicolon'", 
		"'quote'", "'left'", "'backslash'", "'alt'", "'x'", "'equals'", "':'", 
		"'minus'", "'!'", "'comma'", "'meta'", "'enter'", "'closebracket'", "'slash'", 
		"SPACE", "EQUALS", "'#'", "'-'", "NUMBER", "LOWERCASE", "UPPERCASE", "LETTER", 
		"'_'", "NEWLINE", "'board '", "'squareBumper '", "'triangleBumper '", 
		"'circleBumper '", "'absorber '", "'ball '", "'leftFlipper '", "'rightFlipper '", 
		"'portal '", "'name'", "'orientation'", "'xVelocity'", "'yVelocity'", 
		"'width'", "'height'", "'otherBoard'", "'otherPortal'", "'gravity'", "'friction1'", 
		"'friction2'", "'fire'", "'trigger'", "'action'", "'keyup'", "'keydown'", 
		"'key'"
	};
	public static final String[] ruleNames = {
		"T__27", "T__26", "T__25", "T__24", "T__23", "T__22", "T__21", "T__20", 
		"T__19", "T__18", "T__17", "T__16", "T__15", "T__14", "T__13", "T__12", 
		"T__11", "T__10", "T__9", "T__8", "T__7", "T__6", "T__5", "T__4", "T__3", 
		"T__2", "T__1", "T__0", "SPACE", "EQUALS", "COMMENT_MARKER", "NEGATIVE", 
		"NUMBER", "LOWERCASE", "UPPERCASE", "LETTER", "UNDERSCORE", "NEWLINE", 
		"BOARD", "SQUARE_BUMPER", "TRIANGLE_BUMPER", "CIRCLE_BUMPER", "ABSORBER", 
		"BALL", "LEFT_FLIPPER", "RIGHT_FLIPPER", "PORTAL", "NAME_LABEL", "ORIENTATION_LABEL", 
		"X_VELOCITY_LABEL", "Y_VELOCITY_LABEL", "WIDTH_LABEL", "HEIGHT_LABEL", 
		"OTHER_BOARD_LABEL", "OTHER_PORTAL_LABEL", "GRAVITY_LABEL", "FRICTION1_LABEL", 
		"FRICTION2_LABEL", "FIRE", "TRIGGER", "ACTION", "KEYUP", "KEYDOWN", "KEYTOK"
	};


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
	    


	public BoardFileLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "BoardFile.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\2\4B\u0234\b\1\4\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t"+
		"\b\4\t\t\t\4\n\t\n\4\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20"+
		"\t\20\4\21\t\21\4\22\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27"+
		"\t\27\4\30\t\30\4\31\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36"+
		"\t\36\4\37\t\37\4 \t \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\4"+
		"(\t(\4)\t)\4*\t*\4+\t+\4,\t,\4-\t-\4.\t.\4/\t/\4\60\t\60\4\61\t\61\4\62"+
		"\t\62\4\63\t\63\4\64\t\64\4\65\t\65\4\66\t\66\4\67\t\67\48\t8\49\t9\4"+
		":\t:\4;\t;\4<\t<\4=\t=\4>\t>\4?\t?\4@\t@\4A\tA\3\2\3\2\3\2\3\2\3\2\3\3"+
		"\3\3\3\3\3\3\3\3\3\3\3\4\3\4\3\4\3\5\3\5\3\6\3\6\3\7\3\7\3\7\3\7\3\7\3"+
		"\7\3\7\3\7\3\7\3\7\3\b\3\b\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t\3\t\3\t\3\n"+
		"\3\n\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\13\3\f\3"+
		"\f\3\f\3\f\3\f\3\f\3\r\3\r\3\r\3\r\3\r\3\r\3\16\3\16\3\17\3\17\3\17\3"+
		"\17\3\17\3\17\3\17\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\20\3\20\3\21\3"+
		"\21\3\21\3\21\3\21\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3\22\3"+
		"\23\3\23\3\23\3\23\3\24\3\24\3\25\3\25\3\25\3\25\3\25\3\25\3\25\3\26\3"+
		"\26\3\27\3\27\3\27\3\27\3\27\3\27\3\30\3\30\3\31\3\31\3\31\3\31\3\31\3"+
		"\31\3\32\3\32\3\32\3\32\3\32\3\33\3\33\3\33\3\33\3\33\3\33\3\34\3\34\3"+
		"\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\34\3\35\3\35\3\35\3"+
		"\35\3\35\3\35\3\36\6\36\u0123\n\36\r\36\16\36\u0124\3\37\5\37\u0128\n"+
		"\37\3\37\3\37\5\37\u012c\n\37\3 \3 \3!\3!\3\"\6\"\u0133\n\"\r\"\16\"\u0134"+
		"\3#\3#\3$\3$\3%\3%\3&\3&\3\'\3\'\3\'\5\'\u0142\n\'\3(\3(\3(\3(\3(\3(\3"+
		"(\3)\3)\3)\3)\3)\3)\3)\3)\3)\3)\3)\3)\3)\3)\3*\3*\3*\3*\3*\3*\3*\3*\3"+
		"*\3*\3*\3*\3*\3*\3*\3*\3+\3+\3+\3+\3+\3+\3+\3+\3+\3+\3+\3+\3+\3+\3,\3"+
		",\3,\3,\3,\3,\3,\3,\3,\3,\3-\3-\3-\3-\3-\3-\3.\3.\3.\3.\3.\3.\3.\3.\3"+
		".\3.\3.\3.\3.\3/\3/\3/\3/\3/\3/\3/\3/\3/\3/\3/\3/\3/\3/\3\60\3\60\3\60"+
		"\3\60\3\60\3\60\3\60\3\60\3\61\3\61\3\61\3\61\3\61\3\62\3\62\3\62\3\62"+
		"\3\62\3\62\3\62\3\62\3\62\3\62\3\62\3\62\3\63\3\63\3\63\3\63\3\63\3\63"+
		"\3\63\3\63\3\63\3\63\3\64\3\64\3\64\3\64\3\64\3\64\3\64\3\64\3\64\3\64"+
		"\3\65\3\65\3\65\3\65\3\65\3\65\3\66\3\66\3\66\3\66\3\66\3\66\3\66\3\67"+
		"\3\67\3\67\3\67\3\67\3\67\3\67\3\67\3\67\3\67\3\67\38\38\38\38\38\38\3"+
		"8\38\38\38\38\38\39\39\39\39\39\39\39\39\3:\3:\3:\3:\3:\3:\3:\3:\3:\3"+
		":\3;\3;\3;\3;\3;\3;\3;\3;\3;\3;\3<\3<\3<\3<\3<\3=\3=\3=\3=\3=\3=\3=\3"+
		"=\3>\3>\3>\3>\3>\3>\3>\3?\3?\3?\3?\3?\3?\3@\3@\3@\3@\3@\3@\3@\3@\3A\3"+
		"A\3A\3A\2B\3\3\1\5\4\1\7\5\1\t\6\1\13\7\1\r\b\1\17\t\1\21\n\1\23\13\1"+
		"\25\f\1\27\r\1\31\16\1\33\17\1\35\20\1\37\21\1!\22\1#\23\1%\24\1\'\25"+
		"\1)\26\1+\27\1-\30\1/\31\1\61\32\1\63\33\1\65\34\1\67\35\19\36\1;\37\1"+
		"= \1?!\1A\"\1C#\1E$\1G%\1I&\1K\'\1M(\1O)\1Q*\1S+\1U,\1W-\1Y.\1[/\1]\60"+
		"\1_\61\1a\62\1c\63\1e\64\1g\65\1i\66\1k\67\1m8\1o9\1q:\1s;\1u<\1w=\1y"+
		">\1{?\1}@\1\177A\1\u0081B\1\3\2\4\4\13\13\"\"\4C\\c|\u0238\2\3\3\2\2\2"+
		"\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2"+
		"\2\2\2\21\3\2\2\2\2\23\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2"+
		"\2\33\3\2\2\2\2\35\3\2\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2"+
		"\2\2\'\3\2\2\2\2)\3\2\2\2\2+\3\2\2\2\2-\3\2\2\2\2/\3\2\2\2\2\61\3\2\2"+
		"\2\2\63\3\2\2\2\2\65\3\2\2\2\2\67\3\2\2\2\29\3\2\2\2\2;\3\2\2\2\2=\3\2"+
		"\2\2\2?\3\2\2\2\2A\3\2\2\2\2C\3\2\2\2\2E\3\2\2\2\2G\3\2\2\2\2I\3\2\2\2"+
		"\2K\3\2\2\2\2M\3\2\2\2\2O\3\2\2\2\2Q\3\2\2\2\2S\3\2\2\2\2U\3\2\2\2\2W"+
		"\3\2\2\2\2Y\3\2\2\2\2[\3\2\2\2\2]\3\2\2\2\2_\3\2\2\2\2a\3\2\2\2\2c\3\2"+
		"\2\2\2e\3\2\2\2\2g\3\2\2\2\2i\3\2\2\2\2k\3\2\2\2\2m\3\2\2\2\2o\3\2\2\2"+
		"\2q\3\2\2\2\2s\3\2\2\2\2u\3\2\2\2\2w\3\2\2\2\2y\3\2\2\2\2{\3\2\2\2\2}"+
		"\3\2\2\2\2\177\3\2\2\2\2\u0081\3\2\2\2\3\u0083\3\2\2\2\5\u0088\3\2\2\2"+
		"\7\u008e\3\2\2\2\t\u0091\3\2\2\2\13\u0093\3\2\2\2\r\u0095\3\2\2\2\17\u009f"+
		"\3\2\2\2\21\u00a6\3\2\2\2\23\u00ab\3\2\2\2\25\u00ad\3\2\2\2\27\u00b9\3"+
		"\2\2\2\31\u00bf\3\2\2\2\33\u00c5\3\2\2\2\35\u00c7\3\2\2\2\37\u00d1\3\2"+
		"\2\2!\u00d7\3\2\2\2#\u00dc\3\2\2\2%\u00e6\3\2\2\2\'\u00ea\3\2\2\2)\u00ec"+
		"\3\2\2\2+\u00f3\3\2\2\2-\u00f5\3\2\2\2/\u00fb\3\2\2\2\61\u00fd\3\2\2\2"+
		"\63\u0103\3\2\2\2\65\u0108\3\2\2\2\67\u010e\3\2\2\29\u011b\3\2\2\2;\u0122"+
		"\3\2\2\2=\u0127\3\2\2\2?\u012d\3\2\2\2A\u012f\3\2\2\2C\u0132\3\2\2\2E"+
		"\u0136\3\2\2\2G\u0138\3\2\2\2I\u013a\3\2\2\2K\u013c\3\2\2\2M\u0141\3\2"+
		"\2\2O\u0143\3\2\2\2Q\u014a\3\2\2\2S\u0158\3\2\2\2U\u0168\3\2\2\2W\u0176"+
		"\3\2\2\2Y\u0180\3\2\2\2[\u0186\3\2\2\2]\u0193\3\2\2\2_\u01a1\3\2\2\2a"+
		"\u01a9\3\2\2\2c\u01ae\3\2\2\2e\u01ba\3\2\2\2g\u01c4\3\2\2\2i\u01ce\3\2"+
		"\2\2k\u01d4\3\2\2\2m\u01db\3\2\2\2o\u01e6\3\2\2\2q\u01f2\3\2\2\2s\u01fa"+
		"\3\2\2\2u\u0204\3\2\2\2w\u020e\3\2\2\2y\u0213\3\2\2\2{\u021b\3\2\2\2}"+
		"\u0222\3\2\2\2\177\u0228\3\2\2\2\u0081\u0230\3\2\2\2\u0083\u0084\7f\2"+
		"\2\u0084\u0085\7q\2\2\u0085\u0086\7y\2\2\u0086\u0087\7p\2\2\u0087\4\3"+
		"\2\2\2\u0088\u0089\7u\2\2\u0089\u008a\7j\2\2\u008a\u008b\7k\2\2\u008b"+
		"\u008c\7h\2\2\u008c\u008d\7v\2\2\u008d\6\3\2\2\2\u008e\u008f\7w\2\2\u008f"+
		"\u0090\7r\2\2\u0090\b\3\2\2\2\u0091\u0092\7{\2\2\u0092\n\3\2\2\2\u0093"+
		"\u0094\7A\2\2\u0094\f\3\2\2\2\u0095\u0096\7d\2\2\u0096\u0097\7c\2\2\u0097"+
		"\u0098\7e\2\2\u0098\u0099\7m\2\2\u0099\u009a\7u\2\2\u009a\u009b\7r\2\2"+
		"\u009b\u009c\7c\2\2\u009c\u009d\7e\2\2\u009d\u009e\7g\2\2\u009e\16\3\2"+
		"\2\2\u009f\u00a0\7r\2\2\u00a0\u00a1\7g\2\2\u00a1\u00a2\7t\2\2\u00a2\u00a3"+
		"\7k\2\2\u00a3\u00a4\7q\2\2\u00a4\u00a5\7f\2\2\u00a5\20\3\2\2\2\u00a6\u00a7"+
		"\7e\2\2\u00a7\u00a8\7v\2\2\u00a8\u00a9\7t\2\2\u00a9\u00aa\7n\2\2\u00aa"+
		"\22\3\2\2\2\u00ab\u00ac\7.\2\2\u00ac\24\3\2\2\2\u00ad\u00ae\7q\2\2\u00ae"+
		"\u00af\7r\2\2\u00af\u00b0\7g\2\2\u00b0\u00b1\7p\2\2\u00b1\u00b2\7d\2\2"+
		"\u00b2\u00b3\7t\2\2\u00b3\u00b4\7c\2\2\u00b4\u00b5\7e\2\2\u00b5\u00b6"+
		"\7m\2\2\u00b6\u00b7\7g\2\2\u00b7\u00b8\7v\2\2\u00b8\26\3\2\2\2\u00b9\u00ba"+
		"\7t\2\2\u00ba\u00bb\7k\2\2\u00bb\u00bc\7i\2\2\u00bc\u00bd\7j\2\2\u00bd"+
		"\u00be\7v\2\2\u00be\30\3\2\2\2\u00bf\u00c0\7u\2\2\u00c0\u00c1\7r\2\2\u00c1"+
		"\u00c2\7c\2\2\u00c2\u00c3\7e\2\2\u00c3\u00c4\7g\2\2\u00c4\32\3\2\2\2\u00c5"+
		"\u00c6\7\60\2\2\u00c6\34\3\2\2\2\u00c7\u00c8\7u\2\2\u00c8\u00c9\7g\2\2"+
		"\u00c9\u00ca\7o\2\2\u00ca\u00cb\7k\2\2\u00cb\u00cc\7e\2\2\u00cc\u00cd"+
		"\7q\2\2\u00cd\u00ce\7n\2\2\u00ce\u00cf\7q\2\2\u00cf\u00d0\7p\2\2\u00d0"+
		"\36\3\2\2\2\u00d1\u00d2\7s\2\2\u00d2\u00d3\7w\2\2\u00d3\u00d4\7q\2\2\u00d4"+
		"\u00d5\7v\2\2\u00d5\u00d6\7g\2\2\u00d6 \3\2\2\2\u00d7\u00d8\7n\2\2\u00d8"+
		"\u00d9\7g\2\2\u00d9\u00da\7h\2\2\u00da\u00db\7v\2\2\u00db\"\3\2\2\2\u00dc"+
		"\u00dd\7d\2\2\u00dd\u00de\7c\2\2\u00de\u00df\7e\2\2\u00df\u00e0\7m\2\2"+
		"\u00e0\u00e1\7u\2\2\u00e1\u00e2\7n\2\2\u00e2\u00e3\7c\2\2\u00e3\u00e4"+
		"\7u\2\2\u00e4\u00e5\7j\2\2\u00e5$\3\2\2\2\u00e6\u00e7\7c\2\2\u00e7\u00e8"+
		"\7n\2\2\u00e8\u00e9\7v\2\2\u00e9&\3\2\2\2\u00ea\u00eb\7z\2\2\u00eb(\3"+
		"\2\2\2\u00ec\u00ed\7g\2\2\u00ed\u00ee\7s\2\2\u00ee\u00ef\7w\2\2\u00ef"+
		"\u00f0\7c\2\2\u00f0\u00f1\7n\2\2\u00f1\u00f2\7u\2\2\u00f2*\3\2\2\2\u00f3"+
		"\u00f4\7<\2\2\u00f4,\3\2\2\2\u00f5\u00f6\7o\2\2\u00f6\u00f7\7k\2\2\u00f7"+
		"\u00f8\7p\2\2\u00f8\u00f9\7w\2\2\u00f9\u00fa\7u\2\2\u00fa.\3\2\2\2\u00fb"+
		"\u00fc\7#\2\2\u00fc\60\3\2\2\2\u00fd\u00fe\7e\2\2\u00fe\u00ff\7q\2\2\u00ff"+
		"\u0100\7o\2\2\u0100\u0101\7o\2\2\u0101\u0102\7c\2\2\u0102\62\3\2\2\2\u0103"+
		"\u0104\7o\2\2\u0104\u0105\7g\2\2\u0105\u0106\7v\2\2\u0106\u0107\7c\2\2"+
		"\u0107\64\3\2\2\2\u0108\u0109\7g\2\2\u0109\u010a\7p\2\2\u010a\u010b\7"+
		"v\2\2\u010b\u010c\7g\2\2\u010c\u010d\7t\2\2\u010d\66\3\2\2\2\u010e\u010f"+
		"\7e\2\2\u010f\u0110\7n\2\2\u0110\u0111\7q\2\2\u0111\u0112\7u\2\2\u0112"+
		"\u0113\7g\2\2\u0113\u0114\7d\2\2\u0114\u0115\7t\2\2\u0115\u0116\7c\2\2"+
		"\u0116\u0117\7e\2\2\u0117\u0118\7m\2\2\u0118\u0119\7g\2\2\u0119\u011a"+
		"\7v\2\2\u011a8\3\2\2\2\u011b\u011c\7u\2\2\u011c\u011d\7n\2\2\u011d\u011e"+
		"\7c\2\2\u011e\u011f\7u\2\2\u011f\u0120\7j\2\2\u0120:\3\2\2\2\u0121\u0123"+
		"\t\2\2\2\u0122\u0121\3\2\2\2\u0123\u0124\3\2\2\2\u0124\u0122\3\2\2\2\u0124"+
		"\u0125\3\2\2\2\u0125<\3\2\2\2\u0126\u0128\7\"\2\2\u0127\u0126\3\2\2\2"+
		"\u0127\u0128\3\2\2\2\u0128\u0129\3\2\2\2\u0129\u012b\7?\2\2\u012a\u012c"+
		"\7\"\2\2\u012b\u012a\3\2\2\2\u012b\u012c\3\2\2\2\u012c>\3\2\2\2\u012d"+
		"\u012e\7%\2\2\u012e@\3\2\2\2\u012f\u0130\7/\2\2\u0130B\3\2\2\2\u0131\u0133"+
		"\4\62;\2\u0132\u0131\3\2\2\2\u0133\u0134\3\2\2\2\u0134\u0132\3\2\2\2\u0134"+
		"\u0135\3\2\2\2\u0135D\3\2\2\2\u0136\u0137\4c|\2\u0137F\3\2\2\2\u0138\u0139"+
		"\4C\\\2\u0139H\3\2\2\2\u013a\u013b\t\3\2\2\u013bJ\3\2\2\2\u013c\u013d"+
		"\7a\2\2\u013dL\3\2\2\2\u013e\u013f\7\17\2\2\u013f\u0142\7\f\2\2\u0140"+
		"\u0142\7\f\2\2\u0141\u013e\3\2\2\2\u0141\u0140\3\2\2\2\u0142N\3\2\2\2"+
		"\u0143\u0144\7d\2\2\u0144\u0145\7q\2\2\u0145\u0146\7c\2\2\u0146\u0147"+
		"\7t\2\2\u0147\u0148\7f\2\2\u0148\u0149\7\"\2\2\u0149P\3\2\2\2\u014a\u014b"+
		"\7u\2\2\u014b\u014c\7s\2\2\u014c\u014d\7w\2\2\u014d\u014e\7c\2\2\u014e"+
		"\u014f\7t\2\2\u014f\u0150\7g\2\2\u0150\u0151\7D\2\2\u0151\u0152\7w\2\2"+
		"\u0152\u0153\7o\2\2\u0153\u0154\7r\2\2\u0154\u0155\7g\2\2\u0155\u0156"+
		"\7t\2\2\u0156\u0157\7\"\2\2\u0157R\3\2\2\2\u0158\u0159\7v\2\2\u0159\u015a"+
		"\7t\2\2\u015a\u015b\7k\2\2\u015b\u015c\7c\2\2\u015c\u015d\7p\2\2\u015d"+
		"\u015e\7i\2\2\u015e\u015f\7n\2\2\u015f\u0160\7g\2\2\u0160\u0161\7D\2\2"+
		"\u0161\u0162\7w\2\2\u0162\u0163\7o\2\2\u0163\u0164\7r\2\2\u0164\u0165"+
		"\7g\2\2\u0165\u0166\7t\2\2\u0166\u0167\7\"\2\2\u0167T\3\2\2\2\u0168\u0169"+
		"\7e\2\2\u0169\u016a\7k\2\2\u016a\u016b\7t\2\2\u016b\u016c\7e\2\2\u016c"+
		"\u016d\7n\2\2\u016d\u016e\7g\2\2\u016e\u016f\7D\2\2\u016f\u0170\7w\2\2"+
		"\u0170\u0171\7o\2\2\u0171\u0172\7r\2\2\u0172\u0173\7g\2\2\u0173\u0174"+
		"\7t\2\2\u0174\u0175\7\"\2\2\u0175V\3\2\2\2\u0176\u0177\7c\2\2\u0177\u0178"+
		"\7d\2\2\u0178\u0179\7u\2\2\u0179\u017a\7q\2\2\u017a\u017b\7t\2\2\u017b"+
		"\u017c\7d\2\2\u017c\u017d\7g\2\2\u017d\u017e\7t\2\2\u017e\u017f\7\"\2"+
		"\2\u017fX\3\2\2\2\u0180\u0181\7d\2\2\u0181\u0182\7c\2\2\u0182\u0183\7"+
		"n\2\2\u0183\u0184\7n\2\2\u0184\u0185\7\"\2\2\u0185Z\3\2\2\2\u0186\u0187"+
		"\7n\2\2\u0187\u0188\7g\2\2\u0188\u0189\7h\2\2\u0189\u018a\7v\2\2\u018a"+
		"\u018b\7H\2\2\u018b\u018c\7n\2\2\u018c\u018d\7k\2\2\u018d\u018e\7r\2\2"+
		"\u018e\u018f\7r\2\2\u018f\u0190\7g\2\2\u0190\u0191\7t\2\2\u0191\u0192"+
		"\7\"\2\2\u0192\\\3\2\2\2\u0193\u0194\7t\2\2\u0194\u0195\7k\2\2\u0195\u0196"+
		"\7i\2\2\u0196\u0197\7j\2\2\u0197\u0198\7v\2\2\u0198\u0199\7H\2\2\u0199"+
		"\u019a\7n\2\2\u019a\u019b\7k\2\2\u019b\u019c\7r\2\2\u019c\u019d\7r\2\2"+
		"\u019d\u019e\7g\2\2\u019e\u019f\7t\2\2\u019f\u01a0\7\"\2\2\u01a0^\3\2"+
		"\2\2\u01a1\u01a2\7r\2\2\u01a2\u01a3\7q\2\2\u01a3\u01a4\7t\2\2\u01a4\u01a5"+
		"\7v\2\2\u01a5\u01a6\7c\2\2\u01a6\u01a7\7n\2\2\u01a7\u01a8\7\"\2\2\u01a8"+
		"`\3\2\2\2\u01a9\u01aa\7p\2\2\u01aa\u01ab\7c\2\2\u01ab\u01ac\7o\2\2\u01ac"+
		"\u01ad\7g\2\2\u01adb\3\2\2\2\u01ae\u01af\7q\2\2\u01af\u01b0\7t\2\2\u01b0"+
		"\u01b1\7k\2\2\u01b1\u01b2\7g\2\2\u01b2\u01b3\7p\2\2\u01b3\u01b4\7v\2\2"+
		"\u01b4\u01b5\7c\2\2\u01b5\u01b6\7v\2\2\u01b6\u01b7\7k\2\2\u01b7\u01b8"+
		"\7q\2\2\u01b8\u01b9\7p\2\2\u01b9d\3\2\2\2\u01ba\u01bb\7z\2\2\u01bb\u01bc"+
		"\7X\2\2\u01bc\u01bd\7g\2\2\u01bd\u01be\7n\2\2\u01be\u01bf\7q\2\2\u01bf"+
		"\u01c0\7e\2\2\u01c0\u01c1\7k\2\2\u01c1\u01c2\7v\2\2\u01c2\u01c3\7{\2\2"+
		"\u01c3f\3\2\2\2\u01c4\u01c5\7{\2\2\u01c5\u01c6\7X\2\2\u01c6\u01c7\7g\2"+
		"\2\u01c7\u01c8\7n\2\2\u01c8\u01c9\7q\2\2\u01c9\u01ca\7e\2\2\u01ca\u01cb"+
		"\7k\2\2\u01cb\u01cc\7v\2\2\u01cc\u01cd\7{\2\2\u01cdh\3\2\2\2\u01ce\u01cf"+
		"\7y\2\2\u01cf\u01d0\7k\2\2\u01d0\u01d1\7f\2\2\u01d1\u01d2\7v\2\2\u01d2"+
		"\u01d3\7j\2\2\u01d3j\3\2\2\2\u01d4\u01d5\7j\2\2\u01d5\u01d6\7g\2\2\u01d6"+
		"\u01d7\7k\2\2\u01d7\u01d8\7i\2\2\u01d8\u01d9\7j\2\2\u01d9\u01da\7v\2\2"+
		"\u01dal\3\2\2\2\u01db\u01dc\7q\2\2\u01dc\u01dd\7v\2\2\u01dd\u01de\7j\2"+
		"\2\u01de\u01df\7g\2\2\u01df\u01e0\7t\2\2\u01e0\u01e1\7D\2\2\u01e1\u01e2"+
		"\7q\2\2\u01e2\u01e3\7c\2\2\u01e3\u01e4\7t\2\2\u01e4\u01e5\7f\2\2\u01e5"+
		"n\3\2\2\2\u01e6\u01e7\7q\2\2\u01e7\u01e8\7v\2\2\u01e8\u01e9\7j\2\2\u01e9"+
		"\u01ea\7g\2\2\u01ea\u01eb\7t\2\2\u01eb\u01ec\7R\2\2\u01ec\u01ed\7q\2\2"+
		"\u01ed\u01ee\7t\2\2\u01ee\u01ef\7v\2\2\u01ef\u01f0\7c\2\2\u01f0\u01f1"+
		"\7n\2\2\u01f1p\3\2\2\2\u01f2\u01f3\7i\2\2\u01f3\u01f4\7t\2\2\u01f4\u01f5"+
		"\7c\2\2\u01f5\u01f6\7x\2\2\u01f6\u01f7\7k\2\2\u01f7\u01f8\7v\2\2\u01f8"+
		"\u01f9\7{\2\2\u01f9r\3\2\2\2\u01fa\u01fb\7h\2\2\u01fb\u01fc\7t\2\2\u01fc"+
		"\u01fd\7k\2\2\u01fd\u01fe\7e\2\2\u01fe\u01ff\7v\2\2\u01ff\u0200\7k\2\2"+
		"\u0200\u0201\7q\2\2\u0201\u0202\7p\2\2\u0202\u0203\7\63\2\2\u0203t\3\2"+
		"\2\2\u0204\u0205\7h\2\2\u0205\u0206\7t\2\2\u0206\u0207\7k\2\2\u0207\u0208"+
		"\7e\2\2\u0208\u0209\7v\2\2\u0209\u020a\7k\2\2\u020a\u020b\7q\2\2\u020b"+
		"\u020c\7p\2\2\u020c\u020d\7\64\2\2\u020dv\3\2\2\2\u020e\u020f\7h\2\2\u020f"+
		"\u0210\7k\2\2\u0210\u0211\7t\2\2\u0211\u0212\7g\2\2\u0212x\3\2\2\2\u0213"+
		"\u0214\7v\2\2\u0214\u0215\7t\2\2\u0215\u0216\7k\2\2\u0216\u0217\7i\2\2"+
		"\u0217\u0218\7i\2\2\u0218\u0219\7g\2\2\u0219\u021a\7t\2\2\u021az\3\2\2"+
		"\2\u021b\u021c\7c\2\2\u021c\u021d\7e\2\2\u021d\u021e\7v\2\2\u021e\u021f"+
		"\7k\2\2\u021f\u0220\7q\2\2\u0220\u0221\7p\2\2\u0221|\3\2\2\2\u0222\u0223"+
		"\7m\2\2\u0223\u0224\7g\2\2\u0224\u0225\7{\2\2\u0225\u0226\7w\2\2\u0226"+
		"\u0227\7r\2\2\u0227~\3\2\2\2\u0228\u0229\7m\2\2\u0229\u022a\7g\2\2\u022a"+
		"\u022b\7{\2\2\u022b\u022c\7f\2\2\u022c\u022d\7q\2\2\u022d\u022e\7y\2\2"+
		"\u022e\u022f\7p\2\2\u022f\u0080\3\2\2\2\u0230\u0231\7m\2\2\u0231\u0232"+
		"\7g\2\2\u0232\u0233\7{\2\2\u0233\u0082\3\2\2\2\b\2\u0124\u0127\u012b\u0134"+
		"\u0141";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
	}
}