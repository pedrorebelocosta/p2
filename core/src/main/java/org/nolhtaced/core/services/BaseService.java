package org.nolhtaced.core.services;

import org.modelmapper.ModelMapper;
import org.nolhtaced.core.NolhtacedSession;
import org.nolhtaced.core.providers.ModelMapperProvider;

public class BaseService {
    protected final ModelMapper mapper = ModelMapperProvider.getModelMapper();
    protected NolhtacedSession session;

    public BaseService(NolhtacedSession session) {
        this.session = session;
    }
}
