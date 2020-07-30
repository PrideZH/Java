package com.pengfu.getcolor.view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScreenPane extends JPanel {

	private static final long serialVersionUID = 1L;

	// 显示器大小
	public static final int WIDTH = 200;
	public static final int HEIGHT = 200;

	JLabel jLabel;
	Image img;

	public ScreenPane() {
		setSize(WIDTH, HEIGHT);

		initConponents();
	}

	/** 初始化组件 */
	private void initConponents() {

	}

	public void setImage(Image image) {
		Image img = image.getScaledInstance(WIDTH, HEIGHT, Image.SCALE_DEFAULT);
		this.img = img;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		// 绘制屏幕图像
		g.drawImage(img, 0, 0, WIDTH, HEIGHT, null);
		g.setColor(Color.WHITE);
		g.drawRect(WIDTH / 2, HEIGHT / 2, 10, 10);
	}

}
