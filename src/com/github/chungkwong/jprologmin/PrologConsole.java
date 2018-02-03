/*
 * Copyright (C) 2016,2018 Chan Chung Kwong <1m02math@126.com>
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
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.*;
/**
 * A simple console to Prolog processor
 * @author Chan Chung Kwong <1m02math@126.com>
 */
public class PrologConsole implements Shell{
	private final Database db=new Database();
	private List<Predication> predications;
	private Processor exec;
	private String line;
	private static final String PROMPT="';' for more, 'a' for all, <RET> for exit:\n";
	@Override
	public boolean acceptLine(String line){
		if(exec!=null){
			this.line=line.trim();
			return true;
		}
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
		if(exec!=null){
			if("".equals(line)){
			}else if("a".equals(line)){
				while(reexecute(buf)!=null){
					
				}
			}else if(";".equals(line)){
				if(reexecute(buf)!=null)
					return buf.append(PROMPT).toString();
			}else{
				return PROMPT;
			}
			line=null;
			exec=null;
		}
		if(!predications.isEmpty()){
			Predication pred=predications.remove(0);
			if(pred.getPredicate().getFunctor().equals("?-")){
				try{
					exec=new Processor((Predication)pred.getArguments().get(0),db);
					Substitution subst=exec.getSubstitution();
					if(subst==null){
						buf.append("Gaol failed\n");
						exec=null;
					}else{
						buf.append(subst.toStringUser()).append('\n').append(PROMPT);
					}
				}catch(Exception ex){
					buf.append(ex).append('\n');
					Logger.getGlobal().log(Level.SEVERE,"",ex);
				}
			}else{
				db.addPredication(pred);
			}
		}
		return buf.toString();
	}
	private Substitution reexecute(StringBuilder buf){
		try{
			exec.reexecute();
			Substitution subst=exec.getSubstitution();
			if(subst==null){
				buf.append("No more\n");
			}else{
				buf.append(subst.toStringUser()).append('\n');
			}
			return subst;
		}catch(Exception ex){
			buf.append(ex).append('\n');
			Logger.getGlobal().log(Level.SEVERE,"",ex);
			return null;
		}
	}
	@Override
	public List<String> getHints(String prefix){
		return db.getProcedures().keySet().stream().map((proc)->proc.getFunctor().toString())
			.filter((proc)->proc.startsWith(prefix)).collect(Collectors.toList());
	}
}