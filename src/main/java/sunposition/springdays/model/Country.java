package sunposition.springdays.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Data
@Table(name = "country")
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "capital")
    private String capital;

    @Column(name = "population")
    private Long population;

    @Column(name = "language")
    private String language;

    @OneToMany(mappedBy = "country", cascade = CascadeType.ALL)
    private List<Day> days = new ArrayList<>();
}