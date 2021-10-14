package io.github.rothschil.design.decorator.war3;

/**
 * @author <a href="https://github.com/rothschil">Sam</a>
 * @date 2019/10/9 - 11:09
 * @since 1.0.0
 */
public class War3Main {

    public static void main(String[] args) {
        DmHunter hunter = new Original();
        hunter.display();

        DmHunter succubus = new Changer2Succubus(hunter);
        succubus.display();

        DmHunter girl = new Changer2Girl(hunter);
        girl.display();
    }
}
