package com.example.onthego;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class NewsPagerAdapter extends FragmentPagerAdapter {

    public NewsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return NewsCategoryFragment.newInstance(position); // Pass category index to the fragment
    }

    @Override
    public int getCount() {
        return 5; // Adjust the count for the number of categories you have
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        // Set tab titles for each category
        switch (position) {
            case 0:
                return "Today's News";
            case 1:
                return "India News";
            case 2:
                return "World News";
            case 3:
                return "Science News";
            case 4:
                return "Education News";
            default:
                return "Category " + (position + 1);
        }
    }
}
