//package com.example.springboot.controller;
//
//import com.alibaba.fastjson.JSON;
//import com.alibaba.fastjson.JSONArray;
//import com.example.springboot.module.DisputeType;
//import com.example.springboot.service.DisputeTypeService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.util.StringUtils;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.servlet.ModelAndView;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.UUID;
//
//@RestController
//public class DisputeTypeController {
//
//    @Autowired
//    private DisputeTypeService disputeTypeService;
//
//    @RequestMapping("/selectAll")
//    public JSON selectAll() {
//        String list = getTypeListFun();
//        JSONArray array=JSONArray.parseArray(list);
//        return (JSON) JSON.toJSON(array);
//    }
//
//    @RequestMapping("/selectTypeList")
//    public JSON selectTypeList() {
//        List<DisputeType> list = getTypeList();
//        return (JSON) JSON.toJSON(list);
//    }
//
//    @RequestMapping("/delete")
//    public void delete() {
//        String str = "4,6";
//        String[] ids = StringUtils.split(str,",");
//        disputeTypeService.deleteByIds(ids);
//
//    }
//
//    @RequestMapping("/addType")
//    public void addType() {
//        DisputeType disputeType = new DisputeType();
//        disputeType.setId(UUID.randomUUID().toString().replace("-", "").toLowerCase());
//        disputeType.setParentId("1");
//        disputeType.setParentIds("1");
//        disputeType.setParentNames("menu1");
//        disputeType.setName("menu7");
//        disputeType.setType("1");
//        disputeType.setCreateBy("1");
//        disputeType.setLeadOfficeId("1");
//        disputeType.setCreateDate(new Date());
//        disputeType.setUpdateBy("1");
//        disputeType.setUpdateDate(new Date());
//        disputeType.setDelFlag("0");
//
//        Long sort = 1l;
//        List<DisputeType> list = disputeTypeService.selectAll();
//        if (list.size() > 0) {
//            sort = list.get(list.size() -1).getSort() + 1;
//        }
//        disputeType.setSort(sort);
//        disputeTypeService.add(disputeType);
//
//    }
//
//    public String getTypeListFun(){
//        List<DisputeType> typeList = new ArrayList<DisputeType>();
//        List<DisputeType> paChTypeList = new ArrayList<DisputeType>();
//        typeList = disputeTypeService.selectAll();
//        StringBuffer menuBuffer=new StringBuffer();
//        String menu="";
//        menuBuffer.append("[{name:'分类',open:true,id:'0',children:[");
//
//        if(typeList!=null){
//            for(DisputeType s: typeList){
//                if(!s.getParentId().isEmpty() && "0".equals(s.getParentId())){
//                    menuBuffer.append("{name:'"+s.getName()+"', id:'"+s.getId()+"',");
//                    List<DisputeType> iterateMenus = iterateMenus(typeList, s.getId(),menuBuffer);
//                    s.setSubList(iterateMenus);
//                    menuBuffer.append("},");
//                    paChTypeList.add(s);
//                }
//            }
//            menuBuffer.append("]");
//            menuBuffer.append("}]");
//            menu=menuBuffer.toString();
//            menu=menu.replaceAll(",]", "]");
//
//        }
//        return menu;
//    }
//
//    public List<DisputeType> iterateMenus(List<DisputeType> list,String pid,StringBuffer buffer){
//        List<DisputeType> result = new ArrayList<DisputeType>();
//        buffer.append(" children:[");
//        for (DisputeType  disputeType :list) {
//            String typeId = disputeType.getId();//获取菜单的id
//            String parentid = disputeType.getParentId();//获取菜单的父id
//            if(!parentid.isEmpty()){
//                if(parentid.equals(pid)){
//                    buffer.append("{ name:'"+disputeType.getName()+"', id:'" + disputeType.getId() + "',");
//                    List<DisputeType> iterateMenu = iterateMenus(list,typeId,buffer);
//                    disputeType.setSubList(iterateMenu);
//                    buffer.append("},");
//                    result.add(disputeType);
//                }
//            }
//        }
//
//        buffer.append("]");
//        return result;
//    }
//
//    public List<DisputeType> getTypeList(){
//        List<DisputeType> typeList = new ArrayList<DisputeType>();
//        List<DisputeType> paChTypeList = new ArrayList<DisputeType>();
//        typeList = disputeTypeService.selectAll();
//        List<DisputeType> list = new ArrayList<>();
//        String menu="";
//
//        if(typeList!=null){
//            for(DisputeType s: typeList){
//                if(!s.getParentId().isEmpty() && "0".equals(s.getParentId())){
//                    paChTypeList.add(s);
//                    List<DisputeType> iterateMenus = iterate(typeList, s.getId(),paChTypeList);
//                }
//            }
//        }
//        return paChTypeList;
//    }
//
//    public List<DisputeType> iterate(List<DisputeType> list,String pid,List<DisputeType> pList){
//        List<DisputeType> result = new ArrayList<DisputeType>();
//        for (DisputeType  disputeType :list) {
//            String typeId = disputeType.getId();//获取菜单的id
//            String parentid = disputeType.getParentId();//获取菜单的父id
//            if(!parentid.isEmpty()){
//                if(parentid.equals(pid)){
//                    pList.add(disputeType);
//                    List<DisputeType> iterateMenu = iterate(list,typeId,pList);
//                    disputeType.setSubList(iterateMenu);
//                    result.add(disputeType);
//                }
//            }
//        }
//        return result;
//    }
//}
