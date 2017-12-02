package activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

import ca.uottawa.cohab.R;

/**
 * Created by Tony on 12/2/2017.
 */

public class ChangePicture extends AppCompatActivity {

        private static final String TAG = "Main22";

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_change_picture);

        }

        public void changePic (View view){

            Intent returnIntent = new Intent();

            ImageView selectedImage = (ImageView) view;

            returnIntent.putExtra("imageID",selectedImage.getId());

            setResult(RESULT_OK,returnIntent);
            finish();
        }

    }
