package wyz.android.com.metroofdc.domain;

import java.util.List;

/**
 * Created by wangyuzhe on 16/6/24.
 */
public class BusStop {

    /**
     * Lat : 38.878356
     * Lon : -76.990378
     * Name : K ST + POTOMAC AVE
     * Routes : ["V7","V7c","V7cv1","V7v1","V7v2","V8","V9"]
     * StopID : 1000533
     */

    private List<StopsBean> Stops;

    public List<StopsBean> getStops() {
        return Stops;
    }

    public void setStops(List<StopsBean> Stops) {
        this.Stops = Stops;
    }

    public static class StopsBean {
        private double Lat;
        private double Lon;
        private String Name;
        private String StopID;
        private List<String> Routes;

        public double getLat() {
            return Lat;
        }

        public void setLat(double Lat) {
            this.Lat = Lat;
        }

        public double getLon() {
            return Lon;
        }

        public void setLon(double Lon) {
            this.Lon = Lon;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getStopID() {
            return StopID;
        }

        public void setStopID(String StopID) {
            this.StopID = StopID;
        }

        public List<String> getRoutes() {
            return Routes;
        }

        public void setRoutes(List<String> Routes) {
            this.Routes = Routes;
        }
    }
}
