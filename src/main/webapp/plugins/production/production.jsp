<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <title>生产任务单申请</title>
    <%@include file="../production/mafactory_head.jsp" %>

    <script src="../plugins/js/select-vue.js?v=1.4456"></script>

    <style>
        .info-table th, td {
            max-width: 500px;
            word-wrap: break-word;
            text-overflow: ellipsis;
            white-space: nowrap;
            overflow: hidden;
            width: auto;
        }

        .form-table tbody td:first-child {
            font-weight: bold;
            text-align: right;
        }
    </style>
</head>

<body>
<!-- 面包屑 -->
<ol class="be-breadcrumb breadcrumb">
    <li>
        修整工厂
    </li>
    <li>
        生产任务单管理
    </li>
    <li>
        生产任务单申请
    </li>
    <div style="float: right">
        <button onclick="test()">测试 </button>
        <button class="btn btn-link" onclick="javascript:window.location.reload();">
            <span class="glyphicon glyphicon-refresh"></span>&nbsp;刷新
        </button>
    </div>
</ol>

<input type="hidden" name="redirectUrl" value="${request.contextPath}${request.requestURI}?${request.queryString}">
<div id='prod_order_apply' v-cloak>
    <legend class="sm">生产任务单申请</legend>
    <div class="panel panel-default" role="tabpanel" aria-labelledby="headingOne">
        <div id="collapseOne" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne">
            <div class="panel-body">
                <table class="table form-table" style="max-width: 600px">
                    <tbody>
                    <tr>
                        <td style="width: 150px">申请机构</td>
                        <%--<td>
                            <v-select type="usableFactoryOrg" v-model="prodOrder.orgId" :disabled="!isZongbu"></v-select>
                        </td>--%>
                    </tr>
                    <tr>
                        <td>生产类型</td>
                        <td>
                            <v-select type="prodBizType" v-model="prodOrder.bizType"
                                      @change="onChangeProdBizType"></v-select>
                        </td>
                    </tr>
                    <tr>
                        <td>业务类型</td>
                        <td>
                            <select data-toggle="select" v-select2="" v-model="prodOrder.produceType">
                                <option value="">请选择业务类型</option>
                                <option v-for="type in typeOptions" :value="type.value">
                                    {{type.text}}
                                </option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <td>预投产日期</td>
                        <td>
                            <div class="input-group date" data-toggle="datepicker" >
                                <input type="text" class="form-control"
                                       @blur="prodOrder.expectedStartTime = $event.target.value"/>
                                <span class="input-group-addon"><i class="fa fa-calendar" aria-hidden="true"></i></span>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>预结案日期</td>
                        <td>
                            <div class="input-group date" data-toggle="datepicker">
                                <input type="text" class="form-control"
                                       @blur="prodOrder.expectedFinishTime = $event.target.value"/>
                                <span class="input-group-addon"><i class="fa fa-calendar" aria-hidden="true"></i></span>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>投产物料编号</td>
                        <td>
                            <input type="text" class="form-control"
                                   placeholder="物料编号，输入完回车查询"
                                   @keyup="inputMacno($event)"
                                   v-model.trim="itemMac.macNo"
                                   :disabled="MafactoryUtils.isEmpty(prodOrder.produceType)"/>
                        </td>
                    </tr>
                    <tr>
                        <td>投产物料名称</td>
                        <td>
                            {{itemMac.name}}
                        </td>
                    </tr>
                    <tr>
                        <td>预投产数量</td>
                        <td>
                            <div class="input-group">
                                <input id="inputQuantity" type="number" min="1" :max="itemMac.productableQuantity" v-number-only class="form-control"
                                       placeholder="预投产数量"
                                       v-model.trim="itemMac.quantity"
                                       :disabled="itemMac.productableQuantity === 0"/>
                                <label class="be-input-group-label input-group-addon" for="inputQuantity">可投产数量 {{this.itemMac.productableQuantity}}</label>
                                <span class="input-group-btn">
                                <button class="btn btn-default" type="button"
                                        @click="addItem"
                                        :disabled="itemMac.quantity < 1 || itemMac.quantity > itemMac.productableQuantity">添加</button>
                              </span>
                            </div>
                        </td>
                    </tr>
                    <tr>
                        <td>备注</td>
                        <td>
                            <textarea type="text" class="form-control"
                                      v-model.trim="prodOrder.remark"
                                      maxlength="400"
                            ></textarea>
                        </td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <hr class="hr-condensed">
    <template v-if="debug=='true'">{{prodOrder}}</template>
    <legend class="sm">投产物料明细</legend>
    <div class="panel panel-default" role="tabpanel" aria-labelledby="headingOne">
        <div id="collapseOne" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne">
            <div class="panel-body">
                <table class="table info-table">
                    <thead>
                    <tr>
                        <th>物品编号</th>
                        <th>物品名称</th>
                        <%--<th v-if="prodOrder.bizType == 1">机型</th>--%>
                        <th>预投产数量</th>
                        <th>预出货数量</th>
                        <th>操作</th>
                    </tr>
                    </thead>
                    <tbody>
                    <tr v-for="(item, idx) in prodOrder.items">
                        <td center>{{item.macNo}}</td>
                        <td center>{{item.name}}</td>
                        <%--<td v-if="prodOrder.bizType == 1" center>
                            <div class="form-group" style="display: initial;">
                                <v-select type="availableModel" :params="{orgId: prodOrder.orgId, macno: item.macNo}" v-model="item.thirdModelId"></v-select>-->
                            </div>
                        </td>--%>
                        <td center>{{item.quantity}}</td>
                        <td center>{{Math.round(item.quantity * 0.9)}}</td>
                        <td center><input type="button" class="btn btn-danger" value="删 除" @click="prodOrder.items.splice(idx, 1)"/></td>
                    </tr>
                    </tbody>
                </table>
            </div>
        </div>
    </div>
    <input type="button" :disabled="submitting" class="submitBtn btn btn-primary" value="申 请" @click="apply()">
</div>
<script type="text/javascript">

    function test() {
        var data = {params:'{"ids":"123"}'};
        console.log("--------------------请求相关数据   开始 ---------------------");
        console.log(data);
        console.log("--------------------请求相关数据   结束 ---------------------");
        $.ajax({
            url: "/test",
            type: "POST",
            data: data,
            dataType: "json",
            success: function (data) {
                console.log("--------------------打印后台返回的相关数据 开始--------------------- " );
                console.log(data);
                console.log("--------------------打印后台返回的相关数据 结束--------------------- " );
            },
            error: function (data) {

            }
        });
    }

    new Vue({
        el: '#prod_order_apply',
        data: {
            /* 接口请求数据结构定义 */
            prodOrder: {
                // 申请机构
                orgId: '',
                // 生产业务类型
                bizType: '',
                // 生产类型
                produceType: '',
                // 预投产日期
                expectedStartTime: '',
                // 预结案日期
                expectedFinishTime: '',
                // 投产明细
                items: []
            },
            /* 页面数据绑定 */
            // 生产类型下拉选项
            typeOptions: [],
            // 当前投产物料
            itemMac: {
                macNo: '',
                // thirdModelId: '',
                name: '',
                // 输入的数量
                quantity: 0,
                // 可投产数量
                productableQuantity: 0,
                //临时变量
                macNo_temp: 0
            },
            // 是否提交中
            submitting: false,
            debug: '${debug}'
        },
        computed: {
            productableQuantityMsg: function () {
                var qt = this.itemMac.productableQuantity;
                return qt !== 0 ? '1 ~ ' + qt : '∞'
            }
        },
        created: function () {
            /*this.prodOrder.orgId = isZongbu ? '' : orgId;*/
        },
        methods: {
            // 初始化生产类型
            onChangeProdBizType: function (val) {
                this.prodOrder.produceType = '';
                if (val) {
                    this.typeOptions = val.childs;
                } else {
                    this.typeOptions = [];
                }
            },
            // 当前投产物料变更
            onChangeCurrentMac: function (val) {
                if (!val || val < 1) {
                    this.itemMac.name = '';
                } else {
                    var mac = this.macOptions[val - 1];
                    this.itemMac.name = mac.text;
                }
            },
            // 回车输入物料号完成
            inputMacno: function (event) {
                if (event.keyCode != 13 || MafactoryUtils.isEmpty(this.itemMac.macNo)) return;
                this.initItemMac();
                this.itemMac.macNo_temp = this.itemMac.macNo;
            },
            // 初始化正在添加的物料信息
            initItemMac: function () {
                let self = this;
                const macNo = this.itemMac.macNo;
                this.itemMac.name = '';
                this.itemMac.quantity = this.itemMac.productableQuantity = 0;
                MafactoryUtils.postJson(this, '/productionOrder/getProduceMaterial', {macNo: macNo, bizType: this.prodOrder.bizType}, function (resp) {
                    self.itemMac.name = resp.macname;
                    self.queryProductableQuantity(macNo);
                });
            },
            // 查询该物料可投产最大值
            queryProductableQuantity: function (macNo) {
                var itemMac = this.itemMac;
                if (!macNo) {
                    itemMac.quantity = itemMac.productableQuantity = 0;
                    return;
                }
                $.postJson(defaultUrl, 'productionOrder.productableQuantity', {
                    orgId: this.prodOrder.orgId,
                    macNo: macNo,
                    prodType: this.prodOrder.produceType
                }, function (resp) {
                    itemMac.quantity = itemMac.productableQuantity = resp.quantity;
                });
            },
            // 添加投产物料
            addItem: function () {
                if (this.itemMac.macNo_temp != this.itemMac.macNo) {
                    $.notifyMsg("请回车重新校验物料号", "warning");
                    return;
                }
                if (this.prodOrder.bizType == '1' && this.prodOrder.items.length > 0) {
                    $.notifyMsg("整机业务，只能添加一个物品编号", "warning");
                    return;
                }
                var mac = this.itemMac;
                // 不能重复添加相同物料
                var items = this.prodOrder.items;
                if (items.filter(function (item) {
                            return item.macNo === mac.macNo;
                        }).length > 0) {
                    $.notifyMsg("不能重复添加相同物料", "warning");
                    return;
                }
                this.prodOrder.items.push({macNo: mac.macNo, name: mac.name, quantity: mac.quantity});
                mac.macNo = '';
                mac.name = '';
                mac.productableQuantity = 0;
                mac.quantity = 0;

            },
            // 提交申请
            apply: function () {
                if (!confirm('确认提交吗？') || !this.preconditionApply()) return;
                var self = this;
                this.submitting = true;
                $.postJson(defaultUrl, 'productionOrder.apply', this.prodOrder, function (resp) {
                    successMsg("任务单申请成功，单号：" + resp.id, false);
                }, false, function (data) {
                    self.submitting = false;
                    console.log(data);
                    $.notifyMsg(data.message, "danger");
                });
            },
            // 提交前校验
            preconditionApply: function () {
                var data = this.prodOrder;
                var msg = '';
                if (!data.orgId) msg += '机构、';
                if (!data.bizType) msg += '生产类型、';
                if (!data.produceType) msg += '业务类型、';
                if (!data.expectedStartTime) msg += '预投产日期、';
                if (!data.expectedFinishTime) msg += '预结案日期、';
                if (data.items.length < 1) msg += '投产物品、';
                if (msg) $.notifyMsg(msg.substring(0, msg.length - 1) + ' 不可为空', "warning");
                // 整机业务需要选择三级机型
                // if (data.bizType == 1 && data.items.some(item => MafactoryUtils.isEmpty(item.thirdModelId))) $.notifyMsg('整机业务必须选择三级机型', "warning");
                // 无提示信息验证通过
                return msg.length === 0;
            }
        },
        watch: {
            'prodOrder.orgId': function (val) {
                this.prodOrder.items = [];
                this.queryProductableQuantity(this.itemMac.macNo);
            },
            'prodOrder.produceType': function (val) {
                this.prodOrder.items = [];
                this.itemMac.macNo = '';
                this.queryProductableQuantity(this.itemMac.macNo);
            }
        }
    });
</script>
</body>
</html>
