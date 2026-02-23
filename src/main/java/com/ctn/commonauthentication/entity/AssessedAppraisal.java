package com.ctn.commonauthentication.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Entity
@Table(name = "assessedappraisalview")
public class AssessedAppraisal {



    @Id
    private int virtual_id;
    private String appraisalid;

    private Integer shopid;
    private String assessed_datetime;
    private String email_sent_time;
    private String company_name;
    private Boolean is_rejected_by_shop;
    private String status;
    private String type;
    private String note;
    private String message_for_matching_shop;
    private String ip;
    private String param;
    private String customer_phone;
    private String customer_name;
    private String customer_email;
    private String customer_post_number;
    private String customer_prefecture;
    private String customer_municipalities;
    private String customer_address;
    private String customer_address_detail;
    private String car_type;
    private String car_maker;
    private String car_model_year;
    private String car_traveled_distance;
    private String inspect_remain;
    private String car_state;
    private String runnable;
    private String wheel_drive;
    private String fuel;
    private String shift;
    private String accident;
    private String displacement;
    private String body_color;
    private String loan;
    private String desire_date;
    private String grade;
    private String exterior;
    private String scratch;
    private String dent;
    private String peel;
    private String rust;
    private String interior;
    private String dirt;
    private String tear;
    private String air_conditioning;
    private String smoke;
    private String navigation;
    private String auto_slide;
    private String leather_sheet;
    private String handle_type;
    private String back_monitor;
    private String sunroof;
    private String wheel;
    private String assessment_date_first;
    private String assessment_date_second;
    private String assessment_date_third;
    private String request_ymd;
    private String craeted_at;
    private String updated_at;
    private String version;
    private String saved_utm_param;
    private String fpc;


    public AssessedAppraisal() {
    }

    public AssessedAppraisal(int virtual_id , String appraisalid, int shopid, String assessed_datetime,String email_sent_datetime, String company_name, String status, String type, String note, String message_for_matching_shop, String ip, String param, String customer_phone, String customer_name, String customer_email, String customer_post_number, String customer_prefecture, String customer_municipalities, String customer_address, String customer_address_detail, String car_type, String car_maker, String car_model_year, String car_traveled_distance, String inspect_remain, String car_state, String runnable, String wheel_drive, String fuel, String shift, String accident, String displacement, String body_color, String loan, String desire_date, String grade, String exterior, String scratch, String dent, String peel, String rust, String interior, String dirt, String tear, String air_conditioning, String smoke, String navigation, String auto_slide, String leather_sheet, String handle_type, String back_monitor, String sunroof, String wheel, String assessment_date_first, String assessment_date_second, String assessment_date_third, String request_ymd, String craeted_at, String updated_at, String version, String saved_utm_param, String fpc) {
        this.appraisalid = appraisalid;
        this.shopid = shopid;
        this.assessed_datetime = assessed_datetime;
        this.email_sent_time = email_sent_datetime;
        this.company_name = company_name;
        this.status = status;
        this.type = type;
        this.note = note;
        this.message_for_matching_shop = message_for_matching_shop;
        this.ip = ip;
        this.param = param;
        this.customer_phone = customer_phone;
        this.customer_name = customer_name;
        this.customer_email = customer_email;
        this.customer_post_number = customer_post_number;
        this.customer_prefecture = customer_prefecture;
        this.customer_municipalities = customer_municipalities;
        this.customer_address = customer_address;
        this.customer_address_detail = customer_address_detail;
        this.car_type = car_type;
        this.car_maker = car_maker;
        this.car_model_year = car_model_year;
        this.car_traveled_distance = car_traveled_distance;
        this.inspect_remain = inspect_remain;
        this.car_state = car_state;
        this.runnable = runnable;
        this.wheel_drive = wheel_drive;
        this.fuel = fuel;
        this.shift = shift;
        this.accident = accident;
        this.displacement = displacement;
        this.body_color = body_color;
        this.loan = loan;
        this.desire_date = desire_date;
        this.grade = grade;
        this.exterior = exterior;
        this.scratch = scratch;
        this.dent = dent;
        this.peel = peel;
        this.rust = rust;
        this.interior = interior;
        this.dirt = dirt;
        this.tear = tear;
        this.air_conditioning = air_conditioning;
        this.smoke = smoke;
        this.navigation = navigation;
        this.auto_slide = auto_slide;
        this.leather_sheet = leather_sheet;
        this.handle_type = handle_type;
        this.back_monitor = back_monitor;
        this.sunroof = sunroof;
        this.wheel = wheel;
        this.assessment_date_first = assessment_date_first;
        this.assessment_date_second = assessment_date_second;
        this.assessment_date_third = assessment_date_third;
        this.request_ymd = request_ymd;
        this.craeted_at = craeted_at;
        this.updated_at = updated_at;
        this.version = version;
        this.saved_utm_param = saved_utm_param;
        this.virtual_id = virtual_id;
        this.fpc = fpc;
    }



    public String getAssessed_datetime() {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSSSS");
            // Parse the input string
            Date date = inputFormat.parse(assessed_datetime);

            // Output format
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy/MM/dd H:mm:ss");

            // Format the date as a string
            String formattedDate = outputFormat.format(date);
            return formattedDate;
        }catch (Exception ex) {
            return assessed_datetime;
        }

    }

    public void setAssessed_datetime(String assessed_datetime) {
        this.assessed_datetime = assessed_datetime;
    }

    public String getEmail_sent_datetime() {
        try {
            String formattedDate = "";
            if (email_sent_time != null) {
                // Define input format
                DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");

                // Parse the input string
                LocalDateTime dateTime = LocalDateTime.parse(email_sent_time, inputFormatter);
                // Output format
                DateTimeFormatter outputFormatter = DateTimeFormatter.ofPattern("yyyy/M/d H:mm:ss");

                // Format the date as a string
                formattedDate = dateTime.format(outputFormatter);
            }
            return formattedDate;
        }catch (Exception ex) {
            return email_sent_time;
        }

    }

    public void setEmail_sent_datetime(String email_sent_datetime) {
        this.email_sent_time = email_sent_datetime;
    }

    public String getCompany_name() {
        return company_name;
    }

    public void setCompany_name(String company_name) {
        this.company_name = company_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getMessage_for_matching_shop() {
        return message_for_matching_shop;
    }

    public void setMessage_for_matching_shop(String message_for_matching_shop) {
        this.message_for_matching_shop = message_for_matching_shop;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
    }

    public String getCustomer_phone() {
        return customer_phone;
    }

    public void setCustomer_phone(String customer_phone) {
        this.customer_phone = customer_phone;
    }

    public String getCustomer_name() {
        return customer_name;
    }

    public void setCustomer_name(String customer_name) {
        this.customer_name = customer_name;
    }

    public String getCustomer_email() {
        return customer_email;
    }

    public void setCustomer_email(String customer_email) {
        this.customer_email = customer_email;
    }

    public String getCustomer_post_number() {
        return customer_post_number;
    }

    public void setCustomer_post_number(String customer_post_number) {
        this.customer_post_number = customer_post_number;
    }

    public String getCustomer_prefecture() {
        return customer_prefecture;
    }

    public void setCustomer_prefecture(String customer_prefecture) {
        this.customer_prefecture = customer_prefecture;
    }

    public String getCustomer_municipalities() {
        return customer_municipalities;
    }

    public void setCustomer_municipalities(String customer_municipalities) {
        this.customer_municipalities = customer_municipalities;
    }

    public String getCustomer_address() {
        return customer_address;
    }

    public void setCustomer_address(String customer_address) {
        this.customer_address = customer_address;
    }

    public String getCustomer_address_detail() {
        return customer_address_detail;
    }

    public void setCustomer_address_detail(String customer_address_detail) {
        this.customer_address_detail = customer_address_detail;
    }

    public String getCar_type() {
        return car_type;
    }

    public void setCar_type(String car_type) {
        this.car_type = car_type;
    }

    public String getCar_maker() {
        return car_maker;
    }

    public void setCar_maker(String car_maker) {
        this.car_maker = car_maker;
    }

    public String getCar_model_year() {
        return car_model_year;
    }

    public void setCar_model_year(String car_model_year) {
        this.car_model_year = car_model_year;
    }

    public String getCar_traveled_distance() {
        return car_traveled_distance;
    }

    public void setCar_traveled_distance(String car_traveled_distance) {
        this.car_traveled_distance = car_traveled_distance;
    }

    public String getInspect_remain() {
        return inspect_remain;
    }

    public void setInspect_remain(String inspect_remain) {
        this.inspect_remain = inspect_remain;
    }

    public String getCar_state() {
        return car_state;
    }

    public void setCar_state(String car_state) {
        this.car_state = car_state;
    }

    public String getRunnable() {
        return runnable;
    }

    public void setRunnable(String runnable) {
        this.runnable = runnable;
    }

    public String getWheel_drive() {
        return wheel_drive;
    }

    public void setWheel_drive(String wheel_drive) {
        this.wheel_drive = wheel_drive;
    }

    public String getFuel() {
        return fuel;
    }

    public void setFuel(String fuel) {
        this.fuel = fuel;
    }

    public String getShift() {
        return shift;
    }

    public void setShift(String shift) {
        this.shift = shift;
    }

    public String getAccident() {
        return accident;
    }

    public void setAccident(String accident) {
        this.accident = accident;
    }

    public String getDisplacement() {
        return displacement;
    }

    public void setDisplacement(String displacement) {
        this.displacement = displacement;
    }

    public String getBody_color() {
        return body_color;
    }

    public void setBody_color(String body_color) {
        this.body_color = body_color;
    }

    public String getLoan() {
        return loan;
    }

    public void setLoan(String loan) {
        this.loan = loan;
    }

    public String getDesire_date() {
        return desire_date;
    }

    public void setDesire_date(String desire_date) {
        this.desire_date = desire_date;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getExterior() {
        return exterior;
    }

    public void setExterior(String exterior) {
        this.exterior = exterior;
    }

    public String getScratch() {
        return scratch;
    }

    public void setScratch(String scratch) {
        this.scratch = scratch;
    }

    public String getDent() {
        return dent;
    }

    public void setDent(String dent) {
        this.dent = dent;
    }

    public String getPeel() {
        return peel;
    }

    public void setPeel(String peel) {
        this.peel = peel;
    }

    public String getRust() {
        return rust;
    }

    public void setRust(String rust) {
        this.rust = rust;
    }

    public String getInterior() {
        return interior;
    }

    public void setInterior(String interior) {
        this.interior = interior;
    }

    public String getDirt() {
        return dirt;
    }

    public void setDirt(String dirt) {
        this.dirt = dirt;
    }

    public String getTear() {
        return tear;
    }

    public void setTear(String tear) {
        this.tear = tear;
    }

    public String getAir_conditioning() {
        return air_conditioning;
    }

    public void setAir_conditioning(String air_conditioning) {
        this.air_conditioning = air_conditioning;
    }

    public String getSmoke() {
        return smoke;
    }

    public void setSmoke(String smoke) {
        this.smoke = smoke;
    }

    public String getNavigation() {
        return navigation;
    }

    public void setNavigation(String navigation) {
        this.navigation = navigation;
    }

    public int getVirtual_id() {
        return virtual_id;
    }

    public void setVirtual_id(int virtual_id) {
        this.virtual_id = virtual_id;
    }

    public String getAuto_slide() {
        return auto_slide;
    }

    public void setAuto_slide(String auto_slide) {
        this.auto_slide = auto_slide;
    }

    public String getLeather_sheet() {
        return leather_sheet;
    }

    public void setLeather_sheet(String leather_sheet) {
        this.leather_sheet = leather_sheet;
    }

    public String getHandle_type() {
        return handle_type;
    }

    public void setHandle_type(String handle_type) {
        this.handle_type = handle_type;
    }

    public String getEmail_sent_time() {
        return email_sent_time;
    }

    public void setEmail_sent_time(String email_sent_time) {
        this.email_sent_time = email_sent_time;
    }

    public Boolean getIs_rejected_by_shop() {
        return is_rejected_by_shop;
    }

    public void setIs_rejected_by_shop(Boolean is_rejected_by_shop) {
        this.is_rejected_by_shop = is_rejected_by_shop;
    }

    public String getBack_monitor() {
        return back_monitor;
    }

    public void setBack_monitor(String back_monitor) {
        this.back_monitor = back_monitor;
    }

    public String getSunroof() {
        return sunroof;
    }

    public void setSunroof(String sunroof) {
        this.sunroof = sunroof;
    }

    public String getWheel() {
        return wheel;
    }

    public void setWheel(String wheel) {
        this.wheel = wheel;
    }

    public String getAssessment_date_first() {
        return assessment_date_first;
    }

    public void setAssessment_date_first(String assessment_date_first) {
        this.assessment_date_first = assessment_date_first;
    }

    public String getAssessment_date_second() {
        return assessment_date_second;
    }

    public void setAssessment_date_second(String assessment_date_second) {
        this.assessment_date_second = assessment_date_second;
    }

    public String getAssessment_date_third() {
        return assessment_date_third;
    }

    public void setAssessment_date_third(String assessment_date_third) {
        this.assessment_date_third = assessment_date_third;
    }

    public String getRequest_ymd() {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy/MM/dd H:mm:ss");
            // Parse the input string
            Date date = inputFormat.parse(request_ymd);

            // Output format
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy/MM/dd H:mm:ss");

            // Format the date as a string
            String formattedDate = outputFormat.format(date);
            return formattedDate;
        }catch (Exception ex) {
            return request_ymd;
        }

    }

    public void setRequest_ymd(String request_ymd) {
        this.request_ymd = request_ymd;
    }

    public String getCraeted_at() {
        return craeted_at;
    }

    public void setCraeted_at(String craeted_at) {
        this.craeted_at = craeted_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getSaved_utm_param() {
        return saved_utm_param;
    }

    public void setSaved_utm_param(String saved_utm_param) {
        this.saved_utm_param = saved_utm_param;
    }
    public String[] getAllAsStringArray() throws NoSuchFieldException, IllegalAccessException {
        Field[] fields = this.getClass().getDeclaredFields();
        String[] allValues = new String[fields.length];

        for (int i = 0; i < fields.length; i++) {
            fields[i].setAccessible(true);
            Object value = fields[i].get(this);

            if(value == null) {
                allValues[i] = "";
            }else {
                allValues[i] = String.valueOf(value);
            }

        }

        return allValues;
    }

    public String
    getAppraisalid() {
        return appraisalid;
    }

    public void setAppraisalid(String appraisalid) {
        this.appraisalid = appraisalid;
    }

    public int getShopid() {
        return shopid;
    }

    public void setShopid(int shopid) {
        this.shopid = shopid;
    }

    public String getFpc() {
        return fpc;
    }

    public void setFpc(String fpc) {
        this.fpc = fpc;
    }
}

