package com.yang.user.coolweather.utils;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.yang.user.coolweather.db.City;
import com.yang.user.coolweather.db.County;
import com.yang.user.coolweather.db.Province;
import com.yang.user.coolweather.gson.Weather;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by User on 2017/3/2.
 */

public class Utilty {
    /**
     * 解析和处理服务器返回的省级数据
     */
    public static boolean handleProvinceResponse(String response){
        if(!TextUtils.isEmpty(response)){
            try {
                JSONArray allProvinces = new JSONArray(response);
                for(int i=0;i<allProvinces.length();i++){
                    JSONObject provincesobject = allProvinces.getJSONObject(i);
                    Province province = new Province();
                    province.setProvinceName(provincesobject.getString("name"));
                    province.setProvinceCode(provincesobject.getInt("id"));
                    province.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }
    /**
     *
     *解析和处理服务器返回的市级数据
     */
    public static boolean handleCityResponse(String response,int provinceId){
        if(!TextUtils.isEmpty(response)){
            try {
                JSONArray allCity = new JSONArray(response);
                for(int i = 0 ; i< allCity.length() ; i++){
                    JSONObject cityjsonObject = allCity.getJSONObject(i);
                    City city = new City();
                    city.setCityName(cityjsonObject.getString("name"));
                    city.setCityCode(cityjsonObject.getInt("id"));
                    city.setProvinceId(provinceId);
                    city.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

        return false;
    }
    /**
     *
     *解析和处理服务器返回的县级数据
     */

    public static boolean handleCountyResponse(String response,int cityId){
        if(!TextUtils.isEmpty(response)){
            try {
                JSONArray allcountys = new JSONArray(response);
                for(int i=0;i<allcountys.length();i++){
                    JSONObject countyObject = allcountys.getJSONObject(i);
                    County county = new County();
                    county.setCountyName(countyObject.getString("name"));
                    county.setCityId(countyObject.getInt("weather_id"));
                    county.setCityId(cityId);
                    county.save();
                }
                return true;
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    /**
     * 将返回的JSON数据解析成Weather实体类
     */
    public static Weather handleWeatherResponse(String response) {
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray jsonArray = jsonObject.getJSONArray("HeWeather");
            String weatherContent = jsonArray.getJSONObject(0).toString();
            return new Gson().fromJson(weatherContent, Weather.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
