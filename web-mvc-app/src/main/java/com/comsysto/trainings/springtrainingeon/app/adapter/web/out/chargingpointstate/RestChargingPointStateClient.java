package com.comsysto.trainings.springtrainingeon.app.adapter.web.out.chargingpointstate;

import com.comsysto.trainings.springtrainingeon.app.domain.ChargingPointName;
import com.comsysto.trainings.springtrainingeon.app.domain.charging.ChargingPointState;
import com.comsysto.trainings.springtrainingeon.app.domain.charging.ChargingStationId;
import com.comsysto.trainings.springtrainingeon.app.port.context.out.ContextRepository;
import com.comsysto.trainings.springtrainingeon.app.port.web.out.chargingpointstate.ChargingPointStateClient;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import static com.comsysto.trainings.springtrainingeon.app.adapter.web.out.chargingpointstate.WebClientConfig.MY_WEB_CLIENT;

@Slf4j
@Component
public class RestChargingPointStateClient implements ChargingPointStateClient
{
	private final ContextRepository contextRepository;
	private final WebClient webClient;

	public RestChargingPointStateClient(ContextRepository contextRepository, @Qualifier(MY_WEB_CLIENT) WebClient webClient)
	{
		this.contextRepository = contextRepository;
		this.webClient = webClient;
	}

	@Data
	public static class ChargingPointResponse {
		private ChargingPointState state;
	}

	@Override
	public ChargingPointState getChargingPointState(ChargingStationId chargingStationId, ChargingPointName name)
	{
		log.error("Context: %s".formatted(contextRepository.getContext()));
		ChargingPointResponse response = webClient.get()
				.uri(chargingStationId.getValue() + "/" + name.getValue())
				.retrieve()
				.bodyToMono(ChargingPointResponse.class)
				.block();

		if (response == null) {
			log.warn("Could not retrieve charging point state");
			return ChargingPointState.UNKNOWN;
		}

		return response.getState();
	}

}
