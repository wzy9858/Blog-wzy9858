package com.wzy.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName visitors
 */
@TableName(value ="visitors")
@Data
public class Visitors implements Serializable {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer visitorId;

    /**
     * 
     */
    private String ipAddress;

    /**
     * 
     */
    private String geoLocation;

    /**
     * 
     */
    private String userAgent;

    /**
     * 
     */
    private String deviceInfo;

    /**
     * 
     */
    private Date firstVisitTime;

    /**
     * 
     */
    private Date lastVisitTime;

    /**
     * 
     */
    private Integer visitDuration;

    /**
     * 
     */
    private Integer visitFrequency;

    /**
     * 
     */
    private String visitorNotes;

    /**
     * 
     */
    private Integer isBlacklisted;

    @TableField(exist = false)
    private static final long serialVersionUID = 1L;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Visitors other = (Visitors) that;
        return (this.getVisitorId() == null ? other.getVisitorId() == null : this.getVisitorId().equals(other.getVisitorId()))
            && (this.getIpAddress() == null ? other.getIpAddress() == null : this.getIpAddress().equals(other.getIpAddress()))
            && (this.getGeoLocation() == null ? other.getGeoLocation() == null : this.getGeoLocation().equals(other.getGeoLocation()))
            && (this.getUserAgent() == null ? other.getUserAgent() == null : this.getUserAgent().equals(other.getUserAgent()))
            && (this.getDeviceInfo() == null ? other.getDeviceInfo() == null : this.getDeviceInfo().equals(other.getDeviceInfo()))
            && (this.getFirstVisitTime() == null ? other.getFirstVisitTime() == null : this.getFirstVisitTime().equals(other.getFirstVisitTime()))
            && (this.getLastVisitTime() == null ? other.getLastVisitTime() == null : this.getLastVisitTime().equals(other.getLastVisitTime()))
            && (this.getVisitDuration() == null ? other.getVisitDuration() == null : this.getVisitDuration().equals(other.getVisitDuration()))
            && (this.getVisitFrequency() == null ? other.getVisitFrequency() == null : this.getVisitFrequency().equals(other.getVisitFrequency()))
            && (this.getVisitorNotes() == null ? other.getVisitorNotes() == null : this.getVisitorNotes().equals(other.getVisitorNotes()))
            && (this.getIsBlacklisted() == null ? other.getIsBlacklisted() == null : this.getIsBlacklisted().equals(other.getIsBlacklisted()));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getVisitorId() == null) ? 0 : getVisitorId().hashCode());
        result = prime * result + ((getIpAddress() == null) ? 0 : getIpAddress().hashCode());
        result = prime * result + ((getGeoLocation() == null) ? 0 : getGeoLocation().hashCode());
        result = prime * result + ((getUserAgent() == null) ? 0 : getUserAgent().hashCode());
        result = prime * result + ((getDeviceInfo() == null) ? 0 : getDeviceInfo().hashCode());
        result = prime * result + ((getFirstVisitTime() == null) ? 0 : getFirstVisitTime().hashCode());
        result = prime * result + ((getLastVisitTime() == null) ? 0 : getLastVisitTime().hashCode());
        result = prime * result + ((getVisitDuration() == null) ? 0 : getVisitDuration().hashCode());
        result = prime * result + ((getVisitFrequency() == null) ? 0 : getVisitFrequency().hashCode());
        result = prime * result + ((getVisitorNotes() == null) ? 0 : getVisitorNotes().hashCode());
        result = prime * result + ((getIsBlacklisted() == null) ? 0 : getIsBlacklisted().hashCode());
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", visitorId=").append(visitorId);
        sb.append(", ipAddress=").append(ipAddress);
        sb.append(", geoLocation=").append(geoLocation);
        sb.append(", userAgent=").append(userAgent);
        sb.append(", deviceInfo=").append(deviceInfo);
        sb.append(", firstVisitTime=").append(firstVisitTime);
        sb.append(", lastVisitTime=").append(lastVisitTime);
        sb.append(", visitDuration=").append(visitDuration);
        sb.append(", visitFrequency=").append(visitFrequency);
        sb.append(", visitorNotes=").append(visitorNotes);
        sb.append(", isBlacklisted=").append(isBlacklisted);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}