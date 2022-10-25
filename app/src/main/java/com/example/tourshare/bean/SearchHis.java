package com.example.tourshare.bean;

import org.litepal.crud.LitePalSupport;

import java.io.Serializable;

public class SearchHis extends LitePalSupport implements Serializable {
    private long user_id;
    private String search_content;
    private long create_timestamp;

      public long getUser_id() {
            return user_id;
      }

      public void setUser_id(long user_id) {
            this.user_id = user_id;
      }

      public String getSearch_content() {
            return search_content;
      }

      public void setSearch_content(String search_content) {
            this.search_content = search_content;
      }

      public long getCreate_timestamp() {
            return create_timestamp;
      }

      public void setCreate_timestamp(long create_timestamp) {
            this.create_timestamp = create_timestamp;
      }
}
