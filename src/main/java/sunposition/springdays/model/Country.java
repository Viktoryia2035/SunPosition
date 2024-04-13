package sunposition.springdays.model;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
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
