# TX-SPEED服务
## 简介
   区块链的转账速度是无法跟传统金融机构paypal, 支付宝和微信像媲美的，因为去中心化，安全性，可扩展性三者不可兼得。
   针对小额高频转账场景，可以部分牺牲安全性，提高其TPS，采用TX-SPEED技术，使转账效率达到支付宝的水平，如商家和高频用户之间，
   可以建立支付通道。 如果两个用户之间转账频繁，也可以建立支付通道。
   另外，提高转账效率的同时也保护了用户隐私，因为交易都是在链下进行。
   TX-SPEED是Fisco Bcos的 layer2的支付通道解决方案，优势以下几点：
   1 提高转账效率, 支持其并发量超高的业务。
   2 保护用户隐私，因为交易都是在链下进行。
   3 节省区块链的存储空间。 由于减少了交易上链的频次，也有效的节省了区块链的存储。
   就是用户A和用户B有一些资产需要互相transfer，但是每次都走区块链交易提交过程太麻烦了，而且交易手续费也是一笔不小的负担。
   于是，raiden提供了一种基于链外交易的办法，用户A和B首先往链上的某一个合约里各打入一笔钱，之后通过一些加密手段互相通过链下的方式交换一些凭证。
   到最后结算的时候，将最后的凭证提交 到链上，合约会根据最终状态完成最后交易。


## 功能组成：
- *资产管理*：
资产管理可以管理跨群组资产，支持添加资产，输入合约地址和群组ID，即可显示自己的余额。并支持转账。

- *链下转账*：
  使用流程：
  1 创建通道；
  2 双方各自存入资产；
  3 开始链下交易；
  当 Bob 在  送出第一笔 资产交易给 Alice 时，此笔交易并不会发到区块链上，取而代之的是 Bob 会将此笔交易信息包含双方在信道中的余额利用自己的私钥签章过后，送给 Alice 保存此笔信息，此笔信息称为 Balance Proof。当 Alice 也通知 Bob 收到 Balance Proof 后，这笔交易在 Raiden 上面就会成立了。
  4 关闭通道， 一个通道关闭后，进入挑战期。任意一方可以关闭；
  5 另一方更新双方余额数据；
  6 通道结算 settle channel。


## 安装说明
  
  使用方式跟WeBASE-Front服务类似，服务启动后 输入{ip}：5002即可进入界面。
## 1. 拉取代码
执行命令：
```
git clone git@git.weoa.com:ttip/bac-trade.git
```

进入目录：

```
cd bac-trade
```

## 2. 编译代码

执行构建命令：

```
chmod +x ./gradlew
./gradlew build -x test
```

构建完成后，会在根目录下生成已编译的代码目录dist。

## 3. 修改配置

（1）进入dist目录

```
cd dist
```

（2）进入conf目录：

```shell
cd conf
```

**注意：** 需要将节点所在目录`nodes/${ip}/sdk`下的`ca.crt`、`node.crt`和`node.key`文件拷贝到当前conf目录，供SDK与节点建立连接时使用。

（3）修改配置（根据实际情况修改）：

```
vi application.yml
```

``` 
spring:
  datasource:
    url: jdbc:h2:file:./h2/webasefront;DB_CLOSE_ON_EXIT=FALSE // 默认H2库为webasefront
...
server: 
  port: 5003                    // 服务端口
  context-path: /
sdk: 
  ...
  ip: 127.0.0.1                 // 连接节点的监听ip
  channelPort: 20200            // 连接节点的链上链下端口
```

## 4. 服务启停

返回到dist目录执行：
```shell
启动: bash start.sh
停止: bash stop.sh
检查: bash status.sh
```
**备注**：如果脚本执行错误，尝试以下命令:

```
赋权限：chmod + *.sh
转格式：dos2unix *.sh
```

## 5. 访问控制台

```
http://{deployIP}:{frontPort}
示例：http://localhost:5002/
```
