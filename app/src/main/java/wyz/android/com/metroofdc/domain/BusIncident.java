package wyz.android.com.metroofdc.domain;

import java.util.List;

/**
 * Created by wangyuzhe on 16/5/30.
 */
public class BusIncident {

    /**
     * DateUpdated : 2014-10-28T08:13:03
     * Description : 90, 92, X1, X2, X9: Due to traffic congestion at 8th & H St NE, buses are experiencing up to 20 minute delays in both directions.
     * IncidentID : 32297013-57B6-467F-BC6B-93DFA4115652
     * IncidentType : Delay
     * RoutesAffected : ["90","92","X1","X2","X9"]
     */

    private List<BusIncidentsBean> BusIncidents;

    public List<BusIncidentsBean> getBusIncidents() {
        return BusIncidents;
    }

    public void setBusIncidents(List<BusIncidentsBean> BusIncidents) {
        this.BusIncidents = BusIncidents;
    }

    public static class BusIncidentsBean {
        private String DateUpdated;
        private String Description;
        private String IncidentID;
        private String IncidentType;
        private List<String> RoutesAffected;

        public String getDateUpdated() {
            return DateUpdated;
        }

        public void setDateUpdated(String DateUpdated) {
            this.DateUpdated = DateUpdated;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String Description) {
            this.Description = Description;
        }

        public String getIncidentID() {
            return IncidentID;
        }

        public void setIncidentID(String IncidentID) {
            this.IncidentID = IncidentID;
        }

        public String getIncidentType() {
            return IncidentType;
        }

        public void setIncidentType(String IncidentType) {
            this.IncidentType = IncidentType;
        }

        public List<String> getRoutesAffected() {
            return RoutesAffected;
        }

        public void setRoutesAffected(List<String> RoutesAffected) {
            this.RoutesAffected = RoutesAffected;
        }
    }
}
