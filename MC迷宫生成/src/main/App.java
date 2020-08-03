package main;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class App {
	
	private static final int W = 41;
	private static final int H = 41;
	
	public static void main(String[] args) {
		// 生成迷宫
		int[][] maze = new Maze().createMaze(W, H);
		// 写mcfunction文件
		writeFunction(maze);
		System.out.println("生成成功");
	}

	public static void writeFunction(int[][] maze) {
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter("migo.mcfunction"));
			for(int i = 0; i < W; ++i) {
				for(int j = 0; j < H; ++j) {
					// 底部
					bw.write("setblock ~" + i + " ~-1 ~" + j + " minecraft:quartz_block 0 replace");
					bw.newLine();
					if(0 ==maze[j][i]) { // 墙壁
						bw.write("setblock ~" + i + " ~ ~" + j + " minecraft:quartz_block 0 replace");
						bw.newLine();
						bw.write("setblock ~" + i + " ~1 ~" + j + " minecraft:quartz_block 0 replace");
						bw.newLine();
					}else { // 通路
						bw.write("setblock ~" + i + " ~ ~" + j + " minecraft:air 0 replace");
						bw.newLine();
						bw.write("setblock ~" + i + " ~1 ~" + j + " minecraft:air 0 replace");
						bw.newLine();
					}
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
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
