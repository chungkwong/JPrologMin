/*
 * Copyright (C) 2016 Chan Chung Kwong <1m02math@126.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.github.chungkwong.jprologmin;
import java.io.*;
import java.math.*;
import java.util.*;
/**
 * A lexical analyzer for Prolog
 * @author Chan Chung Kwong <1m02math@126.com>
 */
public class PrologLex implements SimpleIterator<Object>{
	PushbackReader in;
	private static final String GRAPHIC_TOKEN_CHARACTER="#$&*+-./:<=>?@^~\\";
	/**
	 * Construct a prologLex from a Reader
	 * @param in the reader providing Prolog code to be analysised
	 */
	public PrologLex(Reader in){
		this.in=new PushbackReader(in,2);
	}
	/**
	 * Construct a prologLex from a Reader with character conversion enabled
	 * @param in the reader providing Prolog code to be analysised
	 * @param conversion character conversion table
	 */
	public PrologLex(Reader in,Map<Character,Character> conversion){
		this(new ConversionReader(in,conversion));
	}
	/**
	 * Analysis the remaining Prolog code
	 * @return the remaining tokens
	 * @throws IOException
	 */
	public ArrayList<Object> getRemainingTokens()throws IOException{
		ArrayList<Object> tokens=new ArrayList<>();
		Object next=nextToken();
		while(next!=null){
			tokens.add(next);
			next=nextToken();
		}
		return tokens;
	}
	private void unreadIfNotEOF(int c) throws IOException{
		if(c!=-1)
			in.unread(c);
	}
	private void eatLayoutText()throws IOException{
		while(true){
			int c=in.read();
			switch(c){
				case ' ':case '\r':case '\n':
					break;
				case '%':
					while(c!=-1&&c!='\n'&&c!='\r'){
						c=in.read();
					}
					break;
				case '/':
					c=in.read();
					if(c=='*'){
						bracket:while(true){
							c=in.read();
							if(c==-1)
								throw new LexicalException("comment not ended");
							while(c=='*'){
								c=in.read();
								if(c=='/')
									break bracket;
							}
						}
					}else{
						unreadIfNotEOF(c);
						in.unread('/');
						return;
					}
					break;
				case -1:
					return;
				default:
					in.unread(c);
					return;
			}
		}
	}
	private char getChar(int base)throws IOException{
		int c=in.read(),ch=0;
		while(c!='\\'&&c!=-1){
			ch=ch*base+Character.digit(c,base);
			c=in.read();
		}
		return (char)ch;
	}
	private char getEspaceCharacter() throws IOException{
		int c=in.read();
		switch(c){
			case '\\':case '`':case '\'':case '\"':
				return (char)c;
			case 'a':
				return '\u0007';
			case 'b':
				return '\b';
			case 'f':
				return '\f';
			case 'n':
				return '\n';
			case 'r':
				return '\r';
			case 't':
				return '\t';
			case 'v':
				return '\u000B';
			case 'x':
				return getChar(16);
			case '\r':
				c=in.read();
				if(c!='\n')
					unreadIfNotEOF(c);
			case '\n':
				return '\0';
			case -1:
				throw new LexicalException("reached end of the stream");
			default:
				in.unread(c);
				return getChar(8);
		}
	}
	private String getQuotedString(char quote) throws IOException{
		StringBuilder buf=new StringBuilder();
		while(true){
			int c=in.read();
			if(c==quote){
				c=in.read();
				if(c==quote)
					buf.append(quote);
				else{
					unreadIfNotEOF(c);
					break;
				}
			}else if(c=='\\'){
				char esc=getEspaceCharacter();
				if(esc!='\0')
					buf.append(esc);
			}else if(c==-1)
				break;
			else
				buf.append((char)c);
		}
		return buf.toString();
	}
	private String getGraphicToken(int start) throws IOException{
		StringBuilder buf=new StringBuilder();
		buf.append((char)start);
		while(GRAPHIC_TOKEN_CHARACTER.indexOf(start=in.read())!=-1)
			buf.append((char)start);
		unreadIfNotEOF(start);
		return buf.toString();
	}
	private Variable getVariable() throws IOException{
		int c=in.read();
		if(Character.isAlphabetic(c)||Character.isDigit(c)){
			StringBuilder buf=new StringBuilder();
			buf.append('_');
			do{
				buf.append((char)c);
				c=in.read();
			}while(Character.isAlphabetic(c)||Character.isDigit(c));
			unreadIfNotEOF(c);
			return new Variable(buf.toString());
		}else{
			unreadIfNotEOF(c);
			return Variable.WILDCARD;
		}
	}
	private Object getIdentifier(int start) throws IOException{
		StringBuilder buf=new StringBuilder();
		while(Character.isLetter(start)||Character.isDigit(start)||start=='_'){
			buf.append((char)start);
			start=in.read();
		}
		if(buf.length()==0)
			throw new LexicalException("Unknown token");
		unreadIfNotEOF(start);
		if(Character.isUpperCase(buf.charAt(0))){
			return new Variable(buf.toString());
		}else{
			return buf.toString();
		}
	}
	private BigInteger getInteger(int base) throws IOException{
		StringBuilder buf=new StringBuilder();
		int c=in.read();
		while(Character.digit(c,base)!=-1){
			buf.append((char)c);
			c=in.read();
		}
		unreadIfNotEOF(c);
		if(buf.length()==0)
			return BigInteger.ZERO;
		else
			return new BigInteger(buf.toString(),base);
	}
	private Number getNumber(int start) throws IOException{
		if(start=='0'){
			start=in.read();
			switch(start){
				case 'b':
					return getInteger(2);
				case 'o':
					return getInteger(8);
				case 'x':
					return getInteger(16);
				case '\'':
					start=in.read();
					if(start=='\'')
						in.read();
					else if(start=='\\')
						start=getEspaceCharacter();
					return BigInteger.valueOf(start);
			}
		}
		unreadIfNotEOF(start);
		BigInteger intPart=getInteger(10);
		start=in.read();
		if(start=='.'){
			int offset=0;
			BigDecimal val=new BigDecimal(intPart),pos=BigDecimal.ONE.movePointLeft(1);
			start=in.read();
			while(start>='0'&&start<='9'){
				val=val.add(BigDecimal.valueOf(start-'0').multiply(pos));
				pos=pos.movePointLeft(1);
				start=in.read();
			}
			if(start=='E'||start=='e'){
				start=in.read();
				boolean neg=false;
				if(start=='-')
					neg=true;
				else if(start==-1)
					throw new LexicalException("Number not ended");
				else if(start!='+')
					in.unread(start);
				int expPart=getInteger(10).intValue();
				val=neg?val.movePointLeft(expPart):val.movePointRight(expPart);
			}else
				unreadIfNotEOF(start);
			return val;
		}else{
			unreadIfNotEOF(start);
			return intPart;
		}
	}
	/**
	 * Find out the next token
	 * @return the next token
	 * @throws IOException
	 */
	public Object nextToken()throws IOException{
		eatLayoutText();
		int c=in.read();
		switch(c){
			case'(':case')':case'[':case ']':case '{':case '}':case '|':case ',':case ';':case '!':
				return String.valueOf((char)c);
			case '`':
				return getQuotedString('`');
			case '\'':
				return getQuotedString('\'');
			case '\"':
				return Lists.asCodeList(getQuotedString('\"'));
			case '.':case '#':case '$': case '&':case '*':case '+':case '-':case '/':case ':':
			case '<':case '=':case '>':case '?':case '@':case '^':case '~':case '\\':
				return getGraphicToken(c);
			case '_':
				return getVariable();
			case '0':case '1':case '2':case '3':case '4':case '5':case '6':case '7':case '8':case '9':
				return getNumber(c);
			case -1:
				in.close();
				return null;
			default:
				return getIdentifier(c);
		}
	}
	@Override
	public Object next(){
		try{
			return nextToken();
		}catch(IOException ex){
			return null;
		}
	}
	/**
	 * The entry of the debug tool which show the tokens generated from
	 * each input line
	 * @param args unused
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException{
		Scanner in=new Scanner(System.in);
		while(in.hasNextLine()){
			PrologLex lex=new PrologLex(new StringReader(in.nextLine()));
			System.out.println(lex.getRemainingTokens());
		}
	}
}
class ConversionReader extends Reader{
	private final Reader in;
	private final Map<Character,Character> conversion;
	public ConversionReader(Reader in,Map<Character,Character> conversion){
		this.in=in;
		this.conversion=conversion;
	}
	@Override
	public int read(char[] cbuf,int off,int len) throws IOException{
		int count=in.read(cbuf,off,len);
		for(int i=off;i<off+count;i++)
			if(conversion.containsKey(cbuf[i]))
				cbuf[i]=conversion.get(cbuf[i]);
		return count;
	}
	@Override
	public void close() throws IOException{
		in.close();
	}
}