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
import java.util.*;
import java.util.stream.*;
import javax.script.*;
/**
 * Provides implementation for javax.script API
 * @author Chan Chung Kwong <1m02math@126.com>
 */
public class PrologEngineFactory implements ScriptEngineFactory{
	/**
	 * The factory
	 */
	public static final PrologEngineFactory INSTANCE=new PrologEngineFactory();
	private static final String LANGUAGE="Prolog";
	private static final String ENGINE="JPrologMin";
	private static final String LANGUAGE_VERSION="N110";
	private static final String ENGINE_VERSION="0.0.1";
	private static final List<String> NAME=Arrays.asList(LANGUAGE,ENGINE);
	private static final List<String> EXTENSION=Arrays.asList("prolog");
	private static final List<String> MIME=Arrays.asList("text/x-prolog");
	private PrologEngineFactory(){
	}
	@Override
	public String getEngineName(){
		return ENGINE;
	}
	@Override
	public String getEngineVersion(){
		return ENGINE_VERSION;
	}
	@Override
	public List<String> getExtensions(){
		return EXTENSION;
	}
	@Override
	public List<String> getMimeTypes(){
		return MIME;
	}
	@Override
	public List<String> getNames(){
		return NAME;
	}
	@Override
	public String getLanguageName(){
		return LANGUAGE;
	}
	@Override
	public String getLanguageVersion(){
		return LANGUAGE_VERSION;
	}
	@Override
	public Object getParameter(String key){
		switch(key){
			case "ScriptEngine.ENGINE":return getEngineName();
			case "ScriptEngine.ENGINE_VERSION":return getEngineVersion();
			case "ScriptEngine.NAME":return getNames();
			case "ScriptEngine.LANGUAGE":return getLanguageName();
			case "ScriptEngine.LANGUAGE_VERSION":return getLanguageVersion();
			default:return null;
		}
	}
	@Override
	public String getMethodCallSyntax(String obj,String m,String... args){
		return Arrays.stream(args).collect(Collectors.joining(",",m+"(",")"));
	}
	@Override
	public String getOutputStatement(String toDisplay){
		return "?-atom_codes(DISPLAY,\""+toDisplay+"\").";
	}
	@Override
	public String getProgram(String... statements){
		return Arrays.stream(statements).collect(Collectors.joining());
	}
	@Override
	public PrologEngine getScriptEngine(){
		return new PrologEngine();
	}
}