package com.pengfu.getcolor.controller;

import java.awt.EventQueue;

import com.pengfu.getcolor.view.AppFrm;

public class App {

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				new AppFrm();
			}
		});
	}
	
}
