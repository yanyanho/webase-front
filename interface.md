# 接口说明

## 1. 资产接口
### 1.1. 创建资产通道网络
#### 接口URL

 **http://localhost:5002/tx-speed/create-bac-network?groupId=1&userAddress=0x86b83442caeeb02966ed9ae9bd782b27d919b677&assetAddress=0x8511decf9cec26d38db522fe6b3080a747c7566e**

#### 调用方法

HTTP GET

#### 请求参数

**1）参数表**

| **序号** | **中文** | **参数名**   | **类型**       | **最大长度** | **必填** | **说明**                           |
| -------- | --------   | ------------  | -------------- | ------------ | -------- | -------------- |
| 1        | 所属群组 | groupId      | Integer         |              | 是        |                      |
| 2        | 用户地址 | userAddress  | String         |              | 是        |                      |
| 3        | 资产合约地址 | aassetAddress| String         |              | 是       |                        |

#### 返回结果

**1）数据格式**
```
 "0xd5ad61903897cc23d0f2bde27878535fa226ee2d"
```

### 1.2. 开启通道

#### 接口URL

**http://localhost:5002/tx-speed/channel-open?groupId=1&userAddress=0x86b83442caeeb02966ed9ae9bd782b27d919b677&bacNetworkAddress=0xd5ad61903897cc23d0f2bde27878535fa226ee2d**

#### 调用方法

HTTP POST

#### 请求参数

**1）参数表**

| **序号** | **中文**     | **参数名**   | **类型** | **最大长度** | **必填** | **说明**             |
| -------- | ------------ | ------------ | -------- | ------------ | -------- | -------------------- |
| 1        | 所属群组     | groupId      | int      |              | 是       |                      |
| 2        | 用户地址     | userAddress         | String   |              | 是       | 用户编号或者用户地址 |
| 3        | 通道网络地址     | bacNetworkAddress | String   |              | 是       |                      |
| 4        | 参与者1      | participant1      | String     |              | 是       |                      |
| 5        | 参与者2      | participant2  | String   |              | 是       |                      |
| 6        | 通道超时时间 | settleTimeout    | BigInteger     |              | 否       |                      |

**2）数据格式**

```
{
     "participant1":"0x86b83442caeeb02966ed9ae9bd782b27d919b677",
     "participant2":"0xf3e3b7f1fc2fc5b0dde66ab8f02cbd418fa2b7e6",
     "settleTimeout":3600000
}
```

#### 响应参数
    返回通道编号
**1）数据格式**

```
{
     1
}
```

### 1.3. 质押资产

#### 接口URL

**http://localhost:5002/tx-speed/deposit?groupId=1&userAddress=0x86b83442caeeb02966ed9ae9bd782b27d919b677&bacNetworkAddress=0xd5ad61903897cc23d0f2bde27878535fa226ee2d**

#### 调用方法

HTTP POST

#### 请求参数

**1）参数表**

| **序号** | **中文**     | **参数名**  | **类型** | **最大长度** | **必填** | **说明**                  |
| -------- | ------------ | ----------- | -------- | ------------ | -------- | ------------------------- |
| 1        | 所属群组     | groupId      | int      |              | 是       |                      |
| 2        | 用户地址     | userAddress         | String   |              | 是       | 用户编号或者用户地址 |
| 3        | 通道网络地址     | bacNetworkAddress | String   |              | 是       |                      |
| 4        | 参与者     | participant |  String      |              | 是       |                           |
| 5        | 对手方 | partner   |  String     |              | 是       |                           |
| 5        | 通道ID | channelIdentifier   |  BigInteger     |              | 是       |                           |

**2）数据格式**
 request body:
```
{
        "participant":"0x86b83442caeeb02966ed9ae9bd782b27d919b677",
       "partner":"0xf3e3b7f1fc2fc5b0dde66ab8f02cbd418fa2b7e6",
       "channelIdentifier":1,
       "totalDeposit":100
}
```

#### 响应参数

**1）数据格式**

```
{
      ture
}
```

### 1.4. 链下转账


#### 接口描述

> 根据合约名及版本号查询合约地址

#### 接口URL

**http://localhost:5002/tx-speed/transfer?userAddress=0x86b83442caeeb02966ed9ae9bd782b27d919b677&bacNetworkAddress=0xd5ad61903897cc23d0f2bde27878535fa226ee2d**

#### 调用方法

HTTP POST

#### 请求参数

1. **参数表**

| **序号** | **中文**     | **参数名**   | **类型**       | **最大长度** | **必填** | **说明** |
| -------- | ------------ | ------------ | -------------- | ------------ | -------- | -------- |
| 1        | 所属群组     | groupId      | int      |              | 是       |                      |
| 2        | 用户地址     | userAddress         | String   |              | 是       | 用户编号或者用户地址 |
| 3        | 通道网络地址     | bacNetworkAddress | String   |              | 是       |                      |
| 4        | 发送方     |fromAddress |  String      |              | 是       |                           |
| 5        | 接收方 | toAddress   |  String     |              | 是       |                           |
| 6        | 通道ID | channelIdentifier   |  BigInteger     |              | 是       |                           |
| 7        | 序列号 | nonce   |  BigInteger     |              | 是       |                           |
| 8        | 参与方1     |participant1 |  String      |              | 是       |                           |
| 9        | 参与方2  | participant2   |  String     |              | 是       |                           |
|10        | 参与方1余额     |participant1Balance | BigInteger     |              | 是       |                           |
|11        | 参与方2余额  | participant2Balance  |  BigInteger     |              | 是       |                           |
|12        | 转账状态  |  status|  Boolean     |              | 是       |                           |

**2）数据格式**
 request body
```
"body": {
      "bacNetworkAddress":"0x86b83442caeeb02966ed9ae9bd782b27d919b677",
      "channelIdentifier":1,
      "fromAddress":"0x86b83442caeeb02966ed9ae9bd782b27d919b677",
      "toAddress":"0xf3e3b7f1fc2fc5b0dde66ab8f02cbd418fa2b7e6",
      "nonce":3,
      "participant1":"0x86b83442caeeb02966ed9ae9bd782b27d919b677",
      "participant2":"0xf3e3b7f1fc2fc5b0dde66ab8f02cbd418fa2b7e6",
      "participant1Balance":90,
      "participant2Balance":10,
      "status":1

    }
```

#### 响应参数

**1）数据格式**
```
 {
      "id": 129,
      "bacNetworkAddress": "0x86b83442caeeb02966ed9ae9bd782b27d919b677",
      "channelIdentifier": 1,
      "fromAddress": "0x86b83442caeeb02966ed9ae9bd782b27d919b677",
      "toAddress": "0xf3e3b7f1fc2fc5b0dde66ab8f02cbd418fa2b7e6",
      "value": null,
      "nonce": 3,
      "fromBalanceHash": "8259637DEDAE6C1A38C00B0F5EF6E7FB2FBAF4826D8C0C493391F7DE698DE23D",
      "fromSignature": "16B1A08AC2840FCA53ABE9DD571A68AA24F781E26EE2E7957A45460F57023A05792C75ABA4AF4D8D7EB7D87ED719378F00699D812BA33EFDEF891232EEA0DAB21C",
      "participant1": "0x86b83442caeeb02966ed9ae9bd782b27d919b677",
      "participant2": "0xf3e3b7f1fc2fc5b0dde66ab8f02cbd418fa2b7e6",
      "participant1Balance": 90,
      "participant2Balance": 10,
      "status": true,
      "createTime": "2019-11-22 12:37:02"
    }
  }
```
### 1.5. 查询通道详情

#### 接口URL

**http://localhost:5002/tx-speed/participant-info?groupId=1&userAddress=0x86b83442caeeb02966ed9ae9bd782b27d919b677&bacNetworkAddress=0xd5ad61903897cc23d0f2bde27878535fa226ee2d&partnerAdress=0xf3e3b7f1fc2fc5b0dde66ab8f02cbd418fa2b7e6&channelIdentifier=1**

#### 调用方法

HTTP GET

#### 请求参数

**1）参数表**

| **序号** | **中文** | **参数名**   | **类型** | **最大长度** | **必填** | **说明** |
| -------- | -------- | ------------ | -------- | ------------ | -------- | -------- |
| 1        | 所属群组     | groupId      | int      |              | 是       |                      |
| 2        | 用户地址     | userAddress         | String   |              | 是       | 用户编号或者用户地址 |
| 3        | 通道网络地址     | bacNetworkAddress | String   |              | 是       |                      |
| 4        | 通道ID | channelIdentifier   |  BigInteger     |              | 是       |                           |
| 5        | 对手方     |partnerAdress |  String      |              | 是       |                           |


#### 响应参数

**1）数据格式**
```
{
"lockedAmount": 0,
    "lockroot": "0000000000000000000000000000000000000000000000000000000000000000",
    "balanchHash": "0000000000000000000000000000000000000000000000000000000000000000",
    "isTheCloser": false,
    "deposit": 100,
    "nonce": 0,
    "withdraw": 0
}
```

### 1.6.  查询通道状态


#### 接口URL

**http://localhost:5002/tx-speed/channel-state?groupId=1&participant2=0x86b83442caeeb02966ed9ae9bd782b27d919b677&bacNetworkAddress=0xd5ad61903897cc23d0f2bde27878535fa226ee2d&participant1=0xf3e3b7f1fc2fc5b0dde66ab8f02cbd418fa2b7e6&channelIdentifier=1**

#### 调用方法

HTTP GET

#### 请求参数

**1）参数表**

| **序号** | **中文**     | **参数名**   | **类型**       | **最大长度** | **必填** | **说明** |
| -------- | ------------ | ------------ | -------------- | ------------ | -------- | -------- |
| 1        | 所属群组     | groupId      | int      |              | 是       |                      |
| 2        | 用户地址     | userAddress         | String   |              | 是       | 用户编号或者用户地址 |
| 3        | 通道网络地址     | bacNetworkAddress | String   |              | 是       |                      |
| 4        | 通道ID | channelIdentifier   |  BigInteger     |              | 是       |                           |
| 5        | 参与方1     |participant1 |  String      |              | 是       |                           |
| 5        | 参与方2     |participant2 |  String      |              | 是       |                           |


#### 响应参数

**2）数据格式**
```
{
       "settle_block_number": 10,
       "state": 1
}
```


### 1.7.  查询转账状态

#### 接口URL

**http://localhost:5002/tx-speed/transfer-log?bacNetworkAddress=0x86b83442caeeb02966ed9ae9bd782b27d919b677&channelIdentifier=1**

#### 调用方法

HTTP GET

#### 请求参数

**1）参数表**

| **序号** | **中文**     | **参数名**   | **类型**       | **最大长度** | **必填** | **说明** |
| -------- | ------------ | ------------ | -------------- | ------------ | -------- | -------- |
| 1        | 通道网络地址     | bacNetworkAddress | String   |              | 是       |                      |
| 2        | 通道ID | channelIdentifier   |  BigInteger     |              | 是       |                           |

#### 响应参数

**1）数据格式**
```
{
      "content": [
        {
          "id": 193,
          "bacNetworkAddress": "0x86b83442caeeb02966ed9ae9bd782b27d919b677",
          "channelIdentifier": 1,
          "fromAddress": "0xf3e3b7f1fc2fc5b0dde66ab8f02cbd418fa2b7e6",
          "toAddress": "0x86b83442caeeb02966ed9ae9bd782b27d919b677",
          "value": null,
          "nonce": 5,
          "fromBalanceHash": "2260FB09C8C9B3CB24C0A2F361AA6DACB2E0E349CB0D867B5D616D5BDAC9A069",
          "fromSignature": "56C341734BF492EC77DB7D80C0F0E77D61422B680A940B3F4A33FBB37591A6535F032249BF8F90E4A8FC689F6DAE0FCD31156FC1B31552CE280E0B1C2A93A9091C",
          "participant1": "0x86b83442caeeb02966ed9ae9bd782b27d919b677",
          "participant2": "0xf3e3b7f1fc2fc5b0dde66ab8f02cbd418fa2b7e6",
          "participant1Balance": 70,
          "participant2Balance": 30,
          "status": true,
          "createTime": "2019-11-22 17:08:07"
        }
      ],
      "totalPages": 1,
      "totalElements": 1,
      "last": true,
      "number": 0,
      "size": 10,
      "sort": [
        {
          "direction": "DESC",
          "property": "createTime",
          "ignoreCase": false,
          "nullHandling": "NATIVE",
          "ascending": false,
          "descending": true
        }
      ],
      "numberOfElements": 1,
      "first": true
    }
```


### 1.8. 通道关闭


#### 接口URL

**http://localhost:5002/tx-speed/channel-close?groupId=1&userAddress=0x86b83442caeeb02966ed9ae9bd782b27d919b677&bacNetworkAddress=0xd5ad61903897cc23d0f2bde27878535fa226ee2d**

#### 调用方法

HTTP POST

#### 请求参数

**1）参数表**

| **序号** | **中文**     | **参数名**   | **类型**       | **最大长度** | **必填** | **说明** |
| -------- | ------------ | ------------ | -------------- | ------------ | -------- | -------- |
| 1        | 所属群组     | groupId      | int      |              | 是       |                      |
| 2        | 用户地址     | userAddress         | String   |              | 是       | 用户编号或者用户地址 |
| 3        | 通道网络地址     | bacNetworkAddress | String   |              | 是       |                      |
| 4        | 通道ID | channelIdentifier   |  BigInteger     |              | 是       |                           |
| 5        | 关闭方 | closingParticipant | String         |       | 是       |  |
| 6        | 关闭方余额 | closingParticipantBalance| Integer         |       | 是       |  |
| 7        | 非关闭方 | nonClosingParticipant | String         |       | 是       |  |
| 8        | 非关闭方余额 | nonClosingParticipantBalance| Integer         |       | 是       |  |
| 9        | 转账序号 | nonce| Integer         |       | 是       |  |
| 10        | 非关闭方余额hash | nonClosingBalanceHash| String         |       | 是       |  |
| 11        | 非关闭方签名 | nonClosingSignature| String         |       | 是       |  |


**2）数据格式**
post body
```
{
      "channelIdentifier":1,
      "closingParticipant":"0x86b83442caeeb02966ed9ae9bd782b27d919b677",
      "closingParticipantBalance":100,
      "nonClosingParticipant":"0xf3e3b7f1fc2fc5b0dde66ab8f02cbd418fa2b7e6",
      "nonClosingParticipantBalance":20,
      "nonce":4,
      "nonClosingBalanceHash":"2260FB09C8C9B3CB24C0A2F361AA6DACB2E0E349CB0D867B5D616D5BDAC9A069",
      "nonClosingSignature":"56C341734BF492EC77DB7D80C0F0E77D61422B680A940B3F4A33FBB37591A6535F032249BF8F90E4A8FC689F6DAE0FCD31156FC1B31552CE280E0B1C2A93A9091C"
    }
 ```

#### 响应参数

**1）数据格式**
```
{
   ture
}
```

### 1.9.非关闭方更新对方余额


#### 接口URL

**http://localhost:5002/tx-speed/update-balacne-proof?groupId=1&userAddress=0x86b83442caeeb02966ed9ae9bd782b27d919b677&bacNetworkAddress=0xd5ad61903897cc23d0f2bde27878535fa226ee2d**

#### 调用方法

HTTP POST

#### 请求参数

**1）参数表**

| **序号** | **中文** | **参数名** | **类型** | **最大长度** | **必填** | **说明** |
| -------- | -------- | ---------- | -------- | ------------ | -------- | -------- |
| 1        | 所属群组     | groupId      | int      |              | 是       |                      |
| 2        | 用户地址     | userAddress         | String   |              | 是       | 用户编号或者用户地址 |
| 3        | 通道网络地址     | bacNetworkAddress | String   |              | 是       |                      |
| 4        | 通道ID | channelIdentifier   |  BigInteger     |              | 是       |                           |
| 5        | 关闭方 | closingParticipant | String         |       | 是       |  |
| 6        | 关闭方余额 | closingParticipantBalance| Integer         |       | 是       |  |
| 7        | 非关闭方 | nonClosingParticipant | String         |       | 是       |  |
| 8        | 关闭方余额 | closingParticipantBalance| Integer         |       | 是       |  |
| 9        | 转账序号 | closingParticipantNonce| Integer         |       | 是       |  |
| 10        | 关闭方签名 | closingSignature| String       |       | 是       |  |

**2）数据格式**

```
{
      "channelIdentifier":1,
      "closingParticipant":"0x86b83442caeeb02966ed9ae9bd782b27d919b677",
      "nonClosingParticipant":"0xf3e3b7f1fc2fc5b0dde66ab8f02cbd418fa2b7e6",
      "closingParticipantBalance":20,
      "closingParticipantNonce":4,
      "balanceHashClosing":"2260FB09C8C9B3CB24C0A2F361AA6DACB2E0E349CB0D867B5D616D5BDAC9A069",
      "closingSignature":"56C341734BF492EC77DB7D80C0F0E77D61422B680A940B3F4A33FBB37591A6535F032249BF8F90E4A8FC689F6DAE0FCD31156FC1B31552CE280E0B1C2A93A9091C"
  }
```

#### 响应参数

**1）数据格式**

```
true
```

### 1.10.通道结算

#### 接口URL

**http://localhost:5002/tx-speed/settle?groupId=1&userAddress=0x86b83442caeeb02966ed9ae9bd782b27d919b677&bacNetworkAddress=0xd5ad61903897cc23d0f2bde27878535fa226ee2d**

#### 调用方法

HTTP POST

#### 请求参数

**1）参数表**

| **序号** | **中文** | **参数名** | **类型** | **最大长度** | **必填** | **说明** |
| -------- | -------- | ---------- | -------- | ------------ | -------- | -------- |
| 1        | 所属群组     | groupId      | int      |              | 是       |                      |
| 2        | 用户地址     | userAddress         | String   |              | 是       | 用户编号或者用户地址 |
| 3        | 通道网络地址     | bacNetworkAddress | String   |              | 是       |                      |
| 4        | 通道ID | channelIdentifier   |  BigInteger     |              | 是       |                           |
| 5        | 参与方1 | participant1 | String         |       | 是       |  |
| 6        | 参与方1余额 | participant1TransferredAmount| Integer         |       | 是       |  |
| 7        | 参与方1锁定余额 |participant1LockedAmount| Integer         |       | 是       |  |
| 8        | 参与方2 | participant2| String         |       | 是       |  |
| 9        | 参与方2余额 | participant2TransferredAmount| Integer         |       | 是       |  |
| 10        | 参与方2锁定余额 | participant2LockedAmount| Integer         |       | 是       |  |

**2）数据格式**

```
{
          "channelIdentifier":1,
          "participant1":"0x86b83442caeeb02966ed9ae9bd782b27d919b677",
          "participant1TransferredAmount":180,
          "participant1LockedAmount":0,
          "participant2":"0xf3e3b7f1fc2fc5b0dde66ab8f02cbd418fa2b7e6",
          "participant2TransferredAmount":20,
          "participant2LockedAmount":0
}
```

#### 响应参数

**1）数据格式**

```
true
```


