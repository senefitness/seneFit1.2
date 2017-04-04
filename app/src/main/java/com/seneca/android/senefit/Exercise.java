package com.seneca.android.senefit;

/**
 * Created by dance on 3/30/2017.
 */

public class Exercise {

    private String name;
    private String description;
    private String id;
    private String license_author;
    private String name_original;
    private String creation_date;
    private String category;



    public Exercise() {

    }
    public Exercise(String name, String description,String id_,String l_a,String n_o,String c_d,String cat) {
        this.name = name;
        this.description = description;
        id = id_;
        license_author = l_a;
        name_original = n_o;
        creation_date = c_d;
        category = cat;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public String getId() {
        return id;
    }

    public String getLicense_author() {
        return license_author;
    }

    public String getName_original() {
        return name_original;
    }

    public String getCreation_date() {
        return creation_date;
    }

    public String getCategory() {
        return category;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setLicense_author(String license_author) {
        this.license_author = license_author;
    }

    public void setName_original(String name_original) {
        this.name_original = name_original;
    }

    public void setCreation_date(String creation_date) {
        this.creation_date = creation_date;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Exercise{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
