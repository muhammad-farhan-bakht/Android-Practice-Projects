package com.example.rishabhcha.videorecording.filter.advance;

import android.content.Context;

import com.example.rishabhcha.videorecording.filter.base.OpenGlUtils;

public class B612ResponsibleFilter extends B612BaseFilter {


    public B612ResponsibleFilter(Context context) {
        super(context);
    }

    @Override
    protected int getInputTexture() {
        return OpenGlUtils.loadTexture(mContext, "filter/responsible_new.png");
    }

}
