package wyz.android.com.metroofdc.domain;

import java.util.List;

/**
 * Created by wangyuzhe on 16/5/22.
 */
public class BusStationsRoute {


    private String RouteID;
    private String Name;
    private Direction0Bean Direction0;
    private Direction1Bean Direction1;

    public String getRouteID() {
        return RouteID;
    }

    public void setRouteID(String RouteID) {
        this.RouteID = RouteID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public Direction0Bean getDirection0() {
        return Direction0;
    }

    public void setDirection0(Direction0Bean Direction0) {
        this.Direction0 = Direction0;
    }

    public Direction1Bean getDirection1() {
        return Direction1;
    }

    public void setDirection1(Direction1Bean Direction1) {
        this.Direction1 = Direction1;
    }

    public static class Direction0Bean {
        private String TripHeadsign;
        private String DirectionText;
        private String DirectionNum;
        /**
         * Lat : 39.01167
         * Lon : -76.909915
         * SeqNum : 1
         */

        private List<ShapeBean> Shape;
        /**
         * StopID : 3003037
         * Name : GREENBELT STATION + BUS BAY D
         * Lon : -76.910033
         * Lat : 39.011597
         * Routes : ["B30"]
         */

        private List<StopsBean> Stops;

        public String getTripHeadsign() {
            return TripHeadsign;
        }

        public void setTripHeadsign(String TripHeadsign) {
            this.TripHeadsign = TripHeadsign;
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

        public List<ShapeBean> getShape() {
            return Shape;
        }

        public void setShape(List<ShapeBean> Shape) {
            this.Shape = Shape;
        }

        public List<StopsBean> getStops() {
            return Stops;
        }

        public void setStops(List<StopsBean> Stops) {
            this.Stops = Stops;
        }

        public static class ShapeBean {
            private double Lat;
            private double Lon;
            private int SeqNum;

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

            public int getSeqNum() {
                return SeqNum;
            }

            public void setSeqNum(int SeqNum) {
                this.SeqNum = SeqNum;
            }
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

    public static class Direction1Bean {
        private String TripHeadsign;
        private String DirectionText;
        private String DirectionNum;
        /**
         * Lat : 39.191546
         * Lon : -76.673027
         * SeqNum : 1
         */

        private List<ShapeBean> Shape;
        /**
         * StopID : 3003039
         * Name : BWI AIRPORT + LIGHT RAIL STATION
         * Lon : -76.672943
         * Lat : 39.191527
         * Routes : ["B30"]
         */

        private List<StopsBean> Stops;

        public String getTripHeadsign() {
            return TripHeadsign;
        }

        public void setTripHeadsign(String TripHeadsign) {
            this.TripHeadsign = TripHeadsign;
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

        public List<ShapeBean> getShape() {
            return Shape;
        }

        public void setShape(List<ShapeBean> Shape) {
            this.Shape = Shape;
        }

        public List<StopsBean> getStops() {
            return Stops;
        }

        public void setStops(List<StopsBean> Stops) {
            this.Stops = Stops;
        }

        public static class ShapeBean {
            private double Lat;
            private double Lon;
            private int SeqNum;

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

            public int getSeqNum() {
                return SeqNum;
            }

            public void setSeqNum(int SeqNum) {
                this.SeqNum = SeqNum;
            }
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
}
