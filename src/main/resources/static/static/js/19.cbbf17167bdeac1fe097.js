webpackJsonp([19],{"7TIk":function(t,s,a){"use strict";Object.defineProperty(s,"__esModule",{value:!0});var e=a("P9l9"),i=a("YaEn"),n={name:"assetDetail",data:function(){return{assetData:null,address:this.$route.query.assetAddress,assetList:[],assetType:this.$route.query.assetType}},mounted:function(){this.getData(),"default"===this.assetType&&this.getDefaultData()},methods:{getDefaultData:function(){this.defaultAssetList=JSON.parse(localStorage.getItem("defaultAssetList"))||[];for(var t=0;t<this.defaultAssetList.length;t++)this.defaultAssetList[t].contractAddress==this.address&&(this.assetData=this.defaultAssetList[t],this.getbalanceDetail())},getData:function(){this.assetList=JSON.parse(localStorage.getItem("assetList"))||[];for(var t=0;t<this.assetList.length;t++)this.assetList[t].contractAddress==this.address&&this.assetList[t].key==localStorage.getItem("userAddress")&&(this.assetData=this.assetList[t],this.getbalanceDetail())},getbalanceDetail:function(){var t=this,s={userAddress:localStorage.getItem("userAddress"),groupId:this.assetData.groupId,contractAddress:this.assetData.contractAddress};Object(e.l)(s).then(function(s){200===s.status?(s.data||0==s.data)&&t.$set(t.assetData,"balance",s.data):(t.$notify({type:"warning",message:s.data.message}),t.$set(t.assetData,"balance",0))}).catch(function(s){t.$notify({type:"danger",message:"系统错误"}),t.$set(t.assetData,"balance",0)})},link:function(){i.a.push("/home")},transfer:function(){i.a.push({path:"/transfer",query:{assetAddress:this.address}})}},filters:{Status:function(t){return t?"暂停":"正常"}}},l={render:function(){var t=this,s=t.$createElement,a=t._self._c||s;return a("div",{staticClass:"module"},[t.assetData?a("div",{staticClass:"detail-card"},[a("van-icon",{attrs:{name:"arrow-left"},on:{click:t.link}}),t._v(" "),a("span",[t._v(" "+t._s(t.assetData.shortName))]),a("br"),t._v(" "),a("span",{staticClass:"detail-card-number"},[t._v(t._s(t.assetData.balance))])],1):t._e(),t._v(" "),t.assetData?a("div",{staticClass:"detail-list"},[a("div",{staticClass:"detail-list-content detail-border-bottom"},[a("span",{staticClass:"detail-list-title"},[t._v("发行总额")]),t._v(" "),a("span",[t._v(t._s(t.assetData.totalAmount/Math.pow(10,t.assetData.minUnit)))])]),t._v(" "),a("div",{staticClass:"detail-list-content detail-border-bottom"},[a("span",{staticClass:"detail-list-title"},[t._v("资产状态")]),t._v(" "),a("span",[t._v(t._s(t._f("Status")(t.assetData.status)))])]),t._v(" "),a("div",{staticClass:"detail-list-content detail-border-bottom"},[a("span",{staticClass:"detail-list-title"},[t._v("资产地址")]),t._v(" "),a("span",[t._v(t._s(t.assetData.contractAddress))])]),t._v(" "),a("div",{staticClass:"detail-list-content"},[a("span",{staticClass:"detail-list-title"},[t._v("资产类型")]),t._v(" "),a("span",[t._v(t._s(t.assetData.contractName))])])]):t._e(),t._v(" "),a("div",{staticClass:"detail-button"},[a("van-button",{staticStyle:{width:"100%"},attrs:{type:"default"},on:{click:t.transfer}},[t._v("转账")])],1)])},staticRenderFns:[]};var d=a("VU/8")(n,l,!1,function(t){a("dBay")},"data-v-0705d6e8",null);s.default=d.exports},dBay:function(t,s){}});