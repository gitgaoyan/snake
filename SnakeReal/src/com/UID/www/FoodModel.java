package com.UID.www;

import java.util.ArrayList;
import java.util.Random;

public class FoodModel {
	public static  int pX;
	public static  int pY;



	public FoodModel() {
		Random r = new Random();
	pX = r.nextInt(Sources.WIDTH / Sources.SIZE);
	pY = r.nextInt(Sources.HEIGHT / Sources.SIZE);
		ArrayList<int[][]> snakeBody = SnakeModel.getSnakeBody();
		boolean NotGo = true;
		for (int i = 0; i < snakeBody.size(); i++) {
			if (snakeBody.get(i)[0][0] == pX && snakeBody.get(i)[1][0] == pY) {
				NotGo = false;
			}
		}

		while (!(Controller.GameMap[pY][pX] == 0 && NotGo)) {//������������ʳ�����߻���ǽ�ϣ���������ֱ���Ϸ�
			NotGo = true;
			pX = r.nextInt(Sources.WIDTH / Sources.SIZE);
			pY = r.nextInt(Sources.HEIGHT / Sources.SIZE);
			for (int i = 0; i < snakeBody.size(); i++) {
				if (snakeBody.get(i)[0][0] == pX
						&& snakeBody.get(i)[1][0] == pY) {
					NotGo = false;
				}
			}
		
		}
		Controller.GameMap[pY][pX]  = 2;
Controller.g.drawImage(Sources.FOOD, pX * Sources.SIZE,
		pY * Sources.SIZE, null);
	}
	public static void main(String[] args) {
		System.out.println("sdnifehri");
	}
}
