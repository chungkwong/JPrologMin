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
import com.github.chungkwong.jprologmin.buildin.*;
import java.math.*;
import java.util.*;
import java.util.stream.*;
/**
 * Some useful methods to manipulate prolog list
 * @author Chan Chung Kwong <1m02math@126.com>
 */
public class Lists{
	/**Empty list*/
	public static final Constant EMPTY_LIST=new Constant("[]");
	/**
	 * @param elements
	 * @return A prolog list with given elements
	 */
	public static Term asList(Term... elements){
		int index=elements.length;
		Term list=EMPTY_LIST;
		while(--index>=0){
			list=new CompoundTerm(".",elements[index],list);
		}
		return list;
	}
	/**
	 * @param elements
	 * @return A prolog list with given elements
	 */
	public static Term asList(List<Term> elements){
		ListIterator<Term> iter=elements.listIterator(elements.size());
		Term list=EMPTY_LIST;
		while(iter.hasPrevious()){
			list=new CompoundTerm(".",iter.previous(),list);
		}
		return list;
	}
	/**
	 * @param term a prolog list
	 * @return the length of the list
	 */
	public static int length(Term term){
		int length=0;
		while(term instanceof CompoundTerm){
			++length;
			term=((CompoundTerm)term).getArguments().get(1);
		}
		return length;
	}
	/**
	 * @param term
	 * @return if term is an empty prolog list
	 */
	public static boolean isEmptyList(Term term){
		return term.equals(EMPTY_LIST);
	}
	/**
	 * @param term
	 * @return if term is an nonempty prolog list
	 */
	public static boolean isNonEmptyList(Term term){
		return term instanceof CompoundTerm&&((CompoundTerm)term).getFunctor().equals(".")
				&&((CompoundTerm)term).getArguments().size()==2;
	}
	/**
	 * @param term
	 * @return if term is an prolog list
	 */
	public static boolean isList(Term term){
		return isEmptyList(term)||isNonEmptyList(term);
	}
	/**
	 * @param term
	 * @return if term is an proper prolog list
	 */
	public static boolean isProperList(Term term){
		while(isNonEmptyList(term)){
			term=((CompoundTerm)term).getArguments().get(1);
		}
		return isEmptyList(term);
	}
	/**
	 * @param list a charcater code list
	 * @return a string
	 */
	public static String codeListToString(Term list){
		List<Term> lst=Lists.toJavaList(list);
		StringBuilder buf=new StringBuilder();
		try{
			for(Term t:lst){
				int code=((BigInteger)((Constant)t).getValue()).intValue();
				if(Character.isValidCodePoint(code))
					buf.append(new String(new int[]{code},0,1));
				else
					throw new RuntimeException();
			}
		}catch(RuntimeException ex){
			throw new DomainException("character_code_list",list);
		}
		return buf.toString();
	}
	/**
	 * @param list a charcater list
	 * @return a string
	 */
	public static String charListToString(Term list){
		List<Term> lst=Lists.toJavaList(list);
		StringBuilder buf=new StringBuilder();
		try{
			for(Term t:lst){
				String ch=(String)((Constant)t).getValue();
				if(ch.codePointCount(0,ch.length())==1)
					buf.append(ch);
				else
					throw new RuntimeException();
			}
		}catch(RuntimeException ex){
			throw new DomainException("character_list",list);
		}
		return buf.toString();
	}
	/**
	 * @param str
	 * @return the collating sequence
	 */
	public static Term asCodeList(String str){
		return asList(str.codePoints().mapToObj((i)->new Constant(BigInteger.valueOf(i))).collect(Collectors.toList()));
	}
	/**
	 * @param str
	 * @return the character sequence
	 */
	public static Term asCharacterList(String str){
		return asList(str.codePoints().mapToObj((i)->new Constant(new String(new int[]{i},0,1))).collect(Collectors.toList()));
	}
	/**
	 * @param term a prolog list
	 * @return the head of the list
	 */
	public static Term head(Term term){
		return ((CompoundTerm)term).getArguments().get(0);
	}
	/**
	 * @param term a prolog list
	 * @return the tail of the list
	 */
	public static Term tail(Term term){
		return ((CompoundTerm)term).getArguments().get(1);
	}
	/**
	 * @param term a prolog list
	 * @return the tail of the list
	 */
	public static List<Term> toJavaTail(Term term){
		return toJavaList(((CompoundTerm)term).getArguments().get(1));
	}
	/**
	 * @param term a prolog list
	 * @return a list containing the same elements of term with order reversed
	 */
	public static Term reverse(Term term){
		Term list=EMPTY_LIST;
		while(isNonEmptyList(term)){
			list=new CompoundTerm(".",((CompoundTerm)term).getArguments().get(0),list);
			term=((CompoundTerm)term).getArguments().get(1);
		}
		if(isEmptyList(term))
			return list;
		else
			throw new RuntimeException("Not a list");
	}
	/**
	 * Sort a list
	 * @param t to be sorted and modified
	 */
	public static void sort(Term t){
		boolean changed=true;
		while(changed){
			changed=false;
			Term iter=t;
			while(iter instanceof CompoundTerm&&((CompoundTerm)iter).getArguments().get(1) instanceof CompoundTerm){
				Term prec=((CompoundTerm)iter).getArguments().get(0);
				Term succ=((CompoundTerm)((CompoundTerm)iter).getArguments().get(1)).getArguments().get(0);
				if(TermComparator.INSTANCE.compare(prec,succ)>0){
					((CompoundTerm)((CompoundTerm)iter).getArguments().get(1)).getArguments().set(0,prec);
					((CompoundTerm)iter).getArguments().set(0,succ);
					changed=true;
				}
				iter=((CompoundTerm)iter).getArguments().get(1);
			}
		}
	}
	/**
	 * Remove equal elements near by
	 * @param t a sorted list
	 */
	public static void uniq(Term t){
		Term iter=t;
		while(iter instanceof CompoundTerm&&((CompoundTerm)iter).getArguments().get(1) instanceof CompoundTerm){
			CompoundTerm next=(CompoundTerm)((CompoundTerm)iter).getArguments().get(1);
			Term prec=((CompoundTerm)iter).getArguments().get(0);
			Term succ=next.getArguments().get(0);
			if(prec.equals(succ))
				((CompoundTerm)iter).getArguments().set(1,next.getArguments().get(1));
			else
				iter=((CompoundTerm)iter).getArguments().get(1);
		}
	}
	/**
	 * @param term A prolog list
	 * @return The corresponsing java List
	 */
	public static List<Term> toJavaList(Term term){
		List<Term> list=new LinkedList();
		while(term instanceof CompoundTerm){
			list.add(((CompoundTerm)term).getArguments().get(0));
			term=((CompoundTerm)term).getArguments().get(1);
		}
		return list;
	}
	/**
	 * @param prologArgs a prolog list
	 * @return The corresponsing java List with all constants replaced with its value
	 */
	public static List<Object> extractJavaArguments(Term prologArgs){
		if(isList(prologArgs)){
			return toJavaList(prologArgs).stream().filter((arg)->arg instanceof Constant)
					.map((arg)->((Constant)arg).getValue()).collect(Collectors.toList());
		}else{
			throw new TypeException("list",prologArgs);
		}
	}
}