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
import com.github.chungkwong.jprologmin.eval.*;
import java.math.*;
import java.util.stream.*;
/**
 * Helper being used to handle terms
 * @author Chan Chung Kwong <1m02math@126.com>
 */
public class Helper{
	/**
	 * Get the value of a constant
	 * @param t the term
	 * @return the value
	 */
	public static final Object getConstantValue(Term t){
		if(t instanceof Constant)
			return((Constant)t).getValue();
		else if(t instanceof Variable)
			throw new com.github.chungkwong.jprologmin.InstantiationException((Variable)t);
		else
			throw new TypeException("constant",t);
	}
	/**
	 * Get the value of a atom
	 * @param t the term
	 * @return the value
	 */
	public static final String getAtomValue(Term t){
		if(t instanceof Constant&&((Constant)t).getValue()instanceof String)
			return (String)((Constant)t).getValue();
		else if(t instanceof Variable)
			throw new com.github.chungkwong.jprologmin.InstantiationException((Variable)t);
		else
			throw new TypeException("atom",t);
	}
	/**
	 * Get the value of a character
	 * @param t the term
	 * @return the value
	 */
	public static final char getCharacterValue(Term t){
		if(t instanceof Constant&&((Constant)t).getValue()instanceof String
				&&((String)((Constant)t).getValue()).length()==1)
			return ((String)((Constant)t).getValue()).charAt(0);
		else if(t instanceof Variable)
			throw new com.github.chungkwong.jprologmin.InstantiationException((Variable)t);
		else
			throw new TypeException("character",t);
	}
	/**
	 * Get the value of a integer
	 * @param t the term
	 * @return the value
	 */
	public static final BigInteger getIntegerValue(Term t){
		if(t instanceof Constant&&((Constant)t).getValue()instanceof BigInteger)
			return (BigInteger)((Constant)t).getValue();
		else if(t instanceof Variable)
			throw new com.github.chungkwong.jprologmin.InstantiationException((Variable)t);
		else
			throw new TypeException("integer",t);
	}
	/**
	 * Get the value of a number
	 * @param t the term
	 * @return the value
	 */
	public static final Number getNumberValue(Term t){
		if(t instanceof Constant
				&&(((Constant)t).getValue()instanceof BigInteger||((Constant)t).getValue()instanceof BigDecimal))
			return (Number)((Constant)t).getValue();
		else if(t instanceof Variable)
			throw new com.github.chungkwong.jprologmin.InstantiationException((Variable)t);
		else
			throw new TypeException("number",t);
	}
	/**
	 * Check if a term is a integer
	 * @param t the term
	 * @return the result
	 */
	public static final boolean isInteger(Term t){
		return t instanceof Constant&&((Constant)t).getValue() instanceof BigInteger;
	}
	/**
	 * Check if a term is a real
	 * @param t the term
	 * @return the result
	 */
	public static final boolean isReal(Term t){
		return t instanceof Constant&&((Constant)t).getValue() instanceof BigDecimal;
	}
	/**
	 * Check if a term is a number
	 * @param t the term
	 * @return the result
	 */
	public static final boolean isNumber(Term t){
		return isInteger(t)||isReal(t);
	}
	/**
	 * Check if a term is a atom
	 * @param t the term
	 * @return the result
	 */
	public static final boolean isAtom(Term t){
		return t instanceof Constant&&((Constant)t).getValue() instanceof String;
	}
	/**
	 * Check if a term is callable
	 * @param t the term
	 * @return the result
	 */
	public static final boolean isCallable(Term t){
		return t instanceof CompoundTerm||isAtom(t);
	}
	/**
	 * Convert a Object into BigDecimal
	 * @param o the object
	 * @return the number
	 */
	public static final BigDecimal toReal(Object o){
		if(o instanceof BigDecimal)
			return (BigDecimal)o;
		else if(o instanceof BigInteger)
			return new BigDecimal((BigInteger)o);
		else
			throw new TypeException("number",new Constant(o));
	}
	/**
	 * Get the sign of a number
	 * @param o the number
	 * @return the sign
	 */
	public static final int signum(Object o){
		if(o instanceof BigInteger)
			return ((BigInteger)o).signum();
		else if(o instanceof BigDecimal)
			return ((BigDecimal)o).signum();
		else
			throw new TypeException("number",new Constant(o));
	}
	/**
	 * Evaluate a expression
	 * @param t the expression
	 * @return the value
	 */
	public static Term evaluate(Term t){
		if(t instanceof Constant){
			return t;
		}else if(t instanceof CompoundTerm){
			CompoundTerm ct=(CompoundTerm)t;
			Evaluable evaluable=EvaluableFunctorTable.getEvaluableFunctor(ct.getFunctor().toString(),ct.getArguments().size());
			if(evaluable==null)
				throw new SystemException("Evaluable functor not found");
			else
				return evaluable.evaluate(ct.getArguments().stream().map((e)->evaluate(e)).collect(Collectors.toList()));
		}else if(t instanceof Variable){
			throw new com.github.chungkwong.jprologmin.InstantiationException((Variable)t);
		}else{
			assert false;
			throw new SystemException();
		}
	}
}