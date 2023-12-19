package com.WeComply.WeComplyBackend.service;

import com.WeComply.WeComplyBackend.dto.SanctionUpdateRequest;
import com.WeComply.WeComplyBackend.entity.Sanction;

import java.util.List;
import java.util.Optional;

public interface SanctionService {
    void updateSanctionDescriptions(List<SanctionUpdateRequest> updateRequests);

    void updateSanctionTriggers(List<SanctionUpdateRequest> updateRequests);
    void resetSanctionConfig();
}
