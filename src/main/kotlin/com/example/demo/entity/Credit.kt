package com.example.demo.entity

import com.example.demo.entity.enummeration.Status
import jakarta.persistence.*
import org.hibernate.Hibernate
import java.math.BigDecimal
import java.time.LocalDate
import java.util.UUID

@Entity
@Table(name = "Credit")
data class Credit (
    @Column(nullable = false, unique = true) var creditCode: UUID = UUID.randomUUID(),
    @Column(nullable = false) val creditValue: BigDecimal = BigDecimal.ZERO,
    @Column(nullable = false) val dayFirstInstallment: LocalDate,
    @Column(nullable = false) val numberOfInstallments: Int = 0,
    @Enumerated val status: Status = Status.IN_PROGRESS,
    //Poderia ser @Enumerated(value = EnumType.STRING), e ao gerar a DDL o status sera de varchar mostrando o que está no enum
    @ManyToOne var customer: Customer? = null,
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) val id: Long? = null
)