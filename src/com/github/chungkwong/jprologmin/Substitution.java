/*
 * Copyright (C) 2015 Chan Chung Kwong <1m02math@126.com>
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
 * A set of equations
 * @author Chan Chung Kwong <1m02math@126.com>
 */
public class Substitution{
	protected HashMap<Variable,Term> assignment;
	//private final HashMap<Variable,HashSet<Variable>> affect;
	/**
	 * A set with no equation
	 */
	public Substitution(){
		assignment=new HashMap<>();
	}
	/**
	 * A set of equations copy from another set
	 * @param parent
	 */
	protected Substitution(Substitution parent){
		assignment=parent.assignment;
	}
	/**
	 * Create a copy on write substitution
	 * @param parent to be copied
	 * @return a copy
	 */
	public static Substitution createCopy(Substitution parent){
		return new CopyOnWriteSubstitution(parent);
	}
	/**
	 * @param obj a variable
	 * @return val, where obj->val is a equation in the set
	 */
	public Term findRoot(Term obj){
		while(assignment.containsKey(obj)){
			obj=assignment.get(obj);
		}
		return obj;
	}
	boolean addEquation(Variable var,Term val){
		assignment.put(var,val);
		assignment.entrySet().stream().forEach((entry)->{
			entry.setValue(entry.getValue().substitute(var,val));
		});
		return true;
	}
	/**
	 * Add a equation
	 * @param var the variable
	 * @param val the value of that variable
	 * @return if the variable can be assigned
	 */
	public boolean assign(Variable var,Term val){
		Term root=findRoot(var);
		val=val.substitute(this);
		if(root instanceof Variable){
			if(val.getVariableSet().contains(var))
				return var.equals(val);
			else
				return addEquation((Variable)root,val);
		}else{
			return root.unities(val,this);
		}
	}
	/**
	 * Remove a equation from the set. You should ensure that no other
	 * equations is generated because of it.
	 * @param var assigned variable
	 */
	public void removeEquation(Variable var){
		assignment.remove(var);
	}
	/**
	 * @return if the set of equations is subject to occur check
	 */
	public boolean occurCheck(){
		return assignment.entrySet().stream().anyMatch(
				(entry)->entry.getValue().getVariableSet().contains(entry.getKey()));
	}
	@Override
	public String toString(){
		StringBuilder buf=new StringBuilder();
		buf.append("{");
		assignment.entrySet().stream().forEach((assign)->{
			buf.append(assign.getKey()).append('=').append(assign.getValue()).append(',');
		});
		buf.append('}');
		return buf.toString();
	}
	public String toStringUser(){
		StringBuilder buf=new StringBuilder();
		buf.append("{");
		assignment.entrySet().stream()
				.filter((assign)->!(assign.getKey()instanceof Variable.InternalVariable))
				.forEach((assign)->{
					buf.append(assign.getKey()).append('=').append(findRoot(assign.getValue())).append(',');
				});
		buf.append('}');
		return buf.toString();
	}
	/**
	 * @return the set of assigned variables
	 */
	public Set<Variable> getAssignedVariables(){
		return assignment.keySet();
	}
	static class CopyOnWriteSubstitution extends Substitution{
		private boolean changed=false;
		public CopyOnWriteSubstitution(Substitution parent){
			super(parent);
		}
		private void change(){
			if(!changed){
				changed=true;
				assignment=new HashMap<>(assignment);
			}
		}
		@Override
		protected boolean addEquation(Variable var,Term val){
			change();
			return super.addEquation(var,val);
		}
		@Override
		public void removeEquation(Variable var){
			change();
			super.removeEquation(var);
		}
	}
}