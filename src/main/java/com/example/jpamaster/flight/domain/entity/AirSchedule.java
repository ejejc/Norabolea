package com.example.jpamaster.flight.domain.entity;

import com.example.jpamaster.common.domain.BaseEntity;
import com.example.jpamaster.common.utils.CommonUtils;
import com.example.jpamaster.flight.constant.FlightConstant;
import com.example.jpamaster.flight.util.FlightUtils;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "air_schedule")
@Where(clause = " deleted = false ")
@Entity
public class AirSchedule extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long airScheduleSeq;

    private LocalDateTime departAt;

    private LocalDateTime arriveAt;

    private Integer flightDistanceKm;

    private Integer estimatedHourSpent;

    private Integer estimatedMinuteSpent;

    private Boolean deleted;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "airplane_seq")
    private Airplane airplane;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "dept_airport_seq")
    private Airport deptAirport;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "arr_airport_seq")
    private Airport arrAirport;

    @OneToMany(mappedBy = "airSchedule", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<AirScheduleSeatType> airScheduleSeatTypes = new HashSet<>();

    private AirSchedule(Airport deptAirport, Airport arrAirport, Airplane airplane) {
        this.deptAirport = deptAirport;
        this.arrAirport = arrAirport;
        this.airplane = airplane;
        this.deleted = false;
    }

    public static AirSchedule createAirSchedule(Airport fromAirport, Airport toAirport, Airplane airplane) {
        return new AirSchedule(fromAirport, toAirport, airplane);
    }

    public void calculateAirSchedule(String expectedTakeoffDate, String expectedTakeoffTime) {
        LocalDateTime depart = FlightUtils.toLocalDateTime(expectedTakeoffDate, expectedTakeoffTime);

        int distance = FlightUtils.getDistanceAsKm(
            CommonUtils.getDoubleFromStr(deptAirport.getLat()),
            CommonUtils.getDoubleFromStr(deptAirport.getLon()),
            CommonUtils.getDoubleFromStr(arrAirport.getLat()),
            CommonUtils.getDoubleFromStr(arrAirport.getLon())
        );

        double approximateTime = FlightUtils.round(distance / FlightConstant.AVERAGE_AIRPLANE_SPEED, 2);

        int hour = (int) approximateTime;
        int min = (int) (((approximateTime - hour) * 3600) / 60);

        LocalDateTime arrive = FlightUtils.calculateArriveLocalDateTime(arrAirport.getLocationEn(), depart, hour, min);

        this.departAt = depart;
        this.arriveAt = arrive;
        this.estimatedHourSpent = hour;
        this.estimatedMinuteSpent = min;
        this.flightDistanceKm = distance;
    }

    public void updateAirSchedule(Airport fromAirport, Airport toAirport, String expectedTakeoffDate, String expectedTakeoffTime) {
        if (deptAirport != null && arrAirport != null && (this.deptAirport.equals(fromAirport) || this.arrAirport.equals(toAirport))) {
            this.deptAirport = fromAirport;
            this.arrAirport = toAirport;

            this.calculateAirSchedule(expectedTakeoffDate, expectedTakeoffTime);
        }
    }
}
