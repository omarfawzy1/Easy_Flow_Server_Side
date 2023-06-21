package com.example.easy_flow_backend.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.GenericGenerator;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Setter
@Getter
@NoArgsConstructor
public class Owner {

    @Id
    @GeneratedValue(generator = "uuid")
    @GenericGenerator(name = "uuid", strategy = "uuid2")
    @Column(name = "owner_id")
    private String id;
    @Column(nullable = false, unique = true)
    private String name;

    private String mail;

    @Column(name = "bank_account")
    private String bankAccount;
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private Set<Line> lines = new HashSet<>();
    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private Set<Ticket> tickets = new HashSet<>();
    @OneToMany(mappedBy = "owner")
    private Set<Turnstile> turnstiles = new HashSet<>();
    @OneToMany(mappedBy = "owner")
    private Set<Plan> plans = new HashSet<>();
    @OneToOne(optional = true)
    @JoinColumn(name = "image_id", referencedColumnName ="image_id")
    @Cascade(org.hibernate.annotations.CascadeType.ALL)
    private ImageData imageData;

    public Owner(String name, String mail, String bankAccount) {
        this.name = name;
        this.mail = mail;
        this.bankAccount = bankAccount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Owner owner = (Owner) o;
        return name.equals(owner.name) && mail.equals(owner.mail) && bankAccount.equals(owner.bankAccount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, mail, bankAccount);
    }
}