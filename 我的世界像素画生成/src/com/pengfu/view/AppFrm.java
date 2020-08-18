package com.pengfu.view;

import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSlider;

import com.pengfu.model.Matrix;
import com.pengfu.util.Util;

public class AppFrm extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private static final int WIDTH = 650;
	private static final int HEIGHT = 700;
	
	private BufferedImage bufferedImage;
	
	private Matrix matrix = new Matrix();

	public AppFrm() {
		setTitle("MC像素画生成");
		setSize(WIDTH, HEIGHT);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		initConponents();
	}
	
	private void initConponents() {
		Container contentPane = getContentPane();
		contentPane.setLayout(new FlowLayout());

		// 图片显示
		ImagePane imagePane = new ImagePane();
		contentPane.add(imagePane);
		
		JButton importBtn = new JButton("导入图片");
		contentPane.add(importBtn);
		
		JSlider jSlider = new JSlider(10, 200, 100);
		contentPane.add(jSlider);
		
		JLabel jLabel = new JLabel("100");
		contentPane.add(jLabel);
		
		JButton startBtn = new JButton("生成图片");
		contentPane.add(startBtn);
		
		JButton exportBtn = new JButton("导出文件");
		contentPane.add(exportBtn);
		
		// 监听器
		// 导入图片
		importBtn.addActionListener((e) -> {
			JFileChooser jFileChooser = new JFileChooser();
			jFileChooser.showOpenDialog(this);
			File file = jFileChooser.getSelectedFile();
			if(file == null) {
				return;
			}
			try {
				bufferedImage = ImageIO.read(file);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
			imagePane.showImage(bufferedImage);
		});
		// 滑动条
		jSlider.addChangeListener((e) -> {
			jLabel.setText(Integer.toString(jSlider.getValue()));
		});
		// 生成像素画文件
		startBtn.addActionListener((e) -> {
			if(bufferedImage == null) {
				JOptionPane.showMessageDialog(this, "未导入图片！");
				return;
			}
			// 根据滑动条数值缩放图片
			int w = bufferedImage.getWidth();
			int h = bufferedImage.getHeight();
			if(w > h) {
				h = jSlider.getValue() * h / w;
				w = jSlider.getValue();
			}else {
				w = jSlider.getValue() * w / h;
				h = jSlider.getValue();
			}
			Image image = bufferedImage.getScaledInstance(w, h, Image.SCALE_FAST);
			BufferedImage buf = Util.toBufferedImage(image);
			imagePane.showImage(buf);

			// 获得图片RBG信息
            matrix.setMatrix(buf);
		});
		exportBtn.addActionListener((e) -> {
			if(matrix.getMatrix() == null) {
				JOptionPane.showMessageDialog(this, "未生成图片！");
				return;
			}
			
			// 导出mcfunction文件
			JFileChooser jFileChooser = new JFileChooser();
			jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			jFileChooser.showOpenDialog(null);
			File file = jFileChooser.getSelectedFile();
			if(file != null) {
				matrix.exportFile(file);
			}	
		});
	}

}
