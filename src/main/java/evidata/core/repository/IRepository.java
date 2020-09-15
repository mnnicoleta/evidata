package evidata.core.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;
import evidata.core.model.BaseEntity;

import java.io.Serializable;

/**
 * Created by Nicolle on iun. in 2018
 */
@NoRepositoryBean
@Transactional
public interface IRepository<T extends BaseEntity<ID>, ID extends Serializable> extends JpaRepository<T, ID> {
}
