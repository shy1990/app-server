业务管理系统
--------

包含:
* 区域管理
* 业务员管理
* 任务
* 获取业务区域(业务是指扫街、注册、客户维护)
* 客户管理
* 照片上传
* 扫街
* 注册
* 通知

## 快速参考

所有API访问都是通过http进行的.所有发送和接受的数据都是`json` 格式.


### 区域管理
| URL     | HTTP Method     |  功能     |
| :-------------                   | :-------------  | :------------- |
| v1/region_custom                 | POST            |  新增自定义区域   |
| v1/region_custom/{id}            | GET             |  获取自定义区域信息   |
| v1/region_custom/{username}                | GET             |  获取业务区域信息(扫街、注册、维护)   |



### 业务员管理

| URL     | HTTP Method     |  功能     |
| :-------------                   | :-------------  | :------------- |
| v1/saleman/login                       | POST            |  登陆   |
| v1/saleman/{username}      | GET             |  获取指定业务员信息    |
| v1/saleman/{username}/password           | PUT       |   修改会员支付密码,要求输入旧密码      |
| v1/saleman                       | POST            |  新增业务员  |

### 任务

| URL     | HTTP Method     |  功能     |
| :-------------                   | :-------------  | :------------- |
| v1/task/sweep                       | POST            |  扫街任务分配   |
| v1/task/visit                       | POST            |  拜访任务分配   |
| v1/task/{id}                        | GET             |  任务审核    |
| v1/task/addVisit                        | GET             |  拜访任务提交    |

### 客户管理

| URL     | HTTP Method     |  功能     |
| :-------------                   | :-------------  | :------------- |
| v1/member/{username}                   | get            |  查询扫街数据   |
| v1/member/findRegistMap              | get            |  注册客户地图查询 (已开发、未开发)  |
| v1/member              | POST            |  添加手机店信息(信息录入) |
| v1/member/rel              | get            |  关联店铺查询|
| v1/member/rel/add            | POST            | 添加关联店铺 |
| v1/member/phoneCount          | GET            | 根据月份分组查销量 |

### 照片上传
| URL     | HTTP Method     |  功能     |
| :-------------                   | :-------------  | :------------- |
| v1/images/upload                  | POST            |  照片上传 |

### 扫街
| URL     | HTTP Method     |  功能     |
| :-------------                   | :-------------  | :------------- |
| v1/saojie                       | POST            |  扫街数据上传 |
| v1/saojie/visit                       | POST            |  拜访任务分配   |
| v1/saojie/{id}                        | GET             |  任务审核    |

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

## 区域管理

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


### 获取自定义区域信息

#### 接口
```
GET v1/region_custom/{id}   
```
#### 请求参数:

id根据自定义区域id查询自定义区域信息

* 示例:

```shell
curl -X GET \
  -H "hmac:username:sign" \
  -H "Content-Type:application/json" \
  http://xxxx.com/region_custom/1001 

```

#### 响应
* 成功: 200

  响应内容格式:`JSON`
```json
  {
    name:"大桥镇",
    "points":"123,2345;123,3532",
    "region_country":"0102"
  }
  
  ```

### 获取业务区域信息(扫街、注册、维护)

#### 接口
```
GET v1/region_custom/{username}
```
#### 请求参数:

通过username查询各个业务阶段所需要的区域数据

* 示例:

```shell
curl -X GET \
  -H "hmac:username:sign" \
  -H "Content-Type:application/json" \
  http://xxxx.com/region_custom/zhangsan 

```

#### 响应
* 成功: 200

  响应内容格式:`JSON` ，注册，客户维护字段不够自己添加,及时更新
```json
  {	
  	"username":"zhangsan",
    "open":[
    		{"region_name":"大桥镇",
    		"points":"123.123,87.565^123.123,87.565^123.123,87.565"},
    		{"region_name":"B镇",
    		"points":"123.123,87.565^123.123,87.565^123.123,87.565"}
           ],
     "nopne":[
    		{"region_name":"大桥镇",
    		"points":"123.123,87.565^123.123,87.565^123.123,87.565"},
    		{"region_name":"B镇",
    		"points":"123.123,87.565^123.123,87.565^123.123,87.565"}
           ]
  }


## 客户管理
### 获取扫街数据v1/member/{username}

username不许为空.且唯一.

#### 接口
```
GET v1/member/{username}
```

#### 请求参数:
通过业务员username去查询

* 示例:

```shell
curl -X POST \
  -H "hmac:username:sign" \
  -H "Content-Type:application/json" \
  http://xxxx.com/v1/member/{username}

```

#### 响应
* 成功: 200

  响应内容格式:`JSON` 暂时这些不够可加，及时更新
```json
  
  	[	
  		{
  			"name":"山大北路店",
  			"id":"1001",
  			"point":"120.333,67.333",
  			"remark":"扫的第一家店"
  		},
  		{
  			"name":"山大南路店",
  			"id":"1002",
  			"point":"124.333,67.343",
  			"remark":"扫的第二家店"
  		}
  		
  ]
  
### 注册地图查询

username不许为空.且唯一.

#### 接口
```
GET  v1/member/findRegistMap
```

#### 请求参数:
通过region_id作为参数去查询

* 示例:

```shell
curl -X POST \
  -H "hmac:username:sign" \
  -H "Content-Type:application/json" \
  http://xxxx.com/v1/member/findRegistMap

```

#### 响应
* 成功: 200

  响应内容格式:`JSON` 暂时这些不够可加，及时更新
```json
  
  	{
  		"username":"zhangsan",
  		"region":"大桥镇",
  		"development":
	  		[ 
	  		  {
	  		  	"name":"山大北路店",
	  		  	"point":"123.23,68.36",
	  		  	"id":"1001"
	  		  },
	  		  {
	  		  	"name":"山大南路店",
	  		  	"point":"123.23,68.36",
	  		  	"id":"1002"
	  		  }
	  		],
	  		"noDevelopment":
	  		[ 
	  		  {
	  		  	"name":"山大南路店",
	  		  	"point":"121.23,69.36",
	  		  	"id":"1003"
	  		  },
	  		  {
	  		  	"name":"山大南路店",
	  		  	"point":"127.23,61.36",
	  		  	"id":"1004"
	  		  }
	  		]
  	}
   

### 客户信息添加（信息录入）


#### 接口
```
GET  v1/member
```

#### 请求参数:
| 名称          | 类型           | 定义          | 必需           | 默认值        | 说明           |
|:------------- | :------------- |:------------- | :------------- |:------------- | :------------- |
| username      | string         | 商家账号      |    是          |               |                |
| name      | string         | 手机店名       |    是          |               |                |
| phone      | string         | 手机号码      |    否          |               |                |
| consignee         | string         | 收货人     |    是          |               |                |
| address     | string         | 收货地址      |    是          |               |                |
| counter_number     | int         | 柜台数      |    是          |               |                |

* 示例:

```shell
curl -X POST \
  -H "hmac:username:sign" \
  -H "Content-Type:application/json" \
  -d "{\"name\":\"小米手机店\",\"phone\":\"15123653623\",\"consignee\":\"张三\",\"address\":\"小米家地址\",\"counter_number\":\"11\"}"\
  http://xxxx.com/v1/member/{region_id}

```

#### 响应
* 成功: 200

  响应内容格式:`JSON` 暂时这些不够可加，及时更新
```json
  
  	{
  		"username":"zhangsan",
  		"region":"大桥镇",
  		"development":
	  		[ 
	  		  {
	  		  	"name":"山大北路店",
	  		  	"point":"123.23,68.36",
	  		  	"id":"1001"
	  		  },
	  		  {
	  		  	"name":"山大南路店",
	  		  	"point":"123.23,68.36",
	  		  	"id":"1002"
	  		  }
	  		],
	  		"noDevelopment":
	  		[ 
	  		  {
	  		  	"name":"山大南路店",
	  		  	"point":"121.23,69.36",
	  		  	"id":"1003"
	  		  },
	  		  {
	  		  	"name":"山大南路店",
	  		  	"point":"127.23,61.36",
	  		  	"id":"1004"
	  		  }
	  		]
  	}
  	
  	
  	
### 关联店铺查询
#### 接口
```
GET  v1/member/rel
```

#### 请求参数:
name ：手机店名字

* 示例:

```shell
curl -X POST \
  -H "hmac:username:sign" \
  -H "Content-Type:application/json" \
  -d "{\"name\":\"小米手机店\"}"\
  http://xxxx.com/v1/member/rel

```

#### 响应
* 成功: 200

  响应内容格式:`JSON` 暂时这些不够可加，及时更新
```json
  
  	{
  		"username":"zhangsan",
  		"region":"大桥镇",
	  		[ 
	  		  {
	  		  	"id":"1001"
	  		  	"name":"山大北路店",
	  		  	"point":"123.23,68.36",
	  		  	"phone":"13455106666",
	  		  	"consignee":"张三",
	  		  	"adress":"山大路北店收货地址",
	  		  	"counter_number":"11",
	  		  	"img_url":"http://3j1688.cn/11.jpg"
	  		  },
	  		  {
	  		  	"name":"山大南路店",
	  		  	"point":"123.23,68.36",
	  		  	"id":"1002",
	  		  	"consignee":"李四,
	  		  	"adress":"山大路南店收货地址",
	  		  	"counter_number":"12",
	  		  	"img_url":"http://3j1688.cn/22.jpg"
	  		  }
	  		]
  	}  	


### 添加关联店铺
#### 接口
```
GET  v1/member/rel/add
```

#### 请求参数:
username1 ：手机店名字   username2 :手机店名字

* 示例:

```shell
curl -X POST \
  -H "hmac:username:sign" \
  -H "Content-Type:application/json" \
  -d "{\"username1\":\"A店\",\"username2\":\"B店\"}"\
  http://xxxx.com/v1/member/rel/add

```

#### 响应
* 成功: 201



### 分月份查询手机销量
#### 接口
```
GET  v1/member/phoneCount
```

#### 请求参数:
month ：10   name :手机店铺名字   username 业务员名字 (或者店铺id，怎么容易怎么来)

* 示例:

```shell
curl -X POST \
  -H "hmac:username:sign" \
  -H "Content-Type:application/json" \
  -d "{\"month\":\"10\",\"name\":\"小明手机店\",\"username\":\"业务员名字\"}"\
  http://xxxx.com/v1/member/phoneCount

```

#### 响应
* 成功: 200

  响应内容格式:`JSON` 暂时这些不够可加，及时更新
```json
  
  	{
  		"username":"zhangsan",
  		"region":"大桥镇",
  		"name":"大桥手机专卖"
	  		[ 
	  		  {
	  		  	"count":"10"
	  		  	"date":"2015-09-12",
	  		  	"ordernum":"201509165265",
	  		  	"sums":"2000"
	  		  },
	  		  {
	  		  	"count":"13"
	  		  	"date":"2015-09-15",
	  		  	"ordernum":"201509175265",
	  		  	"sums":"2000"
	  		  }
	  		]
  	}  	








## 图片上传

#### 接口
```
POST v1/images/upload
```

#### 请求参数:

无

* 示例:

```shell
curl -X POST \
  -H "hmac:username:sign" \
  -H "Content-Type:application/json" \
  -d "{\"data\":\"图片压缩编码\"}"\
  http://xxxx.com/images/upload

```

#### 响应
* 成功: 200

  响应内容格式:`JSON`
```json
  {
    "image_url":"http://image.3j1688.com/a.jpg"
  }



## 扫街
### 扫街数据上传
#### 接口
```
POST v1/saojie
```

#### 请求参数:
| 名称          | 类型           | 定义          | 必需           | 默认值        | 说明           |
|:------------- | :------------- |:------------- | :------------- |:------------- | :------------- |
| name      | string         | 手机店名       |    是          |               |                |
| remark      | string         | 备注      |    否          |               |                |
| image_url         | string         | 手机号码      |    是          |               |                |
| task_id      | int         | 任务id      |    是          |               |                |

* 示例:

```shell
curl -X POST \
  -H "hmac:username:sign" \
  -H "Content-Type:application/json" \
  -d "{\"name\":\"小明手机店\",\"remark\":\"这是备注\",\"image_url\":\"图片地址\",\"task_id\":\"任务id\"}"\
  http://xxxx.com/images/upload

```

#### 响应
* 成功: 201



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






### 业务员登录
username,password,sim卡号,username不许为空.且唯一,password不许为空,phone不许为空.

#### 接口
```
GET v1/saleman/login
```

#### 请求参数:

| 名称          | 类型           | 定义          | 必需           | 默认值        | 说明           |
|:------------- | :------------- |:------------- | :------------- |:------------- | :------------- |
| username      | string         | 用户名        |    是          |               |                |
| password      | string         | 登陆密码      |    否          |               |                |
| phone         | string         | 手机号码      |    是          |               |                |


* 示例:

```shell
curl -X GET \
  -H "hmac:username:sign" \
  -H "Content-Type:application/json" \
  -d "{\"username\":\"yewuzhangsan\",\"password\":\"1234556\",\"phone\":\"1888888888\"}"\
  http://xxxx.com/v1/login

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

  
  
### 获取业务员信息
username不为空

#### 接口
```
GET  v1/saleman/{username}
```

#### 请求参数:

username是业务员的能录名唯一


* 示例:

```shell
curl -X GET \
  -H "hmac:username:sign" \
  -H "Content-Type:application/json" \
  http://xxxx.com/v1/saleman/zhangsan

```

#### 响应
* 成功: 201

  响应内容格式:`JSON`
```json
  {
    username:"zhangsan ",
    "phone":"1888888888",
    "nickname":"张三",
    "region":"ct10010"
  }
  
  
### 修改业务员密码
username不为空,password

#### 接口
```
GET  v1/saleman/{username}/password
```

#### 请求参数:

username是业务员的能录名唯一


* 示例:

```shell
curl -X GET \
  -H "hmac:username:sign" \
  -H "Content-Type:application/json" \
  http://xxxx.com/v1/saleman/{username}/password

```

#### 响应
* 成功: 201
  created,无返回值
  
  
### 扫街任务分配

#### 接口
```
post  v1/task/sweep
```

#### 请求参数:


| 名称          | 类型           | 定义          | 必需           | 默认值        | 说明           |
|:------------- | :------------- |:------------- | :------------- |:------------- | :------------- |
| name      | string         | 任务名字      |    是          |               |                |
| description      | string         | 描述      |    否          |               |                |
| end_time         | date         | 结束时间      |    是          |               |                |
| region_id         | string         | 行政区     |    是          |               |                |
| username         | string         | 任务责任人     |    是          |               |               |
| min_count         | int         | 最小扫街数     |    是          |               |                |



* 示例:

```shell
curl -X POST \
  -H "hmac:username:sign" \
  -H "Content-Type:application/json" \
   -d "{\"name\":\"扫街任务\",\"description\":\"这是一个扫街任务\",\"end_time\":\"2015-12-12\",\"status\":\"1\",\"region\":\"ct10010\",\"username\":\"zhangsan\",\"min_count\":\"100\"}"\
  http://xxxx.com/v1/task/sweep

```

#### 响应
* 成功: 201

  响应内容格式:无
 * 失败: 404
 
 
### 拜访任务分配

#### 接口
```
post  v1/task/visit
```

#### 请求参数:


| 名称          | 类型           | 定义          | 必需           | 默认值        | 说明           |
|:------------- | :------------- |:------------- | :------------- |:------------- | :------------- |
| name      | string         | 任务名字      |    是          |               |                |
| description      | string         | 描述      |    否          |               |                |
| end_time         | date         | 结束时间      |    是          |               |                |
| username         | string         | 任务责任人     |    是          |               |               |
| member         | string         | 商城用户名    |    是          |               |                |
| image_url         | string         | 拜访图片url    |    是          |               |                |
| point         | string         | 拜访坐标   |    是          |               |                |
| remark         | string         | 备注    |    是          |               |                |
| visit_time         | date         | 拜访日期   |    是          |               |                |



* 示例:

```shell
curl -X POST \
  -H "hmac:username:sign" \
  -H "Content-Type:application/json" \
   -d "{\"name\":\"拜访任务\",\"description\":\"这是一个拜访任务\",\"visit_time\":\"2015-12-12\",\"status\":\"1\",\"username\":\"zhangsan\",\"member\":\"xiaoming\",\"image_url\":\"http://xxx.com/xxxx.jpg\"
   ,\"point\":\"1111.111,80.122\",\"remark\":\"备注\",\"visit_time\":\"2015-12-12\"}"\
  http://xxxx.com/v1/task/sweep

```

#### 响应
* 成功: 201

  响应内容格式:无
 * 失败: 404
 


### 任务审核

#### 接口
GET  v1/task/{id}
id审核任务的id


#### 请求参数:
 名称          | 类型           | 定义          | 必需           | 默认值        | 说明           |
|:------------- | :------------- |:------------- | :------------- |:------------- | :------------- |
| name      | string         | 审核名称      |    是          |               |                |
| description      | string         | 描述      |    否          |               |                |
| accept         | int         | 1：接受 0：拒绝      |    是          |               |                |
| remark         | string         |备注    |    否          |               |                |
| username         | string         | 任务责任人     |    是          |               |               |
| task_id         | string         | 任务id     |    是          |               |               |
|



* 示例:

```shell
curl -X GET \
  -H "hmac:username:sign" \
  -H "Content-Type:application/json" 
   -d "{\"name\":\"审核任务\",\"description\":\"这是一个描述\",\"accept\":\"1\",\"remark\":\"备注\",\"username\":\"zhangsan\","task_id":\"任务id\"}"\
  http://xxxx.com/v1/task/{id}

```

#### 响应
* 成功: 200
```json
{
 "task_id":"1125",
 "accept":1,
 "username":"zhangsan"
	
}

  响应内容格式:无
 * 失败: 404
 
 
### 拜访任务添加

#### 接口
POST  v1/task/addvisit
id审核任务的id


#### 请求参数:
 名称          | 类型           | 定义          | 必需           | 默认值        | 说明           |
|:------------- | :------------- |:------------- | :------------- |:------------- | :------------- |
| username      | string         | 业务员姓名      |    是          |               |                |
| member      | string         | 商户用户名     |    否          |               |                |
| point         | string         | 拜访位置坐标    |    是          |               |                |
| remark         | string         |备注    |    否          |               |                |
| visit_time         | date         | 拜访时间    |    是          |               |               |
| image_url         | string         | 拜访图片地址    |    是          |               |               |




* 示例:

```shell
curl -X POST \
  -H "hmac:username:sign" \
  -H "Content-Type:application/json" 
   -d "{\"username\":\"业务员姓名\",\"member\":\"小明家手机店\",\"point\":\"123.23,36.233\",\"remark\":\"备注\",\"visit_time\":\"2015-12-12\","image_url":\"http://3j1688.cn/111.jpg\"}"\
  http://xxxx.com/v1/task/addvisit

```

#### 响应
* 成功: 201
  响应内容格式:无
 * 失败: 404
 
 
 
 
### 业务区域获取

#### 接口
GET  v1/task/{id}
id审核任务的id


#### 请求参数:
 名称          | 类型           | 定义          | 必需           | 默认值        | 说明           |
|:------------- | :------------- |:------------- | :------------- |:------------- | :------------- |
| name      | string         | 审核名称      |    是          |               |                |
| description      | string         | 描述      |    否          |               |                |
| accept         | int         | 1：接受 0：拒绝      |    是          |               |                |
| remark         | string         |备注    |    否          |               |                |
| username         | string         | 任务责任人     |    是          |               |               |
| task_id         | string         | 任务id     |    是          |               |               |



* 示例:

```shell
curl -X GET \
  -H "hmac:username:sign" \
  -H "Content-Type:application/json" 
   -d "{\"name\":\"审核任务\",\"description\":\"这是一个描述\",\"accept\":\"1\",\"remark\":\"备注\",\"username\":\"zhangsan\","task_id":\"任务id\"}"\
  http://xxxx.com/v1/task/{id}

```

#### 响应
* 成功: 200
```json
{
 "task_id":"1125",
 "accept":1,
 "username":"zhangsan"
	
}

  响应内容格式:无
 * 失败: 404
 
 
 
  