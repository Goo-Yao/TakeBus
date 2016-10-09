package com.rdc.takebus.view.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.baidu.mapapi.search.poi.PoiSearch;
import com.baidu.mapapi.search.sug.OnGetSuggestionResultListener;
import com.baidu.mapapi.search.sug.SuggestionResult;
import com.baidu.mapapi.search.sug.SuggestionSearch;
import com.baidu.mapapi.search.sug.SuggestionSearchOption;
import com.rdc.takebus.R;
import com.rdc.takebus.base.BaseSwipeBackActivity;
import com.rdc.takebus.model.adapter.SearchSuggestListAdapter;
import com.rdc.takebus.model.utils.AppConstants;
import com.rdc.takebus.presenter.ActivityPresenter.SearchPresenter;
import com.rdc.takebus.presenter.tbinterface.SearchStationViewInterface;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;


/**
 * 设置站点界面
 * Created by 梦涵 on 2016/5/10.
 */
public class SearchStationActivity extends BaseSwipeBackActivity<SearchStationViewInterface, SearchPresenter>
        implements SearchStationViewInterface, OnGetSuggestionResultListener, View.OnClickListener {

    private TextView tvCancle, tvTips;

    private RecyclerView rvSuggestStationList;
    private RecyclerView.LayoutManager layoutManager;
    private SearchSuggestListAdapter searchSuggestListAdapter;
    private ProgressBar pb;
    private String mStrResult;
    private int stationTag;


    private PoiSearch mPoiSearch = null;
    private EditText keyWorldsView = null;
    private SuggestionSearch mSuggestionSearch = null;
    private List<String> suggest = new ArrayList<String>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_search);
        Intent intentFromSR = getIntent();
        if (intentFromSR != null) {
            Bundle bundle = intentFromSR.getExtras();
            stationTag = bundle.getInt("stationTag");
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    protected SearchPresenter createPresenter() {
        return new SearchPresenter(this);
    }


    public void loadData() {

    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        findAllViewById();
        setAllOnClickListener();
        initSuggestList();
        initSuggestionSearch();
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {

                           public void run() {
                               InputMethodManager inputManager =
                                       (InputMethodManager) keyWorldsView.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                               inputManager.showSoftInput(keyWorldsView, 0);
                           }

                       },
                300);
    }

    private void initSuggestList() {
        layoutManager = new LinearLayoutManager(this);
        rvSuggestStationList.setLayoutManager(layoutManager);
        rvSuggestStationList.setHasFixedSize(true);
        searchSuggestListAdapter = new SearchSuggestListAdapter(this);
        searchSuggestListAdapter.setOnSuggestItemClickListener(new SearchSuggestListAdapter.OnSuggestItemClickListener() {
            @Override
            public void onItemClick(View view, String strSuggest) {
                keyWorldsView.setText(strSuggest);
                Intent intent = new Intent();
                Bundle bundle = new Bundle();
                bundle.putInt("stationTag", stationTag);
                bundle.putString("stationName", strSuggest);
                intent.putExtras(bundle);
                SearchStationActivity.this.setResult(RESULT_OK, intent);
                finish();
                setPendingTransition(AppConstants.OUT_OVERPENDINGTRANSITION);
            }
        });
        rvSuggestStationList.setAdapter(searchSuggestListAdapter);
    }

    private void initSuggestionSearch() {
        mSuggestionSearch = SuggestionSearch.newInstance();
        mSuggestionSearch.setOnGetSuggestionResultListener(this);
        //当输入关键字变化时，动态更新建议列表
        keyWorldsView.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable arg0) {

            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1,
                                          int arg2, int arg3) {

            }

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2,
                                      int arg3) {
                //未输入的情况
                if (cs.length() <= 0) {
                    suggest.clear();
                    pb.setVisibility(View.GONE);
                    tvTips.setVisibility(View.VISIBLE);
                    tvTips.setText("请输入站点信息");
                    rvSuggestStationList.setVisibility(View.GONE);
                    return;
                }

                //已输入的情况
                String city = mPresenter.getCurrentCity();
                if (city != null) {
                    tvTips.setVisibility(View.GONE);
                    rvSuggestStationList.setVisibility(View.GONE);
                    pb.setVisibility(View.VISIBLE);
                    mSuggestionSearch
                            .requestSuggestion((new SuggestionSearchOption())
                                    .keyword(cs.toString()).city(city));
                } else {
                    tvTips.setVisibility(View.VISIBLE);
                    tvTips.setText("网络不佳，请稍候重试");
                }
            }
        });
    }

    private void setAllOnClickListener() {
        tvCancle.setOnClickListener(this);
    }

    private void findAllViewById() {
        tvTips = (TextView) findViewById(R.id.tv_tips);
        rvSuggestStationList = (RecyclerView) findViewById(R.id.rv_station);
        keyWorldsView = (EditText) findViewById(R.id.et_search);
        keyWorldsView.setHint(getIntent().getStringExtra("station"));
        tvCancle = (TextView) findViewById(R.id.tv_cancel);
        pb = (ProgressBar) findViewById(R.id.pb);
    }

    @Override
    protected void initVariables() {
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_cancel:
                finish();
                setPendingTransition(AppConstants.OUT_OVERPENDINGTRANSITION);
                break;
            default:
                break;
        }

    }


    @Override
    public void showStationList(List<String> suggestData) {
        if (suggestData.size() > 0) {
            tvTips.setVisibility(View.GONE);
            pb.setVisibility(View.GONE);
            rvSuggestStationList.setVisibility(View.VISIBLE);
            searchSuggestListAdapter.setData(suggestData);
            searchSuggestListAdapter.notifyDataSetChanged();
        } else {

        }
    }

    @Override
    public void selectStation() {

    }


    @Override
    public void onGetSuggestionResult(SuggestionResult res) {

        if (res == null || res.getAllSuggestions() == null) {
            tvTips.setVisibility(View.VISIBLE);
            tvTips.setText("无相关地点信息");
            rvSuggestStationList.setVisibility(View.GONE);
            return;
        }
        if (suggest.size() > 0) {
            suggest.clear();
        }
        for (SuggestionResult.SuggestionInfo info : res.getAllSuggestions()) {
            if (info.key != null && info.key.contains("公交车站")) {
                suggest.add(info.key);
            }
        }

        if (suggest.size() == 0) {
            tvTips.setVisibility(View.VISIBLE);
            tvTips.setText("无相关公交站信息");
            return;
        }

        showStationList(suggest);
    }
}
