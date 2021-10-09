package xyz.wongs.drunkard.design.decorator.war3;

/** 少女形态
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2021/10/9 - 11:00
 * @since 1.0.0
 */
public class Changer2Girl extends Changer{

    public Changer2Girl(DmHunter dmHunter) {
        super(dmHunter);
    }

    @Override
    public void display() {
        setChange();
        super.display();
    }

    private void setChange() {
        ((Original)super.getDmHunter()).setImage("3.jpg");
    }
}
