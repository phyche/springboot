/**
 * create by lichengzhen in 18-5-3
 *
 * 修整工厂-下拉选择组件
 *
 * 怎么用？
 * <v-select type="usableFactoryOrg" v-model="prodOrder.bizType" @change="onChangeProdBizType"></v-select>
 * type 必传
 * v-model 自动绑定选中值
 * @change 可以获取选中 option 对象，可从中获取 text 或其他后台传来的数据
 *
 * 新增下拉框类型？
 * data.config 中定义新的选项数据源
 */
Vue.component('v-select', Vue.extend({
    template:
        '<select v-if="multiplesel && multiplesel==1" class="form-control" data-toggle="select" v-select2="{onChangeSelectedIndex: onChangeSelectedIndex}" multiple :disabled="disabled">\n' +
        '    <option value="" >{{getPlaceholder()}}</option>\n' +
        '    <option v-for="opt in options" :value="getValue(opt)">\n' +
        '        {{getText(opt)}}\n' +
        '    </option>\n' +
        '</select>'+

        '<select v-else class="form-control" data-toggle="select" v-model="value" v-select2="{onChangeSelectedIndex: onChangeSelectedIndex}" :disabled="disabled">\n' +
        '    <option value="" selected>{{getPlaceholder()}}</option>\n' +
        '    <option v-for="opt in options" :value="getValue(opt)">\n' +
        '        {{getText(opt)}}\n' +
        '    </option>\n' +
        '</select>'
    ,
    props: {
        value: '',
        type: '',
        placeholder: '',
        selected: {
            default: ""
        },
        params: {
            default: {}
        },
        multiplesel: "0"
    },
    data: function () {
        return {
            options: [],
            selectedIndex: 0,
            lastParams: undefined,
            disabled: false,
            config: {
                // 总部人员可使用全部工厂机构，其他仅限制本机构
                usableFactoryOrg: {
                    placeholder: '请选择机构',
                    url: defaultUrl,
                    method: 'common.listUsableFactoryOrg',
                    getText: function (opt) {
                        return opt.text + "（" + opt.value + "）";
                    }
                },
                // 生产单业务类型
                prodBizType: {
                    placeholder: '请选择生产类型',
                    url: defaultUrl,
                    params: {ids: "123"},
                    method: 'listProdOrderBizType',
                    getEmitChangeValue: function(opt) {
                        return {value: opt.value, text: opt.text, childs: opt.childs};
                    }
                },
                // 三级机型列表
                thirdModel: {
                    placeholder: '请选择三级机型',
                    url: defaultUrl,
                    params: {operator: miliao},
                    method: 'common.listThirdModel',
                    getText: function(opt) {
                        return opt.text + "（" + opt.value + "）";
                    }
                },
                // 适用机型
                availableModel: {
                    placeholder: '请选择三级机型',
                    method: 'common.listAvailableModel'
                },
                // 故障描述
                faultDesc: {
                    type: 'constant',
                    placeholder: 'TODO 请输入实际检测故障代码或名称',
                    options: [{value: 'TODO'}]
                },
                // 无法修复原因
                cannotRepairReason: {
                    type: 'constant',
                    placeholder: '请选择无法修复原因',
                    options: [{value: '难修'}, {value: '人为损'}, {value: '主板变形'}, {value: '主板进液腐蚀'}, {value: '焊盘掉'},
                        {value: '焊盘连点'}, {value: '主板划伤'}, {value: '主板损坏'}, {value: '主板吹糊'}, {value: '主板功能区镀锡'}, {value: '主板鼓包'}, {value: '主板断线'}]
                },
                // 写号失败原因
                writeNoFaultReason: {
                    type: 'constant',
                    placeholder: '请选择原因',
                    options: [{value: 1, text: '“刷工厂包”故障'}, {value: 2, text: '“ADB清除”故障'}, {value: 3, text: '“功能检测”故障'},
                        {value: 4, text: '“写IMEI & 烧号 & 绑定FSN与IMEI”故障'}, {value: 5, text: '良品官翻需重新维修'}, {value: 6, text: '其他'}]
                },
                // 初检不合格原因
                firstQcUnqualifiedReason: {
                    type: 'constant',
                    placeholder: '请选择不合格原因',
                    options: [{text: '其它(写号站)'},{text: '外观问题---前屏（质检站）'},{text: '外观问题---后盖/电池后盖（质检站）'},{text: '外观问题---前置摄像头（质检站）'},
                        {text: '外观问题---后置摄像头（质检站）'},{text: '外观问题---螺钉（质检站）'},{text: '外观问题---异物（质检站）'},{text: '外观问题---其它（质检站）'},
                        {text: '不开机（质检站）'},{text: '死机重启故障（质检站）'},{text: '信号故障（质检站）'},{text: '显示故障（质检站）'},{text: '触摸屏故障（质检站）'},
                        {text: '传感器故障（质检站）'},{text: '前置摄像故障（质检站）'},{text: '后置摄像故障（质检站）'},{text: '指纹识别故障（质检站）'},{text: '听筒故障（质检站）'},
                        {text: '扬声器故障（质检站）'},{text: '按键故障（质检站）'},{text: '耳机故障（质检站）'},{text: 'WIFI故障（质检站）'},{text: '蓝牙故障（质检站）'},
                        {text: '红外故障（质检站）'},{text: '充电故障（质检站）'},{text: '内外IMEI信息不一致（质检站）'},{text: '内外FSN信息不一致（质检站）'},{text: '其它（质检站）'},
                        {text: '压感屏校准失败（校准站）'},{text: '指纹校准失败（校准站）'},{text: '其它（校准站）'}]
                },
                finalQcUnqualifiedReason: {
                    type: 'constant',
                    placeholder: '请选择不合格原因',
                    options: [{text: '外观问题---前屏（质检站）'},{text: '外观问题---后盖/电池后盖（质检站）'},{text: '外观问题---前置摄像头（质检站）'},
                        {text: '外观问题---后置摄像头（质检站）'},{text: '外观问题---螺钉（质检站）'},{text: '外观问题---异物（质检站）'},{text: '外观问题---其它（质检站）'},
                        {text: '不开机（质检站）'},{text: '死机重启故障（质检站）'},{text: '信号故障（质检站）'},{text: '显示故障（质检站）'},{text: '触摸屏故障（质检站）'},
                        {text: '传感器故障（质检站）'},{text: '前置摄像故障（质检站）'},{text: '后置摄像故障（质检站）'},{text: '指纹识别故障（质检站）'},{text: '听筒故障（质检站）'},
                        {text: '扬声器故障（质检站）'},{text: '按键故障（质检站）'},{text: '耳机故障（质检站）'},{text: 'WIFI故障（质检站）'},{text: '蓝牙故障（质检站）'},
                        {text: '红外故障（质检站）'},{text: '充电故障（质检站）'},{text: '电量问题（质检站）'},{text: '网标问题（质检站）'},{text: '内外IMEI信息不一致（质检站）'},
                        {text: '内外FSN信息不一致（质检站）'},{text: '其它（质检站）'}]
                },
                // 维修单查询工程师类型
                repairEngineerType: {
                    type: 'constant',
                    placeholder: '请选择工程师类型',
                    options: [{value: 'check', text:'检测工程师'},{value: 'repair', text: '维修工程师'},{value: 'write', text: '写号工程师'},{value: 'currEngineer', text: '当前责任人'}]
                },
                // 维修单查询时间类型
                repairTimeType: {
                    type: 'constant',
                    placeholder: '请选择时间类型',
                    options: [{value: 'create', text:'创建时间'},{value: 'engineerReceive', text: '工程师接收时间'},{value: 'check', text: '检测时间'},{value: 'repair', text:'维修时间'},
                        {value: 'write', text: '写号时间'},{value: 'firstCheck', text: '初检时间'},{value: 'finalCheck', text: '终检时间'},{value: 'finish', text: '完成时间'}]
                },
                // 维修用料查询时间类型
                repairMacTimeType: {
                    type: 'constant',
                    placeholder: '请选择时间类型',
                    options: [{value: 'create', text:'修整单创建时间'},{value: 'lastReturn', text: '最后返还时间'}]
                },
                // 维修用料是否返还类型
                repairIsReturnType: {
                    type: 'constant',
                    placeholder: '请选择返还状态',
                    options: [{value: 'true', text: '全部归还'},{value: 'false', text: '未全部归还'}]
                },
                // 根据维修物料的机型查询bom信息
                newMacList: {
                    placeholder: '请选择新待修品',
                    url: defaultUrl,
                    method: 'repair.getNewMacList'
                },
                // 根据维修物料的机型查询bom信息
                repairUseMac: {
                    placeholder: '请选择物料',
                    url: defaultUrl,
                    method: 'repair.getModelBomMacAndNameByMacno'
                },
                // 根据维修物料的机型查询bom信息的位置号集合
                getLocationByMacno: {
                    placeholder: '请选择位置号',
                    url: defaultUrl,
                    method: 'common.getLocationByMacno'
                },
                // 快速维修维修用料申请列表
                fastRepairMacApply: {
                    placeholder: '请选择物料',
                    url: defaultUrl,
                    method: 'common.getModelBomMacAndNameByMacnos'
                },
                // 根据维修用料查询其替换料
                repairUseMacReplaceNos: {
                    placeholder: '请选择物料',
                    url: defaultUrl,
                    method: 'common.getMacBomByApplyMacno'
                },
                // IMEI 品相
                imeiPhase: {
                    type: 'constant',
                    placeholder: '请选择品相',
                    options: [{value: 'A'},{value: 'B'},{value: 'C'},{value: 'C1'},{value: 'C2'},{value: 'D'}]
                },
                storage: {
                    placeholder: '请选择库',
                    url: defaultUrl,
                    method: 'storage.listStorage'
                },
                section: {
                    placeholder: '请选择区',
                    url: defaultUrl,
                    method: 'storage.listSection'
                },
                user: {
                    placeholder: '当前负责人',
                    url: defaultUrl,
                    method: 'common.listUser',
                    getText: function (opt) {
                        return opt.text + "/" + opt.value;
                    }
                }
            }
        }
    },
    mounted: function () {
        if (!this.params.delay) {
            this.init();
        }
    },
    methods: {
        init: function () {
            var target = this.config[this.type];
            if (target.type === 'constant') {
                this.options = target.options;
            } else {
                const self = this;
                var req = $.extend({}, target.params, this.params);
                const url = target.url ? target.url : defaultUrl;
                $.postJson(url, target.method, req, function (data) {
                    console.log(data)
                    self.param = req;
                    if (target.getOptions instanceof Function) {
                        self.options = target.getOptions(data);
                    } else {
                        self.options = data;
                    }
                });
            }
            this.lastParams = this.params;
        },
        getPlaceholder: function () {
            const cfg = this.config[this.type];
            return this.placeholder ? this.placeholder : cfg.placeholder;
        },
        getValue: function (opt) {
            var cfg = this.config[this.type];
            if (cfg.getValue instanceof Function) {
                return cfg.getValue(opt);
            } else {
                return opt.value ? opt.value : opt.text;
            }
        },
        getText: function(opt) {
            var cfg = this.config[this.type];
            if (cfg.getText instanceof Function) {
                return cfg.getText(opt);
            } else {
                return opt.text ? opt.text : opt.value;
            }
        },
        // 监听 select2，获取被选中 option 的索引
        onChangeSelectedIndex: function (val) {
            this.selectedIndex = val;
        },
        // 获取抛 change 事件时传递的值
        getEmitChangeValue: function (opt) {
            var cfg = this.config[this.type];
            if (!opt) return;
            return cfg.getEmitChangeValue instanceof Function ? cfg.getEmitChangeValue(opt) : {value: opt.value, text: opt.text};
        }
    },
    watch: {
        params: function (val) {
            if (Objects.equals(val, this.lastParams)) return;
            this.lastParams = val;
            this.value = '';
            this.options = [];
            this.init();
        },
        value: function (val) {
            // 为了组件外使用 v-model
            this.$emit('input', val);
        },
        // 获取选中 option 的索引
        selectedIndex: function (val) {
            // 不要直接传 options 里的元素，这个元素是带有 getter、setter 方法，外部操作容易引起难排查的 bug
            // this.$emit('change', this.options[val - 1]);
            var opt = this.options[val - 1];
            this.$emit('change', this.getEmitChangeValue(opt));
        },
        options: function (options) {
            // 只有一个元素时默认选中，其他情况回复缺省值
            if (options.length == 1) {
                this.value = this.getValue(options[0]);
                this.disabled = true;
            } else {
                this.value = '';
                this.disabled = false;
            }
        }
    }
}));