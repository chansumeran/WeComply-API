package com.WeComply.WeComplyBackend.service;

import com.WeComply.WeComplyBackend.dto.SanctionUpdateRequest;
import com.WeComply.WeComplyBackend.entity.Sanction;
import com.WeComply.WeComplyBackend.repository.SanctionRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SanctionServiceImpl implements SanctionService {

    private final SanctionRepository sanctionRepository;

    @Override
    public void updateSanctionDescriptions(List<SanctionUpdateRequest> updateRequests) {

        for (SanctionUpdateRequest request : updateRequests) {
            Sanction existingSanction = sanctionRepository.findById(request.getSanctionId())
                    .orElseThrow(() -> new EntityNotFoundException("Sanction not found with id: " + request.getSanctionId()));

            existingSanction.setDescription(request.getNewDescription());
            sanctionRepository.save(existingSanction);
        }
    }

    @Override
    public void updateSanctionTriggers(List<SanctionUpdateRequest> updateRequests) {

        for (SanctionUpdateRequest request : updateRequests) {
            Sanction existingSanction = sanctionRepository.findById(request.getSanctionId())
                    .orElseThrow(() -> new EntityNotFoundException("Sanction not found with id: " + request.getSanctionId()));

            existingSanction.setTriggerValue(request.getNewTrigger());
            sanctionRepository.save(existingSanction);
        }
    }

    @Override
    public void resetSanctionConfig() {
        resetSanctionValuesById(2, 1, "Warning");
        resetSanctionValuesById(3, 4, "Counselling");
        resetSanctionValuesById(4, 7, "Payment");
    }

    private void resetSanctionValuesById(int sanctionId, int trigger, String description) {
        Optional<Sanction> optionalSanction = sanctionRepository.findBySanctionId(sanctionId);
        optionalSanction.ifPresent(sanction -> {
            sanction.setTriggerValue(trigger);
            sanction.setDescription(description);
            sanctionRepository.save(sanction);
        });
    }

}
