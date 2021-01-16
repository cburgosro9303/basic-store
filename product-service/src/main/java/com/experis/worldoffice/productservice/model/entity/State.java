package com.experis.worldoffice.productservice.model.entity;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(schema = "PRODUCT", name = "STATE")
public class State {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "brand_seq_gen")
    @SequenceGenerator(name = "brand_seq_gen", sequenceName = "brand_id_seq")
    @Column(name="ID")
    private Long id;

    @Column(name = "NAME",length = 50, unique = true)
    private String name;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "State{" +
            "id=" + id +
            ", name='" + name + '\'' +
            '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        State state = (State) o;
        return Objects.equals(getId(), state.getId()) && Objects.equals(getName(), state.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }
}
