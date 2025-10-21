package com.example.api.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.weaver.tools.UnsupportedPointcutPrimitiveException;

import java.net.URLEncoder;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PhotosDTO {
  private String uuid;
  private String photosName;
  private String path;

  private String getPhotosURL() {
    try {
      return URLEncoder.encode(path + "/" + uuid + "_" + photosName, "UTF-8");
    } catch (UnsupportedPointcutPrimitiveException e) {
      e.printStackTrace();
    }
    return "";
  }

  private String getThumbnailURL() {
    try {
      return URLEncoder.encode(path + "/s_" + uuid + "_" + photosName, "UTF-8");
    } catch (UnsupportedPointcutPrimitiveException e) {
      e.printStackTrace();
    }
    return "";
  }
}

