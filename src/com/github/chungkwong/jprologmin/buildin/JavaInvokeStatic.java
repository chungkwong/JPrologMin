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
 * java_invoke(return_value,class,method,list_of_argments);
 * @author Chan Chung Kwong <1m02math@126.com>
 */
public class JavaInvokeStatic extends JavaInvoke{
	public static final JavaInvokeStatic INSTANCE=new JavaInvokeStatic();
	public static final Predicate PREDICATE=new Predicate("java_invoke_static",4);
	@Override
	public boolean activate(List<Term> arguments,Processor exec){
		Class cls;
		try{
			cls=Class.forName(Helper.getConstantValue(arguments.get(1)).toString());
		}catch(ClassNotFoundException ex){
			throw new JavaException(ex,exec.getCurrentActivator());
		}
		String methodName=Helper.getConstantValue(arguments.get(2)).toString();
		return invoke(arguments.get(0),cls,null,methodName,arguments.get(3),exec);
	}
	@Override
	public Predicate getPredicate(){
		return PREDICATE;
	}
}
