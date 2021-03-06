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
 * Implementation of the operator float_round/2
 * @author Chan Chung Kwong <1m02math@126.com>
 */
public class RoundFloat extends Evaluable{
	public static final RoundFloat INSTANCE=new RoundFloat();
	private RoundFloat(){
		super(new EvaluableFunctor("float_round",2));
	}
	@Override
	protected Term evaluate(Object[] args){
		if(args[0] instanceof BigDecimal){
			BigDecimal arg=((BigDecimal)args[0]);
			if(!(args[1] instanceof BigInteger))
				throw new TypeException("integer",new Constant(args[1]));
			int prec=((BigInteger)args[1]).intValueExact();
			return new Constant(arg.round(new MathContext(prec,RoundingMode.HALF_UP)));
		}else
			throw new TypeException("number",new Constant(args[0]));
	}
}