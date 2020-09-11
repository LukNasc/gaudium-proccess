package br.com.gaudium.entrega;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.util.Log;

import androidx.core.content.ContextCompat;

import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.NumberFormat;
import java.util.Locale;

public class Util {
	private static final String TAG = "util_error";

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

	//Formata texto para R$ dinhero

	public static String addCommaPointer(String valor) {
		Locale ptBr = new Locale("pt", "BR");
		BigDecimal parsed = parseToBigDecimal(valor, ptBr);
		return NumberFormat.getCurrencyInstance(ptBr).format(parsed).replaceAll("[R]", "").replaceAll("[$]", "");
	}

	//Auxilia o formatador para valores maiores

	private static BigDecimal parseToBigDecimal(String value, Locale locale) {
		String replaceable = String.format("[%s,.\\s]", NumberFormat.getCurrencyInstance(locale).getCurrency().getSymbol());
		String cleanString = value.replaceAll(replaceable, "");
		return new BigDecimal(cleanString).setScale(
				2, BigDecimal.ROUND_FLOOR).divide(new BigDecimal(100), BigDecimal.ROUND_FLOOR
		);
	}

}
