package wyz.android.com.metroofdc.domain;

import java.util.List;

/**
 * Created by wangyuzhe on 16/5/16.
 */
public class TrainStationInfo {

    /**
     * Car : 6
     * Destination : Grsvnor
     * DestinationCode : A11
     * DestinationName : Grosvenor-Strathmore
     * Group : 2
     * Line : RD
     * LocationCode : B03
     * LocationName : Union Station
     * Min : 2
     */

    private List<TrainsBean> Trains;

    public List<TrainsBean> getTrains() {
        return Trains;
    }

    public void setTrains(List<TrainsBean> Trains) {
        this.Trains = Trains;
    }

    public static class TrainsBean {
        private String Car;
        private String Destination;
        private String DestinationCode;
        private String DestinationName;
        private String Group;
        private String Line;
        private String LocationCode;
        private String LocationName;
        private String Min;

        public String getCar() {
            return Car;
        }

        public void setCar(String Car) {
            this.Car = Car;
        }

        public String getDestination() {
            return Destination;
        }

        public void setDestination(String Destination) {
            this.Destination = Destination;
        }

        public String getDestinationCode() {
            return DestinationCode;
        }

        public void setDestinationCode(String DestinationCode) {
            this.DestinationCode = DestinationCode;
        }

        public String getDestinationName() {
            return DestinationName;
        }

        public void setDestinationName(String DestinationName) {
            this.DestinationName = DestinationName;
        }

        public String getGroup() {
            return Group;
        }

        public void setGroup(String Group) {
            this.Group = Group;
        }

        public String getLine() {
            return Line;
        }

        public void setLine(String Line) {
            this.Line = Line;
        }

        public String getLocationCode() {
            return LocationCode;
        }

        public void setLocationCode(String LocationCode) {
            this.LocationCode = LocationCode;
        }

        public String getLocationName() {
            return LocationName;
        }

        public void setLocationName(String LocationName) {
            this.LocationName = LocationName;
        }

        public String getMin() {
            return Min;
        }

        public void setMin(String Min) {
            this.Min = Min;
        }
    }
}
