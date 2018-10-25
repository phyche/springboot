package com.example.springboot.designMode.interpreter;

/**
 * 解释器模式
 * 给定一个语言，定义它的文法的一种表示，并定义一个解释器，这个解释器使用该表示来解释语言中的句子
 *
 * 使用场合：
 * 当有一个语言需要解释执行，并且你可将该语言中的句子表示为一个抽象语法树时，可以使用解释器模式
 *
 * 优点：容易的改变和扩展文法
 * 不足：解释器模式为文法中的每一条规则至少定义了一个类，因此包含许多规则的文法可能难以管理和维护
 * 建议当文法非常复杂的时候，使用其他的技术如语法分析程序或编译器生成器来处理
 */
public class InterpreterMain {

    public static void main(String[] strg) {

        test();
        test2();
    }

    public static void test() {
        PlayContext context = new PlayContext();
        //音乐--上海滩
        System.out.println("上海滩：");
        context.setPlayText("O 2 E 0.5 G 0.5 A 3 E 0.5 G 0.5 D 3 E 0.5 G 0.5 A 0.5 O 3 C 1 O 2 A 0.5 G 1 C 0.5 E 0.5 D 3 ");
        Expression expression = null;
        while (context.getPlayText().length() > 0) {

            String str = context.getPlayText().substring(0,1);
            switch (str) {
                case "O":
                    expression = new Scale();//当首字母是O时则表达式实例化为音阶
                    break;
                case "C":
                case "D":
                case "E":
                case "F":
                case "G":
                case "A":
                case "B":
                case "P":
                    expression = new Note();//当首字母不是O时则表达式实例化为音符
                    break;
            }
            expression.interpret(context);
        }
    }

    public static void test2() {
        PlayContext context = new PlayContext();
        //音乐--上海滩
        System.out.println("上海滩：");
        context.setPlayText("T 500 O 2 E 0.5 G 0.5 A 3 E 0.5 G 0.5 D 3 E 0.5 G 0.5 A 0.5 O 3 C 1 O 2 A 0.5 G 1 C 0.5 E 0.5 D 3 ");
        Expression expression = null;
        while (context.getPlayText().length() > 0) {

            String str = context.getPlayText().substring(0,1);
            switch (str) {
                case "O":
                    expression = new Scale();//当首字母是O时则表达式实例化为音阶
                    break;
                case "T":
                    expression = new Speed();//当首字母是T时则表达式实例化为速度
                    break;
                case "C":
                case "D":
                case "E":
                case "F":
                case "G":
                case "A":
                case "B":
                case "P":
                    expression = new Note();//当首字母不是O，也不是T时则表达式实例化为音符
                    break;
            }
            expression.interpret(context);
        }
    }
}
