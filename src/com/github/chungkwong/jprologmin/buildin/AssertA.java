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
package com.github.chungkwong.jprologmin.buildin;
import com.github.chungkwong.jprologmin.*;
import java.util.*;
/**
 * Implementation of the predicate asserta/1
 * @author Chan Chung Kwong <1m02math@126.com>
 */
public class AssertA extends BuildinPredicate{
	public static final AssertA INSTANCE=new AssertA();
	public static final Predicate pred=new Predicate("asserta",1);
	@Override
	public boolean activate(List<Term> arguments,Processor exec){
		Term clause=arguments.get(0);
		Clause toAdd;
		if(clause instanceof CompoundTerm&&((CompoundTerm)clause).getFunctor().equals(":-")
				&&((CompoundTerm)clause).getArguments().size()==2){
			toAdd=new Clause(((CompoundTerm)clause).getArguments().get(0).toHead()
					,((CompoundTerm)clause).getArguments().get(1).toBody());
		}else
			toAdd=new Clause(clause.toHead(),new Constant("true"));
		if(exec.getDatabase().isDynamic(toAdd.getHead().getPredicate())){
			exec.getDatabase().addClauseToFirst(toAdd);
			return true;
		}else{
			throw new PermissionException(new Constant("modify_clause")
						,new Constant("static_procedure"),toAdd.getHead().getPredicate().getIndicator());
		}
	}
	@Override
	public Predicate getPredicate(){
		return pred;
	}
}
