package model.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.io.Serializable;

@Entity
@Table(name = "MYRESULTS")
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Result implements Serializable {
    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "make_id")
    private int id;

    @Column(name = "valueX")
    @Min(-3)
    @Max(3)
    private double x;

    @Column(name = "valueY")
    @Min(-3)
    @Max(5)
    private double y;

    @Column(name = "valueR")
    @Min(1)
    @Max(5)
    private double r;

    @Column(name = "currTime", length = 100)
    private String currentTime;

    @Column(name = "execTime")
    private double time;

    @Column(name = "myResult", length = 100)
    private boolean result;


    public Result(double x, double y, double r, String currentTime, double time) {
        this.x = x;
        this.y = y;
        this.r = r;
        this.currentTime = currentTime;
        this.time = time;
    }

    @Override
    public String toString() {
        return "model.entity.Result{" +
                "id" + id +
                "x=" + x +
                ", y=" + y +
                ", r=" + r +
                ", currentTime='" + currentTime +
                ", time=" + time +
                ", result=" + result +
                '}';
    }



}