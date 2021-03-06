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
import com.github.chungkwong.jprologmin.*;
import java.math.*;
/**
 * Implementation of the operator log/1
 * @author Chan Chung Kwong <1m02math@126.com>
 */
public class Log extends Evaluable{
	public static final Log INSTANCE=new Log();
	public Log(){
		super(new EvaluableFunctor("log",1));
	}
	@Override
	protected Term evaluate(Object[] args){
		BigDecimal arg=Helper.toReal(args[0]);
		if(arg.signum()<=0)
			throw new CalculationException(CalculationException.Type.UNDEFINED);
		else
			return new Constant(BigDecimalMath.log(arg,MathContext.UNLIMITED));
	}
}