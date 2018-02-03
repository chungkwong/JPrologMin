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
import java.util.*;
/**
 * Represents operators
 * @author Chan Chung Kwong <1m02math@126.com>
 */
public abstract class Evaluable{
	private final EvaluableFunctor functor;
	/**
	 * Create a operator
	 * @param functor the form of the operator
	 */
	public Evaluable(EvaluableFunctor functor){
		this.functor=functor;
	}
	/**
	 * Get the form of the operator
	 * @return
	 */
	public EvaluableFunctor getFunctor(){
		return functor;
	}
	/**
	 * Evaluate a expression 
	 * @param args the arguments given to this operator
	 * @return the value
	 */
	protected abstract Term evaluate(Object[] args);
	/**
	 * Evaluate a expression 
	 * @param args the arguments given to this operator
	 * @return the value
	 * @throws TypeException if some arguments is not constant
	 */
	public Term evaluate(List<Term> args){
		assert args.size()==functor.getArity();
		if(!args.stream().allMatch((t)->t instanceof Constant))
			throw new TypeException("constant",args.stream().filter((t)->!(t instanceof Constant)).findFirst().get());
		Object[] arguments=new Object[args.size()];
		for(int i=0;i<arguments.length;i++)
			arguments[i]=((Constant)args.get(i)).getValue();
		return evaluate(arguments);
	}
}