package wyz.android.com.metroofdc.domain;

import java.util.List;

/**
 * Created by wangyuzhe on 16/5/27.
 */
public class TrainIncident {

    /**
     * DateUpdated : 2010-07-29T14:21:28
     * DelaySeverity : null
     * Description : Red Line: Expect residual delays to Glenmont due to an earlier signal problem outside Forest Glen.
     * EmergencyText : null
     * EndLocationFullName : null
     * IncidentID : 3754F8B2-A0A6-494E-A4B5-82C9E72DFA74
     * IncidentType : Delay
     * LinesAffected : RD;
     * PassengerDelay : 0
     * StartLocationFullName : null
     */

    private List<IncidentsBean> Incidents;

    public List<IncidentsBean> getIncidents() {
        return Incidents;
    }

    public void setIncidents(List<IncidentsBean> Incidents) {
        this.Incidents = Incidents;
    }

    public static class IncidentsBean {
        private String DateUpdated;
        private Object DelaySeverity;
        private String Description;
        private Object EmergencyText;
        private Object EndLocationFullName;
        private String IncidentID;
        private String IncidentType;
        private String LinesAffected;
        private int PassengerDelay;
        private Object StartLocationFullName;

        public String getDateUpdated() {
            return DateUpdated;
        }

        public void setDateUpdated(String DateUpdated) {
            this.DateUpdated = DateUpdated;
        }

        public Object getDelaySeverity() {
            return DelaySeverity;
        }

        public void setDelaySeverity(Object DelaySeverity) {
            this.DelaySeverity = DelaySeverity;
        }

        public String getDescription() {
            return Description;
        }

        public void setDescription(String Description) {
            this.Description = Description;
        }

        public Object getEmergencyText() {
            return EmergencyText;
        }

        public void setEmergencyText(Object EmergencyText) {
            this.EmergencyText = EmergencyText;
        }

        public Object getEndLocationFullName() {
            return EndLocationFullName;
        }

        public void setEndLocationFullName(Object EndLocationFullName) {
            this.EndLocationFullName = EndLocationFullName;
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

        public String getLinesAffected() {
            return LinesAffected;
        }

        public void setLinesAffected(String LinesAffected) {
            this.LinesAffected = LinesAffected;
        }

        public int getPassengerDelay() {
            return PassengerDelay;
        }

        public void setPassengerDelay(int PassengerDelay) {
            this.PassengerDelay = PassengerDelay;
        }

        public Object getStartLocationFullName() {
            return StartLocationFullName;
        }

        public void setStartLocationFullName(Object StartLocationFullName) {
            this.StartLocationFullName = StartLocationFullName;
        }
    }
}
