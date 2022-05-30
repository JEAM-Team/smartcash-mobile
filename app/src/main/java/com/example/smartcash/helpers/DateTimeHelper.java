package com.example.smartcash.helpers;

import android.os.Build;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateTimeHelper {
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static String toBrazilianFormat(LocalDate date){
        return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}
