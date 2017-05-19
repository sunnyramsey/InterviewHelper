package ars.ramsey.interviewhelper.fragment;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;

import ars.ramsey.interviewhelper.R;
import ars.ramsey.interviewhelper.model.bean.Article;
import ars.ramsey.interviewhelper.presenter.ZhihuArticalPresenter;
import ars.ramsey.interviewhelper.view.ZhihuArticalView;

/**
 * Created by Ramsey on 2017/5/11.
 */

public class ZhihuArticalFragment extends Fragment implements ZhihuArticalView<ZhihuArticalPresenter> {

    private ZhihuArticalPresenter mPresenter;
    private CollapsingToolbarLayout mCollapsingToolbar;
    private NestedScrollView mNestedView;
    private Article mArticle;
    private AppBarLayout mAppBar;
    private Toolbar mToolBar;
    private WebView mWebview;
    private SimpleDraweeView mArticleImg;
    private TextView mLikeText;
    private TextView mCommentText;
    private LinearLayout mContainer;
    private TextView mTitle;


    @Override
    public void setPresenter(ZhihuArticalPresenter presenter) {
        this.mPresenter = presenter;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.zhihu_artical_layout,null);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);//此FLAG可使状态栏透明，且当前视图在绘制时，从屏幕顶端开始即top = 0开始绘制，这也是实现沉浸效果的基础
           // getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);//可不加
        }

        mCollapsingToolbar = (CollapsingToolbarLayout)view.findViewById(R.id.collapsing_toolbar);
        mNestedView = (NestedScrollView)view.findViewById(R.id.nested_scroll_view);
        mAppBar = (AppBarLayout)view.findViewById(R.id.appbar);
        mToolBar = (Toolbar)view.findViewById(R.id.toolbar);
        mArticleImg = (SimpleDraweeView)view.findViewById(R.id.article_img_view);
        mLikeText = (TextView)view.findViewById(R.id.tv_likecount);
        mCommentText = (TextView)view.findViewById(R.id.tv_commentcount);
        mContainer = (LinearLayout)view.findViewById(R.id.content_container);
        mTitle = (TextView)view.findViewById(R.id.tv_title);

        ((AppCompatActivity)getActivity()).setSupportActionBar(mToolBar);
        ((AppCompatActivity)getActivity()).getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        Bundle bundle = getArguments();
        mArticle = bundle.getParcelable("ARTICAL");
        if(mArticle != null)
        {
            if (!TextUtils.isEmpty(mArticle.getTitleImage())) {
                mArticleImg.setImageURI(Uri.parse(mArticle.getTitleImage()));
            }

            mCollapsingToolbar.setTitle(mArticle.getTitle());
            mCollapsingToolbar.setExpandedTitleColor(Color.TRANSPARENT);
            mCollapsingToolbar.setCollapsedTitleTextColor(Color.WHITE);

            mWebview = new WebView(getActivity().getApplicationContext());
            mContainer.addView(mWebview);
            mWebview.getSettings().setJavaScriptEnabled(true);//启用js
            mWebview.getSettings().setBlockNetworkImage(false);//解决图片不显示
            mWebview.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
            String style = "<style>img{ max-width:80%;height:auto;}</style>";
            String html = "<html><body>"+style+mArticle.getContent()+"</body><html>";
            mWebview.loadDataWithBaseURL("https://pic4.zhimg.com/",html,"text/html","utf-8",null);

            mLikeText.setText(String.valueOf(mArticle.getLikesCount()));
            mCommentText.setText(String.valueOf(mArticle.getCommentsCount()));
            mTitle.setText(mArticle.getTitle());
        }

        mNestedView.setOnScrollChangeListener(new NestedScrollView.OnScrollChangeListener() {
            @Override
            public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
            }
        });


        mAppBar.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
            @Override
            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onDestroy() {
        if (mWebview != null) {
            ViewGroup parent = (ViewGroup) mWebview.getParent();
            if (parent != null) {
                parent.removeView(mWebview);
            }
            mWebview.removeAllViews();
            mWebview.destroy();
        }
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            getActivity().finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
