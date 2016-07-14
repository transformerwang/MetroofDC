package wyz.android.com.metroofdc.domain;

import java.util.List;

/**
 * Created by wangyuzhe on 16/6/18.
 */
public class TrainNearby {

    /**
     * ID : 84
     * Name : EAST ENTRANCE (EAST OF 17th & I STs)
     * StationCode1 : C03
     * StationCode2 :
     * Description : Building entrance from the southeast corner of 17th St NW and I St NW(Escalator only).
     * Lat : 38.901169
     * Lon : -77.039164
     */

    private List<EntrancesBean> Entrances;

    public List<EntrancesBean> getEntrances() {
        return Entrances;
    }

    public void setEntrances(List<EntrancesBean> Entrances) {
        this.Entrances = Entrances;
    }

    public static class EntrancesBean {
        private String ID;
        private String Name;
        private String StationCode1;
        private String StationCode2;
        private String Description;
        private double Lat;
        private double Lon;

        public String getID() {
            return ID;
        }

        public void setID(String ID) {
            this.ID = ID;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getStationCode1() {
            return StationCode1;
        }

        public void setStationCode1(String StationCode1) {
            this.StationCode1 = StationCode1;
        }

        public String getStationCode2() {
            return StationCode2;
        }

        public void setStationCode2(String StationCode2) {
            this.StationCode2 = StationCode2;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String Description) {
            this.Description = Description;
        }

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
    }
}
