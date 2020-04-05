package pers.pridezh.gobang;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.HeadlessException;
import java.awt.LayoutManager;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GobangFrame extends JFrame{

	private static final long serialVersionUID = 1L;
	
	public GobangFrame() throws HeadlessException {
		setTitle("五子棋");
		setSize(750, 825);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public static void main(String[] args) {
		//棋盘
		GobangFrame frame = new GobangFrame();
		ChessBoard panel = new ChessBoard(frame);
		frame.add(panel);
		//工具类
		JPanel toolbar = new JPanel();
		JButton startButton = new JButton("重新开始"); 
		JButton backButton=new JButton("悔棋");
		JButton exitButton=new JButton("退出");
		toolbar.add(startButton); 
		toolbar.add(backButton); 
		toolbar.add(exitButton); 
		toolbar.setLayout((LayoutManager) new FlowLayout(FlowLayout.LEFT)); //按钮靠左排
		frame.add(toolbar, BorderLayout.SOUTH); //添加工具类到底部
		ActionListener lis = new ActionListener() { //按钮监视器
			@Override
			public void actionPerformed(ActionEvent e) {
				Object obj=e.getSource(); //获得事件源
				if(obj == startButton){ //重新开始
					panel.restartGame();
				  }
				  else if (obj == exitButton) { //退出
					  System.exit(0);
				  }
				  else if (obj == backButton){ //悔棋
					  panel.goback();
				  }
			}
		};
		startButton.addActionListener(lis);
		backButton.addActionListener(lis);
		exitButton.addActionListener(lis);
		
		frame.setVisible(true);
	}
}
