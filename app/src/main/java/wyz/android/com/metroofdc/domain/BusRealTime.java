package wyz.android.com.metroofdc.domain;

import java.util.List;

/**
 * Created by wangyuzhe on 16/5/22.
 */
public class BusRealTime {

    /**
     * StopName : 7th St + Massachusetts Ave
     * Predictions : [{"RouteID":"74","DirectionText":"North to Convention Center","DirectionNum":"0","Minutes":6,"VehicleID":"3002","TripID":"8683912"},{"RouteID":"70","DirectionText":"North to Silver Spring Station","DirectionNum":"0","Minutes":34,"VehicleID":"7214","TripID":"8683169"},{"RouteID":"70","DirectionText":"North to Silver Spring Station","DirectionNum":"0","Minutes":42,"VehicleID":"6503","TripID":"8683168"}]
     */

    private String StopName;
    /**
     * RouteID : 74
     * DirectionText : North to Convention Center
     * DirectionNum : 0
     * Minutes : 6
     * VehicleID : 3002
     * TripID : 8683912
     */

    private List<PredictionsBean> Predictions;

    public String getStopName() {
        return StopName;
    }

    public void setStopName(String StopName) {
        this.StopName = StopName;
    }

    public List<PredictionsBean> getPredictions() {
        return Predictions;
    }

    public void setPredictions(List<PredictionsBean> Predictions) {
        this.Predictions = Predictions;
    }

    public static class PredictionsBean {
        private String RouteID;
        private String DirectionText;
        private String DirectionNum;
        private int Minutes;
        private String VehicleID;
        private String TripID;

        public String getRouteID() {
            return RouteID;
        }

        public void setRouteID(String RouteID) {
            this.RouteID = RouteID;
        }

        public String getDirectionText() {
            return DirectionText;
        }

        public void setDirectionText(String DirectionText) {
            this.DirectionText = DirectionText;
        }

        public String getDirectionNum() {
            return DirectionNum;
        }

        public void setDirectionNum(String DirectionNum) {
            this.DirectionNum = DirectionNum;
        }

        public int getMinutes() {
            return Minutes;
        }

        public void setMinutes(int Minutes) {
            this.Minutes = Minutes;
        }

        public String getVehicleID() {
            return VehicleID;
        }

        public void setVehicleID(String VehicleID) {
            this.VehicleID = VehicleID;
        }

        public String getTripID() {
            return TripID;
        }

        public void setTripID(String TripID) {
            this.TripID = TripID;
        }
    }
}
