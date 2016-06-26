/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.chungkwong.jprologmin;
import java.util.*;
/**
 *
 * @author Chan Chung Kwong <1m02math@126.com>
 */
public class SimpleIteratorWraper<T> implements Iterator<T>{
	private final SimpleIterator<T> iter;
	private T buffer;
	private boolean ended=false;
	public SimpleIteratorWraper(SimpleIterator<T> iter){
		this.iter=iter;
	}
	@Override
	public boolean hasNext(){
		if(ended)
			return false;
		else if(buffer!=null)
			return true;
		else{
			buffer=iter.next();
			ended=(buffer==null);
			return !ended;
		}
	}
	@Override
	public T next(){
		if(hasNext()){
			T tmp=buffer;
			buffer=null;
			return tmp;
		}else
			throw new NoSuchElementException();
	}
}
