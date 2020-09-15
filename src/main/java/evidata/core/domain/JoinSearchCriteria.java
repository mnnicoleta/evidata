package evidata.core.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Created by Nicolle on aug. in 2018
 */
@Setter
@Getter
@NoArgsConstructor
public class JoinSearchCriteria extends SearchCriteria {
    private String relName;

    public JoinSearchCriteria(String relName, String key, OperationEnum operation, Object value) {
        super(key, operation, value);
        this.relName = relName;
    }
}
