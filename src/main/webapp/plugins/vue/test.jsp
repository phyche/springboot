<!DOCTYPE html>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>
    <script src="../plugins/js/jquery-1.11.3.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/vue/dist/vue.js"></script>
    <link rel="stylesheet" href="https://unpkg.com/element-ui/lib/theme-chalk/index.css">
    <script src="https://unpkg.com/element-ui/lib/index.js"></script>
    <script src="https://unpkg.com/axios/dist/axios.min.js"></script>
</head>
<body>
<div id='prod_order_apply' >

    <div>
        <el-col :span="6" ><el-input placeholder="请输入内容" v-model="keywords" style="padding-bottom:10px;"></el-input></el-col>
        <el-table ref="multipleTable" :data="tableData" border style="width: 100%" @selection-change="handleSelectionChange">
            <el-table-column prop="username" label="用户名" width="180" sortable></el-table-column>
            <el-table-column prop="password" label="密码"></el-table-column>
            <el-table-column prop="pic" label="头像" width="180">
                <template slot-scope="scope">
                    <img  :src="scope.row.pic" alt="" style="width: 50px;height: 50px">
                </template>
            </el-table-column>
        </el-table>
        <el-pagination background
                       @size-change="handleSizeChange"
                       @current-change="handleCurrentChange"
                       :current-page="currentPage"
                       :page-sizes="[2, 5, 10, 20]"
                       :page-size="pageSize"
                       layout="total, sizes, prev, pager, next, jumper"
                       :total="total"
                       :current-page.sync = "currentPage">
        </el-pagination>
    </div>

</div>
</body>
<script src="https://cdn.jsdelivr.net/npm/axios@0.12.0/dist/axios.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/lodash@4.13.1/lodash.min.js"></script>
<script>

    var vue = new Vue({
        el: '#prod_order_apply',
        data: {
            keywords: "",
            total: 5,
            currentPage: 1,
            pageSize: 2,
            tableData: [{
                id: '1111',
                username: '1111',
                password: '1',
                pic: 'http://localhost:8086/images/advertisement/63498178-25b3-4d87-aca5-0f672bda85c2-medium.jpg'
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
            },*/
            handleSelectionChange:function(val) {
                this.multipleSelection = val;
            },
            callbackFunction:function(result) {
                alert(result + "aaa");
            },
            fetchData:function(){ //获取数据
                /*this.loading = true;
                $.ajax({
                    url: "/getList",
                    type: "POST",
                    data: {//跨域请求数据
                        keywords:this.keywords,//输入的关键词
                        curr:this.currentPage,
                        pageSize:this.pageSize
                    },
                    dataType: "json",
                    success: function (data) {
                        console.log("--------------------打印后台返回的相关数据 开始--------------------- " );
                        console.log(data);
                        this.total = data.count;
                        this.currentPage = data.curr;
                        this.tableData = data.data;
                        this.loading = false;
                        console.log(this);
                        console.log("--------------------打印后台返回的相关数据 结束--------------------- " );
                    },
                    error: function (data) {
                        alert("res.status:"+data.status)
                    }
                })*/
                var _this = this   //很重要！！
                axios.get('/getList',{keywords:this.keywords})
                    .then(function (res) {
                        console.log(res);
                        _this.tableData = res.data.data;
                        _this.total = res.data.count;
                        _this.currentPage = res.data.curr;
                    })
                    .catch(function (error) {
                        console.log(error);
                    });
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