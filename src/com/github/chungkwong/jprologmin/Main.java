/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.github.chungkwong.jprologmin;
import com.github.chungkwong.swingconsole.*;
import java.awt.*;
import javax.swing.*;
/**
 *
 * @author Chan Chung Kwong <1m02math@126.com>
 */
public class Main {
	/**
	 * Entry to the GUI for our Prolog processor
	 * @param args unused
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception{
		JFrame f=new JFrame("Console");
		f.add(new SwingConsole(new PrologConsole()),BorderLayout.CENTER);
		f.setExtendedState(JFrame.MAXIMIZED_BOTH);
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		f.setVisible(true);
	}
}