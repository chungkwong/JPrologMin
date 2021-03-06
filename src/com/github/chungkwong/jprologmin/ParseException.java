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

/**
 * Syntax error
 * @author Chan Chung Kwong <1m02math@126.com>
 */
public class ParseException extends PrologException{
	/**
	 * Creates a new instance of <code>ParseException</code> without detail message.
	 */
	public ParseException(){
	}
	/**
	 * Constructs an instance of <code>ParseException</code> with the specified detail message.
	 * @param msg the detail message.
	 */
	public ParseException(String msg) {
		super(msg);
	}
	/**
	 * Constructs an instance of <code>ParseException</code> with the specified detail message.
	 * @param cause the cause
	 */
	public ParseException(Throwable cause){
		super(cause);
	}
	@Override
	public Term getErrorTerm(){
		return new Constant("syntax_error");
	}
}
