package com.gg.whattv.utils;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.DatePicker;

import com.gg.whattv.R;
import com.gg.whattv.activity.MainActivity;

import java.util.Calendar;

public class DialogUtils {

    /**
     * 普通对话框
     */
    public static AlertDialog showAlertDialog(Context context, String title, String message, View view, String positive, String negative, final AlertCallBack callBack) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setView(view);
        builder.setPositiveButton(positive, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                callBack.positive(dialog, which);
            }
        });
        builder.setNegativeButton(negative, null);
        AlertDialog dialog = builder.create();
        dialog.show();
        return dialog;
    }

    public interface AlertCallBack {
        void positive(DialogInterface dialog, int which);
    }

    /**
     * 进度对话框
     */
    public static ProgressDialog showProgressDialog(Context context, String title, String message, boolean horizontal) {
        ProgressDialog dialog = new ProgressDialog(context);
        dialog.setTitle(title);
        dialog.setMessage(message);
        if (horizontal)
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.show();
        return dialog;
    }

    /**
     * 时间对话框
     */
    public static void showDaterPicker(Context context, final DateSetCallBack dateSet) {
        // Use the current date as the default date in the picker
        final Calendar c = Calendar.getInstance();
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(context, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                //Calendar转化时和这里的monthOfYear是对应的
                dateSet.onDateSet(view, year, monthOfYear + 1, dayOfMonth);
            }
        }, year, month, day);

        datePickerDialog.show();
    }

    public interface DateSetCallBack {
        void onDateSet(DatePicker view, int year, int month, int day);
    }

    /**
     * 样式对话框
     */
    public static void showThemeDialog(AppCompatActivity activity) {
        ThemeDialog dialog = new ThemeDialog();
        dialog.show(activity.getSupportFragmentManager(), "theme");
    }

    public static class ThemeDialog extends DialogFragment implements View.OnClickListener {
        @Nullable
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container
                , Bundle savedInstanceState) {
            //hide the dialog title
            this.getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);

            final View layout = inflater.inflate(R.layout.dialog_theme, container, false);
            layout.findViewById(R.id.blue_theme).setOnClickListener(this);
            layout.findViewById(R.id.indigo_theme).setOnClickListener(this);
            layout.findViewById(R.id.green_theme).setOnClickListener(this);
            layout.findViewById(R.id.red_theme).setOnClickListener(this);
            layout.findViewById(R.id.blue_grey_theme).setOnClickListener(this);
            layout.findViewById(R.id.black_theme).setOnClickListener(this);
            layout.findViewById(R.id.purple_theme).setOnClickListener(this);
            layout.findViewById(R.id.orange_theme).setOnClickListener(this);
            layout.findViewById(R.id.pink_theme).setOnClickListener(this);
            return layout;
        }

        //call when dialog show
        @Override
        public void onStart() {
            super.onStart();

            Dialog dialog = getDialog();

            if (dialog != null) {
                //get the size fo parent
                int width = ViewGroup.LayoutParams.MATCH_PARENT;
                int height = ViewGroup.LayoutParams.MATCH_PARENT;

                //get dialog window and set window the size
                Window dialogWindow = dialog.getWindow();
                dialogWindow.setLayout(width, height);
            }
        }

        @Override
        public void onClick(View v) {
            String theme;

            switch (v.getId()) {
                case R.id.blue_theme:
                    theme = "Blue";
                    break;
                case R.id.indigo_theme:
                    theme = "Indigo";
                    break;
                case R.id.green_theme:
                    theme = "Green";
                    break;
                case R.id.red_theme:
                    theme = "Red";
                    break;
                case R.id.blue_grey_theme:
                    theme = "BlueGrey";
                    break;
                case R.id.black_theme:
                    theme = "Black";
                    break;
                case R.id.orange_theme:
                    theme = "Orange";
                    break;
                case R.id.purple_theme:
                    theme = "Purple";
                    break;
                case R.id.pink_theme:
                    theme = "Pink";
                    break;
                default:
                    theme = "Green";
                    break;
            }
            PreferenceUtils.putString(getContext(), "settings_theme", theme);

            //this method can be lay in this line or next line ,this line have specify animation
            getActivity().finish();

            //start activity and reset the theme in onCreate method
            startActivity(new Intent(getContext(), MainActivity.class));
        }
    }


}
