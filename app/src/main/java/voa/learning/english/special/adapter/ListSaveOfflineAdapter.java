package voa.learning.english.special.adapter;

import java.io.File;
import java.util.ArrayList;

import voa.learning.english.special.R;
import voa.learning.english.special.ListSaveOfflineFragment;
import voa.learning.english.special.database.Database;
import voa.learning.english.special.model.VideoSchema;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Build;
import android.os.Environment;
import android.text.Html;
import android.text.Spanned;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;


public class ListSaveOfflineAdapter extends ArrayAdapter<VideoSchema>{
	ArrayList<VideoSchema> listVideo;
	private Database db;
	String stringCat = "Lession Mp3 Offline";
	
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

	
	public ListSaveOfflineAdapter(Context context, int resource,
			ArrayList<VideoSchema> objects, ListView listView) {
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
		TextView delete;
		ImageView thumb;
	}
	
	@Override
	public View getView(int pos, View convertView, ViewGroup parent){
		ViewHolder holder = new ViewHolder();
		if(convertView == null){
			LayoutInflater inflater = LayoutInflater.from(mContext);
			convertView = inflater.inflate(R.layout.mp3_item, parent, false);
			holder.title = (TextView) convertView.findViewById(R.id.new_grid_view_title);
			holder.thumb = (ImageView) convertView.findViewById(R.id.new_grid_view_imageThumb);
			holder.desc = (TextView) convertView.findViewById(R.id.new_grid_view_time);
			holder.delete = (TextView) convertView.findViewById(R.id.new_grid_delete);
			
			int widthImage = widthScreen/4;
			int heightImage = widthScreen/4;
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
	    
	    
		int widthImage = widthScreen/4;
		int heightImage = widthScreen/4; 
		
		 String ExternalStorageDirectoryPath = Environment.getExternalStorageDirectory().getAbsolutePath();
		 String targetPath = ExternalStorageDirectoryPath +"/EnglishListening/";
		 //Bitmap bMap = BitmapFactory.decodeFile(targetPath+"/1402681183490.jpg");
		
		 String urlImage = "file://"+targetPath + videoItem.getThumbnaix();
		 final String delUrlImage = targetPath + videoItem.getThumbnaix();
		 final String delUrlFileMp3 = targetPath + videoItem.getLinkmp3();
	
		Log.e("logurlimages", urlImage);
		imageLoader.displayImage(urlImage, holder.thumb, options);
		
		final Spanned ptitle = Html.fromHtml(videoItem.getTitle());
		final String idsqlite = videoItem.getIdSqlite();
		final int poslistV = pos;
		
		holder.title.setText(ptitle);
			 
		holder.desc.setText(stringCat);
		
		holder.delete.setOnClickListener(new View.OnClickListener() {
		    public void onClick(View v) {
		    	db = new Database(getContext());
				db.open();
				db.deleteRows(idsqlite);
				db.close();
				
				ListSaveOfflineFragment.listVideo.remove(poslistV);
				notifyDataSetChanged();
				
				Log.e("chua images", delUrlImage+"");
				Log.e("chua link mp3", delUrlFileMp3+"");
				
				File file = new File(delUrlImage);
				file.delete();
				File file2 = new File(delUrlFileMp3);
				file2.delete();
		    	
		    	Toast.makeText(getContext(),
		    			"DELETE note: "+ptitle+"", Toast.LENGTH_LONG)
						.show();
		    }
		});
		
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
