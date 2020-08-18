package com.pengfu.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.JPanel;

public class ImagePane extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private static final int WIDTH = 600;
	private static final int HEIGHT = 600;
	
	private Image image = null;
	private int w = 100, h = 100;

	public ImagePane() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
	}
	
	public void showImage(Image image) {
		this.image = image;
		int w = image.getWidth(null);
		int h = image.getHeight(null);
		if(w > h) {
			this.h = WIDTH * h / w;
			this.w = WIDTH;
		}else {
			this.w = HEIGHT * w / h;
			this.h = HEIGHT;
		}
		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, (WIDTH - w) / 2, (HEIGHT - h) / 2, w, h, null);
	}
}
