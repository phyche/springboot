package com.example.springboot.designMode.state;

/**
 * 状态模式
 * 当一个对象内在状态改变时允许改变其行为，这个对象看起来是改变了其类
 * 使用场合：当控制一个对象状态转换的条件表达式过于复杂的情况下
 * 当一个对象的行为取决于它的状态，并且它必须在运行时刻根据状态改变行为
 *
 * 目的是消除庞大的条件分支语句
 */
public class StateMain {

    public static void main(String[] strg) {
        Work work = new Work();
        work.setHour(Double.valueOf(9));
        work.writeProgram();
        work.setHour(Double.valueOf(10));
        work.writeProgram();
        work.setHour(Double.valueOf(12));
        work.writeProgram();
        work.setHour(Double.valueOf(13));
        work.writeProgram();
        work.setHour(Double.valueOf(14));
        work.writeProgram();
        work.setHour(Double.valueOf(17));

        //work.setFinish(true);
        work.setFinish(false);
        work.writeProgram();
        work.setHour(Double.valueOf(19));
        work.writeProgram();
        work.setHour(Double.valueOf(22));
        work.writeProgram();
    }
}
