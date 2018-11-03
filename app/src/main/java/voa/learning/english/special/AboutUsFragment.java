package voa.learning.english.special;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class AboutUsFragment extends Fragment {

	private boolean MyStartActivity(Intent aIntent) {
	    try
	    {
	        startActivity(aIntent);
	        return true;
	    }
	    catch (ActivityNotFoundException e)
	    {
	        return false;
	    }
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		
		View view = inflater.inflate(R.layout.fragment_aboutus, container, false);
		
		Button shareButton = (Button) view.findViewById(R.id.button1);
		
		ImageView rateapps = (ImageView) view.findViewById(R.id.rateapps);
        
        shareButton.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {
        	shareIt();
        	}

			private Intent shareIt() {
				
				Bitmap icon = BitmapFactory.decodeResource(getActivity().getResources(),
                        R.drawable.ic_launcher);
				ByteArrayOutputStream bytes = new ByteArrayOutputStream();
				icon.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
				File file = new File(Environment.getExternalStorageDirectory() + File.separator + "temporary_file.jpg");
				try {
				    file.createNewFile();
				    FileOutputStream fo = new FileOutputStream(file);
				    fo.write(bytes.toByteArray());
				} catch (IOException e) {                       
				        e.printStackTrace();
				}
								
				
				Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
				shareIntent.setType("text/plain");
				shareIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_WHEN_TASK_RESET);
				Uri uri = Uri.parse("file:///sdcard/temporary_file.jpg");

				shareIntent.putExtra(Intent.EXTRA_TEXT, "Easy English Listening https://play.google.com/store/apps/details?id=easy.english.listening");
				
				shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
				
				startActivity(Intent.createChooser(shareIntent, "Share via"));
				return shareIntent;
			}
        });
        
        
        rateapps.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {
        		
        		Intent intent = new Intent(Intent.ACTION_VIEW);
                //Try Google play
                intent.setData(Uri.parse("market://details?id=start.listening.english"));
                if (!MyStartActivity(intent)) {
                    //Market (Google play) app seems not installed, let's try to open a webbrowser
                    intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=start.listening.english"));
                    if (!MyStartActivity(intent)) {
                        //Well if this also fails, we have run out of options, inform the user.
                        Toast.makeText(getActivity(), "Could not open Android market, please install the market app.", Toast.LENGTH_SHORT).show();
                    }
                }
        		
        	}
        });
        
        
		return view;
	}
	

}
