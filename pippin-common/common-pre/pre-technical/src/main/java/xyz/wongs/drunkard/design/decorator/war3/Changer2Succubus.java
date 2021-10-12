package xyz.wongs.drunkard.design.decorator.war3;

/** 女妖形态
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2019/10/9 - 11:00
 * @since 1.0.0
 */
public class Changer2Succubus extends Changer{

    public Changer2Succubus(DmHunter dmHunter) {
        super(dmHunter);
    }

    @Override
    public void display() {
        setChange();
        super.display();
    }

    private void setChange() {
        ((Original)super.getDmHunter()).setImage("2.jpg");
    }
}
