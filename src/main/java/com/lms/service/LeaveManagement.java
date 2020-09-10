package com.lms.service;

import java.text.SimpleDateFormat;
import java.util.*;

public class LeaveManagement {
    private String day;
    private int dd,mm,yyyy;

    public void setDay(String string){
        this.day = new String(string);
        System.out.println(day);
        initializer();
    }
    void initializer(){
        String[] data = day.split("-");
        dd = Integer.parseInt(data[0]);
        mm = Integer.parseInt(data[1]);
        yyyy = Integer.parseInt(data[2]);
    }

    public int leavesPerQuater(){
        int leave = 0;
        for (int i=3; i< 13 ; i=i+3){
            if(mm == i){
                leave = 1;
                break;
            }
            else if(mm < i){
                leave = Math.round(((i-mm+1)*4)/3);
                break;
            }
        }
        return leave;
    }
    public int totalLeave(String fromDate ,String toDate) throws Exception{
        int leave =0;

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date startDate = formatter.parse(fromDate);
        Date endDate = formatter.parse(toDate);
        Calendar start = Calendar.getInstance();
        start.setTime(startDate);
        Calendar end = Calendar.getInstance();
        end.setTime(endDate);
        end.add(end.DATE, 1);

        for (Date date = start.getTime(); start.before(end); start.add(Calendar.DATE, 1), date = start.getTime()) {

            Calendar c = Calendar.getInstance();
            c.setTime(date);
            int dayOfWeek = c.get(Calendar.DAY_OF_WEEK);
            if(dayOfWeek == 1 || dayOfWeek == 7){
                continue;
            }
            else
                leave++;
        }
        return leave;
    }

}
