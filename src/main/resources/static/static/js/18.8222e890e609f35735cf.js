webpackJsonp([18],{P6aS:function(t,e){},t08k:function(t,e,s){"use strict";Object.defineProperty(e,"__esModule",{value:!0});var a=s("mvHQ"),n=s.n(a),i=s("P9l9"),r=s("YaEn"),o={name:"addAsset",data:function(){return{assetType:"BAC001",groupList:[],groupId:"1",assetData:null,assetName:"",minUnit:0,totalAmount:0,description:""}},mounted:function(){},methods:{getGroupList:function(){var t=this;Object(i.r)().then(function(e){200==e.status?e.data&&e.data.length&&(t.groupList=e.data,t.groupId=e.data[0]):t.$notify({type:"warning",message:e.data.message})}).catch(function(e){t.$notify({type:"danger",message:"系统错误"})})},submit:function(){var t=this,e={userAddress:localStorage.getItem("userAddress"),groupId:this.groupId},s={shortName:this.assetName,minUnit:parseInt(this.minUnit),totalAmount:parseInt(this.totalAmount),description:this.description};Object(i.o)(e,s).then(function(e){if(200==e.status){var s={shortName:t.assetName,minUnit:parseInt(t.minUnit),totalAmount:parseInt(t.totalAmount),description:t.description,groupId:t.groupId,contractAddress:e.data,contractName:"BAC001",key:localStorage.getItem("userAddress")};t.getBalanceData(s)}else t.$notify({type:"warning",message:e.data.message})}).catch(function(e){t.$notify({type:"danger",message:"系统错误"})})},getBalanceData:function(t){var e=this,s={userAddress:localStorage.getItem("userAddress"),groupId:this.groupId,contractAddress:t.contractAddress};Object(i.k)(s).then(function(s){200===s.status?(s.data||0==s.data)&&(t.balance=s.data,e.getAssets(t)):e.$notify({type:"warning",message:s.data.message})}).catch(function(t){e.$notify({type:"danger",message:"系统错误"})})},getAssets:function(t){var e=0;this.assetList=JSON.parse(localStorage.getItem("assetList"))||[];for(var s=0;s<this.assetList.length;s++)this.assetList[s].contractAddress==t.contractAddress&&this.assetList[s].key==localStorage.getItem("userAddress")&&(this.assetList[s].contractAddress=t.contractAddress,this.assetList[s].contractName=t.contractName,this.assetList[s].description=t.description,this.assetList[s].groupId=t.groupId,this.assetList[s].minUnit=t.minUnit,this.assetList[s].shortName=t.shortName,this.assetList[s].status=t.status,this.assetList[s].totalAmount=t.totalAmount,this.assetList[s].key=localStorage.getItem("userAddress"),e++);if(0==e){var a={key:localStorage.getItem("userAddress"),contractAddress:t.contractAddress,contractName:t.contractName,description:t.description,groupId:t.groupId,minUnit:t.minUnit,shortName:t.shortName,status:t.status,totalAmount:t.totalAmount};this.assetList.push(a)}localStorage.setItem("assetList",n()(this.assetList)),r.a.push("/home")}}},c={render:function(){var t=this,e=t.$createElement,s=t._self._c||e;return s("div",{staticClass:"module"},[s("van-cell-group",[s("van-field",{attrs:{required:"",clearable:"",label:"资产名称",placeholder:"请输入资产名称"},model:{value:t.assetName,callback:function(e){t.assetName=e},expression:"assetName"}}),t._v(" "),s("van-field",{attrs:{type:"number",required:"",clearable:"",label:"资产精度",placeholder:"请输入资产精度"},model:{value:t.minUnit,callback:function(e){t.minUnit=e},expression:"minUnit"}}),t._v(" "),s("van-field",{attrs:{type:"digit",required:"",clearable:"",label:"发行总量",placeholder:"请输入发行总量"},model:{value:t.totalAmount,callback:function(e){t.totalAmount=e},expression:"totalAmount"}}),t._v(" "),s("van-field",{attrs:{required:"",clearable:"",label:"备注",placeholder:"请输入备注"},model:{value:t.description,callback:function(e){t.description=e},expression:"description"}})],1),t._v(" "),s("van-button",{staticStyle:{width:"100%"},attrs:{type:"default"},on:{click:t.submit}},[t._v("确定")])],1)},staticRenderFns:[]};var d=s("VU/8")(o,c,!1,function(t){s("P6aS")},"data-v-0ab699f0",null);e.default=d.exports}});