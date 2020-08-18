package com.pengfu.model;

import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

import com.pengfu.util.Util;

public class Matrix {

	private int[][][] matrix;
	
	public int[][][] getMatrix() {
		return matrix;
	}

	/** 获得图片RBG信息 */
	public void setMatrix(BufferedImage image) {
		int inageW = image.getWidth();
		int inageH = image.getHeight();
		int[][][] matrix = new int[inageH][inageW][3];
		
		// 左上角为原点进行行扫描
        for(int i = 0; i < inageH; i++){
            for(int j = 0; j < inageW; j++){
                int pixel = image.getRGB(j,i);
                //分离RGB
                matrix[i][j][0] = 0xff & (pixel >> 16);
                matrix[i][j][1] = 0xff & (pixel >> 8);
                matrix[i][j][2] = 0xff & pixel ;
            }
        }
		this.matrix = matrix;
	}

	/** 导出文件 */
	public void exportFile(File file) {
		Block[] blocks = Util.readerJSON("/resources/Block.json");
		BufferedWriter bw = null;
		try {
			bw = new BufferedWriter(new FileWriter(file.getAbsolutePath() + "/painting.mcfunction"));
			for(int i = 0; i < matrix.length; ++i) {
				for(int j = 0; j < matrix[0].length; ++j) {
					int index = compareColor(blocks, matrix[i][j]);
					//System.out.println(blocks[index].R + "-" + blocks[index].G + "-" + blocks[index].B);
				//	System.out.println(matrix[i][j][0] + "-" + matrix[i][j][1] + "-" + matrix[i][j][2]);
					bw.write("setblock ~" + j + " ~" + (matrix.length - i - 1) + " ~ " + blocks[index].blockName);
					bw.newLine();
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
	
	/** 颜色比较 获得方块下标 */
    private int compareColor(Block[] blocks, int[] RGB){
        int index = 0;
        double min = 999;
        for(int i = 0; i < blocks.length; ++i){
        	double temp = isSimilarColorRBG(RGB[0], RGB[1], RGB[2], blocks[i].R, blocks[i].G, blocks[i].B);
            if(temp < min) {
                min = temp;
                index = i;
            }
        }
        return index;
    }
	
	/** 判断颜色相似度 */
	private double isSimilarColorRBG(int r1, int g1, int b1, int r2, int g2, int b2) {
		double dr = (r1 - r2) ;
		double dg = (g1 - g2);
		double db = (b1 - b2) ;
		return Math.sqrt(dr * dr + dg * dg + db * db);
	}
	
}
