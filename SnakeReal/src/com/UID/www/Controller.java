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
	private static StartUI start;// ��Ϸ��ʼ������UI
	public static int Results = 0;// �ɼ�
	public final HashMap<Integer, Integer> WEIGHT = new HashMap<Integer, Integer>();// ��¼ÿ�������ֵ��ָ���ֵ���෴������ӵ���0
	public static final int L = 0, R = 1, U = 2, D = 3;//ÿ�������������ֵ
	private static BufferedImage img;// ����������Ϸ����ı���
	public static Graphics2D g;// ������Ϸ����Ļ���
	public static BufferedImage SaveBackGround;// ���ڱ��汳��ͼƬ
	public Graphics2D gSave;// ����ͼƬ�Ļ���
	private UI_EatSnake window;// ��ʼ��Ϸ��UI
	private SnakeModel snake;// ��
	public static int[][] GameMap;//��ͼ
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

		window = new UI_EatSnake();// ����Ϸ����New����
		window.frame.setVisible(true);
		window.Panel_Head.setBackground(Color.DARK_GRAY);
		//Ϊ������ʾ�齨���KeyListener ,�����ⲻ����������
		window.frame.addKeyListener(this);
		window.f.addKeyListener(this);
		window.addExitLesiten(false);
		//��ͣ��ť��Ӽ���
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
		window.drawNow(img);//����ͼ
		snake = new SnakeModel();//new����
		new FoodModel();//new��ʳ��
//�߿�ʼ������
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
						// System.out.println("�߳�ִ��");
						if (snake.walk()) {// ������ߵ���һ�����ص���True�������߳Ե�ʳ��
							Results++;// �ɼ�+1��
							window.Panel_Head.repaint();//֪ͨ�ɼ��޸�
							GameMap[FoodModel.pY][FoodModel.pX] = 0;//�Ե�ʳ����ʳ����ռ��Ԫ���Ϊ�ݵ��������0
							new FoodModel();
						}
						window.drawNow(img);//������ǰ״̬
						isDie = SnakeModel.getIsDie();//�������û��
						}
				}
				// window.frame.setVisible(false);
				window.Panel_Head.getGraphics().drawString("������", 100, 15);
			}

		}.start();
	}
//�ڻ���ͼ�ϻ�����ͼ
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
//��ȡ��ͼ��
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
		new Sources();//������Դ
		start = new StartUI();//���ý���
		
		start.frame.setVisible(true);
		//����Ϊ���ÿ�ʼ����һЩ����ļ�����Ϣ
		StartUI.setComeBox();
		start.button_MakeMap.addActionListener(new ActionListener() {//������ͼ

			@Override
			public void actionPerformed(ActionEvent e) {
				GetMessage();//��ȡ��Ҫ�����ĵ�ͼ�Ĵ�С��Ϣ
				new MakeMap();//new����������
			}
		});
		start.button_ChangeScoure.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

			}
		});
		start.button_StartGame.addActionListener(new ActionListener() {//���˿�ʼ��Ϸ��ť����ʼ��Ϸ

			@Override
			public void actionPerformed(ActionEvent arg0) {
				GetMessage();//��ȡ��Ϣ
				Sources.SPEED = (100 - start.slider.getValue()) + 50;//����ó��������ٶ�

				start.frame.setVisible(false);

				Sources.AddScore(Sources.ImgPath);//��ȡ��Ӧ����Դλ��
				Controller c = new Controller();
				addListen(c);//����Ƿ������ļ���
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
						tishi("��ĳɼ���" + Controller.Results + "��", c);//������ʾ��

						System.out.println("ִ����");
					}
				}
			}
		}.start();
	}

	private static void GetMessage() {//��ȡ���ý����һϵ����Ϣ
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
			// System.out.println("����8610");
			Sources.ImgPath = Sources.smallImage;
			Sources.SIZE = 10;
			Sources.HEIGHT = 600;
			Sources.WIDTH = 800;
			Sources.BackGroundName = "BACK_GROUND_Big.png";
			Sources.MapPath = "./Map_86_10/";
			Sources.MapName = (String) StartUI.comboBox.getSelectedItem();
		} else if (StartUI.checkBox_86Size.isSelected()
				&& StartUI.checkBox_BigSize.isSelected()) {
			// System.out.println("����8620");
			Sources.SIZE = 20;
			Sources.ImgPath = Sources.bigImage;
			Sources.HEIGHT = 600;
			Sources.WIDTH = 800;
			Sources.BackGroundName = "BACK_GROUND_Big.png";
			Sources.MapPath = "./Map_86_20/";
			Sources.MapName = (String) StartUI.comboBox.getSelectedItem();
		}
	}

	private static void tishi(String s, Controller c) {//��ʾ��
		int option = JOptionPane.showConfirmDialog(null, s, "�Ƿ�ʼ��һ�֣�",
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
	public void keyPressed(KeyEvent e) {//��������
		switch (e.getKeyCode()) {
		case KeyEvent.VK_LEFT:
			if (WEIGHT.get(snake.dir) + WEIGHT.get(L) != 0) {
				
				snake.dir = L;
				 System.out.println("��");
			}
			break;
		case KeyEvent.VK_RIGHT:
			if (WEIGHT.get(snake.dir) + WEIGHT.get(R) != 0) {
				snake.dir = R;
				System.out.println("��");
			}
			break;
		case KeyEvent.VK_UP:
			if (WEIGHT.get(snake.dir) + WEIGHT.get(U) != 0) {
				snake.dir = U;
				System.out.println("��");
			}
			break;
		case KeyEvent.VK_DOWN:
			if (WEIGHT.get(snake.dir) + WEIGHT.get(D) != 0) {
				snake.dir = D;
				System.out.println("��");
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