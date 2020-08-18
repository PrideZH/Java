package com.pengfu.util;

import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.Transparency;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.swing.ImageIcon;

import com.google.gson.Gson;
import com.pengfu.model.Block;

public class Util {
	
	/** 将image对象 转成 BufferedImage */
	public static BufferedImage toBufferedImage(Image image) {
        if (image instanceof BufferedImage) {
            return (BufferedImage) image;
        }
        image = new ImageIcon(image).getImage();
        BufferedImage bimage = null;
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        try {
            int transparency = Transparency.OPAQUE;
            GraphicsDevice gs = ge.getDefaultScreenDevice();
            GraphicsConfiguration gc = gs.getDefaultConfiguration();
            bimage = gc.createCompatibleImage(
                    image.getWidth(null), image.getHeight(null), transparency);
        } catch (HeadlessException e) {
            e.printStackTrace();
        }
 
        if (bimage == null) {
            int type = BufferedImage.TYPE_INT_RGB;
            bimage = new BufferedImage(image.getWidth(null), image.getHeight(null), type);
        }
        Graphics g = bimage.createGraphics();
 
        g.drawImage(image, 0, 0, null);
        g.dispose();
 
        return bimage;
    }
	
	/**  读取json文件 */
	public static Block[] readerJSON (String fileName) {
		FileInputStream fis = null;
		InputStreamReader isr = null;
		BufferedReader br = null;
		String content = "";
		try{
			fileName = Util.class.getResource(fileName).getFile();
			fileName = java.net.URLDecoder.decode(fileName,"utf-8");
            fis = new FileInputStream(fileName);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            String str = null;
            while((str = br.readLine()) != null){
            	content = content.concat(str);
            }
            Gson gson = new Gson();
            return gson.fromJson(content, Block[].class);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
        	try {
				fis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        	try {
				isr.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        	try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
		return null;
	}

}
