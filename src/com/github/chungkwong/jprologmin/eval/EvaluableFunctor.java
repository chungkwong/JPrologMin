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
import java.util.*;
/**
 *
 * @author Chan Chung Kwong <1m02math@126.com>
 */
public class EvaluableFunctor{
	private final String functor;
	private final int arity;
	public EvaluableFunctor(String functor,int arity){
		this.functor=functor;
		this.arity=arity;
	}
	public String getFunctor(){
		return functor;
	}
	public int getArity(){
		return arity;
	}
	@Override
	public boolean equals(Object obj){
		return obj instanceof EvaluableFunctor&&((EvaluableFunctor)obj).getArity()==arity
				&&((EvaluableFunctor)obj).getFunctor().equals(functor);
	}
	@Override
	public int hashCode(){
		int hash=7;
		hash=11*hash+Objects.hashCode(this.functor);
		hash=11*hash+this.arity;
		return hash;
	}
	@Override
	public String toString(){
		return functor+"/"+arity;
	}
}
