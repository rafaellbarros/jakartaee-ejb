package br.com.rafaellbarros.jakartaee.ejb.model.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Setter
@Getter
@EqualsAndHashCode
@NamedQueries({
        @NamedQuery(name="getPersonByISSN", query="select p from Person p where p.id = :issn")
})
@Entity
@Table(name = "PERSON", schema = "OT")
public class Person implements Serializable {

    @Id
    @Column(name = "ID_PERSON", nullable = false, precision = 0)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq_person_generator")
    @SequenceGenerator(sequenceName = "OT.SEQ_PERSON", initialValue = 1, allocationSize = 1, schema = "OT", name = "seq_person_generator")
    private Long id;

    @Column(name = "NM_PERSON", nullable = false, length = 150)
    private String name;

    @Basic
    @Column(name = "NM_MIDDLE", nullable = true, length = 100)
    private String middle;

    @Basic
    @Column(name = "NM_FAMILY", nullable = false, length = 50)
    private String family;

    @Column(name = "ID_ISSN", nullable = false, length = 50)
    private String issn;

    @Column(name = "DT_CREATED", nullable = false)
    private Date creation;

    @Column(name = "ST_PERSON", nullable = true, length = 1)
    private String status;

}
