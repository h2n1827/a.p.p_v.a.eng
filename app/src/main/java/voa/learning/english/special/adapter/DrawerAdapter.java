package voa.learning.english.special.adapter;

import java.util.ArrayList;

import voa.learning.english.special.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

public class DrawerAdapter extends ArrayAdapter<CategoryAdapter>{
	ArrayList<CategoryAdapter> values;
	Context mContext;
	
	DisplayImageOptions options = new DisplayImageOptions.Builder()
		.showImageOnFail(R.drawable.no_thumbnail)
		.showImageForEmptyUri(R.drawable.no_thumbnail)
		.showStubImage(R.drawable.no_thumbnail)
		.bitmapConfig(Bitmap.Config.RGB_565)
		.imageScaleType(ImageScaleType.IN_SAMPLE_INT)
		.cacheInMemory()
		.cacheOnDisc()
	.build();
	
	public DrawerAdapter(Context context, int resource,
			ArrayList<CategoryAdapter> objects) {
		super(context, resource, objects);
		values = objects;
		mContext = context;
		// TODO Auto-generated constructor stub
	}
	
	public class ViewHolder{
		TextView title;
		
		ImageView thumb;
	}
	
	@Override
	public View getView(int pos, View convertView, ViewGroup parent){
		ViewHolder holder = new ViewHolder();
		if(convertView == null){
			LayoutInflater inflater = LayoutInflater.from(mContext);
			convertView = inflater.inflate(R.layout.drawer_item, parent, false);
			holder.title = (TextView) convertView.findViewById(R.id.title);
			holder.thumb = (ImageView) convertView.findViewById(R.id.imageView);

			convertView.setTag(holder);
		}
		else{
			holder = (ViewHolder) convertView.getTag();
		}
		
		holder.title.setText(Html.fromHtml(values.get(pos).getTitle()));
		//ImageLoader.getInstance().displayImage(values.get(pos).getIcon(), holder.thumb, options);
		
		return convertView;
	}
	

}
