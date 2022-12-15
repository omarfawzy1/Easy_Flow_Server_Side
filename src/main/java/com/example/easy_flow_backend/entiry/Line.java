package com.example.easy_flow_backend.entiry;
import jakarta.persistence.*;
import java.util.Objects;

@Entity
public class Line {
    @Id
    private String line_id;

    private float price;

    @ManyToOne
    @JoinColumn(name="owner_id")
    private Owner owner_fk;

    public Line() {

    }

    public Line(String line_id, float price, Owner owner_fk) {
        this.line_id = line_id;
        this.price = price;
        this.owner_fk = owner_fk;
    }

    public String getLine_id() {
        return line_id;
    }

    public void setLine_id(String line_id) {
        this.line_id = line_id;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Owner getOwner_fk() {
        return owner_fk;
    }

    public void setOwner_fk(Owner owner_fk) {
        this.owner_fk = owner_fk;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Line line = (Line) o;
        return Float.compare(line.price, price) == 0 && Objects.equals(line_id, line.line_id) && Objects.equals(owner_fk, line.owner_fk);
    }

    @Override
    public int hashCode() {
        return Objects.hash(line_id, price, owner_fk);
    }
}
