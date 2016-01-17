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

public class FootFragment extends Fragment {
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    private List<String> tabs;

    public FootFragment() {
    }

    public static FootFragment newInstance(String param1, String param2) {
        FootFragment fragment = new FootFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_foot, container, false);
        ViewPager viewPager = (ViewPager) view.findViewById(R.id.vp_fragment_foot);
        TabLayout tabLayout = (TabLayout) view.findViewById(R.id.tl_fragment_foot);

        tabs = TabsUtils.getFootTabs();

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

            return FootLeagueFragment.newInstance(position, tabs.get(position));
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
