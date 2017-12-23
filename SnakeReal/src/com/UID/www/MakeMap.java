package com.UID.www;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class MakeMap {
    private JButton button_Save;
    private JButton button_Clear;
    private JTextField Map_Name;
    public static boolean isChangeComBox = false;
    private BufferedImage img = new BufferedImage(Sources.WIDTH + 1,
            Sources.HEIGHT + 1, BufferedImage.TYPE_INT_RGB);
    private int[][] MapSave;
    private Graphics2D g = (Graphics2D) img.getGraphics();
    private int pX;
    private int pY;
    private UI_EatSnake UI_MakeMap;

    public MakeMap() {
        Map_Name = new JTextField();
        Map_Name.setBounds(200, 0, 100, 30);
        button_Save = new JButton("Save");
        button_Clear = new JButton("ClearAll");
        button_Save.setBounds(0, 0, 100, 30);
        button_Save.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                tishi();

            }
        });
        button_Clear.setBounds(100, 0, 100, 30);
        button_Clear.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent arg0) {
                g.setColor(Color.WHITE);
                for (int i = 0; i < MapSave.length; i++) {
                    for (int j = 0; j < MapSave[i].length; j++) {
                        if (MapSave[i][j] == 1) {
                            g.fillRect(j * Sources.SIZE + 1, i * Sources.SIZE
                                    + 1, Sources.SIZE - 1, Sources.SIZE - 1);
                            MapSave[i][j] = 0;
                        }
                    }
                }
                UI_MakeMap.drawNow(img);
            }
        });
        MapSave = new int[Sources.HEIGHT / Sources.SIZE][Sources.WIDTH
                / Sources.SIZE];
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, Sources.WIDTH + 1, Sources.HEIGHT + 1);
        g.setColor(Color.BLACK);
        for (int i = 0; i <= Sources.WIDTH; i += Sources.SIZE) {
            g.drawLine(i, 0, i, Sources.HEIGHT);

        }
        for (int i = 0; i <= Sources.HEIGHT; i += Sources.SIZE) {
            g.drawLine(0, i, Sources.WIDTH, i);

        }
        UI_MakeMap = new UI_EatSnake();
        UI_MakeMap.addExitLesiten(false);
        UI_MakeMap.frame.setVisible(true);
        UI_MakeMap.drawNow(img);
        UI_MakeMap.Panel_Head.add(button_Save);
        UI_MakeMap.Panel_Head.add(button_Clear);
        UI_MakeMap.Panel_Head.add(Map_Name);
        UI_MakeMap.frame.addMouseMotionListener(new MouseAdapter() {//添加鼠标移动监听
            @Override
            public void mouseDragged(MouseEvent e) {
                pX = e.getX();
                pY = e.getY();
                int x = pX / Sources.SIZE;
                int y = pY / Sources.SIZE;
                //以下为设置绘制在方格内
                if (pX >= 0 && pY >= 0 && pX < Sources.WIDTH
                        && pY < Sources.HEIGHT && MapSave[y][x] == 0) {
                    g.setColor(Color.RED);
                    g.fillRect(x * Sources.SIZE + 1, y * Sources.SIZE + 1,
                            Sources.SIZE - 1, Sources.SIZE - 1);
                    MapSave[y][x] = 1;
                    UI_MakeMap.drawNow(img);
                }

            }
        });
        UI_MakeMap.frame.addMouseListener(new MouseAdapter() {//添加鼠标按下监听
            @Override
            public void mouseClicked(MouseEvent e) {
                pX = e.getX();
                pY = e.getY();
                int x = pX / Sources.SIZE;
                int y = pY / Sources.SIZE;
                //	System.out.println(x + " " + y);
                if (pX >= 0 && pY >= 0 && pX < Sources.WIDTH
                        && pY < Sources.HEIGHT && MapSave[y][x] == 0) {
                    g.setColor(Color.RED);
                    MapSave[y][x] = 1;
                } else if (pX >= 0 && pY >= 0 && pX < Sources.WIDTH
                        && pY < Sources.HEIGHT) {
                    g.setColor(Color.WHITE);
                    MapSave[y][x] = 0;
                } else {
                    System.out.println("滚");
                }
                g.fillRect(x * Sources.SIZE + 1, y * Sources.SIZE + 1,
                        Sources.SIZE - 1, Sources.SIZE - 1);

                UI_MakeMap.drawNow(img);

            }
        });

    }

    private void tishi() {//提示框，提示是否保存
        int option = JOptionPane.showConfirmDialog(null, "是(保存退出)否(只是退出)",
                "是否保存并退出？", JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE, null);
            switch (option) {
            case JOptionPane.YES_NO_OPTION: {
                if (saveMap()) {
                    StartUI.setComeBox();
                    UI_MakeMap.frame.setVisible(false);
                    UI_MakeMap.f.setVisible(false);

                } else {
                    UI_MakeMap.Panel_Head.getGraphics().drawString("命名已存在，请重新命名", 300, 15);

                }
                break;
            }
            case JOptionPane.NO_OPTION:
                UI_MakeMap.frame.setVisible(false);
                UI_MakeMap.f.setVisible(false);

        }

    }

    private boolean saveMap() {//判断文件名是否合法
        String[] i = Sources.MapPath.split("/");
        File f = new File(Sources.MapPath + i[1] + Map_Name.getText() + ".date");
        if (f.exists())
            return false;
        else {
            PrintWriter pw = null;
            try {
                f.createNewFile();
                pw = new PrintWriter(new FileWriter(f));

            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            String s = "";
            for (int j = 0; j < MapSave.length; j++) {
                for (int j2 = 0; j2 < MapSave[j].length; j2++) {
                    s = s + MapSave[j][j2];
                }
                pw.println(s);
                s = "";
            }
            pw.flush();
            pw.close();

            return true;
        }
    }
}
