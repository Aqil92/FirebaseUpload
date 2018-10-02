package net.simplifiedcoding.firebaseupload;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ShowImagesActivity extends AppCompatActivity {
    int count;
    HorizontalScrollView horizontalScrollView;
    LinearLayout linearLayout;
    RelativeLayout rl;
    DatabaseReference alpha;
    List<String> imagesUrl = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_images);


        // Your DB with images
        alpha = FirebaseDatabase.getInstance().getReference().child(Constants.DATABASE_PATH_UPLOADS);
        // to put horizontalscroll
        rl = (RelativeLayout)findViewById(R.id.activity_show_images);


        alpha.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                count = (int) dataSnapshot.getChildrenCount();
                for (DataSnapshot ds : dataSnapshot.getChildren()) {

                    imagesUrl.add(ds.getValue().toString());
                    Log.i("onCancelled da", imagesUrl.toString());

                    // Create new horizontal scroll view with imageViews
// There could be any number of images that's why it is better to make programmatically
                    horizontalScrollView = new HorizontalScrollView(ShowImagesActivity.this);
                    linearLayout = new LinearLayout(ShowImagesActivity.this);
                    ViewGroup.LayoutParams prams = new ViewGroup.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    linearLayout.setLayoutParams(prams);
                    linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                    horizontalScrollView.addView(linearLayout);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.i("onCancelled ERRE", databaseError.toString());
            }
        });
        if(imagesUrl.size()!=0)
        show();
    }

    // I showed all images on a button click you can do it on start or on create
    public void show() {

        int c = 0;

        ImageView[] imageViews = new ImageView[count +1 ];
        while (c < count) {
            imageViews[c] = new ImageView(ShowImagesActivity.this);
            imageViews[c].setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            imageViews[c].setPadding(10,0,10,0);
            Log.i("onCancelled image", imagesUrl.get(c).toString());


            Glide.with(this).load(imagesUrl.get(c).toString()).into(imageViews[c]);
            linearLayout.addView(imageViews[c]);
            c++;
        }
        rl.addView(linearLayout);
    }}