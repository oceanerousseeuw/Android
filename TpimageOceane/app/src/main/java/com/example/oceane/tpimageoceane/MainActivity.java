package com.example.oceane.tpimageoceane;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    int LOAD = 42;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void load(View view){
        System.out.println("Chargement de l'image");

        String[] mimeTypes = {"image/jpeg", "image/png"};
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT)
                .setType("image/*")
                .putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes);
        startActivityForResult(intent, LOAD) ;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode==LOAD && resultCode==RESULT_OK){
            setImage(data);
        }
    }

    protected void setImage(Intent data){
        Uri imageUri = data.getData();

        TextView text = findViewById(R.id.MyUrlImage);
        text.setText(imageUri.toString());

        try {
            BitmapFactory.Options option = new BitmapFactory.Options();
            option.inMutable = true;
            Bitmap bm = BitmapFactory.decodeStream(getContentResolver().openInputStream(imageUri), null, option);
            ImageView myImage = findViewById(R.id.myImage);
            myImage.setImageBitmap(bm);
        }catch(Exception e){
            Toast.makeText(this, "Can't load image", Toast.LENGTH_SHORT).show();
        }
    }

    public void splitHorizontal(View view){
        ImageView myImage = findViewById(R.id.myImage);
        Bitmap bitmap = ((BitmapDrawable)myImage.getDrawable()).getBitmap();

        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();

        for (int x=0; x<bitmapWidth/2; x++){
            for(int y=0; y<bitmapHeight; y++){
                int myPixel = bitmap.getPixel(x,y);
                int myDestPixel = bitmap.getPixel(bitmapWidth-x-1, y);
                bitmap.setPixel(x,y, myDestPixel);
                bitmap.setPixel(bitmapWidth-x-1, y, myPixel);
            }
        }
    }


    public void splitVertical(View view){
        ImageView myImage = findViewById(R.id.myImage);
        Bitmap bitmap = ((BitmapDrawable)myImage.getDrawable()).getBitmap();

        int bitmapWidth = bitmap.getWidth();
        int bitmapHeight = bitmap.getHeight();

        for (int x=0; x<bitmapWidth; x++){
            for(int y=0; y<bitmapHeight/2; y++){
                int myPixel = bitmap.getPixel(x,y);
                int myDestPixel = bitmap.getPixel(x, bitmapHeight-y-1);
                bitmap.setPixel(x,y, myDestPixel);
                bitmap.setPixel(x, bitmapHeight-y-1, myPixel);
            }
        }
    }
}
