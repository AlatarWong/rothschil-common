package xyz.wongs.drunkard.design.decorator.war3;

import javax.swing.*;
import java.awt.*;

/** 原生形态
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2019/10/9 - 10:58
 * @since 1.0.0
 */
public class Original extends JFrame implements DmHunter{


    public Original() {
        super("恶魔猎手");
    }

    @Override
    public void display() {
        this.setLayout(new FlowLayout());
        JLabel l1 = new JLabel(new ImageIcon("Doc/decorator/" + t));
        this.add(l1);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private String t = "1.jpg";

    public void setImage(String t){
        this.t = t;
    }
}
