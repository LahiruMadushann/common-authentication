package com.ctn.commonauthentication.entity;

import com.ctn.commonauthentication.dto.*;
import com.ctn.commonauthentication.dto.BodyType;
import com.ctn.commonauthentication.dto.Runnable;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Speciality {


    public enum Exclusivity {EX, NON_EX,BOTH}
    public enum DomesticOrImport { DOMESTIC, IMPORT, NONE ,BOTH }
    public enum NotMove { OK, NG ,ANY} // 不動車をマッチ対象とするか
    public enum NotSpeciality { OK, NG } // 専門外の場合にマッチ対象とするか
    private DomesticOrImport domesticOrImport;
    private NotMove handleNotMove;
    private NotSpeciality handleNotSpeciality;
    private CarTypeSpeciality carTypeSpeciality;
    private MakerSpeciality makerSpeciality;
    private BodyTypeSpeciality bodyTypeSpeciality;
    private Exclusivity exclusivity;
    private Integer shopCeilMatchCount;
    private Integer cielMatchCount;

    private Integer assessedCount;

    /**
     * version two
     */

    private WishMaker wishMaker;
    private NGMaker ngMaker;
    private NGCarType ngCarType;
    private NGBodyType ngBodyType;
    private WishBodyType wishBodyType;

    //add car type
    private WishCarType wishCarType;


    private CTNModelYear yearWishesFrom;
    private CTNModelYear yearWishesTo;
    private CTNModelYear yearNGFrom;
    private CTNModelYear yearNGTo;
    private CTNTraveledDistance distanceWishFrom;
    private CTNTraveledDistance distanceWishTo;
    private CTNTraveledDistance distanceNGFrom;
    private CTNTraveledDistance distanceNGTo;

    private List<PatternDto> patterns;

    public List<PatternDto> getPatterns() {
        return patterns;
    }

    public void setPatterns(List<PatternDto> patterns) {
        this.patterns = patterns;
    }

    private String saleDateDetermination;

    public String getSaleDateDetermination() {
        return saleDateDetermination;
    }

    public void setSaleDateDetermination(String saleDateDetermination) {
        this.saleDateDetermination = saleDateDetermination;
    }

    public Integer getShopCeilMatchCount() {
        return shopCeilMatchCount;
    }

    public void setShopCeilMatchCount(Integer shopCeilMatchCount) {
        this.shopCeilMatchCount = shopCeilMatchCount;
    }

    public Exclusivity getExclusivity() {
        return exclusivity;
    }



    public void setExclusivity(Exclusivity exclusivity) {
        this.exclusivity = exclusivity;
    }


    public CTNModelYear getYearWishesFrom() {
        return yearWishesFrom;
    }

    public void setYearWishesFrom(CTNModelYear yearWishesFrom) {
        this.yearWishesFrom = yearWishesFrom;
    }

    public CTNModelYear getYearWishesTo() {
        return yearWishesTo;
    }

    public void setYearWishesTo(CTNModelYear yearWishesTo) {
        this.yearWishesTo = yearWishesTo;
    }

    public CTNModelYear getYearNGFrom() {
        return yearNGFrom;
    }

    public void setYearNGFrom(CTNModelYear yearNGFrom) {
        this.yearNGFrom = yearNGFrom;
    }

    public CTNModelYear getYearNGTo() {
        return yearNGTo;
    }

    public void setYearNGTo(CTNModelYear yearNGTo) {
        this.yearNGTo = yearNGTo;
    }

    public CTNTraveledDistance getDistanceWishFrom() {
        return distanceWishFrom;
    }

    public void setDistanceWishFrom(CTNTraveledDistance distanceWishFrom) {
        this.distanceWishFrom = distanceWishFrom;
    }

    public CTNTraveledDistance getDistanceWishTo() {
        return distanceWishTo;
    }

    public void setDistanceWishTo(CTNTraveledDistance distanceWishTo) {
        this.distanceWishTo = distanceWishTo;
    }

    public CTNTraveledDistance getDistanceNGFrom() {
        return distanceNGFrom;
    }

    public void setDistanceNGFrom(CTNTraveledDistance distanceNGFrom) {
        this.distanceNGFrom = distanceNGFrom;
    }

    public CTNTraveledDistance getDistanceNGTo() {
        return distanceNGTo;
    }

    public void setDistanceNGTo(CTNTraveledDistance distanceNGTo) {
        this.distanceNGTo = distanceNGTo;
    }

    public boolean active() {
        return domesticOrImport != null && handleNotMove != null && handleNotSpeciality != null;
    }


    public boolean match(CarType carType) {
        return this.carTypeSpeciality.match(carType);
    }
    public boolean matchWithMaker(CarType carType, Maker maker) {
        return this.carTypeSpeciality.matchCarTypeWithMaker(carType,maker);
    }
    public boolean match(OperatorAppraisal maker) {
        return this.makerSpeciality.match(maker);
    }
    public boolean match(MadeFrom madeFrom) {
        if(this.domesticOrImport == DomesticOrImport.DOMESTIC && madeFrom.isDomestic()) return true;
        if(this.domesticOrImport == DomesticOrImport.IMPORT && madeFrom.isImport()) return true;
        if(this.domesticOrImport == DomesticOrImport.BOTH) return true;
        if(this.domesticOrImport == DomesticOrImport.NONE) return false;
        return false;
    }

    public boolean matchAny(List<BodyType> bodyTypes) {
        if (this.bodyTypeSpeciality == null) {
            return false;
        }
        return this.bodyTypeSpeciality.matchAny(bodyTypes);
    }

    public boolean match(BodyType bodyType) {
        return this.bodyTypeSpeciality.match(bodyType);

    }

    public boolean match(Runnable runnable) {

        for (PatternDto pattern : this.patterns) {
            if (pattern.getAccidentHistory() == null || pattern.getRunnable() == null) {
                if (this.handleNotMove == null) return false;
                if (this.handleNotMove == NotMove.ANY) return true;
                if (this.handleNotMove == NotMove.OK) return runnable.oK();
                if (this.handleNotMove == NotMove.NG) return runnable.nG();
                return false;
            }
            String accidentHistory = pattern.getAccidentHistory();
            String runnableStatus = pattern.getRunnable();

            if (this.handleNotMove == NotMove.OK && (accidentHistory != null || runnableStatus != null)) {
                String accident;
                String executable;
                accident = switch (runnable.getAccident()) {
                    case "NO" -> "なし";
                    case "RESTORED" -> "あり(修復済)";
                    case "NOT_RESTORED" -> "あり(未修理)";
                    case "UNKNOWN" -> "不明";
                    default -> "不明";
                };

                executable = runnable.getMoving().equals("YES") ? "走行可" : "走行不可";

                if (accidentHistory.equals(accident) && runnableStatus.equals(executable)) {
                    return true;
                }

            }

        }
        if (this.handleNotMove == NotMove.OK ) {
            return false;
        }
        if (this.handleNotMove == NotMove.ANY ) {
            return true;
        }
        if (this.handleNotMove == NotMove.NG ) {
            return runnable.nG();
        }
        return true;
    }

//    private boolean matchesPattern(Runnable runnable, String accidentHistory, String runnableStatus) {
//        String accident = runnable.accident();
//        String runnableState = runnable.runnable();
//
//        return (accident.equals("NO") && accidentHistory.equals("なし"))
//                || (accident.equals("RESTORED") && accidentHistory.equals("あり(修復済)"))
//                || (accident.equals("NOT_RESTORED") && accidentHistory.equals("あり(未修理)"))
//                || (accident.equals("UNKNOWN") && accidentHistory.equals("不明"))
//                || (runnableState.equals("YES") && runnableStatus.equals("走行可"))
//                || (runnableState.equals("NO") && runnableStatus.equals("走行不可"));
//    }


    public boolean notNG(Maker maker ) {
        return ! this.ngMaker.contains(maker);
    }
    public boolean notNG(BodyType bodyType ) {
        return ! this.ngBodyType.contains(bodyType);
    }

    public boolean notNG(List<BodyType> bodyTypes) {
        if (bodyTypes == null || bodyTypes.isEmpty()) {
            return true;
        }

        return bodyTypes.stream()
                .allMatch(bodyType -> !this.ngBodyType.contains(bodyType));
    }

    //car type related things
    public boolean notNG(CarType carType ) {
        return ! this.ngCarType.contains(carType);
    }
    //-------
    public boolean notNG(CTNModelYear modelYear ) {
        if (this.yearWishesFrom == null && this.yearWishesTo == null) return true;
        if (this.yearWishesFrom != null && this.yearWishesTo == null) return yearWishesFrom.isBefore(modelYear);
        if (this.yearWishesFrom == null && this.yearWishesTo != null) return yearWishesTo.isAfter(modelYear);;
        if (this.yearWishesFrom.unspecified() && this.yearWishesTo.unspecified()) return true;
        if (this.yearWishesFrom.specified() && this.yearWishesTo.unspecified()) return yearWishesFrom.isBefore(modelYear);
        if (this.yearWishesFrom.unspecified() && this.yearWishesTo.specified()) return yearWishesTo.isAfter(modelYear);
        if (this.yearWishesFrom.specified() && this.yearWishesTo.specified()) return yearWishesFrom.isBefore(modelYear) && yearWishesTo.isAfter(modelYear);
        return false;
    }

    public boolean match(CTNModelYear modelYear ) {
        if (this.yearWishesFrom == null && this.yearWishesTo == null) return false;
        if (this.yearWishesFrom != null && this.yearWishesTo == null) return yearWishesFrom.isBefore(modelYear);
        if (this.yearWishesFrom == null && this.yearWishesTo != null) return yearWishesTo.isAfter(modelYear);;
        if (this.yearWishesFrom.unspecified() && this.yearWishesTo.unspecified()) return false;
        if (this.yearWishesFrom.specified() && this.yearWishesTo.unspecified()) return yearWishesFrom.isBefore(modelYear);
        if (this.yearWishesFrom.unspecified() && this.yearWishesTo.specified()) return yearWishesTo.isAfter(modelYear);
        if (this.yearWishesFrom.specified() && this.yearWishesTo.specified()) return yearWishesFrom.isBefore(modelYear) && yearWishesTo.isAfter(modelYear);
        return false;
    }
    public boolean notNG(CTNTraveledDistance carTraveledDistance ) {
        if (this.distanceWishFrom == null && this.distanceWishTo == null) return true;
        if (this.distanceWishFrom != null && this.distanceWishTo == null) return distanceWishFrom.isBefore(carTraveledDistance);
        if (this.distanceWishFrom == null && this.distanceWishTo != null) return distanceWishTo.isAfter(carTraveledDistance);;
        if (this.distanceWishFrom.unspecified() && this.distanceWishTo.unspecified()) return true;
        if (this.distanceWishFrom.specified() && this.distanceWishTo.unspecified()) return  distanceWishTo.isBefore(carTraveledDistance);
        if (this.distanceWishFrom.unspecified() && this.distanceWishTo.specified()) return  distanceWishTo.isAfter(carTraveledDistance);
        if (this.distanceWishFrom.specified() && this.distanceWishTo.specified()) return  (distanceWishFrom.isBefore(carTraveledDistance) && distanceWishTo.isAfter(carTraveledDistance));
        return false;
    }

    public boolean match(CTNTraveledDistance carTraveledDistance ) {
        if (this.distanceWishFrom == null && this.distanceWishTo == null) return false;
        if (this.distanceWishFrom != null && this.distanceWishTo == null) return distanceWishFrom.isBefore(carTraveledDistance);
        if (this.distanceWishFrom == null && this.distanceWishTo != null) return distanceWishTo.isAfter(carTraveledDistance);;
        if (this.distanceWishFrom.unspecified() && this.distanceWishTo.unspecified()) return false;
        if (this.distanceWishFrom.specified() && this.distanceWishTo.unspecified()) return ! distanceWishFrom.isBefore(carTraveledDistance);
        if (this.distanceWishFrom.unspecified() && this.distanceWishTo.specified()) return ! distanceWishTo.isAfter(carTraveledDistance);
        if (this.distanceWishFrom.specified() && this.distanceWishTo.specified()) return (distanceWishFrom.isBefore(carTraveledDistance) && distanceWishTo.isAfter(carTraveledDistance));
        return false;
    }

    public boolean notNG(String string ) {
        if(this.saleDateDetermination == null) return false;
//        if (!this.saleDateDetermination.equals("NG")) return true;
        if (this.saleDateDetermination.equals("NG") && string.equals("未定")) return false;
        if (!this.saleDateDetermination.equals("NG")) return true;
        return true;
    }

    public DomesticOrImport getDomesticOrImport() {
        return domesticOrImport;
    }

    public void setDomesticOrImport(DomesticOrImport domesticOrImport) {
        this.domesticOrImport = domesticOrImport;
    }

    public NotMove getHandleNotMove() {
        return handleNotMove;
    }

    public void setHandleNotMove(NotMove handleNotMove) {
        this.handleNotMove = handleNotMove;
    }

    public NotSpeciality getHandleNotSpeciality() {
        return handleNotSpeciality;
    }

    public void setHandleNotSpeciality(NotSpeciality handleNotSpeciality) {
        this.handleNotSpeciality = handleNotSpeciality;
    }

    public CarTypeSpeciality getCarTypeSpeciality() {
        return carTypeSpeciality;
    }

    public void setCarTypeSpeciality(CarTypeSpeciality carTypeSpeciality) {
        this.carTypeSpeciality = carTypeSpeciality;
    }

    public MakerSpeciality getMakerSpeciality() {
        return makerSpeciality;
    }

    public void setMakerSpeciality(MakerSpeciality makerSpeciality) {
        this.makerSpeciality = makerSpeciality;
    }

    public BodyTypeSpeciality getBodyTypeSpeciality() {
        return bodyTypeSpeciality;
    }

    public List<BodyType> getNonEmptyContents() {
        if (this.getBodyTypeSpeciality().getContents() == null || this.getBodyTypeSpeciality().getContents().isEmpty()) {
            return new ArrayList<>();
        }

        return this.getBodyTypeSpeciality().getContents().stream()
                .filter(bodyType -> bodyType != null &&
                        bodyType.getContent() != null &&
                        !bodyType.getContent().trim().isEmpty())
                .collect(Collectors.toList());
    }

    public void setBodyTypeSpeciality(BodyTypeSpeciality bodyTypeSpeciality) {
        this.bodyTypeSpeciality = bodyTypeSpeciality;
    }

    public WishMaker getWishMaker() {
        return wishMaker;
    }

    public void setWishMaker(WishMaker wishMaker) {
        this.wishMaker = wishMaker;
    }

    public NGMaker getNgMaker() {
        return ngMaker;
    }

    public void setNgMaker(NGMaker ngMaker) {
        this.ngMaker = ngMaker;
    }

    public WishBodyType getWishBodyType() {
        return wishBodyType;
    }

    public void setWishBodyType(WishBodyType wishBodyType) {
        this.wishBodyType = wishBodyType;
    }



    public WishCarType getWishCarType() {
        return wishCarType;
    }

    public void setWishCarType(WishCarType wishCarType) {
        this.wishCarType = wishCarType;
    }

    public Integer getAssessedCount() {
        return assessedCount;
    }

    public void setAssessedCount(Integer assessedCount) {
        this.assessedCount = assessedCount;
    }

    public NGCarType getNgCarType() {
        return ngCarType;
    }

    public void setNgCarType(NGCarType ngCarType) {
        this.ngCarType = ngCarType;
    }

    public NGBodyType getNgBodyType() {
        return ngBodyType;
    }

    public void setNgBodyType(NGBodyType ngBodyType) {
        this.ngBodyType = ngBodyType;
    }

    public Integer getCielMatchCount() {
        return cielMatchCount;
    }

    public void setCielMatchCount(Integer cielMatchCount) {
        this.cielMatchCount = cielMatchCount;
    }
}
