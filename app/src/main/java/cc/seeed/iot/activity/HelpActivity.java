package cc.seeed.iot.activity;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;

import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;
import cc.seeed.iot.R;
import cc.seeed.iot.adapter.HelpAdapter;
import cc.seeed.iot.entity.FAQBean;

/**
 * author: Jerry on 2016/6/1 16:01.
 * description:
 */
public class HelpActivity extends BaseActivity {

    @InjectView(R.id.toolbar)
    Toolbar mToolbar;
    @InjectView(R.id.mLvSupport)
    RecyclerView mLvSupport;

    List<FAQBean> beanList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_help);
        ButterKnife.inject(this);

        initDate();
        initView();
    }

    private void initDate() {
        beanList.add(new FAQBean(R.mipmap.help01_breathing, getString(R.string.help_bean1_title), getString(R.string.help_bean1_msg)));
        beanList.add(new FAQBean(R.mipmap.help02_blink_2, getString(R.string.help_bean2_title), getString(R.string.help_bean2_msg)));
        beanList.add(new FAQBean(R.mipmap.help03_blink_1_quick, getString(R.string.help_bean3_title), getString(R.string.help_bean3_msg)));
        beanList.add(new FAQBean(R.mipmap.help04_no_blink, getString(R.string.help_bean4_title), getString(R.string.help_bean4_msg)));
        beanList.add(new FAQBean(R.mipmap.help05_blink_01s, getString(R.string.help_bean5_title), getString(R.string.help_bean5_msg)));
        beanList.add(new FAQBean(R.mipmap.help06_blink_1s, getString(R.string.help_bean6_title), getString(R.string.help_bean6_msg)));

        HelpAdapter adapter = new HelpAdapter( beanList);
        View headerView = LayoutInflater.from(this).inflate(R.layout.help_header_layout, null);
        View footerView = LayoutInflater.from(this).inflate(R.layout.help_footer_layout, null);

        if (mLvSupport != null) {
            mLvSupport.setHasFixedSize(true);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
            mLvSupport.setLayoutManager(layoutManager);
            mLvSupport.setAdapter(adapter);
        }
    }

    private void initView() {
        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle(R.string.menu_support);
    }

 /*   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.grove_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            finish();
            return true;
        } else if (id == R.id.share) {
            //   DialogUtils.showShare(HelpActivity.this,"activity Share","Share",grove.GroveName);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
*/

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

}
