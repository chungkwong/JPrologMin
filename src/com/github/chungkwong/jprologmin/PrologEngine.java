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
import java.io.*;
import java.util.*;
import javax.script.*;
/**
 *
 * @author Chan Chung Kwong <1m02math@126.com>
 */
public class PrologEngine extends AbstractScriptEngine{
	private final Database db=new Database();
	@Override
	public Iterator<Substitution> eval(String script,ScriptContext context) throws ScriptException{
		return eval(new StringReader(script),context);
	}
	@Override
	public Iterator<Substitution> eval(Reader reader,ScriptContext context) throws ScriptException{
		PrologParser parser=new PrologParser(new PrologLex(reader));
		Predication pred;
		SubstitutionIterator iter=null;
		while((pred=parser.next())!=null){
			if(pred.getPredicate().getFunctor().equals("?-")){
				iter=new SubstitutionIterator(new Processor((Predication)pred.getArguments().get(0),db));
			}else if(pred.getPredicate().getFunctor().equals(":-")){
				db.addClauseToLast(new Clause((Predication)pred.getArguments().get(0),
						(Predication)pred.getArguments().get(1)));
				iter=null;
			}else{
				db.addClauseToLast(new Clause(pred,new Constant("true")));
				iter=null;
			}
		}
		return iter;
	}
	@Override
	public Bindings createBindings(){
		return new javax.script.SimpleBindings();
	}
	@Override
	public ScriptEngineFactory getFactory(){
		return PrologEngineFactory.INSTANCE;
	}
	private static class SubstitutionIterator implements Iterator<Substitution>{
		private final Processor exec;
		private Substitution cache;
		public SubstitutionIterator(Processor exec){
			this.exec=exec;
			this.cache=exec.getSubstitution();
		}
		@Override
		public boolean hasNext(){
			return cache!=null;
		}
		@Override
		public Substitution next(){
			Substitution ret=cache;
			exec.reexecute();
			cache=exec.getSubstitution();
			return ret;
		}
	}
	public static void main(String[] args) throws ScriptException{
		PrologEngine prologEngine=new PrologEngine();
		Scanner in=new Scanner(System.in);
		while(in.hasNextLine()){
			Iterator<Substitution> eval=prologEngine.eval(in.nextLine(),(ScriptContext)null);
			if(eval!=null)
				while(eval.hasNext())
					System.out.println(eval.next().toStringUser());
		}
	}
}