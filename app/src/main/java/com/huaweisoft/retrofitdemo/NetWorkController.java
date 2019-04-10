package com.huaweisoft.retrofitdemo;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;

import com.huaweisoft.retrofitdemo.bean.ArticleList;
import com.huaweisoft.retrofitdemo.bean.LoginBean;
import com.huaweisoft.retrofitdemo.util.CookieUtil;
import com.huaweisoft.retrofitdemo.util.ParseErrorUtil;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
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
