package com.example.rishabhcha.videorecording.filter.advance;


import android.content.Context;

import com.example.rishabhcha.videorecording.filter.base.OpenGlUtils;

public class B612PerfumeFilter extends B612BaseFilter {


    public B612PerfumeFilter(Context context) {
        super(context);
    }

    @Override
    protected int getInputTexture() {
        return OpenGlUtils.loadTexture(mContext, "filter/perfume_new.png");
    }
}
