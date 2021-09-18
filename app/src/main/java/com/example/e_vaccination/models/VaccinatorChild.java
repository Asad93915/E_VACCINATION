package com.example.e_vaccination.models;

public class VaccinatorChild {
    private User user;
    private Child child;
    private ChildVaccines childVaccines;

    public VaccinatorChild(User user, Child child, ChildVaccines childVaccines) {
        this.user = user;
        this.child = child;
        this.childVaccines = childVaccines;
    }

    public VaccinatorChild() {
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Child getChild() {
        return child;
    }

    public void setChild(Child child) {
        this.child = child;
    }

    public ChildVaccines getChildVaccines() {
        return childVaccines;
    }

    public void setChildVaccines(ChildVaccines childVaccines) {
        this.childVaccines = childVaccines;
    }
}
