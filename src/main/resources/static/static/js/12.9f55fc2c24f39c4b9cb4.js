webpackJsonp([12],{"kN+R":function(t,s){},oc1x:function(t,s,e){"use strict";Object.defineProperty(s,"__esModule",{value:!0});var a=e("mvHQ"),n=e.n(a),i=e("P9l9"),o=e("YaEn"),r={name:"inTransaction",data:function(){return{newAssetList:[],assetList:[],amountShow:!1,amount:0,assetObj:null}},mounted:function(){localStorage.getItem("changeAssetList")&&(this.newAssetList=JSON.parse(localStorage.getItem("changeAssetList"))),this.getAllBalance()},methods:{link:function(){o.a.push("/orderList")},getAllBalance:function(){if(localStorage.getItem("userAddress")&&this.newAssetList.length)for(var t=0;t<this.newAssetList.length;t++)this.getExchangeBalance(this.newAssetList[t])},getExchangeBalance:function(t){var s=this,e={exchangeContractAddress:t.exchangeContractAddress,userAddress:t.key,groupId:t.groupId,minUnit:t.minUnit,assetAddress:t.contractAddress};Object(i.f)(e).then(function(e){var a=e.status,i=e.data;if(200===a){for(var o=0,r=0;r<s.newAssetList.length;r++)s.newAssetList[r].contractAddress==t.contractAddress&&(s.newAssetList[r].balance=i,o++);0==o&&(s.$set(t,"balance",i),s.$set(t,"key",localStorage.getItem("userAddress")),s.newAssetList.push(t)),console.log(s.newAssetList),localStorage.setItem("changeAssetList",n()(s.newAssetList)),s.getAssets()}else s.$notify({type:"warning",message:e.data.message})}).catch(function(t){s.$notify({type:"danger",message:"系统错误"})})},getAssets:function(){this.assetList=[],this.newAssetList=JSON.parse(localStorage.getItem("changeAssetList"));for(var t=0;t<this.newAssetList.length;t++)this.newAssetList[t].key&&this.newAssetList[t].key==localStorage.getItem("userAddress")&&this.assetList.push(this.newAssetList[t])},create:function(){o.a.push("/depositAsset")},takeOut:function(t){this.amountShow=!0,this.assetObj=t},recipientTrakeConfirm:function(){var t=this,s={groupId:this.assetObj.groupId,exchangeContractAddress:this.assetObj.exchangeContractAddress,userAddress:this.assetObj.key},e={assetContractAddress:this.assetObj.contractAddress,amount:this.amount,minUnit:this.assetObj.minUnit};Object(i.k)(s,e).then(function(s){t.amountShow=!1,200==s.status?s.data&&(t.$notify({type:"success",message:"取出资产成功！"}),t.getAllBalance()):t.$notify({type:"warning",message:s.data.message})}).catch(function(s){t.amountShow=!1,t.$notify({type:"danger",message:"系统错误"})})}}},c={render:function(){var t=this,s=t.$createElement,e=t._self._c||s;return e("div",{staticClass:"module"},[e("div",{staticClass:"header"},[e("span",[t._v("链内交易")]),t._v(" "),e("span",{staticClass:"header-right",on:{click:t.link}},[e("span",[t._v("订单")]),t._v(" "),e("van-icon",{attrs:{name:"arrow"}})],1)]),t._v(" "),e("div",{staticClass:"cross-content"},[e("ul",t._l(t.assetList,function(s){return e("li",{staticClass:"list-content"},[e("svg",{staticClass:"icon",staticStyle:{width:"28px",height:"28px"},attrs:{"aria-hidden":"true"}},[e("use",{attrs:{"xlink:href":"#wbs-icon-qianbi"}})]),t._v(" "),e("div",{staticClass:"list-left"},[e("span",[t._v(t._s(s.shortName))]),e("br"),t._v(" "),e("span",[t._v(t._s(s.contractName))])]),t._v(" "),e("div",{staticClass:"list-right"},[e("span",[t._v(t._s(s.balance))]),e("br")]),e("br"),t._v(" "),e("van-button",{staticStyle:{margin:"5px 10px 0 0"},attrs:{type:"primary",size:"small",text:""},on:{click:function(e){return t.takeOut(s)}}},[t._v("取钱")])],1)}),0)]),t._v(" "),e("div",{staticClass:"cross-bottom"},[e("van-button",{staticStyle:{width:"100%"},attrs:{type:"default"},on:{click:t.create}},[t._v("存钱")])],1),t._v(" "),e("van-dialog",{attrs:{title:"取出资产","show-cancel-button":""},on:{confirm:t.recipientTrakeConfirm},model:{value:t.amountShow,callback:function(s){t.amountShow=s},expression:"amountShow"}},[e("van-field",{attrs:{required:"",clearable:"",label:"额度",placeholder:"请输入额度"},model:{value:t.amount,callback:function(s){t.amount=s},expression:"amount"}})],1)],1)},staticRenderFns:[]};var l=e("VU/8")(r,c,!1,function(t){e("kN+R")},"data-v-4747600a",null);s.default=l.exports}});