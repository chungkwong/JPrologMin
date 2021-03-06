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
 *
 * @author Chan Chung Kwong <1m02math@126.com>
 */
public class RepresentationException extends PrologException{
	static String FUNCTOR="representation_error";
	private final String expected;
	private final Term argument;
	/**
	 * Construct a type_error
	 * @param expected expected type
	 * @param argument the term causing this error
	 */
	public RepresentationException(String expected,Term argument) {
		this.expected=expected;
		this.argument=argument;
	}
	@Override
	public String getMessage(){
		return argument+" cannot represent "+expected;
	}
	/**
	 * @return expected type
	 */
	public String getExpected(){
		return expected;
	}
	/**
	 * @return the unexpected term
	 */
	public Term getArgument(){
		return argument;
	}
	@Override
	public Term getErrorTerm(){
		return new CompoundTerm(FUNCTOR,new Constant(expected),argument);
	}
}
