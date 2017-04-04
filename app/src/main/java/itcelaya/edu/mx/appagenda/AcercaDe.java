package itcelaya.edu.mx.appagenda;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.text.util.Linkify;
import android.widget.ImageView;
import android.widget.TextView;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by emmanuel-nova on 01/04/17.
 */

public class AcercaDe extends AppCompatActivity {

    @InjectView(R.id.txtCorreoDev)
    TextView txtCorreoDev;
    @InjectView(R.id.txtTelDev)
    TextView txtTelDev;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_acerca_de);
        ButterKnife.inject(this);
        Linkify.addLinks(txtCorreoDev,Linkify.EMAIL_ADDRESSES);
        Linkify.addLinks(txtTelDev,Linkify.PHONE_NUMBERS);
        Drawable originalDrawable = getResources().getDrawable(R.mipmap.ic_dev);
        Bitmap originalBitmap = ((BitmapDrawable) originalDrawable).getBitmap();
        RoundedBitmapDrawable roundedDrawable = RoundedBitmapDrawableFactory.create(getResources(), originalBitmap);
        roundedDrawable.setCornerRadius(originalBitmap.getHeight());
        ImageView imageView = (ImageView) findViewById(R.id.imgFoto);
        imageView.setImageDrawable(roundedDrawable);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}