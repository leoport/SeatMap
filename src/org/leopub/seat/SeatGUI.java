package org.leopub.seat;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.WindowConstants;

public class SeatGUI extends JFrame {
    private static final long serialVersionUID = -1835308198743191719L;

    private JLabel courseNameLabel;
    private JTextField courseNameTextField;
    private JLabel courseTimeLabel;
    private JTextField courseTimeTextField;
    private JLabel roomLabel;
    private JTextField roomTextField;
    private JLabel unitsLabel;
    private JTextField unitsTextField;
    private JButton generateButton;

    public SeatGUI() {
        initComponents();
    }
     
    private void initComponents() {
        courseNameLabel = new JLabel();
        courseNameTextField = new JTextField();
        courseTimeLabel = new JLabel();
        courseTimeTextField = new JTextField();
        roomLabel = new JLabel();
        roomTextField = new JTextField();
        unitsLabel = new JLabel();
        unitsTextField = new JTextField();
        generateButton = new JButton();
 
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("考试座位表生成器");
 
        courseNameLabel.setText("课程名称");
        courseTimeLabel.setText("考试时间");
        roomLabel.setText("考试地点");
        unitsLabel.setText("考试班级");
        generateButton.setText("生成座位表");
 
        generateButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent event) {
                try {
                    List<Unit> units = new ArrayList<Unit>();
                    for (String unitName : unitsTextField.getText().split(" ")) {
                        units.add(new Unit(unitName));
                    }
                    String filename = Generator.generateExcel(courseNameTextField.getText(), new Room(roomTextField.getText()), units, courseTimeTextField.getText());
                    Runtime.getRuntime().exec("cmd /c start " + filename);
                } catch (SeatFileException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(null, e.getMessage());
                }
            }
        });
 
        setSize(250, 220);
        setLocation(300, 300);
        GroupLayout layout = new GroupLayout(getContentPane());
        this.getContentPane().setLayout(layout);
        GroupLayout.SequentialGroup hGroup = layout.createSequentialGroup();
        hGroup.addGap(5);//��Ӽ��
        hGroup.addGroup(layout.createParallelGroup()
                .addComponent(courseNameLabel)
                .addComponent(courseTimeLabel)
                .addComponent(roomLabel)
                .addComponent(unitsLabel));
        hGroup.addGap(5);
        hGroup.addGroup(layout.createParallelGroup()
                .addComponent(courseNameTextField)
                .addComponent(courseTimeTextField)
                .addComponent(roomTextField)
                .addComponent(unitsTextField)
                .addComponent(generateButton));
        hGroup.addGap(5);
        layout.setHorizontalGroup(hGroup);
        GroupLayout.SequentialGroup vGroup = layout.createSequentialGroup();
        vGroup.addGap(10);
        vGroup.addGroup(layout.createParallelGroup()
                .addComponent(courseNameLabel)
                .addComponent(courseNameTextField));
        vGroup.addGap(5);
        vGroup.addGroup(layout.createParallelGroup()
                .addComponent(courseTimeLabel)
                .addComponent(courseTimeTextField));
        vGroup.addGap(5);
        vGroup.addGroup(layout.createParallelGroup()
                .addComponent(roomLabel)
                .addComponent(roomTextField));
        vGroup.addGap(5);
        vGroup.addGroup(layout.createParallelGroup()
                .addComponent(unitsLabel)
                .addComponent(unitsTextField));
        vGroup.addGroup(layout.createParallelGroup(Alignment.TRAILING)
                .addComponent(generateButton));
        vGroup.addGap(10);
        layout.setVerticalGroup(vGroup);
    }
}
