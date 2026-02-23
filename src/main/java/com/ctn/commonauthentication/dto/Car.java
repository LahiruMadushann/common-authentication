package com.ctn.commonauthentication.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Car {

    private String car_type;
    private String car_maker;
    private String car_model_year;
    private String car_traveled_distance;
    private String body_type;
    private List<String> body_types;
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

    public CarType type() {
        return new CarType(this.car_type);
    }
    public Maker maker() {
        return new Maker(this.car_maker);
    }
    public BodyType bodyType() {
        if (body_types != null && !body_types.isEmpty()) {
            return new BodyType(body_types.get(0));
        }
        return new BodyType(this.body_type);
    }

    public List<BodyType> bodyTypes() {
        List<BodyType> result = new java.util.ArrayList<>();

        if (body_types != null && !body_types.isEmpty()) {
            result = body_types.stream()
                    .filter(bt -> bt != null && !bt.trim().isEmpty())
                    .map(BodyType::new)
                    .collect(java.util.stream.Collectors.toList());
        }

        if (result.isEmpty() && body_type != null && !body_type.trim().isEmpty()) {
            result.add(new BodyType(body_type));
        }

        return result;
    }
    public MadeFrom madeFrom() {
        return new MadeFrom("いすゞ".equals(this.car_maker) ||
                "スズキ".equals(this.car_maker) ||   // メルセデス・ベンツ
                "スバル".equals(this.car_maker) ||
                "ゼロスポーツ".equals(this.car_maker) ||
                "ダイハツ".equals(this.car_maker) ||
                "トミーカイラ".equals(this.car_maker) ||
                "トヨタ".equals(this.car_maker) ||
                "ホンダ".equals(this.car_maker) ||
                "マツダ".equals(this.car_maker) ||
                "レクサス".equals(this.car_maker) ||
                "三菱".equals(this.car_maker) ||
                "三菱ふそう".equals(this.car_maker) ||
                "光岡自動車".equals(this.car_maker) ||
                "日産".equals(this.car_maker) ||
                "日野自動車".equals(this.car_maker) ||
                "KTM".equals(this.car_maker));
    }
    public Runnable runnable_comp() {

//        if (this.accident == null) this.accident = "不明";
//        if (this.runnable == null) this.runnable = "走行可";
//        boolean moving = "走行可".equals(this.runnable);
//        boolean accidentNg = "なし".equals(this.accident) || "あり(修復済)".equals(this.accident) || "不明".equals(this.accident);
        boolean moving =false;
        boolean accidentNg = false;

        String runnable =null;
        String accident = null;

        if(Objects.equals(this.accident, "なし") && Objects.equals(this.runnable, "走行可")){
            moving = true;
            accidentNg = true;
        }else if(Objects.equals(this.accident, "あり(修復済)") && Objects.equals(this.runnable, "走行可")){
            moving = true;
            accidentNg = true;

        }else if(Objects.equals(this.accident, "あり(修復済み)") && Objects.equals(this.runnable, "走行可")){
            moving = true;
            accidentNg = true;

        }else if(Objects.equals(this.accident, "不明") && Objects.equals(this.runnable, "走行不可")){
            moving = true;
            accidentNg = true;
        }

        //new part
        if(Objects.equals(this.accident, "なし") && Objects.equals(this.runnable, "走行可")) {
            runnable = "YES";
            accident = "NO";

        }else if (Objects.equals(this.accident, "なし") && Objects.equals(this.runnable, "走行不可"))   {
            runnable = "NO";
            accident = "NO";

        }else if(Objects.equals(this.accident, "あり(修復済)") && Objects.equals(this.runnable, "走行可")){
            runnable = "YES";
            accident = "RESTORED";

        }else if(Objects.equals(this.accident, "あり(修復済み)") && Objects.equals(this.runnable, "走行可")){
            runnable = "YES";
            accident = "RESTORED";

        }else if(Objects.equals(this.accident, "あり(修復済)") && Objects.equals(this.runnable, "走行不可")){
            runnable = "NO";
            accident = "RESTORED";

        }else if(Objects.equals(this.accident, "あり(修復済み)") && Objects.equals(this.runnable, "走行不可")){
            runnable = "NO";
            accident = "RESTORED";

        }else if(Objects.equals(this.accident, "あり(未修理)") && Objects.equals(this.runnable, "走行可")){
            runnable = "YES";
            accident = "NOT_RESTORED";

        }else if(Objects.equals(this.accident, "あり(未修理)") && Objects.equals(this.runnable, "走行不可")){
            runnable = "NO";
            accident = "NOT_RESTORED";

        }else if(Objects.equals(this.accident, "不明") && Objects.equals(this.runnable, "走行可")){
            runnable = "YES";
            accident = "UNKNOWN";
            //Not drivable
        }else if(Objects.equals(this.accident, "不明") && Objects.equals(this.runnable, "走行不可")){
            runnable = "NO";
            accident = "UNKNOWN";
        } else {
            runnable = "YES";
            accident = "NO";
        }

        return new Runnable(moving,accidentNg,runnable,accident);
    }

    public CTNModelYear modelYear() {
        return new CTNModelYear(this.car_model_year);
    }

    public CTNTraveledDistance traveledDistance() {
        return new CTNTraveledDistance(this.car_traveled_distance);
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

    public String getBody_type() {
        return body_type;
    }

    public void setBody_type(String body_type) {
        this.body_type = body_type;
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

    public List<String> getBody_types() {
        return body_types;
    }

    public void setBody_types(List<String> body_types) {
        this.body_types = body_types;
    }
}
