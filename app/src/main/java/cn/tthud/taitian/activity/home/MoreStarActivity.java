package cn.tthud.taitian.activity.home;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.example.xrecyclerview.XRecyclerView;
import com.google.gson.reflect.TypeToken;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ViewInject;

import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import cn.tthud.taitian.R;
import cn.tthud.taitian.adapter.StarXueyuanAdapter;
import cn.tthud.taitian.base.ActivityBase;
import cn.tthud.taitian.base.OnItemClickListener;
import cn.tthud.taitian.base.WebViewActivity;
import cn.tthud.taitian.bean.ActivityBean;
import cn.tthud.taitian.bean.StarXueyuanBean;
import cn.tthud.taitian.net.FlowAPI;
import cn.tthud.taitian.utils.GsonUtils;
import cn.tthud.taitian.utils.SPUtils;
import cn.tthud.taitian.xutils.CommonCallbackImp;
import cn.tthud.taitian.xutils.MXUtils;

public class MoreStarActivity extends ActivityBase {

    @ViewInject(R.id.xrv_custom)
    private XRecyclerView xrvCustom;

    @ViewInject(R.id.page_refresh)
    private LinearLayout page_refresh;
    private StarXueyuanAdapter adapter;

    private int mPage = 1;
    private int mMaxPage = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appendMainBody(this,R.layout.activity_more_star);
        appendTopBody(R.layout.activity_top_text);
        setTopBarTitle("更多学员");
        setTopLeftDefultListener();
        initRecyclerView();
        mPage = 1;
        loadData();
    }

    private void loadData() {
        RequestParams requestParams = FlowAPI.getRequestParams(FlowAPI.APP_STAR_MORE);
        requestParams.addParameter("p", mPage);
        MXUtils.httpGet(requestParams, new CommonCallbackImp("更多学员",requestParams){
            @Override
            public void onSuccess(String data) {
                super.onSuccess(data);
                try {
                    JSONObject jsonObject = new JSONObject(data);
                    String status = jsonObject.getString("status");
                    String info = jsonObject.getString("info");
                    if(FlowAPI.HttpResultCode.SUCCEED.equals(status)){
                        xrvCustom.refreshComplete();
                        String result = jsonObject.getString("data");
                        JSONObject jsonObject1 = new JSONObject(result);
                        String maxPage = jsonObject1.getString("maxPage");
                        mMaxPage = Integer.parseInt(maxPage);
                        String list = jsonObject1.getString("list");
                        Type type=new TypeToken<List<StarXueyuanBean>>(){}.getType();
                        List<StarXueyuanBean> beanList = GsonUtils.jsonToList(list,type);
                        adapter.addAll(beanList);
                        adapter.notifyDataSetChanged();

                        //xrvCustom.loadMoreComplete();
                        if(adapter.getData().size() == 0){
                            page_refresh.setVisibility(View.VISIBLE);
                            xrvCustom.setVisibility(View.GONE);
                        }else {
                            page_refresh.setVisibility(View.GONE);
                            xrvCustom.setVisibility(View.VISIBLE);
                        }
                        if(mPage >= mMaxPage){
                            xrvCustom.noMoreLoading();
                        }
                    }else {
                        showMsg(info);
                    }
                }catch (JSONException e){
                    e.printStackTrace();
                }
            }
        });

    }



    private void initRecyclerView() {
        // 禁止下拉刷新
        xrvCustom.setPullRefreshEnabled(true);
        xrvCustom.setLoadingMoreEnabled(true);
        // 去掉刷新头
        //xrvCustom.clearHeader();
        xrvCustom.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                adapter.clear();
                adapter.notifyDataSetChanged();
                mPage = 1;
                loadData();
            }

            @Override
            public void onLoadMore() {
                mPage += 1;
                loadData();
            }
        });
        xrvCustom.setLayoutManager(new GridLayoutManager(this,2));
        // 需加，不然滑动不流畅
        xrvCustom.setNestedScrollingEnabled(false);
        xrvCustom.setHasFixedSize(false);
        xrvCustom.setItemAnimator(new DefaultItemAnimator());
        adapter = new StarXueyuanAdapter();
        adapter.setOnItemClickListener(new OnItemClickListener<StarXueyuanBean>() {
            @Override
            public void onClick(final StarXueyuanBean starXueyuanBean, int position) {
                if (TextUtils.isEmpty(starXueyuanBean.getUrl())) {
                    return;
                }
                // 点击
                Intent intent = new Intent(MoreStarActivity.this,WebViewActivity.class);
                intent.putExtra("title",starXueyuanBean.getTitle());
                intent.putExtra("url", starXueyuanBean.getUrl());
                MoreStarActivity.this.startActivity(intent);
            }
        });
        xrvCustom.setAdapter(adapter);
    }

}
