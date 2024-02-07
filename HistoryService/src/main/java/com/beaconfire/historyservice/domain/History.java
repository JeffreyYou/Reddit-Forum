package com.beaconfire.historyservice.domain;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="History")
public class History {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema
    private Long historyId;

    @Column
    @Schema
    private Long userId;

    @Column
    @Schema
    private String postId;

    @Column
    @Schema
    private Date viewDate;

}
