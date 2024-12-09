package com.sparta_logistics.order.domain.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;


@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@SQLDelete(sql = "UPDATE p_order SET deleted_at = NOW() where id = ?")
@Table(name = "p_order")
public class Order extends Base {

  @Id
  @GeneratedValue(strategy = GenerationType.UUID)
  private String id;

  @Column(nullable = false)
  private String supplierCompanyId;

  @Column(nullable = false)
  private String receiverCompanyId;

  @Column(nullable = false)
  private String userId;

  @Column(nullable = false)
  private String productId;

  @Column(nullable = false, unique = true)
  private String deliveryId;

  @Column(nullable = false)
  private Integer quantity;

  private String requestDescription;

  @Column(nullable = false)
  private Boolean isRefunded;
}
