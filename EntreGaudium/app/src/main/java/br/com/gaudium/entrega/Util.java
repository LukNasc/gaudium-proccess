package br.com.gaudium.entrega;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

public class Util {
	public static BitmapDescriptor bitmapDescriptorFromVector(Context context, int vectorResId) {
		Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorResId);
		vectorDrawable.setBounds(0, 0, vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight());
		Bitmap bitmap = Bitmap.createBitmap(vectorDrawable.getIntrinsicWidth(), vectorDrawable.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(bitmap);
		vectorDrawable.draw(canvas);
		return BitmapDescriptorFactory.fromBitmap(bitmap);
	}

	public static void tocarSomVibrar(Context ctx) {
		Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
		Ringtone r = RingtoneManager.getRingtone(ctx.getApplicationContext(), notification);
		r.play();

		Vibrator v = (Vibrator) ctx.getSystemService(Context.VIBRATOR_SERVICE);
		// Vibrate for 500 milliseconds
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
			v.vibrate(VibrationEffect.createOneShot(400, VibrationEffect.EFFECT_DOUBLE_CLICK));
		} else {
			//deprecated in API 26
			v.vibrate(300);
		}
	}

	public static void playPop(Context ctx){
		MediaPlayer mPlayer = MediaPlayer.create(ctx, R.raw.pop);
		mPlayer.start();
	}

	public static void playCompleted(Context ctx){
		MediaPlayer mPlayer = MediaPlayer.create(ctx, R.raw.completed);
		mPlayer.start();
	}
}
