package com.UID.www;

import java.awt.Image;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
//资源加载类
public class Sources {
	public static int WIDTH = 600;
	public static  int HEIGHT = 400;
	public static  int SIZE = 20;
	public static  int SPEED = 100;
	public static Image BACK_GROUND;
	public static Image ZHUAN_KUAI;
	public static Image[] SNAKE_BODY = new Image[4];
	public static Image[] SNAKE_HEAD = new Image[4];
	public static Image[] SNAKE_LAST = new Image[4];
	public static Image img_On;
	public static Image img_Out;
	public static ImageIcon ICON_INTO_CLOSE;
	public static ImageIcon ICON_EXIT_CLOSE ;
	public static Image FOOD;
	public static Image ICON;
	public static String MapPath = "./Map_86_10/";
	public static String MapName= "Map_8610.date";
	public static String ImgPath = "./PICT/";
	public static String bigImage = "./PICT/";
	public static String smallImage = "./PICT_S/";
	public static String BackGroundName = "BACK_GROUND.png";
	public Sources() {
		try {
			img_On = ImageIO.read(new File("./PICT_S/OnClose.png"));
			img_Out = ImageIO
					.read(new File("./PICT_S/OutClose.png"));
			ICON_INTO_CLOSE= new ImageIcon(img_On);
			ICON_EXIT_CLOSE = new ImageIcon(img_Out);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	public static boolean AddScore(String Path) {
		try {
			//System.out.println("执行");

			//System.out.println("结束");
			ICON = ImageIO.read(new File(Path + "ICON.png"));
			BACK_GROUND = ImageIO.read(new File(Path+BackGroundName));
			ZHUAN_KUAI = ImageIO.read(new File(Path + "BRICKS.png"));
			FOOD = ImageIO.read(new File(Path + "FOOD.png"));
			SNAKE_BODY[0] = ImageIO.read(new File(Path + "BODY_L_R.png"));
			SNAKE_BODY[1] = SNAKE_BODY[0];
			SNAKE_BODY[2] = ImageIO.read(new File(Path + "BODY_U_D.png"));
			SNAKE_BODY[3] = SNAKE_BODY[2];
			SNAKE_HEAD[0] = ImageIO.read(new File(Path + "HEAD_L.png"));
			SNAKE_HEAD[1] = ImageIO.read(new File(Path + "HEAD_R.png"));
			SNAKE_HEAD[2] = ImageIO.read(new File(Path + "HEAD_U.png"));
			SNAKE_HEAD[3] = ImageIO.read(new File(Path + "HEAD_D.png"));
			SNAKE_LAST[0] = ImageIO.read(new File(Path + "LAST_L.png"));
			SNAKE_LAST[1] = ImageIO.read(new File(Path + "LAST_R.png"));
			SNAKE_LAST[2] = ImageIO.read(new File(Path + "LAST_U.png"));
			SNAKE_LAST[3] = ImageIO.read(new File(Path + "LAST_D.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println(e);
			return false;
		}
		return true;
	}
}
