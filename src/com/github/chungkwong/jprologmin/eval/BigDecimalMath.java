/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.chungkwong.jprologmin.eval;
import java.math.*;
/**
 *
 * @author Chan Chung Kwong <1m02math@126.com>
 */
public class BigDecimalMath{
	//TODO
	public static final  BigDecimal sin(BigDecimal x,MathContext prec){
		return BigDecimal.valueOf(Math.sin(x.doubleValue()));
	}
	public static final BigDecimal cos(BigDecimal x,MathContext prec){
		return BigDecimal.valueOf(Math.cos(x.doubleValue()));
	}
	public static final BigDecimal atan(BigDecimal x,MathContext prec){
		return BigDecimal.valueOf(Math.atan(x.doubleValue()));
	}
	public static final BigDecimal exp(BigDecimal x,MathContext prec){
		return BigDecimal.valueOf(Math.exp(x.doubleValue()));
	}
	public static final BigDecimal log(BigDecimal x,MathContext prec){
		return BigDecimal.valueOf(Math.log(x.doubleValue()));
	}
	public static final BigDecimal sqrt(BigDecimal x,MathContext prec){
		return BigDecimal.valueOf(Math.sqrt(x.doubleValue()));
	}
	public static final BigDecimal pow(BigDecimal x,BigDecimal y,MathContext prec){
		return BigDecimal.valueOf(Math.pow(x.doubleValue(),y.doubleValue()));
	}
}
