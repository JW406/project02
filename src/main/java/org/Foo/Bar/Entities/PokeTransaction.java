package org.Foo.Bar.Entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;

import org.springframework.stereotype.Component;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Entity
@Table(name = "proj02_poke_transaction")
public class PokeTransaction {
  @Setter(value = AccessLevel.NONE)
  @JsonIgnore
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "PROJ02_POKE_TRANSACTION")
  @SequenceGenerator(name = "PROJ02_POKE_TRANSACTION", sequenceName = "PROJ02_POKE_TRANSACTION_S", allocationSize = 1)
  @Column
  private Integer id;

  @Column(name = "tx_created_time")
  @Temporal(TemporalType.TIMESTAMP)
  private Date txCreatedTime;

  @Column(name = "tx_source_type")
  @Enumerated(EnumType.STRING)
  private TxType txSourceType;

  @Column(name = "poke_token_exchanged")
  private Long pokeTokenExchanged;
}
