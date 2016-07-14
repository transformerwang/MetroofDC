package wyz.android.com.metroofdc.domain;

import java.util.List;

/**
 * Created by wangyuzhe on 16/5/22.
 */
public class BusStations {

    /**
     * RouteID : 10A
     * Name : 10A - HUNTING POINT -PENTAGON
     * LineDescription : Hunting Point-Pentagon Line
     */

    private List<RoutesBean> Routes;

    public List<RoutesBean> getRoutes() {
        return Routes;
    }

    public void setRoutes(List<RoutesBean> Routes) {
        this.Routes = Routes;
    }

    public static class RoutesBean {
        private String RouteID;
        private String Name;
        private String LineDescription;

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

        public String getLineDescription() {
            return LineDescription;
        }

        public void setLineDescription(String LineDescription) {
            this.LineDescription = LineDescription;
        }
    }
}
