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
import java.util.*;
/**
 * Reexecutable buildin predicate
 * @author Chan Chung Kwong <1m02math@126.com>
 */
public abstract class ReexecutableBuildinPredicate implements Procedure{
	/**
	 * Executed when the goal first executed
	 * @param arguments the arguments of the goal
	 * @param exec the prolog processor
	 * @param var a variable that can be used to store information
	 */
	public abstract void firstActivate(List<Term> arguments,Processor exec,Variable var);
	/**
	 * Executed when the goal executed
	 * @param arguments the arguments of the goal
	 * @param exec the prolog processor
	 * @param var a variable that can be used to store information
	 * @return if the goal succeed
	 */
	public abstract boolean againActivate(List<Term> arguments,Processor exec,Variable var);
	@Override
	public void execute(Processor exec){
		exec.getStack().peek().setBI(ExecutionState.BacktraceInfo.BIP);
		firstActivate(exec.getCurrentActivator().getArguments(),exec,getVariable(exec.getStack().size()));
		reexecute(exec);
	}
	@Override
	public void reexecute(Processor exec){
		ExecutionState ccs=new ExecutionState(exec.getCurrentState());
		exec.getStack().push(ccs);
		if(againActivate(exec.getCurrentActivator().getArguments(),exec,getVariable(exec.getStack().size()-1))){
			exec.getCurrentDecoratedSubgoal().setActivator(new Constant("true"));
			exec.getCurrentState().substitute(exec.getCurrentSubst());
		}else{
			exec.getStack().pop();
			exec.getCurrentDecoratedSubgoal().setActivator(new Constant("fail"));
		}
	}
	@Override
	public String toString(){
		return getPredicate().toString();
	}
	private Variable getVariable(int index){
		return new Variable("_"+getPredicate().getFunctor()+index);
	}
}
