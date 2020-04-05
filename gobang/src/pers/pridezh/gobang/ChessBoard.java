package pers.pridezh.gobang;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Stack;

import javax.swing.JPanel;

public class ChessBoard extends JPanel{

	private static final long serialVersionUID = 1L;
	public static final int X = 25; //棋盘位置X
	public static final int Y = 25;  //棋盘位置Y
	public static final int SPAN = 50; //格子大小
	public static final int ROW = 15; //行数
	public static final int COL = 15; //列数
	public static final int CHESS_SIZE = 32; //棋子大小
	
	Stack<Point> chesses = new Stack<>(); //棋子
	Point[][] chessesPos = new Point[ROW][COL]; //存放棋子位置
	boolean isBlack = true; 
	Point cursor = new Point(); //光标位置
	boolean gameOver = false; //游戏是否结束

	public ChessBoard(GobangFrame frame) {
		
		setBackground(new Color(199, 137, 57));
		
		MouseAdapter md = new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				if(!gameOver) {
					for(Point chess : chesses) { //判断该位置是否有棋
						if((cursor.x >= COL || cursor.y >= ROW) || (cursor.x == chess.x && cursor.y == chess.y)) {
							return;
						}
					}
					Point point = new Point(cursor.x, cursor.y, isBlack);
					chesses.add(point);
					chessesPos[cursor.y][cursor.x] = point;
					if(result(cursor.x, cursor.y)) {
						gameOver = true;
					}
					isBlack = !isBlack;
					repaint();
				}	
			}

			@Override
			public void mouseMoved(MouseEvent e) {
				if(!gameOver) {
					cursor.x = (e.getX() - X + SPAN / 2) / SPAN;
					cursor.y = (e.getY() - Y - 32 + SPAN / 2) / SPAN; //顶部菜单高32
					repaint();
				}	
			}
		};
		frame.addMouseListener(md);
		frame.addMouseMotionListener(md); 
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		//绘制棋盘
		//画线
		g.setColor(Color.black);
		for(int i = 0; i < ROW; i++) {
			g.drawLine(X, Y + i * SPAN, X + (ROW - 1) * SPAN, Y + i * SPAN);
			g.drawLine(X + i * SPAN, Y, X + i * SPAN, Y + (COL - 1) * SPAN);
		}
		//画外框
		g.drawRect(X - 4, Y - 4, (COL - 1) * SPAN + 8, (ROW - 1) * SPAN + 8);
		//画点
		g.fillOval(X + 3 * SPAN - 4, Y + 3 * SPAN - 4, 8, 8);
		g.fillOval(X + 11 * SPAN - 4, Y + 3 * SPAN - 4, 8, 8);
		g.fillOval(X + 7 * SPAN - 4, Y + 7 * SPAN - 4, 8, 8);
		g.fillOval(X + 3 * SPAN - 4, Y + 11 * SPAN - 4, 8, 8);
		g.fillOval(X + 11 * SPAN - 4, Y + 11 * SPAN - 4, 8, 8);
		//绘制光标
		g.drawOval(X + cursor.x * SPAN - 10 / 2, Y + cursor.y * SPAN - 10 / 2, 10, 10);
		//绘制棋子
		for(Point chess : chesses) {
			g.setColor(chess.isBlack ? Color.black : Color.white);
			g.fillOval(X + chess.x * SPAN - CHESS_SIZE / 2, Y + chess.y * SPAN - CHESS_SIZE / 2, CHESS_SIZE, CHESS_SIZE);
		}
		//绘制刚下棋子的光标
		if(!chesses.isEmpty()) {
			Point chess = chesses.peek();
			g.setColor(chess.isBlack ? Color.white : Color.black);
			g.drawOval(X + chess.x * SPAN - 10 / 2, Y + chess.y * SPAN - 10 / 2, 10, 10);
		}	
	}
	
	/**
	 * 悔棋
	 */
	public void goback() {
		if(!chesses.isEmpty()) {
			Point chess = chesses.pop();
			chessesPos[chess.y][chess.x] = null;
		}	
		gameOver = false;
		isBlack = !isBlack;
		repaint();
	}
	
	/**
	 * 重新开始
	 */
	public void restartGame() {
		chessesPos = new Point[ROW][COL];
		chesses.clear();
		gameOver = false;
		isBlack = true;
		repaint();
	}
	
	private boolean result(int x, int y) {
		int i, j, sum = 0;
		//行
		for(j = x - 4 < 0 ? 0 : x - 4 ; j < (x + 5 >= COL ? COL : x + 5); j++) {
			if(chessesPos[y][j] == null || isBlack != (chessesPos[y][j].isBlack)) {
				sum = 0;
			}else {
				sum++;
			}
			if(sum == 5) {
				return true;
			}
		}
		//列
		sum = 0;
		for(i = y - 4 < 0 ? 0 : y - 4 ; i < (y + 5 >= COL ? COL : y + 5); i++) {
			if(chessesPos[i][x] == null || isBlack != (chessesPos[i][x].isBlack)) {
				sum = 0;
			}else {
				sum++;
			}
			if(sum == 5) {
				return true;
			}
		}
		//撇
		sum = 0;
		int ip = y < COL - 4 ? COL - 4 : COL - y - 1;
		int jp = x > 4 ? 4 : x;
		int num = ip < jp ? ip : jp;
		for(i = y + num, j = x - num; i >= (y - 4 < 0 ? 0 : y - 4) && j < (x + 5 >= COL ? COL : x + 5); i--, j++) {
			if(chessesPos[i][j] == null || isBlack != (chessesPos[i][j].isBlack)) {
				sum = 0;		
			}else {
				sum++;	
			}
			if(sum == 5) {
				return true;
			}
		}
		//捺
		sum = 0;
		ip = y > 4 ? 4 : y;
		jp = x > 4 ? 4 : x;
		num = ip < jp ? ip : jp;
		for(i = y - num, j = x - num; 
		i < (y + 5 >= COL ? COL : y + 5) && j < (x + 5 >= COL ? COL : x + 5);
		i++, j++) {
			if(chessesPos[i][j] == null || isBlack != (chessesPos[i][j].isBlack)) {
				sum = 0;
			}else {
				sum++;
			}
			if(sum == 5) {
				return true;
			}
		}
		return false;
	}
}
