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
 * Implementation of the operator abs/1
 * @author Chan Chung Kwong <1m02math@126.com>
 */
public class Abs extends Evaluable{
	public static final Abs INSTANCE=new Abs();
	public Abs(){
		super(new EvaluableFunctor("abs",1));
	}
	@Override
	protected Term evaluate(Object[] args){
		if(args[0] instanceof BigInteger)
			return new Constant(((BigInteger)args[0]).abs());
		else if(args[0] instanceof BigDecimal)
			return new Constant(((BigDecimal)args[0]).abs());
		else
			throw new TypeException("number",new Constant(args[0]));
	}
}
