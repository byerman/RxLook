package com.huaweisoft.retrofitdemo.view;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.AnimationSet;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.huaweisoft.retrofitdemo.R;
import com.huaweisoft.retrofitdemo.util.UIUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 点击弹出信息View
 */
public class PoPUpView extends RelativeLayout {

    /**
     * 每一个数据对应动画缩放的时长
     */
    private static final int SCALE_DURATION = 100;
    /**
     * 底部弹出按钮
     */
    private ImageButton ibtnPopUp;
    /**
     * 第一层列表
     */
    private ListView firstListView;
    /**
     * 第二层列表
     */
    private ListView secondListView;
    /**
     * 第一层数据
     */
    private List<String> firstDatas = new ArrayList<>();
    /**
     * 第二层数据
     */
    private List<List<String>> secondDatas = new ArrayList<>();
    private List<String> currentDatas = new ArrayList<>();
    private int currentIndex = 0;
    private PoPUpAdapter firstAdapter;
    private PoPUpAdapter secondAdapter;
    /**
     * 位移动画
     */
    private TranslateAnimation translateAnimation;
    /**
     * 缩放动画开始
     */
    private ScaleAnimation scaleAnimationStart;
    /**
     * 缩放动画结束
     */
    private ScaleAnimation scaleAnimationEnd;
    /**
     * 透明动画
     */
    private AlphaAnimation alphaAnimation;
    /**
     * 动画集合
     */
    private AnimationSet animationSet;

    public PoPUpView(Context context) {
        this(context,null);
    }

    public PoPUpView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    public PoPUpView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context,attrs,defStyleAttr);
        init();
    }

    /**
     * 设置第一层显示的数据
     * @param datas 列表数据
     */
    public void setFirstData(List<String> datas) {
        firstDatas = datas;
        initFirstView();
    }

    /**
     * 设置第二层的数据
     * @param datas 列表数据
     */
    public void setSecondData(List<List<String>> datas) {
        secondDatas = datas;
        initSecondView();
    }

    private void init() {
        initPopIcon();
        mergeView();
        initAnimation();
    }

    /**
     * 初始化底部popIcon
     */
    @SuppressLint("ResourceType")
    private void initPopIcon() {
        ibtnPopUp = new ImageButton(getContext());
        ibtnPopUp.setBackgroundResource(R.drawable.ic_popup_bg);
        RelativeLayout.LayoutParams params = new LayoutParams(UIUtils.dipToPx(getContext(),48),UIUtils.dipToPx(getContext(),48));
        params.addRule(ALIGN_PARENT_BOTTOM);
        params.addRule(ALIGN_PARENT_RIGHT);
        params.bottomMargin = UIUtils.dipToPx(getContext(),18);
        params.rightMargin = UIUtils.dipToPx(getContext(),12);
        ibtnPopUp.setLayoutParams(params);
        ibtnPopUp.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                setIbtnPopUpCenter();
                firstListView.setVisibility(View.VISIBLE);
                firstListView.startAnimation(animationSet);
                ibtnPopUp.setClickable(false);
            }
        });
    }

    /**
     * 初始化第一层View
     */
    private void initFirstView() {
        if (firstDatas != null) {
            firstListView = new ListView(getContext());
            firstAdapter = new PoPUpAdapter(getContext(),firstDatas,new FirstClickListener());
            firstListView.setAdapter(firstAdapter);
            RelativeLayout.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.addRule(ALIGN_PARENT_BOTTOM);
            params.addRule(ALIGN_PARENT_RIGHT);
            params.setMargins(UIUtils.dipToPx(getContext(),10),0,
                    UIUtils.dipToPx(getContext(),10),UIUtils.dipToPx(getContext(),88));
            firstListView.setLayoutParams(params);
            firstListView.setDividerHeight(0);
            addView(firstListView);
            firstListView.setVisibility(View.GONE);
        }
    }

    /**
     * 初始化第二层View
     */
    private void initSecondView() {
        if (secondDatas != null && secondDatas.size() > 0) {
            secondListView = new ListView(getContext());
            currentDatas.addAll(secondDatas.get(currentIndex));
            secondAdapter = new PoPUpAdapter(getContext(),currentDatas,new SecondClickListener());
            secondListView.setAdapter(secondAdapter);
            RelativeLayout.LayoutParams params = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            params.addRule(ALIGN_PARENT_BOTTOM);
            params.addRule(ALIGN_PARENT_RIGHT);
            params.setMargins(UIUtils.dipToPx(getContext(),10),0,
                    UIUtils.dipToPx(getContext(),10),UIUtils.dipToPx(getContext(),88));
            secondListView.setLayoutParams(params);
            secondListView.setVisibility(View.GONE);
            secondListView.setDividerHeight(0);
            addView(secondListView);
        }
    }

    /**
     * 合并View
     */
    private void mergeView() {
        addView(ibtnPopUp);
    }

    /**
     * 初始化动画
     */
    private void initAnimation() {
        initTranslateAnimation();
        initScaleAnimation();
        initAlphaAnimation();
        animationSet = new AnimationSet(true);
        animationSet.addAnimation(translateAnimation);
        animationSet.addAnimation(alphaAnimation);
        animationSet.setDuration(600);
    }

    /**
     * 初始化位移动画
     */
    private void initTranslateAnimation() {
        translateAnimation = new TranslateAnimation(0.0f,0.0f,300.0f,0.0f);
    }

    /**
     * 初始化缩放动画
     */
    private void initScaleAnimation() {
        scaleAnimationStart = new ScaleAnimation(1.0f,1.0f,0.1f,1.0f);
        scaleAnimationEnd = new ScaleAnimation(1.0f,1.0f,1.0f,0.1f);
    }

    /**
     * 初始化透明度动画
     */
    private void initAlphaAnimation() {
        alphaAnimation = new AlphaAnimation(0.3f,1.0f);
    }

    private void setIbtnPopUpCenter() {
        RelativeLayout.LayoutParams params = new LayoutParams(UIUtils.dipToPx(getContext(),48),UIUtils.dipToPx(getContext(),48));
        params.addRule(ALIGN_PARENT_BOTTOM);
        params.addRule(CENTER_HORIZONTAL);
        params.bottomMargin = UIUtils.dipToPx(getContext(),18);
        ibtnPopUp.setLayoutParams(params);
    }

    private void setIbtnPopUpRight() {
        RelativeLayout.LayoutParams params = new LayoutParams(UIUtils.dipToPx(getContext(),48),UIUtils.dipToPx(getContext(),48));
        params.addRule(ALIGN_PARENT_BOTTOM);
        params.addRule(ALIGN_PARENT_RIGHT);
        params.bottomMargin = UIUtils.dipToPx(getContext(),18);
        params.rightMargin = UIUtils.dipToPx(getContext(),12);
        ibtnPopUp.setLayoutParams(params);
    }

    /**
     * 第一层View点击监听
     */
    private class FirstClickListener implements PoPUpAdapter.ItemClickListener{

        @Override
        public void onClick(int position) {
            if (secondDatas.size() == 0) {
                firstListView.setVisibility(View.GONE);
                setIbtnPopUpRight();
                ibtnPopUp.setClickable(true);
                mListener.onClick(position,0);
            } else {
                if (currentIndex != position) {
                    currentDatas.clear();
                    currentDatas.addAll(secondDatas.get(position));
                    secondAdapter.notifyDataSetChanged();
                    currentIndex = position;
                }
                firstListView.setVisibility(View.GONE);
                secondListView.setVisibility(View.VISIBLE);
                scaleAnimationStart.setDuration(SCALE_DURATION * currentDatas.size());
                secondListView.startAnimation(scaleAnimationStart);
            }
        }
    }

    private class SecondClickListener implements PoPUpAdapter.ItemClickListener {

        @Override
        public void onClick(int position) {
            setIbtnPopUpRight();
            scaleAnimationEnd.setDuration(SCALE_DURATION * currentDatas.size());
            secondListView.startAnimation(scaleAnimationEnd);
            secondListView.setVisibility(View.GONE);
            ibtnPopUp.setClickable(true);
            mListener.onClick(currentIndex,position);
        }
    }

    /**
     * PoPuPView点击监听
     */
    public interface IPopUpClickListener {
        /**
         * 点击的位置
         * @param currentIndex 传入第二个列表的index
         * @param position 传入第二个列表的index个列表中的position
         */
        void onClick(int currentIndex,int position);
    }

    private IPopUpClickListener mListener = null;

    public void setPopUpClickListener(IPopUpClickListener listener) {
        mListener = listener;
    }
}
