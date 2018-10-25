package com.example.springboot;

import java.util.*;

public class Test {
    /*public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        List<Map<String,Object>> list = new ArrayList<>();
        Map temp = new HashMap();
        while (in.hasNextInt()) {//注意while处理多个case
            temp.put("num",Integer.valueOf(in.nextInt()));
            list.add(temp);
        }

        Collections.sort(list, new Comparator<Map<String,Object>>(){

            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                if(Integer.valueOf(o1.get("num").toString()) > Integer.valueOf(o2.get("num").toString())){
                    return 1;
                }
                if(Integer.valueOf(o1.get("num").toString()) == Integer.valueOf(o2.get("num").toString())){
                    return 0;
                }
                return -1;
            }
        });
        String str = "";
        for(Map<String,Object> map : list) {
            str += map.get("num") + ",";
        }
        System.out.print(str);

    }*/
    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        String a = "22 33 11";
        String[] b = a.split(" ");
        List<Map<String,Object>> list = new ArrayList<>();
        int m = 1;
        for (int i=0;i<b.length;i++) {
            Map temp = new HashMap();
            temp.put("num",b[i]);
            m = m+1;
            list.add(temp);
        }

        Collections.sort(list, new Comparator<Map<String,Object>>(){

            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                if(Integer.valueOf(o1.get("num").toString()) > Integer.valueOf(o2.get("num").toString())){
                    return -1;
                }
                if(Integer.valueOf(o1.get("num").toString()) == Integer.valueOf(o2.get("num").toString())){
                    return 0;
                }
                return 1;
            }
        });
        String str = "";
        for(Map<String,Object> map : list) {
            str += map.get("num") + ", ";
        }
        System.out.print(str.substring(0,str.length()-2));

    }

}
