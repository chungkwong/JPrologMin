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
public class PushBackIterator<T> implements Iterator<T>{
	LinkedList<T> buffer=new LinkedList<>();
	Iterator<T> iter;
	public PushBackIterator(Iterator<T> iter){
		this.iter=iter;
	}
	public void pushBack(T item){
		buffer.addFirst(item);
	}
	public T peek(){
		if(buffer.isEmpty())
			buffer.addFirst(iter.next());
		return buffer.getFirst();
	}
	@Override
	public boolean hasNext(){
		return iter.hasNext()||!buffer.isEmpty();
	}
	@Override
	public T next(){
		return buffer.isEmpty()?iter.next():buffer.removeFirst();
	}
}