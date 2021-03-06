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
 * Implementation of the operator float_integer_part/1
 * @author Chan Chung Kwong <1m02math@126.com>
 */
public class IntegerPart extends Evaluable{
	public static final IntegerPart INSTANCE=new IntegerPart();
	private IntegerPart(){
		super(new EvaluableFunctor("float_integer_part",1));
	}
	@Override
	protected Term evaluate(Object[] args){
		if(args[0] instanceof BigDecimal){
			BigDecimal arg=((BigDecimal)args[0]);
			return new Constant(arg.setScale(0,RoundingMode.DOWN).toBigInteger());
		}else
			throw new TypeException("number",new Constant(args[0]));
	}
}