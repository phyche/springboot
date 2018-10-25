package com.example.springboot.designMode.interpreter;

/**
 * 表达式类AbstractExpression
 */
public abstract class Expression {

    //解释器
    public void interpret(PlayContext playContext) {
        if (playContext.getPlayText().length() == 0) {
            return;
        } else {

            String playKey = playContext.getPlayText().substring(0,1);
            playContext.setPlayText(playContext.getPlayText().substring(2));
            Integer playValue = Math.round(Float.valueOf(playContext.getPlayText().substring(0,playContext.getPlayText().indexOf(" "))));

            //获得playKey和playValue后将其从演奏文本中移除
            playContext.setPlayText(playContext.getPlayText().substring(playContext.getPlayText().indexOf(" ")+1));
            excute(playKey,playValue);
        }
    }

    public abstract void excute(String key,Integer value);
}
