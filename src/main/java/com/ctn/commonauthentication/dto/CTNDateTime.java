package com.ctn.commonauthentication.dto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CTNDateTime {
    private static String DT_FORMAT = "yyyy/MM/dd H:mm:ss";

    private LocalDateTime content;

    public CTNDateTime() {
        this.content = LocalDateTime.now();
    }

    public CTNDateTime(String content) {
        if (content == null) {
            this.content = null;
        } else {
            this.content = LocalDateTime.parse(content, DateTimeFormatter.ofPattern(DT_FORMAT));
        }
    }

    public boolean filled() {
        return this.content != null;
    }

    public CTNDateTime(LocalDateTime content) {
        if (content == null) {
            this.content = null;
        }
        this.content = content;
    }

    public String toString() {
        return this.content.format(DateTimeFormatter.ofPattern(DT_FORMAT));
    }

    public String expression() {
        return this.content.format(DateTimeFormatter.ofPattern(DT_FORMAT));
    }

    public static CTNDateTime now() {
        return new CTNDateTime();
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((content == null) ? 0 : content.hashCode());
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
        CTNDateTime other = (CTNDateTime) obj;
        if (content == null) {
            if (other.content != null)
                return false;
        } else if (!content.equals(other.content))
            return false;
        return true;
    }

}
