package org.molodyko.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

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
@Table(name = "category_rename")
public class CategoryRename {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "category_before_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Category categoryBefore;

    @ManyToOne
    @ToString.Exclude
    @JoinColumn(name = "category_after_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Category categoryAfter;

    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = "user_id")
    @OnDelete(action = OnDeleteAction.CASCADE)
    private User user;
}
