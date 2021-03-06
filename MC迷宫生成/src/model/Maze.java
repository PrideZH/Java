package model;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JOptionPane;

public class Maze {

	private ArrayList<Search> searchList = new ArrayList<>(); // 存放待搜索点
	private Random random = new Random();
	
	private int[][] move = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};
	private int w, h; // 地图大小
	private int[][] maze = null;
	
	public void createMaze(int w, int h) {
		this.w = w;
		this.h = h;
		maze = new int[h][w];
		maze[h - 2][w - 1] = 1; // 终点
		maze[1][0] = 1; // 起点
		searchList.add(new Search(1, 1, 1, 1)); // 从(1,1)位置开始搜索
		while(true) {
			if(searchList.isEmpty()) {
				break;
			} else {
				// 随机获得搜索点继续搜索
				dig(searchList.remove(random.nextInt(searchList.size())));
			}
		}
	}
	
	public int[][] getMaze(){
		return maze;
	}
	
	private boolean isInList(int x, int y) {
		for(Search search : searchList) {
			if(search.x == x && search.y == y) {
				return true;
			}
		}
		return false;
	}
	
	private void dig(Search search) {
		maze[search.y][search.x] = 1;
		maze[search.digY][search.digX] = 1;
		// 搜索4个方向 添加搜索点
		for(int i = 0; i < 4; ++i)
		{
			int x = search.x + 2 * move[i][0], y = search.y + 2 * move[i][1]; // 获得下一步搜索位置
			if(x < 0 || x > w - 1 || y < 0 || y > h - 1) continue; // 越界
			else if(maze[y][x] == 1) continue; // 连通迷宫
			if(!isInList(x, y)){ // 搜索点存在时不在添加
				searchList.add(new Search(x, y, search.x + move[i][0], search.y + move[i][1]));
			}
		}
	}
	
	public void exportFile(File file) {
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(file.getAbsolutePath() + "/maze.mcfunction"));
			for(int i = 0; i < w; ++i) {
				for(int j = 0; j < h; ++j) {
					// 底部
					bw.write("setblock ~" + i + " ~-1 ~" + j + " minecraft:quartz_block");
					bw.newLine();
					if(0 ==maze[j][i]) { // 墙壁
						bw.write("setblock ~" + i + " ~ ~" + j + " minecraft:quartz_block");
						bw.newLine();
						bw.write("setblock ~" + i + " ~1 ~" + j + " minecraft:quartz_block");
						bw.newLine();
					}else { // 通路
						bw.write("setblock ~" + i + " ~ ~" + j + " minecraft:air");
						bw.newLine();
						bw.write("setblock ~" + i + " ~1 ~" + j + " minecraft:air");
						bw.newLine();
					}
				}
			}
			JOptionPane.showMessageDialog(null, "导出文件成功！");
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
		} finally {
			if(bw != null) {
				try {
					bw.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
