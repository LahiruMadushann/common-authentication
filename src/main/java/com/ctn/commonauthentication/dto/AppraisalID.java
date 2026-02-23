package com.ctn.commonauthentication.dto;

import java.time.LocalDate;
import java.util.Random;

public class AppraisalID {
    private long content;

    public static long newID() {
        return Long.parseLong(
                String.format(
                        "%4d%02d%02d%05d",
                        LocalDate.now().getYear(),
                        LocalDate.now().getMonth().getValue(),
                        LocalDate.now().getDayOfMonth(),
                        new Random().nextInt(99999)
                )
        );
    }

    public AppraisalID(long content) {
        this.content = content;
    }

    public AppraisalID(String cf7_counter) {
        if (cf7_counter == null) {
            this.content = newID();
            return;
        }
        if (cf7_counter == "") {
            this.content = newID();
            return;
        }
        this.content = Long.parseLong(cf7_counter);
    }

    public AppraisalID() {
        this.content = -1;
    }

    public boolean filled() {
        return this.content != -1;
    }

    public boolean equals(AppraisalID other) {
        return this.content == other.content;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + (int) (content ^ (content >>> 32));
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AppraisalID other = (AppraisalID) obj;
        if (content != other.content)
            return false;
        return true;
    }

    public long getContent() {
        return content;
    }

    public void setContent(long content) {
        this.content = content;
    }
}
