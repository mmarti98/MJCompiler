
package rs.ac.bg.etf.pp1;

import java_cup.runtime.Symbol;
%%

%{
	// ukljucivanje informacije o poziciji tokena, pravi token na osnovu tipa
	private Symbol new_symbol(int type) {
		return new Symbol(type, yyline+1, yycolumn);
	}
	
	// ukljucivanje informacije o poziciji tokena, pravi token na osnovu tipa i objekta
	private Symbol new_symbol(int type, Object value) {
		return new Symbol(type, yyline+1, yycolumn, value);
	}
%}

%cup
%line
%column

%xstate COMMENT

%eofval{
	return new_symbol(sym.EOF);
%eofval}

%%

" " 	{ }
"\b" 	{ }
"\t" 	{ }
"\r\n" 	{ }
"\f" 	{ }

"program" { return new_symbol(sym.PROGRAM, yytext());}
"break" { return new_symbol(sym.BREAK, yytext()); }
"else" 	{ return new_symbol(sym.ELSE, yytext()); }
"const" { return new_symbol(sym.CONST, yytext()); }
"if" { return new_symbol(sym.IF, yytext()); }
"do" { return new_symbol(sym.DO, yytext()); }
"while" { return new_symbol(sym.WHILE, yytext()); }
"new" { return new_symbol(sym.NEW, yytext()); }
"print" { return new_symbol(sym.PRINT, yytext()); }
"read" { return new_symbol(sym.READ, yytext()); }
"return" { return new_symbol(sym.RETURN, yytext()); }
"void" { return new_symbol(sym.VOID, yytext()); }
"continue" { return new_symbol(sym.CONTINUE, yytext()); }


"++" 		{ return new_symbol(sym.INCREMENT, yytext()); }
"--" 		{ return new_symbol(sym.DECREMENT, yytext()); }
"+" 		{ return new_symbol(sym.PLUS, yytext()); }
"-" 		{ return new_symbol(sym.MINUS, yytext()); }
"*" 		{ return new_symbol(sym.MUL, yytext()); }
"/" 		{ return new_symbol(sym.DIV, yytext()); }
"%" 		{ return new_symbol(sym.MOD, yytext()); }
"==" 		{ return new_symbol(sym.EQUAL, yytext()); }
"!=" 		{ return new_symbol(sym.NOT_EQUAL, yytext()); }
">=" 		{ return new_symbol(sym.GREATER_EQUAL, yytext()); }
"<=" 		{ return new_symbol(sym.LESS_EQUAL, yytext()); }
">" 		{ return new_symbol(sym.GREATER, yytext()); }
"<" 		{ return new_symbol(sym.LESS, yytext()); }
"&&" 		{ return new_symbol(sym.LOGICAL_AND, yytext()); }
"||" 		{ return new_symbol(sym.LOGICAL_OR, yytext()); }
"=" 		{ return new_symbol(sym.ASSIGN, yytext()); }
";" 		{ return new_symbol(sym.SEMICOLON, yytext()); }
"," 		{ return new_symbol(sym.COMMA, yytext()); }
"(" 		{ return new_symbol(sym.LEFT_PARENTHESIS, yytext()); }
")" 		{ return new_symbol(sym.RIGHT_PARENTHESIS, yytext()); }
"[" 		{ return new_symbol(sym.LEFT_BRACKETS, yytext()); }
"]" 		{ return new_symbol(sym.RIGHT_BRACKETS, yytext()); }
"{" 		{ return new_symbol(sym.LEFT_BRACES, yytext()); }
"}"			{ return new_symbol(sym.RIGHT_BRACES, yytext()); }
"?" 		{ return new_symbol(sym.QUESTION_MARK, yytext()); }
":" 		{ return new_symbol(sym.COLON, yytext()); }

"//" {yybegin(COMMENT);}
<COMMENT> . {yybegin(COMMENT);}
<COMMENT> "\r\n" { yybegin(YYINITIAL); }


[0-9]+  { return new_symbol(sym.NUM_CONST, new Integer (yytext())); }
("'").("'")  { return new_symbol(sym.CHAR_CONST, new Character (yytext().charAt(1))); }
("true"|"false")  { return new_symbol(sym.BOOL_CONST, new Boolean (yytext())); }
([a-z]|[A-Z])[a-z|A-Z|0-9|_]* 	{return new_symbol (sym.IDENTIFICATOR, yytext()); }

. { System.err.println("Leksicka greska ("+yytext()+") u liniji "+(yyline+1) +" u koloni "+(yycolumn+1)); }




