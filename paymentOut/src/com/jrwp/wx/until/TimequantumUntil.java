package com.jrwp.wx.until;

public class TimequantumUntil {
    public static int islaterorearly(int timquan, int hour) {
        int a = 0;
        switch (timquan) {
            case 1:
                a = 9;
                break;
            case 2:
                a = 10;
                break;
            case 3:
                a = 11;
                break;
            case 4:
                a = 14;
                break;
            case 5:
                a = 15;
                break;
            case 6:
                a = 14;
                break;
        }

        if (hour > a) {
            //迟到
            return 2;
        } else if (hour == a) {
            //准时
            return 0;
        } else {
            //早到
            return 1;
        }

    }

    public static int getTimqun(int hour) {
        if (hour < 10) {
            return 1;
        } else if (hour < 11) {
            return 2;
        } else if (hour < 14) {
            return 3;
        } else if (hour < 15) {
            return 4;
        } else if (hour < 16) {
            return 5;
        } else if (hour < 24) {
            return 6;
        }
        return 0;
    }
}
