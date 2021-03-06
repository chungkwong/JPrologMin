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
import com.github.chungkwong.jprologmin.InstantiationException;
import com.github.chungkwong.jprologmin.*;
import java.util.*;
/**
 * Implementation of the predicate retract/1
 * @author Chan Chung Kwong <1m02math@126.com>
 */
public class Retract extends ReexecutableBuildinPredicate{
	public static final Retract INSTANCE=new Retract();
	public static final Predicate pred=new Predicate("retract",1);
	@Override
	public void firstActivate(List<Term> arguments,Processor exec,Variable var){

	}
	@Override
	public boolean againActivate(List<Term> arguments,Processor exec,Variable var){
		Term clause=arguments.get(0);
		Term head,body;
		if(clause instanceof CompoundTerm&&((CompoundTerm)clause).getFunctor().equals(":-")
				&&((CompoundTerm)clause).getArguments().size()==2){
			head=((CompoundTerm)clause).getArguments().get(0);
			body=((CompoundTerm)clause).getArguments().get(1);
		}else{
			head=clause;
			body=new Constant("true");
		}
		if(head instanceof Variable)
			throw new InstantiationException((Variable)head);
		else if(!Helper.isCallable(head))
			throw new TypeException("callable",head);
		Predicate predicate=((Predication)head).getPredicate();
		Procedure proc=exec.getDatabase().getProcedure(predicate);
		if(proc!=null){
			if(exec.getDatabase().isDynamic(predicate)){
				Iterator<Clause> iter=((UserPredicate)proc).getClauses().iterator();
				while(iter.hasNext()){
					Clause cand=iter.next();
					Term fromDatabase=new CompoundTerm(".",cand.getHead(),cand.getBody());
					Term fromUser=new CompoundTerm(".",head,body);
					Substitution subst=Substitution.createCopy(exec.getCurrentSubst());
					if(fromDatabase.renameAllVariable().unities(fromUser,subst)){
						iter.remove();
						if(((UserPredicate)proc).getClauses().isEmpty())
							exec.getDatabase().removeProcedure(predicate);
						return true;
					}
				}
			}else{
				throw new PermissionException(new Constant("modify_clause")
						,new Constant("static_procedure"),head);
			}
			return false;
		}else
			return false;
	}
	@Override
	public Predicate getPredicate(){
		return pred;
	}
}