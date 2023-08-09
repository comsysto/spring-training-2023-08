package com.comsysto.trainings.springtrainingeon.app.adapter.web.out.chargingpointstate;

import com.comsysto.trainings.springtrainingeon.app.domain.ChargingPointName;
import com.comsysto.trainings.springtrainingeon.app.domain.charging.ChargingPointState;
import com.comsysto.trainings.springtrainingeon.app.domain.charging.ChargingStationId;
import com.comsysto.trainings.springtrainingeon.app.port.context.out.ContextRepository;
import com.comsysto.trainings.springtrainingeon.app.port.web.out.chargingpointstate.ChargingPointStateClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class RestChargingPointStateClient implements ChargingPointStateClient
{
	private final ContextRepository contextRepository;

	@Override
	public ChargingPointState getChargingPointState(ChargingStationId chargingStationId, ChargingPointName name)
	{
		log.error("Context: %s".formatted(contextRepository.getContext()));

		return ChargingPointState.UNKNOWN;
	}

}
