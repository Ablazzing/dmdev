package org.molodyko.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = {"categoryBefore", "categoryAfter", "user"})
@Table(name = "category_rename")
public class CategoryRename {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "category_before_id")
    private Category categoryBefore;

    @ManyToOne
    @JoinColumn(name = "category_after_id")
    private Category categoryAfter;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
}
