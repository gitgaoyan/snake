package com.UID.www;

import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
public class UI_EatSnake {
//游戏界面的UI设计
	public JFrame frame;
    private BufferedImage img = new BufferedImage(Sources.WIDTH,Sources.HEIGHT,BufferedImage.TYPE_INT_RGB);
    //所创建图像的宽度,所创建图像的高度,所创建图像的风格类型（是bufferedImage中定义静态变量，返回int。）
	public JFrame f;
	public JPanel Panel_Head;
	public JButton Button_EXIT;
	
	public UI_EatSnake() {
		initialize();//初始化一个窗口
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame() {//这是上边的控制框
			
			private static final long serialVersionUID = 1L;

			@Override
			public void paint(Graphics g) {
		
				g.drawImage(img, 0, 0, null);
			}
		};
		frame.setBounds(100, 100, Sources.WIDTH+1,Sources.HEIGHT+1);
		frame.setUndecorated(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	    f = new JFrame();//这是下边的游戏框
		f.setBounds(100, 80, Sources.WIDTH, 20);
		f.setUndecorated(true);
		f.setVisible(true);
		f.getContentPane().setLayout(null);
         
		 Panel_Head = new JPanel(){
			
			private static final long serialVersionUID = 1L;

			@Override
			public void paint(Graphics g) {
				super.paint(g);
				g.drawString("你的成绩是"+Controller.Results, 10, 15);
			}
		 };
		
		Panel_Head.setLayout(null);
		Panel_Head.setBounds(0, 0, Sources.WIDTH, 20);
		Panel_Head.addMouseMotionListener(new MouseAdapter() {
			private Point draggingAnchor = null;

			@Override
			public void mouseMoved(MouseEvent e) {

				f.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				frame.setCursor(Cursor
						.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				draggingAnchor = new Point(e.getX() + Panel_Head.getX(), e
						.getY() + Panel_Head.getY());

			}

			@Override
			public void mouseDragged(MouseEvent e) {
				f.setLocation(e.getLocationOnScreen().x - draggingAnchor.x,
						e.getLocationOnScreen().y - draggingAnchor.y);
				frame.setLocation(e.getLocationOnScreen().x - draggingAnchor.x,
						e.getLocationOnScreen().y - draggingAnchor.y + 20);
				//System.out.println(frame.getX());

			}
		});
		 Button_EXIT = new JButton();
		Button_EXIT.setBounds(Sources.WIDTH-20, 0, 20, 20);
		Button_EXIT.setBorderPainted(false);
		Panel_Head.add(Button_EXIT);
		Button_EXIT.setIcon(Sources.ICON_EXIT_CLOSE);

		f.getContentPane().add(Panel_Head);
	}
	public void addExitLesiten(final boolean isExit) {
		Button_EXIT.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseExited(MouseEvent e) {
				Button_EXIT.setIcon(Sources.ICON_EXIT_CLOSE);
			}
			@Override
			public void mouseEntered(MouseEvent e) {
				Button_EXIT.setIcon(Sources.ICON_INTO_CLOSE);
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if(isExit==false)
				System.exit(0);
				else
					System.out.println("没结束");
			}
		});
	}
	public void drawNow(BufferedImage bimg) {
		img = bimg;
		frame.repaint();
//		如果此组件是轻量级组件，则此方法会尽快调用此组件的 paint 方法。否则此方法会尽快调用此组件的 update 方法。 
//repaint()调用此组件的 paint 方法刷新窗体。

	}
}
