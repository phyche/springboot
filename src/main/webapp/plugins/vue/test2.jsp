<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
    <link rel="stylesheet" href="https://unpkg.com/element-ui/lib/theme-chalk/index.css">
    <script src="https://unpkg.com/element-ui/lib/index.js"></script>
</head>
<body>
<div id='prod_order_apply' >
<template>
    <div>
        <el-input placeholder="请输入内容" v-model="keywords" style="padding-bottom:10px;"></el-input>
        <el-table ref="multipleTable" :data="tableData" border style="width: 100%" @selection-change="handleSelectionChange">
            <%--<el-table-column type="selection" width="60"></el-table-column>--%>
            <el-table-column prop="username" label="用户名" width="180" <%--sortable--%>></el-table-column>
            <el-table-column prop="password" label="密码" <%--show-overflow-tooltip--%>></el-table-column>
            <el-table-column prop="pic" label="头像" width="180"></el-table-column>
           <%-- <el-table-column prop="eRange" label="经营范围" width="180"></el-table-column>
            <el-table-column prop="eModel" label="经营模式" width="180"></el-table-column>
            <el-table-column prop="createTime" label="创建日期" width="180"></el-table-column>
            <el-table-column prop="updateTime" label="更新日期" width="180"></el-table-column>
            <el-table-column prop="recordStatus" label="企业状态" width="180"></el-table-column>--%>
        </el-table>
        <el-pagination background
                       @size-change="handleSizeChange"
                       @current-change="handleCurrentChange"
                       :current-page="currentPage"
                       :page-sizes="[2, 5, 10, 20]"
                       :page-size="pageSize"
                       layout="total, sizes, prev, pager, next, jumper"
                       :total="total">
        </el-pagination>
    </div>
</template>
</div>
</body>
<script src="https://cdn.jsdelivr.net/npm/axios@0.12.0/dist/axios.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/lodash@4.13.1/lodash.min.js"></script>
<script>

    new Vue({
        el: '#prod_order_apply',
        data: {
            keyword: "",
            total: 5,
            currentPage: 1,
            pageSize: 10,
            tableData: [{
                /*eNumber: 'A10001',*/
                username: '',
                password: '',
                pic: ''/*,
                eModel: '国有企业',
                createTime: '2017-03-27',
                updateTime: '2016-03-27',
                recordStatus: '1'*/
            }],
            multipleSelection: []
        },
        created: function(){
            // 组件创建完后获取数据，
            // 此时 data 已经被 observed 了
            this.fetchData();
        },
        methods: {
            /*toggleSelection:function(rows) {
                if (rows) {
                    rows.forEach(function(row)  {
                        this.$refs.multipleTable.toggleRowSelection(row);
                    });
                } else {
                    this.$refs.multipleTable.clearSelection();
                }
            },
            handleSelectionChange:function(val) {
                this.multipleSelection = val;
            },*/
            callbackFunction:function(result) {
                alert(result + "aaa");
            },
            fetchData:function(){ //获取数据
                $.ajax({
                    url: "/getList",
                    type: "POST",
                    data: {//跨域请求数据
                        params: {
                            keywords:this.keyword//输入的关键词
                        }
                        },
                    dataType: "json",
                    success: function (data) {
                        console.log("--------------------打印后台返回的相关数据 开始--------------------- " );
                        console.log(data);
                        this.total = res.body.count;
                        this.currentPage = res.body.curr;
                        this.tableData = res.body.data;
                        console.info(res);
                        console.log("--------------------打印后台返回的相关数据 结束--------------------- " );
                    },
                    error: function (data) {
                        alert("res.status:"+res.status)
                    }
                })

                /*this.$.jsonp("http://localhost:8086/getList",{//跨域请求数据
                    params: {
                        keywords:this.keyword//输入的关键词
                    },
                    jsonpCallback:'callbackFunction'//这里是callback
                }).then(function(res) {//请求成功回调，请求来的数据赋给searchList数组
                    this.total = res.body.count;
                    this.currentPage = res.body.curr;
                    this.tableData = res.body.data;
                    console.info(res);
                },function(res) {//失败显示状态码
                    alert("res.status:"+res.status)
                })*/
            },
            handleSizeChange:function(val){
                this.pageSize = val;
                this.currentPage = 1;
                this.fetchData(1, val);

            },
            handleCurrentChange:function(val){
                this.currentPage = val;
                this.fetchData(val, this.pageSize);

            }

        }
    });
</script>
<style>
    .el-table th {
        text-align: center;
    }

    .el-table tbody tr td:first-child {
        text-align: center;
    }
</style>
</html>