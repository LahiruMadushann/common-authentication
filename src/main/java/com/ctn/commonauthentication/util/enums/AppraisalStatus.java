package com.ctn.commonauthentication.util.enums;

public enum AppraisalStatus{

    /*
     * MANAGEMENT_STATUS AND BUYER_STATUS
     */
    unsupported,
    user_input,
    not_compatible, //same as unsupported
    processing,
    appointment,
    closed,
    unsent,
    deleted, //same as cancelled
    submit_an_application, //submit an application
    recognition_applied, //apply for recognition
    recognition_not_accepted, // application is not accepted by the applicant
    cancelled,
    scheduled,

    /*
     * BUYER_STATUS ONLY
     */

    unconnected, //manual
    no_connected_assessments, //manual
    assessment_reservation, //manual
    assessed, //manual
    unexecuted, //manual
    contracted,
    user_complaint,
    duplication_of_media,
    sale_of_other_companies


}
