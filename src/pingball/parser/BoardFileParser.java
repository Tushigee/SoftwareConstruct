// Generated from /Users/dve/Documents/6.005/pingball-phase3/src/pingball/parser/BoardFile.g4 by ANTLR 4.0

package pingball.parser;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class BoardFileParser extends Parser {
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
	public static final String[] tokenNames = {
		"<INVALID>", "'down'", "'shift'", "'up'", "'y'", "'?'", "'backspace'", 
		"'period'", "'ctrl'", "','", "'openbracket'", "'right'", "'space'", "'.'", 
		"'semicolon'", "'quote'", "'left'", "'backslash'", "'alt'", "'x'", "'equals'", 
		"':'", "'minus'", "'!'", "'comma'", "'meta'", "'enter'", "'closebracket'", 
		"'slash'", "SPACE", "EQUALS", "'#'", "'-'", "NUMBER", "LOWERCASE", "UPPERCASE", 
		"LETTER", "'_'", "NEWLINE", "'board '", "'squareBumper '", "'triangleBumper '", 
		"'circleBumper '", "'absorber '", "'ball '", "'leftFlipper '", "'rightFlipper '", 
		"'portal '", "'name'", "'orientation'", "'xVelocity'", "'yVelocity'", 
		"'width'", "'height'", "'otherBoard'", "'otherPortal'", "'gravity'", "'friction1'", 
		"'friction2'", "'fire'", "'trigger'", "'action'", "'keyup'", "'keydown'", 
		"'key'"
	};
	public static final int
		RULE_root = 0, RULE_otherLine = 1, RULE_boardLine = 2, RULE_boardElementLine = 3, 
		RULE_fireLine = 4, RULE_commentLine = 5, RULE_keyLine = 6, RULE_boardSpecifiers = 7, 
		RULE_gravitySpec = 8, RULE_friction1Spec = 9, RULE_friction2Spec = 10, 
		RULE_sqBumperLine = 11, RULE_trBumperLine = 12, RULE_circBumperLine = 13, 
		RULE_absorberLine = 14, RULE_portalLine = 15, RULE_leftFlipLine = 16, 
		RULE_rightFlipLine = 17, RULE_ballLine = 18, RULE_nameSpec = 19, RULE_xSpec = 20, 
		RULE_ySpec = 21, RULE_orientationSpec = 22, RULE_widthSpec = 23, RULE_heightSpec = 24, 
		RULE_otherBoardSpec = 25, RULE_otherPortalSpec = 26, RULE_xFloatSpec = 27, 
		RULE_yFloatSpec = 28, RULE_xVelSpec = 29, RULE_yVelSpec = 30, RULE_keySpec = 31, 
		RULE_triggerSpec = 32, RULE_actionSpec = 33, RULE_name = 34, RULE_antlrInt = 35, 
		RULE_antlrFloat = 36, RULE_key = 37;
	public static final String[] ruleNames = {
		"root", "otherLine", "boardLine", "boardElementLine", "fireLine", "commentLine", 
		"keyLine", "boardSpecifiers", "gravitySpec", "friction1Spec", "friction2Spec", 
		"sqBumperLine", "trBumperLine", "circBumperLine", "absorberLine", "portalLine", 
		"leftFlipLine", "rightFlipLine", "ballLine", "nameSpec", "xSpec", "ySpec", 
		"orientationSpec", "widthSpec", "heightSpec", "otherBoardSpec", "otherPortalSpec", 
		"xFloatSpec", "yFloatSpec", "xVelSpec", "yVelSpec", "keySpec", "triggerSpec", 
		"actionSpec", "name", "antlrInt", "antlrFloat", "key"
	};

	@Override
	public String getGrammarFileName() { return "BoardFile.g4"; }

	@Override
	public String[] getTokenNames() { return tokenNames; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public ATN getATN() { return _ATN; }


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
	    

	public BoardFileParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class RootContext extends ParserRuleContext {
		public OtherLineContext otherLine(int i) {
			return getRuleContext(OtherLineContext.class,i);
		}
		public List<OtherLineContext> otherLine() {
			return getRuleContexts(OtherLineContext.class);
		}
		public BoardLineContext boardLine() {
			return getRuleContext(BoardLineContext.class,0);
		}
		public TerminalNode EOF() { return getToken(BoardFileParser.EOF, 0); }
		public CommentLineContext commentLine(int i) {
			return getRuleContext(CommentLineContext.class,i);
		}
		public List<CommentLineContext> commentLine() {
			return getRuleContexts(CommentLineContext.class);
		}
		public RootContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_root; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BoardFileListener ) ((BoardFileListener)listener).enterRoot(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BoardFileListener ) ((BoardFileListener)listener).exitRoot(this);
		}
	}

	public final RootContext root() throws RecognitionException {
		RootContext _localctx = new RootContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_root);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(79);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,0,_ctx);
			while ( _alt!=2 && _alt!=-1 ) {
				if ( _alt==1 ) {
					{
					{
					setState(76); commentLine();
					}
					} 
				}
				setState(81);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,0,_ctx);
			}
			setState(82); boardLine();
			setState(86);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << SPACE) | (1L << COMMENT_MARKER) | (1L << SQUARE_BUMPER) | (1L << TRIANGLE_BUMPER) | (1L << CIRCLE_BUMPER) | (1L << ABSORBER) | (1L << BALL) | (1L << LEFT_FLIPPER) | (1L << RIGHT_FLIPPER) | (1L << PORTAL) | (1L << FIRE) | (1L << KEYUP) | (1L << KEYDOWN))) != 0)) {
				{
				{
				setState(83); otherLine();
				}
				}
				setState(88);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(89); match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OtherLineContext extends ParserRuleContext {
		public List<TerminalNode> SPACE() { return getTokens(BoardFileParser.SPACE); }
		public KeyLineContext keyLine() {
			return getRuleContext(KeyLineContext.class,0);
		}
		public FireLineContext fireLine() {
			return getRuleContext(FireLineContext.class,0);
		}
		public BoardElementLineContext boardElementLine() {
			return getRuleContext(BoardElementLineContext.class,0);
		}
		public TerminalNode SPACE(int i) {
			return getToken(BoardFileParser.SPACE, i);
		}
		public CommentLineContext commentLine() {
			return getRuleContext(CommentLineContext.class,0);
		}
		public OtherLineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_otherLine; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BoardFileListener ) ((BoardFileListener)listener).enterOtherLine(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BoardFileListener ) ((BoardFileListener)listener).exitOtherLine(this);
		}
	}

	public final OtherLineContext otherLine() throws RecognitionException {
		OtherLineContext _localctx = new OtherLineContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_otherLine);
		int _la;
		try {
			setState(116);
			switch ( getInterpreter().adaptivePredict(_input,9,_ctx) ) {
			case 1:
				enterOuterAlt(_localctx, 1);
				{
				{
				setState(92);
				_la = _input.LA(1);
				if (_la==SPACE) {
					{
					setState(91); match(SPACE);
					}
				}

				setState(94); boardElementLine();
				setState(96);
				switch ( getInterpreter().adaptivePredict(_input,3,_ctx) ) {
				case 1:
					{
					setState(95); match(SPACE);
					}
					break;
				}
				}
				}
				break;

			case 2:
				enterOuterAlt(_localctx, 2);
				{
				{
				setState(99);
				_la = _input.LA(1);
				if (_la==SPACE) {
					{
					setState(98); match(SPACE);
					}
				}

				setState(101); fireLine();
				setState(103);
				switch ( getInterpreter().adaptivePredict(_input,5,_ctx) ) {
				case 1:
					{
					setState(102); match(SPACE);
					}
					break;
				}
				}
				}
				break;

			case 3:
				enterOuterAlt(_localctx, 3);
				{
				{
				setState(105); commentLine();
				setState(107);
				switch ( getInterpreter().adaptivePredict(_input,6,_ctx) ) {
				case 1:
					{
					setState(106); match(SPACE);
					}
					break;
				}
				}
				}
				break;

			case 4:
				enterOuterAlt(_localctx, 4);
				{
				{
				setState(110);
				_la = _input.LA(1);
				if (_la==SPACE) {
					{
					setState(109); match(SPACE);
					}
				}

				setState(112); keyLine();
				setState(114);
				switch ( getInterpreter().adaptivePredict(_input,8,_ctx) ) {
				case 1:
					{
					setState(113); match(SPACE);
					}
					break;
				}
				}
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BoardLineContext extends ParserRuleContext {
		public TerminalNode BOARD() { return getToken(BoardFileParser.BOARD, 0); }
		public List<TerminalNode> SPACE() { return getTokens(BoardFileParser.SPACE); }
		public List<BoardSpecifiersContext> boardSpecifiers() {
			return getRuleContexts(BoardSpecifiersContext.class);
		}
		public NameSpecContext nameSpec() {
			return getRuleContext(NameSpecContext.class,0);
		}
		public BoardSpecifiersContext boardSpecifiers(int i) {
			return getRuleContext(BoardSpecifiersContext.class,i);
		}
		public TerminalNode SPACE(int i) {
			return getToken(BoardFileParser.SPACE, i);
		}
		public BoardLineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_boardLine; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BoardFileListener ) ((BoardFileListener)listener).enterBoardLine(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BoardFileListener ) ((BoardFileListener)listener).exitBoardLine(this);
		}
	}

	public final BoardLineContext boardLine() throws RecognitionException {
		BoardLineContext _localctx = new BoardLineContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_boardLine);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(119);
			_la = _input.LA(1);
			if (_la==SPACE) {
				{
				setState(118); match(SPACE);
				}
			}

			setState(121); match(BOARD);
			setState(122); nameSpec();
			setState(126);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
			while ( _alt!=2 && _alt!=-1 ) {
				if ( _alt==1 ) {
					{
					{
					setState(123); match(SPACE);
					}
					} 
				}
				setState(128);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,11,_ctx);
			}
			setState(138);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << GRAVITY_LABEL) | (1L << FRICTION1_LABEL) | (1L << FRICTION2_LABEL))) != 0)) {
				{
				{
				setState(129); boardSpecifiers();
				setState(133);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
				while ( _alt!=2 && _alt!=-1 ) {
					if ( _alt==1 ) {
						{
						{
						setState(130); match(SPACE);
						}
						} 
					}
					setState(135);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,12,_ctx);
				}
				}
				}
				setState(140);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BoardElementLineContext extends ParserRuleContext {
		public SqBumperLineContext sqBumperLine() {
			return getRuleContext(SqBumperLineContext.class,0);
		}
		public BallLineContext ballLine() {
			return getRuleContext(BallLineContext.class,0);
		}
		public PortalLineContext portalLine() {
			return getRuleContext(PortalLineContext.class,0);
		}
		public LeftFlipLineContext leftFlipLine() {
			return getRuleContext(LeftFlipLineContext.class,0);
		}
		public AbsorberLineContext absorberLine() {
			return getRuleContext(AbsorberLineContext.class,0);
		}
		public CircBumperLineContext circBumperLine() {
			return getRuleContext(CircBumperLineContext.class,0);
		}
		public RightFlipLineContext rightFlipLine() {
			return getRuleContext(RightFlipLineContext.class,0);
		}
		public TrBumperLineContext trBumperLine() {
			return getRuleContext(TrBumperLineContext.class,0);
		}
		public BoardElementLineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_boardElementLine; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BoardFileListener ) ((BoardFileListener)listener).enterBoardElementLine(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BoardFileListener ) ((BoardFileListener)listener).exitBoardElementLine(this);
		}
	}

	public final BoardElementLineContext boardElementLine() throws RecognitionException {
		BoardElementLineContext _localctx = new BoardElementLineContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_boardElementLine);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(149);
			switch (_input.LA(1)) {
			case BALL:
				{
				setState(141); ballLine();
				}
				break;
			case SQUARE_BUMPER:
				{
				setState(142); sqBumperLine();
				}
				break;
			case TRIANGLE_BUMPER:
				{
				setState(143); trBumperLine();
				}
				break;
			case CIRCLE_BUMPER:
				{
				setState(144); circBumperLine();
				}
				break;
			case ABSORBER:
				{
				setState(145); absorberLine();
				}
				break;
			case PORTAL:
				{
				setState(146); portalLine();
				}
				break;
			case LEFT_FLIPPER:
				{
				setState(147); leftFlipLine();
				}
				break;
			case RIGHT_FLIPPER:
				{
				setState(148); rightFlipLine();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class FireLineContext extends ParserRuleContext {
		public List<TerminalNode> SPACE() { return getTokens(BoardFileParser.SPACE); }
		public ActionSpecContext actionSpec() {
			return getRuleContext(ActionSpecContext.class,0);
		}
		public TerminalNode FIRE() { return getToken(BoardFileParser.FIRE, 0); }
		public TriggerSpecContext triggerSpec() {
			return getRuleContext(TriggerSpecContext.class,0);
		}
		public TerminalNode SPACE(int i) {
			return getToken(BoardFileParser.SPACE, i);
		}
		public FireLineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_fireLine; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BoardFileListener ) ((BoardFileListener)listener).enterFireLine(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BoardFileListener ) ((BoardFileListener)listener).exitFireLine(this);
		}
	}

	public final FireLineContext fireLine() throws RecognitionException {
		FireLineContext _localctx = new FireLineContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_fireLine);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(151); match(FIRE);
			setState(153); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(152); match(SPACE);
				}
				}
				setState(155); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==SPACE );
			setState(157); triggerSpec();
			setState(159); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(158); match(SPACE);
				}
				}
				setState(161); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==SPACE );
			setState(163); actionSpec();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CommentLineContext extends ParserRuleContext {
		public TerminalNode COMMENT_MARKER() { return getToken(BoardFileParser.COMMENT_MARKER, 0); }
		public TerminalNode SPACE() { return getToken(BoardFileParser.SPACE, 0); }
		public CommentLineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_commentLine; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BoardFileListener ) ((BoardFileListener)listener).enterCommentLine(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BoardFileListener ) ((BoardFileListener)listener).exitCommentLine(this);
		}
	}

	public final CommentLineContext commentLine() throws RecognitionException {
		CommentLineContext _localctx = new CommentLineContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_commentLine);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(166);
			_la = _input.LA(1);
			if (_la==SPACE) {
				{
				setState(165); match(SPACE);
				}
			}

			setState(168); match(COMMENT_MARKER);
			setState(179);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,19,_ctx);
			while ( _alt!=1 && _alt!=-1 ) {
				if ( _alt==1+1 ) {
					{
					setState(177);
					switch ( getInterpreter().adaptivePredict(_input,18,_ctx) ) {
					case 1:
						{
						setState(169);
						matchWildcard();
						}
						break;

					case 2:
						{
						setState(170); match(9);
						}
						break;

					case 3:
						{
						setState(171); match(23);
						}
						break;

					case 4:
						{
						setState(172); match(5);
						}
						break;

					case 5:
						{
						setState(173); match(21);
						}
						break;

					case 6:
						{
						setState(174); match(13);
						}
						break;

					case 7:
						{
						setState(175); match(4);
						}
						break;

					case 8:
						{
						setState(176); match(19);
						}
						break;
					}
					} 
				}
				setState(181);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,19,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class KeyLineContext extends ParserRuleContext {
		public List<TerminalNode> SPACE() { return getTokens(BoardFileParser.SPACE); }
		public ActionSpecContext actionSpec() {
			return getRuleContext(ActionSpecContext.class,0);
		}
		public TerminalNode KEYDOWN() { return getToken(BoardFileParser.KEYDOWN, 0); }
		public TerminalNode KEYUP() { return getToken(BoardFileParser.KEYUP, 0); }
		public TerminalNode SPACE(int i) {
			return getToken(BoardFileParser.SPACE, i);
		}
		public KeySpecContext keySpec() {
			return getRuleContext(KeySpecContext.class,0);
		}
		public KeyLineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_keyLine; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BoardFileListener ) ((BoardFileListener)listener).enterKeyLine(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BoardFileListener ) ((BoardFileListener)listener).exitKeyLine(this);
		}
	}

	public final KeyLineContext keyLine() throws RecognitionException {
		KeyLineContext _localctx = new KeyLineContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_keyLine);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(182);
			_la = _input.LA(1);
			if ( !(_la==KEYUP || _la==KEYDOWN) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			setState(183); match(SPACE);
			setState(184); keySpec();
			setState(185); match(SPACE);
			setState(186); actionSpec();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BoardSpecifiersContext extends ParserRuleContext {
		public Friction2SpecContext friction2Spec() {
			return getRuleContext(Friction2SpecContext.class,0);
		}
		public Friction1SpecContext friction1Spec() {
			return getRuleContext(Friction1SpecContext.class,0);
		}
		public GravitySpecContext gravitySpec() {
			return getRuleContext(GravitySpecContext.class,0);
		}
		public BoardSpecifiersContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_boardSpecifiers; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BoardFileListener ) ((BoardFileListener)listener).enterBoardSpecifiers(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BoardFileListener ) ((BoardFileListener)listener).exitBoardSpecifiers(this);
		}
	}

	public final BoardSpecifiersContext boardSpecifiers() throws RecognitionException {
		BoardSpecifiersContext _localctx = new BoardSpecifiersContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_boardSpecifiers);
		try {
			setState(191);
			switch (_input.LA(1)) {
			case GRAVITY_LABEL:
				enterOuterAlt(_localctx, 1);
				{
				setState(188); gravitySpec();
				}
				break;
			case FRICTION1_LABEL:
				enterOuterAlt(_localctx, 2);
				{
				setState(189); friction1Spec();
				}
				break;
			case FRICTION2_LABEL:
				enterOuterAlt(_localctx, 3);
				{
				setState(190); friction2Spec();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class GravitySpecContext extends ParserRuleContext {
		public TerminalNode EQUALS() { return getToken(BoardFileParser.EQUALS, 0); }
		public TerminalNode GRAVITY_LABEL() { return getToken(BoardFileParser.GRAVITY_LABEL, 0); }
		public AntlrFloatContext antlrFloat() {
			return getRuleContext(AntlrFloatContext.class,0);
		}
		public GravitySpecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_gravitySpec; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BoardFileListener ) ((BoardFileListener)listener).enterGravitySpec(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BoardFileListener ) ((BoardFileListener)listener).exitGravitySpec(this);
		}
	}

	public final GravitySpecContext gravitySpec() throws RecognitionException {
		GravitySpecContext _localctx = new GravitySpecContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_gravitySpec);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(193); match(GRAVITY_LABEL);
			setState(194); match(EQUALS);
			setState(195); antlrFloat();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Friction1SpecContext extends ParserRuleContext {
		public TerminalNode EQUALS() { return getToken(BoardFileParser.EQUALS, 0); }
		public AntlrFloatContext antlrFloat() {
			return getRuleContext(AntlrFloatContext.class,0);
		}
		public TerminalNode FRICTION1_LABEL() { return getToken(BoardFileParser.FRICTION1_LABEL, 0); }
		public Friction1SpecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_friction1Spec; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BoardFileListener ) ((BoardFileListener)listener).enterFriction1Spec(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BoardFileListener ) ((BoardFileListener)listener).exitFriction1Spec(this);
		}
	}

	public final Friction1SpecContext friction1Spec() throws RecognitionException {
		Friction1SpecContext _localctx = new Friction1SpecContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_friction1Spec);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(197); match(FRICTION1_LABEL);
			setState(198); match(EQUALS);
			setState(199); antlrFloat();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class Friction2SpecContext extends ParserRuleContext {
		public TerminalNode FRICTION2_LABEL() { return getToken(BoardFileParser.FRICTION2_LABEL, 0); }
		public TerminalNode EQUALS() { return getToken(BoardFileParser.EQUALS, 0); }
		public AntlrFloatContext antlrFloat() {
			return getRuleContext(AntlrFloatContext.class,0);
		}
		public Friction2SpecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_friction2Spec; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BoardFileListener ) ((BoardFileListener)listener).enterFriction2Spec(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BoardFileListener ) ((BoardFileListener)listener).exitFriction2Spec(this);
		}
	}

	public final Friction2SpecContext friction2Spec() throws RecognitionException {
		Friction2SpecContext _localctx = new Friction2SpecContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_friction2Spec);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(201); match(FRICTION2_LABEL);
			setState(202); match(EQUALS);
			setState(203); antlrFloat();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class SqBumperLineContext extends ParserRuleContext {
		public List<TerminalNode> SPACE() { return getTokens(BoardFileParser.SPACE); }
		public TerminalNode SQUARE_BUMPER() { return getToken(BoardFileParser.SQUARE_BUMPER, 0); }
		public XSpecContext xSpec() {
			return getRuleContext(XSpecContext.class,0);
		}
		public NameSpecContext nameSpec() {
			return getRuleContext(NameSpecContext.class,0);
		}
		public YSpecContext ySpec() {
			return getRuleContext(YSpecContext.class,0);
		}
		public TerminalNode SPACE(int i) {
			return getToken(BoardFileParser.SPACE, i);
		}
		public SqBumperLineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_sqBumperLine; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BoardFileListener ) ((BoardFileListener)listener).enterSqBumperLine(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BoardFileListener ) ((BoardFileListener)listener).exitSqBumperLine(this);
		}
	}

	public final SqBumperLineContext sqBumperLine() throws RecognitionException {
		SqBumperLineContext _localctx = new SqBumperLineContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_sqBumperLine);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(205); match(SQUARE_BUMPER);
			setState(206); nameSpec();
			setState(208); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(207); match(SPACE);
				}
				}
				setState(210); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==SPACE );
			setState(212); xSpec();
			setState(214); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(213); match(SPACE);
				}
				}
				setState(216); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==SPACE );
			setState(218); ySpec();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TrBumperLineContext extends ParserRuleContext {
		public List<TerminalNode> SPACE() { return getTokens(BoardFileParser.SPACE); }
		public XSpecContext xSpec() {
			return getRuleContext(XSpecContext.class,0);
		}
		public OrientationSpecContext orientationSpec() {
			return getRuleContext(OrientationSpecContext.class,0);
		}
		public NameSpecContext nameSpec() {
			return getRuleContext(NameSpecContext.class,0);
		}
		public YSpecContext ySpec() {
			return getRuleContext(YSpecContext.class,0);
		}
		public TerminalNode TRIANGLE_BUMPER() { return getToken(BoardFileParser.TRIANGLE_BUMPER, 0); }
		public TerminalNode SPACE(int i) {
			return getToken(BoardFileParser.SPACE, i);
		}
		public TrBumperLineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_trBumperLine; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BoardFileListener ) ((BoardFileListener)listener).enterTrBumperLine(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BoardFileListener ) ((BoardFileListener)listener).exitTrBumperLine(this);
		}
	}

	public final TrBumperLineContext trBumperLine() throws RecognitionException {
		TrBumperLineContext _localctx = new TrBumperLineContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_trBumperLine);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(220); match(TRIANGLE_BUMPER);
			setState(221); nameSpec();
			setState(223); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(222); match(SPACE);
				}
				}
				setState(225); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==SPACE );
			setState(227); xSpec();
			setState(229); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(228); match(SPACE);
				}
				}
				setState(231); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==SPACE );
			setState(233); ySpec();
			setState(235); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(234); match(SPACE);
				}
				}
				setState(237); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==SPACE );
			setState(239); orientationSpec();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class CircBumperLineContext extends ParserRuleContext {
		public List<TerminalNode> SPACE() { return getTokens(BoardFileParser.SPACE); }
		public XSpecContext xSpec() {
			return getRuleContext(XSpecContext.class,0);
		}
		public TerminalNode CIRCLE_BUMPER() { return getToken(BoardFileParser.CIRCLE_BUMPER, 0); }
		public NameSpecContext nameSpec() {
			return getRuleContext(NameSpecContext.class,0);
		}
		public YSpecContext ySpec() {
			return getRuleContext(YSpecContext.class,0);
		}
		public TerminalNode SPACE(int i) {
			return getToken(BoardFileParser.SPACE, i);
		}
		public CircBumperLineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_circBumperLine; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BoardFileListener ) ((BoardFileListener)listener).enterCircBumperLine(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BoardFileListener ) ((BoardFileListener)listener).exitCircBumperLine(this);
		}
	}

	public final CircBumperLineContext circBumperLine() throws RecognitionException {
		CircBumperLineContext _localctx = new CircBumperLineContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_circBumperLine);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(241); match(CIRCLE_BUMPER);
			setState(242); nameSpec();
			setState(244); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(243); match(SPACE);
				}
				}
				setState(246); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==SPACE );
			setState(248); xSpec();
			setState(250); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(249); match(SPACE);
				}
				}
				setState(252); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==SPACE );
			setState(254); ySpec();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AbsorberLineContext extends ParserRuleContext {
		public List<TerminalNode> SPACE() { return getTokens(BoardFileParser.SPACE); }
		public XSpecContext xSpec() {
			return getRuleContext(XSpecContext.class,0);
		}
		public NameSpecContext nameSpec() {
			return getRuleContext(NameSpecContext.class,0);
		}
		public TerminalNode ABSORBER() { return getToken(BoardFileParser.ABSORBER, 0); }
		public YSpecContext ySpec() {
			return getRuleContext(YSpecContext.class,0);
		}
		public HeightSpecContext heightSpec() {
			return getRuleContext(HeightSpecContext.class,0);
		}
		public WidthSpecContext widthSpec() {
			return getRuleContext(WidthSpecContext.class,0);
		}
		public TerminalNode SPACE(int i) {
			return getToken(BoardFileParser.SPACE, i);
		}
		public AbsorberLineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_absorberLine; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BoardFileListener ) ((BoardFileListener)listener).enterAbsorberLine(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BoardFileListener ) ((BoardFileListener)listener).exitAbsorberLine(this);
		}
	}

	public final AbsorberLineContext absorberLine() throws RecognitionException {
		AbsorberLineContext _localctx = new AbsorberLineContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_absorberLine);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(256); match(ABSORBER);
			setState(257); nameSpec();
			setState(259); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(258); match(SPACE);
				}
				}
				setState(261); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==SPACE );
			setState(263); xSpec();
			setState(265); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(264); match(SPACE);
				}
				}
				setState(267); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==SPACE );
			setState(269); ySpec();
			setState(271); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(270); match(SPACE);
				}
				}
				setState(273); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==SPACE );
			setState(275); widthSpec();
			setState(277); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(276); match(SPACE);
				}
				}
				setState(279); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==SPACE );
			setState(281); heightSpec();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class PortalLineContext extends ParserRuleContext {
		public List<TerminalNode> SPACE() { return getTokens(BoardFileParser.SPACE); }
		public XSpecContext xSpec() {
			return getRuleContext(XSpecContext.class,0);
		}
		public NameSpecContext nameSpec() {
			return getRuleContext(NameSpecContext.class,0);
		}
		public YSpecContext ySpec() {
			return getRuleContext(YSpecContext.class,0);
		}
		public OtherPortalSpecContext otherPortalSpec() {
			return getRuleContext(OtherPortalSpecContext.class,0);
		}
		public OtherBoardSpecContext otherBoardSpec() {
			return getRuleContext(OtherBoardSpecContext.class,0);
		}
		public TerminalNode SPACE(int i) {
			return getToken(BoardFileParser.SPACE, i);
		}
		public TerminalNode PORTAL() { return getToken(BoardFileParser.PORTAL, 0); }
		public PortalLineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_portalLine; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BoardFileListener ) ((BoardFileListener)listener).enterPortalLine(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BoardFileListener ) ((BoardFileListener)listener).exitPortalLine(this);
		}
	}

	public final PortalLineContext portalLine() throws RecognitionException {
		PortalLineContext _localctx = new PortalLineContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_portalLine);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(283); match(PORTAL);
			setState(284); nameSpec();
			setState(286); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(285); match(SPACE);
				}
				}
				setState(288); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==SPACE );
			setState(290); xSpec();
			setState(292); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(291); match(SPACE);
				}
				}
				setState(294); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==SPACE );
			setState(296); ySpec();
			setState(298); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(297); match(SPACE);
				}
				}
				setState(300); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==SPACE );
			setState(303);
			_la = _input.LA(1);
			if (_la==OTHER_BOARD_LABEL) {
				{
				setState(302); otherBoardSpec();
				}
			}

			setState(305); otherPortalSpec();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class LeftFlipLineContext extends ParserRuleContext {
		public List<TerminalNode> SPACE() { return getTokens(BoardFileParser.SPACE); }
		public TerminalNode LEFT_FLIPPER() { return getToken(BoardFileParser.LEFT_FLIPPER, 0); }
		public XSpecContext xSpec() {
			return getRuleContext(XSpecContext.class,0);
		}
		public OrientationSpecContext orientationSpec() {
			return getRuleContext(OrientationSpecContext.class,0);
		}
		public NameSpecContext nameSpec() {
			return getRuleContext(NameSpecContext.class,0);
		}
		public YSpecContext ySpec() {
			return getRuleContext(YSpecContext.class,0);
		}
		public TerminalNode SPACE(int i) {
			return getToken(BoardFileParser.SPACE, i);
		}
		public LeftFlipLineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_leftFlipLine; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BoardFileListener ) ((BoardFileListener)listener).enterLeftFlipLine(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BoardFileListener ) ((BoardFileListener)listener).exitLeftFlipLine(this);
		}
	}

	public final LeftFlipLineContext leftFlipLine() throws RecognitionException {
		LeftFlipLineContext _localctx = new LeftFlipLineContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_leftFlipLine);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(307); match(LEFT_FLIPPER);
			setState(308); nameSpec();
			setState(310); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(309); match(SPACE);
				}
				}
				setState(312); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==SPACE );
			setState(314); xSpec();
			setState(316); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(315); match(SPACE);
				}
				}
				setState(318); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==SPACE );
			setState(320); ySpec();
			setState(322); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(321); match(SPACE);
				}
				}
				setState(324); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==SPACE );
			setState(326); orientationSpec();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class RightFlipLineContext extends ParserRuleContext {
		public List<TerminalNode> SPACE() { return getTokens(BoardFileParser.SPACE); }
		public XSpecContext xSpec() {
			return getRuleContext(XSpecContext.class,0);
		}
		public OrientationSpecContext orientationSpec() {
			return getRuleContext(OrientationSpecContext.class,0);
		}
		public NameSpecContext nameSpec() {
			return getRuleContext(NameSpecContext.class,0);
		}
		public YSpecContext ySpec() {
			return getRuleContext(YSpecContext.class,0);
		}
		public TerminalNode RIGHT_FLIPPER() { return getToken(BoardFileParser.RIGHT_FLIPPER, 0); }
		public TerminalNode SPACE(int i) {
			return getToken(BoardFileParser.SPACE, i);
		}
		public RightFlipLineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_rightFlipLine; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BoardFileListener ) ((BoardFileListener)listener).enterRightFlipLine(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BoardFileListener ) ((BoardFileListener)listener).exitRightFlipLine(this);
		}
	}

	public final RightFlipLineContext rightFlipLine() throws RecognitionException {
		RightFlipLineContext _localctx = new RightFlipLineContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_rightFlipLine);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(328); match(RIGHT_FLIPPER);
			setState(329); nameSpec();
			setState(331); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(330); match(SPACE);
				}
				}
				setState(333); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==SPACE );
			setState(335); xSpec();
			setState(337); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(336); match(SPACE);
				}
				}
				setState(339); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==SPACE );
			setState(341); ySpec();
			setState(343); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(342); match(SPACE);
				}
				}
				setState(345); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==SPACE );
			setState(347); orientationSpec();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class BallLineContext extends ParserRuleContext {
		public List<TerminalNode> SPACE() { return getTokens(BoardFileParser.SPACE); }
		public TerminalNode BALL() { return getToken(BoardFileParser.BALL, 0); }
		public YFloatSpecContext yFloatSpec() {
			return getRuleContext(YFloatSpecContext.class,0);
		}
		public NameSpecContext nameSpec() {
			return getRuleContext(NameSpecContext.class,0);
		}
		public XVelSpecContext xVelSpec() {
			return getRuleContext(XVelSpecContext.class,0);
		}
		public YVelSpecContext yVelSpec() {
			return getRuleContext(YVelSpecContext.class,0);
		}
		public XFloatSpecContext xFloatSpec() {
			return getRuleContext(XFloatSpecContext.class,0);
		}
		public TerminalNode SPACE(int i) {
			return getToken(BoardFileParser.SPACE, i);
		}
		public BallLineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ballLine; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BoardFileListener ) ((BoardFileListener)listener).enterBallLine(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BoardFileListener ) ((BoardFileListener)listener).exitBallLine(this);
		}
	}

	public final BallLineContext ballLine() throws RecognitionException {
		BallLineContext _localctx = new BallLineContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_ballLine);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(349); match(BALL);
			setState(350); nameSpec();
			setState(352); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(351); match(SPACE);
				}
				}
				setState(354); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==SPACE );
			setState(356); xFloatSpec();
			setState(358); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(357); match(SPACE);
				}
				}
				setState(360); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==SPACE );
			setState(362); yFloatSpec();
			setState(364); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(363); match(SPACE);
				}
				}
				setState(366); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==SPACE );
			setState(368); xVelSpec();
			setState(370); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(369); match(SPACE);
				}
				}
				setState(372); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==SPACE );
			setState(374); yVelSpec();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NameSpecContext extends ParserRuleContext {
		public TerminalNode NAME_LABEL() { return getToken(BoardFileParser.NAME_LABEL, 0); }
		public TerminalNode EQUALS() { return getToken(BoardFileParser.EQUALS, 0); }
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public NameSpecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_nameSpec; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BoardFileListener ) ((BoardFileListener)listener).enterNameSpec(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BoardFileListener ) ((BoardFileListener)listener).exitNameSpec(this);
		}
	}

	public final NameSpecContext nameSpec() throws RecognitionException {
		NameSpecContext _localctx = new NameSpecContext(_ctx, getState());
		enterRule(_localctx, 38, RULE_nameSpec);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(376); match(NAME_LABEL);
			setState(377); match(EQUALS);
			setState(378); name();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class XSpecContext extends ParserRuleContext {
		public TerminalNode EQUALS() { return getToken(BoardFileParser.EQUALS, 0); }
		public AntlrIntContext antlrInt() {
			return getRuleContext(AntlrIntContext.class,0);
		}
		public XSpecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_xSpec; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BoardFileListener ) ((BoardFileListener)listener).enterXSpec(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BoardFileListener ) ((BoardFileListener)listener).exitXSpec(this);
		}
	}

	public final XSpecContext xSpec() throws RecognitionException {
		XSpecContext _localctx = new XSpecContext(_ctx, getState());
		enterRule(_localctx, 40, RULE_xSpec);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(380); match(19);
			setState(381); match(EQUALS);
			setState(382); antlrInt();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class YSpecContext extends ParserRuleContext {
		public TerminalNode EQUALS() { return getToken(BoardFileParser.EQUALS, 0); }
		public AntlrIntContext antlrInt() {
			return getRuleContext(AntlrIntContext.class,0);
		}
		public YSpecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_ySpec; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BoardFileListener ) ((BoardFileListener)listener).enterYSpec(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BoardFileListener ) ((BoardFileListener)listener).exitYSpec(this);
		}
	}

	public final YSpecContext ySpec() throws RecognitionException {
		YSpecContext _localctx = new YSpecContext(_ctx, getState());
		enterRule(_localctx, 42, RULE_ySpec);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(384); match(4);
			setState(385); match(EQUALS);
			setState(386); antlrInt();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OrientationSpecContext extends ParserRuleContext {
		public TerminalNode EQUALS() { return getToken(BoardFileParser.EQUALS, 0); }
		public AntlrIntContext antlrInt() {
			return getRuleContext(AntlrIntContext.class,0);
		}
		public TerminalNode ORIENTATION_LABEL() { return getToken(BoardFileParser.ORIENTATION_LABEL, 0); }
		public OrientationSpecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_orientationSpec; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BoardFileListener ) ((BoardFileListener)listener).enterOrientationSpec(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BoardFileListener ) ((BoardFileListener)listener).exitOrientationSpec(this);
		}
	}

	public final OrientationSpecContext orientationSpec() throws RecognitionException {
		OrientationSpecContext _localctx = new OrientationSpecContext(_ctx, getState());
		enterRule(_localctx, 44, RULE_orientationSpec);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(388); match(ORIENTATION_LABEL);
			setState(389); match(EQUALS);
			setState(390); antlrInt();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class WidthSpecContext extends ParserRuleContext {
		public TerminalNode EQUALS() { return getToken(BoardFileParser.EQUALS, 0); }
		public AntlrIntContext antlrInt() {
			return getRuleContext(AntlrIntContext.class,0);
		}
		public TerminalNode WIDTH_LABEL() { return getToken(BoardFileParser.WIDTH_LABEL, 0); }
		public WidthSpecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_widthSpec; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BoardFileListener ) ((BoardFileListener)listener).enterWidthSpec(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BoardFileListener ) ((BoardFileListener)listener).exitWidthSpec(this);
		}
	}

	public final WidthSpecContext widthSpec() throws RecognitionException {
		WidthSpecContext _localctx = new WidthSpecContext(_ctx, getState());
		enterRule(_localctx, 46, RULE_widthSpec);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(392); match(WIDTH_LABEL);
			setState(393); match(EQUALS);
			setState(394); antlrInt();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class HeightSpecContext extends ParserRuleContext {
		public TerminalNode EQUALS() { return getToken(BoardFileParser.EQUALS, 0); }
		public AntlrIntContext antlrInt() {
			return getRuleContext(AntlrIntContext.class,0);
		}
		public TerminalNode HEIGHT_LABEL() { return getToken(BoardFileParser.HEIGHT_LABEL, 0); }
		public HeightSpecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_heightSpec; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BoardFileListener ) ((BoardFileListener)listener).enterHeightSpec(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BoardFileListener ) ((BoardFileListener)listener).exitHeightSpec(this);
		}
	}

	public final HeightSpecContext heightSpec() throws RecognitionException {
		HeightSpecContext _localctx = new HeightSpecContext(_ctx, getState());
		enterRule(_localctx, 48, RULE_heightSpec);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(396); match(HEIGHT_LABEL);
			setState(397); match(EQUALS);
			setState(398); antlrInt();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OtherBoardSpecContext extends ParserRuleContext {
		public TerminalNode OTHER_BOARD_LABEL() { return getToken(BoardFileParser.OTHER_BOARD_LABEL, 0); }
		public TerminalNode SPACE() { return getToken(BoardFileParser.SPACE, 0); }
		public TerminalNode EQUALS() { return getToken(BoardFileParser.EQUALS, 0); }
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public OtherBoardSpecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_otherBoardSpec; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BoardFileListener ) ((BoardFileListener)listener).enterOtherBoardSpec(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BoardFileListener ) ((BoardFileListener)listener).exitOtherBoardSpec(this);
		}
	}

	public final OtherBoardSpecContext otherBoardSpec() throws RecognitionException {
		OtherBoardSpecContext _localctx = new OtherBoardSpecContext(_ctx, getState());
		enterRule(_localctx, 50, RULE_otherBoardSpec);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(400); match(OTHER_BOARD_LABEL);
			setState(401); match(EQUALS);
			setState(402); name();
			setState(404);
			_la = _input.LA(1);
			if (_la==SPACE) {
				{
				setState(403); match(SPACE);
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class OtherPortalSpecContext extends ParserRuleContext {
		public TerminalNode EQUALS() { return getToken(BoardFileParser.EQUALS, 0); }
		public TerminalNode OTHER_PORTAL_LABEL() { return getToken(BoardFileParser.OTHER_PORTAL_LABEL, 0); }
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public OtherPortalSpecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_otherPortalSpec; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BoardFileListener ) ((BoardFileListener)listener).enterOtherPortalSpec(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BoardFileListener ) ((BoardFileListener)listener).exitOtherPortalSpec(this);
		}
	}

	public final OtherPortalSpecContext otherPortalSpec() throws RecognitionException {
		OtherPortalSpecContext _localctx = new OtherPortalSpecContext(_ctx, getState());
		enterRule(_localctx, 52, RULE_otherPortalSpec);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(406); match(OTHER_PORTAL_LABEL);
			setState(407); match(EQUALS);
			setState(408); name();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class XFloatSpecContext extends ParserRuleContext {
		public TerminalNode EQUALS() { return getToken(BoardFileParser.EQUALS, 0); }
		public AntlrFloatContext antlrFloat() {
			return getRuleContext(AntlrFloatContext.class,0);
		}
		public XFloatSpecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_xFloatSpec; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BoardFileListener ) ((BoardFileListener)listener).enterXFloatSpec(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BoardFileListener ) ((BoardFileListener)listener).exitXFloatSpec(this);
		}
	}

	public final XFloatSpecContext xFloatSpec() throws RecognitionException {
		XFloatSpecContext _localctx = new XFloatSpecContext(_ctx, getState());
		enterRule(_localctx, 54, RULE_xFloatSpec);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(410); match(19);
			setState(411); match(EQUALS);
			setState(412); antlrFloat();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class YFloatSpecContext extends ParserRuleContext {
		public TerminalNode EQUALS() { return getToken(BoardFileParser.EQUALS, 0); }
		public AntlrFloatContext antlrFloat() {
			return getRuleContext(AntlrFloatContext.class,0);
		}
		public YFloatSpecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_yFloatSpec; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BoardFileListener ) ((BoardFileListener)listener).enterYFloatSpec(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BoardFileListener ) ((BoardFileListener)listener).exitYFloatSpec(this);
		}
	}

	public final YFloatSpecContext yFloatSpec() throws RecognitionException {
		YFloatSpecContext _localctx = new YFloatSpecContext(_ctx, getState());
		enterRule(_localctx, 56, RULE_yFloatSpec);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(414); match(4);
			setState(415); match(EQUALS);
			setState(416); antlrFloat();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class XVelSpecContext extends ParserRuleContext {
		public TerminalNode EQUALS() { return getToken(BoardFileParser.EQUALS, 0); }
		public AntlrFloatContext antlrFloat() {
			return getRuleContext(AntlrFloatContext.class,0);
		}
		public TerminalNode X_VELOCITY_LABEL() { return getToken(BoardFileParser.X_VELOCITY_LABEL, 0); }
		public XVelSpecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_xVelSpec; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BoardFileListener ) ((BoardFileListener)listener).enterXVelSpec(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BoardFileListener ) ((BoardFileListener)listener).exitXVelSpec(this);
		}
	}

	public final XVelSpecContext xVelSpec() throws RecognitionException {
		XVelSpecContext _localctx = new XVelSpecContext(_ctx, getState());
		enterRule(_localctx, 58, RULE_xVelSpec);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(418); match(X_VELOCITY_LABEL);
			setState(419); match(EQUALS);
			setState(420); antlrFloat();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class YVelSpecContext extends ParserRuleContext {
		public TerminalNode Y_VELOCITY_LABEL() { return getToken(BoardFileParser.Y_VELOCITY_LABEL, 0); }
		public TerminalNode EQUALS() { return getToken(BoardFileParser.EQUALS, 0); }
		public AntlrFloatContext antlrFloat() {
			return getRuleContext(AntlrFloatContext.class,0);
		}
		public YVelSpecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_yVelSpec; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BoardFileListener ) ((BoardFileListener)listener).enterYVelSpec(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BoardFileListener ) ((BoardFileListener)listener).exitYVelSpec(this);
		}
	}

	public final YVelSpecContext yVelSpec() throws RecognitionException {
		YVelSpecContext _localctx = new YVelSpecContext(_ctx, getState());
		enterRule(_localctx, 60, RULE_yVelSpec);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(422); match(Y_VELOCITY_LABEL);
			setState(423); match(EQUALS);
			setState(424); antlrFloat();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class KeySpecContext extends ParserRuleContext {
		public TerminalNode KEYTOK() { return getToken(BoardFileParser.KEYTOK, 0); }
		public TerminalNode EQUALS() { return getToken(BoardFileParser.EQUALS, 0); }
		public KeyContext key() {
			return getRuleContext(KeyContext.class,0);
		}
		public KeySpecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_keySpec; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BoardFileListener ) ((BoardFileListener)listener).enterKeySpec(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BoardFileListener ) ((BoardFileListener)listener).exitKeySpec(this);
		}
	}

	public final KeySpecContext keySpec() throws RecognitionException {
		KeySpecContext _localctx = new KeySpecContext(_ctx, getState());
		enterRule(_localctx, 62, RULE_keySpec);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(426); match(KEYTOK);
			setState(427); match(EQUALS);
			setState(428); key();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TriggerSpecContext extends ParserRuleContext {
		public TerminalNode EQUALS() { return getToken(BoardFileParser.EQUALS, 0); }
		public TerminalNode TRIGGER() { return getToken(BoardFileParser.TRIGGER, 0); }
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public TriggerSpecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_triggerSpec; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BoardFileListener ) ((BoardFileListener)listener).enterTriggerSpec(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BoardFileListener ) ((BoardFileListener)listener).exitTriggerSpec(this);
		}
	}

	public final TriggerSpecContext triggerSpec() throws RecognitionException {
		TriggerSpecContext _localctx = new TriggerSpecContext(_ctx, getState());
		enterRule(_localctx, 64, RULE_triggerSpec);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(430); match(TRIGGER);
			setState(431); match(EQUALS);
			setState(432); name();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class ActionSpecContext extends ParserRuleContext {
		public TerminalNode ACTION() { return getToken(BoardFileParser.ACTION, 0); }
		public TerminalNode EQUALS() { return getToken(BoardFileParser.EQUALS, 0); }
		public NameContext name() {
			return getRuleContext(NameContext.class,0);
		}
		public ActionSpecContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_actionSpec; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BoardFileListener ) ((BoardFileListener)listener).enterActionSpec(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BoardFileListener ) ((BoardFileListener)listener).exitActionSpec(this);
		}
	}

	public final ActionSpecContext actionSpec() throws RecognitionException {
		ActionSpecContext _localctx = new ActionSpecContext(_ctx, getState());
		enterRule(_localctx, 66, RULE_actionSpec);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(434); match(ACTION);
			setState(435); match(EQUALS);
			setState(436); name();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class NameContext extends ParserRuleContext {
		public List<TerminalNode> UNDERSCORE() { return getTokens(BoardFileParser.UNDERSCORE); }
		public List<TerminalNode> NUMBER() { return getTokens(BoardFileParser.NUMBER); }
		public TerminalNode UNDERSCORE(int i) {
			return getToken(BoardFileParser.UNDERSCORE, i);
		}
		public TerminalNode NUMBER(int i) {
			return getToken(BoardFileParser.NUMBER, i);
		}
		public List<TerminalNode> UPPERCASE() { return getTokens(BoardFileParser.UPPERCASE); }
		public List<TerminalNode> LOWERCASE() { return getTokens(BoardFileParser.LOWERCASE); }
		public TerminalNode UPPERCASE(int i) {
			return getToken(BoardFileParser.UPPERCASE, i);
		}
		public TerminalNode LOWERCASE(int i) {
			return getToken(BoardFileParser.LOWERCASE, i);
		}
		public NameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_name; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BoardFileListener ) ((BoardFileListener)listener).enterName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BoardFileListener ) ((BoardFileListener)listener).exitName(this);
		}
	}

	public final NameContext name() throws RecognitionException {
		NameContext _localctx = new NameContext(_ctx, getState());
		enterRule(_localctx, 68, RULE_name);
		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(439); 
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,47,_ctx);
			do {
				switch (_alt) {
				case 1:
					{
					{
					setState(438);
					_la = _input.LA(1);
					if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 3) | (1L << 4) | (1L << 19) | (1L << LOWERCASE) | (1L << UPPERCASE) | (1L << UNDERSCORE))) != 0)) ) {
					_errHandler.recoverInline(this);
					}
					consume();
					}
					}
					break;
				default:
					throw new NoViableAltException(this);
				}
				setState(441); 
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,47,_ctx);
			} while ( _alt!=2 && _alt!=-1 );
			setState(446);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 3) | (1L << 4) | (1L << 19) | (1L << NUMBER) | (1L << LOWERCASE) | (1L << UPPERCASE) | (1L << UNDERSCORE))) != 0)) {
				{
				{
				setState(443);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 3) | (1L << 4) | (1L << 19) | (1L << NUMBER) | (1L << LOWERCASE) | (1L << UPPERCASE) | (1L << UNDERSCORE))) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				consume();
				}
				}
				setState(448);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AntlrIntContext extends ParserRuleContext {
		public List<TerminalNode> NUMBER() { return getTokens(BoardFileParser.NUMBER); }
		public TerminalNode NUMBER(int i) {
			return getToken(BoardFileParser.NUMBER, i);
		}
		public AntlrIntContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_antlrInt; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BoardFileListener ) ((BoardFileListener)listener).enterAntlrInt(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BoardFileListener ) ((BoardFileListener)listener).exitAntlrInt(this);
		}
	}

	public final AntlrIntContext antlrInt() throws RecognitionException {
		AntlrIntContext _localctx = new AntlrIntContext(_ctx, getState());
		enterRule(_localctx, 70, RULE_antlrInt);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(450); 
			_errHandler.sync(this);
			_la = _input.LA(1);
			do {
				{
				{
				setState(449); match(NUMBER);
				}
				}
				setState(452); 
				_errHandler.sync(this);
				_la = _input.LA(1);
			} while ( _la==NUMBER );
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class AntlrFloatContext extends ParserRuleContext {
		public List<TerminalNode> NUMBER() { return getTokens(BoardFileParser.NUMBER); }
		public TerminalNode NUMBER(int i) {
			return getToken(BoardFileParser.NUMBER, i);
		}
		public TerminalNode NEGATIVE() { return getToken(BoardFileParser.NEGATIVE, 0); }
		public AntlrFloatContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_antlrFloat; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BoardFileListener ) ((BoardFileListener)listener).enterAntlrFloat(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BoardFileListener ) ((BoardFileListener)listener).exitAntlrFloat(this);
		}
	}

	public final AntlrFloatContext antlrFloat() throws RecognitionException {
		AntlrFloatContext _localctx = new AntlrFloatContext(_ctx, getState());
		enterRule(_localctx, 72, RULE_antlrFloat);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(455);
			_la = _input.LA(1);
			if (_la==NEGATIVE) {
				{
				setState(454); match(NEGATIVE);
				}
			}

			setState(473);
			switch ( getInterpreter().adaptivePredict(_input,54,_ctx) ) {
			case 1:
				{
				setState(457); match(NUMBER);
				setState(458); match(13);
				setState(462);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==NUMBER) {
					{
					{
					setState(459); match(NUMBER);
					}
					}
					setState(464);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
				break;

			case 2:
				{
				setState(466);
				_la = _input.LA(1);
				if (_la==13) {
					{
					setState(465); match(13);
					}
				}

				setState(469); 
				_errHandler.sync(this);
				_la = _input.LA(1);
				do {
					{
					{
					setState(468); match(NUMBER);
					}
					}
					setState(471); 
					_errHandler.sync(this);
					_la = _input.LA(1);
				} while ( _la==NUMBER );
				}
				break;
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class KeyContext extends ParserRuleContext {
		public TerminalNode NUMBER() { return getToken(BoardFileParser.NUMBER, 0); }
		public TerminalNode LOWERCASE() { return getToken(BoardFileParser.LOWERCASE, 0); }
		public KeyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_key; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof BoardFileListener ) ((BoardFileListener)listener).enterKey(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof BoardFileListener ) ((BoardFileListener)listener).exitKey(this);
		}
	}

	public final KeyContext key() throws RecognitionException {
		KeyContext _localctx = new KeyContext(_ctx, getState());
		enterRule(_localctx, 74, RULE_key);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(475);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << 1) | (1L << 2) | (1L << 3) | (1L << 6) | (1L << 7) | (1L << 8) | (1L << 10) | (1L << 11) | (1L << 12) | (1L << 14) | (1L << 15) | (1L << 16) | (1L << 17) | (1L << 18) | (1L << 20) | (1L << 22) | (1L << 24) | (1L << 25) | (1L << 26) | (1L << 27) | (1L << 28) | (1L << NUMBER) | (1L << LOWERCASE))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			consume();
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static final String _serializedATN =
		"\2\3B\u01e0\4\2\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4"+
		"\t\t\t\4\n\t\n\4\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20"+
		"\4\21\t\21\4\22\t\22\4\23\t\23\4\24\t\24\4\25\t\25\4\26\t\26\4\27\t\27"+
		"\4\30\t\30\4\31\t\31\4\32\t\32\4\33\t\33\4\34\t\34\4\35\t\35\4\36\t\36"+
		"\4\37\t\37\4 \t \4!\t!\4\"\t\"\4#\t#\4$\t$\4%\t%\4&\t&\4\'\t\'\3\2\7\2"+
		"P\n\2\f\2\16\2S\13\2\3\2\3\2\7\2W\n\2\f\2\16\2Z\13\2\3\2\3\2\3\3\5\3_"+
		"\n\3\3\3\3\3\5\3c\n\3\3\3\5\3f\n\3\3\3\3\3\5\3j\n\3\3\3\3\3\5\3n\n\3\3"+
		"\3\5\3q\n\3\3\3\3\3\5\3u\n\3\5\3w\n\3\3\4\5\4z\n\4\3\4\3\4\3\4\7\4\177"+
		"\n\4\f\4\16\4\u0082\13\4\3\4\3\4\7\4\u0086\n\4\f\4\16\4\u0089\13\4\7\4"+
		"\u008b\n\4\f\4\16\4\u008e\13\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\5\5\u0098"+
		"\n\5\3\6\3\6\6\6\u009c\n\6\r\6\16\6\u009d\3\6\3\6\6\6\u00a2\n\6\r\6\16"+
		"\6\u00a3\3\6\3\6\3\7\5\7\u00a9\n\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7\3\7"+
		"\7\7\u00b4\n\7\f\7\16\7\u00b7\13\7\3\b\3\b\3\b\3\b\3\b\3\b\3\t\3\t\3\t"+
		"\5\t\u00c2\n\t\3\n\3\n\3\n\3\n\3\13\3\13\3\13\3\13\3\f\3\f\3\f\3\f\3\r"+
		"\3\r\3\r\6\r\u00d3\n\r\r\r\16\r\u00d4\3\r\3\r\6\r\u00d9\n\r\r\r\16\r\u00da"+
		"\3\r\3\r\3\16\3\16\3\16\6\16\u00e2\n\16\r\16\16\16\u00e3\3\16\3\16\6\16"+
		"\u00e8\n\16\r\16\16\16\u00e9\3\16\3\16\6\16\u00ee\n\16\r\16\16\16\u00ef"+
		"\3\16\3\16\3\17\3\17\3\17\6\17\u00f7\n\17\r\17\16\17\u00f8\3\17\3\17\6"+
		"\17\u00fd\n\17\r\17\16\17\u00fe\3\17\3\17\3\20\3\20\3\20\6\20\u0106\n"+
		"\20\r\20\16\20\u0107\3\20\3\20\6\20\u010c\n\20\r\20\16\20\u010d\3\20\3"+
		"\20\6\20\u0112\n\20\r\20\16\20\u0113\3\20\3\20\6\20\u0118\n\20\r\20\16"+
		"\20\u0119\3\20\3\20\3\21\3\21\3\21\6\21\u0121\n\21\r\21\16\21\u0122\3"+
		"\21\3\21\6\21\u0127\n\21\r\21\16\21\u0128\3\21\3\21\6\21\u012d\n\21\r"+
		"\21\16\21\u012e\3\21\5\21\u0132\n\21\3\21\3\21\3\22\3\22\3\22\6\22\u0139"+
		"\n\22\r\22\16\22\u013a\3\22\3\22\6\22\u013f\n\22\r\22\16\22\u0140\3\22"+
		"\3\22\6\22\u0145\n\22\r\22\16\22\u0146\3\22\3\22\3\23\3\23\3\23\6\23\u014e"+
		"\n\23\r\23\16\23\u014f\3\23\3\23\6\23\u0154\n\23\r\23\16\23\u0155\3\23"+
		"\3\23\6\23\u015a\n\23\r\23\16\23\u015b\3\23\3\23\3\24\3\24\3\24\6\24\u0163"+
		"\n\24\r\24\16\24\u0164\3\24\3\24\6\24\u0169\n\24\r\24\16\24\u016a\3\24"+
		"\3\24\6\24\u016f\n\24\r\24\16\24\u0170\3\24\3\24\6\24\u0175\n\24\r\24"+
		"\16\24\u0176\3\24\3\24\3\25\3\25\3\25\3\25\3\26\3\26\3\26\3\26\3\27\3"+
		"\27\3\27\3\27\3\30\3\30\3\30\3\30\3\31\3\31\3\31\3\31\3\32\3\32\3\32\3"+
		"\32\3\33\3\33\3\33\3\33\5\33\u0197\n\33\3\34\3\34\3\34\3\34\3\35\3\35"+
		"\3\35\3\35\3\36\3\36\3\36\3\36\3\37\3\37\3\37\3\37\3 \3 \3 \3 \3!\3!\3"+
		"!\3!\3\"\3\"\3\"\3\"\3#\3#\3#\3#\3$\6$\u01ba\n$\r$\16$\u01bb\3$\7$\u01bf"+
		"\n$\f$\16$\u01c2\13$\3%\6%\u01c5\n%\r%\16%\u01c6\3&\5&\u01ca\n&\3&\3&"+
		"\3&\7&\u01cf\n&\f&\16&\u01d2\13&\3&\5&\u01d5\n&\3&\6&\u01d8\n&\r&\16&"+
		"\u01d9\5&\u01dc\n&\3\'\3\'\3\'\3\u00b5(\2\4\6\b\n\f\16\20\22\24\26\30"+
		"\32\34\36 \"$&(*,.\60\62\64\668:<>@BDFHJL\2\6\3@A\6\5\6\25\25$%\'\'\6"+
		"\5\6\25\25#%\'\'\n\3\5\b\n\f\16\20\24\26\26\30\30\32\36#$\u01ff\2Q\3\2"+
		"\2\2\4v\3\2\2\2\6y\3\2\2\2\b\u0097\3\2\2\2\n\u0099\3\2\2\2\f\u00a8\3\2"+
		"\2\2\16\u00b8\3\2\2\2\20\u00c1\3\2\2\2\22\u00c3\3\2\2\2\24\u00c7\3\2\2"+
		"\2\26\u00cb\3\2\2\2\30\u00cf\3\2\2\2\32\u00de\3\2\2\2\34\u00f3\3\2\2\2"+
		"\36\u0102\3\2\2\2 \u011d\3\2\2\2\"\u0135\3\2\2\2$\u014a\3\2\2\2&\u015f"+
		"\3\2\2\2(\u017a\3\2\2\2*\u017e\3\2\2\2,\u0182\3\2\2\2.\u0186\3\2\2\2\60"+
		"\u018a\3\2\2\2\62\u018e\3\2\2\2\64\u0192\3\2\2\2\66\u0198\3\2\2\28\u019c"+
		"\3\2\2\2:\u01a0\3\2\2\2<\u01a4\3\2\2\2>\u01a8\3\2\2\2@\u01ac\3\2\2\2B"+
		"\u01b0\3\2\2\2D\u01b4\3\2\2\2F\u01b9\3\2\2\2H\u01c4\3\2\2\2J\u01c9\3\2"+
		"\2\2L\u01dd\3\2\2\2NP\5\f\7\2ON\3\2\2\2PS\3\2\2\2QO\3\2\2\2QR\3\2\2\2"+
		"RT\3\2\2\2SQ\3\2\2\2TX\5\6\4\2UW\5\4\3\2VU\3\2\2\2WZ\3\2\2\2XV\3\2\2\2"+
		"XY\3\2\2\2Y[\3\2\2\2ZX\3\2\2\2[\\\7\1\2\2\\\3\3\2\2\2]_\7\37\2\2^]\3\2"+
		"\2\2^_\3\2\2\2_`\3\2\2\2`b\5\b\5\2ac\7\37\2\2ba\3\2\2\2bc\3\2\2\2cw\3"+
		"\2\2\2df\7\37\2\2ed\3\2\2\2ef\3\2\2\2fg\3\2\2\2gi\5\n\6\2hj\7\37\2\2i"+
		"h\3\2\2\2ij\3\2\2\2jw\3\2\2\2km\5\f\7\2ln\7\37\2\2ml\3\2\2\2mn\3\2\2\2"+
		"nw\3\2\2\2oq\7\37\2\2po\3\2\2\2pq\3\2\2\2qr\3\2\2\2rt\5\16\b\2su\7\37"+
		"\2\2ts\3\2\2\2tu\3\2\2\2uw\3\2\2\2v^\3\2\2\2ve\3\2\2\2vk\3\2\2\2vp\3\2"+
		"\2\2w\5\3\2\2\2xz\7\37\2\2yx\3\2\2\2yz\3\2\2\2z{\3\2\2\2{|\7)\2\2|\u0080"+
		"\5(\25\2}\177\7\37\2\2~}\3\2\2\2\177\u0082\3\2\2\2\u0080~\3\2\2\2\u0080"+
		"\u0081\3\2\2\2\u0081\u008c\3\2\2\2\u0082\u0080\3\2\2\2\u0083\u0087\5\20"+
		"\t\2\u0084\u0086\7\37\2\2\u0085\u0084\3\2\2\2\u0086\u0089\3\2\2\2\u0087"+
		"\u0085\3\2\2\2\u0087\u0088\3\2\2\2\u0088\u008b\3\2\2\2\u0089\u0087\3\2"+
		"\2\2\u008a\u0083\3\2\2\2\u008b\u008e\3\2\2\2\u008c\u008a\3\2\2\2\u008c"+
		"\u008d\3\2\2\2\u008d\7\3\2\2\2\u008e\u008c\3\2\2\2\u008f\u0098\5&\24\2"+
		"\u0090\u0098\5\30\r\2\u0091\u0098\5\32\16\2\u0092\u0098\5\34\17\2\u0093"+
		"\u0098\5\36\20\2\u0094\u0098\5 \21\2\u0095\u0098\5\"\22\2\u0096\u0098"+
		"\5$\23\2\u0097\u008f\3\2\2\2\u0097\u0090\3\2\2\2\u0097\u0091\3\2\2\2\u0097"+
		"\u0092\3\2\2\2\u0097\u0093\3\2\2\2\u0097\u0094\3\2\2\2\u0097\u0095\3\2"+
		"\2\2\u0097\u0096\3\2\2\2\u0098\t\3\2\2\2\u0099\u009b\7=\2\2\u009a\u009c"+
		"\7\37\2\2\u009b\u009a\3\2\2\2\u009c\u009d\3\2\2\2\u009d\u009b\3\2\2\2"+
		"\u009d\u009e\3\2\2\2\u009e\u009f\3\2\2\2\u009f\u00a1\5B\"\2\u00a0\u00a2"+
		"\7\37\2\2\u00a1\u00a0\3\2\2\2\u00a2\u00a3\3\2\2\2\u00a3\u00a1\3\2\2\2"+
		"\u00a3\u00a4\3\2\2\2\u00a4\u00a5\3\2\2\2\u00a5\u00a6\5D#\2\u00a6\13\3"+
		"\2\2\2\u00a7\u00a9\7\37\2\2\u00a8\u00a7\3\2\2\2\u00a8\u00a9\3\2\2\2\u00a9"+
		"\u00aa\3\2\2\2\u00aa\u00b5\7!\2\2\u00ab\u00b4\13\2\2\2\u00ac\u00b4\7\13"+
		"\2\2\u00ad\u00b4\7\31\2\2\u00ae\u00b4\7\7\2\2\u00af\u00b4\7\27\2\2\u00b0"+
		"\u00b4\7\17\2\2\u00b1\u00b4\7\6\2\2\u00b2\u00b4\7\25\2\2\u00b3\u00ab\3"+
		"\2\2\2\u00b3\u00ac\3\2\2\2\u00b3\u00ad\3\2\2\2\u00b3\u00ae\3\2\2\2\u00b3"+
		"\u00af\3\2\2\2\u00b3\u00b0\3\2\2\2\u00b3\u00b1\3\2\2\2\u00b3\u00b2\3\2"+
		"\2\2\u00b4\u00b7\3\2\2\2\u00b5\u00b6\3\2\2\2\u00b5\u00b3\3\2\2\2\u00b6"+
		"\r\3\2\2\2\u00b7\u00b5\3\2\2\2\u00b8\u00b9\t\2\2\2\u00b9\u00ba\7\37\2"+
		"\2\u00ba\u00bb\5@!\2\u00bb\u00bc\7\37\2\2\u00bc\u00bd\5D#\2\u00bd\17\3"+
		"\2\2\2\u00be\u00c2\5\22\n\2\u00bf\u00c2\5\24\13\2\u00c0\u00c2\5\26\f\2"+
		"\u00c1\u00be\3\2\2\2\u00c1\u00bf\3\2\2\2\u00c1\u00c0\3\2\2\2\u00c2\21"+
		"\3\2\2\2\u00c3\u00c4\7:\2\2\u00c4\u00c5\7 \2\2\u00c5\u00c6\5J&\2\u00c6"+
		"\23\3\2\2\2\u00c7\u00c8\7;\2\2\u00c8\u00c9\7 \2\2\u00c9\u00ca\5J&\2\u00ca"+
		"\25\3\2\2\2\u00cb\u00cc\7<\2\2\u00cc\u00cd\7 \2\2\u00cd\u00ce\5J&\2\u00ce"+
		"\27\3\2\2\2\u00cf\u00d0\7*\2\2\u00d0\u00d2\5(\25\2\u00d1\u00d3\7\37\2"+
		"\2\u00d2\u00d1\3\2\2\2\u00d3\u00d4\3\2\2\2\u00d4\u00d2\3\2\2\2\u00d4\u00d5"+
		"\3\2\2\2\u00d5\u00d6\3\2\2\2\u00d6\u00d8\5*\26\2\u00d7\u00d9\7\37\2\2"+
		"\u00d8\u00d7\3\2\2\2\u00d9\u00da\3\2\2\2\u00da\u00d8\3\2\2\2\u00da\u00db"+
		"\3\2\2\2\u00db\u00dc\3\2\2\2\u00dc\u00dd\5,\27\2\u00dd\31\3\2\2\2\u00de"+
		"\u00df\7+\2\2\u00df\u00e1\5(\25\2\u00e0\u00e2\7\37\2\2\u00e1\u00e0\3\2"+
		"\2\2\u00e2\u00e3\3\2\2\2\u00e3\u00e1\3\2\2\2\u00e3\u00e4\3\2\2\2\u00e4"+
		"\u00e5\3\2\2\2\u00e5\u00e7\5*\26\2\u00e6\u00e8\7\37\2\2\u00e7\u00e6\3"+
		"\2\2\2\u00e8\u00e9\3\2\2\2\u00e9\u00e7\3\2\2\2\u00e9\u00ea\3\2\2\2\u00ea"+
		"\u00eb\3\2\2\2\u00eb\u00ed\5,\27\2\u00ec\u00ee\7\37\2\2\u00ed\u00ec\3"+
		"\2\2\2\u00ee\u00ef\3\2\2\2\u00ef\u00ed\3\2\2\2\u00ef\u00f0\3\2\2\2\u00f0"+
		"\u00f1\3\2\2\2\u00f1\u00f2\5.\30\2\u00f2\33\3\2\2\2\u00f3\u00f4\7,\2\2"+
		"\u00f4\u00f6\5(\25\2\u00f5\u00f7\7\37\2\2\u00f6\u00f5\3\2\2\2\u00f7\u00f8"+
		"\3\2\2\2\u00f8\u00f6\3\2\2\2\u00f8\u00f9\3\2\2\2\u00f9\u00fa\3\2\2\2\u00fa"+
		"\u00fc\5*\26\2\u00fb\u00fd\7\37\2\2\u00fc\u00fb\3\2\2\2\u00fd\u00fe\3"+
		"\2\2\2\u00fe\u00fc\3\2\2\2\u00fe\u00ff\3\2\2\2\u00ff\u0100\3\2\2\2\u0100"+
		"\u0101\5,\27\2\u0101\35\3\2\2\2\u0102\u0103\7-\2\2\u0103\u0105\5(\25\2"+
		"\u0104\u0106\7\37\2\2\u0105\u0104\3\2\2\2\u0106\u0107\3\2\2\2\u0107\u0105"+
		"\3\2\2\2\u0107\u0108\3\2\2\2\u0108\u0109\3\2\2\2\u0109\u010b\5*\26\2\u010a"+
		"\u010c\7\37\2\2\u010b\u010a\3\2\2\2\u010c\u010d\3\2\2\2\u010d\u010b\3"+
		"\2\2\2\u010d\u010e\3\2\2\2\u010e\u010f\3\2\2\2\u010f\u0111\5,\27\2\u0110"+
		"\u0112\7\37\2\2\u0111\u0110\3\2\2\2\u0112\u0113\3\2\2\2\u0113\u0111\3"+
		"\2\2\2\u0113\u0114\3\2\2\2\u0114\u0115\3\2\2\2\u0115\u0117\5\60\31\2\u0116"+
		"\u0118\7\37\2\2\u0117\u0116\3\2\2\2\u0118\u0119\3\2\2\2\u0119\u0117\3"+
		"\2\2\2\u0119\u011a\3\2\2\2\u011a\u011b\3\2\2\2\u011b\u011c\5\62\32\2\u011c"+
		"\37\3\2\2\2\u011d\u011e\7\61\2\2\u011e\u0120\5(\25\2\u011f\u0121\7\37"+
		"\2\2\u0120\u011f\3\2\2\2\u0121\u0122\3\2\2\2\u0122\u0120\3\2\2\2\u0122"+
		"\u0123\3\2\2\2\u0123\u0124\3\2\2\2\u0124\u0126\5*\26\2\u0125\u0127\7\37"+
		"\2\2\u0126\u0125\3\2\2\2\u0127\u0128\3\2\2\2\u0128\u0126\3\2\2\2\u0128"+
		"\u0129\3\2\2\2\u0129\u012a\3\2\2\2\u012a\u012c\5,\27\2\u012b\u012d\7\37"+
		"\2\2\u012c\u012b\3\2\2\2\u012d\u012e\3\2\2\2\u012e\u012c\3\2\2\2\u012e"+
		"\u012f\3\2\2\2\u012f\u0131\3\2\2\2\u0130\u0132\5\64\33\2\u0131\u0130\3"+
		"\2\2\2\u0131\u0132\3\2\2\2\u0132\u0133\3\2\2\2\u0133\u0134\5\66\34\2\u0134"+
		"!\3\2\2\2\u0135\u0136\7/\2\2\u0136\u0138\5(\25\2\u0137\u0139\7\37\2\2"+
		"\u0138\u0137\3\2\2\2\u0139\u013a\3\2\2\2\u013a\u0138\3\2\2\2\u013a\u013b"+
		"\3\2\2\2\u013b\u013c\3\2\2\2\u013c\u013e\5*\26\2\u013d\u013f\7\37\2\2"+
		"\u013e\u013d\3\2\2\2\u013f\u0140\3\2\2\2\u0140\u013e\3\2\2\2\u0140\u0141"+
		"\3\2\2\2\u0141\u0142\3\2\2\2\u0142\u0144\5,\27\2\u0143\u0145\7\37\2\2"+
		"\u0144\u0143\3\2\2\2\u0145\u0146\3\2\2\2\u0146\u0144\3\2\2\2\u0146\u0147"+
		"\3\2\2\2\u0147\u0148\3\2\2\2\u0148\u0149\5.\30\2\u0149#\3\2\2\2\u014a"+
		"\u014b\7\60\2\2\u014b\u014d\5(\25\2\u014c\u014e\7\37\2\2\u014d\u014c\3"+
		"\2\2\2\u014e\u014f\3\2\2\2\u014f\u014d\3\2\2\2\u014f\u0150\3\2\2\2\u0150"+
		"\u0151\3\2\2\2\u0151\u0153\5*\26\2\u0152\u0154\7\37\2\2\u0153\u0152\3"+
		"\2\2\2\u0154\u0155\3\2\2\2\u0155\u0153\3\2\2\2\u0155\u0156\3\2\2\2\u0156"+
		"\u0157\3\2\2\2\u0157\u0159\5,\27\2\u0158\u015a\7\37\2\2\u0159\u0158\3"+
		"\2\2\2\u015a\u015b\3\2\2\2\u015b\u0159\3\2\2\2\u015b\u015c\3\2\2\2\u015c"+
		"\u015d\3\2\2\2\u015d\u015e\5.\30\2\u015e%\3\2\2\2\u015f\u0160\7.\2\2\u0160"+
		"\u0162\5(\25\2\u0161\u0163\7\37\2\2\u0162\u0161\3\2\2\2\u0163\u0164\3"+
		"\2\2\2\u0164\u0162\3\2\2\2\u0164\u0165\3\2\2\2\u0165\u0166\3\2\2\2\u0166"+
		"\u0168\58\35\2\u0167\u0169\7\37\2\2\u0168\u0167\3\2\2\2\u0169\u016a\3"+
		"\2\2\2\u016a\u0168\3\2\2\2\u016a\u016b\3\2\2\2\u016b\u016c\3\2\2\2\u016c"+
		"\u016e\5:\36\2\u016d\u016f\7\37\2\2\u016e\u016d\3\2\2\2\u016f\u0170\3"+
		"\2\2\2\u0170\u016e\3\2\2\2\u0170\u0171\3\2\2\2\u0171\u0172\3\2\2\2\u0172"+
		"\u0174\5<\37\2\u0173\u0175\7\37\2\2\u0174\u0173\3\2\2\2\u0175\u0176\3"+
		"\2\2\2\u0176\u0174\3\2\2\2\u0176\u0177\3\2\2\2\u0177\u0178\3\2\2\2\u0178"+
		"\u0179\5> \2\u0179\'\3\2\2\2\u017a\u017b\7\62\2\2\u017b\u017c\7 \2\2\u017c"+
		"\u017d\5F$\2\u017d)\3\2\2\2\u017e\u017f\7\25\2\2\u017f\u0180\7 \2\2\u0180"+
		"\u0181\5H%\2\u0181+\3\2\2\2\u0182\u0183\7\6\2\2\u0183\u0184\7 \2\2\u0184"+
		"\u0185\5H%\2\u0185-\3\2\2\2\u0186\u0187\7\63\2\2\u0187\u0188\7 \2\2\u0188"+
		"\u0189\5H%\2\u0189/\3\2\2\2\u018a\u018b\7\66\2\2\u018b\u018c\7 \2\2\u018c"+
		"\u018d\5H%\2\u018d\61\3\2\2\2\u018e\u018f\7\67\2\2\u018f\u0190\7 \2\2"+
		"\u0190\u0191\5H%\2\u0191\63\3\2\2\2\u0192\u0193\78\2\2\u0193\u0194\7 "+
		"\2\2\u0194\u0196\5F$\2\u0195\u0197\7\37\2\2\u0196\u0195\3\2\2\2\u0196"+
		"\u0197\3\2\2\2\u0197\65\3\2\2\2\u0198\u0199\79\2\2\u0199\u019a\7 \2\2"+
		"\u019a\u019b\5F$\2\u019b\67\3\2\2\2\u019c\u019d\7\25\2\2\u019d\u019e\7"+
		" \2\2\u019e\u019f\5J&\2\u019f9\3\2\2\2\u01a0\u01a1\7\6\2\2\u01a1\u01a2"+
		"\7 \2\2\u01a2\u01a3\5J&\2\u01a3;\3\2\2\2\u01a4\u01a5\7\64\2\2\u01a5\u01a6"+
		"\7 \2\2\u01a6\u01a7\5J&\2\u01a7=\3\2\2\2\u01a8\u01a9\7\65\2\2\u01a9\u01aa"+
		"\7 \2\2\u01aa\u01ab\5J&\2\u01ab?\3\2\2\2\u01ac\u01ad\7B\2\2\u01ad\u01ae"+
		"\7 \2\2\u01ae\u01af\5L\'\2\u01afA\3\2\2\2\u01b0\u01b1\7>\2\2\u01b1\u01b2"+
		"\7 \2\2\u01b2\u01b3\5F$\2\u01b3C\3\2\2\2\u01b4\u01b5\7?\2\2\u01b5\u01b6"+
		"\7 \2\2\u01b6\u01b7\5F$\2\u01b7E\3\2\2\2\u01b8\u01ba\t\3\2\2\u01b9\u01b8"+
		"\3\2\2\2\u01ba\u01bb\3\2\2\2\u01bb\u01b9\3\2\2\2\u01bb\u01bc\3\2\2\2\u01bc"+
		"\u01c0\3\2\2\2\u01bd\u01bf\t\4\2\2\u01be\u01bd\3\2\2\2\u01bf\u01c2\3\2"+
		"\2\2\u01c0\u01be\3\2\2\2\u01c0\u01c1\3\2\2\2\u01c1G\3\2\2\2\u01c2\u01c0"+
		"\3\2\2\2\u01c3\u01c5\7#\2\2\u01c4\u01c3\3\2\2\2\u01c5\u01c6\3\2\2\2\u01c6"+
		"\u01c4\3\2\2\2\u01c6\u01c7\3\2\2\2\u01c7I\3\2\2\2\u01c8\u01ca\7\"\2\2"+
		"\u01c9\u01c8\3\2\2\2\u01c9\u01ca\3\2\2\2\u01ca\u01db\3\2\2\2\u01cb\u01cc"+
		"\7#\2\2\u01cc\u01d0\7\17\2\2\u01cd\u01cf\7#\2\2\u01ce\u01cd\3\2\2\2\u01cf"+
		"\u01d2\3\2\2\2\u01d0\u01ce\3\2\2\2\u01d0\u01d1\3\2\2\2\u01d1\u01dc\3\2"+
		"\2\2\u01d2\u01d0\3\2\2\2\u01d3\u01d5\7\17\2\2\u01d4\u01d3\3\2\2\2\u01d4"+
		"\u01d5\3\2\2\2\u01d5\u01d7\3\2\2\2\u01d6\u01d8\7#\2\2\u01d7\u01d6\3\2"+
		"\2\2\u01d8\u01d9\3\2\2\2\u01d9\u01d7\3\2\2\2\u01d9\u01da\3\2\2\2\u01da"+
		"\u01dc\3\2\2\2\u01db\u01cb\3\2\2\2\u01db\u01d4\3\2\2\2\u01dcK\3\2\2\2"+
		"\u01dd\u01de\t\5\2\2\u01deM\3\2\2\29QX^beimptvy\u0080\u0087\u008c\u0097"+
		"\u009d\u00a3\u00a8\u00b3\u00b5\u00c1\u00d4\u00da\u00e3\u00e9\u00ef\u00f8"+
		"\u00fe\u0107\u010d\u0113\u0119\u0122\u0128\u012e\u0131\u013a\u0140\u0146"+
		"\u014f\u0155\u015b\u0164\u016a\u0170\u0176\u0196\u01bb\u01c0\u01c6\u01c9"+
		"\u01d0\u01d4\u01d9\u01db";
	public static final ATN _ATN =
		ATNSimulator.deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
	}
}