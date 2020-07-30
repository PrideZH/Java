package com.pengfu.getcolor.view;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Container;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class ScreenWindow extends JFrame {

	private static final long serialVersionUID = 1L;

	private AppFrm appFrm;
	
	private Image image;
	private JLabel imageLabel;
	private ScreenPane screenPane;
	
	private Dimension screenDims; // 屏幕大小
	private Robot robot;

	public ScreenWindow(AppFrm appFrm) {
		this.appFrm = appFrm;
		
		setUndecorated(true); // 去掉窗口装饰
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setExtendedState(JFrame.MAXIMIZED_BOTH); // 窗口最大化
		
		try {
			robot = new Robot();
		} catch (AWTException e) {
			e.printStackTrace();
		}

		initConponents(); // 初始化组件
		
		// 添加监听器
		addMouseMotionListener(new MouseAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				mouseMovedAct(e);
				try {
					Thread.sleep(50);
				} catch (InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		});
		addMouseListener(new MouseAdapter() {
			@Override
			public void mousePressed(MouseEvent e) {
				mousePressedAct(e);
			}
		});
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				KeyPressedAct(e);
			}
		});

		this.setVisible(true);
	}

	/** 初始化组件 */
	private void initConponents() {
		Container contentPane = getContentPane();
		
		// 展示全屏截图
		// - 取得屏幕尺寸
		screenDims = Toolkit.getDefaultToolkit().getScreenSize();
		// - 取得全屏幕截图
		image = robot.createScreenCapture(
				new Rectangle(0, 0, screenDims.width, screenDims.height));
		imageLabel = new JLabel(new ImageIcon(image));
		// - 显示鼠标为十字形
		imageLabel.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
		contentPane.add(imageLabel);
	
		// 小屏幕
		screenPane = new ScreenPane();
		imageLabel.add(screenPane);
	}
	
	/** 鼠标移动事件 */
	private void mouseMovedAct(MouseEvent e) {
		int x = e.getX();
		int y = e.getY();
		
		// 设置小屏幕位置
		// - 初始位置鼠标右下角
		int dist = 16; // 小屏幕里鼠标距离
		Point screenLoc = new Point(x + dist, y + dist);
		// - 调整x
		if(x > screenDims.width - ScreenPane.WIDTH - dist) {
			screenLoc.x = x - ScreenPane.WIDTH - dist;
		}
		// - 调整y
		if(y > screenDims.height - ScreenPane.HEIGHT - dist) {
			screenLoc.y = y - ScreenPane.HEIGHT - dist;
		}
		// - 设置小屏幕位置
		screenPane.setLocation(screenLoc);
		
		// 获得屏幕显示图像
		image = robot.createScreenCapture(
				new Rectangle(x - 10, y - 10, 20, 20));
		screenPane.setImage(image);
	}
	
	/** 鼠标按下事件 */
	private void mousePressedAct(MouseEvent e) {
		Color pixelColor = robot.getPixelColor(e.getX(), e.getY());
		appFrm.update(pixelColor);
		dispose();
	}
	
	/** 键盘按下事件 */
	private void KeyPressedAct(KeyEvent e) {
		Point location = MouseInfo.getPointerInfo().getLocation();
		switch(e.getKeyCode()) {
		case KeyEvent.VK_UP:
		case KeyEvent.VK_W:
			robot.mouseMove(location.x, location.y - 1);
			break;
		case KeyEvent.VK_DOWN:
		case KeyEvent.VK_S:
			robot.mouseMove(location.x, location.y + 1);
			break;
		case KeyEvent.VK_LEFT:
		case KeyEvent.VK_A:
			robot.mouseMove(location.x - 1, location.y);
			break;
		case KeyEvent.VK_RIGHT:
		case KeyEvent.VK_D:
			robot.mouseMove(location.x + 1, location.y);
			break;
		}
	}
}
