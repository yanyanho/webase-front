webpackJsonp([20],{"j6C/":function(e,t,a){"use strict";Object.defineProperty(t,"__esModule",{value:!0});var n=a("P9l9"),r=a("YaEn"),i={name:"importPrivateKey",data:function(){return{privateKey:""}},mounted:function(){},methods:{onSubmit:function(e){this.queryImportPrivateKey(e.privateKey)},queryImportPrivateKey:function(e){var t=this,a={privateKey:e,userName:sessionStorage.getItem("userName")};Object(n.p)(a).then(function(e){e.data;200===e.status?r.a.push("/user"):t.$notify({type:"warning",message:e.data.message})}).catch(function(e){t.$notify({type:"danger",message:"系统错误"})})}}},s={render:function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"module"},[a("van-form",{on:{submit:e.onSubmit}},[a("van-field",{attrs:{name:"privateKey",label:"导入私钥",placeholder:"请输入私钥",rules:[{required:!0,message:"请填写私钥"}]},model:{value:e.privateKey,callback:function(t){e.privateKey=t},expression:"privateKey"}}),e._v(" "),a("van-button",{staticStyle:{width:"100%"},attrs:{type:"default","native-type":"submit"}},[e._v("\n            提交\n        ")])],1)],1)},staticRenderFns:[]},u=a("VU/8")(i,s,!1,null,null,null);t.default=u.exports}});