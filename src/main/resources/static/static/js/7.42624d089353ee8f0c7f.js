webpackJsonp([7],{"/88X":function(t,s){},MW9V:function(t,s,e){"use strict";Object.defineProperty(s,"__esModule",{value:!0});var a=e("P9l9"),r=e("YaEn"),i={name:"transfer",data:function(){return{address:"",account:0,data:"",assetAddress:this.$route.query.assetAddress,assetData:null,defaultAssetList:JSON.parse(localStorage.getItem("defaultAssetList"))||[],assetList:[]}},mounted:function(){this.getData()},methods:{getData:function(){this.assetList=JSON.parse(localStorage.getItem("assetList"))||[],this.assetList=this.assetList.concat(this.defaultAssetList);for(var t=0;t<this.assetList.length;t++)this.assetList[t].contractAddress!=this.assetAddress||this.assetList[t].key!=localStorage.getItem("userAddress")&&this.assetList[t].owner!=localStorage.getItem("userAddress")||(this.assetData=this.assetList[t])},submit:function(){var t=this;if(this.account.split(".")[1].length>this.assetData.minUnit)this.$notify({type:"warning",message:"转账精度超过资产精度"});else{var s={contractAddress:this.assetData.contractAddress,contractName:this.assetData.contractName,groupId:this.assetData.groupId},e={from:localStorage.getItem("userAddress"),to:this.address,value:parseFloat(this.account),minUnit:this.assetData.minUnit,data:this.data};Object(a.z)(s,e).then(function(s){200==s.status?(t.$notify({type:"success",message:"转账成功"}),r.a.push({path:"/assetDetail",query:{assetAddress:t.assetAddress}})):(t.$notify({type:"warning",message:s.data.message}),r.a.push({path:"/assetDetail",query:{assetAddress:t.assetAddress}}))}).catch(function(s){t.$notify({type:"danger",message:"系统错误"}),r.a.push({path:"/assetDetail",query:{assetAddress:t.assetAddress}})})}}}},n={render:function(){var t=this,s=t.$createElement,e=t._self._c||s;return e("div",[e("van-cell-group",[e("van-field",{attrs:{required:"",clearable:"",label:"接收者",placeholder:"请输入接收者地址"},model:{value:t.address,callback:function(s){t.address=s},expression:"address"}}),t._v(" "),e("van-field",{attrs:{label:"金额",placeholder:"请输入额度",required:""},model:{value:t.account,callback:function(s){t.account=s},expression:"account"}}),t._v(" "),e("van-field",{attrs:{label:"备注",placeholder:"请输入备注",required:""},model:{value:t.data,callback:function(s){t.data=s},expression:"data"}})],1),t._v(" "),e("van-button",{staticStyle:{width:"100%"},attrs:{type:"default"},on:{click:t.submit}},[t._v("确定")])],1)},staticRenderFns:[]};var d=e("VU/8")(i,n,!1,function(t){e("/88X")},null,null);s.default=d.exports}});