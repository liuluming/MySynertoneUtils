package com.my51c.see51.app.utils;

import android.content.Context;
import android.os.Handler;
import android.view.View;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.synertone.netAssistant.R;

/**
 * Created by snt1231 on 2018/4/20.
 */

public class ViewUtil {
    public static  void setSpinner(final Context mContext, final boolean enable, final Spinner...spinners) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(spinners!=null&&spinners.length>0){
                    for(int i=0;i<spinners.length;i++){
                        Spinner spinner = spinners[i];
                        spinner.setEnabled(enable);
                        View selectedView = spinner.getSelectedView();
                        if(selectedView!=null){
                            TextView view= (TextView) selectedView.findViewById(android.R.id.text1);
                            if(view!=null){
                                view.setTextColor(enable==false?mContext.getResources().getColor(R.color.gray):mContext.getResources().getColor(R.color.black
                                ));
                            }
                        }

                    }
                }
            }
        }, 400);
    }
    public static void setToggleButton(boolean enable, ToggleButton mToggleButton) {
        mToggleButton.setEnabled(enable);
    }
}
