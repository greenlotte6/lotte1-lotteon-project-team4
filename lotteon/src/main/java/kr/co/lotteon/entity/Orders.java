package kr.co.lotteon.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
@Table(name = "orders")
public class Orders {

    @Id
    private int oid;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Users_uid")
    private Users users;

    private int order_total;
    private String payment;
    private String order_status;

    @CreationTimestamp
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime order_date;

    private String shipping_status;

    @OneToMany(mappedBy = "orders")
    private List<OrderItem> orderItems;

    private String recipient;

}
