package com.ctn.commonauthentication.util;

import com.ctn.commonauthentication.dto.*;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.math.BigInteger;
import java.util.List;

@Mapper
public interface AppraisalRequestMapper {
    // Include default filter
    public OperatorAppraisal loadAppraisalRequest(long id);
    // No filters included
    public OperatorAppraisal loadAppraisalById(long id);
    List<OperatorAppraisal> loadAppraisalsByIds(List<BigInteger> ids);

    public int countLoadAppraisalRequests(
            List<String> statuses,
            List<String> types,
            String phone_no,
            String kw,
            CTNDateTime from,
            CTNDateTime to,
            boolean isTestData,
            boolean isFilterFromEmailSendTime,
            boolean isBuyer,
            List<Integer> shopIds
    );

    public List<OperatorAppraisal> loadAppraisalRequestsWithFilter(
            List<String> statuses,
            List<String> types,
            String phone_no,
            String kw,
            CTNDateTime from,
            CTNDateTime to,
            boolean isTestData,
            List<Long> ids,
            boolean isFilterFromEmailSendTime,
            boolean isBuyer,
            List<Integer> shopIds,
            int offset,
            int limit
    );
    public List<OperatorAppraisal> loadAppraisalRequestsWithFilterBuyer(
            List<String> statuses,
            List<String> types,
            String phone_no,
            String kw,
            CTNDateTime from,
            CTNDateTime to,
            boolean isTestData,
            List<Long> ids,
            boolean isFilterFromEmailSendTime,
            boolean isBuyer,
            List<Integer> shopIds
    );

    public List<AppraisalID> countLoadAppraisalRequestsWithFilter(
            List<String> statuses,
            List<String> types,
            String phone_no,
            String kw,
            CTNDateTime from,
            CTNDateTime to,
            boolean isTestData,
            boolean isFilterFromEmailSendTime,
            boolean isBuyer,
            List<Integer> shopIds
    );

    public List<OperatorAppraisal> countLoadAppraisalRequestsWithFilterBuyer(
            List<String> statuses,
            List<String> types,
            String phone_no,
            String kw,
            CTNDateTime from,
            CTNDateTime to,
            boolean isTestData,
            boolean isFilterFromEmailSendTime,
            boolean isBuyer,
            List<Integer> shopIds
    );

    public List<OperatorAppraisal> loadAppraisalRequestsBetweenSixMonths(String start_date , String end_date,String email,String phone,String ip);


    //public void storeAppraisalRequestFull(UserAppraisal a);
    @Insert({
            "<script>",
            "INSERT INTO appraisal_request (",
            "  appraisalid,",
            "  <if test='status != null'>status,</if>",
            "  <if test='type != null'>type,</if>",
            "  note,",
            "  message_for_matching_shop,",
            "  <if test='ip != null'>ip,</if>",
            "  <if test='param != null'>param,</if>",
            "  <if test='savedUtmParam != null'>saved_utm_param,</if>",
            "  <if test='fpc != null'>fpc,</if>",
            "  <if test='isTestData != null'>is_test_data,</if>",
            "  <if test='test_email_sent != null'>test_email_sent,</if>",
            "  <if test='customer.phone != null and customer.phone.content != null'>customer_phone,</if>",
            "  <if test='customer.name != null'>customer_name,</if>",
            "  <if test='customer.email != null and customer.email.content != null'>customer_email,</if>",
            "  <if test='customer.post_number != null'>customer_post_number,</if>",
            "  <if test='customer.prefecture != null'>customer_prefecture,</if>",
            "  <if test='customer.municipalities != null'>customer_municipalities,</if>",
            "  <if test='customer.address != null'>customer_address,</if>",
            "  <if test='customer.address_detail != null'>customer_address_detail,</if>",
            "  <if test='car.car_type != null'>car_type,</if>",
            "  <if test='car.car_maker != null'>car_maker,</if>",
            "  <if test='car.car_model_year != null'>car_model_year,</if>",
            "  <if test='car.car_traveled_distance != null'>car_traveled_distance,</if>",
            "  <if test='car.inspect_remain != null'>inspect_remain,</if>",
            "  <if test='car.car_state != null'>car_state,</if>",
            "  <if test='car.runnable != null'>runnable,</if>",
            "  <if test='car.wheel_drive != null'>wheel_drive,</if>",
            "  <if test='car.fuel != null'>fuel,</if>",
            "  <if test='car.shift != null'>shift,</if>",
            "  <if test='car.accident != null'>accident,</if>",
            "  <if test='car.displacement != null'>displacement,</if>",
            "  <if test='car.body_color != null'>body_color,</if>",
            "  <if test='car.loan != null'>loan,</if>",
            "  <if test='car.desire_date != null'>desire_date,</if>",
            "  <if test='car.grade != null'>grade,</if>",
            "  <if test='car.exterior != null'>exterior,</if>",
            "  <if test='car.scratch != null'>scratch,</if>",
            "  <if test='car.dent != null'>dent,</if>",
            "  <if test='car.peel != null'>peel,</if>",
            "  <if test='car.rust != null'>rust,</if>",
            "  <if test='car.interior != null'>interior,</if>",
            "  <if test='car.dirt != null'>dirt,</if>",
            "  <if test='car.tear != null'>tear,</if>",
            "  <if test='car.air_conditioning != null'>air_conditioning,</if>",
            "  <if test='car.smoke != null'>smoke,</if>",
            "  <if test='car.navigation != null'>navigation,</if>",
            "  <if test='car.auto_slide != null'>auto_slide,</if>",
            "  <if test='car.leather_sheet != null'>leather_sheet,</if>",
            "  <if test='car.handle_type != null'>handle_type,</if>",
            "  <if test='car.back_monitor != null'>back_monitor,</if>",
            "  <if test='car.sunroof != null'>sunroof,</if>",
            "  <if test='car.wheel != null'>wheel,</if>",
            "  assessment_date_first,",
            "  assessment_date_second,",
            "  assessment_date_third,",
            "  request_ymd,",
            "  craeted_at,",
            "  updated_at,",
            "  version",
            ") VALUES (",
            "  #{appraisalid.content},",
            "  <if test='status != null'>#{status},</if>",
            "  <if test='type != null'>#{type},</if>",
            "  '',",
            "  '',",
            "  <if test='ip != null'>#{ip},</if>",
            "  <if test='param != null'>#{param},</if>",
            "  <if test='savedUtmParam != null'>#{savedUtmParam},</if>",
            "  <if test='fpc != null'>#{fpc},</if>",
            "  <if test='isTestData != null'>#{isTestData},</if>",
            "  <if test='test_email_sent != null'>#{test_email_sent},</if>",
            "  <if test='customer.phone != null and customer.phone.content != null'>#{customer.phone.content},</if>",
            "  <if test='customer.name != null'>#{customer.name},</if>",
            "  <if test='customer.email != null and customer.email.content != null'>#{customer.email.content},</if>",
            "  <if test='customer.post_number != null'>#{customer.post_number},</if>",
            "  <if test='customer.prefecture != null'>#{customer.prefecture},</if>",
            "  <if test='customer.municipalities != null'>#{customer.municipalities},</if>",
            "  <if test='customer.address != null'>#{customer.address},</if>",
            "  <if test='customer.address_detail != null'>#{customer.address_detail},</if>",
            "  <if test='car.car_type != null'>#{car.car_type},</if>",
            "  <if test='car.car_maker != null'>#{car.car_maker},</if>",
            "  <if test='car.car_model_year != null'>#{car.car_model_year},</if>",
            "  <if test='car.car_traveled_distance != null'>#{car.car_traveled_distance},</if>",
            "  <if test='car.inspect_remain != null'>#{car.inspect_remain},</if>",
            "  <if test='car.car_state != null'>#{car.car_state},</if>",
            "  <if test='car.runnable != null'>#{car.runnable},</if>",
            "  <if test='car.wheel_drive != null'>#{car.wheel_drive},</if>",
            "  <if test='car.fuel != null'>#{car.fuel},</if>",
            "  <if test='car.shift != null'>#{car.shift},</if>",
            "  <if test='car.accident != null'>#{car.accident},</if>",
            "  <if test='car.displacement != null'>#{car.displacement},</if>",
            "  <if test='car.body_color != null'>#{car.body_color},</if>",
            "  <if test='car.loan != null'>#{car.loan},</if>",
            "  <if test='car.desire_date != null'>#{car.desire_date},</if>",
            "  <if test='car.grade != null'>#{car.grade},</if>",
            "  <if test='car.exterior != null'>#{car.exterior},</if>",
            "  <if test='car.scratch != null'>#{car.scratch},</if>",
            "  <if test='car.dent != null'>#{car.dent},</if>",
            "  <if test='car.peel != null'>#{car.peel},</if>",
            "  <if test='car.rust != null'>#{car.rust},</if>",
            "  <if test='car.interior != null'>#{car.interior},</if>",
            "  <if test='car.dirt != null'>#{car.dirt},</if>",
            "  <if test='car.tear != null'>#{car.tear},</if>",
            "  <if test='car.air_conditioning != null'>#{car.air_conditioning},</if>",
            "  <if test='car.smoke != null'>#{car.smoke},</if>",
            "  <if test='car.navigation != null'>#{car.navigation},</if>",
            "  <if test='car.auto_slide != null'>#{car.auto_slide},</if>",
            "  <if test='car.leather_sheet != null'>#{car.leather_sheet},</if>",
            "  <if test='car.handle_type != null'>#{car.handle_type},</if>",
            "  <if test='car.back_monitor != null'>#{car.back_monitor},</if>",
            "  <if test='car.sunroof != null'>#{car.sunroof},</if>",
            "  <if test='car.wheel != null'>#{car.wheel},</if>",
            "  '',",
            "  '',",
            "  '',",
            "  TO_CHAR(CURRENT_TIMESTAMP, 'YYYY/FMMM/FMDD FMHH24:MI:SS'),",
            "  CURRENT_TIMESTAMP,",
            "  CURRENT_TIMESTAMP,",
            "  0",
            ");",
            "",
            "DELETE FROM assessment_date WHERE appraisalid = #{appraisalid.content};",
            "",
            "<if test='aDates != null and aDates.hasValue()'>",
            "  INSERT INTO assessment_date (appraisalid, assessment_date)",
            "  VALUES",
            "  <foreach collection='aDates.content' item='item' separator=','>",
            "    (#{appraisalid.content}, #{item.content})",
            "  </foreach>",
            "</if>",
            "</script>"
    })



    public void storePIN(Customer pin);

    public void clearPIN(@Param("pin") PIN pin);

    public PIN loadPIN(@Param("phone") CTNPhoneNo phone);

    public List<OperatorAppraisal> loadAppraisalRequestWithShop();

    public MatchedAssessmentEmailContents loadAppraisalForSends(long id);

    public AppraisalID createAppraisalID();

    public void saveOperatorAppraisal(@Param("operatorAppraisal") OperatorAppraisal r);

    public List<AppraisalID> loadPhoneRecentRegistered(CTNPhoneNo phone, CTNDateTime f, CTNDateTime t);

    public List<AppraisalID> loadEmailRecentRegistered(CTNEmail email, CTNDateTime f, CTNDateTime t);

    public void updateAppraisalAndAssessedStatus(@Param("appraisalId") Long appraisalId, @Param("status") String status);
    public void updateAppraisalRequestStatus(@Param("appraisalId") Long appraisalId, @Param("status") String status);

}
