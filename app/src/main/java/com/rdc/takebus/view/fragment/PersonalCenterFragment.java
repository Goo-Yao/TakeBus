package com.rdc.takebus.view.fragment;

import android.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.rdc.takebus.R;
import com.rdc.takebus.entity.Information;
import com.rdc.takebus.model.utils.AppConstants;
import com.rdc.takebus.model.utils.BitmapUtil;
import com.rdc.takebus.model.utils.OkHttpUtil;
import com.rdc.takebus.presenter.tbinterface.ResultListener;
import com.rdc.takebus.view.CustomView.CircleImageView;
import com.rdc.takebus.view.CustomView.CustomToast;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import dmax.dialog.SpotsDialog;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by 梦涵 on 2016/5/24.
 */
public class PersonalCenterFragment extends Fragment implements View.OnClickListener {
    private View view;
    private RelativeLayout rlHead, rlName, rlSex, rlCity, rlDescription;
    private TextView tvName, tvSex, tvCity, tvDescription;
    private CardView cardView;
    private CircleImageView imageHead;
    private Bitmap bitmap;
    private SpotsDialog dialog;
    //设置3个请求码，用来标示对应启动的Activity
    public static int CAMERA_REQUEST_CODE = 1;
    public static int GALLERY_REQUEST_CODE = 2;
    public static int CROP_REQUEST_CODE = 3;
    private Information mInformation;
    private String name, sex, description, city, headUrl;
    private Gson gson = new Gson();
    // file:///storage/emulated/0/IMG_20150726_182827路径
    private File tempFile = new File(Environment.getExternalStorageDirectory(),
            getPhotoFileName());

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (view == null) {
            AppConstants.isInfoChanging = true;
            view = inflater.inflate(R.layout.fragment_personal_center, container, false);
            initView(view);
            setListener();
            getPersonData();
            bitmap = BitmapUtil.readBitmap();
            if (bitmap == null) {
                imageHead.setImageBitmap(BitmapFactory.decodeResource(getActivity().getResources(), R.drawable.ic_default_user_icon_48dp));
            } else {
                imageHead.setImageBitmap(bitmap);
            }
        }
        return view;
    }

    private void getHeadImage(String url) {
        Log.e("resultUrl", url + ">>");
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(url).build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                CustomToast.showToast(getActivity(), "获取头像失败", Toast.LENGTH_SHORT);

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                bitmap = BitmapFactory.decodeStream(response.body().byteStream());
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        imageHead.setImageBitmap(bitmap);
                    }
                });
            }
        });
    }

    private void getPersonData() {
        dialog.show();
        OkHttpUtil.getInstance(AppConstants.REQUEST_TYPE_RDC).getData(AppConstants.URL_PERSON_INFO, new ResultListener() {
            @Override
            public void onResultSuccess(String result) {
                Log.e("result", result + ">>");
                if (result != null && !result.contains("html")) {

                    mInformation = gson.fromJson(result, Information.class);
                    headUrl = mInformation.getIconUrl();

                    name = mInformation.getNickName();
                    if (name == null)
                        name = "无";
                    tvName.setText(name);
                    AppConstants.userName = name;
                    if (mInformation.isSex())
                        sex = "男";
                    else
                        sex = "女";
                    tvSex.setText(sex);

                    city = mInformation.getAddress();
                    if (city == null)
                        city = "无";
                    tvCity.setText(city);
                    description = mInformation.getDescription();
                    if (description == null)
                        description = "无";
                    tvDescription.setText(description);
                    if (bitmap == null)
                        if (mInformation.getIconUrl() != null)
                            getHeadImage(mInformation.getIconUrl());
                } else
                    CustomToast.showToast(getActivity(), "获取数据失败", Toast.LENGTH_SHORT);
                dialog.dismiss();
            }

            @Override
            public void onResultFail(String result) {
                CustomToast.showToast(getActivity(), "获取数据失败", Toast.LENGTH_SHORT);
                dialog.dismiss();

            }
        }, getActivity());
    }

    private void initView(View view) {
        imageHead = (CircleImageView) view.findViewById(R.id.img_head);
        rlHead = (RelativeLayout) view.findViewById(R.id.rl_img_head);
        rlName = (RelativeLayout) view.findViewById(R.id.rl_name);
        rlSex = (RelativeLayout) view.findViewById(R.id.rl_sex);
        rlCity = (RelativeLayout) view.findViewById(R.id.rl_city);
        rlDescription = (RelativeLayout) view.findViewById(R.id.rl_description);
        tvDescription = (TextView) view.findViewById(R.id.tv_description);
        tvName = (TextView) view.findViewById(R.id.tv_name);
        tvSex = (TextView) view.findViewById(R.id.tv_sex);
        tvCity = (TextView) view.findViewById(R.id.tv_city);
        cardView = (CardView) view.findViewById(R.id.cardView);

        dialog = new SpotsDialog(getActivity());
    }

    private void setListener() {
        rlHead.setOnClickListener(this);
        rlName.setOnClickListener(this);
        rlSex.setOnClickListener(this);
        rlCity.setOnClickListener(this);
        rlDescription.setOnClickListener(this);
        cardView.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rl_img_head: {
                setHead();
            }
            break;
            case R.id.rl_name: {
                setName();
            }
            break;
            case R.id.rl_sex: {
                setSex();
            }
            break;
            case R.id.rl_city: {
                setCity();
            }
            break;
            case R.id.rl_description: {
                setDescription();
            }
            break;
            case R.id.cardView: {
                submit();
            }
            break;
        }
    }

    private void submit() {
        if (tvSex.getText().toString().equals("男"))
            sex = "true";
        else
            sex = "false";
        dialog.show();
        FormBody body = new FormBody.Builder().add("nickName", tvName.getText().toString()).add("sex", sex)
                .add("address", tvCity.getText().toString()).add("description", tvDescription.getText().toString()).build();
        final Request request = new Request.Builder().url(AppConstants.URL_UPDATE_PERSON_INFO).post(body).build();
        OkHttpUtil.getInstance(AppConstants.REQUEST_TYPE_RDC).postData(request, new ResultListener() {
            @Override
            public void onResultSuccess(String result) {
                Log.e("result", result + ">>");
                if (result.contains("success"))
                    CustomToast.showToast(getActivity(), "上传成功", Toast.LENGTH_SHORT);
                else
                    CustomToast.showToast(getActivity(), "上传失败：" + result, Toast.LENGTH_SHORT);
                dialog.dismiss();
            }

            @Override
            public void onResultFail(String result) {
                CustomToast.showToast(getActivity(), "上传失败", Toast.LENGTH_SHORT);
                dialog.dismiss();
            }
        }, getActivity());
    }

    private void setDescription() {
        showDialog("请输入简介", tvDescription);
    }

    private void showDialog(String title, final TextView tv) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View view = LayoutInflater.from(getActivity()).inflate(R.layout.dialog_name, null);
        final EditText et = (EditText) view.findViewById(R.id.et);
        builder.setTitle(title).setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                tv.setText(et.getText().toString());
                dialog.dismiss();
            }
        }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void setName() {
        showDialog("请输入昵称", tvName);
    }

    private void setCity() {
        String items[] = new String[]{"广州", "上海", "北京", "汕头", "深圳"};
        showArrayDialog(items, tvCity);
    }

    private void setHead() {
        final String items[] = new String[]{"拍照上传", "本地上传"};
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                if (which == 0)
                    takePhoto();
                else if (which == 1)
                    searchPhoto();
                dialog.dismiss();
            }


        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void searchPhoto() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        //设置类型为图片类型
        intent.setType("image/*");
        startActivityForResult(intent, GALLERY_REQUEST_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE) {
            // 它没有用到intent数据，也就是说它的intent 为空，即data == null
            // 如果取消了拍照，data的值为 Intent[ ] ， 不是null
            // 接下来调用系统截图进行裁剪
            if (data == null)
                startImageZoom(Uri.fromFile(tempFile));
            else
                return; // 如果不拍照的话
        } else if (requestCode == GALLERY_REQUEST_CODE) {
            // 区别于拍照，从图库获取图片会返回一个intent数据类型，
            // 如果为 null，则表示没有选择
            // 接下来调用系统截图进行裁剪
            if (data == null) // 没有选择图片或者返回
                return;
            else {
                Uri uri = data.getData();
                Log.e("uri", data.getData().toString() + ">>");
                startImageZoom(uri);
            }
        } else if (requestCode == CROP_REQUEST_CODE) {
            if (data != null) {
                // 截图后 图片 的数据存储 (Intent)data中
                // 利用Bundle.getParcelable(key)解析出Bitmap
                // Bitmap本身实现了Parcelable接口，可以载入对象
                Bundle bundle = data.getExtras();
                bitmap = bundle.getParcelable("data");
                imageHead.setImageBitmap(bitmap);
                BitmapUtil.saveBitmap(bitmap);
                uploadHeadImage();
            }
        }
    }

    private void uploadHeadImage() {
        dialog.show();
        // 图片类型
        MediaType mMediaType = MediaType.parse("imag/*");
        File file = BitmapUtil.getImageHeadFile();
        MultipartBody.Builder builder = new MultipartBody.Builder().addFormDataPart("iconFile", file.getName(), RequestBody.create(mMediaType, file));
        MultipartBody body = builder.build();
        Request request = new Request.Builder().url(AppConstants.URL_UPDATE_HEAD_IMAGE).post(body).build();
        OkHttpUtil.getInstance(AppConstants.REQUEST_TYPE_RDC).postData(request, new ResultListener() {
            @Override
            public void onResultSuccess(String result) {
                Log.e("resultImage", result + ">>");
                if (result.contains("http"))
                    CustomToast.showToast(getActivity(), "上传成功", Toast.LENGTH_SHORT);
                else
                    CustomToast.showToast(getActivity(), "上传失败", Toast.LENGTH_SHORT);
                dialog.dismiss();
            }

            @Override
            public void onResultFail(String result) {
                CustomToast.showToast(getActivity(), "上传失败", Toast.LENGTH_SHORT);
                dialog.dismiss();
            }
        }, getActivity());
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (bitmap != null)
            bitmap.recycle();
    }

    //该方法用来调取系统截图
    public void startImageZoom(Uri uri) { // 向图像裁剪器传递Uri
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");// 设置数据和类型，表示裁剪图像
        intent.putExtra("crop", true); // 设置view为可裁剪
        intent.putExtra("aspectX", 1);// 裁剪的宽高比例
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 150);// 裁剪图片的宽高，最终得到的输出图片的宽高
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);// 裁剪后的数据通过intent返回回来
        intent.putExtra("noFaceDetection", true);
        startActivityForResult(intent, CROP_REQUEST_CODE);
    }

    //调用系统拍照功能
    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //拍照完毕图片存在自己定义的路径中
        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(tempFile));
        startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }

    private void showArrayDialog(final String items[], final TextView tv) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                tv.setText(items[which]);
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void setSex() {
        final String items[] = new String[]{"男", "女"};
        showArrayDialog(items, tvSex);
    }

    //该方法用来设置图片的名字，根据 拍照时间设置，是给“拍照”用的
    private String getPhotoFileName() {
        Date date = new Date(System.currentTimeMillis());
        // 指定时间格式,IMG_20150726_182827 类型
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "'IMG'_yyyyMMdd_HHmmss");
        return dateFormat.format(date) + ".jpg";
    }

}
