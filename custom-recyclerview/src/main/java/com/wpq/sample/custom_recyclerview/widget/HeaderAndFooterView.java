package com.wpq.sample.custom_recyclerview.widget;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.view.Gravity;
import android.view.ViewGroup;

/**
 * @author wpq
 * @version 1.0
 */
public class HeaderAndFooterView extends AppCompatTextView {

    public HeaderAndFooterView(Context context, int backgroundColor, @NonNull String text) {
        super(context);

        ViewGroup.LayoutParams lp = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        setLayoutParams(lp);
        setGravity(Gravity.CENTER);
        setPadding(dp2px(16), dp2px(16), dp2px(16), dp2px(16));
        setBackgroundColor(backgroundColor == 0 ? Color.RED : backgroundColor);
        setText(text);
    }

    private int dp2px(float dp) {
        float density = getResources().getDisplayMetrics().density;
        return (int) (dp * density + 0.5f);
    }


}
