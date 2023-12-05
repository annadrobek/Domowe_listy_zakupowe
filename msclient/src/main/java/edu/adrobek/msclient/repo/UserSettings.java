package edu.adrobek.msclient.repo;

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "UserSettings")
public class UserSettings {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usersettings_generator")
    private int id;

    @Column(name = "setting_name")
    private String setting_name;

    @Column(name = "setting_value")
    private String setting_value;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    public UserSettings() {

    }

    public UserSettings(int user_id, String setting_name, String setting_value, User user) {
        this.user = user;
        setName(setting_name);
        setValue(setting_value);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return setting_name;
    }

    public void setName(String setting_name) {
        this.setting_name = setting_name;
    }

    public String getValue() {
        return setting_value;
    }

    public void setValue(String setting_value) {
        this.setting_value = setting_value;
    }
}
