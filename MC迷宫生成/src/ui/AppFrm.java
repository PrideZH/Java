package ui;

import java.awt.Color;
import java.awt.Container;
import java.awt.FlowLayout;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.SpinnerNumberModel;
import javax.swing.UIManager;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

import model.Maze;

public class AppFrm extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 600;
	private static final int HEIGHT = 650;
	
	private Maze maze = new Maze();

	public AppFrm() {
		setSize(WIDTH, HEIGHT);
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		try {
			UIManager.setLookAndFeel(new NimbusLookAndFeel());
		} catch (Exception e) {
			e.printStackTrace();
		}

		initConponents();
		setVisible(true);			
	}
	
	private void initConponents() {
		Container contentPane = getContentPane();
		contentPane.setBackground(Color.WHITE);
		contentPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 10));
		
		// 迷宫显示面板
		MazePane mazePane = new MazePane();
		contentPane.add(mazePane);
		
		// 迷宫大小控制
		JSpinner widthSpinner = new JSpinner(new SpinnerNumberModel(21, 11, 141, 2));
		((DefaultEditor) widthSpinner.getEditor()).getTextField().setEditable(false);
		contentPane.add(widthSpinner);
		JSpinner heightSpinner = new JSpinner(new SpinnerNumberModel(21, 11, 141, 2));
		((DefaultEditor) heightSpinner.getEditor()).getTextField().setEditable(false);
		contentPane.add(heightSpinner);
		
		// 按钮
		JButton createMazeBtn = new JButton("生成迷宫");
		contentPane.add(createMazeBtn);
		createMazeBtn.addActionListener((e)->{
			// 生成迷宫
			maze.createMaze((int)widthSpinner.getValue(), (int)heightSpinner.getValue());
			mazePane.drawMaze(maze.getMaze());
		});
		
		JButton exportFileBtn = new JButton("导出文件");
		contentPane.add(exportFileBtn);
		exportFileBtn.addActionListener((e)->{
			exportFileAct();
		});
	}
	
	private void exportFileAct() {
		if(maze.getMaze() != null) {
			// 导出mcfunction文件
			JFileChooser jFileChooser = new JFileChooser();
			jFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			jFileChooser.showOpenDialog(null);
			File file = jFileChooser.getSelectedFile();
			if(file != null) {
				maze.exportFile(file);
			}
		}else {
			JOptionPane.showMessageDialog(this, "未创建迷宫！");
		}
	}
	
}
