package com.rkaaya.limepublisher.infrastructure.interfaces;

import com.rkaaya.lime.domain.model.Lime;
import com.rkaaya.lime.server.api.BetvictorApi;
import com.rkaaya.limepublisher.api.services.LimeTextService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LimeController implements BetvictorApi {

    @Autowired
    private LimeTextService limeTextService;

    @Override
    public ResponseEntity<Lime> processLimeText(Integer pStart, Integer pEnd, Integer wCountMin, Integer wCountMax) {
        return ResponseEntity.ok(limeTextService.processLime(pStart, pEnd, wCountMin, wCountMax));
    }

}
