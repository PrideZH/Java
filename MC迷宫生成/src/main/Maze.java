package main;

import java.util.ArrayList;
import java.util.Random;

public class Maze {

	private ArrayList<Search> searchList = new ArrayList<>(); // 存放待搜索点
	private Random random = new Random();
	
	private int[][] move = {{0, -2}, {0, 2}, {-2, 0}, {2, 0}};
	private int w, h; // 地图大小
	private int[][] maze;
	
	public int[][] createMaze(int w, int h) {
		this.w = w;
		this.h = h;
		maze = new int[w][h];
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
		maze[(search.y + search.superY) / 2][(search.x + search.superX) / 2] = 1;
		// 搜索4个方向 添加搜索点
		for(int i = 0; i < 4; ++i)
		{
			int x = search.x + move[i][0], y = search.y + move[i][1]; // 获得下一步搜索位置
			if(x < 0 || x > w - 1 || y < 0 || y > h - 1) continue; // 越界
			else if(maze[y][x] == 1) continue; // 连通迷宫
			if(!isInList(x, y)){ // 搜索点存在时不在添加
				searchList.add(new Search(x, y, search.x, search.y));
				maze[y][x] = 1;
			}
		}
	}

}
