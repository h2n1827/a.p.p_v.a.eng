package voa.learning.english.special.adapter;

import java.util.ArrayList;

import voa.learning.english.special.R;
import voa.learning.english.special.common.DeveloperKey;
import voa.learning.english.special.model.VideoSchema;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Build;
import android.text.Html;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class ListVideoAdapter extends ArrayAdapter<VideoSchema>{
	ArrayList<VideoSchema> listVideo;
	String stringCat = "Different";
	int widthScreen;
	int heightScreen;
	ImageLoader imageLoader;
	DisplayImageOptions options = new DisplayImageOptions.Builder()
		.showImageOnFail(R.drawable.no_thumbnail)
		.showImageForEmptyUri(R.drawable.no_thumbnail)
		.showStubImage(R.drawable.no_thumbnail)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
		.cacheInMemory()
	.build();
	
	private int mLastPosition = 1;
	Context mContext;
	public ListVideoAdapter(Context context, int resource,
			ArrayList<VideoSchema> objects) {
		super(context, resource, objects);
		listVideo = objects;
		mContext =context;
		initHeightAndWidthScreen();
		
		imageLoader = ImageLoader.getInstance();
		// TODO Auto-generated constructor stub
	}
	public class ViewHolder{
		TextView title;
		TextView desc;
		ImageView thumb;
	}
	
	@Override
	public View getView(int pos, View convertView, ViewGroup parent){
		ViewHolder holder = new ViewHolder();
		if(convertView == null){
			LayoutInflater inflater = LayoutInflater.from(mContext);
			convertView = inflater.inflate(R.layout.video_item, parent, false);
			holder.title = (TextView) convertView.findViewById(R.id.new_grid_view_title);
			holder.thumb = (ImageView) convertView.findViewById(R.id.new_grid_view_imageThumb);
			holder.desc = (TextView) convertView.findViewById(R.id.new_grid_view_time);
			
			int widthImage = widthScreen;
			int heightImage = 9 * widthImage/ 16; 
			LinearLayout.LayoutParams layoutImageThangibrahim = new LinearLayout.LayoutParams(widthImage, heightImage);
			holder.thumb.setLayoutParams(layoutImageThangibrahim);
			
			
			convertView.setTag(holder);
		}
		else{
			holder = (ViewHolder) convertView.getTag();
		}
		TranslateAnimation animation = null;
			    
	    if (pos > mLastPosition) {
            animation = new TranslateAnimation(
                Animation.RELATIVE_TO_SELF, 0.0f, 
                Animation.RELATIVE_TO_SELF, 0.0f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.0f);

            animation.setDuration(600);
            convertView.startAnimation(animation);
            mLastPosition = pos;
	    }
		VideoSchema videoItem = listVideo.get(pos);
	    
	    
	    int widthImage = widthScreen;
		int heightImage = 9 * widthImage/ 16; 
		
		Log.e("thanigbrahim", "widthImage: " + widthImage + ", heightImage: " + heightImage);
		//LinearLayout.LayoutParams layoutImage = new LinearLayout.LayoutParams(widthImage, heightImage);
		//holder.thumb.setLayoutParams(layoutImage);
		//String urlBongda = "http://www1.bongda.com.vn/Data/Image/2013/Thang11/24/zkim.jpg";
		String urlImage = "";
		if(Integer.parseInt(videoItem.getMp3())==1){
			urlImage = DeveloperKey.ERRORDM + videoItem.getThumbnaix();
		}else {
			urlImage = "http://img.youtube.com/vi/"+videoItem.getIdYoutube()+"/0.jpg";
		}
		Log.e("urlimg", urlImage);	
		imageLoader.displayImage(urlImage, holder.thumb, options);
		
		holder.title.setText(Html.fromHtml(videoItem.getTitle()));
		
		switch (Integer.parseInt(videoItem.getIdcat())) {
		case 1:
			stringCat = "English Cartoons";
			break;
		case 3:
			stringCat = "Film Trailer";
			break;
		case 4:
			stringCat = "Listen Song Lyrics";
			break;
		case 5:
			stringCat = "English Basic";
			break;
		case 6:
			stringCat = "English Advanced";
			break;
		case 7:
			stringCat = "Pronunciation Tips";
			break;
		case 9:
			stringCat = "VOA learning english";
			break;
			
		case 10:
			stringCat = "Mp3 - Listen News";
			break;
			
		case 11:
			stringCat = "Easy Listening";
			break;
			
		default:
			break;
		}		 
		holder.desc.setText(stringCat);
		
		return convertView;
	}
	
	public void initHeightAndWidthScreen(){
		Point size = new Point();
		WindowManager w = ((Activity) mContext).getWindowManager();
		
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2){
			w.getDefaultDisplay().getSize(size);
			widthScreen = size.x;
			heightScreen = size.y;
		}
		else {
			Display d = w.getDefaultDisplay(); 
			widthScreen = d.getWidth(); 
			heightScreen = d.getHeight(); 
		}
		
		widthScreen -= convertDpToPx(mContext, 20);
	}
	
	public static int convertDpToPx(Context mContext, int dp){
		DisplayMetrics displayMetrics = mContext.getResources().getDisplayMetrics();
	    int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));  
	    px = (int) (dp * displayMetrics.density + 0.5f);
	    return px;
		
	}
	

}
