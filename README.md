# RetrofitDemo

#### 介绍
1.基于Retrofit v2.0 + RxJava2 和wanandroid开放API的Demo
2.RxJava基本操作符的作用演示和源码查看

此Demo参考Carson_Ho关于RxJava和Retrofit的系列文章 原文链接：https://www.jianshu.com/p/2c54f9ccd52f

Retrofit介绍：
![Retrogit简介](https://images.gitee.com/uploads/images/2019/0414/191531_2ea34fc5_1435209.png "944365-b6d3198d37590906.png")
Rxjava介绍 ：
![Rxjava简介](https://images.gitee.com/uploads/images/2019/0414/191727_d3e66059_1435209.png "rxjava.png")

二者结合使用：
传统Retrofit用于描述网络请求接口的方式是使用了Call<T>接口
而RxJava模式使用了Observable<T>接口
栗子：(采用WanAndroid开放API进行举例) 详看ApiService类

另外，网络请求的封装形式&发送形式不同
详看NetworkController类
可对比getArticleListByNormal()和getArticleListByRx()方法

实际使用步骤:
1.添加依赖

```
// retrofit2最新版本
implementation 'com.squareup.retrofit2:retrofit:2.5.0'
// 支持gson解析
implementation 'com.squareup.retrofit2:converter-gson:2.5.0'
// rxjava2最新版本
implementation 'io.reactivex.rxjava2:rxjava:2.2.8'
implementation 'io.reactivex.rxjava2:rxandroid:2.1.1'
// 连接retrofit2和rxjava2
implementation 'com.squareup.retrofit2:adapter-rxjava2:2.5.0'
```
git地址：

rxAndroid：

https://github.com/ReactiveX/RxAndroid

retrofit:

https://github.com/square/retrofit

rxjava:

https://github.com/ReactiveX/RxJava

retrofit2-rxjava2连接

https://github.com/square/retrofit/tree/master/retrofit-adapters/rxjava2

retrofit-gson解析

https://github.com/square/retrofit/blob/46dc939a0dfb470b3f52edc88552f6f7ebb49f42/retrofit-converters/gson/README.md

2.添加网络权限

`<uses-permission android:name="android.permission.INTERNET"/>`

3.创建Retrofit实例

```
retrofit = new Retrofit.Builder()
        .baseUrl(BASE_URL)
        // 添加Gson支持
        .addConverterFactory(GsonConverterFactory.create())
        // 添加Rxjava2支持
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build();
```
4.创建接口返回数据的类(使用gsonFormat插件创建)，复制json字符串即可自动生成类，详见bean包

5.创建描述网络请求的接口 参考ApiServece

6.使用Rxjava模式发送网络请求 参考NetWorkController中的方法

下面是实际开发场景中可能用到的几种情况

![实际开发场景](https://images.gitee.com/uploads/images/2019/0414/193418_8c8e37d2_1435209.png "sjkf.png")
（1）网络请求轮询

     无条件循环：

![需求场景说明](https://images.gitee.com/uploads/images/2019/0414/195340_7b351774_1435209.png "wtjxh.png")
    
    栗子：详见NetWorkController中的loopGetArticleList()方法和loopGetArticleList2()方法，分别是有限次循环和无限次循环

    有条件循环:

![需求场景说明](https://images.gitee.com/uploads/images/2019/0414/193903_d3931b8a_1435209.png "ytjxh.png")

    栗子：详见NetworkController中的loopGetArticleByCondition()方法

(2) 网络请求嵌套回调

    如：一个网络请求成功后，才能进行下一个网络请求
    栗子：详见NetworkController中的getArticleAndData()方法

(3) 网络出错请求重连

    需求：
![需求场景说明](https://images.gitee.com/uploads/images/2019/0414/194214_56679a46_1435209.png "wlccxq.png")
    功能：
![功能说明](https://images.gitee.com/uploads/images/2019/0414/194254_4d5a3f24_1435209.png "wlccgn.png")
    逻辑：
![逻辑说明](https://images.gitee.com/uploads/images/2019/0414/194343_6bccdb32_1435209.png "wlcclj.png")

    栗子：详见NetworkController中的getArticleWhenError()方法

(4) 合并数据源

    需求：
![需求场景说明](https://images.gitee.com/uploads/images/2019/0414/194513_7d5c946a_1435209.png "hbsjy.png")   

    功能说明：
    同时向两个服务器发送网络请求->获取数据->合并数据->统一展示到客户端
    使用操作符：
    组合操作符：merge  组合多个被观察者一起发送数据，合并后按时间线并行执行  被观察者<=4个 >=4个时要用到mergeArray
    zip: 合并多个被观察者发送的事件，生成一个新的事件序列
    栗子：详见NetworkController中的mergeArticleAndLocalRequest()方法和zipArticleDoubleRequest()方法

 **RxJava操作符讲解：** 

1.创建操作符
![输入图片说明](https://images.gitee.com/uploads/images/2019/0421/222121_639ae988_1435209.png "createOperator.png")

    