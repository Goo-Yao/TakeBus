package com.rdc.takebus.model.utils;

import android.animation.ValueAnimator;
import android.content.Context;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.OvershootInterpolator;
import android.view.animation.RotateAnimation;

import com.rdc.takebus.R;


/**
 * 生成动画的工具
 */
public class AnimationUtil {
    private static int mShowMenuTime = 200;
    public static final int TO_HIDE = 0;
    public static final int TO_SHOW = 1;

    /**
     * 旋转动画
     *
     * @param fabView
     * @param mRotateDegree
     * @return
     */
    public static Animation getFABRotateAnimDinning(View fabView, int mRotateDegree) {
        Animation rotateAnimation = new RotateAnimation(-mRotateDegree, -(270 + mRotateDegree),
                fabView.getWidth() / 2, fabView.getHeight() / 2);
        rotateAnimation.setDuration(mShowMenuTime);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setInterpolator(new OvershootInterpolator());
        return rotateAnimation;
    }

    /**
     * 缩放动画
     *
     * @param context
     * @return
     */
    public static Animation getScaleToShowAnimDinning(Context context) {
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.scale_to_show);
        animation.setDuration(mShowMenuTime);
        animation.setInterpolator(new OvershootInterpolator());  // 设置插值器
        return animation;
    }


    public static Animation getFABRotateAnimDining_2(View fabView, int mRotateDegree) {
        Animation rotateAnimation = new RotateAnimation(-(270 + mRotateDegree), -mRotateDegree,
                fabView.getWidth() / 2, fabView.getHeight() / 2);
        rotateAnimation.setDuration(mShowMenuTime);
        rotateAnimation.setFillAfter(true);
        rotateAnimation.setInterpolator(new OvershootInterpolator());
        return rotateAnimation;
    }

    public static Animation getScaleToHideAnimDinning(Context context) {
        Animation scaleAnimation = AnimationUtils.loadAnimation(context, R.anim.scale_to_hide);
        scaleAnimation.setDuration(mShowMenuTime);
        scaleAnimation.setFillAfter(true);
        return scaleAnimation;
    }

    public static ValueAnimator getAlphaAnimatorDining(final View v, int type) {
        int beginValue, endValue;
        if (type == TO_HIDE) {
            beginValue = 1;
            endValue = 0;
        } else {
            beginValue = 0;
            endValue = 1;
        }
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(beginValue, endValue);
        valueAnimator.setDuration(mShowMenuTime);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float tempValue = (float) animation.getAnimatedValue();
                v.setAlpha(tempValue);
            }
        });
        return valueAnimator;
    }
}
