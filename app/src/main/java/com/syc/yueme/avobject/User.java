package com.syc.yueme.avobject;

import android.util.Log;

import com.avos.avoscloud.AVFile;
import com.avos.avoscloud.AVInstallation;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.AVRelation;
import com.avos.avoscloud.AVUser;
import com.syc.yueme.R;
import com.syc.yueme.base.App;

import java.util.Date;

public class User extends AVUser{
    public static enum  relationMode_user {ADD,REMOVE}
    public static final String USERNAME = "username";
    public static final String PASSWORD = "password";
    public static final String AVATAR = "avatar";
    public static final String SIGN = "sign";
    public static final String NICKNAME = "nickname";
    public static final String LOCATION = "location";
    public static final String GENDER = "gender";
    public static final String INSTALLATION = "installation";
    public static final String SCHOOL = "school";
    public static final String STUID = "stuId";
    public static final String BIRTHDAY = "birthday";
    public static final String PEOPLES = "peoples";
    public static final String HOMETOWN = "hometown";
    public static final String CHARACRISTICS = "characristics";
    public static final String HOBBIES = "hobbies";
    public static final String SPECIALITY = "speciality";
    public static final String IGNMESG = "ignMesg";
    public static final String LIKEMESG = "likeMesg";
    public static final String SENDMESG = "sendMesg";
    public static final String TRYYUEMESG = "tryYueMesg";
    public static final String YUESUCCESSMESG = "yueSuccessMesg";
    public static final String LIKEUSER = "likeUser";
    public static final String HATEUSER = "hateUser";
    public static final String FRIENDS = "friends";
    public static final String EMAIL = "email";
    public static final String EMAILVERIFIED = "emailVerified";
    public static final String MOBILEPHONENUMBER = "mobilePhoneNumber";
    public static final String MOBILEPHONEVERIFIED = "mobilePhoneVerified";
    public static final String CREATEAT_USER = "createAt";
    public static final String UPDATEDAT_USER = "updatedAt";
    public static final String YPA = "YPA";

    public static String[] genderStrings = new String[]{App.ctx.getString(R.string.male),
            App.ctx.getString(R.string.female)};

    public static String getCurrentUserId() {
        AVUser user = AVUser.getCurrentUser();
        if (user != null) {
            return user.getObjectId();
        } else {
            return null;
        }
    }

    public static enum Gender {
        Male(0), Female(1);

        int value;

        Gender(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }

        public static Gender fromInt(int index) {
            return values()[index];
        }
    }

    public static String getAvatarUrl(AVUser user) {
        AVFile avatar = user.getAVFile(AVATAR);
        if (avatar != null) {
            return avatar.getUrl();
        } else {
            return null;
        }
    }

    public static Gender getGender(AVUser user) {
        int genderInt = user.getInt(GENDER);
        return Gender.fromInt(genderInt);
    }

    public static void setGender(AVUser user, Gender gender) {
        user.put(GENDER, gender.getValue());
    }

    public static String getGenderDesc(AVUser user) {
        Gender gender = getGender(user);
        return genderStrings[gender.getValue()];
    }

    public boolean getEmailverified()
    {
        return this.getBoolean(EMAILVERIFIED);
    }

    public boolean getMobilePhoneVerified()
    {
        return this.getBoolean(MOBILEPHONEVERIFIED);
    }

    public void setNickname(String nickname)
    {
        this.put(NICKNAME, nickname);
    }

    public String getNickname()
    {
        return this.getString(NICKNAME);
    }

    public void setSign(String sign)
    {
        this.put(SIGN, sign);
    }

    public String getSign()
    {
        return this.getString(SIGN);
    }

    public void setMobilephonenumber(String mobilePhoneNumber)
    {
        this.put(MOBILEPHONENUMBER, mobilePhoneNumber);
    }

    public String getMobilephonenumber()
    {

        return this.getString(MOBILEPHONENUMBER);
    }

    public void setYPA(int ypa)
    {
        this.put(YPA, ypa);
    }

    public int getYPA()
    {
        int ypa = this.getInt(YPA);
        return ypa;
    }

    public void setSchool(String school)
    {
        this.put(SCHOOL,school);
    }

    public String getSchool()
    {
        String school = this.getString(SCHOOL);
        return school;
    }

    public static void setSchool(AVUser user, String school)
    {
        user.put(SCHOOL,school);
    }

    public static String getSchool(AVUser user)
    {
        String school = user.getString(SCHOOL);
        return school;
    }

    public void setStuId(String stuId)
    {
        this.put(STUID,stuId);
    }

    public String getStuId()
    {
        String school = this.getString(STUID);
        return school;
    }

    public static void setStuId(AVUser user, String stuId)
    {
        user.put(STUID,stuId);
    }

    public static String getStuId(AVUser user)
    {
        String stuId = user.getString(STUID);
        return stuId;
    }




    public static AVInstallation getInstallation(AVUser user) {
        try {
            return user.getAVObject(INSTALLATION, AVInstallation.class);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    public void setPeoples(String peoples)
    {
        this.put(PEOPLES,peoples);
    }
    public String getPeoples() {
        return this.getString(PEOPLES);
    }

    public void setHometown(String hometown)
    {
        this.put(HOMETOWN,hometown);
    }

    public String getHometown() {
        return this.getString(HOMETOWN);
    }

    public void setCharacristics(String characristics)
    {
        this.put(CHARACRISTICS,characristics);
    }

    public String getCharacristics() {
        return this.getString(CHARACRISTICS);
    }

    public void setHobbies(String hobbies)
    {
        this.put(HOBBIES,hobbies);
    }

    public String getHobbies() {
        return this.getString(HOBBIES);
    }

    public void setSpeciality(String speciality)
    {
        this.put(SPECIALITY,speciality);
    }

    public String getSpeciality() {
        return this.getString(SPECIALITY);
    }

    public String getBirthday() {
        return this.getString(BIRTHDAY);
    }

    public void setBirthday(String birthday)
    {
        this.put(BIRTHDAY,birthday);
    }

    public void changeIgnMesg(AVObject ignMesg, relationMode_user mode)
    {
        if (this != null) Log.d("获得当前USER", this.getUsername());
        AVRelation<AVObject> relation = this.getRelation(IGNMESG);
        if (mode == relationMode_user.ADD)
        {
            relation.add(ignMesg);
        }
        else if (mode == relationMode_user.REMOVE)
        {
            relation.remove(ignMesg);
        }
    }

    public void changeLikeMesg(AVObject likeMesg, relationMode_user mode)
    {
        if (this != null) Log.d("获得当前USER", this.getUsername());
        AVRelation<AVObject> relation = this.getRelation(LIKEMESG);
        if (mode == relationMode_user.ADD)
        {
            relation.add(likeMesg);
        }
        else if (mode == relationMode_user.REMOVE)
        {
            relation.remove(likeMesg);
        }
    }

    public void changeTryYueMesg(AVObject tryYueMesg, relationMode_user mode)
    {
        if (this != null) Log.d("获得当前USER", this.getUsername());
        AVRelation<AVObject> relation = this.getRelation(TRYYUEMESG);
        if (mode == relationMode_user.ADD)
        {
            relation.add(tryYueMesg);
        }
        else if (mode == relationMode_user.REMOVE)
        {
            relation.remove(tryYueMesg);
        }
    }

    public void changeSendMesg(AVObject sendMesg, relationMode_user mode)
    {
        if (this != null) Log.d("获得当前USER", this.getUsername());
        AVRelation<AVObject> relation = this.getRelation(SENDMESG);
        if (mode == relationMode_user.ADD)
        {
            relation.add(sendMesg);
        }
        else if (mode == relationMode_user.REMOVE)
        {
            relation.remove(sendMesg);
        }
    }

    public void changeYueSuccessMesg(AVObject YueSuccessMesg, relationMode_user mode)
    {
        if (this != null) Log.d("获得当前USER", this.getUsername());
        AVRelation<AVObject> relation = this.getRelation(YUESUCCESSMESG);
        if (mode == relationMode_user.ADD)
        {
            relation.add(YueSuccessMesg);
        }
        else if (mode == relationMode_user.REMOVE)
        {
            relation.remove(YueSuccessMesg);
        }
    }

    public void changeLikeUser(AVUser likeUser, relationMode_user mode)
    {
        if (this != null) Log.d("获得当前USER", this.getUsername());
        AVRelation<AVObject> relation = this.getRelation(LIKEUSER);
        if (mode == relationMode_user.ADD)
        {
            relation.add(likeUser);
        }
        else if (mode == relationMode_user.REMOVE)
        {
            relation.remove(likeUser);
        }
    }

    public void changeHateUser(AVUser hateUser, relationMode_user mode)
    {
        if (this != null) Log.d("获得当前USER", this.getUsername());
        AVRelation<AVObject> relation = this.getRelation(HATEUSER);
        if (mode == relationMode_user.ADD)
        {
            relation.add(hateUser);
        }
        else if (mode == relationMode_user.REMOVE)
        {
            relation.remove(hateUser);
        }
    }

    public void changeFriends(AVUser friends, relationMode_user mode)
    {
        if (this != null) Log.d("获得当前USER", this.getUsername());
        AVRelation<AVObject> relation = this.getRelation(FRIENDS);
        if (mode == relationMode_user.ADD)
        {
            relation.add(friends);
        }
        else if (mode == relationMode_user.REMOVE)
        {
            relation.remove(friends);
        }
    }







}
