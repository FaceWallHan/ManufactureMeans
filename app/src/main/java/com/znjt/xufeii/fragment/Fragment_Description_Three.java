package com.znjt.xufeii.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.znjt.xufeii.R;

/**
 * Created by Administrator on 2019/4/26.
 */

public class Fragment_Description_Three extends Fragment {
    private FragmentChange fragmentChange;

    public void setFragmentChange(FragmentChange fragmentChange) {
        this.fragmentChange = fragmentChange;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_description_three_layout,container,false);
    }
    public  interface  FragmentChange{
        void change();
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView join=getView().findViewById(R.id.join);
        join.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentChange.change();
            }
        });
    }
}
