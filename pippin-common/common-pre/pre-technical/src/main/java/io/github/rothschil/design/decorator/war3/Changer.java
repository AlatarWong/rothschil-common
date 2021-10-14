package io.github.rothschil.design.decorator.war3;

/**
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2019/10/9 - 10:58
 * @since 1.0.0
 */
public class Changer implements DmHunter{

    private DmHunter dmHunter;

    public Changer() {
    }

    public Changer(DmHunter dmHunter) {
        this.dmHunter = dmHunter;
    }

    @Override
    public void display() {
        dmHunter.display();
    }

    public DmHunter getDmHunter() {
        return dmHunter;
    }
}
