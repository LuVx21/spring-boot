package org.luvx.resteasy.entity;

import lombok.Data;

import javax.ws.rs.FormParam;

import org.jboss.resteasy.annotations.providers.multipart.PartType;

@Data
public class DiskFile {
    @FormParam("fileName")
    private String fileName;
    @FormParam("selectedFile")
    @PartType("application/octet-stream")
    private byte[] fileDate;
}
