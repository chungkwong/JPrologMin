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
 * system_error
 * @author Chan Chung Kwong <1m02math@126.com>
 */
public class SystemException extends RuntimeException{
	/**
	 * Creates a new instance of <code>SystemException</code> without detail message.
	 */
	public SystemException(){
	}
	/**
	 * Constructs an instance of <code>SystemException</code> with the specified detail message.
	 * @param msg the detail message.
	 */
	public SystemException(String msg) {
		super(msg);
	}
	/**
	 * Constructs an instance of <code>SystemException</code>.
	 * @param cause the cause
	 */
	public SystemException(Throwable cause){
		super(cause);
	}
	/**
	 * Constructs an instance of <code>SystemException</code>.
	 * @param msg the detail message.
	 * @param cause the cause
	 */
	public SystemException(String msg,Throwable cause){
		super(msg,cause);
	}
}
