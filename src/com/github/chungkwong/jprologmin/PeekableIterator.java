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
public class PeekableIterator<T> implements ListIterator<T>{
	public static final PeekableIterator EMPTY=new PeekableIterator(Collections.EMPTY_LIST.listIterator());
	ListIterator<T> iter;
	public PeekableIterator(ListIterator<T> iter){
		this.iter=iter;
	}
	public T peek(){
		T curr=iter.next();
		iter.previous();
		return curr;
	}
	@Override
	public boolean hasNext(){
		return iter.hasNext();
	}
	@Override
	public T next(){
		return iter.next();
	}
	@Override
	public boolean hasPrevious(){
		return iter.hasPrevious();
	}
	@Override
	public T previous(){
		return iter.previous();
	}
	@Override
	public int nextIndex(){
		return iter.nextIndex();
	}
	@Override
	public int previousIndex(){
		return iter.previousIndex();
	}
	@Override
	public void remove(){
		iter.remove();
	}
	@Override
	public void set(T e){
		iter.set(e);
	}
	@Override
	public void add(T e){
		iter.add(e);
	}
}