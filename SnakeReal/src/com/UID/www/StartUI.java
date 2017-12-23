package com.UID.www;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class StartUI {
//开始界面的UI设计
	public JFrame frame;
	public JButton button_StartGame;
	public JButton button_MakeMap;
	public JButton button_ChangeScoure;
	public static JCheckBox checkBox_SmallSize;
	public static JCheckBox checkBox_BigSize;
	public static JCheckBox checkBox_64Size;
	public static JCheckBox checkBox_86Size;
	public JSlider slider;
	public JPanel panel;
	private static File f;
	public static  JComboBox<String> comboBox;
	private JButton btnNewButton;

	public StartUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 239, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		comboBox = new JComboBox<String>();
		comboBox.setBounds(10, 60, 203, 21);
		frame.getContentPane().add(comboBox);
		checkBox_86Size = new JCheckBox("800*600");
		checkBox_86Size.setBounds(6, 6, 85, 23);

		checkBox_86Size.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (checkBox_86Size.isSelected()) {
					checkBox_64Size.setSelected(false);
				} else {
					checkBox_64Size.setSelected(true);
				}
				setComeBox();

			}
		});
		frame.getContentPane().add(checkBox_86Size);
		checkBox_64Size = new JCheckBox("600*400");
		checkBox_64Size.setBounds(6, 31, 85, 23);

		checkBox_64Size.setSelected(true);
		checkBox_64Size.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (checkBox_64Size.isSelected()) {
					checkBox_86Size.setSelected(false);
				} else {
					checkBox_86Size.setSelected(true);
				}
				setComeBox();
			}
		});
		frame.getContentPane().add(checkBox_64Size);
		checkBox_BigSize = new JCheckBox("\u5927\u50CF\u7D20");
		checkBox_BigSize.setBounds(135, 6, 75, 23);

		checkBox_BigSize.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (checkBox_BigSize.isSelected()) {
					checkBox_SmallSize.setSelected(false);
				} else {
					checkBox_SmallSize.setSelected(true);
				}
				setComeBox();
			}
		});
		frame.getContentPane().add(checkBox_BigSize);
		checkBox_SmallSize = new JCheckBox("\u5C0F\u50CF\u7D20");
		checkBox_SmallSize.setBounds(135, 31, 75, 23);
		checkBox_SmallSize.setSelected(true);

		checkBox_SmallSize.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				if (checkBox_SmallSize.isSelected()) {
					checkBox_BigSize.setSelected(false);
				} else {
					checkBox_BigSize.setSelected(true);
				}
				setComeBox();
			}

		});
		frame.getContentPane().add(checkBox_SmallSize);
		button_ChangeScoure = new JButton("切换贴图");
		button_ChangeScoure.setBounds(120, 228, 93, 23);
		frame.getContentPane().add(button_ChangeScoure);
		button_MakeMap = new JButton("制作地图");
		button_MakeMap.setBounds(10, 228, 93, 23);
		frame.getContentPane().add(button_MakeMap);
		button_StartGame = new JButton("开始游戏");
		button_StartGame.setBounds(63, 195, 93, 23);
		frame.getContentPane().add(button_StartGame);
		slider = new JSlider();
		slider.setBounds(10, 135, 200, 50);
		frame.getContentPane().add(slider);

		btnNewButton = new JButton("\u5220\u9664");
		btnNewButton.setBounds(63, 91, 93, 23);
		btnNewButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!comboBox.getSelectedItem().equals("Map64_10.date")
						&& !comboBox.getSelectedItem().equals("Map86_10.date")
						&& !comboBox.getSelectedItem().equals("Map64_20.date")
						&& !comboBox.getSelectedItem().equals("Map84_20.date")) {
					File[] ff = f.listFiles();
					for (int i = 0; i < ff.length; i++) {
						if (ff[i].getName().equals(comboBox.getSelectedItem())) {
							ff[i].delete();
							setComeBox();
						}
					}

				}
				else{
					frame.setTitle("禁止删除");
				}
			}
		});
		frame.getContentPane().add(btnNewButton);
	}

	public static  void setComeBox() {
		comboBox.removeAllItems();
		if (checkBox_64Size.isSelected() && checkBox_BigSize.isSelected()) {
			f = new File("./Map_64_20/");

		} else if (checkBox_64Size.isSelected()
				&& checkBox_SmallSize.isSelected()) {
			f = new File("./Map_64_10/");

		} else if (checkBox_86Size.isSelected()
				&& checkBox_BigSize.isSelected()) {
			f = new File("./Map_86_20/");

		} else if (checkBox_86Size.isSelected()
				&& checkBox_SmallSize.isSelected()) {
			f = new File("./Map_86_10/");

		} else {
			System.out.println("不可能");
		}
		File[] ff = f.listFiles();
		for (int i = 0; i < ff.length; i++) {
			comboBox.addItem(ff[i].getName());
		}
	}
}
