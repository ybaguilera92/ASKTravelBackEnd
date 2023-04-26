package cu.sitrans.asktravel.service.strategies;

import cu.sitrans.asktravel.models.generic.BaseEntity;
import cu.sitrans.asktravel.payload.request.BaseDTO;

import java.util.Map;

public interface BaseGetStrategy <T extends BaseEntity> {
    public Map<String, Object> getList(BaseDTO baseDTO);
}
