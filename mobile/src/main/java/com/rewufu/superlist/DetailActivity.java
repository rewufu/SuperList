package com.rewufu.superlist;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.rewufu.superlist.adapter.PagerAdapter;
import com.rewufu.superlist.fragments.DetailFragmentItem;
import com.rewufu.superlist.fragments.DetailFragmentList;

@SuppressWarnings("ALL")
public class DetailActivity extends AppCompatActivity {
    private String listName;
    private ViewPager viewPager;
    private DetailFragmentItem detailFragmentItem;
    private PagerAdapter pagerAdapter;
    private static boolean changed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this).build();
        ImageLoader.getInstance().init(config);

        Intent intent = getIntent();
        listName = intent.getStringExtra("listName");
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        if(toolbar != null){
            setSupportActionBar(toolbar);
        }
        toolbar.setTitle(listName);
        toolbar.setTitleTextColor(getResources().getColor(R.color.white));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewPager);
        pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        detailFragmentItem = DetailFragmentItem.newInstance(listName);
        pagerAdapter.addFragment(DetailFragmentList.newInstance(listName), "List");
        pagerAdapter.addFragment(DetailFragmentItem.newInstance(listName), "Item");
        viewPager.setAdapter(pagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if(position == 0 && changed){
                    Fragment fragment = pagerAdapter.getItem(position);
                    getSupportFragmentManager().beginTransaction().detach(fragment).attach(fragment).commit();
                    changed = false;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setTabTextColors(ColorStateList.valueOf(Color.WHITE));
        tabLayout.setBackgroundColor(Color.DKGRAY);
        tabLayout.setupWithViewPager(viewPager);
    }




    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == android.R.id.home) {
            this.finish();
            return true;
        }
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(1 == viewPager.getCurrentItem()){
            if(keyCode == event.KEYCODE_BACK){
                ((DetailFragmentItem)pagerAdapter.getItem(1)).onBackKeyDown();
                return false;
            }
        }

        return super.onKeyDown(keyCode, event);
    }

    public static void change(){
        changed = true;
    }
}
