package com.example.cuahangonline.Fragment;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new ChoXacNhan_fragment();
            case 1:
                return new DaXacNhan_fragment();
            case 2:
                return new DangGiaoHang_fragment();
            case 3:
                return new DaGiaoHang_fragment();
            case 4:
                return new HuyDon_fragment();
            default:
                return new ChoXacNhan_fragment();
        }

    }

    @Override
    public int getCount() {
        return 5;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0:
                title = "Chờ xác nhận";
                break;
            case 1:
                title = "Đã xác nhận";
                break;
            case 2:
                title = "Đang giao hàng";
                break;
            case 3:
                title = "Đã giao hàng";
                break;
            case 4:
                title = "Đơn hủy";
                break;
        }
        return title;
    }
}
