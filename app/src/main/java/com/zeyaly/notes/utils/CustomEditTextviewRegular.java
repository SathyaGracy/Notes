package com.zeyaly.notes.utils;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatEditText;

public class CustomEditTextviewRegular extends AppCompatEditText {

	public CustomEditTextviewRegular(Context context, AttributeSet attrs, int defStyle) {
	      super(context, attrs, defStyle);
	      init();
	  }

	 public CustomEditTextviewRegular(Context context, AttributeSet attrs) {
	      super(context, attrs);
	      init();
	  }

	 public CustomEditTextviewRegular(Context context) {
	      super(context);
	      init();
	 }


	public void init() {
	    Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/Gilroy-Light.ttf");
	    setTypeface(tf);
	}
}