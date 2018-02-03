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
package com.github.chungkwong.jprologmin.eval;
import java.math.*;
/**
 * Helper being used to do floating point operations. 
 * Note that these operations may not be accurate enough.
 * @author Chan Chung Kwong <1m02math@126.com>
 */
public class BigDecimalMath{
	//TODO
	/**
	 * Calculate sine
	 * @param x the argument
	 * @param prec the precision
	 * @return the value of the function
	 */
	public static final BigDecimal sin(BigDecimal x,MathContext prec){
		return BigDecimal.valueOf(Math.sin(x.doubleValue()));
	}
	/**
	 * Calculate sine
	 * @param x the argument
	 * @param prec the precision
	 * @return the value of the function
	 */
	public static final BigDecimal cos(BigDecimal x,MathContext prec){
		return BigDecimal.valueOf(Math.cos(x.doubleValue()));
	}
	/**
	 * Calculate cosine
	 * @param x the argument
	 * @param prec the precision
	 * @return the value of the function
	 */
	public static final BigDecimal atan(BigDecimal x,MathContext prec){
		return BigDecimal.valueOf(Math.atan(x.doubleValue()));
	}
	/**
	 * Calculate exp
	 * @param x the argument
	 * @param prec the precision
	 * @return the value of the function
	 */
	public static final BigDecimal exp(BigDecimal x,MathContext prec){
		return BigDecimal.valueOf(Math.exp(x.doubleValue()));
	}
	/**
	 * Calculate log
	 * @param x the argument
	 * @param prec the precision
	 * @return the value of the function
	 */
	public static final BigDecimal log(BigDecimal x,MathContext prec){
		return BigDecimal.valueOf(Math.log(x.doubleValue()));
	}
	/**
	 * Calculate square root
	 * @param x the argument
	 * @param prec the precision
	 * @return the value of the function
	 */
	public static final BigDecimal sqrt(BigDecimal x,MathContext prec){
		return BigDecimal.valueOf(Math.sqrt(x.doubleValue()));
	}
	/**
	 * Calculate power
	 * @param x the base
	 * @param y the power
	 * @param prec the precision
	 * @return the value of the function
	 */
	public static final BigDecimal pow(BigDecimal x,BigDecimal y,MathContext prec){
		return BigDecimal.valueOf(Math.pow(x.doubleValue(),y.doubleValue()));
	}
}
