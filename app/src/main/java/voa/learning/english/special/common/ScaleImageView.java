package voa.learning.english.special.common;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.widget.ImageView;

public class ScaleImageView extends ImageView{
	private ImageChangeListener imageChangeListener;

	public ScaleImageView(Context context) {
		super(context);
		init();
	}

	public ScaleImageView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	public ScaleImageView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	private void init(){
		//this.setScaleType(ScaleType.CENTER_INSIDE);
	}

	@Override
	public void setImageBitmap(Bitmap bm) {
		super.setImageBitmap(bm);
		if (imageChangeListener != null)
			imageChangeListener.changed((bm == null));
	}

	@Override
	public void setImageDrawable(Drawable d) {
		super.setImageDrawable(d);
		if (imageChangeListener != null)
			imageChangeListener.changed((d == null));
	}

	@Override
	public void setImageResource(int id){
		super.setImageResource(id);
	}

	public interface ImageChangeListener {
		// a callback for when a change has been made to this imageView
		void changed(boolean isEmpty); 
	}

	public ImageChangeListener getImageChangeListener() {
		return imageChangeListener;
	}

	public void setImageChangeListener(ImageChangeListener imageChangeListener) {
		this.imageChangeListener = imageChangeListener;
	}
	private int widthMeasure = 0;
	private int heightMeasure = 0;
	public void setMeasureWidthHeight(int width, int height){
		
		widthMeasure = width;
		heightMeasure = height;
	}
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		
		//int width = MeasureSpec.getSize(widthMeasureSpec);
		//int height = MeasureSpec.getSize(heightMeasureSpec);
		setMeasuredDimension(widthMeasure, heightMeasure);
		/*
		int widthMode = MeasureSpec.getMode(widthMeasureSpec);
		int heightMode = MeasureSpec.getMode(heightMeasureSpec);
		int width = MeasureSpec.getSize(widthMeasureSpec);
		int height = MeasureSpec.getSize(heightMeasureSpec);

		
		if(widthMode == MeasureSpec.EXACTLY || widthMode == MeasureSpec.AT_MOST){
			scaleToWidth = true;
		}else if(heightMode == MeasureSpec.EXACTLY || heightMode == MeasureSpec.AT_MOST){
			scaleToWidth = false;
		}else throw new IllegalStateException("width or height needs to be set to match_parent or a specific dimension");

		if(getDrawable()==null || getDrawable().getIntrinsicWidth()==0 ){
			// nothing to measure
			super.onMeasure(widthMeasureSpec, heightMeasureSpec);
			return;
		}else{
			if(scaleToWidth){
				int iw = this.getDrawable().getIntrinsicWidth();
				int ih = this.getDrawable().getIntrinsicHeight();
				
				if(Setting.isDev){
					Log.e("ScaleImageView", "width:" + width + ", iw:" + iw + ",ih" + ih);
				}
				int heightC = width*ih/iw;
				
				if(height > 0)
				if(heightC>height){
					// dont let hegiht be greater then set max
					heightC = height;
					width = heightC*iw/ih;
				}
				 
				this.setScaleType(ScaleType.CENTER_CROP);
				setMeasuredDimension(width, heightC);

			}else{
				// need to scale to height instead
				int marg = 0;
				if(getParent()!=null){
					if(getParent().getParent()!=null){
						marg+= ((RelativeLayout) getParent().getParent()).getPaddingTop();
						marg+= ((RelativeLayout) getParent().getParent()).getPaddingBottom();
					}
				}

				int iw = this.getDrawable().getIntrinsicWidth();
				int ih = this.getDrawable().getIntrinsicHeight();

				width = height*iw/ih;
				height-=marg;
				setMeasuredDimension(width, height);
			}

		}
		*/
		//setMeasuredDimension(width, height);
	}
}
