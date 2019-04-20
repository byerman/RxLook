package com.huaweisoft.retrofitdemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.huaweisoft.retrofitdemo.bean.ArticleDataBean;
import com.huaweisoft.retrofitdemo.bean.ArticleList;
import com.huaweisoft.retrofitdemo.bean.LoginBean;
import com.huaweisoft.retrofitdemo.util.CookieUtil;
import com.huaweisoft.retrofitdemo.util.ParseErrorUtil;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.huaweisoft.retrofitdemo.config.UrlConfig.BASE_URL;

/**
 * @author baiaj
 * 网络控制器
 */
public class NetWorkController {

    private static final String TAG = NetWorkController.class.getSimpleName();
    @SuppressLint("StaticFieldLeak")
    private volatile static NetWorkController mInstance = null;
    private Context mContext;
    private Retrofit retrofit;
    // 设置变量 = 模拟轮询服务器次数
    private int i = 0;

    // 可重试次数
    private int maxConnectCount = 10;
    // 当前已重试次数
    private int currentRetryCount = 0;
    // 重试等待时间
    private int waitRetryTime = 0;

    private NetWorkController(Context context) {
        mContext = context.getApplicationContext();
        initRetrofit();
    }

    public static NetWorkController getmInstance(Context context) {
        if (mInstance == null) {
            synchronized (NetWorkController.class) {
                if (mInstance == null) {
                    mInstance = new NetWorkController(context);
                }
            }
        }
        return mInstance;
    }

    private void initRetrofit() {
        retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                // 添加Gson支持
                .addConverterFactory(GsonConverterFactory.create())
                // 添加Rxjava2支持
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
    }

    /**
     * 获取公众号列表
     */
    public void getArticleList(ModelEnum.CallAdapter callAdapter) {
        if (callAdapter == ModelEnum.CallAdapter.normal) {
            getArticleListByNormal();
        }else {
            getArticleListByRx();
        }
    }

    /**
     * 有限次循环获取数据
     */
    public void loopGetArticleList() {
        // 第一个参数:事件序列起始点
        // 第二个参数:事件数量
        // 第三个参数:第一次事件延迟发送时间
        // 第四个参数:事件发送间隔时间
        // 第五个参数:时间单位
        Observable.intervalRange(0,10,5,10,TimeUnit.SECONDS)
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) {
                        Log.d(TAG,"第" + aLong + "次循环");
                        getArticleListByRx();
                    }
                }).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Long aLong) {
                Log.d(TAG,"onNext:" + aLong);
            }

            @Override
            public void onError(Throwable e) {
                Log.d(TAG,"onError:" + e.toString());
            }

            @Override
            public void onComplete() {
                Log.d(TAG,"onComplete");
            }
        });
    }

    /**
     * 无限循环获取数据
     */
    public void loopGetArticleList2() {
        // 参数1:初始延迟时间
        // 参数2:间隔时间
        // 参数3:时间单位
        Observable.interval(5,10,TimeUnit.SECONDS)
                .doOnNext(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        Log.d(TAG,"第" + aLong + "次循环");
                        getArticleListByRx();
                    }
                }).subscribe(new Observer<Long>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(Long aLong) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    /**
     * 有条件的循环获取数据
     */
    public void loopGetArticleByCondition() {
        // 创建网络请求接口的实例
        ApiService apiService = retrofit.create(ApiService.class);
        // 采用Observable<T>形式对网络请求进行封装
        Observable<ArticleList> observable = apiService.getArticleListByRx();
        // 发送网络请求并使用reapeatWhen进行有条件循环
        observable.repeatWhen(new Function<Observable<Object>, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(final Observable<Object> objectObservable) throws Exception {
                // 使用flatMap()操作符接收上游的数据并进行处理
                return objectObservable.flatMap(new Function<Object, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Object o) {
                        // 当循环次数等于5次，停止轮询
                        if (i > 3) {
                            return Observable.error(new Throwable("轮询结束"));
                        }
                        // 如果轮询小于4次,则继续发送onNext事件
                        return Observable.just(o).delay(2000, TimeUnit.MILLISECONDS);
                    }
                });
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArticleList>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ArticleList articleList) {
                        if (articleList != null) {
                            List<ArticleList.DataBean> dataBeanList = articleList.getData();
                            for (ArticleList.DataBean bean : dataBeanList) {
                                Log.d(TAG,"第" + (i+1) + "次循环,articleData:" + bean.getName());
                            }
                            i++;
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG,e.toString());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 获取公众号列表后再获取公众号数据
     */
    @SuppressLint("CheckResult")
    public void getArticleAndData() {
        // 创建网络请求接口实例
        final ApiService apiService = retrofit.create(ApiService.class);
        // 创建Observable<T>进行网络请求
        Observable<ArticleList> observable1 = apiService.getArticleListByRx();
        // 发送网络请求
        observable1.subscribeOn(Schedulers.io()) // 在IO线程进行网络请求
                    .observeOn(AndroidSchedulers.mainThread())  // 在主线程处理请求结果
                    .doOnNext(new Consumer<ArticleList>() {
                        @Override
                        public void accept(ArticleList articleList) throws Exception {
                            ArticleList.DataBean dataBean = articleList.getData().get(0);
                            Log.d(TAG,"请求公众号列表成功:" + dataBean.getName() +
                                    "id:" + dataBean.getId());
                        }
                    })
                .observeOn(Schedulers.io()) // 新被观察者切换到IO线程进行网络请求
                .flatMap(new Function<ArticleList, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(ArticleList articleList) {
                        // 将observable1产生的事件进行重新组装再发送，即嵌套网络请求
                        Observable<ArticleDataBean> observable = apiService.getArticleData(articleList.getData().get(0).getId(), 1);
                        return observable;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread()) // 再次切换回主线程进行数据处理
                .subscribe(new Consumer<Object>() {

                    @Override
                    public void accept(Object o) throws Exception {
                        ArticleDataBean bean = (ArticleDataBean) o;
                        Log.d(TAG,"获取公众号数据成功:" + bean.toString());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        // 统一的错误处理(Observable1和observable2事件出现错误都会进入这里)
                        Log.d(TAG,"获取数据失败:" + throwable.toString());
                    }
                });
    }

    /**
     * 网络请求出错后重连
     */
    public void getArticleWhenError() {
        // 创建网路请求接口的实例
        ApiService apiService = retrofit.create(ApiService.class);
        // 采用Observable<T>形式对网络请求进行封装
        final Observable observable = apiService.getArticleListByRx();
        // 发送网络请求(主要异常才会回调retryWhen进行重试)
        // retryWhen功能操作符，当新的观察者发送的事件为error事件，则不重新发送事件
        // 如果发送的事件为onNext事件，则重新发送事件
        observable.retryWhen(new Function<Observable<Throwable>, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(final Observable<Throwable> objectObservable) throws Exception {
                return objectObservable.flatMap(new Function<Throwable, ObservableSource<?>>() {
                    @Override
                    public ObservableSource<?> apply(Throwable throwable) throws Exception {
                        Log.d(TAG,"发生异常:" + throwable.toString());
                        // 当发生的异常 = 网络异常 = IO异常时，才选择重试
                        if (throwable instanceof IOException) {
                            Log.d(TAG,"属于IO异常，需要重试");
                            // 当已重试次数 < 设置的重试次数，才选择重试
                            if (currentRetryCount < maxConnectCount) {
                                // 记录重试次数
                                currentRetryCount++;
                                Log.d(TAG, "重试次数 =" + currentRetryCount);
                                // 设置等待时间
                                waitRetryTime = 1000 + currentRetryCount * 1000;
                                Log.d(TAG,"等待时间:" + waitRetryTime);
                                // 通过返回的Observable发送的事件 = Next事件，从而使得retryWhen（）重订阅，最终实现重试功能
                                return Observable.just(1).delay(waitRetryTime, TimeUnit.MILLISECONDS);
                            } else {
                                return Observable.error(new Throwable("重试次数已超过设置次数 = " + currentRetryCount));
                            }

                        } else {
                            // 如果发生的异常不等于IO异常，则不重试
                            return Observable.error(new Throwable("发生了非网络异常(IO异常)"));
                        }
                    }
                });
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ArticleList>(){

                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ArticleList articleList) {
                        List<ArticleList.DataBean> dataBeanList = articleList.getData();
                        for (ArticleList.DataBean bean : dataBeanList) {
                            Log.d(TAG,"articleData:" + bean.getName());
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    /**
     * 合并公众号列表的网络请求和本地数据请求
     */
    @SuppressLint("CheckResult")
    public void mergeArticleAndLocalRequest() {
        // 创建网络请求接口的实例
        ApiService apiService = retrofit.create(ApiService.class);
        // 采用Observable<T>形式对网络请求进行封装
        Observable<ArticleList> networkObservable = apiService.getArticleListByRx();
        // 获取本地数据
        Observable<String> localObservable = Observable.just("本地数据");
        // 合并事件
        Observable.merge(networkObservable,localObservable)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Object>() {

                    // 这里会轮流收到网络事件和本地事件
                    @Override
                    public void accept(Object o) throws Exception {
                        Log.d(TAG,"收到数据:" + o.toString());
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d(TAG,"发生异常:" + throwable);
                    }
                });
    }

    /**
     * 合并两次请求
     */
    public void zipArticleDoubleRequest() {
        // 创建网络请求接口的实例
        ApiService apiService = retrofit.create(ApiService.class);
        // 采用Observable<T>形式对网络请求进行封装
        Observable<ArticleList> observable = apiService.getArticleListByRx().subscribeOn(Schedulers.io());
        Observable<ArticleDataBean> observable2 = apiService.getArticleData(408,1).subscribeOn(Schedulers.io());
        // 发送网络请求(BiFunction传入的前两个参数是两个Observable发送的数据类型，第三个参数是合并后的数据类型)
        Observable.zip(observable, observable2, new BiFunction<ArticleList, ArticleDataBean, String>() {

            @Override
            public String apply(ArticleList articleList, ArticleDataBean articleDataBean) throws Exception {
                // 返回组装过后的数据
                return articleList.getData().get(0).getName() + ":" + articleDataBean.toString();
            }
        }).observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        Log.d(TAG,"收到合并后的数据:" + s);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        // 两次请求中发生异常都回回调到这里
                        Log.d(TAG,"发生异常:" + throwable.toString());
                    }
                });

    }

    private void getArticleListByNormal() {
        // 创建网络请求接口的实例
        ApiService apiService = retrofit.create(ApiService.class);
        // 采用Call<T>接口对发送请求进行封装
        Call<ArticleList> call = apiService.getArticleList();
        // 发送网络请求(异步)
        call.enqueue(new Callback<ArticleList>() {
            // 成功时回调
            @Override
            public void onResponse(Call<ArticleList> call, Response<ArticleList> response) {
                ArticleList articleList = response.body();
                if (articleList != null) {
                    List<ArticleList.DataBean> dataBeanList = articleList.getData();
                    for (ArticleList.DataBean bean : dataBeanList) {
                        Log.d(TAG,"articleData:" + bean.getName());
                    }
                }
            }
            // 失败时回调
            @Override
            public void onFailure(Call<ArticleList> call, Throwable t) {
                Log.d(TAG,t.toString());
            }
        });
    }

    private void getArticleListByRx() {
        // 创建网络请求接口的实例
        ApiService apiService = retrofit.create(ApiService.class);
        // 采用Observable<T>形式对网络请求进行封装
        Observable<ArticleList> observable = apiService.getArticleListByRx();
        // 发送网络请求
        observable.subscribeOn(Schedulers.io()) // 在IO线程进行网络请求
                    .observeOn(AndroidSchedulers.mainThread()) // 在主线程处理请求结果
                    .subscribe(new Observer<ArticleList>() {
                        // 发送请求后调用该复写方法（无论请求成功与否)
                        @Override
                        public void onSubscribe(Disposable d) {


                        }
                        // 发送请求成功后调用该复写方法
                        @Override
                        public void onNext(ArticleList articleList) {
                            if (articleList != null) {
                                List<ArticleList.DataBean> dataBeanList = articleList.getData();
                                for (ArticleList.DataBean bean : dataBeanList) {
                                    Log.d(TAG,"articleData:" + bean.getName());
                                }
                            }
                        }
                        // 发送请求失败后调用该复写方法
                        @Override
                        public void onError(Throwable e) {
                            Log.d(TAG,e.toString());
                        }
                        // 发送请求成功后，先调用onNext()再调用该复写方法
                        @Override
                        public void onComplete() {
                            Log.d(TAG,"发送请求成功");
                        }
                    });
    }

    /**
     * 登录
     * @param name 账号
     * @param password 密码
     */
    public void login(String name, String password) {
        ApiService apiService = retrofit.create(ApiService.class);
        Call<LoginBean> call = apiService.login(name,password);
        call.enqueue(new Callback<LoginBean>() {
            @Override
            public void onResponse(Call<LoginBean> call, Response<LoginBean> response) {
                LoginBean bean = response.body();
                if (ParseErrorUtil.parseError(bean.getErrorCode(),bean.getErrorMsg(),mContext)) {
                    okhttp3.Headers headers = response.headers();
                    CookieUtil.saveCookie(headers);
                    Log.d(TAG,"loginResult:" + bean.toString());
                    Log.d(TAG,"headers:" + headers.toString());
                }
            }

            @Override
            public void onFailure(Call<LoginBean> call, Throwable t) {
                Log.d(TAG,t.toString());
            }
        });
    }

}
