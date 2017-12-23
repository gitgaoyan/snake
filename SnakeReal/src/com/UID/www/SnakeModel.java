package com.UID.www;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class SnakeModel {
	private static ArrayList<int[][]> SnakeBody;//��������ͷΪ��һ��
	private static boolean isDie = false;

	protected int dir = Controller.R;
	private HashMap<Integer, int[][]> Add = new HashMap<Integer, int[][]>();
	private boolean needAdd = false;

	public SnakeModel() {//��ʼ������
		SnakeBody = new ArrayList<int[][]>();
		int[][] L = { { -1 }, { 0 } };
		int[][] R = { { 1 }, { 0 } };
		int[][] U = { { 0 }, { -1 } };
		int[][] D = { { 0 }, { 1 } };
		Add.put(Controller.L, L);
		Add.put(Controller.R, R);
		Add.put(Controller.U, U);
		Add.put(Controller.D, D);
	
		int[][] MyFristBody = getBody(Controller.GameMap);
		int[][] body_0 = { { MyFristBody[0][0] + 2 }, { MyFristBody[1][0] } };
		int[][] body_1 = { { MyFristBody[0][0] + 1 }, { MyFristBody[1][0] } };
		int[][] body_2 = { { MyFristBody[0][0] }, { MyFristBody[1][0] } };
		SnakeBody.add(body_2);
		SnakeBody.add(body_1);
		SnakeBody.add(body_0);
		Controller.g.drawImage(Sources.SNAKE_HEAD[dir], body_0[0][0]
				* Sources.SIZE, body_0[1][0] * Sources.SIZE, null);
		Controller.g.drawImage(Sources.SNAKE_BODY[dir], body_1[0][0]
				* Sources.SIZE, body_1[1][0] * Sources.SIZE, null);
		Controller.g.drawImage(Sources.SNAKE_LAST[dir], body_2[0][0]
				* Sources.SIZE, body_2[1][0] * Sources.SIZE, null);
	}
public static synchronized void setSnakeIsDie(boolean die) {//�����ߵ�״̬�������Լ����ţ�
	isDie = die;

}
	private int[][] getBody(int[][] GameMap) {//��ȡ��������ʹ�䲻��ʯͷ�����ɲ��������֮�ڲ�������ʯͷ��
		Random r = new Random();
		int pX = r.nextInt(Sources.WIDTH / Sources.SIZE - 5);
		int pY = r.nextInt(Sources.HEIGHT / Sources.SIZE);
		while (GameMap[pY][pX] + GameMap[pY][pX + 1] + GameMap[pY][pX + 2]
				+ GameMap[pY][pX + 3] + GameMap[pY][pX + 4]
				+ GameMap[pY][pX + 5] != 0) {
			pX = r.nextInt(Sources.WIDTH / Sources.SIZE - 5);
			pY = r.nextInt(Sources.HEIGHT / Sources.SIZE);
		}
		int[][] body = { { pX }, { pY } };
		return body;
	}

	public boolean walk() {//��
		int[][] nextPoint = getNextPoint();//��һ����λ��
		//������һ��������
		Controller.g.drawImage(Sources.SNAKE_HEAD[dir], nextPoint[0][0]
				* Sources.SIZE, nextPoint[1][0] * Sources.SIZE, null);
		Controller.g.drawImage(Sources.SNAKE_BODY[dir],
				SnakeBody.get(SnakeBody.size() - 1)[0][0] * Sources.SIZE,
				SnakeBody.get(SnakeBody.size() - 1)[1][0] * Sources.SIZE, null);
		SnakeBody.add(nextPoint);
		//System.out.println(needAdd);
		if(!needAdd){//�����һ������ȥ����˵����һ��û�Ե�ʳ���һ���ͻ��Ƴ�һ��β������������
		
		Controller.g.drawImage(Controller.SaveBackGround.getSubimage(
				SnakeBody.get(0)[0][0] * Sources.SIZE, SnakeBody.get(0)[1][0]
						* Sources.SIZE, Sources.SIZE, Sources.SIZE),
				SnakeBody.get(0)[0][0] * Sources.SIZE, SnakeBody.get(0)[1][0]
						* Sources.SIZE, null);	
		
		Controller.g.drawImage(Controller.SaveBackGround.getSubimage(
				SnakeBody.get(1)[0][0] * Sources.SIZE, SnakeBody.get(1)[1][0]
						* Sources.SIZE, Sources.SIZE, Sources.SIZE),
				SnakeBody.get(1)[0][0] * Sources.SIZE, SnakeBody.get(1)[1][0]
						* Sources.SIZE, null);	
		
		
		if (SnakeBody.get(0)[0][0] == SnakeBody.get(1)[0][0]) {
			if (SnakeBody.get(0)[1][0] - SnakeBody.get(1)[1][0] > 0) {// ������β�ͳ���
				Controller.g.drawImage(Sources.SNAKE_LAST[2], SnakeBody.get(1)[0][0]*Sources.SIZE, SnakeBody.get(1)[1][0]*Sources.SIZE,
						null);
			} else {
				Controller.g.drawImage(Sources.SNAKE_LAST[3], SnakeBody.get(1)[0][0]*Sources.SIZE, SnakeBody.get(1)[1][0]*Sources.SIZE,
						null);
			}
		} else if (SnakeBody.get(0)[1][0] == SnakeBody.get(1)[1][0]) {
			if (SnakeBody.get(0)[0][0] - SnakeBody.get(1)[0][0] > 0) {// ������β�ͳ���
				Controller.g.drawImage(Sources.SNAKE_LAST[0], SnakeBody.get(1)[0][0]*Sources.SIZE, SnakeBody.get(1)[1][0]*Sources.SIZE,
						null);
			} else {
				Controller.g.drawImage(Sources.SNAKE_LAST[1], SnakeBody.get(1)[0][0]*Sources.SIZE, SnakeBody.get(1)[1][0]*Sources.SIZE,
						null);
			}
		}
		SnakeBody.remove(0);
		}
		needAdd = false;
		//System.out.println(Controller.GameMap[SnakeBody.get(SnakeBody.size()-1)[1][0]][SnakeBody.get(SnakeBody.size()-1)[0][0]]);
		if(Controller.GameMap[SnakeBody.get(SnakeBody.size()-1)[1][0]][SnakeBody.get(SnakeBody.size()-1)[0][0]] == 2){//����Ե�ʳ���needAddΪtrue����һ���ͻ�����һ������
			System.out.println("�ҳٵ�ʳ����");
			needAdd  = true;
			return true;
		}
		if(Controller.GameMap[SnakeBody.get(SnakeBody.size()-1)[1][0]][SnakeBody.get(SnakeBody.size()-1)[0][0]] == 1){//����Ե�ʯͷ��������Ϊ����״̬
			setSnakeIsDie(true);
		}
	for (int i = 0; i < SnakeBody.size()-1; i++) {
		if(SnakeBody.get(SnakeBody.size()-1)[0][0]==SnakeBody.get(i)[0][0]&&SnakeBody.get(SnakeBody.size()-1)[1][0]==SnakeBody.get(i)[1][0]){
			setSnakeIsDie(true);
		}
	}
		return false;

	}

	private int[][] getNextPoint() {//�������һ����λ��
		int[][] nextPoint = new int[2][1];

		nextPoint[0][0] = SnakeBody.get(SnakeBody.size() - 1)[0][0]
				+ Add.get(dir)[0][0];
		nextPoint[1][0] = SnakeBody.get(SnakeBody.size() - 1)[1][0]
				+ Add.get(dir)[1][0];
		if (nextPoint[0][0] >= Sources.WIDTH / Sources.SIZE) {
			nextPoint[0][0] = 0;
		}
		if (nextPoint[0][0] < 0) {
			nextPoint[0][0] = Sources.WIDTH / Sources.SIZE - 1;
		}
		if (nextPoint[1][0] >= Sources.HEIGHT / Sources.SIZE) {
			nextPoint[1][0] = 0;
		}
		if (nextPoint[1][0] < 0) {
			nextPoint[1][0] = Sources.HEIGHT / Sources.SIZE - 1;
		}
		return nextPoint;
	}

	public static ArrayList<int[][]> getSnakeBody() {//��ȡ��ǰ������
		return SnakeBody;
	}
	public static synchronized boolean getIsDie() {//��ȡ���Ƿ�����
		// TODO Auto-generated method stub
		return isDie;
	}
}
