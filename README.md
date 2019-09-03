# BAC资产管理服务
## 简介
此服务是BAC的资产管理工具。 输入{ip}：{port}即可进入界面。目前主要有资产管理和跨链交易两个功能；

资产管理：
资产管理可以管理跨群组资产，支持添加资产，输入合约地址和群组ID，即可显示自己的余额。并支持转账。


跨链交易：
跨链交易是基于哈希时间锁实现的。哈希锁定通过时间差和隐藏哈希值来实现资产的原子交换。
 交互流程如图所示：

用户手册：
1 现在资产管理添加要进行跨链的交易的资产；如群组1的AAA和 群组2的BBB
2 进入跨链资产交易界面：选择发起方或者接收方，无论接收方和发起方，步骤都是3步；存入资产，取出对方资产，以及超时取回自己资产；
3 存入资产： 发起方输入 接受者，密文，转出的资产（自己的待交换资产） 和转入资产（对手方的资产）数量；并选择过期时间，默认发起方是3小时，点击提交会返回合同ID，并告知接收方；
   如果是接收方的话需输入发起方的合同ID， 由发起方线下告诉接收方，成功提交后会返回另外一个合约id(接收方合约id)，接收方需要把接收方的合约id也告诉发起方
 4 取回资产：发起方输入 接收方合约id， 并输入自己第一步设置的密语，取回资产。 
             接收方收到发起方取回资产的通知，获得了密语，也会输入发起方的合约id和密语，取回发起方的资产。交易完成。
  5  超时取回： 存入资产无论发起方还是接收方都会设置超时时间，如果超时时间过了。可以取回自己存入合约的资产。                     

