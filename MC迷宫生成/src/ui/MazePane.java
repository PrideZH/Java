package ui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class MazePane extends JPanel {
	
	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 550;
	private static final int HEIGHT = 550;
	
	private int[][] maze; 
	private double size;
	private int x, y;
	
	public MazePane() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
	}
	
	public void drawMaze(int[][] maze) {
		this.maze = maze;
		// 舍长显示的迷宫大小位置
		int h, w;
		if(maze.length > maze[0].length) {
			h = HEIGHT;
			this.size = (double) HEIGHT / maze.length;
			w = (int) (maze[0].length * this.size);
		}else {
			w = WIDTH;
			this.size = (double) WIDTH / maze[0].length;
			h = (int) (maze.length * this.size);
		}
		this.x = (WIDTH - w) / 2;
		this.y = (HEIGHT - h) / 2;
		repaint();
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(new Color(240, 240, 240));
		g.fillRect(0, 0, WIDTH, HEIGHT);

		if(maze != null) {
			// 绘制迷宫墙
			g.setColor(Color.BLACK);
			for(int i = 0; i < maze.length; i++) {
				for(int j = 0; j < maze[0].length; j++) {
					if(0 == maze[i][j]) {
						g.fillRect((int) (j * this.size + this.x), 
								(int) (i * this.size + this.y),
								(int) this.size,
								(int) this.size);
					}
				}
			}
		}
	}
}
