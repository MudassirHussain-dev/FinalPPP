package com.example.myapplication.model;

public class Post_Codes_Getter_Setter {
    String Post_codes, POst_Office, Province, postcity,Related_Gpo;

    public String getRelated_Gpo() {
        return Related_Gpo;
    }

    public void setRelated_Gpo(String related_Gpo) {
        Related_Gpo = related_Gpo;
    }

    public String getPostcity() { return postcity;
    }

    public void setPostcity(String postcity) {
        this.postcity = postcity;
    }

    public String getPost_codes() {
        return Post_codes;
    }

    public void setPost_codes(String post_codes) {
        Post_codes = post_codes;
    }

    public String getPOst_Office() {
        return POst_Office;
    }

    public void setPOst_Office(String POst_Office) {
        this.POst_Office = POst_Office;
    }

    public String getProvince() {
        return Province;
    }

    public void setProvince(String province) {
        Province = province;
    }
}
