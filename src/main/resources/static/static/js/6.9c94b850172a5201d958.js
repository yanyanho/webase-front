webpackJsonp([6],{"jcw+":function(e,t){},sOfO:function(e,t,s){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var a=s("P9l9"),n=s("yt7g"),i=s("YaEn"),o={name:"depositAsset",data:function(){return{assetGetObj:null,assetGetName:"",assetGet:"",amountGet:0,showPicker:!1,assetGiveObj:null,assetGiveName:"",assetGive:"",amountGive:0,showPicker1:!1,time:Object(n.a)(new Date((new Date).getTime()+72e5)),showTime:!1,newAssetList:[],assetList:JSON.parse(localStorage.getItem("changeAssetList")),currentTime:Object(n.a)(new Date((new Date).getTime()+72e5)),htlcExchangeList:[],assetGetBalance:0,assetGetBalanceShow:!1,assetGiveBalance:0,assetGiveBalanceShow:!1}},mounted:function(){this.getHtlcExchange(),this.getAssets()},methods:{onConfirm:function(e){var t=this;this.assetGetObj=e,this.showPicker=!1,this.assetGet=e.shortName;var s={exchangeContractAddress:e.exchangeContractAddress,userAddress:e.key,groupId:e.groupId,minUnit:e.minUnit,assetAddress:e.contractAddress};Object(a.f)(s).then(function(e){200===e.status?(e.data||0==e.data)&&(t.assetGetBalance=e.data,t.assetGetBalanceShow=!0):(t.$notify({type:"warning",message:e.data.message}),t.assetGetBalance=0,t.assetGetBalanceShow=!0)}).catch(function(e){t.$notify({type:"danger",message:"系统错误"}),t.assetGetBalance=0,t.assetGetBalanceShow=!0})},onConfirm1:function(e){var t=this;this.assetGiveObj=e,this.showPicker1=!1,this.assetGive=e.shortName;var s={exchangeContractAddress:e.exchangeContractAddress,userAddress:e.key,groupId:e.groupId,minUnit:e.minUnit,assetAddress:e.contractAddress};Object(a.f)(s).then(function(e){200===e.status?(e.data||0==e.data)&&(t.assetGiveBalance=e.data,t.assetGiveBalanceShow=!0):(t.$notify({type:"warning",message:e.data.message}),t.assetGiveBalance=0,t.assetGiveBalanceShow=!0)}).catch(function(e){t.$notify({type:"danger",message:"系统错误"}),t.assetGiveBalance=0,t.assetGiveBalanceShow=!0})},checkTime:function(e){this.time=e,this.showTime=!1},submit:function(){this.assetGet?this.amountGet?this.assetGive?this.amountGive?this.setdeposit():this.$notify({type:"warning",message:"请输入转入资产额度"}):this.$notify({type:"warning",message:"请选择转入资产"}):this.$notify({type:"warning",message:"请输入转出资产额度"}):this.$notify({type:"warning",message:"请输入转出资产"})},getHtlcExchange:function(){var e=this;Object(a.o)().then(function(t){var s=t.status,a=t.data;200===s?a&&(e.htlcExchangeList=a):e.$notify({type:"warning",message:t.data.message})}).catch(function(t){e.$notify({type:"danger",message:"系统错误"})})},getAssets:function(){if(this.assetList&&this.assetList.length)for(var e=0;e<this.assetList.length;e++)this.assetList[e].key==localStorage.getItem("userAddress")&&this.newAssetList.push(this.assetList[e])},setdeposit:function(){var e=this,t="";this.htlcExchangeList.forEach(function(s){s.groupId==e.assetGetObj.groupId&&(t=s.exchangeContractAddress)});var s={userAddress:localStorage.getItem("userAddress"),groupId:this.assetGetObj.groupId,exchangeContractAddress:t},o=Object(n.d)()+this.time+":00",c={amountGet:this.amountGet,assetGetShortName:this.assetGetObj.shortName,assetGet:this.assetGetObj.contractAddress,amountGive:this.amountGive,assetGive:this.assetGiveObj.contractAddress,assetGiveShortName:this.assetGiveObj.shortName,expires:new Date(o).getTime(),assetGetMinUnit:this.assetGetObj.minUnit,assetGiveMinUnit:this.assetGiveObj.minUnit};Object(a.i)(s,c).then(function(t){e.loading=!1;var s=t.status;t.data;200===s?(e.$notify({type:"success",message:"挂单成功！"}),i.a.push("/orderList")):e.$notify({type:"warning",message:t.data.message})}).catch(function(t){e.$notify({type:"danger",message:"系统错误"})})}}},c={render:function(){var e=this,t=e.$createElement,s=e._self._c||t;return s("div",{staticClass:"module"},[s("van-cell-group",[s("van-field",{attrs:{readonly:"",clickable:"",label:"买入资产",value:e.assetGet,placeholder:"选择买入资产"},on:{click:function(t){e.showPicker=!0}}}),e._v(" "),e.assetGetBalanceShow?s("div",{staticStyle:{padding:"5px 0 5px 15px",color:"#ccc","font-size":"12px"}},[s("span",[e._v("资产剩余额度："+e._s(e.assetGetBalance))])]):e._e(),e._v(" "),s("van-field",{attrs:{type:"number",label:"买入额度",placeholder:"请买入额度",required:"",maxLength:"15"},model:{value:e.amountGet,callback:function(t){e.amountGet=t},expression:"amountGet"}}),e._v(" "),s("van-field",{attrs:{readonly:"",clickable:"",label:"卖出资产",value:e.assetGive,placeholder:"选择卖出资产"},on:{click:function(t){e.showPicker1=!0}}}),e._v(" "),e.assetGiveBalanceShow?s("div",{staticStyle:{padding:"5px 0 5px 15px",color:"#ccc","font-size":"12px"}},[s("span",[e._v("资产剩余额度："+e._s(e.assetGiveBalance))])]):e._e(),e._v(" "),s("van-field",{attrs:{type:"number",label:"卖出额度",placeholder:"请卖出额度",required:"",maxLength:"15"},model:{value:e.amountGive,callback:function(t){e.amountGive=t},expression:"amountGive"}}),e._v(" "),s("van-field",{attrs:{label:"过期时间",placeholder:"请选择过期时间",required:""},on:{click:function(t){e.showTime=!0}},model:{value:e.time,callback:function(t){e.time=t},expression:"time"}})],1),e._v(" "),s("van-popup",{attrs:{position:"bottom"},model:{value:e.showPicker,callback:function(t){e.showPicker=t},expression:"showPicker"}},[s("van-picker",{attrs:{"show-toolbar":"",columns:e.newAssetList,"value-key":"shortName"},on:{cancel:function(t){e.showPicker=!1},confirm:e.onConfirm}})],1),e._v(" "),s("van-popup",{attrs:{position:"bottom"},model:{value:e.showTime,callback:function(t){e.showTime=t},expression:"showTime"}},[s("van-datetime-picker",{attrs:{type:"time"},on:{cancel:function(t){e.showTime=!1},confirm:e.checkTime},model:{value:e.currentTime,callback:function(t){e.currentTime=t},expression:"currentTime"}})],1),e._v(" "),s("van-popup",{attrs:{position:"bottom"},model:{value:e.showPicker1,callback:function(t){e.showPicker1=t},expression:"showPicker1"}},[s("van-picker",{attrs:{"show-toolbar":"",columns:e.newAssetList,"value-key":"shortName"},on:{cancel:function(t){e.showPicker1=!1},confirm:e.onConfirm1}})],1),e._v(" "),s("div",{staticClass:"cross-bottom"},[s("van-button",{staticStyle:{width:"100%"},attrs:{type:"default"},on:{click:e.submit}},[e._v("确定")])],1)],1)},staticRenderFns:[]};var r=s("VU/8")(o,c,!1,function(e){s("jcw+")},"data-v-c96c5476",null);t.default=r.exports}});