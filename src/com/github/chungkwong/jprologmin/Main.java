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
import java.awt.*;
import java.util.Iterator;
import java.util.Scanner;
import javax.script.ScriptContext;
import javax.swing.*;
/**
 * The executable program
 * @author Chan Chung Kwong <1m02math@126.com>
 */
public class Main {
	/**
	 * Entry to the Prolog processor
	 * @param args --gui for GUI
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception{
		PrologConsole console=new PrologConsole();
		if(args.length==1&&"--cli".equals(args[0])){
			Scanner in=new Scanner(System.in);
			System.out.print("> ");
			System.out.flush();
			while(in.hasNextLine()){
				String line=in.nextLine();
				while(!console.acceptLine(line)){
					System.out.print("| ");
					System.out.flush();
					line+=in.nextLine();
				}
				System.out.print(console.evaluate());
				System.out.print("> ");
				System.out.flush();
			}
		}else{
			System.out.println("Use argument '--cli' if you prefer using command line");
			JFrame f=new JFrame("Console");
			f.add(new SwingConsole(console),BorderLayout.CENTER);
			f.setExtendedState(JFrame.MAXIMIZED_BOTH);
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			f.setVisible(true);
		}
	}
}