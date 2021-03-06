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
package com.github.chungkwong.jprologmin.directive;
import com.github.chungkwong.jprologmin.*;
import java.util.*;
/**
 * Implementation of the directive initialization/1
 * @author Chan Chung Kwong <1m02math@126.com>
 */
public class Initialization implements Directive{
	public static final Initialization INSTANCE=new Initialization();
	public static final Predicate pred=new Predicate("initialization",1);
	@Override
	public Predicate getPredicate(){
		return pred;
	}
	@Override
	public void process(List<Term> arguments,Database db){
		Term goal=arguments.get(0);
		if(Helper.isCallable(goal))
			db.addInitialization((Predication)goal);
		else
			throw new TypeException("callable",goal);
	}
}
