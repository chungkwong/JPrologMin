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

/**
 * Decorated subgoal
 * @author Chan Chung Kwong <1m02math@126.com>
 */
public class DecoratedSubgoal implements Cloneable{
	private Predication activator;
	private ExecutionState cutparent;
	/**
	 * Construct a DecoratedSubgoal
	 * @param activator the activator
	 * @param cutparent the cut parent
	 */
	public DecoratedSubgoal(Predication activator,ExecutionState cutparent){
		this.activator=activator;
		this.cutparent=cutparent;
	}
	@Override
	public String toString(){
		return getActivator().toString();
	}
	/**
	 * @return the activator
	 */
	public Predication getActivator(){
		return activator;
	}
	/**
	 * @return the cut parent
	 */
	public ExecutionState getCutparent(){
		return cutparent;
	}
	/**
	 * Set the activator
	 * @param activator the new value
	 */
	public void setActivator(Predication activator){
		this.activator=activator;
	}
	/**
	 * Set the cut parent
	 * @param cutparent the new one
	 */
	public void setCutparent(ExecutionState cutparent){
		this.cutparent=cutparent;
	}
	@Override
	public DecoratedSubgoal clone(){
		return new DecoratedSubgoal(activator,cutparent);
	}
}