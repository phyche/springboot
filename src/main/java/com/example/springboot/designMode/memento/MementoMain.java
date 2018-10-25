package com.example.springboot.designMode.memento;

/**
 *备忘录模式
 * 在不破坏封装性的前提下，捕获一个对象的内部状态，并在该对象之外保存这个状态。
 *
 * 适用于功能比较复杂，但需要维护或记录历史属性的类，或者需要保存的熟悉只是众多属性中很小一部分
 */
public class MementoMain {

    public static void main(String[] strg) {

        GameRole lixiaoyao = new GameRole();
        lixiaoyao.getInstanceState();
        lixiaoyao.stateDisplay();

        RoleStackCaretaker stateAdmin = new RoleStackCaretaker();
        stateAdmin.setMemento(lixiaoyao.saveState());

        //大战boss时，损耗严重
        lixiaoyao.fight();
        lixiaoyao.stateDisplay();

        //恢复之前的状态
        lixiaoyao.recoveryState(stateAdmin.getMemento());
        lixiaoyao.stateDisplay();
    }
}
