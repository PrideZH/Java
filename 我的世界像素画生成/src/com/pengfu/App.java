package com.pengfu;

import java.awt.EventQueue;

import com.pengfu.view.AppFrm;

public class App {
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				new AppFrm().setVisible(true);
			}
		});
	}
	
}
