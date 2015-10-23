业务管理系统
--------

包含:
* 区域管理
* 业务员管理
* 任务分配
* 扫街
* 注册
* 客户管理
* 通知

## 快速参考

所有API访问都是通过http进行的.所有发送和接受的数据都是`json` 格式.
### 区域
| URL     | HTTP Method     |  功能     |
| :-------------                   | :-------------  | :------------- |
| v1/region_custom                    | POST            |  新增自定义区域   |
| v1/region_custom/{id}                   | GET            |  获取自定义区域信息   |


### 业务员

| URL     | HTTP Method     |  功能     |
| :-------------                   | :-------------  | :------------- |
| v1/saleman/login                       | POST            |  登陆   |
| v1/saleman/{username}      | GET             |  获取指定业务员信息    |
| v1/saleman/{username}/payPassword           | PUT       |   修改会员支付密码,要求输入旧密码      |
| v1/saleman                       | POST            |  新增会员   |

### 交易

| URL     | HTTP Method     |  功能     |
| :-------------                   | :-------------  | :------------- |
| v1/tradings    | GET             |  查询交易列表  |
| v1/tradings    | POST      | 新增交易     |
| v1/tradings/{tradingNo}/status    | PUT      | 更改交易状态     |
| v1/tradings/stats    | GET     | 交易信息统计     |


### 其他
| URL     | HTTP Method     |  功能     |
| :-------------                   | :-------------  | :------------- |
| v1/members/{username}/change    | POST             |  会员信息修改回调     |

## 请求格式

对于`POST` 和 `PUT` 请求，请求的主体必须是 `JSON` 格式，而且 HTTP header 的 `Content-Type` 需要设置为 `application/json`。
用户验证是通过 HTTP header 来进行的，http header 增加 `hmac:username:sign 。

### hmac 算法

hmacMd5(secret,queryParam+payLoad)

## 查询
使用GET请求,通过queryParam传递查询条件.
### 查询约束

| KEY     | 条件     |
| :------------- | :------------- |
| sc\_EQ\_{$propertyname}      | 等于      |
| sc\_LIKE\_{$propertyname}     | 模糊匹配      |
| sc\_GT\_{$propertyname}       | 大于      |
| sc\_LT\_$propertyname}      | 小于      |
| sc\_GTE\_{$propertyname}      | 大于等于      |
| sc\_LTE\_{$propertyname}     | 小于等于      |
| page       | 页数,从零开始      |
| size       | 每页大小,默认值20     |
| sort=property,property(,ASC or DESC)       | 排序      |

例如查询交易额大于100元的成功交易记录,并按照交易时间排序:
```shell
curl -X GET \
  -H "hmac:username:sign" \
  -H "Content-Type:application/json" \
  http://xxxx.com/v1/tradings?sc_GT_amount=100&sc_EQ_status=success&sort=createTime,asc

```
## 区域

### 新增自定义区域

#### 接口
```
POST v1/region_custom    
```
#### 请求参数:

| 名称          | 类型           | 定义          | 必需           | 默认值        | 说明           |
|:------------- | :------------- |:------------- | :------------- |:------------- | :------------- |
| name           | string         | 区域名        |    是          |               |                |
| region_country | string         | 行政区域id      |    是          |               |                |
| points         | string         | 坐标链      |    是          |               |                |

* 示例:

```shell
curl -X POST \
  -H "hmac:username:sign" \
  -H "Content-Type:application/json" \
  -d "{\"name\":\"大桥镇\",\"region_country\":\"0102\",\"points\":\"123,2345;123,3532\"}"\
  http://xxxx.com/v1/region_custom

```
#### 响应
* 成功: 201

  响应内容格式:`JSON`
```json
  {
    name:"大桥镇",
    "points":"123,2345;123,3532",
    "region_country":"0102"
  }
```

## 业务员

### 新增业务员
phone,username不许为空.且唯一.

#### 接口
```
POST v1/saleman
```

#### 请求参数:

| 名称          | 类型           | 定义          | 必需           | 默认值        | 说明           |
|:------------- | :------------- |:------------- | :------------- |:------------- | :------------- |
| username      | string         | 用户名        |    是          |               |                |
| password      | string         | 登陆密码      |    否          |               |                |
| phone         | string         | 手机号码      |    是          |               |                |
| nickname      | string         | 真实姓名      |    是          |               |                |
| region          | string       | 自定义地区编码|    是          |               |                |

* 示例:

```shell
curl -X POST \
  -H "hmac:username:sign" \
  -H "Content-Type:application/json" \
  -d "{\"username\":\"yewuzhangsan\",\"password\":\"1234556\",\"phone\":\"1888888888\",\"nickname\":\"张三\",\"region\":\"ct10010\"}"\
  http://xxxx.com/v1/saleman

```

#### 响应
* 成功: 201

  响应内容格式:`JSON`
```json
  {
    username:"yewuzhangsan",
    "phone":"1888888888",
    "nickname":"张三",
    "region":"ct10010"
  }
```

