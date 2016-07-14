package wyz.android.com.metroofdc.domain;

import java.util.List;

/**
 * Created by wangyuzhe on 16/6/18.
 */
public class BusNearby {

    /**
     * StopID : 1000533
     * Name : K ST SE + 12TH ST SE
     * Lon : -76.990377
     * Lat : 38.878355
     * Routes : ["V1","V4"]
     */

    private List<StopsBean> Stops;

    public List<StopsBean> getStops() {
        return Stops;
    }

    public void setStops(List<StopsBean> Stops) {
        this.Stops = Stops;
    }

    public static class StopsBean {
        private String StopID;
        private String Name;
        private double Lon;
        private double Lat;
        private List<String> Routes;

        public String getStopID() {
            return StopID;
        }

        public void setStopID(String StopID) {
            this.StopID = StopID;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public double getLon() {
            return Lon;
        }

        public void setLon(double Lon) {
            this.Lon = Lon;
        }

        public double getLat() {
            return Lat;
        }

        public void setLat(double Lat) {
            this.Lat = Lat;
        }

        public List<String> getRoutes() {
            return Routes;
        }

        public void setRoutes(List<String> Routes) {
            this.Routes = Routes;
        }
    }
}
