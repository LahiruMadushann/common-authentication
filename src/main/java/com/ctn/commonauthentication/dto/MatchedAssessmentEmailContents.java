package com.ctn.commonauthentication.dto;

import lombok.Data;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class MatchedAssessmentEmailContents {

    public  String appraisalid;
    public  String status;
    public  String appraisal_type;
    public  String customer_phone;
    public  String customer_name;
    public  String customer_email;
    public  String customer_post_number;
    public  String customer_prefecture;
    public  String customer_municipalities;
    public  String customer_address;
    public  String customer_address_detail;
    public  String car_type;
    public  String car_maker;
    public  String car_model_year;
    public  String car_traveled_distance;
    public  String inspect_remain;
    public  String car_state;
    public  String runnable;
    public  String wheel_drive;
    public  String fuel;
    public  String shift;
    public  String accident;
    public  String displacement;
    public  String body_color;
    public  String loan;
    public  String desire_date;
    public  String grade;
    public  String exterior;
    public  String scratch;
    public  String dent;
    public  String peel;
    public  String rust;
    public  String interior;
    public  String dirt;
    public  String tear;
    public  String air_conditioning;
    public  String smoke;
    public  String navigation;
    public  String auto_slide;
    public  String leather_sheet;
    public  String handle_type;
    public  String back_monitor;
    public  String sunroof;
    public  String wheel;
    public List<CTNDateTime> assessment_date;
    public  String request_YMD;
    public  String formatted_request_YMD;
    public  String buyer_mail_sent_time;
    public  String note;
    public  String param;
    public  String message_for_matching_shop;
    public List<String> formatted_assessment_date = new ArrayList<>();
    public boolean isDrafted = false;
    public String draftBuyerName = "";
    public List<String> draftBuyerEmailList = new ArrayList<>();
    public boolean is_test_shop = false;

    //Generate formatted assement date
    public void generateFormattedList() {

        for (CTNDateTime dateTime : assessment_date){
            String datetimeOriginal = dateTime.toString();
            String formattedDate = formatDateString(datetimeOriginal);
            this.formatted_assessment_date.add(formattedDate);
        }

        this.formatted_request_YMD = formatDateString2(request_YMD);
        LocalDateTime currentDateTime = LocalDateTime.now();
        Timestamp timestampT = Timestamp.valueOf(currentDateTime);
        this.buyer_mail_sent_time = formatTimestamp(timestampT);
    }

    private  String formatDateString(String inputDate) {
        String DT_FORMAT = "yyyy/MM/dd H:mm:ss";
        String NEW_FORMAT = "yyyy-MM-dd";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DT_FORMAT);
            Date date = sdf.parse(inputDate);

            sdf.applyPattern(NEW_FORMAT);
            return sdf.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null; // Handle the exception according to your needs
        }
    }

    private  String formatDateString2(String inputDate) {
        String DT_FORMAT = "yyyy/MM/dd H:mm:ss";
        String NEW_FORMAT = "yyyy-MM-dd H:mm:ss";
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(DT_FORMAT);
            Date date = sdf.parse(inputDate);

            sdf.applyPattern(NEW_FORMAT);
            return sdf.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return null; // Handle the exception according to your needs
        }
    }

    private String formatTimestamp(Timestamp timestamp) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd H:mm:ss");
        String formattedDate = sdf.format(timestamp);
        if (formattedDate.contains(".")) {
            formattedDate = formattedDate.substring(0, formattedDate.indexOf('.'));
        }

        String[] parts = formattedDate.split(" ");
        String datePart = parts[0];
        String timePart = parts[1];

        String[] timeParts = timePart.split(":");
        String hourPart = timeParts[0];
        String minutePart = timeParts[1];
        String secondPart = timeParts[2];

        hourPart = hourPart.replaceFirst("^0+(?!$)", "");

        formattedDate = datePart + " " + hourPart + ":" + minutePart + ":" + secondPart;

        return formattedDate;
    }
    public MatchedAssessmentEmailContents(){}
}
