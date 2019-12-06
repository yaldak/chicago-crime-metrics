package records;

import java.time.ZonedDateTime;
import java.util.Objects;

public class CrimeRecord {
    class Location {
        public double latitude;
        public double longitude;
        public String humanAddress;

        @Override
        public String toString() {
            return "Location{" + "latitude=" + latitude + ", longitude=" + longitude +
                    ", humanAddress='" + humanAddress + '\'' + '}';
        }
    }

    public int id;
    public String caseNumber;
    public ZonedDateTime date;
    public String block;
    public String iucr;
    public String primaryType;
    public String description;
    public String locationDescription;
    public boolean arrest;
    public boolean domestic;
    public int beat;
    public int district;
    public int ward;
    public int communityArea;
    public String fbiCode;
    public int xCoordinate;
    public int yCoordinate;
    public int year;
    public ZonedDateTime updatedOn;
    public Location location;

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
