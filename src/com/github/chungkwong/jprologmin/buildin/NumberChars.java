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
 * Implementation of the predicate number_chars/2
 * @author Chan Chung Kwong <1m02math@126.com>
 */
public class NumberChars extends BuildinPredicate{
	public static final NumberChars INSTANCE=new NumberChars();
	public static final Predicate pred=new Predicate("number_chars",2);
	@Override
	public boolean activate(List<Term> arguments,Processor exec){
		Term number=arguments.get(0),list=arguments.get(1);
		if(Lists.isProperList(list)){
			if(!(number instanceof Variable||Helper.isNumber(number)))
				throw new TypeException("number",number);
			String strFromList=Lists.charListToString(list);
			try{
				Predication token=exec.getDatabase().getParser(strFromList).next();
				if(Helper.isNumber(token))
					return token.unities(number,exec.getCurrentSubst());
				else
					throw new RuntimeException();
			}catch(RuntimeException ex){
				throw new ParseException(ex);
			}
		}else if(list instanceof Variable){
			return Lists.asCharacterList(Helper.getNumberValue(number).toString()).unities(list,exec.getCurrentSubst());
		}else{
			throw new DomainException("character_list",list);
		}
	}
	@Override
	public Predicate getPredicate(){
		return pred;
	}
}
