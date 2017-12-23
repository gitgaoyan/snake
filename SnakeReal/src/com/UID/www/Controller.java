package com.UID.www;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.JOptionPane;

public class Controller implements KeyListener {
	private static StartUI start;// 游戏开始的设置UI
	public static int Results = 0;// 成绩
	public final HashMap<Integer, Integer> WEIGHT = new HashMap<Integer, Integer>();// 记录每个方向的值所指向的值，相反方向相加等于0
	public static final int L = 0, R = 1, U = 2, D = 3;//每个方向所代表的值
	private static BufferedImage img;// 用于整个游戏画面的保存
	public static Graphics2D g;// 用于游戏画面的绘制
	public static BufferedImage SaveBackGround;// 用于保存背景图片
	public Graphics2D gSave;// 背景图片的画笔
	private UI_EatSnake window;// 开始游戏的UI
	private SnakeModel snake;// 蛇
	public static int[][] GameMap;//地图
    private  boolean isGoWalk = true;

	public Controller() {
		WEIGHT.put(0, -1);
		WEIGHT.put(1, 1);
		WEIGHT.put(2, 2);
		WEIGHT.put(3, -2);
		img = new BufferedImage(Sources.WIDTH, Sources.HEIGHT,
				BufferedImage.TYPE_INT_RGB);
		SaveBackGround = new BufferedImage(Sources.WIDTH, Sources.HEIGHT,
				BufferedImage.TYPE_INT_RGB);
		g = (Graphics2D) img.getGraphics();
		gSave = (Graphics2D) SaveBackGround.getGraphics();

		window = new UI_EatSnake();// 把游戏界面New出来
		window.frame.setVisible(true);
		window.Panel_Head.setBackground(Color.DARK_GRAY);
		//为所有显示组建添加KeyListener ,以免检测不到键盘输入
		window.frame.addKeyListener(this);
		window.f.addKeyListener(this);
		window.addExitLesiten(false);
		//暂停按钮添加监听
		window.Button_EXIT.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if(isGoWalk)
					isGoWalk = false;
				else
					isGoWalk = true;
			}
		});
		window.Button_EXIT.addKeyListener(this);
		g.drawImage(Sources.BACK_GROUND, 0, 0, null);
		gSave.drawImage(Sources.BACK_GROUND, 0, 0, null);
		GameMap = new int[Sources.HEIGHT / Sources.SIZE][Sources.WIDTH
				/ Sources.SIZE];
		GameMap = getMap();
		drawMap();
		window.drawNow(img);//画地图
		snake = new SnakeModel();//new出蛇
		new FoodModel();//new出食物
//蛇开始走起来
		new Thread() {
			private boolean isDie = false;
			@Override
			public void run() {
				while (!isDie) {
					try {
						Thread.sleep(Sources.SPEED);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
						if(isGoWalk){
						// System.out.println("线程执行");
						if (snake.walk()) {// 如果蛇走的那一步返回的是True，表明蛇吃到食物
							Results++;// 成绩+1；
							window.Panel_Head.repaint();//通知成绩修改
							GameMap[FoodModel.pY][FoodModel.pX] = 0;//吃到食物则本食物所占单元格变为草地所代表的0
							new FoodModel();
						}
						window.drawNow(img);//画出当前状态
						isDie = SnakeModel.getIsDie();//检测蛇死没死
						}
				}
				// window.frame.setVisible(false);
				window.Panel_Head.getGraphics().drawString("你死了", 100, 15);
			}

		}.start();
	}
//在缓冲图上画出地图
	private void drawMap() {

		for (int i = 0; i < GameMap.length; i++) {
			for (int j = 0; j < GameMap[i].length; j++) {
				if (GameMap[i][j] == 1) {

					g.drawImage(Sources.ZHUAN_KUAI, j * Sources.SIZE, i
							* Sources.SIZE, null);
				} else {
					GameMap[i][j] = 0;
				}
			}

		}
	}
//获取地图包
	private int[][] getMap() {
		File f = new File(Sources.MapPath + Sources.MapName);
		int[][] Game_Map = new int[Sources.HEIGHT / Sources.SIZE][Sources.WIDTH
				/ Sources.SIZE];
		String s = null;
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(f));
			for (int i = 0; i < Sources.HEIGHT / Sources.SIZE; i++) {
				s = br.readLine();

				for (int j = 0; j < Sources.WIDTH / Sources.SIZE; j++) {
					Game_Map[i][j] = Integer.parseInt(s.charAt(j) + "");
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return Game_Map;
	}

	public static void main(String[] args) {
		new Sources();//加载资源
		start = new StartUI();//设置界面
		
		start.frame.setVisible(true);
		//以下为设置开始界面一些组件的监听信息
		StartUI.setComeBox();
		start.button_MakeMap.addActionListener(new ActionListener() {//制作地图

			@Override
			public void actionPerformed(ActionEvent e) {
				GetMessage();//获取索要制作的地图的大小信息
				new MakeMap();//new出制作界面
			}
		});
		start.button_ChangeScoure.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});
		start.button_StartGame.addActionListener(new ActionListener() {//点了开始游戏按钮，开始游戏

			@Override
			public void actionPerformed(ActionEvent arg0) {
				GetMessage();//获取信息
				Sources.SPEED = (100 - start.slider.getValue()) + 50;//计算得出所调的速度

				start.frame.setVisible(false);

				Sources.AddScore(Sources.ImgPath);//获取相应的资源位置
				Controller c = new Controller();
				addListen(c);//添加是否死亡的监听
			}

		});
	}

	private static void addListen(final Controller c) {
		new Thread() {
			private boolean isOver = false;

			public void run() {
				
				while (!isOver) {
					try {
						sleep(Sources.SPEED);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					if (SnakeModel.getIsDie()) {
						isOver = true;
						tishi("你的成绩是" + Controller.Results + "分", c);//弹出提示框

						System.out.println("执行了");
					}
				}
			}
		}.start();
	}

	private static void GetMessage() {//获取设置界面的一系列信息
		// TODO Auto-generated method stub
		if (StartUI.checkBox_64Size.isSelected()
				&& StartUI.checkBox_SmallSize.isSelected()) {
			Sources.ImgPath = Sources.smallImage;
			Sources.SIZE = 10;
			Sources.HEIGHT = 400;
			Sources.WIDTH = 600;
			Sources.BackGroundName = "BACK_GROUND.png";
			Sources.MapPath = "./Map_64_10/";
			Sources.MapName = (String) StartUI.comboBox.getSelectedItem();
		} else if (StartUI.checkBox_64Size.isSelected()
				&& StartUI.checkBox_BigSize.isSelected()) {
			Sources.SIZE = 20;
			Sources.ImgPath = Sources.bigImage;
			Sources.HEIGHT = 400;
			Sources.WIDTH = 600;
			Sources.BackGroundName = "BACK_GROUND.png";
			Sources.MapPath = "./Map_64_20/";
			Sources.MapName = (String) StartUI.comboBox.getSelectedItem();
		} else if (StartUI.checkBox_86Size.isSelected()
				&& StartUI.checkBox_SmallSize.isSelected()) {
			// System.out.println("悬赏8610");
			Sources.ImgPath = Sources.smallImage;
			Sources.SIZE = 10;
			Sources.HEIGHT = 600;
			Sources.WIDTH = 800;
			Sources.BackGroundName = "BACK_GROUND_Big.png";
			Sources.MapPath = "./Map_86_10/";
			Sources.MapName = (String) StartUI.comboBox.getSelectedItem();
		} else if (StartUI.checkBox_86Size.isSelected()
				&& StartUI.checkBox_BigSize.isSelected()) {
			// System.out.println("悬赏8620");
			Sources.SIZE = 20;
			Sources.ImgPath = Sources.bigImage;
			Sources.HEIGHT = 600;
			Sources.WIDTH = 800;
			Sources.BackGroundName = "BACK_GROUND_Big.png";
			Sources.MapPath = "./Map_86_20/";
			Sources.MapName = (String) StartUI.comboBox.getSelectedItem();
		}
	}

	private static void tishi(String s, Controller c) {//提示框
		int option = JOptionPane.showConfirmDialog(null, s, "是否开始下一局？",
				JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE, null);
		switch (option) {
		case JOptionPane.YES_NO_OPTION: {
			c.window.f.setVisible(false);
			c.window.frame.setVisible(false);
			SnakeModel.setSnakeIsDie(false);
			Controller.Results = 0;
			c = null;
			c = new Controller();
			addListen(c);
			break;
		}
		case JOptionPane.NO_OPTION:
			SnakeModel.setSnakeIsDie(false);
			c.window.f.setVisible(false);
			c.window.frame.setVisible(false);

			Controller.Results = 0;
			c = null;
			start.frame.setVisible(true);
			break;
		}

	}

	@Override
	public void keyPressed(KeyEvent e) {//按键监听
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			if (WEIGHT.get(snake.dir) + WEIGHT.get(L) != 0) {
				
				snake.dir = L;
				 System.out.println("左");
			}
			break;
		case KeyEvent.VK_RIGHT:
			if (WEIGHT.get(snake.dir) + WEIGHT.get(R) != 0) {
				snake.dir = R;
				System.out.println("右");
			}
			break;
		case KeyEvent.VK_UP:
			if (WEIGHT.get(snake.dir) + WEIGHT.get(U) != 0) {
				snake.dir = U;
				System.out.println("上");
			}
			break;
		case KeyEvent.VK_DOWN:
			if (WEIGHT.get(snake.dir) + WEIGHT.get(D) != 0) {
				snake.dir = D;
				System.out.println("下");
			}
		}

	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub

	}

}