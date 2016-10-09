package com.rdc.takebus.model.holder;

import android.media.Image;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.rdc.takebus.R;

/**
 * Created by ZZH on 2016/5/20.
 */
public class BottomHolder extends RecyclerView.ViewHolder {
    public static int ALIPAY_TAG = 1;
    public static int WECHAT_TAG = 2;
    //价格
    private TextView tvPrice;
    //路线名字
    private TextView tvRoute;
    private RelativeLayout rlAlipay;
    private RelativeLayout rlWechat;
    private int payTag = 0;
    private ImageView imgAli;
    private ImageView imgWechat;
    private CardView cardView;
    private TextView tvCardView;

    public TextView getTvCardView() {
        return tvCardView;
    }

    public void setTvCardView(TextView tvCardView) {
        this.tvCardView = tvCardView;
    }

    public BottomHolder(View itemView) {
        super(itemView);
        tvPrice = (TextView) itemView.findViewById(R.id.tv_price);
        tvRoute = (TextView) itemView.findViewById(R.id.tv_route);
        rlAlipay = (RelativeLayout) itemView.findViewById(R.id.rl_alipay);
        rlWechat = (RelativeLayout) itemView.findViewById(R.id.rl_wechat);
        imgAli = (ImageView) itemView.findViewById(R.id.img_circle_alipay);
        imgWechat = (ImageView) itemView.findViewById(R.id.img_circle_wechat);
        cardView = (CardView) itemView.findViewById(R.id.cardView);
        tvCardView = (TextView) itemView.findViewById(R.id.tv_cardView);
    }

    public TextView getTvPrice() {
        return tvPrice;
    }

    public void setTvPrice(TextView tvPrice) {
        this.tvPrice = tvPrice;
    }

    public TextView getTvRoute() {
        return tvRoute;
    }

    public void setTvRoute(TextView tvRoute) {
        this.tvRoute = tvRoute;
    }

    public RelativeLayout getRlAlipay() {
        return rlAlipay;
    }

    public void setRlAlipay(RelativeLayout rlAlipay) {
        this.rlAlipay = rlAlipay;
    }

    public RelativeLayout getRlWechat() {
        return rlWechat;
    }

    public void setRlWechat(RelativeLayout rlWechat) {
        this.rlWechat = rlWechat;
    }

    public int getPayTag() {
        return payTag;
    }

    public void setPayTag(int payTag) {
        this.payTag = payTag;
    }

    public ImageView getImgAli() {
        return imgAli;
    }

    public void setImgAli(ImageView imgAli) {
        this.imgAli = imgAli;
    }

    public ImageView getImgWechat() {
        return imgWechat;
    }

    public void setImgWechat(ImageView imgWechat) {
        this.imgWechat = imgWechat;
    }

    public CardView getCardView() {
        return cardView;
    }

    public void setCardView(CardView cardView) {
        this.cardView = cardView;
    }

}
