package com.example.rishabhcha.videorecording.filter.advance;


import android.content.Context;

import com.example.rishabhcha.videorecording.filter.base.OpenGlUtils;

public class B612HeartFilter extends B612BaseFilter {


    public B612HeartFilter(Context context) {
        super(context);
    }

    @Override
    protected int getInputTexture() {
        return OpenGlUtils.loadTexture(mContext, "filter/heart_new.png");
    }


}
