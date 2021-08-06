package com.example.e_vaccination.Utils;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.view.View;

import com.google.android.material.snackbar.Snackbar;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class AppUtils {
    public static ProgressDialog progressdialog;
    public static Boolean isProgressBarShowing = false;


    public static void startProgressBar(Activity activity, String msg) {
        progressdialog = new ProgressDialog(activity);
        progressdialog.setMessage(msg);
        progressdialog.setCancelable(false);
        progressdialog.show();
        isProgressBarShowing = true;
    }

    public static void dismissProgressBar() {
        progressdialog.dismiss();
        isProgressBarShowing = false;
    }

    public static void success(Activity activity, String msg) {
        Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content), msg,
                Snackbar.LENGTH_LONG);
//                .setAction("Action", null)
//                .setActionTextColor(activity.getResources().getColor(R.color.white));
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(Color.GREEN);
        snackbar.show();
    }

    public static void error(Activity activity, String msg) {
        Snackbar snackbar = Snackbar.make(activity.findViewById(android.R.id.content),
                msg, Snackbar.LENGTH_LONG);
//                .setAction("Action", null)
//                .setActionTextColor(activity.getResources().getColor(R.color.white));
        View sbView = snackbar.getView();
        sbView.setBackgroundColor(Color.RED);
        snackbar.show();
    }

    public static void SavePreference(Context context, String key, String value) {
        SharedPreferences preferences = context.getSharedPreferences("User", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
        editor.commit();
    }

    public String getPreference(Context context, String key) {
        SharedPreferences preferences = context.getSharedPreferences("User", Context.MODE_PRIVATE);
        return preferences.getString(key, "");

    }

    public static boolean isValidEmail(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static String getRandomKey() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890_";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 18) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }
}
