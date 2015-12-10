package com.yx.android.copyuc.bean;

import java.util.List;

/**
 * Created by yx on 2015/12/9 0009.
 */
public class WeatherInfoDto {


    /**
     * error : 0
     * status : success
     * date : 2014-04-18
     * results : [{"currentCity":"嘉兴","weather_data":[{"date":"今天（周三）","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/duoyun.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/duoyun.png","weather":"多云","wind":"微风","temperature":"23℃"},{"date":"明天（周四）","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/leizhenyu.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/zhongyu.png","weather":"雷阵雨转中雨","wind":"微风","temperature":"29～22℃"},{"date":"后天（周五）","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/yin.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/duoyun.png","weather":"阴转多云","wind":"微风","temperature":"31～23℃"},{"date":"大后天（周六）","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/duoyun.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/duoyun.png","weather":"多云","wind":"微风","temperature":"31～24℃"}]}]
     */

    private int error;
    private String status;
    private String date;
    /**
     * currentCity : 嘉兴
     * weather_data : [{"date":"今天（周三）","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/duoyun.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/duoyun.png","weather":"多云","wind":"微风","temperature":"23℃"},{"date":"明天（周四）","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/leizhenyu.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/zhongyu.png","weather":"雷阵雨转中雨","wind":"微风","temperature":"29～22℃"},{"date":"后天（周五）","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/yin.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/duoyun.png","weather":"阴转多云","wind":"微风","temperature":"31～23℃"},{"date":"大后天（周六）","dayPictureUrl":"http://api.map.baidu.com/images/weather/day/duoyun.png","nightPictureUrl":"http://api.map.baidu.com/images/weather/night/duoyun.png","weather":"多云","wind":"微风","temperature":"31～24℃"}]
     */

    private List<WeatherInfo> results;

    public void setError(int error) {
        this.error = error;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setResults(List<WeatherInfo> results) {
        this.results = results;
    }

    public int getError() {
        return error;
    }

    public String getStatus() {
        return status;
    }

    public String getDate() {
        return date;
    }

    public List<WeatherInfo> getResults() {
        return results;
    }

    public static class WeatherInfo {
        private String currentCity;

        public String getPm25() {
            return pm25;
        }

        public void setPm25(String pm25) {
            this.pm25 = pm25;
        }

        private String pm25;
        /**
         * date : 今天（周三）
         * dayPictureUrl : http://api.map.baidu.com/images/weather/day/duoyun.png
         * nightPictureUrl : http://api.map.baidu.com/images/weather/night/duoyun.png
         * weather : 多云
         * wind : 微风
         * temperature : 23℃
         */

        private List<WeatherDataEntity> weather_data;

        public void setCurrentCity(String currentCity) {
            this.currentCity = currentCity;
        }

        public void setWeather_data(List<WeatherDataEntity> weather_data) {
            this.weather_data = weather_data;
        }

        public String getCurrentCity() {
            return currentCity;
        }

        public List<WeatherDataEntity> getWeather_data() {
            return weather_data;
        }

        public static class WeatherDataEntity {
            private String date;
            private String dayPictureUrl;
            private String nightPictureUrl;
            private String weather;
            private String wind;
            private String temperature;

            public void setDate(String date) {
                this.date = date;
            }

            public void setDayPictureUrl(String dayPictureUrl) {
                this.dayPictureUrl = dayPictureUrl;
            }

            public void setNightPictureUrl(String nightPictureUrl) {
                this.nightPictureUrl = nightPictureUrl;
            }

            public void setWeather(String weather) {
                this.weather = weather;
            }

            public void setWind(String wind) {
                this.wind = wind;
            }

            public void setTemperature(String temperature) {
                this.temperature = temperature;
            }

            public String getDate() {
                return date;
            }

            public String getDayPictureUrl() {
                return dayPictureUrl;
            }

            public String getNightPictureUrl() {
                return nightPictureUrl;
            }

            public String getWeather() {
                return weather;
            }

            public String getWind() {
                return wind;
            }

            public String getTemperature() {
                return temperature;
            }
        }
    }
}
