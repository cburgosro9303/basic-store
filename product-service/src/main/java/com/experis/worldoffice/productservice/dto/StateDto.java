package com.experis.worldoffice.productservice.dto;

import java.io.Serializable;
import java.util.Objects;

public class StateDto implements Serializable {


    private Long id;
    private String name;

    public StateDto() {
    }

    public StateDto(Long id, String name) {
        this.id = id;
        this.name = name;
    }

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
        StateDto state = (StateDto) o;
        return Objects.equals(getId(), state.getId()) && Objects.equals(getName(), state.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());
    }
}
