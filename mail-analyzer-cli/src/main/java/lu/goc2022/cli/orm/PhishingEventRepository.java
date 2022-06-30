package lu.goc2022.cli.orm;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhishingEventRepository extends CrudRepository<PhishingEvent, Long> {

	public List<PhishingEvent> findAllByCreationDateGreaterThan(Timestamp theta);

}
