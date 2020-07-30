package com.pengfu.getcolor.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public class AppFrm extends JFrame {

	private static final long serialVersionUID = 1L;
	
	// 应用窗口大小
	private static final int WIDTH = 600;
	private static final int HEIGHT = 300;

	// 组件
	private Container contentPane;
	private JPanel colorPane; // 颜色显示面板
	private JTextField RGB16Txt;
	private JTextField RGB10Txt;
	private JTextField RGBHEXTxt;
	
	private Font font = new Font("宋体", Font.BOLD, 16);
	
	private Clipboard clipbd;
	
	public AppFrm() {
		// 设置窗口属性
		setTitle("Get Color");
		setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		clipbd = getToolkit().getSystemClipboard();
		
		try {
			UIManager.setLookAndFeel(new NimbusLookAndFeel());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		// 初始化组件
		initConponents();
		
		setVisible(true);
	}
	
	/** 初始化组件 */
	private void initConponents() {
		contentPane = getContentPane();
		contentPane.setBackground(Color.WHITE);
		contentPane.setLayout(new BorderLayout());
		
		// 颜色显示面板
		colorPane = new JPanel();
		colorPane.setPreferredSize(new Dimension(200, 0));
		colorPane.setBackground(Color.WHITE);
		// - 设置边框
		colorPane.setBorder(
				BorderFactory.createCompoundBorder(
						BorderFactory.createLineBorder(Color.WHITE, 30), // 外边框
						BorderFactory.createLineBorder(Color.PINK, 3))); // 内边框
		contentPane.add(colorPane, BorderLayout.WEST);
		
		// 数据面板
		JPanel dataPane = new JPanel(); 
		dataPane.setPreferredSize(new Dimension(400, 0));
		dataPane.setBackground(Color.WHITE);
		contentPane.add(dataPane, BorderLayout.EAST);
		dataPane.setLayout(null);
		// - 获取颜色按钮
		JButton eyedropperBtn = new JButton("获取颜色");
		eyedropperBtn.setBounds(30, 30, 128, 32);
		dataPane.add(eyedropperBtn);
		eyedropperBtn.addActionListener((e)->{
			new ScreenWindow(this);
		});
		
		// - 位置坐标
		// - RGB 十六进制
		// - - 标签
		dataPane.add(createRGBLbl(50, 100, "RGB 十六进制:"));
		// - - 显示框
		RGB16Txt = createRGBTxt(180, 95);
		dataPane.add(RGB16Txt);
		// - - 复制按钮
		dataPane.add(createCopyBtn(340, 100, RGB16Txt));
		// - RGB 十进制
		// - - 标签
		dataPane.add(createRGBLbl(50, 150, "RGB 十进制:"));
		// - - 显示框
		RGB10Txt = createRGBTxt(180, 145);
		dataPane.add(RGB10Txt);
		// - - 复制按钮
		dataPane.add(createCopyBtn(340, 150, RGB10Txt));
		// - RGB HEX
		// - - 标签
		dataPane.add(createRGBLbl(50, 200, "RGB HEX:"));
		// - - 显示框
		RGBHEXTxt = createRGBTxt(180, 195);
		dataPane.add(RGBHEXTxt);
		// - - 复制按钮
		dataPane.add(createCopyBtn(340, 200, RGBHEXTxt));
	}
	
	/** 标签 */
	private JLabel createRGBLbl(int x, int y, String text) {
		JLabel lbl = new JLabel(text);
		lbl.setBounds(x, y, 128, 22);
		lbl.setFont(font);
		return lbl;
	}
	
	/** 显示框 */
	private JTextField createRGBTxt(int x, int y) {
		JTextField txt = new JTextField();
		txt.setBounds(x, y, 140, 32);
		txt.setEditable(false); 
		txt.setFont(font);
		return txt;
	}
	
	/** 复制按钮 */
	private JButton createCopyBtn(int x, int y, JTextField txt) {
		JButton btn = new JButton();
		btn.setBounds(x, y, 22, 22);
		btn.addActionListener((e)->{
			StringSelection clipString = new StringSelection(txt.getText());
			clipbd.setContents(clipString, clipString);
		});
		return btn;
	}
	
	/** 更新显示框数据 */
	public void update(Color pixelColor) {
		int R = pixelColor.getRed();
		int G = pixelColor.getGreen();
		int B = pixelColor.getBlue();
		String HEX1 = Integer.toHexString(R);
		String HEX2 = Integer.toHexString(G);
		String HEX3 = Integer.toHexString(B);
		RGB16Txt.setText(HEX1 + ", " + HEX2 + ", " + HEX3);
		RGB10Txt.setText(R + ", " + G + ", " + B);
		RGBHEXTxt.setText("#" + HEX1 + HEX2 + HEX3);
		colorPane.setBackground(pixelColor);
	}
	
}
