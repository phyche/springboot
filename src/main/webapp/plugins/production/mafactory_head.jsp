<%@page pageEncoding="utf-8" %>
<html>
<head>
    <meta http-equiv="content-type" content="text/html; charset=utf-8"/>
    <title>生产任务单申请</title>
    <script src="../plugins/js/jquery-1.11.3.min.js"></script>
    <script src="../plugins/js/onemodel.js?v=1.1"></script>
    <script src="../plugins/js/vue.min.js"></script>
    <script src="../plugins/js/vue-i18n.min.js"></script>

</head>
<body>
</body>
<script>
    var _hmt = _hmt || [];
    (function() {
        var hm = document.createElement("script");
        hm.src = "https://hm.baidu.com/hm.js?02f2b1424a5046f7ae2353645198ca13";
        var s = document.getElementsByTagName("script")[0];
        s.parentNode.insertBefore(hm, s);
    })();
</script>
<style type="text/css" rel="stylesheet">
    [v-cloak] { display: none }
    .form-group.select {width:151px}
    .form-group input, .form-group input.form-control, .form-group select, .form-group button {  height: 34px;  }
</style>

<script>
    var orgId = "${admin.orgId}";//操作人所在机构
    var miliao ="${admin.miliao}";//操作人米聊号
    var person = "${admin.name}";//操作人名称
    var isZongbu = "${admin.orgId == 'A10001'}";
    defaultUrl = "../";
    var defaultConfiguration = {
        pageNumber: 1,
        pageSize: 10,
        exportSize: 20000
    };

    function log(msg) {
        console.log(msg);
    }

    /*$('.alert-blank .close,.alert-blank .alertMsgSubmit,.alert-blank .alertMsgClose').click(function (e) {
        $('.alert-blank').removeClass('show');
    });*/

    (function ($) {
        $.fn.serializeJson = function () {
            var serializeObj = {};
            var array = this.serializeArray();
            var str = this.serialize();
            $(array).each(function () {
                if (typeof(serializeObj[this.name]) != 'undefined') {
                    if ($.isArray(serializeObj[this.name])) {
                        serializeObj[this.name].push(this.value);
                    } else {
                        serializeObj[this.name] = [serializeObj[this.name], this.value];
                    }
                } else {
                    serializeObj[this.name] = this.value;
                }
            });
            return serializeObj;
        };
    })(jQuery);

    /**
     *  关闭当前的tab页，并调用传入iframe页的search方法，相当于刷新该页的数据
     **/
    function closeCurTabAndInvokeSearchMethod(iframeId) {
        if (iframeId) {
            $(window.parent.document.getElementById(iframeId).contentWindow.document).find(".search-btn").click();
        }
        $(window.parent.document.getElementById("tab")).find('.curr .tab-close').click();
    }

    /**
     * 提示操作成功并关闭当前页(默认)
     */
    function successMsg(message, stayCurrentPage, callback, iframeId) {
        var msg = message ? message : "操作成功!";
        $.notifyMsg(msg, "success", function () {
            if (!stayCurrentPage) {
                $(window.parent.document.getElementById("tab")).find('.curr .tab-close').click();
            } else {
                if (typeof callback === "function") {
                    callback();
                }
            }
        });
        if (iframeId) {
            $(window.parent.document.getElementById(iframeId).contentWindow.document).find(".search-btn").click();
        }

    }

    function escape2Html(str) {
        var arrEntities = {'lt': '<', 'gt': '>', 'nbsp': ' ', 'amp': '&', 'quot': '"'};
        return str.replace(/&(lt|gt|nbsp|amp|quot);/ig, function (all, t) {
            return arrEntities[t];
        });
    }

    const Objects = {
        // 对象比较
        equals: function (x, y) {
            var in1 = x instanceof Object;
            var in2 = y instanceof Object;
            if (!in1 || !in2) {
                return x === y;
            }
            if (Object.keys(x).length !== Object.keys(y).length) {
                return false;
            }
            for (var p in x) {
                var a = x[p] instanceof Object;
                var b = y[p] instanceof Object;
                if (a && b) {
                    return this.equals(x[p], y[p]);
                }
                else if (x[p] !== y[p]) {
                    return false;
                }
            }
            return true;
        }
    };

    const MafactoryUtils = {
        isEmpty: function (obj) {
            if (!obj) return true;
            if (Array.isArray(obj)) {
                return obj.length < 1;
            }
            if (typeof obj == 'object' && Object.keys(obj) < 1) return true;
            return false;
        },
        isNotEmpty: function (obj) {
            return !this.isEmpty(obj);
        },
        arrUnique: function (arr) {
            var res = [];
            var json = {};
            for (var i = 0; i < arr.length; i++) {
                if (!this.isEmpty(arr[i]) && !json[arr[i]]) {
                    res.push(arr[i]);
                    json[arr[i]] = 1;
                }
            }
            return res;
        },
        // vueObj 需要定义 submitting 属性
        postJson: function (vueObj, method, params, callback, errorCallback) {
            vueObj.submitting = true;
            $.postJson(defaultUrl, method, params, function (resp) {
                vueObj.submitting = false;
                callback(resp);
            }, true, function (data) {
                console.log(data);
                vueObj.submitting = false;
                if (typeof errorCallback == 'function') {
                    errorCallback()
                } else {
                    $.notifyMsg(data.message.replace(/\n/, '<p/>'), "danger");
                }
            });
        },
        // 输入 回车 时执行回调方法
        doIfPressEnter: function (event, callback) {
            if (event.keyCode === 13 && callback instanceof Function) {
                callback();
            }
        },
        // 跳转修整单页面
        handleRepairPage: function (imei) {
            $.ajax({
                url: '../xmsmafactory/mafactory!getHandlePageByStatus.action',
                type: "POST",
                dataType: "json",
                data: {"imei": imei},
                success: function (json) {
                    if (json.code == '1') {
                        var res = JSON.parse(json.result);
                        var enumStatus = JSON.parse(res.enumStatus);
                        if (!enumStatus['viewName']) {
                            $.notifyMsg('单子当前状态[' + enumStatus.description + ']，无后续流程。', 'warning');
                            return;
                        }
                        // 打开检测处理页，并关闭接收页
                        parent.createIframeTab(enumStatus.viewName, enumStatus.description, '../xmsmafactory/mafactory!api.action?router=' + enumStatus.viewName + '&id=' + res.id);
                        return true;
                    } else {
                        $.notify({
                            message: json.message
                        }, {
                            type: 'danger'
                        });
                        return false;
                    }
                }
            });
        },
        notify: function (type, msg) {
            $.notify({
                message: msg
            }, {
                type: type,
                delay: 2000,
                placement: {
                    align: 'center'
                }
            });
        }
    };

    // vue、select2 适配指令
    Vue.directive('select2', {
        inserted: function (el, binding, vnode) {
            // select2 会将 options.data 初始化为下拉选项
            /*$(el).select2().on("select2:select", function (e) {
                // 对于 select 标签 v-model 监听 change 事件，并且将 event.target.value 赋值给模型
                el.dispatchEvent(new Event('change', { target: e.target }));
            });*/
        },
        update: function (el, binding, vnode) {
            // $(el).trigger("change");
            // 获取选中 option 的索引，
            if (binding.value && binding.value.onChangeSelectedIndex) {
                binding.value.onChangeSelectedIndex(el.selectedIndex);
            }
            // vue 渲染完成后再渲染 select2
            //Vue.nextTick(() => $(el).select2());
        }
    });
    /*Vue.directive('number-only', {
        bind: function (el) {
            el.addEventListener('keyup', () => {
                el.value = el.value.replace(/\D+/, '');
        });
        },
        unbind: function (el) {
            el.removeEventListener('keyup', el.handler);
        }
    })*/

</script>
</body>
</html>