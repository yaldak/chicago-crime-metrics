package records;

import java.util.Date;
import java.util.Objects;

public class CrimeRecord {
    class Location {
        double latitude;
        double longitude;
        String humanAddress;

        @Override
        public String toString() {
            return "Location{" + "latitude=" + latitude + ", longitude=" + longitude + ", humanAddress='" + humanAddress + '\'' + '}';
        }
    }

    int id;
    String caseNumber;
    Date date;
    String block;
    String iucr;
    String primaryType;
    String description;
    String locationDescription;
    boolean arrest;
    boolean domestic;
    int beat;
    int district;
    int ward;
    int communityArea;
    String fbiCode;
    int xCoordinate;
    int yCoordinate;
    int year;
    Date updatedOn;
    Location location;

    @Override
    public String toString() {
        return "CrimeRecord{" + "id=" + id + ", caseNumber='" + caseNumber + '\'' + ", date='" + date + '\'' +
                ", block='" + block + '\'' + ", iucr='" + iucr + '\'' + ", primaryType='" + primaryType + '\'' +
                ", description='" + description + '\'' + ", locationDescription='" + locationDescription + '\'' +
                ", arrest=" + arrest + ", domestic=" + domestic + ", beat=" + beat + ", district=" + district +
                ", ward=" + ward + ", communityArea=" + communityArea + ", fbiCode='" + fbiCode + '\'' +
                ", xCoordinate=" + xCoordinate + ", yCoordinate=" + yCoordinate + ", year=" + year +
                ", updatedOn='" + updatedOn + '\'' + ", location=" +
                    (Objects.isNull(location) ? "null" : location.toString()) + '}';
    }
}
