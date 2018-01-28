package com.nightonke.saver.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.MaterialViewPagerHelper;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.nightonke.saver.R;
import com.nightonke.saver.util.CoCoinUtil;

/**
 * Created by Weiping on 2016/2/2.
 */
public class HelpAboutFragment extends Fragment {

    private ObservableScrollView mScrollView;
    private Activity activity;
    private Context mContext;

    public static HelpAboutFragment newInstance() {
        HelpAboutFragment fragment = new HelpAboutFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (context instanceof Activity) {
            activity = (Activity) context;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_help_about_view, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mScrollView = view.findViewById(R.id.scrollView);
        MaterialViewPagerHelper.registerScrollView(getActivity(), mScrollView, null);

        view.findViewById(R.id.layout_2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://github.com/Nightonke/CoCoin")));
            }
        });
        view.findViewById(R.id.layout_3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://blog.csdn.net/u012925008")));
            }
        });
        view.findViewById(R.id.layout_4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CoCoinUtil.getInstance().copyToClipboard("Nightonke@outlook.com", mContext);
//                CoCoinUtil.getInstance().showToast(mContext, mContext.getResources().getString(R.string.copy_to_clipboard));
                Snackbar.make(v,R.string.copy_to_clipboard,Snackbar.LENGTH_SHORT).show();
            }
        });
    }

}
