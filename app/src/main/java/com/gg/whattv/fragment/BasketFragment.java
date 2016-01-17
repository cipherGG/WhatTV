package com.gg.whattv.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gg.whattv.R;
import com.gg.whattv.utils.TabsUtils;

import java.util.List;

public class BasketFragment extends Fragment {

    private List<String> tabs;

    public BasketFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_basket, container, false);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.vp_fragment_basket);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tl_fragment_basket);

        tabs = TabsUtils.getNBATabs();

        MyPagerAdapter pagerAdapter = new MyPagerAdapter(getActivity().getSupportFragmentManager());
        viewPager.setAdapter(pagerAdapter);
        tabLayout.setupWithViewPager(viewPager);

        return view;
    }

    private class MyPagerAdapter extends FragmentStatePagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        //call when current tab or current viewPager changed
        @Override
        public Fragment getItem(int position) {

            return BasketNBAFragment.newInstance(position, tabs.get(position));
        }

        //the viewPagers length is sync with tabs length
        @Override
        public int getCount() {

            return tabs.size();
        }

        //call when set every tab name, the viewPagers title is sync with tabs text
        @Override
        public CharSequence getPageTitle(int position) {

            return tabs.get(position);
        }
    }

}
