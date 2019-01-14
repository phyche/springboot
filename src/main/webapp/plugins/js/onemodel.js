/**
 * Created by lxy on 17-5-31.
 */

//默认交互地址
var defaultUrl = "../";
//默认文件上传地址
var uploadUrl = "../afterservice/afterservice!uploadFile.action";


//传入表单的id得到表单的json字符串
$.getFormJson = function (formId) {
    var formArray = $("#" + formId).serializeArray();

    var jsonStr = "{";
    $.each(formArray, function (i, item) {
        var name = item.name;
        var value = item.value;
        if (value == "" || value == null || value == undefined) {
            value = "\"\"";
        }
        var str = ("\"" + name + "\"") + ":" + (value + ",");
        jsonStr += str;
    })
    jsonStr = jsonStr.substring(0, jsonStr.length - 1);
    jsonStr += "}";
    console.log("form转化完之后的json数据为:" + jsonStr);
    return jsonStr;
}
//传入表单的id得到表单的json对象
$.getFormJsonObject = function (formId) {
    var o = {};
    var a =  $("#" + formId).serializeArray();
    $.each(a, function() {
        if (o[this.name]) {
            if (!o[this.name].push) {
                o[this.name] = [o[this.name]];
            }
            o[this.name].push(this.value || '');
        } else {
            o[this.name] = this.value || '';
        }
    });
    return o;
};

$.postJson = function (url, method, jsonData, callback, isAsync , errorCallback) {
    /**
     封装好的ajax请求模板
     url:请求ｘｍｓ的ａｃｔｉｏｎ
     method：请求maf的接口
     isAsync:是否同步 默认是true(异步),false(同步)
     callback回调函数
     **/
    if(typeof jsonData == "object"){
        jsonData = JSON.stringify(jsonData);//后台只接受json字符串
    }
    var data = {"method": method, "params": jsonData};
    console.log("--------------------请求相关数据   开始 ---------------------");
    console.log(data);
    console.log("--------------------请求相关数据   结束 ---------------------");
    var type = "POST";
    var async = true;
    if (isAsync == false) {
        async = false;
    }
    $.ajax({
        url: url+method,
        type: type,
        data: data,
        /*cache: false,
        async: async,*/
        dataType: "json",
        success: function (data) {
            console.log("--------------------打印后台返回的相关数据 开始--------------------- " + method);
            console.log(data);
            console.log("--------------------打印后台返回的相关数据 结束--------------------- " + method);
            if(data.code=="-1"){
                if (typeof errorCallback === "function") {
                    errorCallback(data);
                }else{
                    $.notifyMsg(data.message,"danger");
                }
            }else{
                if (typeof callback === "function") {
                    callback(data.result);
                }
            }
        },
        error: function (data) {
            if (typeof errorCallback === "function") {
                errorCallback(data);

            }else{
                console.log(data);
                $.notifyMsg(data.message,"danger");
            }
        }
    });
}

//用于表单上传或者data已经组装好的情况下
$.postData = function (url, method, data, callback, isAsync) {
    var async = true;
    if (isAsync == false) {
        async = false;
    }
    $.ajax({
        url: url+method,
        type: 'POST',
        data: data,
        async: true,
        cache: false,
        dataType: "json",
        contentType: false,
        processData: false,
        success: function (data) {
            if (typeof callback === "function") {
                callback(data.result);
            }
        },
        error: function (data) {
            $.notifyMsg(data.message,"danger");
        } 
    });
}

/**
 *
 * @param msg 提示内容
 * @param code　提示种类  danger,warning,success
 * @param callback  回调函数
 */
$.notifyMsg = function (msg,code,callback,errorCallBack,title) { //code 分为
    var modalDemoLabel = $('#modalDemoLabel');
    var myModalLabel = modalDemoLabel.find("[id=myModalLabel]");
    myModalLabel.removeClass("alert-warning").removeClass("alert-danger").removeClass("alert-success");
    switch (code){
        case "danger":
            myModalLabel.addClass("alert-danger");
            break;
        case "warning":
            myModalLabel.addClass("alert-warning");
            break;
        case "success":
            myModalLabel.addClass("alert-success");
            break;
        default://默认success
          /*  $(modalDemoLabel).addClass("alert-success");*/
            break;
    }
    modalDemoLabel.find("[class=modal-body]").html(msg);

    if( typeof callback === "function" || typeof errorCallBack === "function"){
        $(modalDemoLabel).find(".alertMsgSubmit").click(() => callback());
        $(modalDemoLabel).find(".alertMsgClose").click(() => errorCallBack());
        $(modalDemoLabel).find(".close").click(() => errorCallBack());
    }

    modalDemoLabel.addClass('show');

}

/**
 *
 * @param msg 提示内容
 * @param code　提示种类  danger,warning,success
 * @param callback  回调函数
 * @param title 自定义标题
 */
$.notifyMsgWithTitle = function (msg,code,callback,errorCallBack,title) { //code 分为
    $.notifyMsg(msg,code,callback,errorCallBack);
    title&&$('#myModalLabel > div').html(title)
}

/***
 * 查询商品其他信息
 * condition取值：
 * history  历史服务单
 * itemGurentee 商品三包
 * materialGurentee 部件三包
 * insurance 保险
 */

function getOrderItemExrData (serviceType,item,condition,orgId) {
    var queryData = {
         "orderId" : item.orderId,
            "imei" : item.imei,
              "sn" : item.sn,
         "goodsId" : item.goodsId,
        "orgId"    : orgId,
      "serviceType": serviceType,
       "condition" : condition
    };
    var method = "srvServicing.getOrderItemExrData";
    var returnData = null;
    $.postJson(defaultUrl, method, queryData, function (data) {
        if(condition=="history")            returnData = data.serviceHistory;
        if(condition=="itemGurentee")       returnData = data.warranty;
        if(condition=="materialGurentee")   returnData = data.macWarranty;
        if(condition=="insurance")          returnData = data.insurances;

    },false);
    return returnData;
}

/**
 * 获取工单一体页面对象
 * @param sId
 * @param conditions
 * @returns {*} 页面对象
 */
$.getServiceVo = function (sId , conditions) {
    if(!sId) return;
    var queryData = {
        "sId" : sId,
        "conditions" : ($.isEmpty(conditions) ? "" :conditions),
    };
    var method = "srvServicing.getServiceVo";
    var returnData = null;
    $.postJson(defaultUrl, method, queryData, function (data) {
       returnData = data;
    },false);
    return returnData;
}

/***
 *
 * 通过 id的集合获取包含id和name的集合
 */

$.getNameNodes = function (ids , conditions) {
    if(!ids || !conditions) return;
    var queryData = {
        "ids" : ids,
        "conditions" : conditions
    };
    var method = "common.getNameNodes";
    var returnData = [];
    $.postJson(defaultUrl, method, queryData, function (data) {
        returnData = data.nameNodes;
    },false);
    return returnData;
}

/**
 * 判断js对象是否为空
 * @param obj
 * @param name  对象名称
 */
$.checkEmpty = function(obj,name){
    if($.isEmptyObject(obj)){
        $.notifyMsg(name+"参数为空","warning");
    }
}

/**
 * 得到请求基础服务的address实体
 * @param baseAddress
 * @returns {{countryId: (string|*|string|string|string|string), provinceId: (fields.province|{validators}|*|string|number), cityId: (*|string|fields.city|{validators}|number), districtId: *, streetId: (*|fields.street|{validators}|null|string), address: (string|*|string)}}
 */
$.getReqAddress = function(baseAddress){
    var address = {"countryId":baseAddress.country,"provinceId":baseAddress.province,"cityId":baseAddress.city,"districtId":baseAddress.district,"streetId":baseAddress.street,"address":baseAddress.addressDetail};
    return address;
}



/**
 * 判断js对象是否不为空
 * @param val
 * @returns {boolean}  不为空返回true
 */
$.isNotEmpty = function (val) {
    if(val == "" || val == null || typeof val == undefined ){
        return false;
    }
    return true;
}

$.showAvailableModels=function(orgId,macno){
    var queryData = {
        "orgId": orgId,
        "macno": macno
    };
    $.postJson("../afterservice/afterservice!api.action",
        "common.getAvailableModels",
        queryData,
        function (data) {
            if(data.length==0){
                $.notifyMsg("没有查到适用机型","danger");
                return;
            }
            var modelNames = "";
            for (var i = 0 ; i < data.length;i++) {
                modelNames += data[i].name+";   ";
            }
            $.notifyMsg(modelNames,"success");
        }
        ,
        true
    )
}
/**
 * 判断js对象是否为空
 * @param val
 * @returns {boolean}  不为空返回true
 */
$.isEmpty = function (val) {
   return !($.isNotEmpty(val));
}


function detail(sId) {
   parent.createIframeTab('service_info_detail','服务单详情','/afterservice/afterservice!api.action?router=service_info_detail&sId='+sId);
}

/**
 * 取消编辑
 */
function cancelEdit() {
    window.top.closeCurrentTabAndRefreshTab();
}

/**
 * 校验是否是保险维修转换机
 */
$.checkIsRepairTransferInsuranceExchange = function(items) {
    var tarItem = items.filter(function (item) {
        if(!item.methods){
            return false;
        }
        var tarMethod = item.methods.filter(function (method) {
            return method.code == 'WX27' || method.code == 'BH01';
        });
        return tarMethod.length > 0 && item.insuranceEnable == 'YY';
    });
    return tarItem.length > 0;
}

/**
 * 根据正则获取查询主键的类型
 */
$.getQueryType = function(queryKey){
    var orderPatt = /[0-9]{16}/;//订单
    var snPatt = /\d{4,5}[/]\d{8,9}/;//第一种sn 带"/"
    var snPattSnd = /\d{12}/;//第二种sn 12位数字
    var imeiPatt = /86\d{13}/;//imei
    var srvPatt = /AS\d{13}/;//工单
    var saPatt = /SA\d{13}/;//特批单

    if(orderPatt.test(queryKey)){
        return "order";
    }

    else if(snPatt.test(queryKey)){
        return "sn";
    }

    else if(snPattSnd.test(queryKey)){
        return "sn";
    }

    else if(imeiPatt.test(queryKey)){
        return "imei";
    }

    else if(srvPatt.test(queryKey)){
        return "sId";
    }

    else if(saPatt.test(queryKey)){
        return "sa";
    }

    else {
        return null;
    }
}