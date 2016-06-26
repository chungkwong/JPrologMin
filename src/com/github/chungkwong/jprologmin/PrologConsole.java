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
import com.github.chungkwong.swingconsole.*;
import java.util.*;
import java.util.stream.*;
/**
 * A simple console to Prolog processor
 * @author Chan Chung Kwong <1m02math@126.com>
 */
public class PrologConsole implements Shell{
	private final Database db=new Database();
	private List<Predication> predications;
	@Override
	public boolean acceptLine(String line){
		PrologParser parser=db.getParser(line);
		try{
			predications=parser.getRemaining();
		}catch(Exception ex){
			return false;
		}
		return true;
	}
	@Override
	public String evaluate(){
		StringBuilder buf=new StringBuilder();
		for(Predication pred:predications){
			if(pred.getPredicate().getFunctor().equals("?-")){
				try{
					Processor exec=new Processor((Predication)pred.getArguments().get(0),db);
					Substitution subst=exec.getSubstitution();
					if(subst==null)
						buf.append("Gaol failed\n");
					else{
						while(subst!=null){
							buf.append(subst.toStringUser()).append('\n');
							exec.reexecute();
							subst=exec.getSubstitution();
						}
					}
				}catch(Exception ex){
					buf.append(ex).append('\n');
					ex.printStackTrace();
				}
			}else if(pred.getPredicate().getFunctor().equals(":-")){
				db.addClauseToLast(new Clause((Predication)pred.getArguments().get(0),
						(Predication)pred.getArguments().get(1)));
				buf.append("Rule added\n");
			}else{
				db.addClauseToLast(new Clause(pred,new Constant("true")));
				buf.append("Fact added\n");
			}
		}
		return buf.toString();
	}
	@Override
	public List<String> getHints(String prefix){
		return db.getProcedures().keySet().stream().map((proc)->proc.getFunctor().toString())
			.filter((proc)->proc.startsWith(prefix)).collect(Collectors.toList());
	}
}