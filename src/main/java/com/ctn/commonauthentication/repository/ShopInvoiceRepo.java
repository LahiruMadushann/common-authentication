package com.ctn.commonauthentication.repository;

import com.ctn.commonauthentication.entity.ShopInvoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShopInvoiceRepo extends JpaRepository<ShopInvoice, Integer> {

    @Query("SELECT DISTINCT u.userId, u.userName, sh.id, sh.companyName, a.appraisalid, a.customerEmail, a.customerName, " +
            "a.customerPostNumber, a.customerPrefecture, a.customerMunicipalities, a.customerAddress, a.customerAddressDetail, " +
            "a.customerPhone, a.createdAt, a.carType, a.carMaker, a.carModelYear, sh.name, sh.stop, sh.cancel, sh.cancellationCategory, " +
            "sh.appealStatement, sh.businessHours, sh.shopTypeEnum, sh.holidayMatching, sh.paymentMethod, sh.refferal, sh.introduction, sh.phoneNumber, sh.shopImageUrl, " +
            "sh.postalCode, sh.prefectures, sh.municipalities, sh.address, " +
            "sv.id, sv.startDate, sv.endDate, " +
            "shh.id, shh.week, shh.Day, ass.value, ass.finalOffer " +
            "FROM AppraisalRequestInformation a " +
            "JOIN Assessed ass ON a.appraisalid = ass.id.appraisalid " +
            "JOIN ShopInvoice sh ON sh.id = ass.id.shopid " +
            "JOIN UserInvoice u ON u.userId = sh.userInvoice.userId " +
            "LEFT JOIN ShopVacation sv ON sv.shopId = sh.id " +
            "LEFT JOIN ShopHoliday shh ON shh.shopId = sh.id " +
            "WHERE a.appraisalid IN :appraisalIds " +
            "AND ass.email_sent_time IS NOT NULL " +
            "ORDER BY a.createdAt DESC")
    List<Object[]> findDetailsByUserId(@Param("appraisalIds") List<Long> appraisalIds);

    @Query(value = "SELECT shopid from shops where user_id = :user_id", nativeQuery = true)
    List<Long> getShopIdsByUserID(@Param("user_id") Long userId);

    @Query(value = "SELECT\n" +
            "    s.shopid as shopid,  -- 0\n" +
            "    s.company_name as company_name,  -- 1\n" +
            "    s.name as name,  -- 2\n" +
            "    s.stopped as stopped,  -- 3\n" +
            "    s.cancelled as cancelled,  -- 4\n" +
            "    s.shop_type as shop_type,  -- 5\n" +
            "    s.refferal as refferal,  -- 6\n" +
            "    s.introduction as introduction,  -- 7\n" +
            "    s.user_id as user_id,  -- 8\n" +
            "    s.phone_number as phone_number,  -- 9\n" +
            "    s.shop_image_url as \"shop_image_url\",  -- 10\n" +
            "    general.id as general_id,  -- 11\n" +
            "    special.special_condition_id as special_condition_id,  -- 12\n" +
            "    general.exclusivity as general_exclusivity,  -- 13\n" +
            "    special.exclusivity as special_exclusivity,  -- 14\n" +
            "    bm.id as branch_map_id,  -- 15\n" +
            "    bm.branch_id as branch_id,  -- 16\n" +
            "    bm.head_branch_id as head_branch_id,  -- 17\n" +
            "    s.shopid as shopid2,  -- 18\n" +
            "    s.postal_code as pcode,  -- 19\n" +
            "    s.prefectures as pref,  -- 20\n" +
            "    s.municipalities as muni,  -- 21\n" +
            "    s.address as address,  -- 22\n" +
            "    bd.id as billing_data_id,  -- 23\n" +
            "    bd.billing_address_zip_code as billing_address_zip_code,  -- 24\n" +
            "    bd.billing_address as billing_address,  -- 25\n" +
            "    bd.billing_prefecture as billing_address_prefecture,  -- 26\n" +
            "    bd.billing_address_municipalities as billing_address_municipalities,  -- 27\n" +
            "    bd.billing_department as billing_department,  -- 28\n" +
            "    bd.billing_person_in_charge as billing_person_in_charge,  -- 29\n" +
            "    vacation.id as vacation_id,  -- 30\n" +
            "    vacation.start_date as vacation_start_date,  -- 31\n" +
            "    vacation.end_date as vacation_end_date,  -- 32\n" +
            "    holidays.id as holidays_id,  -- 33\n" +
            "    holidays.day as holidays_day,  -- 34\n" +
            "    holidays.week as holidays_week,  -- 35\n" +
            "    s.holiday_matching as holiday_matching,  -- 36\n" +
            "    s.payment_method as payment_method,  -- 37\n" +
            "    be.email as billing_email, -- 38\n" +
            "    be.id as billing_email_id, --39\n" +
            "    users.user_id as username, --40\n" +
            "    s.cancellation_category as cancellation_category, --41\n" +
            "    s.appeal_statement as appeal_statement, --42\n" +
            "    s.business_hours as business_hours, --43\n" +
            "    s.schedule_date as scheduleDate --44\n" +
            "     " +
            "FROM shops s\n" +
            "         LEFT JOIN shop_handling_condition_general general ON general.shopid = s.shopid\n" +
            "         LEFT JOIN shop_handling_condition_special special ON special.shopid = s.shopid\n" +
            "         LEFT JOIN branch_map bm ON bm.branch_id = s.shopid\n" +
            "         LEFT JOIN billing_data bd ON bd.shopid = s.shopid\n" +
            "         LEFT JOIN shop_vacation vacation ON vacation.shopid = s.shopid\n" +
            "         LEFT JOIN shop_holidays holidays ON holidays.shopid = s.shopid\n" +
            "         LEFT JOIN billing_emails be on be.shopid = s.shopid\n" +
            "         LEFT JOIN users users on users.id = s.user_id\n" +
            "WHERE s.shopid = :id", nativeQuery = true)
    List<Object> getShopDetails(@Param("id") int id);

    @Query("SELECT s.userInvoice.userId FROM ShopInvoice s WHERE s.id = :shopId")
    Integer findUserInvoiceById(@Param("shopId") Integer shopId);

}
